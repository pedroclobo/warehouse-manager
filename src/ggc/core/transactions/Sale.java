package ggc.core.transactions;

import ggc.core.products.Product;
import ggc.core.partners.Partner;

/**
 * This class represents a sale transaction.
 */
public abstract class Sale extends Transaction {

	/**
	 * Constructor.
	 *
	 * @param partner     the transaction's associated partner.
	 * @param product     the transaction's processed product.
	 * @param quantity    the quantity of product processed.
	 * @param paymentDate the transaction's payment date.
	 */
	protected Sale(Partner partner, Product product, int quantity, int paymentDate) {
		super(partner, product, quantity, paymentDate);
	}

}
