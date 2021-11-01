package guru.springframework.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;

/**
 * @Service Indicates that an annotated class is a "Service"
 */
@Service
@Profile(value = { "jpadao", "jpadaotest" })
public class ProductServiceJpaDaoImpl implements ProductService {
	/**
	 * @PersistenceUnit Expresses a dependency on an EntityManagerFactory and
	 *                  itsassociated persistence unit.
	 */
	@PersistenceUnit
	// Thread Safe Interface used to interact with the persistence unit.
	private EntityManagerFactory emf;

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public List<Product> listAll() {
		// Se crea uno por cada thread. No es thread safe
		EntityManager em = emf.createEntityManager();
		List<Product> prodList = em.createQuery("from Product", Product.class).getResultList();
		em.close();
		return prodList;
	}

	@Override
	public Product getById(Integer id) {
		EntityManager em = emf.createEntityManager();
		Product p = em.find(Product.class, id);
		em.close();
		return p;
	}

	@Override
	public Product saveOrUpdate(Product domainObject) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Product savedProduct = em.merge(domainObject);
		em.getTransaction().commit();
		em.close();
		return savedProduct;
	}

	@Override
	public void delete(Integer id) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.remove(em.find(Product.class, id));
		em.getTransaction().commit();
		em.close();
	}
}