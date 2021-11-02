package guru.springframework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;

/**
 * Created by jt on 11/15/15.
 */
@RequestMapping("/customer")
@Controller
public class CustomerController {

	private CustomerService customerService;

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping({ "/list", "/" })
	public String listCustomers(Model model) {
		System.out.println("customerService.listAll(): " + customerService.listAll());
		model.addAttribute("customers", customerService.listAll());
		//model.addAttribute("customers", (List<Customer>)customerService.listAll());
		return "customer/list";
	}

	@GetMapping("/show/{id}")
	public String showCustomer(@PathVariable Integer id, Model model) {
		model.addAttribute("customer", customerService.getById(id));
		return "customer/show";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("customer", customerService.getById(id));
		return "customer/customerform";
	}

	@GetMapping("/new")
	public String newCustomer(Model model) {
		model.addAttribute("customer", new Customer());
		return "customer/customerform";
	}

	//@RequestMapping(method = RequestMethod.POST)
	@PostMapping()
	public String saveOrUpdate(Customer customer) {
		System.out.println("saveOrUpdate: " + customer);
		Customer newCustomer = customerService.saveOrUpdate(customer);
		System.out.println("saveOrUpdate: " + newCustomer);
		return "redirect:customer/show/" + newCustomer.getId();
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		customerService.delete(id);
		return "redirect:/customer/list";
	}
}
