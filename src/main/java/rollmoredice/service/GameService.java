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

import rollmoredice.entities.Game;
import rollmoredice.entities.User;
import rollmoredice.exception.ResourceNotFoundException;
import rollmoredice.payload.GameRequest;
import rollmoredice.payload.GameSummary;
import rollmoredice.payload.PagedResponse;
import rollmoredice.repositories.GameRepository;
import rollmoredice.repositories.UserRepository;
import rollmoredice.security.UserPrincipal;


@Service
public class GameService {
	
	@Autowired 
	private GameRepository gameRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CharacterSheetService.class);
	
	
	public PagedResponse<GameSummary> getAllUsersGames(
			UserPrincipal currentUser, int page, int size) {
		
		new ModelMapper().validatePageNumberAndSize(page, size);
		
		User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", currentUser.getId()));
		
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
	    Page<Game> games = gameRepository.findByDMUserId(user.getID(), pageable);
	     
	    return response(games);
	}
	
	public PagedResponse<GameSummary> getAllGamesUserIsIn(
			UserPrincipal currentUser, int page, int size) {
		
		new ModelMapper().validatePageNumberAndSize(page, size);
		
		User user = userRepository.findById(currentUser.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", currentUser.getId()));
		
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
	    Page<Game> games = gameRepository.findPlayerGames(user.getID(), pageable);
	     
	    return response(games);
	}
	
	public Game createGame(GameRequest gameRequest, long userId) {
		
		User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		
		Game game = new ModelMapper().mapRequestToGame(gameRequest, user);

        return gameRepository.save(game);
    }
	
	private PagedResponse<GameSummary> response(Page<Game> games) {
		if (games.getNumberOfElements() == 0) {
	    	return new PagedResponse<>(Collections.emptyList(), games.getNumber(),
	    			games.getSize(), games.getTotalElements(), 
	    			games.getTotalPages(), games.isLast());
	    }
	     
	    List<GameSummary> gameSummaries = games.map(game -> {
	    	return new ModelMapper().mapGameToSummary(game); }).getContent();
	     
	    return new PagedResponse<>(gameSummaries, games.getNumber(), 
	    		games.getSize(), games.getTotalElements(), 
	    		games.getTotalPages(), games.isLast());
	}
}
