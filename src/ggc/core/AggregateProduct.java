package ggc.core;

import java.util.List;

public class AggregateProduct extends Product {
	private Recipe _recipe;

	public AggregateProduct(String id, Recipe recipe) {
		super(id);
		_recipe = recipe;
	}

	public double getAlpha() {
		return _recipe.getAlpha();
	}

	public List getComponents() {
		return _recipe.getComponents();
	}
}
