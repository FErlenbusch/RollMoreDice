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
import rollmoredice.entities.Item;
import rollmoredice.entities.User;
import rollmoredice.exception.ResourceNotFoundException;
import rollmoredice.payload.ItemRequest;
import rollmoredice.payload.ItemSummary;
import rollmoredice.payload.PagedResponse;
import rollmoredice.repositories.CharacterSheetRepository;
import rollmoredice.repositories.GameRepository;
import rollmoredice.repositories.ItemRepository;
import rollmoredice.repositories.UserRepository;
import rollmoredice.security.UserPrincipal;


@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;

	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private CharacterSheetRepository characterRepository;
	
	public PagedResponse<ItemSummary> getAllUsersItems(UserPrincipal currentUser, int page, int size) {
		new ModelMapper().validatePageNumberAndSize(page, size);
		
		User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", currentUser.getId()));
		
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
	    Page<Item> items = itemRepository.findByCreatorUserId(user.getID(), pageable);
	     
	    return response(items);
	}
	
	public PagedResponse<ItemSummary> getItemsInAGame(long gameId, int page, int size) {
		new ModelMapper().validatePageNumberAndSize(page, size);
		
		Game game = gameRepository.findById(gameId)
				.orElseThrow(() -> new ResourceNotFoundException("Game", "gameId", gameId));
		
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
		Page<Item> items = itemRepository.findItemsInGame(game.getId(), pageable);
		
		return response(items);
	}
	
	public PagedResponse<ItemSummary> getCharactersItems(long charId, int page, int size) {
		new ModelMapper().validatePageNumberAndSize(page, size);
		
		CharacterSheet sheet = characterRepository.findById(charId)
				.orElseThrow(() -> new ResourceNotFoundException("CharacterSheet", "charId", charId));
		
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
		Page<Item> items = itemRepository.findCharacterItems(sheet.getId(), pageable);
		
		return response(items);
	}
	
	public Item createItem(ItemRequest itemRequest, long userId) {
		
		User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		
		Item item = new ModelMapper().mapRequestToItem(itemRequest, user);

        return itemRepository.save(item);
    }
	
	private PagedResponse<ItemSummary> response(Page<Item> items) {
		if (items.getNumberOfElements() == 0) {
	    	return new PagedResponse<>(Collections.emptyList(), items.getNumber(),
	    			items.getSize(), items.getTotalElements(), 
	    			items.getTotalPages(), items.isLast());
	    }
	     
	    List<ItemSummary> itemSummaries = items.map(item -> {
	    	return new ModelMapper().mapItemToSummary(item); }).getContent();
	     
	    return new PagedResponse<>(itemSummaries, items.getNumber(), 
	    		items.getSize(), items.getTotalElements(), 
	    		items.getTotalPages(), items.isLast());
	}
}
