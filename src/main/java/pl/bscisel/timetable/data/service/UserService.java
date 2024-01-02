package pl.bscisel.timetable.data.service;

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

    public boolean userExistsByEmailAddress(String email, Long excludeId) {
        if (excludeId == null)
            return userRepository.existsByEmailAddressIgnoreCase(email);
        else
            return userRepository.existsByEmailAddressIgnoreCaseAndIdNot(email, excludeId);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public List<User> search(String filter) {
        if (filter == null || filter.isEmpty()) {
            return userRepository.findAll();
        }
        return userRepository.findByEmailAddressContainsIgnoreCase(filter);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}
