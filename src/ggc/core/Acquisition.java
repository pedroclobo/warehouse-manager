package ggc.core;

/**
 * This public class represents an acquisition transaction.
 * In an acquisition transaction, the warehouse acquires a product
 * from a partner.
 */
public class Acquisition extends Transaction {

	/** The price paid. */
	private double _price;

	/**
	 * Create a new acquisition.
	 *
	 * @param key         the transaction's key.
	 * @param partner     the transaction's associated partner.
	 * @param product     the transaction's processed product.
	 * @param quantity    the quantity of product processed.
	 * @param paymentDate the transaction's payment date.
	 * @param price       the transaction's price.
	 */
	Acquisition(int key, Partner partner, Product product, int quantity, Date paymentDate, double price) {
		super(key, partner, product, quantity, paymentDate);
		_price = price;

		// Add new stock of product.
		product.add(quantity, partner, price / quantity);

		// Pay the transaction.
		pay();
	}

	/**
	 * @return the transaction's price.
	 */
	@Override
	double getPrice() {
		return _price;
	}

	/**
	 * Pays the acquisition.
	 */
	@Override
	void pay() {}

	/**
	 * Updates the transaction's price.
	 */
	@Override
	void updatePrice() {}

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
			Math.round(_price) + "|" +
			getPaymentDate();
	}

}
