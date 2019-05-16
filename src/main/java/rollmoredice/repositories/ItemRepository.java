package rollmoredice.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rollmoredice.entities.Item;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	Optional<Item> findById(long itemId);
	
	@Query("SELECT i FROM Item i WHERE i.creator.id = :userId")
	Page<Item> findByCreatorUserId(@Param("userId") long userId, Pageable pageable);
	
	@Query("SELECT i FROM Item i, Game g WHERE g.id = :gameId AND i IN g.items")
	Page<Item> findItemsInGame(@Param("gameId") long gameId, Pageable pageable);
	
	@Query("SELECT i FROM Item i, CharacterSheet c WHERE c.id = :charId AND i IN c.items")
	Page<Item> findCharacterItems(@Param("charId") long charId, Pageable pageable);
}
