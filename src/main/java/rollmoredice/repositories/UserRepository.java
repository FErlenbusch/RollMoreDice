package rollmoredice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rollmoredice.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByUsernameIgnoreCase(String username);
	
	Optional<User> findUserByEmailIgnoreCase(String email);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByUsernameOrEmail(String username, String email);
	
	Optional<User> findById(long id);
	
	List<User> findByIdIn(List<Long> userIds);
	
	Boolean existsByUsername(String username);
	
    Boolean existsByEmail(String email);
}