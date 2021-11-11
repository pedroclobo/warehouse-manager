package ggc.core;

import java.io.Serializable;
import ggc.core.products.Product;

public class Notification implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	private String _type;
	private Product _product;

	public Notification(String type) {
		_type = type;
	}

	public String getType() {
		return _type;
	}

	public Product getProduct() {
		return _product;
	}

	public String toString() {
		return _type + "|" + _product.getKey() + "|" + _product.getLowestPrice();
	}
}
