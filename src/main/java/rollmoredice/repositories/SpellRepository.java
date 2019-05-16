package rollmoredice.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rollmoredice.entities.Spell;


@Repository
public interface SpellRepository extends JpaRepository<Spell, Long> {
	
	Optional<Spell> findById(long spellId);
	
	@Query("SELECT s FROM Spell s WHERE s.creator.id = :userId")
	Page<Spell> findByCreatorUserId(@Param("userId") long userId, Pageable pageable);
	
	@Query("SELECT s FROM Spell s, Game g WHERE g.id = :gameId AND s IN g.spells")
	Page<Spell> findSpellsInGame(@Param("gameId") long gameId, Pageable pageable);
	
	@Query("SELECT s FROM Spell s, CharacterSheet c WHERE c.id = :charId AND s IN c.spells")
	Page<Spell> findCharacterSpells(@Param("charId") long charId, Pageable pageable);
}
