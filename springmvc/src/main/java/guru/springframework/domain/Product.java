package guru.springframework.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;

/**
 * Created by jt on 11/6/15.
 */
@Entity
public class Product extends AbstractDomainClass {

	private String description;
	private BigDecimal price;
	private String imageUrl;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "Product [description=" + description + ", price=" + price + ", imageUrl=" + imageUrl + ", id=" + id
				+ ", version=" + version + ", dateCreated=" + dateCreated + ", lastUpdated=" + lastUpdated + "]";
	}

}
