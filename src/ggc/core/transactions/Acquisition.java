package ggc.core.transactions;

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
	 * @param partner     the transaction's associated partner.
	 * @param product     the transaction's processed product.
	 * @param quantity    the quantity of product processed.
	 * @param paymentDate the transaction's payment date.
	 * @param price       the price paid.
	 */
	public Acquisition(Partner partner, Product product, int quantity, int paymentDate, double price) {
		super(partner, product, quantity, paymentDate);
		_price = price;
	}

	/**
	 * @return the acquisition's paid price.
	 */
	public double getPrice() {
		return _price;
	}

	/**
	 * String representation of acquisition.
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "COMPRA|" +
			getId() + "|" +
			getPartner().getId() + "|" +
			getProduct().getId() + "|" +
			getProductQuantity() + "|" +
			getPrice() + "|" +
			getPaymentDate();
	}

}
