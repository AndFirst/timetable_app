package pl.bscisel.timetable.service;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.repository.TeacherInfoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing teacher info.
 */
@Service
public class TeacherInfoService implements BasicEntityOperationsService<TeacherInfo> {
    private final TeacherInfoRepository teacherRepository;

    /**
     * Constructs an instance of TeacherInfoService.
     */
    public TeacherInfoService(TeacherInfoRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    /**
     * Searches for teachers based on the provided filter.
     *
     * @param filter The filter to apply to the search. If null or empty, returns all teachers.
     * @return A list of teachers that match the search criteria.
     */
    @Override
    public List<TeacherInfo> search(@Nullable String filter) {
        filter = StringUtils.stripToNull(filter);
        if (filter == null) {
            return teacherRepository.findAll();
        }
        return teacherRepository.findByDegreeAndNameAndSurnameConcatenatedContaining(filter);
    }

    /**
     * Saves the provided teacher.
     *
     * @param teacher The teacher to be saved.
     */
    @Override
    public void save(@NotNull TeacherInfo teacher) {
        teacherRepository.save(teacher);
    }

    /**
     * Deletes the provided teacher.
     *
     * @param teacher The teacher to be deleted.
     */
    @Override
    public void delete(@NotNull TeacherInfo teacher) {
        teacherRepository.delete(teacher);
    }

    @Override
    public TeacherInfo createEmpty() {
        return new TeacherInfo();
    }

    @Override
    public Class<TeacherInfo> getEntityClass() {
        return TeacherInfo.class;
    }

    /**
     * Retrieves a list of all teachers.
     *
     * @return A list of all teachers.
     */
    public List<TeacherInfo> findAll() {
        return teacherRepository.findAll();
    }

    /**
     * Finds a teacher by the account id.
     *
     * @param accountId The id of the account.
     * @return An optional containing the teacher if found, or empty otherwise.
     */
    public Optional<TeacherInfo> findByAccountId(@NotNull Long accountId) {
        return teacherRepository.findByAccountId(accountId);
    }

    /**
     * Finds a teacher by the id.
     *
     * @param teacherId The id of the teacher.
     * @return An optional containing the teacher if found, or empty otherwise.
     */
    public Optional<TeacherInfo> findById(@NotNull Long teacherId) {
        return teacherRepository.findById(teacherId);
    }

    /**
     * Checks if a teacher with the given account id exists, excluding the specified teacher id if provided.
     *
     * @param id               The id of the teacher to check for existence.
     * @param excludeTeacherId The id of the teacher to be excluded from the check, or null if not applicable.
     * @return true if a teacher with the given account id exists, false otherwise.
     */
    public boolean existsByAccountId(@NotNull Long id, @Nullable Long excludeTeacherId) {
        if (excludeTeacherId == null)
            return teacherRepository.existsByAccountId(id);
        else
            return teacherRepository.existsByAccountIdAndIdNot(id, excludeTeacherId);
    }
}
