package pl.bscisel.timetable.data.entity;

import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.utils.EntityTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsultationTest {

    @Test
    public void ensureTeacherIsRequired() {
        Consultation consultation = new Consultation();
        assertEquals(1, EntityTestUtils.validator.validateProperty(consultation, "teacher").size());

        consultation.setTeacher(new TeacherInfo());
        assertEquals(0, EntityTestUtils.validator.validateProperty(consultation, "teacher").size());
    }

}