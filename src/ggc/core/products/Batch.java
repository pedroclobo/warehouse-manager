package ggc.core.products;

import java.io.Serializable;

import ggc.core.partners.Partner;

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
	 * @param product the product to hold.
	 * @param partner the supplier.
	 * @param price   the unit price.
	 */
	public Batch(Product product, Partner partner, double price) {
		_product = product;
		_partner = partner;
		_stock = 0;
		_price = price;
	}

	/**
	 * @return the product held.
	 */
	public Product getProduct() {
		return _product;
	}

	/**
	 * @return the partner who supplies the products.
	 */
	public Partner getPartner() {
		return _partner;
	}

	/**
	 * @return the product stock.
	 */
	public int getStock() {
		return _stock;
	}

	/**
	 * @return the product price.
	 */
	public double getPrice() {
		return _price;
	}

	/**
	 * Determines if the batch is empty.
	 *
	 * @return true if the batch is empty.
	 */
	public boolean isEmpty() {
		return _stock == 0;
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

  	/** @see java.lang.Object#toString() */
	@Override
	public String toString() {
		return _product.getId() + "|" + _partner.getId() + "|" + (int) _price + "|" + _stock;
	}

	/**
	 * Add product units to current stock.
	 *
	 * @param units number of units to add.
	 */
	public void add(int units) {
		_stock += units;
	}

	/**
	 * Remove product units from the current stock.
	 *
	 * @param units number of units to remove.
	 * @return true, if the operation was successful; false otherwise.
	 */
	public boolean remove(int units) {
		if (_stock >= units) {
			_stock -= units;
			return true;
		}

		return false;
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

}
