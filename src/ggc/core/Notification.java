package ggc.core;

import java.io.Serializable;

public class Notification implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	private String _type;
	private Product _product;

	public Notification(String type, Product product) {
		_type = type;
		_product = product;
	}

	public String toString() {
		return _type + "|" + _product.getKey() + "|" + Math.round(_product.getLowestPrice());
	}
}
