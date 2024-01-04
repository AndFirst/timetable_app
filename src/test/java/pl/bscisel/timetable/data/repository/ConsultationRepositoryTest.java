package pl.bscisel.timetable.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.bscisel.timetable.data.entity.Consultation;

import javax.sql.DataSource;
import java.time.DayOfWeek;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest(properties = {"spring.datasource.initialization-mode=never"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // because @Sql script is not reset after each test don't know why
class ConsultationRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ConsultationRepository consultationRepository;

    @Test
    @Sql(value = "consultations_data.sql")
    void testFindAllByTeacherId() {
        List<Consultation> consultations = consultationRepository.findAllByTeacherId(1L);
        assertEquals(1, consultations.size());
        consultations = consultationRepository.findAllByTeacherId(2L);
        assertEquals(2, consultations.size());
        assertEquals(DayOfWeek.MONDAY, consultations.get(0).getDayOfWeek());
    }

}