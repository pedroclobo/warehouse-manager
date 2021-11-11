package ggc.core.products;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import ggc.core.partners.Partner;
import ggc.core.exception.NoProductStockException;

/**
 * This is an abstract class that represents a product.
 * Subclasses refine this class in accordance with the type of product they
 * represent. In its most abstract form, a product has an identifier, a
 * collection of batches storing it and the maximum price it has ever achieved.
 */
public abstract class Product implements Comparable<Product>, Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** Product identifier. */
	private String _key;

	/** Highest Price. */
	private double _maxPrice;

	/** Collection of batches that hold the product. */
	private TreeSet<Batch> _batches;

	/**
	 * @param key the product identifier.
	 */
	public Product(String key) {
		_key = key;
		_maxPrice = 0;
		_batches = new TreeSet<>(Batch.getComparatorByPrice());
	}

	/**
	 * @return the product key;
	 */
	public final String getKey() {
		return _key;
	}

	/**
	 * @return the product highest price.
	 */
	public final double getMaxPrice() {
		return _maxPrice;
	}

	/**
	 * @return the lowest price available for the product.
	 */
	public double getLowestPrice() {
		return _batches.first().getPrice();
	}

	/**
	 * Determines if there's available product stock.
	 *
	 * @return true, if there's at least one unit available; false otherwise.
	 */
	public final boolean hasStock() {
		return getStock() > 0;
	}

	/**
	 * @return the available stock product.
	 */
	public final int getStock() {
		int stock = 0;

		for (Batch b: _batches) {
			stock += b.getStock();
		}

		return stock;
	}

	public abstract void checkAggregation(int amount) throws NoProductStockException;

	public abstract boolean canBeDisaggregated();

	public abstract Iterator<Product> getProductIterator();

	public abstract Iterator<Integer> getQuantityIterator();

	public abstract int getNTimeFactor();

	/**
	 * Aggregates the product.
	 */
	public abstract void aggregate(int amount);

	/**
	 * Disaggregates the product.
	 */
	public abstract void disaggregate(int amount, Partner partner);

	/**
	 * @return a collection of batches sorted by their natural order.
	 */
	public Collection<Batch> getBatches() {
		Set<Batch> batches = new TreeSet<>();
		batches.addAll(_batches);

		return batches;
	}

	/**
	 * Returns a collection of all batches under the specified price.
	 *
	 * @param price the price to compare to.
	 */
	public Collection<Batch> getBatchesUnderGivenPrice(double price) {
		Collection<Batch> batches = new TreeSet<>();

		for (Batch batch: _batches) {
			if (batch.getPrice() >= price)
				break;
			batches.add(batch);
		}

		return batches;
	}

	/**
	 * Removes a batch.
	 *
	 * @param batch the batch to remove.
	 */
	public void removeBatch(Batch batch) {
		_batches.remove(batch);
	}

	/**
	 * Adds units of product to a batch.
	 *
	 * @param units   the number of units to add.
	 * @param partner the partner who supplies the units.
	 * @param double  the price per unit of the product.
	 */
	public void add(int units, Partner partner, double price) {

		// Update maximum price if higher
		if (price > _maxPrice)
			_maxPrice = price;

		// Add to new batch
		Batch batch = new Batch(this, partner, price);
		batch.add(units);
		_batches.add(batch);
		partner.addBatch(batch);
	}

	/**
	 * Remove units from the batches.
	 *
	 * @param units the number of units to remove.
	 * @return the total price of the units removed.
	 */
	public double remove(int units) {
		Batch batch = null;
		double total = 0;
		int removed = 0;

		if (units > getStock())
			return total;

		while (removed < units) {
			batch = _batches.first();
			while (batch.remove(1)) {
				removed++;
				total += batch.getPrice();
				if (removed == units)
					break;
			}
			if (batch.getStock() == 0)
				removeBatch(batch);
		}

		return total;
	}

  	/** @see java.lang.Object#equals(java.lang.Object) */
	@Override
	public boolean equals(Object other) {
		return other instanceof Product &&
			   _key.equalsIgnoreCase(((Product) other).getKey());
	}

	/**
	 * Compares products by their key.
	 */
	@Override
	public int compareTo(Product other) {
		return _key.compareToIgnoreCase(other.getKey());
	}

  	/** @see java.lang.Object#toString() */
	@Override
	public String toString() {
		return "" + _key + "|" + (int) _maxPrice + "|" + getStock();
	}

}
