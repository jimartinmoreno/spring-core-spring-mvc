package guru.springframework.shutdown;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * Implementamos la interfaz para que se ejecute en este caso cuando la app
 * termina la ejecución
 * 
 * ApplicationListener: Interface to be implemented by application event
 * listeners.
 */
@Component
public class SpringShutdown implements ApplicationListener<ContextClosedEvent> {

	/**
	 * Handle an application event.
	 */
	@Override
	public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
		doSomething();

	}

	private void doSomething() {
		System.out.println(SpringShutdown.class + ".doSomething");
	}
}