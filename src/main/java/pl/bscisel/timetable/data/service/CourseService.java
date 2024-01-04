package pl.bscisel.timetable.data.service;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.repository.CourseRepository;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    /**
     * Constructor.
     *
     * @param courseRepository course repository
     */
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Save course.
     *
     * @param course course to save
     */
    public void save(@NotNull Course course) {
        courseRepository.save(course);
    }

    /**
     * Delete course.
     *
     * @param course course to delete
     */
    public void delete(@NotNull Course course) {
        courseRepository.delete(course);
    }

    /**
     * Find courses for given filter. It searches by code and name.
     *
     * @param filter filter to search
     * @return list of courses that match the filter
     */
    public List<Course> search(@Nullable String filter) {
        filter = StringUtils.stripToNull(filter);
        if (filter == null) {
            return courseRepository.findAll();
        }
        return courseRepository.findByCodeContainsIgnoreCaseOrNameContainsIgnoreCase(filter, filter);
    }

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

    public List<Course> findAll() {
        return courseRepository.findAll();
    }
}
