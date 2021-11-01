package guru.springframework.bootstrap;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Customer;
import guru.springframework.domain.Product;
import guru.springframework.domain.User;
import guru.springframework.services.CustomerService;
import guru.springframework.services.ProductService;

/**
 * Implementamos la interfaz para que se ejecute en este caso cuando la app
 * arranca para cargar unos datos de prueba en la BD Class raised when an
 * ApplicationContext gets initialized or refreshed.
 * 
 * ApplicationListener: Interface to be implemented by application event
 * listeners.
 */
@Component
@Profile(value = { "jpadaotest" }) // Solo se ejecuta si usamos el perfil "jpadaotest"
public class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private ProductService productService;

	private CustomerService customerService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * Handle an application event.
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		loadProducts();
		loadCustomers();
	}

	public void loadProducts() {

		Product product1 = new Product();
		product1.setDescription("Product 1");
		product1.setPrice(new BigDecimal("12.99"));
		product1.setImageUrl("http://example.com/product1");
		productService.saveOrUpdate(product1);

		Product product2 = new Product();
		product2.setDescription("Product 2");
		product2.setPrice(new BigDecimal("14.99"));
		product2.setImageUrl("http://example.com/product2");
		productService.saveOrUpdate(product2);

		Product product3 = new Product();
		product3.setDescription("Product 3");
		product3.setPrice(new BigDecimal("34.99"));
		product3.setImageUrl("http://example.com/product3");
		productService.saveOrUpdate(product3);

		Product product4 = new Product();
		product4.setDescription("Product 4");
		product4.setPrice(new BigDecimal("44.99"));
		product4.setImageUrl("http://example.com/product4");
		productService.saveOrUpdate(product4);

		Product product5 = new Product();
		product5.setDescription("Product 5");
		product5.setPrice(new BigDecimal("25.99"));
		product5.setImageUrl("http://example.com/product5");
		productService.saveOrUpdate(product5);

	}

	public void loadCustomers() {
		Customer customer1 = new Customer();
		customer1.setFirstName("Micheal");
		customer1.setLastName("Weston");
		customer1.setAddressLine1("1 Main St");
		customer1.setCity("Miami");
		customer1.setState("Florida");
		customer1.setZipCode("33101");
		customer1.setEmail("micheal@burnnotice.com");
		customer1.setPhoneNumber("305.333.0101");
		User user = new User();
		user.setUsername("This is my user name");
		user.setPassword("MyAwesomePassword");
		customer1.setUser(user);
		customerService.saveOrUpdate(customer1);

		Customer customer2 = new Customer();
		customer2.setFirstName("Fiona");
		customer2.setLastName("Glenanne");
		customer2.setAddressLine1("1 Key Biscane Ave");
		customer2.setCity("Miami");
		customer2.setState("Florida");
		customer2.setZipCode("33101");
		customer2.setEmail("fiona@burnnotice.com");
		customer2.setPhoneNumber("305.323.0233");
		user = new User();
		user.setUsername("This is my user name");
		user.setPassword("MyAwesomePassword");
		customer2.setUser(user);
		customerService.saveOrUpdate(customer2);

		Customer customer3 = new Customer();
		customer3.setFirstName("Sam");
		customer3.setLastName("Axe");
		customer3.setAddressLine1("1 Little Cuba Road");
		customer3.setCity("Miami");
		customer3.setState("Florida");
		customer3.setZipCode("33101");
		customer3.setEmail("sam@burnnotice.com");
		customer3.setPhoneNumber("305.426.9832");
		user = new User();
		user.setUsername("This is my user name");
		user.setPassword("MyAwesomePassword");
		customer3.setUser(user);
		customerService.saveOrUpdate(customer3);
	}
}