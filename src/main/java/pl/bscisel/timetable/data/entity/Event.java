package pl.bscisel.timetable.data.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.DayOfWeek;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = false)
@Data
@MappedSuperclass
public abstract class Event extends AbstractEntity {

    @NotNull(message = "Start time cannot be empty")
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @NotNull(message = "End time cannot be empty")
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @NotNull(message = "Day of week cannot be empty")
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Nullable
    @Size(max = 50, message = "Location cannot exceed {max} characters")
    @Column(name = "location", length = 50)
    private String location;

    @Nullable
    @Size(max = 500, message = "Description cannot exceed {max} characters")
    @Column(name = "description", length = 500)
    private String description;

    @PrePersist
    @PreUpdate
    void validateTime() {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
    }

    public void setLocation(String location) {
        this.location = location.strip();
    }
}
