package pet.store.entity.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.PetStore;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class CustomerData {
    private Long customer_id;
    private String customer_first_name;
    private String customer_last_name;
    private String customer_email;

    private Set<CustomerPetStore> petStores = new HashSet<>();


    public CustomerData(Customer customer) {
        customer_id = customer.getCustomer_id();
        customer_first_name = customer.getCustomer_first_name();
        customer_last_name = customer.getCustomer_last_name();
        customer_email = customer.getCustomer_email();
    }

    @Data
    @NoArgsConstructor
    private static class CustomerPetStore {
        private Long pet_store_id;
        private String pet_store_name;
        private String pet_store_address;
        private String pet_store_city;
        private String pet_store_state;
        private String pet_store_zip;
        private String pet_store_phone;

        public CustomerPetStore(PetStore petStore) {
            pet_store_id = petStore.getPet_store_id();
            pet_store_name = petStore.getPet_store_name();
            pet_store_address = petStore.getPet_store_address();
            pet_store_city = petStore.getPet_store_city();
            pet_store_state = petStore.getPet_store_state();
            pet_store_zip = petStore.getPet_store_zip();
            pet_store_phone = petStore.getPet_store_phone();
        }
    }

}
