package pl.bscisel.timetable.data.repository;


import jakarta.persistence.QueryHint;
import org.hibernate.jpa.HibernateHints;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;

import java.util.List;

public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnit, Long> {

    /**
     * Find top level organizational units. Order by name.
     *
     * @return list of top level organizational units
     */
    @QueryHints(value = {@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true")})
    List<OrganizationalUnit> findByParentUnitNullOrderByName();

    /**
     * Find organizational units by parent unit id. Order by name.
     *
     * @param id parent unit id
     * @return list of organizational units
     */
    @QueryHints(value = {@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true")})
    List<OrganizationalUnit> findByParentUnitIdOrderByName(Long id);

    /**
     * Find all organizational units.
     *
     * @return list of organizational units
     */
    @QueryHints(value = {@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true")})
    @Override
    @NotNull
    List<OrganizationalUnit> findAll();
}
