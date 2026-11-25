package team.wardrobe;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.wardrobe.employee.enums.EmployeeStatus;
import team.wardrobe.employee.model.Department;
import team.wardrobe.employee.model.Employee;
import team.wardrobe.uniform.model.DepartmentUniformAllocation;
import team.wardrobe.uniform.model.EmployeeUniformAssignment;
import team.wardrobe.uniform.model.UniformType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WardrobeModelsTest {
    @Autowired
    private EntityManager database;

    @Test
    void tracksDepartmentAllowanceSeparatelyFromWhatAnEmployeeReceived() {
        Department department = new Department("Admissions");
        Employee employee = new Employee("00042", "Test", "Employee", department);
        UniformType polo = new UniformType("Team member polo");
        LocalDate issueDate = LocalDate.of(2026, 11, 25);
        var allocation = new DepartmentUniformAllocation(department, polo, 2);
        var assignment = new EmployeeUniformAssignment(employee, polo, "XL", 1, issueDate);
        database.persist(department);
        database.persist(employee);
        database.persist(polo);
        database.persist(allocation);
        database.persist(assignment);
        database.flush();
        database.clear();

        var issued = database.find(EmployeeUniformAssignment.class, assignment.getId());
        var allowed = database.find(DepartmentUniformAllocation.class, allocation.getId());
        assertEquals("00042", issued.getEmployee().getEid());
        assertEquals(EmployeeStatus.ACTIVE, issued.getEmployee().getStatus());
        assertEquals(allowed.getDepartment(), issued.getEmployee().getDepartment());
        assertEquals(allowed.getUniformType(), issued.getUniformType());
        assertEquals(2, allowed.getQuantity());
        assertEquals(1, issued.getQuantity());
        assertEquals("XL", issued.getSize());
        assertEquals(issueDate, issued.getIssuedOn());
        assertNull(issued.getReturnedOn());
        assertNotNull(issued.getCreatedAt());
    }

    @Test
    void rejectsReturnsDatedBeforeIssue() {
        Department department = new Department("Admissions");
        Employee employee = new Employee("00042", "Test", "Employee", department);
        UniformType polo = new UniformType("Team member polo");
        database.persist(department);
        database.persist(employee);
        database.persist(polo);
        LocalDate issued = LocalDate.of(2026, 11, 25);
        var assignment = new EmployeeUniformAssignment(employee, polo, "XL", 1, issued);
        assignment.setReturnedOn(issued.minusDays(1));
        assertThrows(ConstraintViolationException.class, () -> {
            database.persist(assignment);
            database.flush();
        });
    }
}
