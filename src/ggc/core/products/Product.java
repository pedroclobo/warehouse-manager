package ggc.core.products;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import ggc.core.partners.Partner;

/**
 * This is an abstract class that represents a product.
 * Subclasses refine this class in accordance with the type of product they
 * represent. In its most abstract form, a product has an id, a collection of
 * batches storing it and the maximum price it has ever achieved.
 */
public abstract class Product implements Comparable<Product>, Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** Product id. */
	private String _id;

	/** Highest Price. */
	private double _maxPrice;

	/** Collection of batches that hold the product. */
	private TreeSet<Batch> _batches;

	/**
	 * @param id the product id.
	 */
	public Product(String id) {
		_id = id;
		_maxPrice = 0;
		_batches = new TreeSet<>(Batch.getComparatorByPrice());
	}

	/**
	 * @return the product id;
	 */
	public final String getId() {
		return _id;
	}

	/**
	 * @return the product highest price.
	 */
	public final double getMaxPrice() {
		return _maxPrice;
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
	public int getStock() {
		int stock = 0;

		for (Batch b: _batches) {
			stock += b.getStock();
		}

		return stock;
	}

  	/** @see java.lang.Object#equals(java.lang.Object) */
	@Override
	public boolean equals(Object other) {
		return other instanceof Product &&
			   _id.equalsIgnoreCase(((Product) other).getId());
	}

	/**
	 * @return the lowest price available for the product.
	 */
	public double getLowestPrice() {
		return _batches.first().getPrice();
	}

  	/** @see java.lang.Object#toString() */
	@Override
	public String toString() {
		return "" + _id + "|" + (int) _maxPrice + "|" + getStock();
	}

	/**
	 * @return a collection of batches sorted by their natural order.
	 */
	public Collection<Batch> getBatches() {
		Set<Batch> batches = new TreeSet<>();
		batches.addAll(_batches);

		return batches;
	}

	public Collection<Batch> getBatchesWithLowerPrice(double price) {
		Collection<Batch> batches = new TreeSet<>();

		for (Batch b: _batches) {
			if (b.getPrice() >= price)
				break;
			batches.add(b);
		}

		return batches;
	}

	/**
	 * Compares products by their id.
	 */
	@Override
	public int compareTo(Product other) {
		return _id.compareTo(other.getId());
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

}
