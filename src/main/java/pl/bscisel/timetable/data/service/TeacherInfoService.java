package pl.bscisel.timetable.data.service;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.repository.TeacherInfoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherInfoService {
    private final TeacherInfoRepository teacherRepository;

    public TeacherInfoService(TeacherInfoRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<TeacherInfo> findAll() {
        return teacherRepository.findAll();
    }

    public Optional<TeacherInfo> findByUserId(@NotNull Long userId) {
        return teacherRepository.findByUserId(userId);
    }

    public Optional<TeacherInfo> findById(@NotNull Long teacherId) {
        return teacherRepository.findById(teacherId);
    }

    public void save(@NotNull TeacherInfo teacher) {
        teacherRepository.save(teacher);
    }

    public void delete(@NotNull TeacherInfo teacher) {
        teacherRepository.delete(teacher);
    }

    public List<TeacherInfo> search(@Nullable String filter) {
        filter = StringUtils.stripToNull(filter);
        if (filter == null) {
            return teacherRepository.findAll();
        }
        return teacherRepository.findByDegreeAndNameAndSurnameConcatenatedContaining(filter);
    }

    public boolean existsByUserId(@NotNull Long id, @Nullable Long excludeTeacherId) {
        if (excludeTeacherId == null)
            return teacherRepository.existsByUserId(id);
        else
            return teacherRepository.existsByUserIdAndIdNot(id, excludeTeacherId);
    }
}
