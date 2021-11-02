package guru.springframework.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created by jt on 12/15/15.
 */
@Entity
public class CartDetail extends AbstractDomainClass {

	// Relación many to one bidireccional
	@ManyToOne
	private Cart cart;

	// Relación one to one unidireccional
	@OneToOne
	private Product product;

	private Integer quantity;

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartDetail [product=" + product + ", quantity=" + quantity + ", id=" + id
				+ ", version=" + version + ", dateCreated=" + dateCreated + ", lastUpdated=" + lastUpdated + "]";
	}
}
