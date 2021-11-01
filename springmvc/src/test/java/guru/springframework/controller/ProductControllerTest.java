package guru.springframework.controller;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.controllers.ProductController;
import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;

/**
 * To test de controllers
 * @author jimar
 *
 */

class ProductControllerTest {

	@Mock // Mockito Mock object
	private ProductService productService;

	@InjectMocks // setups up controller, and injects mock objects into it.
	private ProductController productController;

	//Main entry point for server-side Spring MVC test support. 
	private MockMvc mockMvc;

	private Integer id = 1;
	private String description = "Test Description";
	private BigDecimal price = new BigDecimal("12.00");
	private String imageUrl = "example.com";

	@BeforeEach
	public void setup() {
		System.out.println("------- Init setup --------");
		/**
		 * @MockitoAnnotations.openMocks (this) -> initializes fields annotated with
		 *                               Mockito annotations.See also MockitoSession
		 *                               which not only initializes mocksbut also adds
		 *                               extra validation for cleaner tests!
		 * 
		 *                               Allows shorthand creation of objects required
		 *                               for testing. Minimizes repetitive mock creation
		 *                               code. Makes the test class more readable. Makes
		 *                               the verification error easier to read because
		 *                               field name is used to identify the mock.
		 * 
		 */
		MockitoAnnotations.openMocks(this); // initilizes controller and mocks

		/**
		 * @MockMvcBuilders.standaloneSetup: Build a MockMvc instance by registering one
		 *                                   or more @Controller instances and
		 *                                   configuring Spring MVC
		 *                                   infrastructureprogrammatically.
		 */
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
		System.out.println("------- End setup ---------");
	}

	@AfterEach
	public void afterEach() {
		System.out.println("------- @AfterEach --------");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@DisplayName("testList")
	void testList() throws Exception {

		List<Product> products = new ArrayList<>();
		products.add(new Product());
		products.add(new Product());

		// specific Mockito interaction, tell stub to return list of products
		when(productService.listAll()).thenReturn((List) products); // need to strip generics to keep Mockito happy.

		mockMvc.perform(get("/product/list")).andExpect(status().isOk()).andExpect(view().name("product/list"))
				.andExpect(model().attribute("products", hasSize(2)));

	}

	@Test
	void testShow() throws Exception {

		Product returnProduct = new Product();
		returnProduct.setId(id);
		returnProduct.setDescription(description);
		returnProduct.setPrice(price);
		returnProduct.setImageUrl(imageUrl);

		// Tell Mockito stub to return new product for ID 1
		when(productService.getById(id)).thenReturn(returnProduct);

		mockMvc.perform(get("/product/show/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("product/show"))
				.andExpect(model().attribute("product", instanceOf(Product.class)))
				.andExpect(model().attribute("product", hasProperty("id", is(id))))
				.andExpect(model().attribute("product", hasProperty("description", is(description))))
				.andExpect(model().attribute("product", hasProperty("price", is(price))))
				.andExpect(model().attribute("product", hasProperty("imageUrl", is(imageUrl))));
	}

	@Test
	void testEdit() throws Exception {
		Product returnProduct = new Product();
		returnProduct.setId(id);
		returnProduct.setDescription(description);
		returnProduct.setPrice(price);
		returnProduct.setImageUrl(imageUrl);

		// Tell Mockito stub to return new product for ID 1
		when(productService.getById(id)).thenReturn(returnProduct);

		mockMvc.perform(get("/product/edit/1")).andExpect(status().isOk()).andExpect(view().name("product/productform"))
				.andExpect(model().attribute("product", instanceOf(Product.class)))
				.andExpect(model().attribute("product", hasProperty("id", is(id))))
				.andExpect(model().attribute("product", hasProperty("description", is(description))))
				.andExpect(model().attribute("product", hasProperty("price", is(price))))
				.andExpect(model().attribute("product", hasProperty("imageUrl", is(imageUrl))));
	}

	@Test
	void testNewProduct() throws Exception {
		// should not call service
		verifyNoInteractions(productService);

		mockMvc.perform(get("/product/new")).andExpect(status().isOk()).andExpect(view().name("product/productform"))
				.andExpect(model().attribute("product", instanceOf(Product.class)));
	}

	@Test
	void testSaveOrUpdate() throws Exception {

		Product returnProduct = new Product();
		returnProduct.setId(id);
		returnProduct.setDescription(description);
		returnProduct.setPrice(price);
		returnProduct.setImageUrl(imageUrl);

		/**
		 * Enables stubbing methods. Use it when you want the mock to return particular
		 * value when particular method is called.
		 */
		when(productService.saveOrUpdate(any(Product.class))).thenReturn(returnProduct);

		mockMvc.perform(post("/product").param("id", String.valueOf(id)).param("description", description)
				.param("price", price.toString()).param("imageUrl", imageUrl))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/product/show/1"))
				//.andExpect(content().contentType(MediaType.TEXT_HTML))
				.andExpect(model().attribute("product", instanceOf(Product.class)))
				.andExpect(model().attribute("product", hasProperty("id", is(id))))
				.andExpect(model().attribute("product", hasProperty("description", is(description))))
				.andExpect(model().attribute("product", hasProperty("price", is(price))))
				.andExpect(model().attribute("product", hasProperty("imageUrl", is(imageUrl))))
				.andExpect(model().attribute("product", hasProperty("imageUrl", equalTo(imageUrl))));

		
		/**
		 * ArgumentCaptor<Product>:  Use it to capture argument values for further 
		 * assertions.
		 * 
		 * Mockito verifies argument values in natural java style: by using an equals()
		 * method.This is also the recommended way of matching arguments because it
		 * makes tests clean & simple.In some situations though, it is helpful to assert
		 * on certain arguments after the actual verification.
		 * 
		 */
		ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
		verify(productService).saveOrUpdate(boundProduct.capture());
		

		assertEquals(id, boundProduct.getValue().getId());
		assertEquals(description, boundProduct.getValue().getDescription());
		assertEquals(price, boundProduct.getValue().getPrice());
		assertEquals(imageUrl, boundProduct.getValue().getImageUrl());
	}

	@Test
	void testDelete() throws Exception {
		Integer id = 1;

		mockMvc.perform(get("/product/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/product/list"));

		verify(productService, times(1)).delete(id);
	}

}
