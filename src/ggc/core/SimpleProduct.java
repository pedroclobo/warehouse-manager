package ggc.core;

import java.util.Iterator;
import java.util.ArrayList;

import ggc.core.exception.NoProductStockException;

/**
 * This public class represents a simple product.
 */
public class SimpleProduct extends Product {

	/**
	 * Creates a new simple product.
	 *
	 * @param key the product key.
	 */
	SimpleProduct(String key) {
		super(key);
	}

	/**
	 * @return an iterator of the products that make up the aggregate product.
	 */
	@Override
	public Iterator<Product> getProductIterator() {
		return new ArrayList<Product>().iterator();
	}

	/**
	 * @return an iterator of the quantities of products that make up the aggregate product.
	 */
	@Override
	public Iterator<Integer> getQuantityIterator() {
		return new ArrayList<Integer>().iterator();
	}

	/**
	 * @return the time factor to account on credit sale payments.
	 */
	@Override
	public int getNTimeFactor() {
		return 5;
	}

	/**
	 * Checks if the aggregation of the product is possible.
	 *
	 * @param amount the amount of product to disaggregate.
	 * @throws NoProductStockException if there's not enough product stock of any of the components.
	 */
	@Override
	public void checkAggregation(int amount) throws NoProductStockException {
		if (getStock() < amount) {
			throw new NoProductStockException(getKey(), amount, getStock());
		}
	}

	/**
	 * Aggregates the product.
	 */
	@Override
	public void aggregate() {}

	/**
	 * Determines if a product can be disaggregated.
	 * Aggregate products can always be disaggregated.
	 *
	 * @return true, if the product can be disaggregated; false, otherwise.
	 */
	@Override
	boolean canBeDisaggregated() {
		return false;
	}

	/**
	 * Disaggregates the product.
	 */
	@Override
	public void disaggregate(int amount, Partner partner) {}

}
