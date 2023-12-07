package pl.bscisel.timetable.data.service;

import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.repository.ClassGroupRepository;
import pl.bscisel.timetable.data.repository.OrganizationalUnitRepository;

import java.util.List;

@Service
public class OrganizationalUnitService {
    private final OrganizationalUnitRepository unitRepository;
    private final ClassGroupRepository classGroupRepository;


    public OrganizationalUnitService(OrganizationalUnitRepository unitRepository, ClassGroupRepository classGroupRepository) {
        this.unitRepository = unitRepository;
        this.classGroupRepository = classGroupRepository;
    }

    public List<OrganizationalUnit> getTopLevelUnits() {
        return unitRepository.findByParentUnitIsNull();
    }

    public List<OrganizationalUnit> findOrganizationalUnitsByParentUnitId(Long id) {
        return unitRepository.findOrganizationalUnitsByParentUnitId(id);
    }

    public boolean hasChildUnitsByOrganizationalUnitId(Long id) {
        return unitRepository.hasChildUnitsByOrganizationalUnitId(id);
    }

    public List<ClassGroup> findClassGroupsByOrganizationalUnitId(Long id) {
        return classGroupRepository.findClassGroupsByOrganizationalUnitId(id);
    }

    public boolean hasClassGroupsByOrganizationalUnitId(Long id) {
        return classGroupRepository.hasClassGroupsByOrganizationalUnitId(id);
    }
}
