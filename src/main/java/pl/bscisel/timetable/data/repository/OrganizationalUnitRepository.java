package pl.bscisel.timetable.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;

import java.util.List;

public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnit, Long> {

    @Query(value = "SELECT ou FROM OrganizationalUnit ou WHERE ou.isTopLevel IS NOT NULL AND ou.isTopLevel = 1")
    List<OrganizationalUnit> findTopLevelOrganizationalUnits();

    List<OrganizationalUnit> findByParentUnitId(Long id);

}
