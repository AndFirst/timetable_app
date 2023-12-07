package pl.bscisel.timetable.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.bscisel.timetable.data.entity.TeacherOrganizationalUnit;

public interface TeacherOrganizationUnitRepository extends JpaRepository<TeacherOrganizationalUnit, Long> {

}
