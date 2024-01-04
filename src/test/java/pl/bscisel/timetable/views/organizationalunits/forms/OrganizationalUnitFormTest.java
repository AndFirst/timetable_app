package pl.bscisel.timetable.views.organizationalunits.forms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrganizationalUnitFormTest {

    OrganizationalUnitService organizationalUnitService;
    OrganizationalUnitForm form;

    @BeforeEach
    public void setUp() {
        organizationalUnitService = mock(OrganizationalUnitService.class);
        form = spy(new OrganizationalUnitForm(organizationalUnitService));
    }

    @Test
    public void testRequiredFieldsAreRequired() {
        assertTrue(form.name.isRequired());
    }

    @Test
    public void testBindingsAreSet() {
        assertNotNull(form.getBinder().getBinding("name"));
        assertNotNull(form.getBinder().getBinding("parentUnit"));
        assertNotNull(form.getBinder().getBinding("description"));
    }

    @Test
    public void testNameBinding() {
        OrganizationalUnit bean = new OrganizationalUnit();
        form.setFormBean(bean);
        form.name.setValue("Test organizational unit");
        assertEquals("Test organizational unit", bean.getName());
    }

    @Test
    public void testParentUnitBinding() {
        OrganizationalUnit bean = new OrganizationalUnit();
        form.setFormBean(bean);
        when(organizationalUnitService.getNameWithId(any())).thenReturn("1. Test organizational unit");
        OrganizationalUnit unit = new OrganizationalUnit();
        unit.setName("Test organizational unit");
        form.parentUnit.setValue(unit);
        assertEquals(unit.getName(), bean.getParentUnit().getName());
    }

    @Test
    public void testDescriptionBinding() {
        OrganizationalUnit bean = new OrganizationalUnit();
        form.setFormBean(bean);
        form.description.setValue("Test description");
        assertEquals("Test description", bean.getDescription());
    }

}