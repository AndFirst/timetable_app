package pl.bscisel.timetable.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.AbstractEntity;
import pl.bscisel.timetable.form.AbstractForm;
import pl.bscisel.timetable.service.BasicEntityOperationsService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractManagingViewTest {

    AbstractManagingView<
            AbstractEntity,
            AbstractForm<AbstractEntity>,
            BasicEntityOperationsService<AbstractEntity>> view;
    BasicEntityOperationsService<AbstractEntity> service;
    AbstractForm<AbstractEntity> form;

    @NotNull
    private static AbstractManagingView<AbstractEntity, AbstractForm<AbstractEntity>, BasicEntityOperationsService<AbstractEntity>> getImplementedView() {
        return new AbstractManagingView<>() {
            @Override
            void setForm(AbstractForm<AbstractEntity> form) {
                this.form = form;
            }

            @Override
            void setService(BasicEntityOperationsService<AbstractEntity> service) {
                this.service = service;
            }

            @Override
            void setGridColumns() {

            }
        };
    }

    @NotNull
    private static AbstractForm<AbstractEntity> getImplementedForm() {
        return new AbstractForm<>(new Binder<>(AbstractEntity.class)) {
            @Override
            protected void configureForm() {

            }

            @Override
            protected void configureFields() {

            }

            @Override
            protected void setFieldsRequired() {

            }

            @Override
            protected void populateFields() {

            }

            @Override
            protected void setBindings() {

            }

            @Override
            protected void configureEnterShortcut() {

            }

            @Override
            protected void addComponentsToForm() {

            }

        };
    }

    @BeforeEach
    public void setUp() {
        service = mock(BasicEntityOperationsService.class);
        when(service.getEntityClass()).thenReturn(AbstractEntity.class);

        form = spy(getImplementedForm()); // creating object manually because simple mock doesn't work here

        var implementedView = getImplementedView();  // creating object manually because simple mock doesn't work here
        implementedView.setService(service);
        implementedView.setForm(form);

        view = spy(implementedView);
    }

    @Test
    public void testInit() {
        view.init();
        verify(view, times(1)).configureLayout();
        verify(view, times(1)).configureToolbar();
        verify(view, times(1)).configureGrid();
        verify(view, times(1)).setGridColumns();
        verify(view, times(1)).configureForm();
        verify(view, times(1)).createToolbarLayout();
        verify(view, times(1)).createContentLayout();
        assertEquals(2, view.getComponentCount());
        verify(view, times(1)).updateItems();
    }

    @Test
    public void testConfigureLayout() {
        view.configureLayout();
        assertEquals("managing-view", view.getClassName());
        assertEquals("100%", view.getWidth());
        assertEquals("100%", view.getHeight());
    }

    @Test
    public void testConfigureToolbar() {
        view.configureToolbar();
        verify(view, times(1)).configureFilter();
        verify(view, times(1)).configureAddButton();
    }

    @Test
    public void testConfigureFilter() {
        view.configureFilter();
        assertNotNull(view.textFilter);
        view.configureGrid();
        view.textFilter.setValue("test");
        verify(view, times(1)).updateItems();
        assertEquals(ValueChangeMode.LAZY, view.textFilter.getValueChangeMode());
        assertTrue(view.textFilter.isClearButtonVisible());
    }

    @Test
    public void testConfigureAddButton() {
        view.configureAddButton();
        assertNotNull(view.addEntityButton);
        view.configureGrid();
        view.addEntityButton.click();
        verify(view, times(1)).add();
    }

    @Test
    public void testConfigureForm() {
        view.configureForm();
        assertFalse(view.form.isVisible());
        verify(form, times(1)).addSaveAction(any());
        verify(form, times(1)).addDeleteAction(any());
        verify(form, times(1)).addCancelAction(any());
    }

    @Test
    public void testConfigureGrid() {
        view.configureGrid();
        assertEquals("grid", view.grid.getClassName());
        assertEquals("100%", view.grid.getWidth());
        assertEquals("100%", view.grid.getHeight());

        AbstractEntity entity = new AbstractEntity() {
            @Override
            public Long getId() {
                return 1L;
            }
        };

        view.grid.select(entity);
        verify(view, times(1)).edit(entity, false);
    }

    @Test
    public void testSave() {
        view.configureFilter();
        view.configureGrid();
        AbstractEntity entity = new AbstractEntity() {
            @Override
            public Long getId() {
                return 1L;
            }
        };
        view.save(entity);
        verify(service, times(1)).save(entity);
        verify(view, times(1)).updateItems();
        verify(view, times(1)).closeEditor();
    }

    @Test
    public void testDelete() {
        view.configureFilter();
        view.configureGrid();
        AbstractEntity entity = new AbstractEntity() {
        };
        view.delete(entity);
        verify(service, times(1)).delete(entity);
        verify(view, times(1)).updateItems();
        verify(view, times(1)).closeEditor();
    }

    @Test
    public void testAdd() {
        view.configureGrid();
        AbstractEntity abstractEntity = new AbstractEntity() {
        };
        view.grid.setItems(abstractEntity);

        assertTrue(view.grid.getSelectedItems().isEmpty());

        view.grid.select(abstractEntity);

        assertFalse(view.grid.getSelectedItems().isEmpty());

        view.add();

        assertTrue(view.grid.getSelectedItems().isEmpty());

        verify(view, times(3)).edit(any(), anyBoolean()); // todo: possible bug, should be 2 times?
    }

    @Test
    public void testEdit() {
        AbstractEntity entity = new AbstractEntity() {
        };
        view.edit(entity, true);
        verify(form, times(1)).setFormBean(entity);
        verify(form, times(1)).setVisible(true);
        verify(view, times(1)).addClassName("editing");

        view.edit(null, false);
        verify(view, times(1)).closeEditor();
    }

    @Test
    public void testCloseEditor() {
        view.closeEditor();
        verify(form, times(1)).setFormBean(null);
        verify(form, times(1)).setVisible(false);
        verify(view, times(1)).removeClassName("editing");
    }

    @Test
    public void testCloseForm() {
        view.closeForm();
        verify(view, times(1)).closeEditor();
    }

    @Test
    public void testCreateToolbarLayout() {
        view.configureToolbar();
        Component component = view.createToolbarLayout();
        assertNotNull(component);
        assertEquals("toolbar", component.getClassName());
        assertEquals(2, component.getChildren().count());
    }

    @Test
    public void testCreateContentLayout() {
        view.configureGrid();
        HorizontalLayout layout = view.createContentLayout();
        assertNotNull(layout);
        assertEquals("100%", layout.getWidth());
        assertEquals("100%", layout.getHeight());
        assertEquals(2, layout.getFlexGrow(layout.getComponentAt(0)));
        assertEquals(1, layout.getFlexGrow(layout.getComponentAt(1)));
        assertEquals(2, layout.getChildren().count());
    }

    @Test
    public void testUpdateItems() {
        view.textFilter = new TextField();
        view.configureGrid();
        view.updateItems();
        verify(service, times(1)).search(view.textFilter.getValue());

        when(service.search("test")).thenReturn(List.of(new AbstractEntity() {
        }));

        view.textFilter.setValue("test");
        view.updateItems();
        verify(service, times(1)).search("test");
        assertEquals(1, view.grid.getDataProvider().size(new Query<>()));
    }
}