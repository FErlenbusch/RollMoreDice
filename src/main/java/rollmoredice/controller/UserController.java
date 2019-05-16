package rollmoredice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import rollmoredice.entities.User;
import rollmoredice.exception.ResourceNotFoundException;
import rollmoredice.payload.CharacterSheetSummary;
import rollmoredice.payload.GameSummary;
import rollmoredice.payload.ItemSummary;
import rollmoredice.payload.PagedResponse;
import rollmoredice.payload.SpellSummary;
import rollmoredice.payload.UserSummary;
import rollmoredice.repositories.UserRepository;
import rollmoredice.security.CurrentUser;
import rollmoredice.security.UserPrincipal;
import rollmoredice.service.CharacterSheetService;
import rollmoredice.service.GameService;
import rollmoredice.service.ItemService;
import rollmoredice.service.SpellService;
import rollmoredice.util.AppConstants;


@RestController
@Api(tags = { "User Api" })
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CharacterSheetService characterService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired SpellService spellService;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	@GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets the user info of the signed in user",
		authorizations = {@Authorization(value = "Bearer Token")})
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername(), 
        		currentUser.getEmail());
    }
    
    @GetMapping("/users/{username}")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets a user's information given a user's username",
		authorizations = {@Authorization(value = "Bearer Token")})
    public UserSummary getUserSummary(@PathVariable(value = "username") String username) {
    	User user = userRepository.findByUsername(username).orElseThrow(() -> 
    				new ResourceNotFoundException("User", "username", username));
    	
    	return new UserSummary(user.getID(), user.getUsername(), user.getEmail());
    }
    
    @GetMapping("/user/mycharacters")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets a all characters created by the signed in user",
		authorizations = {@Authorization(value = "Bearer Token")})
    public PagedResponse<CharacterSheetSummary> getMyCharacters(
    		@CurrentUser UserPrincipal currentUser,
    		@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    	
    	return characterService.getAllUsersCharacters(currentUser, page, size);
    }
    
    @GetMapping("/user/mygames")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets a all games created by the signed in user",
		authorizations = {@Authorization(value = "Bearer Token")})
    public PagedResponse<GameSummary> getMyGames(
    		@CurrentUser UserPrincipal currentUser,
    		@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    	
    	return gameService.getAllUsersGames(currentUser, page, size);
    }
    
    @GetMapping("/user/gamesin")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets a all games the signed in user is a player in",
		authorizations = {@Authorization(value = "Bearer Token")})
    public PagedResponse<GameSummary> getGamesIn(
    		@CurrentUser UserPrincipal currentUser,
    		@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    	
    	return gameService.getAllGamesUserIsIn(currentUser, page, size);
    }
    
    @GetMapping("/user/myitems")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets a all items created by the signed in user",
		authorizations = {@Authorization(value = "Bearer Token")})
    public PagedResponse<ItemSummary> getMyItems(
    		@CurrentUser UserPrincipal currentUser,
    		@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    	
    	return itemService.getAllUsersItems(currentUser, page, size);
    }
    
    @GetMapping("/user/myspells")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets a all spells created by the signed in user",
		authorizations = {@Authorization(value = "Bearer Token")})
    public PagedResponse<SpellSummary> getMySpells(
    		@CurrentUser UserPrincipal currentUser,
    		@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    	
    	return spellService.getAllUsersSpells(currentUser, page, size);
    }
    
    public User getUserById(long id) {
    	return userRepository.findById(id).orElseThrow(() -> 
			new ResourceNotFoundException("User", "username", id));
    }
}
