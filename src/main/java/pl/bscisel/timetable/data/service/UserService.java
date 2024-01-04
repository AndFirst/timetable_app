package pl.bscisel.timetable.data.service;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.Role;
import pl.bscisel.timetable.data.entity.User;
import pl.bscisel.timetable.data.repository.RoleRepository;
import pl.bscisel.timetable.data.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public boolean userExistsByEmailAddress(@NotNull String email, @Nullable Long excludeId) {
        if (excludeId == null)
            return userRepository.existsByEmailAddressIgnoreCase(email);
        else
            return userRepository.existsByEmailAddressIgnoreCaseAndIdNot(email, excludeId);
    }

    public void save(@NotNull User user) {
        userRepository.save(user);
    }

    public void delete(@NotNull User user) {
        userRepository.delete(user);
    }

    public List<User> search(@Nullable String filter) {
        filter = StringUtils.trimToNull(filter);
        if (filter == null) {
            return userRepository.findAll();
        }
        return userRepository.findByEmailAddressContainsIgnoreCase(filter);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
