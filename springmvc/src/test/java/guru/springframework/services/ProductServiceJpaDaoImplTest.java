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
import guru.springframework.domain.Product;

/**
 * To test the ProductServiceJpaDaoImpl
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { JpaIntegrationConfig.class })
@Profile(value = { "jpadaotest", "jpadao" }) // Se ejecuta si usamos el perfil "jpadaotest" y "jpadao"
public class ProductServiceJpaDaoImplTest {

	private ProductService productService;

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Test
	public void testListMethod() throws Exception {
		List<Product> products = (List<Product>) productService.listAll();
		System.out.println("testListMethod: " + products);
		assert products.size() == 5;
		assertThat(products).size().isEqualTo(5);
	}
}