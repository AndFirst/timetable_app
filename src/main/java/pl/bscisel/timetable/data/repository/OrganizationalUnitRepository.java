package pl.bscisel.timetable.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;

import java.util.List;

public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnit, Long> {
    List<OrganizationalUnit> findByParentUnitIsNull();

    List<OrganizationalUnit> findOrganizationalUnitsByParentUnitId(Long id);

    @Query("SELECT COUNT(ou) > 0 FROM OrganizationalUnit ou WHERE ou.parentUnit.id = :id")
    boolean hasChildUnitsByOrganizationalUnitId(@Param("id") Long organizationalUnitId);

}
