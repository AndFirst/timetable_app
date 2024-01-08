package pl.bscisel.timetable.service;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.repository.CourseRepository;

import java.util.List;

/**
 * Service class for managing courses.
 */
@Service
public class CourseService {
    private final CourseRepository courseRepository;

    /**
     * Constructs an instance of CourseService.
     */
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Saves the provided course.
     * @param course The course to be saved.
     */
    public void save(@NotNull Course course) {
        courseRepository.save(course);
    }

    /**
     * Deletes the provided course.
     * @param course The course to be deleted.
     */
    public void delete(@NotNull Course course) {
        courseRepository.delete(course);
    }

    /**
     * Searches for courses based on the provided filter.
     *
     * @param filter The filter to apply to the search. If null or empty, returns all courses.
     * @return A list of courses that match the search criteria.
     */
    public List<Course> search(@Nullable String filter) {
        filter = StringUtils.stripToNull(filter);
        if (filter == null) {
            return courseRepository.findAll();
        }
        return courseRepository.findByCodeContainsIgnoreCaseOrNameContainsIgnoreCase(filter, filter);
    }

    /**
     * Checks if a course with the given code exists, excluding the specified course id if provided.
     *
     * @param code The code of the course to check for existence.
     * @param excludeId The id of the course to be excluded from the check, or null if not applicable.
     * @return true if a course with the given code exists, false otherwise.
     */
    public boolean existsByCode(@Nullable String code, @Nullable Long excludeId) {
        code = StringUtils.stripToNull(code);
        if (code == null) {
            return false;
        }
        if (excludeId == null) {
            return courseRepository.existsByCodeIgnoreCase(code);
        } else {
            return courseRepository.existsByCodeIgnoreCaseAndIdNot(code, excludeId);
        }
    }

    /**
     * Retrieves a list of all courses.
     *
     * @return A list of all courses.
     */
    public List<Course> findAll() {
        return courseRepository.findAll();
    }
}
