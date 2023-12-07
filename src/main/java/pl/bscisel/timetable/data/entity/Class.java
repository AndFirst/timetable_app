package pl.bscisel.timetable.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Class extends Event {

    public static final ClassFrequency DEFAULT_FREQUENCY = ClassFrequency.ALL_WEEKS;

    private String type;

    @NotNull(message = "You must select a course")
    @ManyToOne
    private Course course;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "class_teacher", joinColumns = {@JoinColumn(name = "class_id")}, inverseJoinColumns = {@JoinColumn(name = "teacher_id")})
    private Set<Teacher> teachers;

    private char frequency;

    public Class() {
        setFrequency(DEFAULT_FREQUENCY);
    }

    public ClassFrequency getFrequency() {
        return ClassFrequency.valueOf(String.valueOf(frequency));
    }

    public void setFrequency(ClassFrequency frequency) {
        this.frequency = frequency.getSymbol();
    }

    @Getter
    public enum ClassFrequency {
        ODD_WEEKS('O'), EVEN_WEEKS('E'), ALL_WEEKS('A');

        private final char symbol;

        ClassFrequency(char symbol) {
            this.symbol = symbol;
        }

    }

}
