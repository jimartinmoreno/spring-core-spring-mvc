package guru.springframework.controller;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import guru.springframework.controllers.ProductController;
import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;

/**
 * Test con contexto del ProductController que utiliza un mock de ProductService
 * 
 * @author jimar
 *
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerIntegrationTest {

	@MockBean // Mockito Mock object
	private ProductService productService;

	@Autowired
	private MockMvc mockMvc;

	private Integer id = 1;
	private String description = "Test Description";
	private BigDecimal price = new BigDecimal("12.00");
	private String imageUrl = "example.com";

	@BeforeEach
	public void setup() {
		System.out.println("------- @BeforeEach --------");
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

		mockMvc.perform(get("/product/show/1")).andExpect(status().isOk()).andExpect(view().name("product/show"))
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
		// Tell Mockito stub to return new product for ID 1
		when(productService.getById(id)).thenReturn(returnProduct);

		mockMvc.perform(post("/product").param("id", String.valueOf(id)).param("description", description)
				.param("price", price.toString()).param("imageUrl", imageUrl)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/product/show/1"));

		/**
		 * ArgumentCaptor<Product>: Use it to capture argument values for further
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
