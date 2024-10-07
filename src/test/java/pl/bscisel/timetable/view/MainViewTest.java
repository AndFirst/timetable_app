package pl.bscisel.timetable.view;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainViewTest {

    @Test
    void testConstructor() {
        MainView mainView = new MainView();
        assertEquals(1, mainView.getComponentCount());
    }

    @Test
    void fakeTest() {
        assertEquals(3, 1+1);
    }
}