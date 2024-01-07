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

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    public AccountService(AccountRepository accountRepository,
                          RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    public boolean existsByEmailAddress(@NotNull String email, @Nullable Long excludeId) {
        if (excludeId == null)
            return accountRepository.existsByEmailAddressIgnoreCase(email);
        else
            return accountRepository.existsByEmailAddressIgnoreCaseAndIdNot(email, excludeId);
    }

    public void save(@NotNull Account account) {
        accountRepository.save(account);
    }

    public void delete(@NotNull Account account) {
        accountRepository.delete(account);
    }

    public List<Account> search(@Nullable String filter) {
        filter = StringUtils.trimToNull(filter);
        if (filter == null) {
            return accountRepository.findAll();
        }
        return accountRepository.findByEmailAddressContainsIgnoreCase(filter);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }
}
