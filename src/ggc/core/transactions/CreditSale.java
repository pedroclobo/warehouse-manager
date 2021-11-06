package ggc.core.transactions;

import ggc.core.products.Product;
import ggc.core.partners.Partner;

/**
 * Represents a sale with a monetary value associated.
 * This class extends Sale by adding a price to it.
 */
public class CreditSale extends Sale {

	/** The credit sale's base price. */
	private double _basePrice;

	/** The credit sale's real paid price. */
	private double _effectivePrice;

	/** The credit sale's payment deadline date. */
	private int _paymentDeadline;

	/**
	 * Constructor.
	 *
	 * @param partner        the credit sale's associated partner.
	 * @param product        the credit sale's processed product.
	 * @param quantity       the quantity of product processed.
	 * @param paymentDate    the credit sale's payment date.
	 * @param basePrice      the credit sale's base price.
	 * @param effectivePrice the credit sale's real paid price.
	 */
	public CreditSale(Partner partner, int paymentDeadline, Product product, int amount, double basePrice) {
		super(partner, product, amount, -1);
		_basePrice = basePrice;
		_effectivePrice = -1;
		_paymentDeadline = paymentDeadline;
	}

	/**
	 * @return the credit sale's base price.
	 */
	public double getBasePrice() {
		return _basePrice;
	}

	/**
	 * @return the credit sale's real paid value.
	 */
	public double getEffectivePrice() {
		return _effectivePrice;
	}

	/**
	 * Pays the credit sale.
	 */
	/*
	public double pay(int date) {
		int nFactor = 0;
		getProduct() instanceof SimpleProduct ? nFactor = 5 : nFactor = 3;

		if (_limitPaymentDate - date >= nFactor)

		}

	}
	*/

}
