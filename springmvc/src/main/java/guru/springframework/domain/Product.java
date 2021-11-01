package guru.springframework.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * Created by jt on 11/6/15.
 */
@Entity
public class Product implements DomainObject {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/**
	 * @Version Specifies the version field or property of an entity class
	 *          thatserves as its optimistic lock value. The version is used to
	 *          ensureintegrity when performing the merge operation and for
	 *          optimisticconcurrency control.
	 */
	@Version
	private Integer version;

	private String description;
	private BigDecimal price;
	private String imageUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", version=" + version + ", description=" + description + ", price=" + price
				+ ", imageUrl=" + imageUrl + "]";
	}
}
