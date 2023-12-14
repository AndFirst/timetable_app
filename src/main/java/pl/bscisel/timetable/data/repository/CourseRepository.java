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

}
