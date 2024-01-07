package pl.bscisel.timetable.views.organizationalunits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.service.ClassGroupService;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;
import pl.bscisel.timetable.views.organizationalunits.dataproviders.OrganizationalUnitDataProvider;
import pl.bscisel.timetable.views.organizationalunits.forms.ClassGroupForm;
import pl.bscisel.timetable.views.organizationalunits.forms.OrganizationalUnitForm;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class OrganizationalUnitViewTest {

    OrganizationalUnitView view;
    OrganizationalUnitService service;
    OrganizationalUnitDataProvider dataProvider;
    OrganizationalUnitForm form;
    ClassGroupService classGroupService;
    ClassGroupForm classGroupForm;

    @BeforeEach
    public void setUp() {
        service = mock(OrganizationalUnitService.class);
        dataProvider = spy(new OrganizationalUnitDataProvider(service));
        form = spy(OrganizationalUnitForm.class);
        form.setOrganizationalUnitService(service);
        classGroupService = mock(ClassGroupService.class);
        classGroupForm = spy(ClassGroupForm.class);
        classGroupForm.setOrganizationalUnitService(service);
        classGroupForm.setClassGroupService(classGroupService);

        view = spy(new OrganizationalUnitView(service, dataProvider, form, classGroupService, classGroupForm));
    }

    @Test
    public void testFormNotVisibleOnStart() {
        assertFalse(view.unitForm.isVisible());
        assertFalse(view.groupForm.isVisible());
    }

    @Test
    public void testGridIsVisibleOnStart() {
        assertTrue(view.unitsGrid.isVisible());
        assertTrue(view.groupsGrid.isVisible());
    }

    @Test
    public void testToolbarIsVisibleOnStart() {
        assertTrue(view.addUnitBtn.isVisible());
        assertTrue(view.addGroupBtn.isVisible());
        assertTrue(view.editUnitBtn.isVisible());
        assertTrue(view.editGroupBtn.isVisible());
    }

    @Test
    public void testAddUnit() {
        view.addUnitBtn.click();
        assertTrue(view.unitForm.isVisible());
    }

    @Test
    public void testAddGroup() {
        view.addGroupBtn.click();
        assertTrue(view.groupForm.isVisible());
    }

    @Test
    public void testEditUnit() {
        assertFalse(view.unitForm.isVisible());
        assertFalse(view.groupForm.isVisible());
        view.unitsGrid.asSingleSelect().setValue(new OrganizationalUnit());
        view.editUnitBtn.click();
        assertTrue(view.unitForm.isVisible());
        assertFalse(view.groupForm.isVisible());
    }

    @Test
    public void testEditGroup() {
        assertFalse(view.unitForm.isVisible());
        assertFalse(view.groupForm.isVisible());
        view.groupsGrid.asSingleSelect().setValue(new ClassGroup());
        view.editGroupBtn.click();
        assertFalse(view.unitForm.isVisible());
        assertTrue(view.groupForm.isVisible());
    }

    @Test
    public void testCloseUnitForm() {
        view.addUnitBtn.click();
        assertTrue(view.unitForm.isVisible());
        view.closeUnitEditor();
        assertFalse(view.unitForm.isVisible());
        verify(view.unitForm, times(1)).setFormBean(null);
    }

    @Test
    public void testCloseGroupForm() {
        view.addGroupBtn.click();
        assertTrue(view.groupForm.isVisible());
        view.closeGroupEditor();
        assertFalse(view.groupForm.isVisible());
        verify(view.groupForm, times(1)).setFormBean(null);
    }

    @Test
    public void testUnitFormActionsAdded() {
        verify(view.unitForm, times(1)).addCancelAction(any());
        verify(view.unitForm, times(1)).addDeleteAction(any());
        verify(view.unitForm, times(1)).addSaveAction(any());
    }

    @Test
    public void testGroupFormActionsAdded() {
        verify(view.groupForm, times(1)).addCancelAction(any());
        verify(view.groupForm, times(1)).addDeleteAction(any());
        verify(view.groupForm, times(1)).addSaveAction(any());
    }

    @Test
    public void testSaveUnit() {
        OrganizationalUnit unit = new OrganizationalUnit();
        view.saveUnit(unit);
        verify(service, times(1)).save(unit);
        verify(view, times(1)).finishUnitEditing(unit);
        verify(view, times(1)).selectUnitInGrid(unit);
    }

    @Test
    public void testSaveGroup() {
        ClassGroup group = new ClassGroup();
        view.saveGroup(group);
        verify(classGroupService, times(1)).save(group);
        verify(view, times(1)).finishGroupEditing(group);
    }

    @Test
    public void testDeleteUnit() {
        OrganizationalUnit unit = new OrganizationalUnit();
        view.deleteUnit(unit);
        verify(service, times(1)).delete(unit);
    }

    @Test
    public void testDeleteGroup() {
        ClassGroup group = new ClassGroup();
        view.deleteGroup(group);
        verify(classGroupService, times(1)).delete(group);
    }

    @Test
    public void testFinishUnitEditing() {
        OrganizationalUnit unit = new OrganizationalUnit();
        view.finishUnitEditing(unit);
        verify(dataProvider, times(1)).refreshAll();
        verify(view, times(1)).closeUnitEditor();
        verify(view, times(1)).expandParents(unit.getParentUnit());
    }

    @Test
    public void testFinishGroupEditing() {
        ClassGroup group = new ClassGroup();
        view.finishGroupEditing(group);
        verify(view, times(1)).closeGroupEditor();
        verify(view, times(1)).expandParents(group.getOrganizationalUnit());
        verify(view, times(1)).selectUnitInGrid(group.getOrganizationalUnit());
        verify(view, times(1)).updateGroupGrid(group.getOrganizationalUnit());
    }


}