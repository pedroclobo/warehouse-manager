package ggc.core;

/**
 * This class represents a sale transaction.
 */
public abstract class Sale extends Transaction {

	/**
	 * Constructor.
	 *
	 * @param key         the transaction's key.
	 * @param partner     the transaction's associated partner.
	 * @param product     the transaction's processed product.
	 * @param quantity    the quantity of product processed.
	 * @param paymentDate the transaction's payment date.
	 */
	protected Sale(int key, Partner partner, Product product, int quantity, Date paymentDate) {
		super(key, partner, product, quantity, paymentDate);
	}

}
