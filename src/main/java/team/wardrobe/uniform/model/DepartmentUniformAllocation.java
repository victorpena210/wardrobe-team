package team.wardrobe.uniform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Positive;
import team.wardrobe.employee.model.Department;
import team.wardrobe.shared.model.WardrobeRecord;

@Entity
@Table(name = "department_uniform_allocations", uniqueConstraints =
        @UniqueConstraint(columnNames = {"department_id", "uniform_type_id"}))
public class DepartmentUniformAllocation extends WardrobeRecord {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uniform_type_id", nullable = false)
    private UniformType uniformType;

    @Positive
    @Column(nullable = false)
    private int quantity;

    protected DepartmentUniformAllocation() {
    }

    public DepartmentUniformAllocation(Department department, UniformType uniformType, int quantity) {
        this.department = department;
        this.uniformType = uniformType;
        this.quantity = quantity;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public UniformType getUniformType() {
        return uniformType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
