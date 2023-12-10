package pl.bscisel.timetable.data.service;

import org.apache.logging.log4j.spi.LoggerContext;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.repository.ClassGroupRepository;
import pl.bscisel.timetable.data.repository.OrganizationalUnitRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
public class OrganizationalUnitService {
    private final OrganizationalUnitRepository orgUnitRepo;
    private final ClassGroupRepository classGroupRepo;

    public OrganizationalUnitService(OrganizationalUnitRepository organizationalUnitRepository, ClassGroupRepository classGroupRepository) {
        this.orgUnitRepo = organizationalUnitRepository;
        this.classGroupRepo = classGroupRepository;
    }

    public List<OrganizationalUnit> getTopLevelUnits() {
        return orgUnitRepo.findTopLevelOrganizationalUnits();
    }

    public List<OrganizationalUnit> findOrganizationalUnitsByParentUnitId(Long id) {
        return orgUnitRepo.findByParentUnitId(id);
    }

    public List<ClassGroup> findClassGroupsByOrganizationalUnitId(Long id) {
        return classGroupRepo.findByOrganizationalUnitId(id);
    }

}
