package guru.springframework.services.jpaservices;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Created by jt on 12/14/15.
 */
public class AbstractJpaDaoService {

    protected EntityManagerFactory emf;
    /**
	 * @PersistenceUnit Expresses a dependency on an EntityManagerFactory and
	 *                  itsassociated persistence unit.
	 */
	@PersistenceUnit
	// Thread Safe Interface used to interact with the persistence unit.
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }
}
