package guru.springframework.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * Created by jt on 12/15/15.
 */
@Entity
public class Cart implements DomainObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Version
	private Integer version;

	// Relación one to one bidireccional
	@OneToOne
	private User user;

	/**
	 * orphanRemoval = true (Optional) Whether to apply the remove operation to entities that have been
	 * removed from the relationship and to cascade the remove operation to those
	 * entities.
	 */
	// Relación one to many bidireccional
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", orphanRemoval = true)
	private List<CartDetail> cartDetails = new ArrayList<>();

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartDetail> getCartDetails() {
		return cartDetails;
	}

	public void setCartDetails(List<CartDetail> cartDetails) {
		this.cartDetails = cartDetails;
	}

	public void addCartDetail(CartDetail cartDetail) {
		cartDetail.setCart(this);
		cartDetails.add(cartDetail);
	}

	public void removeCartDetail(CartDetail cartDetail) {
		cartDetail.setCart(null);
		this.cartDetails.remove(cartDetail);
	}
}
