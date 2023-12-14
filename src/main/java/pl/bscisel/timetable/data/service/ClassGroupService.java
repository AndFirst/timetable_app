package pl.bscisel.timetable.data.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.repository.ClassGroupRepository;

import java.util.List;

@Service
public class ClassGroupService {
    private final ClassGroupRepository classGroupRepo;

    public ClassGroupService(ClassGroupRepository classGroupRepository) {
        this.classGroupRepo = classGroupRepository;
    }

    public List<ClassGroup> findClassGroupsByOrganizationalUnitId(Long organizationalUnitId) {
        return classGroupRepo.findByOrganizationalUnitId(organizationalUnitId);
    }

    public void saveClassGroup(ClassGroup classGroup) {
        classGroupRepo.save(classGroup);
    }

    public void deleteClassGroup(ClassGroup classGroup) {
        classGroupRepo.delete(classGroup);
    }

    public boolean classGroupExistsByNameAndOrganizationalUnitId(String name, @NotNull Long organizationalUnitId, @Nullable Long excludeId) {
        if (excludeId == null) {
            return findClassGroupsByOrganizationalUnitId(organizationalUnitId).stream().anyMatch(group -> group.getName().equalsIgnoreCase(name.strip()));
        } else {
            return findClassGroupsByOrganizationalUnitId(organizationalUnitId).stream().anyMatch(group -> group.getName().equalsIgnoreCase(name.strip()) && !group.getId().equals(excludeId));
        }
    }
}
