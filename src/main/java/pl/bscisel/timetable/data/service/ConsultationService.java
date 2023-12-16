package pl.bscisel.timetable.data.service;

import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.Consultation;
import pl.bscisel.timetable.data.repository.ConsultationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultationService {
    private final ConsultationRepository consultationRepository;

    public ConsultationService(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    public Optional<Consultation> findById(Long id) {
        return consultationRepository.findById(id);
    }

    public List<Consultation> findAllByTeacherId(Long teacherId) {
        return consultationRepository.findAllByTeacherId(teacherId);
    }
}
