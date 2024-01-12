package pl.bscisel.timetable.view.layout.sidebar;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.service.TeacherInfoService;
import pl.bscisel.timetable.view.layout.sidebar.components.TeacherButton;
import pl.bscisel.timetable.view.timetables.TeacherTimetableView;

import java.util.List;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TeachersNav extends VerticalLayout {
    private final TeacherInfoService teacherInfoService;

    public TeachersNav(TeacherInfoService teacherInfoService) {
        this.teacherInfoService = teacherInfoService;
        setSpacing(false);

        setupTeachers();
    }

    private void setupTeachers() {
        List<TeacherInfo> teachers = teacherInfoService.findAllOrderBySurname();
        add(makeButtonsForTeachers(teachers));
    }

    private TeacherButton[] makeButtonsForTeachers(List<TeacherInfo> teachers) {
        return teachers.stream().map(this::makeButtonForTeacher).toArray(TeacherButton[]::new);
    }

    private TeacherButton makeButtonForTeacher(TeacherInfo teacher) {
        TeacherButton button = new TeacherButton(teacher.getDegree() + " " + teacher.getName() + " " + teacher.getSurname());
        button.addClickListener(event -> getUI().ifPresent(x -> x.navigate(TeacherTimetableView.class, teacher.getId())));
        return button;
    }
}
