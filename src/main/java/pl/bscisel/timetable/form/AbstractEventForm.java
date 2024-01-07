package pl.bscisel.timetable.form;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.BeanValidator;
import org.jetbrains.annotations.NotNull;
import pl.bscisel.timetable.data.entity.Class;
import pl.bscisel.timetable.data.entity.Event;

import java.time.DayOfWeek;
import java.time.LocalTime;

public abstract class AbstractEventForm<T extends Event> extends AbstractForm<T> {
    TimePicker startTime = new TimePicker("Start time");
    TimePicker endTime = new TimePicker("End time");
    Select<DayOfWeek> dayOfWeek = new Select<>();

    public AbstractEventForm(Binder<T> binder) {
        super(binder);
    }

    @Override
    void configureForm() {
        setResponsiveSteps(new ResponsiveStep("0", 1));
    }

    @Override
    void configureFields() {
        startTime.setMin(LocalTime.of(7, 0));
        startTime.setMax(LocalTime.of(21, 45));

        endTime.setMin(LocalTime.of(7, 15));
        endTime.setMax(LocalTime.of(22, 0));

        dayOfWeek.setLabel("Day of week");
        dayOfWeek.setRequiredIndicatorVisible(true);
    }

    @Override
    void setFieldsRequired() {
        startTime.setRequired(true);
        endTime.setRequired(true);
    }

    @Override
    void populateFields() {
        dayOfWeek.setItems(DayOfWeek.values());
        dayOfWeek.setItemLabelGenerator(item -> item.toString().charAt(0) + item.toString().substring(1).toLowerCase());
    }

    @Override
    void setBindings() {
        binder.forField(startTime)
                .withValidator(new BeanValidator(Class.class, "startTime"))
                .withValidator(startTime -> {
                    if (binder.getBean() == null || binder.getBean().getEndTime() == null)
                        return true;
                    return startTime.isBefore(binder.getBean().getEndTime());
                }, "Start time must be before end time")
                .bind(Event::getStartTime, Event::setStartTime);
        binder.forField(endTime)
                .withValidator(new BeanValidator(Class.class, "endTime"))
                .withValidator(endTime -> {
                    if (binder.getBean() == null || binder.getBean().getStartTime() == null)
                        return true;
                    return endTime.isAfter(binder.getBean().getStartTime());
                }, "End time must be after start time")
                .withValidator(endTime -> {
                    if (binder.getBean() == null || binder.getBean().getStartTime() == null)
                        return true;
                    return endTime.minusMinutes(14).isAfter(binder.getBean().getStartTime());
                }, "Event must be at least 15 minutes long")
                .bind(Event::getEndTime, Event::setEndTime);
        binder.forField(dayOfWeek)
                .withValidator(new BeanValidator(Class.class, "dayOfWeek"))
                .bind(Event::getDayOfWeek, Event::setDayOfWeek);

    }

    @NotNull
    protected HorizontalLayout createDurationFields() {
        HorizontalLayout duration = new HorizontalLayout(startTime, endTime);
        duration.setFlexGrow(1, startTime, endTime);
        duration.setWidthFull();
        return duration;
    }

}
