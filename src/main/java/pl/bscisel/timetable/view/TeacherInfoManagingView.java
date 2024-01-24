package pl.bscisel.timetable.view;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.form.TeacherInfoForm;
import pl.bscisel.timetable.service.TeacherInfoService;
import pl.bscisel.timetable.view.layout.MainLayout;

// LLR_360, LLR_362, LLR_363, LLR_364, LLR_365
@Route(value = "teachers", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Timetable - Teachers")
public class TeacherInfoManagingView extends AbstractManagingView<TeacherInfo, TeacherInfoForm, TeacherInfoService> {

    public TeacherInfoManagingView() {
    }

    @Override
    @Autowired
    void setService(TeacherInfoService teacherInfoService) {
        this.service = teacherInfoService;
    }

    @Override
    @Autowired
    void setForm(TeacherInfoForm form) {
        this.form = form;
    }

    @Override
    void configureFilter() {
        super.configureFilter();
        textFilter.setPlaceholder("Filter...");
    }

    @Override
    void configureAddButton() {
        super.configureAddButton();
        addEntityButton.setText("Add teacher");
    }

    @Override
    void setGridColumns() {
        grid.setColumns("id", "degree", "name", "surname", "phoneNumber");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
    }

}
