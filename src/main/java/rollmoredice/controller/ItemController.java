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
import rollmoredice.entities.Item;
import rollmoredice.exception.ResourceNotFoundException;
import rollmoredice.payload.ApiResponse;
import rollmoredice.payload.ItemRequest;
import rollmoredice.payload.ItemSummary;
import rollmoredice.repositories.ItemRepository;
import rollmoredice.security.CurrentUser;
import rollmoredice.security.UserPrincipal;
import rollmoredice.service.ItemService;
import rollmoredice.service.ModelMapper;


@RestController
@Api(tags = { "Item Api" })
@RequestMapping("/api")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
	
	
	@GetMapping("/item/{itemId}")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets a item given a item's Id number",
		authorizations = {@Authorization(value = "Bearer Token")})
	public ItemSummary getItem(@PathVariable("itemId") long itemId) {
		
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item", "itemId", itemId));
		
		return new ModelMapper().mapItemToSummary(item);
	}
	
	@PostMapping("/item")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "POST", 
		value = "Creates a item to be associated with the user creating it as the creator",
		authorizations = {@Authorization(value = "Bearer Token")})
    public ResponseEntity<?> createItem(@Valid @RequestBody ItemRequest itemRequest,
    		@CurrentUser UserPrincipal currentUser) {
		
        Item item = itemService.createItem(itemRequest, currentUser.getId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{itemId}")
                .buildAndExpand(item.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Item Created Successfully"));
    }
	
	@PutMapping(value="/item/{itemId}")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "PUT", 
		value = "Updates a existing Item",
		notes = "Updates item of given itemId with given information",
		authorizations = {@Authorization(value = "Bearer Token")})
    public ResponseEntity<?> updateItem(@PathVariable("itemId") long itemId,
                                           @Valid @RequestBody ItemSummary summary) {
        
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item", "itemId", itemId));
		
		if (item.getId() == summary.getId()) {
			Item updatedItem = itemRepository.save(new ModelMapper().mapSummaryToItem(item, summary));
			return ResponseEntity.ok().body(new ModelMapper().mapItemToSummary(updatedItem));
		}
		
		return (ResponseEntity<?>) ResponseEntity.badRequest();
    }
	
	@DeleteMapping(value="/item/{itemId}")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "DELETE", 
		value = "Deletes the item of with the given itemId",
		authorizations = {@Authorization(value = "Bearer Token")})
    public ResponseEntity<?> deleteItem(@PathVariable("itemId") long itemId) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    itemRepository.deleteById(itemId);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
