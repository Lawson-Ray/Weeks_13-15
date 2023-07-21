package pet.store.entity.controller.model;

import lombok.*;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class PetStoreData {
    private Long pet_store_id;
    private String pet_store_name;
    private String pet_store_address;
    private String pet_store_city;
    private String pet_store_state;
    private String pet_store_zip;
    private String pet_store_phone;

    public PetStoreData(PetStore petStore) {
        pet_store_id = petStore.getPet_store_id();
        pet_store_name = petStore.getPet_store_name();
        pet_store_address = petStore.getPet_store_address();
        pet_store_city = petStore.getPet_store_city();
        pet_store_state = petStore.getPet_store_state();
        pet_store_zip = petStore.getPet_store_zip();
        pet_store_phone = petStore.getPet_store_phone();

        for (Employee employee : petStore.getEmployees()) {
            employees.add(new EmployeeResponse(employee));
        }
    }

    private Set<EmployeeResponse> employees = new HashSet<>();

    @Value
    static class EmployeeResponse {
        private long employee_id;

        private String employee_first_name;
        private String employee_last_name;
        private String employee_phone;
        private String employee_job_title;

        EmployeeResponse(Employee employee) {
            employee_id = employee.getEmployee_id();
            employee_first_name = employee.getEmployee_first_name();
            employee_last_name = employee.getEmployee_last_name();
            employee_phone = employee.getEmployee_phone();
            employee_job_title = employee.getEmployee_job_title();
        }

        private Set<CustomerResponse> customers = new HashSet<>();


        @Data
        @NoArgsConstructor
        static class CustomerResponse {
            private Long customer_id;
            private String customer_first_name;
            private String customer_last_name;
            private String customer_email;
        }
    }
}