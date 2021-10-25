package ggc.core;

public class Component {
	private Product _product;
	private int _quantity;

	public Component(Product product, int quantity) {
		_product = product;
		_quantity = quantity;
	}

	public Product getProduct() {
		return _product;
	}

	public int getQuantity() {
		return _quantity;
	}

	public String toString() {
		return _product.getId() + ":" + _quantity;
	}
}
