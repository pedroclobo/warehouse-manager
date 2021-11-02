package ggc.core.products;

import java.io.Serializable;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * This class holds the information about which products compose
 * its aggregate product.
 */
public class Recipe implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** The aggravation price factor. */
	private double _aggravation;

	/** A collection of the product components. */
	private List<Component> _components;

	/**
	 * Creates a recipe.
	 *
	 * @param aggravation the aggravation price factor.
	 * @param products    the collection of products of the recipe.
	 * @param quantities  the quantities of those products.
	 */
	public Recipe(double aggravation, List<Product> products, List<Integer> quantities) {
		_aggravation = aggravation;
		_components = new ArrayList<>();

		Iterator<Product> iter1 = products.iterator();
		Iterator<Integer> iter2 = quantities.iterator();

		// Create components of every product
		while (iter1.hasNext() && iter2.hasNext()) {
			Product p = iter1.next();
			Integer i = iter2.next();
			_components.add(new Component(p, i));
		}
	}

	/**
	 * @return the aggravation price factor.
	 */
	public double getAggravation() {
		return _aggravation;
	}

	/**
	 * @return a collection of all components.
	 */
	public List getComponents() {
		return _components;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
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
