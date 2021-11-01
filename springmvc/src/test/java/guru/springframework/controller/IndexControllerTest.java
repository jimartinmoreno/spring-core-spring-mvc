package guru.springframework.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.controllers.IndexController;

class IndexControllerTest {
	
	//Main entry point for server-side Spring MVC test support. 
	private MockMvc mockMvc;

	private IndexController indexController;

	@BeforeEach
	public void setUp() {
		indexController = new IndexController();
		mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
	}

	@Test
	public void testIndex() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

}
