package pl.bscisel.timetable.view.organizationalunits.dataproviders;

import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.service.OrganizationalUnitService;

import java.util.stream.Stream;

@Component
public class OrganizationalUnitDataProvider extends AbstractBackEndHierarchicalDataProvider<OrganizationalUnit, String>  {

    OrganizationalUnitService orgUnitService;

    public OrganizationalUnitDataProvider(@Autowired OrganizationalUnitService orgUnitService) {
        this.orgUnitService = orgUnitService;
    }

    @Override
    protected Stream<OrganizationalUnit> fetchChildrenFromBackEnd(HierarchicalQuery<OrganizationalUnit, String> query) {
        if (query.getParent() == null) {
            return orgUnitService.findTopLevelUnits().stream();
        } else {
            return orgUnitService.findChildrenByUnitId(query.getParent().getId()).stream();
        }
    }

    @Override
    public int getChildCount(HierarchicalQuery<OrganizationalUnit, String> query) {
        if (query.getParent() == null) {
            return orgUnitService.countTopLevelUnits();
        } else {
            return orgUnitService.countChildrenByUnitId(query.getParent().getId());
        }
    }

    @Override
    public boolean hasChildren(@NotNull OrganizationalUnit item) {
        return orgUnitService.hasChildrenByUnitId(item.getId());
    }
}
