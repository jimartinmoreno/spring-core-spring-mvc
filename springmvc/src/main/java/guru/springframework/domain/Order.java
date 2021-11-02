package guru.springframework.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import guru.springframework.enums.OrderStatus;

/**
 * Created by jt on 12/16/15.
 */
@Entity
@Table(name = "ORDER_HEADER")
public class Order extends AbstractDomainClass {

	private OrderStatus orderStatus;
	private Date dateShipped;

	// Relación one to one Unidireccional
	@OneToOne
	private Customer customer;

	@Embedded
	private Address shipToAddress;

	// Relación one to one Bidireccional
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
	private List<OrderDetail> orderDetails = new ArrayList<>();
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getShipToAddress() {
		return shipToAddress;
	}

	public void setShipToAddress(Address shipToAddress) {
		this.shipToAddress = shipToAddress;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public void addToOrderDetails(OrderDetail orderDetail) {
		orderDetail.setOrder(this);
		orderDetails.add(orderDetail);
	}

	public void removeOrderDetail(OrderDetail orderDetail) {
		orderDetail.setOrder(null);
		orderDetails.remove(orderDetail);
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getDateShipped() {
		return dateShipped;
	}

	public void setDateShipped(Date dateShipped) {
		this.dateShipped = dateShipped;
	}

	@Override
	public String toString() {
		return "Order [customer=" + customer + ", shipToAddress=" + shipToAddress + ", orderDetails=" + orderDetails
				+ ", orderStatus=" + orderStatus + ", dateShipped=" + dateShipped + ", id=" + id + ", version="
				+ version + ", dateCreated=" + dateCreated + ", lastUpdated=" + lastUpdated + "]";
	}

}
