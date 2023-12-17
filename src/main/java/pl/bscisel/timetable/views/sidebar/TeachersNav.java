package pl.bscisel.timetable.views.sidebar;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.service.TeacherInfoService;
import pl.bscisel.timetable.views.sidebar.components.TeacherButton;
import pl.bscisel.timetable.views.timetable.TeacherView;

import java.util.List;


public class TeachersNav extends VerticalLayout {
    private final TeacherInfoService teacherInfoService;

    public TeachersNav(TeacherInfoService teacherInfoService) {
        this.teacherInfoService = teacherInfoService;

        setupTeachers();
        setSpacing(false);
    }

    private void setupTeachers() {
        List<TeacherInfo> teachers = teacherInfoService.findAllTeachers();
        add(makeButtonsForTeachers(teachers));
    }

    private TeacherButton[] makeButtonsForTeachers(List<TeacherInfo> teachers) {
        return teachers.stream().map(this::makeButtonForTeacher).toArray(TeacherButton[]::new);
    }

    private TeacherButton makeButtonForTeacher(TeacherInfo teacher) {
        TeacherButton button = new TeacherButton(teacher.getDegree() + " " + teacher.getName() + " " + teacher.getSurname());
        button.addClickListener(event -> getUI().ifPresent(x -> x.navigate(TeacherView.class, teacher.getId())));
        return button;
    }
}
