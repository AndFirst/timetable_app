package pl.bscisel.timetable.views.organizationalunits.forms;

import com.vaadin.flow.data.provider.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrganizationalUnitFormTest {

    OrganizationalUnitService organizationalUnitService;
    OrganizationalUnitForm form;

    @BeforeEach
    public void setUp() {
        organizationalUnitService = mock(OrganizationalUnitService.class);
        form = spy(OrganizationalUnitForm.class);
        form.setOrganizationalUnitService(organizationalUnitService);
    }

    @Test
    public void testInit() {
        form.init();
        verify(form).configureFields();
        verify(form).populateFields();
        verify(form).setBindings();
        verify(form).configureEnterShortcut(form.description);

        assertEquals(4, form.getChildren().count()); // 3 fields + buttons
    }

    @Test
    public void testConfigureFields() {
        form.configureFields();
        assertTrue(form.name.isRequired());
        assertEquals("If not selected, organizational unit will be top level. Organizational unit cannot be its own parent.", form.parentUnit.getHelperText());
    }

    @Test
    public void testPopulateFields() {
        mockOrganizationalUnitService();
        form.populateFields();
        verify(organizationalUnitService, times(1)).findAll();
        assertEquals(2, form.parentUnit.getDataProvider().size(new Query<>()));
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
        assertNotNull(form.getBinder().getBinding("name"));
        assertNotNull(form.getBinder().getBinding("description"));
        assertNotNull(form.getBinder().getBinding("parentUnit"));
    }

    @Test
    public void testNameBinding() {
        form.setBindings();
        OrganizationalUnit orgUnit = new OrganizationalUnit();
        form.setFormBean(orgUnit);
        form.name.setValue("Test");
        assertEquals("Test", orgUnit.getName());
    }

    @Test
    public void testDescriptionBinding() {
        form.setBindings();
        OrganizationalUnit orgUnit = new OrganizationalUnit();
        form.setFormBean(orgUnit);
        form.description.setValue("Test");
        assertEquals("Test", orgUnit.getDescription());
    }

    @Test
    public void testParentUnitBinding() {
        form.setBindings();
        OrganizationalUnit orgUnit = new OrganizationalUnit();
        form.setFormBean(orgUnit);
        OrganizationalUnit parentUnit = new OrganizationalUnit();
        parentUnit.setName("Test");
        OrganizationalUnit parentUnit2 = new OrganizationalUnit();
        parentUnit2.setName("Test2");
        form.parentUnit.setItems(List.of(parentUnit, parentUnit2));
        form.parentUnit.setValue(parentUnit2);
        assertNotNull(orgUnit.getParentUnit());
        assertEquals("Test2", orgUnit.getParentUnit().getName());
    }
}