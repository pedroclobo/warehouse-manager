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

	/**
	 * Creates a new notification.
	 *
	 * @param type    the notification's type.
	 * @param product the product associated with the notification.
	 */
	public Notification(String type, Product product) {
		_type = type;
		_product = product;
	}

	/**
	 * String representation of notification.
	 *
  	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return _type + "|" + _product.getKey() + "|" + Math.round(_product.getLowestPrice());
	}

}
