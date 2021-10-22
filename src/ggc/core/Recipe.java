package ggc.core;

import java.util.List;
import java.util.ArrayList;

public class Recipe {
	private double _aggravation;
	private List<Product> _products;
	private List<Integer> _quantities;

	public Recipe(double aggravation, List<Product> products, List<Integer> quantities) {
		_aggravation = aggravation;
		_products = new ArrayList<>(products);
		_quantities = new ArrayList<>(quantities);
	}

	public double getAggravation() {
		return _aggravation;
	}

	/*
	public List getComponents() {
		return _components;
	}
	*/
}
