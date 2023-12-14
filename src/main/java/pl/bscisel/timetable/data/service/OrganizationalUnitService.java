package pl.bscisel.timetable.data.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.repository.OrganizationalUnitRepository;

import java.util.List;

@Service
public class OrganizationalUnitService {
    private final OrganizationalUnitRepository orgUnitRepo;

    public OrganizationalUnitService(OrganizationalUnitRepository organizationalUnitRepository) {
        this.orgUnitRepo = organizationalUnitRepository;
    }

    public List<OrganizationalUnit> findTopLevelUnits() {
        return orgUnitRepo.findByParentUnitNull();
    }

    public int countTopLevelUnits() {
        return findTopLevelUnits().size();
    }

    public List<OrganizationalUnit> findChildrenByUnitId(Long id) {
        return orgUnitRepo.findByParentUnitId(id);
    }

    public int countChildrenByUnitId(Long id) {
        return findChildrenByUnitId(id).size();
    }

    public boolean hasChildrenByUnitId(Long id) {
        return !findChildrenByUnitId(id).isEmpty();
    }

    public List<OrganizationalUnit> findAll() {
        return orgUnitRepo.findAll();
    }

    public void saveOrganizationalUnit(OrganizationalUnit organizationalUnit) {
        orgUnitRepo.save(organizationalUnit);
    }

    public void deleteOrganizationalUnit(OrganizationalUnit organizationalUnit) {
        orgUnitRepo.delete(organizationalUnit);
    }

    public String getNameWithId(OrganizationalUnit organizationalUnit) {
        return organizationalUnit.getId() + ". " + organizationalUnit.getName();
    }

    public boolean organizationalUnitExistsByNameAndParentUnitId(String name, @Nullable Long parentUnitId, @Nullable Long excludeId) {
        if (parentUnitId == null && excludeId == null) {
            return orgUnitRepo.findByParentUnitNull().stream().anyMatch(unit -> unit.getName().equalsIgnoreCase(name.strip()));
        } else if (parentUnitId == null) {
            return orgUnitRepo.findByParentUnitNull().stream().anyMatch(unit -> unit.getName().equalsIgnoreCase(name.strip()) && !unit.getId().equals(excludeId));
        } else if (excludeId == null) {
            return orgUnitRepo.findByParentUnitId(parentUnitId).stream().anyMatch(unit -> unit.getName().equalsIgnoreCase(name.strip()));
        } else {
            return orgUnitRepo.findByParentUnitId(parentUnitId).stream().anyMatch(unit -> unit.getName().equalsIgnoreCase(name.strip()) && !unit.getId().equals(excludeId));
        }
    }
}
