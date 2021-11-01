package guru.springframework;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import guru.springframework.controllers.ProductController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
public class SpringmvcApplicationTests {
	
	@Autowired
	ProductController productController;

	@Test
	public void contextLoads() {
		Assertions.assertThat(productController).isNotNull();
	}

}
