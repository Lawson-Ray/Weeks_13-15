package pet.store.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;
    private String customer_first_name;
    private String customer_last_name;
    private String customer_email;

    @ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
    private Set<PetStore> petStores = new HashSet<>();


}
