package guru.springframework.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.Customer;
import guru.springframework.domain.User;
import guru.springframework.services.CustomerService;
import guru.springframework.services.security.EncryptionService;

@Service
@Profile(value = { "jpadao", "jpadaotest" })
public class CustomerServiceJPADaoImpl extends AbstractJpaDaoService implements CustomerService {
	
	private EncryptionService encryptionService;

	@Autowired
	public void setEncryptionService(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}

	@Override
	public List<Customer> listAll() {
		EntityManager em = emf.createEntityManager();
		List<Customer> custList = em.createQuery("from Customer", Customer.class).getResultList();
		em.close();

		// Predicate<Customer> p1 = (cust) -> cust.getPhoneNumber() != null;
		//
		// custList.stream().filter(cust -> cust.getPhoneNumber() !=
		// null).collect(Collectors.toMap(Customer::getId, cust -> cust));
		// custList.stream().filter(cust -> cust.getPhoneNumber() !=
		// null).collect(Collectors.toMap(Customer::getId, Function.identity()));

		return custList;
	}

	@Override
	public Customer getById(Integer id) {
		EntityManager em = emf.createEntityManager();
		Customer customer = em.find(Customer.class, id);
		em.close();
		return customer;
	}

	@Override
	public Customer saveOrUpdate(Customer customer) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		User user = customer.getUser();
		if (user != null && user.getPassword() != null) {
			user.setEncryptedPassword(encryptionService.encryptString(user.getPassword()));
		}
		Customer savedCustomer = em.merge(customer);
		em.getTransaction().commit();
		em.close();
		return savedCustomer;
	}

	@Override
	public void delete(Integer id) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.remove(em.find(Customer.class, id));
		em.getTransaction().commit();
		em.close();
	}
}