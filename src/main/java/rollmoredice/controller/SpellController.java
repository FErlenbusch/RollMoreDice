package rollmoredice.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import rollmoredice.entities.Spell;
import rollmoredice.exception.ResourceNotFoundException;
import rollmoredice.payload.ApiResponse;
import rollmoredice.payload.SpellRequest;
import rollmoredice.payload.SpellSummary;
import rollmoredice.repositories.SpellRepository;
import rollmoredice.security.CurrentUser;
import rollmoredice.security.UserPrincipal;
import rollmoredice.service.ModelMapper;
import rollmoredice.service.SpellService;


@RestController
@Api(tags = { "Spell Api" })
@RequestMapping("/api")
public class SpellController {
	
	@Autowired
	private SpellService spellService;
	
	@Autowired
	private SpellRepository spellRepository;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SpellController.class);
	
	
	@GetMapping("/spell/{spellId}")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets a spell given a spell's Id number",
		authorizations = {@Authorization(value = "Bearer Token")})
	public SpellSummary getSpell(@PathVariable("spellId") long spellId) {
		
		Spell spell = spellRepository.findById(spellId)
				.orElseThrow(() -> new ResourceNotFoundException("Spell", "spellId", spellId));
		
		return new ModelMapper().mapSpellToSummary(spell);
	}
	
	@PostMapping("/spell")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "POST", 
		value = "Creates a spell to be associated with the user creating it as the creator",
		authorizations = {@Authorization(value = "Bearer Token")})
    public ResponseEntity<?> createSpell(@Valid @RequestBody SpellRequest spellRequest,
    		@CurrentUser UserPrincipal currentUser) {
		
        Spell spell = spellService.createSpell(spellRequest, currentUser.getId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{spellId}")
                .buildAndExpand(spell.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Spell Created Successfully"));
    }
	
	@PutMapping(value="/spell/{spellId}")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "PUT", 
		value = "Updates a existing spell",
		notes = "Updates spell of given spellId with given information",
		authorizations = {@Authorization(value = "Bearer Token")})
    public ResponseEntity<?> updateSpell(@PathVariable("spellId") long spellId,
                                           @Valid @RequestBody SpellSummary summary) {
        
		Spell spell = spellRepository.findById(spellId)
				.orElseThrow(() -> new ResourceNotFoundException("Spell", "spellId", spellId));
		
		if (spell.getId() == summary.getId()) {
			Spell updatedSpell = spellRepository.save(new ModelMapper().mapSummaryToSpell(spell, summary));
			return ResponseEntity.ok().body(new ModelMapper().mapSpellToSummary(updatedSpell));
		}
		
		return (ResponseEntity<?>) ResponseEntity.badRequest();
    }
	
	@DeleteMapping(value="/spell/{spellId}")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "DELETE", 
		value = "Deletes the spell of with the given spellId",
		authorizations = {@Authorization(value = "Bearer Token")})
    public ResponseEntity<?> deleteSpell(@PathVariable("spellId") long spellId) {
        return spellRepository.findById(spellId)
                .map(spell -> {
                    spellRepository.deleteById(spellId);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
