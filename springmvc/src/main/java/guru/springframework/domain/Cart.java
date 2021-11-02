package guru.springframework.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Created by jt on 12/15/15.
 */
@Entity
public class Cart extends AbstractDomainClass {

	// Relación one to one bidireccional
	@OneToOne
	private User user;

	/**
	 * orphanRemoval = true (Optional) Whether to apply the remove operation to entities that have been
	 * removed from the relationship and to cascade the remove operation to those
	 * entities.
	 */
	// Relación one to many bidireccional
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<CartDetail> cartDetails = new ArrayList<>();

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

	@Override
	public String toString() {
		return "Cart [cartDetails=" + cartDetails + ", id=" + id + ", version=" + version + ", dateCreated="
				+ dateCreated + ", lastUpdated=" + lastUpdated + "]";
	}
}
