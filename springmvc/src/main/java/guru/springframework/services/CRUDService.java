package guru.springframework.services;

import java.util.List;

import guru.springframework.domain.DomainObject;

/**
 * Created by jt on 11/14/15.
 */
public interface CRUDService<T> {
	
	List<? extends DomainObject> listAll();

	T getById(Integer id);

	T saveOrUpdate(T domainObject);

	void delete(Integer id);
}
