package ggc.core;

import java.io.Serializable;

/**
 * Represents a transaction between the Warehouse and its partners.
 * Each transaction is represented by a unique number.
 */
public abstract class Transaction implements Serializable, Comparable<Transaction> {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** The transaction's key. */
	private int _key;

	/** The transaction's associated partner. */
	private Partner _partner;

	/** The transaction's processed product. */
	private Product _product;

	/** The amount of product processed. */
	private int _amount;

	/** The date in which the transaction was paid. */
	private Date _paymentDate;

	/**
	 * Constructor.
	 *
	 * @param key         the transaction's key.
	 * @param partner     the transaction's associated partner.
	 * @param product     the transaction's processed product.
	 * @param amount      the amount of product processed.
	 * @param paymentDate the transaction's payment date.
	 */
	Transaction(int key, Partner partner, Product product, int amount, Date paymentDate) {
		_key = key;
		_partner = partner;
		_product = product;
		_amount = amount;
		_paymentDate = paymentDate;
	}

	/**
	 * @return the transaction's key.
	 */
	final int getKey() {
		return _key;
	}

	/**
	 * @return the transaction's associated partner.
	 */
	final Partner getPartner() {
		return _partner;
	}

	/**
	 * @return the transaction's processed product.
	 */
	final Product getProduct() {
		return _product;
	}

	/**
	 * @return the transaction's product amount.
	 */
	final int getProductAmount() {
		return _amount;
	}

	/**
	 * @return the transaction's payment date.
	 */
	final Date getPaymentDate() {
		return _paymentDate;
	}

	/**
	 * Set the transaction's payment date to the current date.
	 */
	final void setPaymentDate() {
		_paymentDate = Date.now();
	}

	/**
	 * Determines if a transaction has been paid.
	 */
	final boolean isPaid() {
		return _paymentDate != null;
	}

	/**
	 * Pays the transaction.
	 */
	abstract void pay();

	/**
	 * Updates the transaction price, accouting for discounts.
	 */
	abstract void updatePrice();

	/**
	 * Returns the transaction's price.
	 */
	abstract double getPrice();

	/**
	 * Determines if two transactions are equal.
	 * Two transactions are the same if they hold the same id.
	 *
	 * @param other transaction to compare to.
	 */
	@Override
	public boolean equals(Object other) {
		return other instanceof Transaction &&
			_key == ((Transaction) other).getKey();
	}

	/**
	 * Compares transactions by id.
	 *
	 * @param other transaction to compare to.
	 */
	@Override
	public int compareTo(Transaction other) {
		return _key - other.getKey();
	}

}
