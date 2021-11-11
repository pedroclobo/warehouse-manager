package ggc.core;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import ggc.core.exception.NoProductStockException;

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

	@Override
	public boolean canBeDisaggregated() {
		return true;
	}

	/**
	 * Aggregates the product.
	 */
	@Override
	public void aggregate() {
		Iterator<Product> prodIter = getProductIterator();
		Iterator<Integer> quantIter = getQuantityIterator();

		while (prodIter.hasNext() && quantIter.hasNext()) {
			Product component = prodIter.next();
			int componentAmount = quantIter.next();

			component.aggregate();
		}
	}

	/**
	 * Disaggregates the product.
	 */
	@Override
	public void disaggregate(int amount, Partner partner) {
		double productPrice;

		Iterator<Product> prodIter = getProductIterator();
		Iterator<Integer> quantIter = getQuantityIterator();

		while (prodIter.hasNext() && quantIter.hasNext()) {
			Product product = prodIter.next();
			int productAmount = quantIter.next();

			if (product.hasStock())
				productPrice = product.getLowestPrice();
			else
				productPrice = product.getMaxPrice();

			// Add components to stock.
			product.add(productAmount * amount, partner, productPrice);
		}

		// Remove aggregate product.
		remove(amount);
	}

	public Iterator<Product> getProductIterator() {
		return _recipe.getProductIterator();
	}

	public Iterator<Integer> getQuantityIterator() {
		return _recipe.getQuantityIterator();
	}

	public int getNTimeFactor() {
		return 3;
	}

	@Override
	public void checkAggregation(int amount) throws NoProductStockException {
		// Iterate over all components, to know their insertion prices.
		Iterator<Product> prodIter = getProductIterator();
		Iterator<Integer> quantIter = getQuantityIterator();

		while (prodIter.hasNext() && quantIter.hasNext()) {
			Product component = prodIter.next();
			int componentAmount = quantIter.next();

			try {
				component.checkAggregation((amount - getStock()) * componentAmount);
			} catch (NoProductStockException e) {
				throw e;
			}
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return super.toString() + "|" + getAggravation() + "|" + _recipe;
	}

	/**
	 * This class represents a component.
	 * Each component is made up by a product, in a certain quantity.
	 */
	private class Component implements Serializable {

		/** Serial number for serialization. */
		private static final long serialVersionUID = 202109192006L;

		/** The product of the component. */
		private Product _product;

		/** The quantity of product. */
		private int _quantity;

		/**
		 * Constructor.
		 *
		 * @param product  the product of the component.
		 * @param quantity the quantity of product.
		 */
		private Component(Product product, int quantity) {
			_product = product;
			_quantity = quantity;
		}

		/**
		 * @return the product of the component.
		 */
		private Product getProduct() {
			return _product;
		}

		/**
		 * @return the quantity of product of the component.
		 */
		private int getQuantity() {
			return _quantity;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return _product.getKey() + ":" + _quantity;
		}

	}

	/**
	 * This class holds the information about which products compose
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
		 * Constructor.
		 *
		 * @param aggravation the aggravation price factor.
		 * @param products    the collection of products of the recipe.
		 * @param quantities  the quantities of those products.
		 */
		private Recipe(double aggravation, List<Product> products, List<Integer> quantities) {
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
		 * TODO
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
		 * TODO
		 */
		private Iterator<Integer> getQuantityIterator() {
			Iterator<Component> iter = _components.iterator();
			return new Iterator<Integer>() {
				public boolean hasNext() {
					return iter.hasNext();
				}

				public Integer next() {
					return iter.next()._quantity;
				}
			};
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
					s += c.toString() + "#";
				else
					s += c.toString();
			}

			return s;
		}

	}

}
