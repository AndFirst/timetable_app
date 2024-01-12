package pl.bscisel.timetable.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.repository.ClassGroupRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing class groups.
 */
@Service
public class ClassGroupService {
    private final ClassGroupRepository classGroupRepo;

    /**
     * Constructs an instance of ClassGroupService.
     */
    public ClassGroupService(ClassGroupRepository classGroupRepository) {
        this.classGroupRepo = classGroupRepository;
    }

    /**
     * Retrieves a list of class groups based on the organizational unit id, ordered by name.
     *
     * @param organizationalUnitId The id of the organizational unit.
     * @return A list of class groups for the specified organizational unit, ordered by name.
     */
    public List<ClassGroup> findByOrganizationalUnitId(@NotNull Long organizationalUnitId) {
        return classGroupRepo.findByOrganizationalUnitIdOrderByName(organizationalUnitId);
    }

    /**
     * Saves the provided class group.
     *
     * @param classGroup The class group to be saved.
     */
    public void save(@NotNull ClassGroup classGroup) {
        classGroupRepo.save(classGroup);
    }

    /**
     * Deletes the provided class group.
     *
     * @param classGroup The class group to be deleted.
     */
    public void delete(@NotNull ClassGroup classGroup) {
        classGroupRepo.delete(classGroup);
    }

    /**
     * Checks if a class group with the given name and organizational unit id exists, excluding the specified class group id if provided.
     *
     * @param name                 The name of the class group to check for existence.
     * @param organizationalUnitId The id of the organizational unit to which the class group belongs.
     * @param excludeId            The id of the class group to be excluded from the check, or null if not applicable.
     * @return true if a class group with the given name and organizational unit id exists, false otherwise.
     */
    public boolean existsByNameAndOrganizationalUnitId(@NotNull String name, @NotNull Long organizationalUnitId, @Nullable Long excludeId) {
        if (excludeId == null) {
            return findByOrganizationalUnitId(organizationalUnitId).stream().anyMatch(group -> group.getName().equalsIgnoreCase(name.strip()));
        } else {
            return findByOrganizationalUnitId(organizationalUnitId).stream().anyMatch(group -> group.getName().equalsIgnoreCase(name.strip()) && !group.getId().equals(excludeId));
        }
    }

    /**
     * Retrieves a list of all class groups.
     *
     * @return A list of all class groups.
     */
    public List<ClassGroup> findAll() {
        return classGroupRepo.findAll();
    }

    /**
     * Retrieves an optional class group by its id.
     *
     * @param classGroupId The id of the class group to retrieve.
     * @return An optional containing the class group if found, or empty otherwise.
     */
    public Optional<ClassGroup> findById(@NotNull Long classGroupId) {
        return classGroupRepo.findById(classGroupId);
    }
}
