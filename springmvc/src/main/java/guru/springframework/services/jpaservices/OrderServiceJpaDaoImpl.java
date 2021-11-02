package guru.springframework.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.Order;
import guru.springframework.services.OrderService;

/**
 * Created by jt on 12/16/15.
 */
@Service
@Profile(value = { "jpadao", "jpadaotest" })
public class OrderServiceJpaDaoImpl extends AbstractJpaDaoService implements OrderService {

	@Override
	public List<Order> listAll() {
		EntityManager em = emf.createEntityManager();
		List<Order> orderList = em.createQuery("from Order", Order.class).getResultList();
		em.close();
		return orderList;
	}

	@Override
	public Order getById(Integer id) {
		EntityManager em = emf.createEntityManager();
		Order order = em.find(Order.class, id);
		em.close();
		return order;
	}

	@Override
	public Order saveOrUpdate(Order domainObject) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		Order savedProduct = em.merge(domainObject);
		em.getTransaction().commit();
		em.close();
		return savedProduct;
	}

	@Override
	public void delete(Integer id) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.remove(em.find(Order.class, id));
		em.getTransaction().commit();
		em.close();
	}
}
