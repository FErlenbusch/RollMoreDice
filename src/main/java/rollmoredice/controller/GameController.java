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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import rollmoredice.entities.CharacterSheet;
import rollmoredice.entities.Game;
import rollmoredice.entities.Item;
import rollmoredice.entities.Spell;
import rollmoredice.entities.User;
import rollmoredice.exception.ResourceNotFoundException;
import rollmoredice.payload.ApiResponse;
import rollmoredice.payload.GameRequest;
import rollmoredice.payload.GameSummary;
import rollmoredice.payload.ItemSummary;
import rollmoredice.payload.PagedResponse;
import rollmoredice.payload.SpellSummary;
import rollmoredice.repositories.CharacterSheetRepository;
import rollmoredice.repositories.GameRepository;
import rollmoredice.repositories.ItemRepository;
import rollmoredice.repositories.SpellRepository;
import rollmoredice.repositories.UserRepository;
import rollmoredice.security.CurrentUser;
import rollmoredice.security.UserPrincipal;
import rollmoredice.service.GameService;
import rollmoredice.service.ItemService;
import rollmoredice.service.ModelMapper;
import rollmoredice.service.SpellService;
import rollmoredice.util.AppConstants;


@RestController
@Api(tags = { "Game Api" })
@RequestMapping("/api")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private SpellService spellService;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CharacterSheetRepository characterRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private SpellRepository spellRepository;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(GameController.class);
	
	
	@GetMapping("/game/{gameId}")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets a game given a game's Id number",
		authorizations = {@Authorization(value = "Bearer Token")})
	public GameSummary getGame(@PathVariable("gameId") long gameId) {
		
		Game game = gameRepository.findById(gameId)
				.orElseThrow(() -> new ResourceNotFoundException("Game", "gameId", gameId));
		
		return new ModelMapper().mapGameToSummary(game);
	}
	
	@GetMapping("/game/items/{gameId}")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets all items associated with a game given the game's Id number",
		authorizations = {@Authorization(value = "Bearer Token")})
	public PagedResponse<ItemSummary> getItemsInGame(@PathVariable("gameId") long gameId,
    		@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		
		return itemService.getItemsInAGame(gameId, page, size);
	}
	
	@GetMapping("/game/spells/{gameId}")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "GET", 
		value = "Gets all spells associated with a game given the game's Id number",
		authorizations = {@Authorization(value = "Bearer Token")})
	public PagedResponse<SpellSummary> getSpellsInGame(@PathVariable("gameId") long gameId,
    		@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		
		return spellService.getSpellsInAGame(gameId, page, size);
	}
	
	@PostMapping("/game")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "POST", 
		value = "Creates a game to be associated with the user creating it as the Dungeon Master",
		authorizations = {@Authorization(value = "Bearer Token")})
    public ResponseEntity<?> createGame(@Valid @RequestBody GameRequest gameRequest,
    		@CurrentUser UserPrincipal currentUser) {
		
        Game game = gameService.createGame(gameRequest, currentUser.getId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{gameId}")
                .buildAndExpand(game.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Game Created Successfully"));
    }
	
	@PutMapping("/game/addplayer/{userId}")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "PUT", 
		value = "Adds a user as a to a game",
		notes = "Adds the user associated with the given userId as a player to the given game",
		authorizations = {@Authorization(value = "Bearer Token")})
	public ResponseEntity<?> addUserToGame(@PathVariable("userId") long userId, 
			@Valid @RequestBody GameSummary inGame) {
		
		Game game = gameRepository.findById(inGame.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Game", "gameId", inGame.getId()));
		
		User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		
		game.getPlayers().add(user);
		
		gameRepository.save(game);
		
		return ResponseEntity.ok().body(new ModelMapper().mapGameToSummary(game));
	}
	
	@PutMapping("/game/addcharacter/{characterId}")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "PUT", 
		value = "Adds a character as a player character to a game",
		notes = "Adds the character associated with the given characterId to the given game as a "
				+ "player character",
		authorizations = {@Authorization(value = "Bearer Token")})
	public ResponseEntity<?> addCharacterToGame(@PathVariable("characterId") long characterId, 
			@Valid @RequestBody GameSummary inGame) {
		
		Game game = gameRepository.findById(inGame.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Game", "gameId", inGame.getId()));
		
		CharacterSheet characterSheet = characterRepository.findById(characterId).orElseThrow(
				() -> new ResourceNotFoundException("Character", "characterId", characterId));
		
		game.getPlayerCharacters().add(characterSheet);
		
		gameRepository.save(game);
		
		return ResponseEntity.ok().body(new ModelMapper().mapGameToSummary(game));
	}
	
	@PutMapping("/game/addnpc/{characterId}")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "PUT", 
		value = "Adds a character as a NPC to a game",
		notes = "Adds the character associated with the given characterId to the given game as a NPC",
		authorizations = {@Authorization(value = "Bearer Token")})
	public ResponseEntity<?> addNPCToGame(@PathVariable("characterId") long characterId, 
			@Valid @RequestBody GameSummary inGame) {
		
		Game game = gameRepository.findById(inGame.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Game", "gameId", inGame.getId()));
		
		CharacterSheet characterSheet = characterRepository.findById(characterId).orElseThrow(
				() -> new ResourceNotFoundException("Character", "characterId", characterId));
		
		game.getNonPlayerCharacters().add(characterSheet);
		
		gameRepository.save(game);
		
		return ResponseEntity.ok().body(new ModelMapper().mapGameToSummary(game));
	}
	
	@PutMapping("/game/additem/{itemId}")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "PUT", 
		value = "Adds a item to a game",
		notes = "Adds the item associated with the given itemId to the given game",
		authorizations = {@Authorization(value = "Bearer Token")})
	public ResponseEntity<?> addItemToGame(@PathVariable("itemId") long itemId, 
			@Valid @RequestBody GameSummary inGame) {
		
		Game game = gameRepository.findById(inGame.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Game", "gameId", inGame.getId()));
		
		Item item = itemRepository.findById(itemId).orElseThrow(
				() -> new ResourceNotFoundException("Item", "itemId", itemId));
		
		game.getItems().add(item);
		
		gameRepository.save(game);
		
		return ResponseEntity.ok().body(new ModelMapper().mapGameToSummary(game));
	}
	
	@PutMapping("/game/addspell/{spellId}")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "PUT", 
		value = "Adds a spell to a game",
		notes = "Adds the spell associated with the given spellId to the given game",
		authorizations = {@Authorization(value = "Bearer Token")})
	public ResponseEntity<?> addSpellToGame(@PathVariable("spellId") long spellId, 
			@Valid @RequestBody GameSummary inGame) {
		
		Game game = gameRepository.findById(inGame.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Game", "gameId", inGame.getId()));
		
		Spell spell = spellRepository.findById(spellId).orElseThrow(
				() -> new ResourceNotFoundException("Spell", "spellId", spellId));
		
		game.getSpells().add(spell);
		
		gameRepository.save(game);
		
		return ResponseEntity.ok().body(new ModelMapper().mapGameToSummary(game));
	}
	
	@PutMapping(value="/game/{gameId}")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "PUT", 
		value = "Updates a existing game",
		notes = "Updates game of given gameId with given information",
		authorizations = {@Authorization(value = "Bearer Token")})
    public ResponseEntity<?> updateGame(@PathVariable("gameId") long gameId,
                                           @Valid @RequestBody GameSummary newGame) {
        
		Game oldGame = gameRepository.findById(gameId)
				.orElseThrow(() -> new ResourceNotFoundException("Game", "gameId", gameId));
		
		if (oldGame.getId() == newGame.getId()) {
			Game updatedGame = gameRepository.save(new ModelMapper().mapSummaryToGame(oldGame, newGame));
			return ResponseEntity.ok().body(new ModelMapper().mapGameToSummary(updatedGame));
		}
		
		return (ResponseEntity<?>) ResponseEntity.badRequest();
    }

    @DeleteMapping(value="/game/{gameId}")
    @PreAuthorize("hasRole('USER')")
	@ApiOperation(httpMethod = "DELETE", 
		value = "Deletes the game of with the given gameId",
		authorizations = {@Authorization(value = "Bearer Token")})
    public ResponseEntity<?> deleteGame(@PathVariable("gameId") long gameId) {
        return gameRepository.findById(gameId)
                .map(game -> {
                    gameRepository.deleteById(gameId);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
