package pl.bscisel.timetable.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.bscisel.timetable.data.entity.Class;

import java.util.List;

public interface ClassRepository extends JpaRepository<Class, Long> {

    List<Class> findAllByClassGroupId(Long classGroupId);

    @Query("SELECT c FROM Class c JOIN c.teachers t WHERE t.id = :teacherId")
    List<Class> findAllByTeacherId(@Param("teacherId") Long teacherId);

}
