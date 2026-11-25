package team.wardrobe.employee.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import team.wardrobe.employee.enums.EmployeeStatus;
import team.wardrobe.shared.model.WardrobeRecord;

@Entity
@Table(name = "employees")
public class Employee extends WardrobeRecord {
    @NotBlank
    @Size(max = 64)
    @Column(nullable = false, length = 64, unique = true)
    private String eid;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String lastName;

    @Email
    @Size(max = 254)
    @Column(length = 254)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmployeeStatus status = EmployeeStatus.ACTIVE;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "separation_date")
    private LocalDate separationDate;

    protected Employee() {
    }

    public Employee(String eid, String firstName, String lastName, Department department) {
        this.eid = eid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    public String getEid() {
        return eid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getSeparationDate() {
        return separationDate;
    }

    public void setSeparationDate(LocalDate separationDate) {
        this.separationDate = separationDate;
    }

    @AssertTrue(message = "Separation date must be on or after hire date")
    private boolean isEmploymentPeriodValid() {
        return hireDate == null || separationDate == null || !separationDate.isBefore(hireDate);
    }
}
