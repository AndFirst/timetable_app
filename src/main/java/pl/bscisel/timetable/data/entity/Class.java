package pl.bscisel.timetable.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"teachers"})
@Data
@Entity
@Table(name = "classes")
public class Class extends Event {

    public static final ClassFrequency DEFAULT_FREQUENCY = ClassFrequency.ALL_WEEKS;

    @NotBlank(message = "Type cannot be empty")
    @Size(min = 2, max = 50, message = "Type must be between {min} and {max} characters long")
    @Column(name = "type", length = 50, nullable = false)
    private String type;

    @NotNull(message = "You must select a course")
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @NotNull(message = "You must select a class group")
    @ManyToOne
    @JoinColumn(name = "class_group_id", nullable = false)
    private ClassGroup classGroup;

    @ManyToMany
    @JoinTable(name = "class_teacher", joinColumns = {@JoinColumn(name = "class_id")}, inverseJoinColumns = {@JoinColumn(name = "teacher_id")})
    private Set<TeacherInfo> teachers;

    /**
     * Frequency of the class. Can be one of the following:
     * <ul>
     *     <li>O - odd weeks</li>
     *     <li>E - even weeks</li>
     *     <li>A - all weeks</li>
     * </ul>
     */
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
