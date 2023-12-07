package pl.bscisel.timetable.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Consultation extends Event {

    @NotNull
    @ManyToOne
    private Teacher teacher;

}
