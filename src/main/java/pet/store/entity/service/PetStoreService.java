package pet.store.entity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.store.entity.Customer;
import pet.store.entity.Dao.CustomerDao;
import pet.store.entity.Dao.EmployeeDao;
import pet.store.entity.Dao.PetStoreDao;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;
import pet.store.entity.controller.model.CustomerData;
import pet.store.entity.controller.model.EmployeeData;
import pet.store.entity.controller.model.PetStoreData;

import java.util.*;

@Service
public class PetStoreService {
    @Autowired
    private PetStoreDao petStoreDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private EmployeeDao employeeDao;

    @Transactional(readOnly = false)
    public PetStoreData savePetStore(PetStoreData petStoreData) {

        Long petStoreId = petStoreData.getPet_store_id();

        PetStore petStore = findOrCreatePetStore(petStoreId);

        setFieldsInPetStore(petStore, petStoreData);
        return new PetStoreData(petStoreDao.save(petStore));
    }

    private void setFieldsInPetStore(PetStore petStore, PetStoreData petStoreData) {
        petStore.setPet_store_name(petStoreData.getPet_store_name());
        petStore.setPet_store_address(petStoreData.getPet_store_address());
        petStore.setPet_store_city(petStoreData.getPet_store_city());
        petStore.setPet_store_state(petStoreData.getPet_store_state());
        petStore.setPet_store_zip(petStoreData.getPet_store_zip());
        petStore.setPet_store_phone(petStoreData.getPet_store_phone());
    }

    private PetStore findOrCreatePetStore(Long petStoreId) {
        PetStore petStore;

        if (Objects.isNull(petStoreId)) {
            petStore = new PetStore();
        } else {
            petStore = findPetStoreById(petStoreId);
        }
        return petStore;
    }


    private PetStore findPetStoreById(Long petStoreId) {
        return petStoreDao.findById(petStoreId).orElseThrow(() -> new NoSuchElementException("Pet Store with ID=" + petStoreId + " was not found."));
    }

    @Transactional(readOnly = true)
    public List<PetStoreData> retrieveAllPetStores() {
        List<PetStore> petStores = petStoreDao.findAll();
        List<PetStoreData> response = new LinkedList<>();
        for (PetStore petStore : petStores) {
            response.add(new PetStoreData(petStore));
        }
        return response;
        // or
        // @formatter:off
        // return petStoreDao.findAll()
        // .stream()
        // .map(petStoreData::new)
        // .toList();
        // @formatter:on
    }

    @Transactional(readOnly = true)
    public PetStoreData retrievePetStoreById(Long petStoreId) {
        PetStore petStore = findPetStoreById(petStoreId);
        return new PetStoreData(petStore);
    }

    @Transactional(readOnly = false)
    public void deletePetStoreByID(Long petStoreId) {
        PetStore petStore = findPetStoreById(petStoreId);
        petStoreDao.delete(petStore);
    }
    @Transactional(readOnly = false)
    public EmployeeData saveEmployee(Long petStoreId, EmployeeData employeeData) {
        PetStore petStore = findPetStoreById(petStoreId);

        Employee employee = findOrCreateEmployee(employeeData.getEmployee_id());

        setEmployeeFields(employee, employeeData);
        employee.setPetStore(petStore);
        petStore.getEmployees().add(employee);

        Employee dbEmployee = employeeDao.save(employee);
        return new EmployeeData(dbEmployee);
    }

    private void setEmployeeFields(Employee employee, EmployeeData employeeData) {
        employee.setEmployee_first_name(employeeData.getEmployee_first_name());
        employee.setEmployee_last_name(employeeData.getEmployee_last_name());
        employee.setEmployee_phone(employeeData.getEmployee_phone());
        employee.setEmployee_job_title((employeeData.getEmployee_job_title()));
    }

    private Employee findOrCreateEmployee(Long employeeId) {
        Employee employee;
        if(Objects.isNull(employeeId)) {
            employee = new Employee();
        }
        else {
            employee = findEmployeeById(employeeId);
        }
        return employee;
    }

    private Employee findEmployeeById(Long employeeId) {
        return employeeDao.findById(employeeId).orElseThrow(() -> new NoSuchElementException("employee with ID=" + employeeId + " does not exist."));
    }

    @Transactional(readOnly = false)
    public CustomerData saveCustomer(Long petStoreId, CustomerData customerData) {
        PetStore petStore = findPetStoreById(petStoreId);

        Customer customer = findOrCreateCustomer(customerData.getCustomer_id());

        setCustomerFields(customer, customerData);
        customer.setPetStores(Collections.singleton(petStore));
        Customer dbcustomer = customerDao.save(customer);

        return new CustomerData(dbcustomer);
    }

    private void setCustomerFields(Customer customer, CustomerData customerData) {
        customer.setCustomer_first_name(customerData.getCustomer_first_name());
        customer.setCustomer_last_name((customerData.getCustomer_last_name()));
        customer.setCustomer_email(customerData.getCustomer_email());
    }

    private Customer findOrCreateCustomer(Long customerId) {
        Customer customer;
        if(Objects.isNull(customerId)) {
            customer = new Customer();
        }
        else {
            customer = findCustomerById(customerId);
        }
        return customer;
    }

    private Customer findCustomerById(Long customerId) {
        return customerDao.findById(customerId).orElseThrow(() -> new NoSuchElementException("customer with ID=" + customerId + " does not exist."));
    }

    @Transactional(readOnly = true)
    public CustomerData retrieveCustomerById(Long petStoreId, Long customerId) {
        findPetStoreById(petStoreId);
        Customer customer = findCustomerById(customerId);

        if(!customer.getPetStores().contains(petStoreId)) {
            throw new IllegalStateException("Customer with ID=" + customerId + " is not owned by pet store with ID=" + petStoreId);
        }

        return new CustomerData(customer);
    }

    public EmployeeData retrieveEmployeeById(Long petStoreId, Long employeeId) {

        findPetStoreById(petStoreId);
        Employee employee = findEmployeeById(employeeId);

        if(employee.getPetStore().getPet_store_id() != petStoreId) {
            throw new IllegalStateException("Employee with ID=" + employeeId + " is not owned by pet store with ID=" + petStoreId);
        }
        return new EmployeeData(employee);
    }

}

