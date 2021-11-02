package guru.springframework.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.User;
import guru.springframework.services.UserService;
import guru.springframework.services.security.EncryptionService;

@Service
@Profile(value = { "jpadao", "jpadaotest" })
public class UserServiceJpaDaoImpl extends AbstractJpaDaoService implements UserService {

	private EncryptionService encryptionService;

	@Autowired
	public void setEncryptionService(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}

	@Override
	public List<User> listAll() {
		EntityManager em = emf.createEntityManager();
		List<User> users = em.createQuery("from User", User.class).getResultList();
		em.close();
		return users;
	}

	@Override
	public User getById(Integer id) {
		EntityManager em = emf.createEntityManager();
		User user = em.find(User.class, id);
		em.close();
		return user;
	}

	@Override
	public User saveOrUpdate(User user) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		if (user.getPassword() != null) {
			user.setEncryptedPassword(encryptionService.encryptString(user.getPassword()));
		}

		User saveduser = em.merge(user);
		em.getTransaction().commit();
		em.close();
		return saveduser;
	}

	@Override
	public void delete(Integer id) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.remove(em.find(User.class, id));
		em.getTransaction().commit();
		em.close();
	}
}
