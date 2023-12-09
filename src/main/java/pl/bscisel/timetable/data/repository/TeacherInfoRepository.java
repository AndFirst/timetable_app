package pl.bscisel.timetable.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.bscisel.timetable.data.entity.TeacherInfo;

public interface TeacherInfoRepository extends JpaRepository<TeacherInfo, Long> {

}
