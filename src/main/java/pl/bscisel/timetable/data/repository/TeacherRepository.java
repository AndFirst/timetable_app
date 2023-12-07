package pl.bscisel.timetable.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.bscisel.timetable.data.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
