package pl.bscisel.timetable.data.entity;

import jakarta.persistence.*;
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

    @Size(max = 50, message = "Type cannot exceed {max} characters")
    @Column(name = "type", length = 50)
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
    char frequency;

    public Class() {
        setFrequency(DEFAULT_FREQUENCY);
    }

    public void setType(String type) {
        this.type = type.strip();
    }

    public ClassFrequency getFrequency() {
        return ClassFrequency.fromSymbol(frequency);
    }

    public void setFrequency(ClassFrequency frequency) {
        this.frequency = frequency.getSymbol();
    }

    @Getter
    public enum ClassFrequency {
        ODD_WEEKS('O'), EVEN_WEEKS('E'), ALL_WEEKS('A');

        public static final ClassFrequency DEFAULT_FREQUENCY = ALL_WEEKS;

        private final char symbol;

        ClassFrequency(char symbol) {
            this.symbol = symbol;
        }

        public static ClassFrequency fromSymbol(char symbol) {
            for (ClassFrequency frequency : ClassFrequency.values()) {
                if (frequency.symbol == symbol) {
                    return frequency;
                }
            }
            return null;
        }

        public String getLabel() {
            return this.name().charAt(0) + this.name().substring(1).toLowerCase().replace("_", " ");
        }

    }

}
