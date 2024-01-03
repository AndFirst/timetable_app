package pl.bscisel.timetable.data.entity.utils;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityTestUtils {
    public static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static void testStringSizeConstraints(Object object, StringSetter setter, String propertyName, int min, int max) {
        String tooLong = "A".repeat(max + 1);
        String justRightMin = "A".repeat(min);
        String justRightMax = "A".repeat(max);

        if (min > 0) {
            String tooShort = "A".repeat(min - 1);
            setter.set(tooShort);
            assertEquals(1, validator.validateProperty(object, propertyName).size());
        }

        setter.set(tooLong);
        assertEquals(1, validator.validateProperty(object, propertyName).size());

        setter.set(justRightMin);
        assertEquals(0, validator.validateProperty(object, propertyName).size());

        setter.set(justRightMax);
        assertEquals(0, validator.validateProperty(object, propertyName).size());
    }
}
