package ggc.core;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import ggc.core.exception.NoProductStockException;

/**
 * This public class represents products composed from other products.
 * Each aggregate product has a recipe, which designates the products
 * it is made of. Along with this recipe, it also has a aggravation
 * factor, which depends on the recipe, used for calculating aggregation
 * price of the product.
 */
public class AggregateProduct extends Product {

	/** The product recipe. */
	private Recipe _recipe;

	/**
	 * Creates a new aggregate product.
	 *
	 * @param key         the product key.
	 * @param aggravation the aggravation factor.
	 * @param products    the products that make up the aggregate product.
	 * @param amounts     the amounts of product that make up the aggregate product.
	 */
	AggregateProduct(String key, double aggravation, List<Product> products, List<Integer> amounts) {
		super(key);
		_recipe = new Recipe(aggravation, products, amounts);
	}

	/**
	 * @return the aggravation factor.
	 */
	double getAggravation() {
		return _recipe.getAggravation();
	}

	/**
	 * Determines if a product can be disaggregated.
	 * Aggregate products can always be disaggregated.
	 *
	 * @return true, if the product can be disaggregated; false, otherwise.
	 */
	@Override
	boolean canBeDisaggregated() {
		return true;
	}

	/**
	 * Disaggregates the product.
	 *
	 * @param amount  the amount of product to disaggregate.
	 * @param partner the partner who requested to disaggregation.
	 */
	@Override
	void disaggregate(int amount, Partner partner) {
		double componentPrice;

		Iterator<Product> prodIter = getProductIterator();
		Iterator<Integer> quantIter = getQuantityIterator();

		while (prodIter.hasNext() && quantIter.hasNext()) {
			Product component = prodIter.next();
			int componentAmount = quantIter.next();

			componentPrice = (component.hasStock()) ? component.getLowestPrice() : component.getMaxPrice();

			// Add components to stock.
			component.add(componentAmount * amount, partner, componentPrice);
		}

		// Remove aggregate product.
		remove(amount);
	}

	/**
	 * Checks if the aggregation of the product is possible.
	 *
	 * @param amount the amount of product to disaggregate.
	 * @throws NoProductStockException if there's not enough product stock of any of the components.
	 */
	@Override
	void checkAggregation(int amount) throws NoProductStockException {
		// Iterate over all components, to know their insertion prices.
		Iterator<Product> prodIter = getProductIterator();
		Iterator<Integer> quantIter = getQuantityIterator();

		while (prodIter.hasNext() && quantIter.hasNext()) {
			Product component = prodIter.next();
			int componentAmount = quantIter.next();

			try {
				// Check aggregation for all subcomponents.
				component.checkAggregation((amount - getStock()) * componentAmount);
			} catch (NoProductStockException e) {
				throw e;
			}
		}
	}

	/**
	 * Aggregates the product.
	 */
	@Override
	void aggregate() {
		Iterator<Product> prodIter = getProductIterator();
		Iterator<Integer> quantIter = getQuantityIterator();

		while (prodIter.hasNext() && quantIter.hasNext()) {
			Product component = prodIter.next();
			int componentAmount = quantIter.next();

			component.aggregate();
		}
	}

	/**
	 * @return an iterator of the products that make up the aggregate product.
	 */
	@Override
	Iterator<Product> getProductIterator() {
		return _recipe.getProductIterator();
	}

	/**
	 * @return an iterator of the quantities of products that make up the aggregate product.
	 */
	@Override
	Iterator<Integer> getQuantityIterator() {
		return _recipe.getQuantityIterator();
	}

	/**
	 * @return the time factor to account on credit sale payments.
	 */
	@Override
	int getNTimeFactor() {
		return 3;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return super.toString() + "|" + _recipe;
	}

	/**
	 * This public class represents a component.
	 * Each component is made up by a product, in a certain amount.
	 */
	private class Component implements Serializable {

		/** Serial number for serialization. */
		private static final long serialVersionUID = 202109192006L;

		/** The product of the component. */
		private Product _product;

		/** The amount of product. */
		private int _amount;

		/**
		 * Creates a new component.
		 *
		 * @param product  the product of the component.
		 * @param amount   the amount of product.
		 */
		private Component(Product product, int amount) {
			_product = product;
			_amount = amount;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return _product.getKey() + ":" + _amount;
		}

	}

	/**
	 * This public class holds the information about which products compose
	 * its aggregate product.
	 */
	private class Recipe implements Serializable {

		/** Serial number for serialization. */
		private static final long serialVersionUID = 202109192006L;

		/** The aggravation price factor. */
		private double _aggravation;

		/** A collection of the product components. */
		private List<Component> _components;

		/**
		 * Creates a new recipe.
		 *
		 * @param aggravation the aggravation price factor.
		 * @param products    the collection of products of the recipe.
		 * @param amounts     the collection of amounts of those products.
		 */
		private Recipe(double aggravation, List<Product> products, List<Integer> amounts) {
			_aggravation = aggravation;
			_components = new ArrayList<>();

			Iterator<Product> iter1 = products.iterator();
			Iterator<Integer> iter2 = amounts.iterator();

			// Create components of every product
			while (iter1.hasNext() && iter2.hasNext()) {
				Product product = iter1.next();
				Integer amount = iter2.next();
				_components.add(new Component(product, amount));
			}
		}

		/**
		 * @return the aggravation price factor.
		 */
		private double getAggravation() {
			return _aggravation;
		}

		/**
		 * @return a collection of all components.
		 */
		private List getComponents() {
			return _components;
		}

		/**
		 * @return an iterator of all product in the recipe.
		 */
		private Iterator<Product> getProductIterator() {
			Iterator<Component> iter = _components.iterator();
			return new Iterator<Product>() {
				public boolean hasNext() {
					return iter.hasNext();
				}

				public Product next() {
					return iter.next()._product;
				}
			};
		}

		/**
		 * @return an iterator of all product amounts in the recipe.
		 */
		private Iterator<Integer> getQuantityIterator() {
			Iterator<Component> iter = _components.iterator();
			return new Iterator<Integer>() {
				public boolean hasNext() {
					return iter.hasNext();
				}

				public Integer next() {
					return iter.next()._amount;
				}
			};
		}

		/**
		 * String representation of recipe.
		 *
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			StringBuilder recipe = new StringBuilder();
			Iterator<Component> iter = _components.iterator();

			while(iter.hasNext()) {
				Component c = iter.next();
				if (iter.hasNext()) {
					recipe.append(c.toString() + "#");
				}
				else {
					recipe.append(c.toString());
				}
			}
			return recipe.toString();
		}

	}

}
