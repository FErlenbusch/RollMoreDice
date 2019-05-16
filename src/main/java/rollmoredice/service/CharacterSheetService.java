package rollmoredice.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import rollmoredice.entities.CharacterSheet;
import rollmoredice.entities.User;
import rollmoredice.exception.CharacterExistsException;
import rollmoredice.exception.ResourceNotFoundException;
import rollmoredice.payload.CharacterSheetRequest;
import rollmoredice.payload.CharacterSheetSummary;
import rollmoredice.payload.PagedResponse;
import rollmoredice.repositories.CharacterSheetRepository;
import rollmoredice.repositories.UserRepository;
import rollmoredice.security.UserPrincipal;

@Service
public class CharacterSheetService {
	
	@Autowired 
	private CharacterSheetRepository characterSheetRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CharacterSheetService.class);
	
	
	public PagedResponse<CharacterSheetSummary> getAllUsersCharacters(
			UserPrincipal currentUser, int page, int size) {
		
		new ModelMapper().validatePageNumberAndSize(page, size);
		
		User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", currentUser.getId()));
		
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
	    Page<CharacterSheet> sheets = characterSheetRepository.findByCreatedBy(user.getID(), pageable);
	     
	    return response(sheets);
	}
	
	public CharacterSheet createCharacter(CharacterSheetRequest characterRequest, long userId) {
		
		User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		
		if (characterSheetRepository.existsByUserIdAndCharName(user.getID(), characterRequest.getCharName())) {
			throw new CharacterExistsException(user.getUsername(), characterRequest.getCharName());
		}
		
		CharacterSheet character = new ModelMapper().mapRequestToCharacterSheet(characterRequest, user);

        return characterSheetRepository.save(character);
    }
	
	private PagedResponse<CharacterSheetSummary> response(Page<CharacterSheet> sheets) {
		if (sheets.getNumberOfElements() == 0) {
	    	return new PagedResponse<>(Collections.emptyList(), sheets.getNumber(),
	    			sheets.getSize(), sheets.getTotalElements(), 
	    			sheets.getTotalPages(), sheets.isLast());
	    }
	     
	    List<CharacterSheetSummary> characterSheetSummaries = sheets.map(sheet -> {
	    	return new ModelMapper().mapCharacterSheetToSummary(sheet); }).getContent();
	     
	    return new PagedResponse<>(characterSheetSummaries, sheets.getNumber(), 
	    		sheets.getSize(), sheets.getTotalElements(), 
	    		sheets.getTotalPages(), sheets.isLast());
	}
	
	
}
