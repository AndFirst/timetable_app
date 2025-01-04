package pl.bscisel.timetable.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    @JoinTable(name = "classes_teachers", joinColumns = {@JoinColumn(name = "class_id")}, inverseJoinColumns = {@JoinColumn(name = "teacher_id")})
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

    /**
     * Creates a new class with default frequency
     */
    public Class() {
        setFrequency(ClassFrequency.DEFAULT_FREQUENCY);
    }

    /**
     * Sets the type of the class, stripping it of whitespaces
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type.strip();
    }

    /**
     * Gets the frequency of the class
     * @return the frequency
     */
    public ClassFrequency getFrequency() {
        return ClassFrequency.fromSymbol(frequency);
    }

    /**
     * Sets the frequency of the class
     * @param frequency the frequency to set
     */
    public void setFrequency(ClassFrequency frequency) {
        this.frequency = frequency.getSymbol();
    }

    /**
     * Needed for saving the frequency in the database with only one character
     */
    @Getter
    public enum ClassFrequency {
        ODD_WEEKS('O'), EVEN_WEEKS('E'), ALL_WEEKS('A');

        /**
         * The default frequency
         */
        public static final ClassFrequency DEFAULT_FREQUENCY = ALL_WEEKS;

        private final char symbol;

        /**
         * Creates a new frequency with the given symbol
         * @param symbol the symbol
         */
        ClassFrequency(char symbol) {
            this.symbol = symbol;
        }

        /**
         * Gets the frequency from the symbol
         * @param symbol the symbol
         * @return the frequency
         */
        public static ClassFrequency fromSymbol(char symbol) {
            for (ClassFrequency frequency : values()) {
                if (frequency.symbol == symbol) {
                    return frequency;
                }
            }
            return null;
        }

        /**
         * Gets the label of the frequency
         * @return the label
         */
        public String getLabel() {
            return this.name().charAt(0) + this.name().substring(1).toLowerCase().replace("_", " ");
        }

    }

}
