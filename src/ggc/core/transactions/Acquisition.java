package ggc.core.transactions;

import ggc.core.Date;
import ggc.core.products.Product;
import ggc.core.partners.Partner;

/**
 * This class represents a acquisition transaction.
 * In an acquisitions transaction, the warehouse acquires a product
 * from a partner.
 */
public class Acquisition extends Transaction {

	/** The price paid. */
	private double _price;

	/**
	 * Constructor.
	 *
	 * @param key         the transaction's key.
	 * @param partner     the transaction's associated partner.
	 * @param product     the transaction's processed product.
	 * @param quantity    the quantity of product processed.
	 * @param paymentDate the transaction's payment date.
	 * @param price       the transaction's price.
	 */
	public Acquisition(int key, Partner partner, Product product, int quantity, Date paymentDate, double price) {
		super(key, partner, product, quantity, paymentDate);
		_price = price;
		product.add(quantity, partner, price / quantity);
		pay();
	}

	/**
	 * Returns the transaction's price.
	 *
	 * @param date the current date.
	 */
	@Override
	public double getPrice() {
		return _price;
	}

	/**
	 * Pays the acquisition.
	 */
	@Override
	public void pay() {
		getPartner().payTransaction(this);
	}

	/**
	 * Updates the transaction price, accouting for discounts.
	 *
	 * @param date the current date.
	 */
	@Override
	public void updatePrice() {}

	/**
	 * String representation of acquisition.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "COMPRA|" +
			getKey() + "|" +
			getPartner().getKey() + "|" +
			getProduct().getKey() + "|" +
			getProductAmount() + "|" +
			(int) _price + "|" +
			getPaymentDate();
	}

}
