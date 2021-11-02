package guru.springframework.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.controllers.CustomerController;
import guru.springframework.domain.Address;
import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;

class CustomerControllerTest {

	@Mock // Mockito Mock object
	private CustomerService customerService;

	@InjectMocks // setups up controller, and injects mock objects into it.
	private CustomerController customerController;

	// Main entry point for server-side Spring MVC test support.
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		System.out.println("------- Init setup --------");
		MockitoAnnotations.openMocks(this); // initilizes controller and mocks
		/**
		 * @MockMvcBuilders.standaloneSetup: Build a MockMvc instance by registering one
		 *                                   or more @Controller instances and
		 *                                   configuring Spring MVC
		 *                                   infrastructureprogrammatically.
		 */
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
		System.out.println("------- End setup ---------");
	}

	@AfterEach
	public void afterEach() {
		System.out.println("------- @AfterEach --------");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void testList() throws Exception {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer());
		customers.add(new Customer());

		when(customerService.listAll()).thenReturn((List) customers);

		mockMvc.perform(get("/customer/list")).andExpect(status().isOk()).andExpect(view().name("customer/list"))
				.andExpect(model().attribute("customers", hasSize(2)));
	}

	@Test
	void testShow() throws Exception {
		Integer id = 1;

		when(customerService.getById(id)).thenReturn(new Customer());

		mockMvc.perform(get("/customer/show/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("customer/show"))
			.andExpect(model().attribute("customer", instanceOf(Customer.class)));
	}

	@Test
	void testEdit() throws Exception {
		Integer id = 1;

		when(customerService.getById(id)).thenReturn(new Customer());

		mockMvc.perform(get("/customer/edit/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("customer/customerform"))
			.andExpect(model().attribute("customer", instanceOf(Customer.class)));
	}

	@Test
	void testNewCustomer() throws Exception {
		verifyNoInteractions(customerService);

		mockMvc.perform(get("/customer/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("customer/customerform"))
			.andExpect(model().attribute("customer", instanceOf(Customer.class)));
	}

	@Test
	void testSaveOrUpdate() throws Exception {
		Integer id = 1;
		Customer returnCustomer = new Customer();
		String firstName = "Micheal";
		String lastName = "Weston";
		String addressLine1 = "1 Main St";
		String addressLine2 = "Apt 301";
		String city = "Miami";
		String state = "Florida";
		String zipCode = "33101";
		String email = "micheal@burnnotice.com";
		String phoneNumber = "305.333.0101";

		returnCustomer.setId(id);
		returnCustomer.setFirstName(firstName);
		returnCustomer.setLastName(lastName);
		returnCustomer.setBillingAddress(new Address());
		returnCustomer.getBillingAddress().setAddressLine1(addressLine1);
		returnCustomer.getBillingAddress().setAddressLine2(addressLine2);
		returnCustomer.getBillingAddress().setCity(city);
		returnCustomer.getBillingAddress().setState(state);
		returnCustomer.getBillingAddress().setZipCode(zipCode);
		returnCustomer.setEmail(email);
		returnCustomer.setPhoneNumber(phoneNumber);

		when(customerService.saveOrUpdate(any(Customer.class))).thenReturn(returnCustomer);

		mockMvc.perform(post("/customer")
				.param("id", "1")
				.param("firstName", firstName)
				.param("lastName", lastName)
				.param("billingAddress.addressLine1", addressLine1)
				.param("billingAddress.addressLine2", addressLine2)
				.param("billingAddress.city", city)
				.param("billingAddress.state", state)
				.param("billingAddress.zipCode", zipCode)
				.param("email", email)
				.param("phoneNumber", phoneNumber))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:customer/show/1"))
				.andExpect(model().attribute("customer", instanceOf(Customer.class)))
				.andExpect(model().attribute("customer", hasProperty("firstName", is(firstName))))
				.andExpect(model().attribute("customer", hasProperty("lastName", is(lastName))))
				.andExpect(model().attribute("customer", hasProperty("billingAddress", notNullValue())))
				.andExpect(model().attribute("customer", hasProperty("billingAddress", hasProperty("addressLine1", equalTo(addressLine1)))))
                .andExpect(model().attribute("customer", hasProperty("billingAddress", hasProperty("addressLine2", is(addressLine2)))))
                .andExpect(model().attribute("customer", hasProperty("billingAddress", hasProperty("city", is(city)))))
                .andExpect(model().attribute("customer", hasProperty("billingAddress", hasProperty("state", is(state)))))
                .andExpect(model().attribute("customer", hasProperty("billingAddress", hasProperty("zipCode", is(zipCode)))))
				.andExpect(model().attribute("customer", hasProperty("email", is(email))))
				.andExpect(model().attribute("customer", hasProperty("phoneNumber", is(phoneNumber))));

		System.out.println(model());
		/**
		 * Intercepta el objecto que se le pasa al controller en este test es
		 * "returnCustomer"
		 */
		ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
		verify(customerService).saveOrUpdate(customerCaptor.capture());

		Customer boundCustomer = customerCaptor.getValue();

		assertEquals(id, boundCustomer.getId());
		assertEquals(firstName, boundCustomer.getFirstName());
		assertEquals(lastName, boundCustomer.getLastName());
		assertEquals(addressLine1, boundCustomer.getBillingAddress().getAddressLine1());
		assertEquals(addressLine2, boundCustomer.getBillingAddress().getAddressLine2());
		assertEquals(city, boundCustomer.getBillingAddress().getCity());
		assertEquals(state, boundCustomer.getBillingAddress().getState());
		assertEquals(zipCode, boundCustomer.getBillingAddress().getZipCode());
		assertEquals(email, boundCustomer.getEmail());
		assertEquals(phoneNumber, boundCustomer.getPhoneNumber());
	}
}
