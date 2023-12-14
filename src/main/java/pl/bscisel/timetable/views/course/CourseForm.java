package pl.bscisel.timetable.views.course;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;
import pl.bscisel.timetable.data.entity.Course;

public class CourseForm extends FormLayout {
    TextField code = new TextField("Code");
    TextField name = new TextField("Name");
    TextArea description = new TextArea("Description");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    ShortcutRegistration enterShortcut;

    Binder<Course> binder = new BeanValidationBinder<>(Course.class);

    public CourseForm() {
        binder.bindInstanceFields(this);
        configureButtons();

        add(code, name, description, getButtons());
    }

    private void configureButtons() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        close.addClickShortcut(Key.ESCAPE);
        configureEnterShortcut();
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    private Component getButtons() {
        return new HorizontalLayout(save, delete, close);
    }

    public void setCourse(Course course) {
        binder.setBean(course);
    }

    private void configureEnterShortcut() {
        if (enterShortcut == null)
            enterShortcut = save.addClickShortcut(Key.ENTER);

        description.addBlurListener(e -> {
            enterShortcut = save.addClickShortcut(Key.ENTER);
        });

        description.addFocusListener(e -> {
            enterShortcut.remove();
        });
    }

    @Getter
    public static abstract class CourseFormEvent extends ComponentEvent<CourseForm> {
        private final Course course;

        protected CourseFormEvent(CourseForm source, Course course) {
            super(source, false);
            this.course = course;
        }

    }

    public static class SaveEvent extends CourseFormEvent {
        SaveEvent(CourseForm source, Course contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends CourseFormEvent {
        DeleteEvent(CourseForm source, Course contact) {
            super(source, contact);
        }
    }

    public static class CloseEvent extends CourseFormEvent {
        CloseEvent(CourseForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }

}
