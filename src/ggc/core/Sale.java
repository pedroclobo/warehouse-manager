package ggc.core;

/**
 * This public class represents a sale transaction.
 * Each subclass especifies a concrete implementation.
 */
public abstract class Sale extends Transaction {

	/**
	 * Constructor.
	 *
	 * @param key         the transaction's key.
	 * @param partner     the transaction's associated partner.
	 * @param product     the transaction's processed product.
	 * @param amount      the amount of product processed.
	 * @param paymentDate the transaction's payment date.
	 */
	Sale(int key, Partner partner, Product product, int amount, Date paymentDate) {
		super(key, partner, product, amount, paymentDate);
	}

}
