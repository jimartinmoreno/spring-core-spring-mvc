package guru.springframework.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

/**
 * Created by jt on 12/16/15.
 */
@MappedSuperclass
public class AbstractDomainClass implements DomainObject {

	/**
	 * @GeneratedValue Provides for the specification of generation strategies for
	 *                 the values of primary keys.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	/**
	 * @Version Specifies the version field or property of an entity class
	 *          thatserves as its optimistic lock value. The version is used to
	 *          ensureintegrity when performing the merge operation and for
	 *          optimisticconcurrency control.
	 */
	@Version
	Integer version;

	Date dateCreated;
	Date lastUpdated;

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	@PreUpdate
	@PrePersist
	public void updateTimeStamps() {
		lastUpdated = new Date();
		if (dateCreated == null) {
			dateCreated = new Date();
		}
	}
}
