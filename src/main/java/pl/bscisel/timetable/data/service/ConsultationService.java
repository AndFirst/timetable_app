package pl.bscisel.timetable.data.service;

import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.repository.ConsultationRepository;

@Service
public class ConsultationService {
    private final ConsultationRepository consultationRepository;

    public ConsultationService(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

}
