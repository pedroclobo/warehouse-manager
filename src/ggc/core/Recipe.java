package ggc.core;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Recipe {
	private double _aggravation;
	private List<Component> _components;

	public Recipe(double aggravation, List<Product> products, List<Integer> quantities) {
		_aggravation = aggravation;
		_components = new ArrayList<>();

		Iterator<Product> iter1 = products.iterator();
		Iterator<Integer> iter2 = quantities.iterator();

		while (iter1.hasNext() && iter2.hasNext()) {
			Product p = iter1.next();
			Integer i = iter2.next();
			_components.add(new Component(p, i));
		}
	}

	public double getAggravation() {
		return _aggravation;
	}

	public List getComponents() {
		return _components;
	}

	public String toString() {
		String s = "";
		Iterator<Component> iter = _components.iterator();

		while(iter.hasNext()) {
			Component c = iter.next();
			if (iter.hasNext())
				s += c.toString() + "|";
			else
				s += c.toString();
		}

		return s;
	}
}
