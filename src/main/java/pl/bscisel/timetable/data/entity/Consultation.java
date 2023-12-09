package pl.bscisel.timetable.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "consultations")
public class Consultation extends Event {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherInfo teacher;

}
