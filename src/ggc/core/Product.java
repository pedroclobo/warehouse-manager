package ggc.core;

import java.io.Serializable;
import java.util.Collections;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import ggc.core.exception.NoProductStockException;

/**
 * This is an abstract public class that represents a product.
 * Subclasses refine this public class in accordance with the type of product they represent.
 * In its most abstract form, a product has an identifier, a collection of batches
 * holding it in stock and the maximum price it has ever achieved.
 */
public abstract class Product implements Comparable<Product>, Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** The product's identifier. */
	private String _key;

	/** The product's all time highest price. */
	private double _maxPrice;

	/** Determines if a product has been recently added. */
	private boolean _new;

	/** Collection of batches that hold the product. */
	private List<Batch> _batches;

	/** Collection of all entities interested in being notified. */
	private Set<Notifiable> _notifiables;

	/**
	 * @param key the product identifier.
	 */
	Product(String key) {
		_key = key;
		_maxPrice = 0;

		// When created a product is new.
		_new = true;

		_batches = new ArrayList<>();
		_notifiables = new HashSet<>();
	}

	/**
	 * @return the product key;
	 */
	final String getKey() {
		return _key;
	}

	/**
	 * @return the product highest price.
	 */
	final double getMaxPrice() {
		return _maxPrice;
	}

	/**
	 * Determines if a product has been recently added.
	 *
	 * @return true, if the product is new; false, otherwise.
	 */
	final boolean isNew() {
		return _new;
	}

	/**
	 * @return the lowest price available for the product.
	 */
	final double getLowestPrice() {
		return (_batches.size() > 0) ? _batches.get(0).getPrice() : 0;
	}

	/**
	 * Determines if there's available product stock.
	 *
	 * @return true, if there's at least one unit available; false otherwise.
	 */
	final boolean hasStock() {
		return getStock() > 0;
	}

	/**
	 * @return the available stock product.
	 */
	final int getStock() {
		int stock = 0;

		for (Batch b: _batches) {
			stock += b.getStock();
		}

		return stock;
	}

	/**
	 * @return an iterator of the products that make up the aggregate product.
	 */
	abstract Iterator<Product> getProductIterator();

	/**
	 * @return an iterator of the quantities of products that make up the aggregate product.
	 */
	abstract Iterator<Integer> getQuantityIterator();

	/**
	 * @return the time factor to account on credit sale payments.
	 */
	abstract int getNTimeFactor();

	/**
	 * Checks if the aggregation of the product is possible.
	 *
	 * @param amount the amount of product to disaggregate.
	 * @throws NoProductStockException if there's not enough product stock of any of the components.
	 */
	abstract void checkAggregation(int amount) throws NoProductStockException;

	/**
	 * Aggregates the product.
	 */
	abstract void aggregate();

	/**
	 * Determines if a product can be disaggregated.
	 * Aggregate products can always be disaggregated.
	 *
	 * @return true, if the product can be disaggregated; false, otherwise.
	 */
	abstract boolean canBeDisaggregated();

	/**
	 * Disaggregates the product.
	 *
	 * @param amount  the amount of product to disaggregate.
	 * @param partner the partner who requested to disaggregation.
	 *
	 * @return the removal price.
	 */
	abstract double disaggregate(int amount, Partner partner);

	/**
	 * @return a collection of batches sorted by their natural order.
	 */
	final Collection<Batch> getBatches() {
		List<Batch> batches = new ArrayList<Batch>(_batches);
		Collections.sort(batches);
		return batches;
	}

	/**
	 * Returns a collection of all batches under the specified price.
	 *
	 * @param price the price to compare to.
	 */
	final Collection<Batch> getBatchesUnderGivenPrice(double price) {
		Collection<Batch> batches = new ArrayList<>();

		for (Batch batch: _batches) {
			if (batch.getPrice() >= price) {
				break;
			}
			batches.add(batch);
		}

		return batches;
	}

	/**
	 * Adds a batch.
	 *
	 * @param batch the batch to add.
	 */
	final void addBatch(Batch batch) {
		batch.getPartner().addBatch(batch);
		_batches.add(batch);
		Collections.sort(_batches, Batch.getComparatorByPrice());
	}

	/**
	 * Removes a batch.
	 *
	 * @param batch the batch to remove.
	 */
	final void removeBatch(Batch batch) {
		batch.getPartner().removeBatch(batch);
		_batches.remove(batch);
		Collections.sort(_batches, Batch.getComparatorByPrice());
	}

	/**
	 * Adds units of product to a batch.
	 *
	 * @param units   the number of units to add.
	 * @param partner the partner who supplies the units.
	 * @param double  the price per unit of the product.
	 */
	final void add(int units, Partner partner, double price) {

		// The product is no longer new.
		_new = false;

		// Update maximum price if higher.
		if (price > _maxPrice)
			_maxPrice = price;

		// Add to new batch.
		Batch batch = new Batch(this, partner, price);
		addBatch(batch);
		batch.add(units);
	}

	/**
	 * Remove units from the batches.
	 *
	 * @param units the number of units to remove.
	 * @return the total price of the units removed.
	 */
	final double remove(int units) {
		Batch batch = null;
		double total = 0;
		int removed = 0;

		// Impossible operation.
		if (units > getStock())
			return total;

		// Remove unit by unit.
		while (removed < units) {
			batch = _batches.get(0);

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

	/**
	 * Determines if the notifiable is registered.
	 *
	 * @param notifiable the notifiable.
	 */
	final boolean isRegisteredNotifiable(Notifiable notifiable) {
		return _notifiables.contains(notifiable);
	}


	/**
	 * Adds a notifiable to the product's collection.
	 *
	 * @param notifiable the notifiable to add.
	 */
	final void addNotifiable(Notifiable notifiable) {
		_notifiables.add(notifiable);
	}

	/**
	 * Removes a notifiable from the product's collection.
	 *
	 * @param notifiable the notifiable to remove.
	 */
	final void removeNotifiable(Notifiable notifiable) {
		_notifiables.remove(notifiable);
	}

	/**
	 * Sends a notification to all subscribed notifiables.
	 *
	 * @param notification the notification to send.
	 */
	final void sendNotification(Notification notification) {
		for (Notifiable notifiable: _notifiables) {
			notifiable.updateNotifications(notification);
		}
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
		return "" + _key + "|" + Math.round(_maxPrice) + "|" + getStock();
	}

}
