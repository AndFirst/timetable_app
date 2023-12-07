package pl.bscisel.timetable.data.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.DayOfWeek;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class Event extends AbstractEntity {

    @NotNull
    private LocalTime startDate;

    @NotNull
    private LocalTime endDate;

    @NotNull
    private DayOfWeek dayOfWeek;

    @Size(max = 50, message = "Location cannot exceed {max} characters")
    private String location;

    @Size(max = 500, message = "Description cannot exceed {max} characters")
    private String description;

}
