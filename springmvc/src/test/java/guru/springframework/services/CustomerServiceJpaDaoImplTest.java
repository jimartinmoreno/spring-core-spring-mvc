package guru.springframework.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Address;
import guru.springframework.domain.Customer;
import guru.springframework.domain.User;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { JpaIntegrationConfig.class })
@Profile(value = { "jpadaotest", "jpadao" }) // Se ejecuta si usamos el perfil "jpadaotest" y "jpadao"
public class CustomerServiceJpaDaoImplTest {

	private CustomerService customerService;

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Test
	public void testListMethod() throws Exception {
		List<Customer> customers = (List<Customer>) customerService.listAll();
		customers.forEach(System.out::println);
		assert customers.size() == 3;
		assertThat(customers).size().isEqualTo(3);
	}
	
	@Test
    public void testSaveWithUser() {

		Customer customer1 = new Customer();
		customer1.setFirstName("Nacho");
		customer1.setLastName("Martin");
		customer1.setBillingAddress(new Address());
		customer1.getBillingAddress().setAddressLine1("1 Main St");
		customer1.getBillingAddress().setCity("Miami");
		customer1.getBillingAddress().setState("Florida");
		customer1.getBillingAddress().setZipCode("33101");
		customer1.setPhoneNumber("305.333.0101");
        User user = new User();
        user.setUsername("This is my user name");
        user.setPassword("MyAwesomePassword");
        customer1.setUser(user);
        Customer savedCustomer = customerService.saveOrUpdate(customer1);
        assert savedCustomer.getUser().getId() != null;
        assertThat(savedCustomer.getUser().getId()).isNotNull();
        assertThat(savedCustomer.getBillingAddress()).isNotNull();
        
		customerService.delete(savedCustomer.getId());
		List<Customer> customers = (List<Customer>) customerService.listAll();
		customers.forEach(System.out::println);
		assert customers.size() == 3;
		assertThat(customers).size().isEqualTo(3);
    }
}
