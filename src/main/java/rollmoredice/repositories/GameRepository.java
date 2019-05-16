package rollmoredice.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rollmoredice.entities.Game;


@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
	
	Optional<Game> findById(long id);
	
	@Query("SELECT g FROM Game g WHERE g.dungeonMaster.id = :userId")
	Page<Game> findByDMUserId(@Param("userId") long userId, Pageable pageable);
	
	@Query("SELECT g FROM Game g WHERE ?1 member of g.playerCharacters")
	Page<Game> findPlayerGames(@Param("userId") long userId, Pageable pageable);
}
