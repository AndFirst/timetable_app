package pl.bscisel.timetable.view.login;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginViewTest {
    private LoginView view;

    @BeforeEach
    public void setUp() {
        view = spy(new LoginView());
    }

    @Test
    public void testInit() {
        view.init();
        verify(view, times(1)).setSizeFull();
        verify(view, times(1)).setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        verify(view, times(1)).setAlignItems(FlexComponent.Alignment.CENTER);

        assertEquals("login", view.login.getAction());

        assertEquals(2, view.getComponentCount());
        assertEquals("Timetable", view.getComponentAt(0).getElement().getText());
    }
}