package guru.springframework.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public interface MyControllers {
	
	@GetMapping("/list")
	public String listProducts(Model model);

}
