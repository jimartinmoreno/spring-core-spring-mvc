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
import guru.springframework.domain.Cart;
import guru.springframework.domain.CartDetail;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Product;
import guru.springframework.domain.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { JpaIntegrationConfig.class })
@Profile(value = { "jpadaotest", "jpadao" }) // Se ejecuta si usamos el perfil "jpadaotest" y "jpadao"
class UserServiceJpaDaoImplTest {

	//private UserService userService;
	private ProductService productService;

	private CustomerService customerService;

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	// @Autowired
	// public void setUserService(UserService userService) {
	// this.userService = userService;
	// }

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Test
	void testSaveOfUser() throws Exception {
		
		User user = new User();

		user.setUsername("someusername");
		user.setPassword("myPassword");
		
		Customer customer = new Customer();
		customer.setFirstName("Chevy");
		customer.setLastName("Chase");

		customer.setUser(user);

		Customer customerdUser = customerService.saveOrUpdate(customer);
		

		assert customerdUser.getUser().getId() != null;
		assertThat(customerdUser.getUser().getId()).isNotNull();
		assert customerdUser.getUser().getEncryptedPassword() != null;
		assertThat(customerdUser.getUser().getEncryptedPassword()).isNotNull();

		System.out.println("Encrypted Password");
		System.out.println(customerdUser.getUser().getEncryptedPassword());

		customerService.delete(customerdUser.getId());
	}

	@Test
	void testSaveOfUserWithCustomer() throws Exception {

		User user = new User();

		user.setUsername("someusername");
		user.setPassword("myPassword");

		Customer customer = new Customer();
		customer.setFirstName("Chevy");
		customer.setLastName("Chase");

		customer.setUser(user);

		Customer customerdUser = customerService.saveOrUpdate(customer);

		assert customerdUser.getId() != null;
		assertThat(customerdUser.getId()).isNotNull();
		assert customerdUser.getVersion() != null;
		assertThat(customerdUser.getVersion()).isNotNull();
		assert customerdUser.getUser() != null;
		assertThat(customerdUser.getUser()).isNotNull();
		assert customerdUser.getUser().getId() != null;
		assertThat(customerdUser.getUser().getId()).isNotNull();

		customerService.delete(customerdUser.getId());

	}

	@Test
	void testAddCartToUser() throws Exception {
		User user = new User();

		user.setUsername("someusername");
		user.setPassword("myPassword");
		user.setCart(new Cart());
		
		Customer customer = new Customer();
		customer.setFirstName("Chevy");
		customer.setLastName("Chase");

		customer.setUser(user);

		Customer customerdUser = customerService.saveOrUpdate(customer);

		assert customerdUser.getUser().getId() != null;
		assertThat(customerdUser.getUser().getId()).isNotNull();
		assert customerdUser.getUser().getVersion() != null;
		assertThat(customerdUser.getUser().getVersion()).isNotNull();
		assert customerdUser.getUser().getCart() != null;
		assertThat(customerdUser.getUser().getCart()).isNotNull();
		assert customerdUser.getUser().getCart().getId() != null;
		assertThat(customerdUser.getUser().getCart().getId()).isNotNull();
		
		customerService.delete(customerdUser.getId());
	}

	@Test
	void testAddCartToUserWithCartDetails() throws Exception {
		User user = new User();

		user.setUsername("someusername");
		user.setPassword("myPassword");
		
		user.setCart(new Cart());

		List<Product> storedProducts = (List<Product>) productService.listAll();

		CartDetail cartItemOne = new CartDetail();
		cartItemOne.setProduct(storedProducts.get(0));
		user.getCart().addCartDetail(cartItemOne);

		CartDetail cartItemTwo = new CartDetail();
		cartItemTwo.setProduct(storedProducts.get(1));
		user.getCart().addCartDetail(cartItemTwo);

		
		Customer customer = new Customer();
		customer.setFirstName("Chevy");
		customer.setLastName("Chase");

		customer.setUser(user);

		Customer customerdUser = customerService.saveOrUpdate(customer);
		

		assert customerdUser.getUser().getId() != null;
		assertThat(customerdUser.getUser().getId()).isNotNull();
		assert customerdUser.getUser().getVersion() != null;
		assertThat(customerdUser.getUser().getVersion()).isNotNull();
		assert customerdUser.getUser().getCart() != null;
		assertThat(customerdUser.getUser().getCart()).isNotNull();
		assert customerdUser.getUser().getCart().getId() != null;
		assertThat(customerdUser.getUser().getCart().getId()).isNotNull();
		assert customerdUser.getUser().getCart().getCartDetails().size() == 2;
		assertThat(customerdUser.getUser().getCart().getCartDetails()).size().isEqualTo(2);
		
		customerService.delete(customerdUser.getId());
	}

	@Test
	void testAddAndRemoveCartToUserWithCartDetails() throws Exception {
		User user = new User();

		user.setUsername("someusername");
		user.setPassword("myPassword");
		
		user.setCart(new Cart());

		List<Product> storedProducts = (List<Product>) productService.listAll();

		CartDetail cartItemOne = new CartDetail();
		cartItemOne.setProduct(storedProducts.get(0));
		user.getCart().addCartDetail(cartItemOne);

		CartDetail cartItemTwo = new CartDetail();
		cartItemTwo.setProduct(storedProducts.get(1));
		user.getCart().addCartDetail(cartItemTwo);

		
		Customer customer = new Customer();
		customer.setFirstName("Chevy");
		customer.setLastName("Chase");

		customer.setUser(user);

		Customer customerdUser = customerService.saveOrUpdate(customer);

		assert customerdUser.getUser().getCart().getCartDetails().size() == 2;
		assertThat(customerdUser.getUser().getCart().getCartDetails()).size().isEqualTo(2);

		customerdUser.getUser().getCart().removeCartDetail(customerdUser.getUser().getCart().getCartDetails().get(0));

		customerService.saveOrUpdate(customerdUser);

		assert customerdUser.getUser().getCart().getCartDetails().size() == 1;
		assertThat(customerdUser.getUser().getCart().getCartDetails()).size().isEqualTo(1);
		
		customerService.delete(customerdUser.getId());
	}
}
