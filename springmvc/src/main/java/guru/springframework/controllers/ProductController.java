package guru.springframework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;

/**
 * Created by jt on 11/6/15.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

	private ProductService productService;

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/list")
	public String listProducts(Model model) {
		model.addAttribute("products", productService.listAll());
		return "product/list";
	}

	@GetMapping("/show/{id}")
	public String getProduct(@PathVariable Integer id, Model model) {
		model.addAttribute("product", productService.getById(id));
		return "product/show";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("product", productService.getById(id));
		return "product/productform";
	}

	@GetMapping("/new")
	public String newProduct(Model model) {
		model.addAttribute("product", new Product());
		return "product/productform";
	}

	//@RequestMapping(value = "", method = RequestMethod.POST)
	@PostMapping()
	public String saveOrUpdateProduct(Product product) {
		System.out.println("saveOrUpdateProduct: " + product);
		Product savedProduct = productService.saveOrUpdate(product);
		return "redirect:/product/show/" + savedProduct.getId();
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		
		productService.delete(id);
		return "redirect:/product/list";
	}
}
