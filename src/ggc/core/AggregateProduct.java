package ggc.core;

import java.util.List;

public class AggregateProduct extends Product {
	private Recipe _recipe;

	public AggregateProduct(String id, double aggravation, List<Product> products, List<Integer> quantities) {
		super(id);
		_recipe = new Recipe(aggravation, products, quantities);
	}

	public double getAggravation() {
		return _recipe.getAggravation();
	}

	/*
	public List getComponents() {
		return _recipe.getComponents();
	}
	*/
}
