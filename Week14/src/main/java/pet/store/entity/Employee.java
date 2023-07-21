package pet.store.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employee_id;

    private String employee_first_name;
    private String employee_last_name;
    private String employee_phone;
    private String employee_job_title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pet_store_id", nullable = false)
    private PetStore petStore;

}
