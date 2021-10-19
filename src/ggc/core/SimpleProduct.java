package ggc.core;

public class SimpleProduct extends Product {
	public SimpleProduct(String id) {
		super(id);
	}

	public SimpleProduct copy() {
		return new SimpleProduct(this.getId());
	}
}
