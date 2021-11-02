package guru.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//@SpringBootApplication(scanBasePackages={"guru.springframework"})
public class SpringmvcApplication {
	
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(SpringmvcApplication.class, args);
		System.out.println("Beans ****");
		// Arrays.asList(context.getBeanDefinitionNames()).forEach(System.out::println);
		// System.out.println("Beans ****");
		System.out.println("count: " + context.getBeanDefinitionCount());

	}
	
	 private static void checkBeansPresence(String... beans) {
	        for (String beanName : beans) {
	            System.out.println("Is " + beanName + " in ApplicationContext: " + 
	              context.containsBean(beanName));
	        }
	    }
}
