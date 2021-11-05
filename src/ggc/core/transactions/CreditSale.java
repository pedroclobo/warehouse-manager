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

	/** The credit sale's limit payment date. */
	private int _limitPaymentDate;

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
	public CreditSale(Partner partner, Product product, int quantity, double basePrice, double effectivePrice, int limitPaymentDate) {
		super(partner, product, quantity, -1);
		_basePrice = basePrice;
		_effectivePrice = effectivePrice;
		_limitPaymentDate = limitPaymentDate;
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
	 * Determines if the credit sale has already been paid.
	 *
	 * @return true, if it has been paid; false, otherwise.
	 */
	public boolean isPaid() {
		return getPaymentDate() != -1;
	}

}
