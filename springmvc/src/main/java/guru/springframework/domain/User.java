package guru.springframework.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
public class User implements DomainObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Version
	private Integer version;

	private String username;

	@Transient
	private String password;

	private String encryptedPassword;
	private Boolean enabled = true;

	@OneToOne
	@MapsId
	// Relación one to one  bidireccional
	private Customer customer;

	/**
	 * orphanRemoval = true (Optional) Whether to apply the remove operation to entities that have been
	 * removed from the relationship and to cascade the remove operation to those
	 * entities.
	 */
	// Relación one to one bidireccional
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Cart cart;

	@Override
	public Integer getId() {
		return id;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", version=" + version + ", username=" + username + ", password=" + password
				+ ", encryptedPassword=" + encryptedPassword + ", enabled=" + enabled + "]";
	}

}