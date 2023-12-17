package pl.bscisel.timetable.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.bscisel.timetable.data.entity.TeacherInfo;

import java.util.Optional;

public interface TeacherInfoRepository extends JpaRepository<TeacherInfo, Long> {

    Optional<TeacherInfo> findByUserId(Long userId);

}
