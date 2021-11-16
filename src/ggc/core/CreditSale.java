package ggc.core;

/**
 * This public class represents a credit sale transaction.
 * In a credit sale transaction, a partner acquires some product from the warehouse.
 */
public class CreditSale extends Sale {

	/** The credit sale's base price. */
	private double _basePrice;

	/** The credit sale's real paid price. */
	private double _effectivePrice;

	/** The credit sale's payment deadline date. */
	private Date _paymentDeadline;

	/**
	 * Creates a new credit sale.
	 *
	 * @param key             the credit sale's key.
	 * @param partner         the credit sale's associated partner.
	 * @param product         the credit sale's processed product.
	 * @param quantity        the quantity of product processed.
	 * @param paymentDeadline the credit sale's payment date.
	 */
	CreditSale(int key, Partner partner, Product product, int amount, Date paymentDeadline) {
		super(key, partner, product, amount, null);

		// Remove products and calculate remove price.
		_basePrice = product.remove(amount);

		_paymentDeadline = paymentDeadline;

		// Update price, according to partner status.
		updatePrice();
	}

	/**
	 * @return the credit sale's base price.
	 */
	double getBasePrice() {
		return _basePrice;
	}

	/**
	 * @return the credit sale's price.
	 */
	@Override
	double getPrice() {
		if (isPaid()) {
			return _effectivePrice;
		} else {
			updatePrice();
			return _effectivePrice;
		}
	}

	/**
	 * @return the payment deadline.
	 */
	Date getPaymentDeadline() {
		return _paymentDeadline;
	}

	/**
	 * Pays the transaction.
	 */
	@Override
	void pay() {
		_effectivePrice = getPrice();
		getPartner().payTransaction(this);
		setPaymentDate();
	}

	/**
	 * Updates the transaction price, accouting for discounts.
	 */
	@Override
	public void updatePrice() {
		if (!isPaid()) {
			_effectivePrice = getPartner().getTransactionPrice(this);
		}
	}

	/**
	 * String representation of acquisition.
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String creditSale =
			"VENDA|" +
			getKey() + "|" +
			getPartner().getKey() + "|" +
			getProduct().getKey() + "|" +
			getProductAmount() + "|" +
			Math.round(_basePrice) + "|" +
			Math.round(getPrice()) + "|" +
			_paymentDeadline;

		if (isPaid())
			creditSale += "|" + getPaymentDate();

		return creditSale;
	}

}
