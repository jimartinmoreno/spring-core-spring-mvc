package guru.springframework.config;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @EnableAutoConfiguration Enable auto-configuration of the Spring Application
 *                          Context, attempting to guess and configure beans
 *                          that you are likely to need. Auto-configuration
 *                          classes are usually applied based on your classpath
 *                          and what beans you have defined.
 * 
 * @Configuration Indicates that a class declares one or more @Bean methods and
 *                may be processed by the Spring container to generate bean
 *                definitions and service requests for those beans at runtime
 * 
 * 
 * @ComponentScan to define specific packages to scan. If specific packages are
 *                not defined, scanning will occur from the package of the class
 *                that declares this annotation.
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan("guru.springframework") // Lo usamos porque no estamos en el paquete raiz del que cuelgan todos los
										// beans
public class JpaIntegrationConfig {
}
