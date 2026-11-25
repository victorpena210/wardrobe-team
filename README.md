# wardrobe.team

Spring Boot model layer for employee records and uniform distribution.

| Package | Models |
| --- | --- |
| `team.wardrobe.employee.model` | Department, Employee |
| `team.wardrobe.employee.enums` | EmployeeStatus |
| `team.wardrobe.uniform.model` | UniformType, DepartmentUniformAllocation, EmployeeUniformAssignment |

A department allocation records how many items of one uniform type an employee in that department is entitled to. An assignment records the size, quantity, and issue date for an actual distribution. Its optional return date applies to the entire assignment. Record separately issued or independently returnable items as separate assignments.

Run `mvn test` with Maven and JDK 17 or later. Tests create a temporary H2 database. The project includes the MySQL driver for the application database.
