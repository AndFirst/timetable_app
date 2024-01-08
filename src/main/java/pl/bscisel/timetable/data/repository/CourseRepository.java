package pl.bscisel.timetable.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.bscisel.timetable.data.entity.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Find courses by code or name ignoring case.
     * @param code course code
     * @param name course name
     * @return list of courses
     */
    List<Course> findByCodeContainsIgnoreCaseOrNameContainsIgnoreCase(String code, String name);

    /**
     * Check if course with given code exists excluding course with given id. Ignore case.
     * @param code course code
     * @param excludeId id of course to exclude
     * @return true if exists, false otherwise
     */
    boolean existsByCodeIgnoreCaseAndIdNot(String code, Long excludeId);

    /**
     * Check if course with given code exists. Ignore case.
     * @param code course code
     * @return true if exists, false otherwise
     */
    boolean existsByCodeIgnoreCase(String code);
}
