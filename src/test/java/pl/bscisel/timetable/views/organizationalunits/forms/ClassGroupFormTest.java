package pl.bscisel.timetable.views.organizationalunits.forms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.service.ClassGroupService;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;

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
        form = spy(new ClassGroupForm(organizationalUnitService, classGroupService));
    }

    @Test
    public void testRequiredFieldsAreRequired() {
        assertTrue(form.name.isRequired());
        assertTrue(form.organizationalUnit.isRequired());
    }

    @Test
    public void testBindingsAreSet() {
        assertNotNull(form.getBinder().getBinding("name"));
        assertNotNull(form.getBinder().getBinding("organizationalUnit"));
        assertNotNull(form.getBinder().getBinding("description"));
    }

    @Test
    public void testNameBinding() {
        ClassGroup bean = new ClassGroup();
        form.setFormBean(bean);
        form.name.setValue("Test class group");
        assertEquals("Test class group", bean.getName());
    }

    @Test
    public void testOrganizationalUnitBinding() {
        ClassGroup bean = new ClassGroup();
        form.setFormBean(bean);
        when(organizationalUnitService.getNameWithId(any())).thenReturn("1. Test organizational unit");
        OrganizationalUnit unit = new OrganizationalUnit();
        unit.setName("Test organizational unit");
        form.organizationalUnit.setValue(unit);
        assertEquals(unit.getName(), bean.getOrganizationalUnit().getName());
    }

    @Test
    public void testDescriptionBinding() {
        ClassGroup bean = new ClassGroup();
        form.setFormBean(bean);
        form.description.setValue("Test description");
        assertEquals("Test description", bean.getDescription());
    }
}