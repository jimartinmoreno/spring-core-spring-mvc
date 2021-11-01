package guru.springframework.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * Created by jt on 11/14/15.
 */
@Entity
public class Customer implements DomainObject {
	@Id
	/**
	 * @GeneratedValue Provides for the specification of generation strategies for
	 *                 the values of primary keys.
	 */
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Version
	private Integer version;

	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipCode;

	/**
	 * @OneToOne Specifies a single-valued association to another entity that has
	 *           one-to-one multiplicity. It is not normally necessary to specify
	 *           the associated target entity explicitly since it can usually be
	 *           inferred from the type of the object being referenced.
	 * 
	 *           If the relationship is bidirectional, the non-owning side must use
	 *           the mappedBy element of the OneToOne annotation to specify the
	 *           relationship field or property of the owning side.
	 * 
	 *           CascadeType.ALL: Defines the set of cascadable operations that are
	 *           propagated to the associated entity.The value cascade=ALL is
	 *           equivalent to cascade={PERSIST, MERGE, REMOVE, REFRESH, DETACH}.
	 */
	// Relación one to one bidireccional
	@OneToOne(mappedBy = "customer", cascade = { CascadeType.ALL }, orphanRemoval = true)
	//@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user; // The primary owner of the relationship is the user so we don't want any
						// cascade from the customer side

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		user.setCustomer(this);
	}
	
	public void removeUser(User user) {
        if (user != null) {
        	user.setCustomer(null);
        }
        this.user = null;
    }

	@Override
	public String toString() {
		return "Customer [id=" + id + ", version=" + version + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", phoneNumber=" + phoneNumber + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode
				+ "]";
	}

}
