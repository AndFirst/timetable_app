package pl.bscisel.timetable.service;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.Account;
import pl.bscisel.timetable.data.entity.Role;
import pl.bscisel.timetable.data.repository.AccountRepository;
import pl.bscisel.timetable.data.repository.RoleRepository;

import java.util.List;

/**
 * Service class for managing user accounts and roles.
 */
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    /**
     * Constructs an instance of AccountService.
     */
    public AccountService(AccountRepository accountRepository,
                          RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Checks if an account with the given email address exists, excluding the specified account id if provided.
     *
     * @param email The email address to check for existence.
     * @param excludeId The id of the account to be excluded from the check, or null if not applicable.
     * @return true if an account with the given email address exists, false otherwise.
     */
    public boolean existsByEmailAddress(@NotNull String email, @Nullable Long excludeId) {
        if (excludeId == null)
            return accountRepository.existsByEmailAddressIgnoreCase(email);
        else
            return accountRepository.existsByEmailAddressIgnoreCaseAndIdNot(email, excludeId);
    }

    /**
     * Saves the provided account.
     *
     * @param account The account to be saved.
     */
    public void save(@NotNull Account account) {
        accountRepository.save(account);
    }

    /**
     * Deletes the provided account.
     *
     * @param account The account to be deleted.
     */
    public void delete(@NotNull Account account) {
        accountRepository.delete(account);
    }

    /**
     * Searches for accounts based on the provided filter.
     *
     * @param filter The filter to apply to the search. If null or empty, returns all accounts.
     * @return A list of accounts that match the search criteria.
     */
    public List<Account> search(@Nullable String filter) {
        filter = StringUtils.trimToNull(filter);
        if (filter == null) {
            return accountRepository.findAll();
        }
        return accountRepository.findByEmailAddressContainsIgnoreCase(filter);
    }

    /**
     * Retrieves a list of all roles.
     *
     * @return A list of all roles.
     */
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    /**
     * Retrieves a list of all accounts.
     *
     * @return A list of all accounts.
     */
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }
}
