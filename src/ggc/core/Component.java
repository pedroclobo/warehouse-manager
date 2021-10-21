package ggc.core;

public class Component {
	Product _product;
	int _quantity;

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
}
