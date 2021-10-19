package ggc.core;

public class AggregateProduct extends Product {
	public AggregateProduct(String id) {
		super(id);
	}

	public AggregateProduct copy() {
		return new AggregateProduct(this.getId());
	}
}
