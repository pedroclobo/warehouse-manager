package ggc.core;

import java.io.Serializable;

/**
 * Represents a notification.
 */
public class Notification implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** The notification's type. */
	private String _type;

	/** The product associated with the notification. */
	private Product _product;

	/** The product's price. */
	private double _price;

	/**
	 * Creates a new notification.
	 *
	 * @param type    the notification's type.
	 * @param product the product associated with the notification.
	 * @param product the product's price.
	 */
	public Notification(String type, Product product, double price) {
		_type = type;
		_product = product;
		_price = price;
	}

	/**
	 * String representation of notification.
	 *
  	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return _type + "|" + _product.getKey() + "|" + Math.round(_price);
	}

}
