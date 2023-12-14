package pl.bscisel.timetable.data.repository;


import jakarta.persistence.QueryHint;
import org.hibernate.jpa.HibernateHints;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;

import java.util.List;

public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnit, Long> {

    @QueryHints(value = {@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true")})
    List<OrganizationalUnit> findByParentUnitNull();

    @QueryHints(value = {@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true")})
    List<OrganizationalUnit> findByParentUnitId(Long id);

    @QueryHints(value = {@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true")})
    @Override
    @NotNull
    List<OrganizationalUnit> findAll();
}
