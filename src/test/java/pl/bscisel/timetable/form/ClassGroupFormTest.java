package pl.bscisel.timetable.form;

import com.vaadin.flow.data.provider.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.service.ClassGroupService;
import pl.bscisel.timetable.service.OrganizationalUnitService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassGroupFormTest {

    OrganizationalUnitService organizationalUnitService;
    ClassGroupService classGroupService;
    ClassGroupForm form;

    @BeforeEach
    public void setUp() {
        organizationalUnitService = mock(OrganizationalUnitService.class);
        classGroupService = mock(ClassGroupService.class);
        form = spy(ClassGroupForm.class);
        form.setOrganizationalUnitService(organizationalUnitService);
        form.setClassGroupService(classGroupService);
    }

    @Test
    public void testConfigureForm() {
        form.configureForm();
    }

    @Test
    public void testConfigureFields() {
        form.configureFields();
    }

    @Test
    public void testSetFieldsRequired() {
        form.setFieldsRequired();
        assertTrue(form.name.isRequired());
        assertTrue(form.organizationalUnit.isRequired());
    }

    @Test
    public void testPopulateFields() {
        mockOrganizationalUnitService();
        form.populateFields();
        verify(organizationalUnitService, times(1)).findAll();
        assertEquals(2, form.organizationalUnit.getDataProvider().size(new Query<>()));
    }

    private void mockOrganizationalUnitService() {
        OrganizationalUnit orgUnit = new OrganizationalUnit();
        orgUnit.setId(1L);
        orgUnit.setName("Test");

        OrganizationalUnit orgUnit2 = new OrganizationalUnit();
        orgUnit2.setId(2L);
        orgUnit2.setName("Test2");
        when(organizationalUnitService.findAll()).thenReturn(List.of(orgUnit, orgUnit2));
    }

    @Test
    public void testSetBindings() {
        form.setBindings();
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.name)));
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.organizationalUnit)));
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.description)));
    }

    @Test
    public void testNameBinding() {
        form.setBindings();
        ClassGroup bean = new ClassGroup();
        form.setFormBean(bean);
        form.name.setValue("Test class group");
        assertEquals("Test class group", bean.getName());
    }

    @Test
    public void testOrganizationalUnitBinding() {
        form.setBindings();
        ClassGroup bean = new ClassGroup();
        form.setFormBean(bean);
        OrganizationalUnit orgUnit = new OrganizationalUnit();
        orgUnit.setName("Test");
        OrganizationalUnit orgUnit2 = new OrganizationalUnit();
        orgUnit2.setName("Test2");
        form.organizationalUnit.setItems(List.of(orgUnit, orgUnit2));
        form.organizationalUnit.setValue(orgUnit2);
        assertEquals("Test2", bean.getOrganizationalUnit().getName());
    }

    @Test
    public void testDescriptionBinding() {
        form.setBindings();
        ClassGroup bean = new ClassGroup();
        form.setFormBean(bean);
        form.description.setValue("Test description");
        assertEquals("Test description", bean.getDescription());
    }

    @Test
    public void testConfigureEnterShortcut() {
        form.configureEnterShortcut();
        verify(form, times(1)).configureEnterShortcutWithFix(form.description);
    }

    @Test
    public void testAddComponentsToForm() {
        form.addComponentsToForm();
        assertEquals(4, form.getChildren().count());
    }
}