package guru.springframework.services.mapservices;

import guru.springframework.domain.User;
import guru.springframework.services.UserService;

/**
 * Created by jt on 12/14/15.
 */
public class UserServiceMapImpl extends AbstractMapService implements UserService {


	@Override
	public User getById(Integer id) {
		return (User) super.getById(id);
	}

	@Override
	public User saveOrUpdate(User domainObject) {
		return (User) super.saveOrUpdate(domainObject);
	}

	@Override
	protected void loadDomainObjects() {
		// TODO Auto-generated method stub
		
	}
}