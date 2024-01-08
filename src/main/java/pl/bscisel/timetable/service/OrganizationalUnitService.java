package pl.bscisel.timetable.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.repository.OrganizationalUnitRepository;

import java.util.List;

/**
 * Service class for managing organizational units.
 */
@Service
public class OrganizationalUnitService {
    private final OrganizationalUnitRepository orgUnitRepo;

    /**
     * Constructs an instance of OrganizationalUnitService.
     */
    public OrganizationalUnitService(OrganizationalUnitRepository organizationalUnitRepository) {
        this.orgUnitRepo = organizationalUnitRepository;
    }

    /**
     * Retrieves a list of top-level organizational units.
     * @return A list of top-level organizational units.
     */
    public List<OrganizationalUnit> findTopLevelUnits() {
        return orgUnitRepo.findByParentUnitNull();
    }

    /**
     * Counts the number of top-level organizational units.
     *
     * @return The count of top-level organizational units.
     */
    public int countTopLevelUnits() {
        return findTopLevelUnits().size();
    }

    /**
     * Retrieves the children of an organizational unit based on its id.
     *
     * @param id The id of the parent organizational unit.
     * @return A list of children organizational units.
     */
    public List<OrganizationalUnit> findChildrenByUnitId(@NotNull Long id) {
        return orgUnitRepo.findByParentUnitId(id);
    }

    /**
     * Counts the number of children of an organizational unit based on its id.
     *
     * @param id The id of the parent organizational unit.
     * @return The count of children organizational units.
     */
    public int countChildrenByUnitId(@NotNull Long id) {
        return findChildrenByUnitId(id).size();
    }

    /**
     * Checks if an organizational unit has children based on its id.
     *
     * @param id The id of the parent organizational unit.
     * @return True if the organizational unit has children, false otherwise.
     */
    public boolean hasChildrenByUnitId(@NotNull Long id) {
        return !findChildrenByUnitId(id).isEmpty();
    }

    /**
     * Retrieves all organizational units.
     * @return A list of all organizational units.
     */
    public List<OrganizationalUnit> findAll() {
        return orgUnitRepo.findAll();
    }

    /**
     * Saves the provided organizational unit.
     * @param organizationalUnit The organizational unit to be saved.
     */
    public void save(OrganizationalUnit organizationalUnit) {
        orgUnitRepo.save(organizationalUnit);
    }

    /**
     * Deletes the provided organizational unit.
     * @param organizationalUnit The organizational unit to be deleted.
     */
    public void delete(OrganizationalUnit organizationalUnit) {
        orgUnitRepo.delete(organizationalUnit);
    }

    /**
     * Retrieves the name of an organizational unit with its id prepended.
     *
     * @param organizationalUnit The organizational unit.
     * @return The name of the organizational unit with its id prepended.
     */
    public String getNameWithId(@NotNull OrganizationalUnit organizationalUnit) {
        return organizationalUnit.getId() + ". " + organizationalUnit.getName();
    }

    /**
     * Checks if an organizational unit with the given name and optional parent id exists, excluding the specified id.
     *
     * @param name The name of the organizational unit.
     * @param parentUnitId The id of the parent organizational unit.
     * @param excludeId The id to be excluded from the check.
     * @return True if an organizational unit with the given name exists, false otherwise.
     */
    public boolean organizationalUnitExistsByNameAndParentUnitId(@NotNull String name, @Nullable Long parentUnitId, @Nullable Long excludeId) {
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
