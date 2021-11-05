package ggc.core.transactions;

import ggc.core.products.Product;
import ggc.core.partners.Partner;

/**
 * TODO
 */
public class BreakdownSale extends Sale {

	/**
	 * Constructor.
	 *
	 * @param partner        the breakdown sale's associated partner.
	 * @param product        the breakdown sale's processed product.
	 * @param quantity       the quantity of product processed.
	 * @param paymentDate    the breakdown sale's payment date.
	 */
	public BreakdownSale(Partner partner, Product product, int quantity, int paymentDate) {
		super(partner, product, quantity, paymentDate);
	}

	/**
	 * String representation of breakdown sale.
	 *
	 * @see java.lang.Object#toString()
	 */
	// TODO
	public String toString() {
		return null;
	}

}
