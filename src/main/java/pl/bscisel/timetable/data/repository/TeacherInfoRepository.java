package pl.bscisel.timetable.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.bscisel.timetable.data.entity.TeacherInfo;

import java.util.List;
import java.util.Optional;

public interface TeacherInfoRepository extends JpaRepository<TeacherInfo, Long> {

    Optional<TeacherInfo> findByAccountId(Long accountId);

    @Query("SELECT t FROM TeacherInfo t WHERE LOWER(CONCAT(t.degree, ' ', t.name, ' ', t.surname)) LIKE CONCAT('%', LOWER(:filter), '%')")
    List<TeacherInfo> findByDegreeAndNameAndSurnameConcatenatedContaining(@Param("filter") String filter);

    boolean existsByAccountId(Long id);

    boolean existsByAccountIdAndIdNot(Long id, Long excludeTeacherId);
}
