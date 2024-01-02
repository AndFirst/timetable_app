package pl.bscisel.timetable.data.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.repository.ClassGroupRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClassGroupService {
    private final ClassGroupRepository classGroupRepo;

    public ClassGroupService(ClassGroupRepository classGroupRepository) {
        this.classGroupRepo = classGroupRepository;
    }

    public List<ClassGroup> findClassGroupsByOrganizationalUnitId(Long organizationalUnitId) {
        return classGroupRepo.findByOrganizationalUnitIdOrderByName(organizationalUnitId);
    }

    public void saveClassGroup(ClassGroup classGroup) {
        classGroupRepo.save(classGroup);
    }

    public void deleteClassGroup(ClassGroup classGroup) {
        classGroupRepo.delete(classGroup);
    }

    public boolean classGroupExistsByNameAndOrganizationalUnitId(@NotNull String name, @NotNull Long organizationalUnitId, @Nullable Long excludeId) {
        if (excludeId == null) {
            return findClassGroupsByOrganizationalUnitId(organizationalUnitId).stream().anyMatch(group -> group.getName().equalsIgnoreCase(name.strip()));
        } else {
            return findClassGroupsByOrganizationalUnitId(organizationalUnitId).stream().anyMatch(group -> group.getName().equalsIgnoreCase(name.strip()) && !group.getId().equals(excludeId));
        }
    }

    public List<ClassGroup> findAll() {
        return classGroupRepo.findAll();
    }

    public Optional<ClassGroup> findById(Long classGroupId) {
        return classGroupRepo.findById(classGroupId);
    }
}
