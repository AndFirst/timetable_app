package pl.bscisel.timetable.data.service;

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

    public List<TeacherInfo> findAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<TeacherInfo> findTeacherByUserId(Long userId) {
        return teacherRepository.findByUserId(userId);
    }

    public Optional<TeacherInfo> findTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId);
    }

    public void save(TeacherInfo teacher) {
        teacherRepository.save(teacher);
    }

    public void delete(TeacherInfo teacher) {
        teacherRepository.delete(teacher);
    }

    public List<TeacherInfo> search(String filter) {
        if (filter == null || filter.isEmpty()) {
            return teacherRepository.findAll();
        }
        return teacherRepository.findByDegreeAndNameAndSurnameConcatenatedContaining(filter);
    }

    public boolean teacherInfoExistsByUserId(Long id, Long excludeTeacherId) {
        if (excludeTeacherId == null)
            return teacherRepository.existsByUserId(id);
        else
            return teacherRepository.existsByUserIdAndIdNot(id, excludeTeacherId);
    }
}
