package pl.bscisel.timetable.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.bscisel.timetable.data.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAddress(String emailAddress);

    boolean existsByEmailAddressIgnoreCase(String emailAddress);

    boolean existsByEmailAddressIgnoreCaseAndIdNot(String emailAddress, Long id);

    List<User> findByEmailAddressContainsIgnoreCase(String filter);
}
