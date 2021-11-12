package ggc.core;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Class Batch implements a batch.
 * A batch is an entity responsible for holding products.
 * Each batch has a partner, who supplies product units at a certain price.
 */
public class Batch implements Comparable<Batch>, Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** Product to hold. */
	private Product _product;

	/** Partner who supplies the product. */
	private Partner _partner;

	/** Current product stock. */
	private int _stock;

	/** Product unit price. */
	private double _price;

	/**
	 * Creates a new batch.
	 *
	 * @param product the product to hold.
	 * @param partner the supplier.
	 * @param price   the product unit price.
	 */
	Batch(Product product, Partner partner, double price) {
		_product = product;
		_partner = partner;
		_stock = 0;
		_price = price;
	}

	/**
	 * @return the product held.
	 */
	Product getProduct() {
		return _product;
	}

	/**
	 * @return the partner who supplies the products.
	 */
	Partner getPartner() {
		return _partner;
	}

	/**
	 * @return the product stock.
	 */
	int getStock() {
		return _stock;
	}

	/**
	 * @return the product price.
	 */
	double getPrice() {
		return _price;
	}

	/**
	 * Determines if the batch is empty.
	 *
	 * @return true if the batch is empty.
	 */
	boolean isEmpty() {
		return _stock == 0;
	}

	/**
	 * Add product units to current stock.
	 *
	 * @param units number of units to add.
	 */
	void add(int units) {
		_stock += units;
	}

	/**
	 * Remove product units from the current stock.
	 *
	 * @param units number of units to remove.
	 * @return true, if the operation was successful; false otherwise.
	 */
	boolean remove(int units) {
		if (_stock >= units) {
			_stock -= units;
			return true;
		}

		return false;
	}

	/**
	 * @return a batch's comparator by price.
	 */
	static final Comparator<Batch> getComparatorByPrice() {
		class BatchPriceComparator implements Comparator<Batch>, Serializable {

			/** Serial number for serialization. */
			private static final long serialVersionUID = 202109192006L;

			@Override
			public int compare(Batch b1, Batch b2) {
				int i = (int) (b1.getPrice() - b2.getPrice());
				if (i != 0)
					return i;

				i = b1.getProduct().compareTo(b2.getProduct());
				if (i != 0)
					return i;

				i = b1.getPartner().compareTo(b2.getPartner());
				if (i != 0)
					return i;

				i = b1.getStock() - b2.getStock();
				return i;
			}
		}

		return new BatchPriceComparator();
	}

  	/** @see java.lang.Object#equals(java.lang.Object) */
	@Override
	public boolean equals(Object other) {
		return other instanceof Batch &&
			   _product.equals(((Batch) other).getProduct()) &&
			   _partner.equals(((Batch) other).getPartner()) &&
			   _stock == ((Batch) other).getStock() &&
			   _price == ((Batch) other).getPrice();
	}

	/**
	 * Compares batches.
	 */
	@Override
	public int compareTo(Batch other) {
		int i = _product.compareTo(other.getProduct());
		if (i != 0)
			return i;

		i = _partner.compareTo(other.getPartner());
		if (i != 0)
			return i;

		i = (int) (_price - other.getPrice());
		if (i != 0)
			return i;

		i = _stock - other.getStock();
		return i;
	}


  	/**
	 * String representation of batch.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return _product.getKey() + "|" + _partner.getKey() + "|" + Math.round(_price) + "|" + _stock;
	}

}
