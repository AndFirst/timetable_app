package pl.bscisel.timetable.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.bscisel.timetable.data.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Find account by email address ignoring case.
     * @param emailAddress email address
     * @return account
     */
    Optional<Account> findByEmailAddressIgnoreCase(String emailAddress);

    /**
     * Check if account with given email address exists. Ignore case.
     * @param emailAddress email address
     * @return true if exists, false otherwise
     */
    boolean existsByEmailAddressIgnoreCase(String emailAddress);

    /**
     * Check if account with given email address exists excluding account with given id. Ignore case.
     * @param emailAddress email address
     * @param id id of account to exclude
     * @return true if exists, false otherwise
     */
    boolean existsByEmailAddressIgnoreCaseAndIdNot(String emailAddress, Long id);

    /**
     * Find accounts by email address containing given filter. Ignore case.
     * @param filter filter
     * @return list of accounts
     */
    List<Account> findByEmailAddressContainsIgnoreCase(String filter);
}
