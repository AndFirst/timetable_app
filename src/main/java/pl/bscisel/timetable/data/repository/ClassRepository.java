package pl.bscisel.timetable.data.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import pl.bscisel.timetable.data.entity.Class;

public interface ClassRepository extends JpaRepository<Class, Long> {

}
