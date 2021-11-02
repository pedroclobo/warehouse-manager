package ggc.core.products;

import java.util.List;

/**
 * This class represents products made from other products.
 * Each aggregate product has a recipe, which designates the products
 * it is made of. Along with this recipe, it also has a aggravation
 * factor, which depends on the recipe, used for calculating the price
 * of the product.
 */
public class AggregateProduct extends Product {

	/** The product recipe. */
	private Recipe _recipe;

	/**
	 * Creates a new aggregate product.
	 *
	 * @param id          the product id.
	 * @param aggravation the aggravation factor.
	 * @param products    the products that make up the aggregate product.
	 * @param quantities  the quantities of product that make up the aggregate product.
	 */
	public AggregateProduct(String id, double aggravation, List<Product> products, List<Integer> quantities) {
		super(id);
		_recipe = new Recipe(aggravation, products, quantities);
	}

	/**
	 * @return the aggravation factor.
	 */
	public double getAggravation() {
		return _recipe.getAggravation();
	}

	/**
	 * @return a collection of all the components.
	 */
	public List getComponents() {
		return _recipe.getComponents();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return super.toString() + "|" + _recipe;
	}

}
