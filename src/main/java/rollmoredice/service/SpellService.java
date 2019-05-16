package rollmoredice.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import rollmoredice.entities.CharacterSheet;
import rollmoredice.entities.Game;
import rollmoredice.entities.Spell;
import rollmoredice.entities.User;
import rollmoredice.exception.ResourceNotFoundException;
import rollmoredice.payload.PagedResponse;
import rollmoredice.payload.SpellRequest;
import rollmoredice.payload.SpellSummary;
import rollmoredice.repositories.CharacterSheetRepository;
import rollmoredice.repositories.GameRepository;
import rollmoredice.repositories.SpellRepository;
import rollmoredice.repositories.UserRepository;
import rollmoredice.security.UserPrincipal;


@Service
public class SpellService {
	@Autowired
	private SpellRepository spellRepository;

	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private CharacterSheetRepository characterRepository;
	
	
	public PagedResponse<SpellSummary> getAllUsersSpells(UserPrincipal currentUser, int page, int size) {
		new ModelMapper().validatePageNumberAndSize(page, size);
		
		User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", currentUser.getId()));
		
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
	    Page<Spell> spells = spellRepository.findByCreatorUserId(user.getID(), pageable);
	     
	    return response(spells);
	}
	
	public PagedResponse<SpellSummary> getSpellsInAGame(long gameId, int page, int size) {
		new ModelMapper().validatePageNumberAndSize(page, size);
		
		Game game = gameRepository.findById(gameId)
				.orElseThrow(() -> new ResourceNotFoundException("Game", "gameId", gameId));
		
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
		Page<Spell> spells = spellRepository.findSpellsInGame(game.getId(), pageable);
		
		return response(spells);
	}
	
	public PagedResponse<SpellSummary> getCharactersSpells(long charId, int page, int size) {
		new ModelMapper().validatePageNumberAndSize(page, size);
		
		CharacterSheet sheet = characterRepository.findById(charId)
				.orElseThrow(() -> new ResourceNotFoundException("CharacterSheet", "charId", charId));
		
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
		Page<Spell> spells = spellRepository.findCharacterSpells(sheet.getId(), pageable);
		
		return response(spells);
	}
	
	public Spell createSpell(SpellRequest spellRequest, long userId) {
		
		User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		
		Spell spell = new ModelMapper().mapRequestToSpell(spellRequest, user);

        return spellRepository.save(spell);
    }
	
	private PagedResponse<SpellSummary> response(Page<Spell> spells) {
		if (spells.getNumberOfElements() == 0) {
	    	return new PagedResponse<>(Collections.emptyList(), spells.getNumber(),
	    			spells.getSize(), spells.getTotalElements(), 
	    			spells.getTotalPages(), spells.isLast());
	    }
	     
	    List<SpellSummary> spellSummaries = spells.map(spell -> {
	    	return new ModelMapper().mapSpellToSummary(spell); }).getContent();
	     
	    return new PagedResponse<>(spellSummaries, spells.getNumber(), 
	    		spells.getSize(), spells.getTotalElements(), 
	    		spells.getTotalPages(), spells.isLast());
	}
}
