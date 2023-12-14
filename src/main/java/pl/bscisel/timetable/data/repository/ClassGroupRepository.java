package pl.bscisel.timetable.data.repository;


import jakarta.persistence.QueryHint;
import org.hibernate.jpa.HibernateHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import pl.bscisel.timetable.data.entity.ClassGroup;

import java.util.List;

public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long> {

    @QueryHints(value = {@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true")})
    List<ClassGroup> findByOrganizationalUnitId(Long organizationalUnitId);

}
