package guru.springframework.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by jt on 11/14/15.
 */
@Entity
public class Customer extends AbstractDomainClass {

	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;

	@Embedded
	private Address billingAddress;

//	@Embedded
//	private Address shippingAddress;

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
	// @JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user; // The primary owner of the relationship is the user so we don't want any
						// cascade from the customer side

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

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

//	public Address getShippingAddress() {
//		return shippingAddress;
//	}
//
//	public void setShippingAddress(Address shippingAddress) {
//		this.shippingAddress = shippingAddress;
//	}

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
		return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phoneNumber="
				+ phoneNumber + ", billingAddress=" + billingAddress + ", user=" + user + ", id=" + id + ", version="
				+ version + ", dateCreated=" + dateCreated + ", lastUpdated=" + lastUpdated + "]";
	}

}
