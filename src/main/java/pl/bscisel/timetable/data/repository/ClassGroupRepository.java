package pl.bscisel.timetable.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.bscisel.timetable.data.entity.ClassGroup;

import java.util.List;

public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long> {

    List<ClassGroup> findByOrganizationalUnitId(Long organizationalUnitId);

}
