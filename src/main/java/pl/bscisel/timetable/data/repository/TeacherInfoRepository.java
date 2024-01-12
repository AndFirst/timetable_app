package pl.bscisel.timetable.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.bscisel.timetable.data.entity.TeacherInfo;

import java.util.List;
import java.util.Optional;

public interface TeacherInfoRepository extends JpaRepository<TeacherInfo, Long> {

    /**
     * Find teacher by account id.
     *
     * @param accountId account id
     * @return teacher info
     */
    Optional<TeacherInfo> findByAccountId(Long accountId);

    /**
     * Find teachers by filter. Filter is applied to degree, name and surname concatenated.
     *
     * @param filter filter
     * @return list of teachers
     */
    @Query("SELECT t FROM TeacherInfo t WHERE LOWER(CONCAT(t.degree, ' ', t.name, ' ', t.surname)) LIKE CONCAT('%', LOWER(:filter), '%')")
    List<TeacherInfo> findByDegreeAndNameAndSurnameConcatenatedContaining(@Param("filter") String filter);

    /**
     * Check if teacher with given account id exists.
     *
     * @param id account id
     * @return true if exists, false otherwise
     */
    boolean existsByAccountId(Long id);

    /**
     * Check if teacher with given account id exists and is not the one with given id.
     *
     * @param id               account id
     * @param excludeTeacherId teacher id to exclude
     * @return true if exists, false otherwise
     */
    boolean existsByAccountIdAndIdNot(Long id, Long excludeTeacherId);

    /**
     * Find all teachers ordered by surname.
     *
     * @return list of teachers
     */
    List<TeacherInfo> findAllByOrderBySurname();
}
