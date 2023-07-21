package pet.store.entity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pet.store.entity.controller.model.CustomerData;
import pet.store.entity.controller.model.EmployeeData;
import pet.store.entity.controller.model.PetStoreData;
import pet.store.entity.service.PetStoreService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
    @Autowired
    private PetStoreService petStoreService;
    @PostMapping("/store")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData) {
    log.info("Creating pet store {}", petStoreData);
    return petStoreService.savePetStore(petStoreData);
    }

    @PutMapping("/store/{petStoreId}")
    public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
        petStoreData.setPet_store_id(petStoreId);
        log.info("Updating pet store {}", petStoreData);
        return petStoreService.savePetStore(petStoreData);
    }
    @GetMapping("/store")
    public List<PetStoreData> retrieveAllPetStores(){
        log.info("Retrieve all pet stores called.");
        return petStoreService.retrieveAllPetStores();
    }
    @GetMapping("/store/{petStoreId}")
    public PetStoreData retrievePetStoreById(@PathVariable Long petStoreId) {
        log.info("retrieving pet store with ID={}", petStoreId);
        return petStoreService.retrievePetStoreById(petStoreId);
    }
    @DeleteMapping("/store")
    public void deleteAllPetStores() {
        log.info("Attempting to delete all pet stores");
        throw new UnsupportedOperationException("Deleting all pet stores is not allowed.");
    }
    @DeleteMapping("/store/{petStoreId}")
    public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
        log.info("Deleting pet store with ID={}", petStoreId);

        petStoreService.deletePetStoreByID(petStoreId);
        return Map.of("message", "Deletion of pet store with ID=" + petStoreId + " was successful.");
    }


    @PostMapping("/store/{petStoreId}/customer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CustomerData insertCustomer(@PathVariable Long petStoreId, @RequestBody CustomerData customerData) {

        log.info("Creating customer {} for pet store with ID=", petStoreId, customerData);
        return petStoreService.saveCustomer(petStoreId, customerData);
    }
    @GetMapping("/store/{petStoreId}/customer/{customerId}")
    public CustomerData retrieveCustomerById(@PathVariable Long petStoreId, @PathVariable Long customerId) {
        log.info("Retrieving customer with ID={} for pet store with ID={}", customerId, petStoreId);
        return petStoreService.retrieveCustomerById(petStoreId, customerId);
    }

    @PostMapping("/store/{petStoreId}/employee")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EmployeeData insertEmployee(@PathVariable Long petStoreId, @RequestBody EmployeeData employeeData) {

        log.info("Creating employee {} for pet store with ID=", petStoreId, employeeData);
        return petStoreService.saveEmployee(petStoreId, employeeData);
    }
    @GetMapping("/store/{petStoreId}/employee/{employeeId}")
    public EmployeeData retrieveEmployeeId(@PathVariable Long petStoreId, @PathVariable Long employeeId) {
        log.info("Retrieving employee with ID={} for pet store with ID={}", employeeId, petStoreId);
        return petStoreService.retrieveEmployeeById(petStoreId, employeeId);
    }
}
