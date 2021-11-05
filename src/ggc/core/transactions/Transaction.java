package ggc.core.transactions;

import java.io.Serializable;

import ggc.core.products.Product;
import ggc.core.partners.Partner;

/**
 * Represents a transaction between the Warehouse and its partners.
 * Each transaction is represented by a number (id).
 */
public abstract class Transaction implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** Total number of transactions. */
	private static int _numberTransactions = 0;

	/** The transaction's id. */
	private int _id;

	/** The transaction's associated partner. */
	private Partner _partner;

	/** The transaction's processed product. */
	private Product _product;

	/** The quantity of product processed. */
	private int _quantity;

	/** The date in which the transaction was paid. */
	private int _paymentDate;

	/**
	 * Constructor.
	 *
	 * @param partner     the transaction's associated partner.
	 * @param product     the transaction's processed product.
	 * @param quantity    the quantity of product processed.
	 * @param paymentDate the transaction's payment date.
	 */
	protected Transaction(Partner partner, Product product, int quantity, int paymentDate) {
		_id = _numberTransactions++;
		_partner = partner;
		_product = product;
		_quantity = quantity;
		_paymentDate = paymentDate;
	}

	/**
	 * @return the transaction's id.
	 */
	public int getId() {
		return _id;
	}

	/**
	 * @return the transaction's product quantity.
	 */
	public int getProductQuantity() {
		return _quantity;
	}

	/**
	 * @return the transaction's associated partner.
	 */
	public Partner getPartner() {
		return _partner;
	}

	/**
	 * @return the transaction's processed product.
	 */
	public Product getProduct() {
		return _product;
	}

	/**
	 * @return the transaction's payment date.
	 */
	public int getPaymentDate() {
		return _paymentDate;
	}

	/**
	 * Set the transaction's payment date.
	 *
	 * @param date the payment date.
	 */
	// TODO
	public void setPaymentDate(int date) {
		_paymentDate = date;
	}

}
