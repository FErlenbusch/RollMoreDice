package rollmoredice.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import rollmoredice.entities.CharacterSheet;
import rollmoredice.entities.Item;
import rollmoredice.entities.Spell;
import rollmoredice.exception.ResourceNotFoundException;
import rollmoredice.payload.ApiResponse;
import rollmoredice.payload.CharacterSheetRequest;
import rollmoredice.payload.CharacterSheetSummary;
import rollmoredice.payload.ItemSummary;
import rollmoredice.payload.PagedResponse;
import rollmoredice.payload.SpellSummary;
import rollmoredice.repositories.CharacterSheetRepository;
import rollmoredice.repositories.ItemRepository;
import rollmoredice.repositories.SpellRepository;
import rollmoredice.security.CurrentUser;
import rollmoredice.security.UserPrincipal;
import rollmoredice.service.CharacterSheetService;
import rollmoredice.service.ItemService;
import rollmoredice.service.ModelMapper;
import rollmoredice.service.SpellService;
import rollmoredice.util.AppConstants;



@RestController
@Api(tags = { "Character Sheet Api" })
@RequestMapping("/api")
public class CharacterSheetController {
	
	@Autowired
	private CharacterSheetService characterService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private SpellService spellService;
	
	@Autowired
	private CharacterSheetRepository characterRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired 
	private SpellRepository spellRepository;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CharacterSheetController.class);
	
	
	@GetMapping("/charactersheet/{sheetId}")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets a character sheet given a character's Id number",
		authorizations = {@Authorization(value = "Bearer Token")})
	public CharacterSheetSummary getCharacterSheet(@PathVariable("sheetId") long sheetId) {
		
		CharacterSheet sheet = characterRepository.findById(sheetId)
				.orElseThrow(() -> new ResourceNotFoundException("CharacterSheet", "sheetId", sheetId));
		
		return new ModelMapper().mapCharacterSheetToSummary(sheet);
	}
	
	@GetMapping("/charactersheet/items/{charId}")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets all a character's items given the character's Id number",
		authorizations = {@Authorization(value = "Bearer Token")})
	public PagedResponse<ItemSummary> getCharactersItems(@PathVariable("charId") long charId,
    		@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		
		return itemService.getCharactersItems(charId, page, size);
	}
	
	@GetMapping("/charactersheet/spells/{charId}")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets all a character's spells given the character's Id number",
		authorizations = {@Authorization(value = "Bearer Token")})
	public PagedResponse<SpellSummary> getCharactersSpells(@PathVariable("charId") long charId,
    		@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		
		return spellService.getCharactersSpells(charId, page, size);
	}
	
	@PostMapping("/charactersheet")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "POST", 
		value = "Creates a character to be associated with the user creating it",
		authorizations = {@Authorization(value = "Bearer Token")})
    public ResponseEntity<?> createCharacterSheet(@Valid @RequestBody CharacterSheetRequest characterRequest,
    		@CurrentUser UserPrincipal currentUser) {
		
        CharacterSheet character = characterService.createCharacter(characterRequest, currentUser.getId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{characterId}")
                .buildAndExpand(character.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Character Sheet Created Successfully"));
    }
	
	@PutMapping("/charactersheet/{sheetId}")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "PUT", 
		value = "Updates a existing character sheet",
		notes = "Updates sheet of given character sheet id with given information",
		authorizations = {@Authorization(value = "Bearer Token")})
	public ResponseEntity<?> updateCharacterSheet(@PathVariable("sheetId") long sheetId,
            @Valid @RequestBody CharacterSheetSummary newSheet) {
		
		CharacterSheet oldSheet = characterRepository.findById(sheetId)
				.orElseThrow(() -> new ResourceNotFoundException("CharacterSheet", "sheetId", sheetId));
		
		if (oldSheet.getId() == newSheet.getId()) {
			CharacterSheet updatedSheet = characterRepository.save(
					new ModelMapper().mapSummaryToCharacterSheet(oldSheet, newSheet));
			
			return ResponseEntity.ok(new ModelMapper().mapCharacterSheetToSummary(updatedSheet));
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/charactersheet/additem/{itemId}")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "PUT", 
		value = "Adds a item to a character sheet",
		notes = "Adds the item associated with the given itemId to the given character sheet",
		authorizations = {@Authorization(value = "Bearer Token")})
	public ResponseEntity<?> addItemToCharacter(@PathVariable("itemId") long itemId, 
			@Valid @RequestBody CharacterSheetSummary summary) {
		
		CharacterSheet sheet = characterRepository.findById(summary.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Character", "charId", summary.getId()));
		
		Item item = itemRepository.findById(itemId).orElseThrow(
				() -> new ResourceNotFoundException("Item", "itemId", itemId));
		
		sheet.getItems().add(item);
		
		characterRepository.save(sheet);
		
		return ResponseEntity.ok().body(new ModelMapper().mapCharacterSheetToSummary(sheet));
	}
	
	@PutMapping("/charactersheet/addspell/{spellId}")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "PUT", 
		value = "Adds a spell to a character sheet",
		notes = "Adds the spell associated with the given spellId to the given character sheet",
		authorizations = {@Authorization(value = "Bearer Token")})
	public ResponseEntity<?> addSpellToCharacter(@PathVariable("spellId") long spellId, 
			@Valid @RequestBody CharacterSheetSummary summary) {
		
		CharacterSheet sheet = characterRepository.findById(summary.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Character", "charId", summary.getId()));
		
		Spell spell = spellRepository.findById(spellId).orElseThrow(
				() -> new ResourceNotFoundException("Spell", "spellId", spellId));
		
		sheet.getSpells().add(spell);
		
		characterRepository.save(sheet);
		
		return ResponseEntity.ok().body(new ModelMapper().mapCharacterSheetToSummary(sheet));
	}
	
	
	@DeleteMapping(value="/charactersheet/{sheetId}")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "DELETE", 
		value = "Deletes the character sheet of the given character sheet id",
		authorizations = {@Authorization(value = "Bearer Token")})
    public ResponseEntity<?> deleteCharacterSheet(@PathVariable("sheetId") long sheetId) {
		return characterRepository.findById(sheetId)
                .map(sheet -> {
                    characterRepository.deleteById(sheetId);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
	}
}
