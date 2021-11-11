package ggc.core;

import java.util.Iterator;
import java.util.ArrayList;

import ggc.core.exception.NoProductStockException;

/**
 * This class represents a simple product.
 */
public class SimpleProduct extends Product {

	/** Creates a new simple product.
	 * @param id the product id
	 */
	public SimpleProduct(String id) {
		super(id);
	}

	public boolean canBeDisaggregated() {
		return false;
	}

	public Iterator<Product> getProductIterator() {
		return new ArrayList<Product>().iterator();
	}

	public Iterator<Integer> getQuantityIterator() {
		return new ArrayList<Integer>().iterator();
	}

	public int getNTimeFactor() {
		return 5;
	}

	/**
	 * Aggregates the product.
	 */
	@Override
	public void aggregate() {}

	/**
	 * Disaggregates the product.
	 */
	@Override
	public void disaggregate(int amount, Partner partner) {}

	@Override
	public void checkAggregation(int amount) throws NoProductStockException {
		if (getStock() < amount) {
			throw new NoProductStockException(getKey(), amount, getStock());
		}
	}

}
