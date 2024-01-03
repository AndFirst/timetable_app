package pl.bscisel.timetable.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import pl.bscisel.timetable.data.entity.ClassGroup;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(properties = {"spring.datasource.initialization-mode=never"})
class ClassGroupRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ClassGroupRepository classGroupRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Sql("class_groups_data.sql")
    @Test
    void findByOrganizationalUnitIdOrderByName() {
        List<ClassGroup> classGroups = classGroupRepository.findByOrganizationalUnitIdOrderByName(1L);

        assertEquals(3, classGroups.size());
        assertEquals("Class Group 1", classGroups.get(0).getName());
    }
}