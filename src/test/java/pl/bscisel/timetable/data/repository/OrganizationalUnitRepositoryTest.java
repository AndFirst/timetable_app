package pl.bscisel.timetable.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest(properties = {"spring.datasource.initialization-mode=never"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // because @Sql script is not reset after each test don't know why
class OrganizationalUnitRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OrganizationalUnitRepository organizationalUnitRepository;

    @Test
    @Sql(value = "organizational_units_data.sql")
    public void testFindByParentUnitNull() {
        List<OrganizationalUnit> organizationalUnits = organizationalUnitRepository.findByParentUnitNullOrderByName();
        assertEquals(4, organizationalUnits.size());
    }

    @Test
    @Sql(value = "organizational_units_data.sql")
    public void testFindByParentUnitId() {
        List<OrganizationalUnit> organizationalUnits = organizationalUnitRepository.findByParentUnitIdOrderByName(1L);
        assertEquals(4, organizationalUnits.size());
        organizationalUnits = organizationalUnitRepository.findByParentUnitIdOrderByName(2L);
        assertEquals(3, organizationalUnits.size());
        organizationalUnits = organizationalUnitRepository.findByParentUnitIdOrderByName(3L);
        assertEquals(0, organizationalUnits.size());
    }

    @Test
    @Sql(value = "organizational_units_data.sql")
    public void testFindAll() {
        List<OrganizationalUnit> organizationalUnits = organizationalUnitRepository.findAll();
        assertEquals(11, organizationalUnits.size());
    }
}