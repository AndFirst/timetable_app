package pl.bscisel.timetable.data.entity;

import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.utils.EntityTestUtils;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    private final Validator validator = EntityTestUtils.validator;

    @Test
    public void ensureStartTimeIsRequired() {
        Event event = new Event() {
        };
        assertEquals(1, validator.validateProperty(event, "startTime").size());

        event.setStartTime(LocalTime.of(8, 0));
        assertEquals(0, validator.validateProperty(event, "startTime").size());
    }

    @Test
    public void ensureEndTimeIsRequired() {
        Event event = new Event() {
        };
        assertEquals(1, validator.validateProperty(event, "endTime").size());

        event.setEndTime(LocalTime.of(10, 0));
        assertEquals(0, validator.validateProperty(event, "endTime").size());
    }

    @Test
    public void ensureDayOfWeekIsRequired() {
        Event event = new Event() {
        };
        assertEquals(1, validator.validateProperty(event, "dayOfWeek").size());

        event.setDayOfWeek(DayOfWeek.MONDAY);
        assertEquals(0, validator.validateProperty(event, "dayOfWeek").size());
    }

    @Test
    public void ensureLocationSizeConstraints() {
        Event event = new Event() {
        };
        EntityTestUtils.testStringSizeConstraints(event, event::setLocation, "location", 0, 50);
    }

    @Test
    public void ensureSetLocationStripsWhitespaces() {
        Event event = new Event() {
        };
        event.setLocation("  Location  ");
        assertEquals("Location", event.getLocation());
    }

    @Test
    public void ensureDescriptionSizeConstraints() {
        Event event = new Event() {
        };
        EntityTestUtils.testStringSizeConstraints(event, event::setDescription, "description", 0, 500);
    }

    @Test
    public void testValidateTime() {
        Event event = new Event() {
        };
        event.setStartTime(LocalTime.of(10, 0));
        event.setEndTime(LocalTime.of(8, 0));

        assertThrows(IllegalArgumentException.class, event::validateTime);

        event.setStartTime(LocalTime.of(8, 0));
        event.setEndTime(LocalTime.of(10, 0));
        assertDoesNotThrow(event::validateTime);
    }
}
