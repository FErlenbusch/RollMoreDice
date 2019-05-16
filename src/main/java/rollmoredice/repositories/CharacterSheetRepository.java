package rollmoredice.repositories;

import rollmoredice.entities.CharacterSheet;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface CharacterSheetRepository extends JpaRepository<CharacterSheet, Long> {
	
	Optional<CharacterSheet> findById(long characterSheetId);
	
	Page<CharacterSheet> findByCreatedBy(long userId, Pageable pageable);
	
	@Query("SELECT c FROM CharacterSheet c WHERE c.player.id = :userId")
	Optional<CharacterSheet> findByUserId(@Param("userId") long userId);
	
	@Query("SELECT count(c) > 0 From CharacterSheet c Where c.player.id = :userId AND c.charName = :charName")
	Boolean existsByUserIdAndCharName(@Param("userId") long userId,
			@Param("charName") String charName);
}
