package ggc.core;

public class Notification {
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
		return _type + "|" + _product.getId() + "|" + _product.getLowestPrice();
	}
}
