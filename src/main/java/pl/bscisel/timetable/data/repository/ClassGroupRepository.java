package pl.bscisel.timetable.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.bscisel.timetable.data.entity.ClassGroup;

import java.util.List;

public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long> {

    List<ClassGroup> findClassGroupsByOrganizationalUnitId(Long organizationalUnitId);

    @Query("SELECT COUNT(cg) > 0 FROM ClassGroup cg WHERE cg.organizationalUnit.id = :id")
    boolean hasClassGroupsByOrganizationalUnitId(@Param("id") Long organizationalUnitId);

}
