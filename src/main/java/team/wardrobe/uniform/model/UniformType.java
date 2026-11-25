package team.wardrobe.uniform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import team.wardrobe.shared.model.WardrobeRecord;

@Entity
@Table(name = "uniform_types")
public class UniformType extends WardrobeRecord {
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Size(max = 500)
    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    protected UniformType() {
    }

    public UniformType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
