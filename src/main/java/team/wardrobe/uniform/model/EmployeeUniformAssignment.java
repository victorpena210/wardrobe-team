package team.wardrobe.uniform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import team.wardrobe.employee.model.Employee;
import team.wardrobe.shared.model.WardrobeRecord;

@Entity
@Table(name = "employee_uniform_assignments")
public class EmployeeUniformAssignment extends WardrobeRecord {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uniform_type_id", nullable = false)
    private UniformType uniformType;

    @NotBlank
    @Size(max = 32)
    @Column(nullable = false, length = 32)
    private String size;

    @Positive
    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDate issuedOn;

    @Column(name = "returned_on")
    private LocalDate returnedOn;

    protected EmployeeUniformAssignment() {
    }

    public EmployeeUniformAssignment(Employee employee, UniformType uniformType, String size, int quantity, LocalDate issuedOn) {
        this.employee = employee;
        this.uniformType = uniformType;
        this.size = size;
        this.quantity = quantity;
        this.issuedOn = issuedOn;
    }

    public Employee getEmployee() {
        return employee;
    }

    public UniformType getUniformType() {
        return uniformType;
    }

    public String getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getIssuedOn() {
        return issuedOn;
    }

    public LocalDate getReturnedOn() {
        return returnedOn;
    }

    public void setReturnedOn(LocalDate returnedOn) {
        this.returnedOn = returnedOn;
    }

    @AssertTrue(message = "Return date must be on or after issue date")
    private boolean isIssuePeriodValid() {
        return returnedOn == null || (issuedOn != null && !returnedOn.isBefore(issuedOn));
    }
}
