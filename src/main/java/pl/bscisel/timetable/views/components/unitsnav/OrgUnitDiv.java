package pl.bscisel.timetable.views.components.unitsnav;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import lombok.Getter;

public class OrgUnitDiv extends Div {
    @Getter
    private boolean childrenSet = false;

    private Div children = null;

    OrgUnitDiv(Component... components) {
        super(components);
        setClassName("org-unit-div");
    }

    public void setChildren(Div children) {
        this.children = children;
        children.getStyle().set("margin-left", "10px");
        add(children);
        childrenSet = true;
    }

    public void hideChildren() {
        if (children != null) children.setVisible(false);
    }

    public void showChildren() {
        if (children == null) throw new RuntimeException("Trying to display a children div that has not been loaded.");
        children.setVisible(true);
    }
}
