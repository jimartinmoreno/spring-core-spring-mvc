package guru.springframework.bootstrap;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Address;
import guru.springframework.domain.Cart;
import guru.springframework.domain.CartDetail;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Order;
import guru.springframework.domain.OrderDetail;
import guru.springframework.domain.Product;
import guru.springframework.domain.User;
import guru.springframework.enums.OrderStatus;
import guru.springframework.services.CustomerService;
import guru.springframework.services.OrderService;
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

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderService orderService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * Handle an application event.
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		loadProducts();
		loadCustomers();
		loadCarts();
        loadOrderHistory();
	}

	private void loadOrderHistory() {
		List<Customer> customers = (List<Customer>) customerService.listAll();
		List<Product> products = (List<Product>) productService.listAll();

		customers.forEach(customer -> {
			Order order = new Order();
			order.setCustomer(customer);
			order.setOrderStatus(OrderStatus.SHIPPED);

			products.forEach(product -> {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setProduct(product);
				orderDetail.setQuantity(1);
				order.addToOrderDetails(orderDetail);
			});
			orderService.saveOrUpdate(order);
		});
	}

	private void loadCarts() {
		List<Customer> customers = (List<Customer>) customerService.listAll();
		List<Product> products = (List<Product>) productService.listAll();

		
		customers.forEach(customer -> {
			customer.getUser().setCart(new Cart());
			
			CartDetail cartDetail = new CartDetail();
			cartDetail.setProduct(products.get(0));
			cartDetail.setQuantity(2);
			
			customer.getUser().getCart().addCartDetail(cartDetail);
			
			customerService.saveOrUpdate(customer);
		});
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
		customer1.setBillingAddress(new Address());
		customer1.getBillingAddress().setAddressLine1("1 Main St");
		customer1.getBillingAddress().setCity("Miami");
		customer1.getBillingAddress().setState("Florida");
		customer1.getBillingAddress().setZipCode("33101");
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
		customer2.setBillingAddress(new Address());
		customer2.getBillingAddress().setAddressLine1("1 Main St");
		customer2.getBillingAddress().setCity("Miami");
		customer2.getBillingAddress().setState("Florida");
		customer2.getBillingAddress().setZipCode("33101");
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
		customer3.setBillingAddress(new Address());
		customer3.getBillingAddress().setAddressLine1("1 Little Cuba Road");
		customer3.getBillingAddress().setCity("Miami");
		customer3.getBillingAddress().setState("Florida");
		customer3.getBillingAddress().setZipCode("33101");
		customer3.setEmail("sam@burnnotice.com");
		customer3.setPhoneNumber("305.426.9832");
		user = new User();
		user.setUsername("This is my user name");
		user.setPassword("MyAwesomePassword");
		customer3.setUser(user);
		customerService.saveOrUpdate(customer3);
	}
}