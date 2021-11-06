package ggc.core.transactions;

import java.util.Iterator;

import ggc.core.products.Product;
import ggc.core.products.AggregateProduct;
import ggc.core.partners.Partner;

/**
 * TODO
 */
public class BreakdownSale extends Sale {

	private double _basePrice;
	private double _effectivePrice;

	/**
	 * Constructor.
	 *
	 * @param partner     the breakdown sale's associated partner.
	 * @param product     the breakdown sale's processed product.
	 * @param quantity    the quantity of product processed.
	 * @param paymentDate the breakdown sale's payment date.
	 */
	public BreakdownSale(Partner partner, Product product, int quantity, int paymentDate) {
		super(partner, product, quantity, paymentDate);
		_basePrice = 0;
		_effectivePrice = 0;

		this.calculateBreakdownSalePrice();
	}

	private double getInsertionPrice() {
		Iterator<Product> prodIter = ((AggregateProduct) getProduct()).getProductIterator();
		Iterator<Integer> quantIter = ((AggregateProduct) getProduct()).getQuantityIterator();
		double total = 0;
		double productPrice = 0;

		while (prodIter.hasNext() && quantIter.hasNext()) {
			Product product = prodIter.next();
			int quantity = quantIter.next();

			if (product.hasStock())
				productPrice = product.getLowestPrice();
			else
				productPrice = product.getMaxPrice();

			total += productPrice * quantity;
		}

		return total;
	}

	private void calculateBreakdownSalePrice() {
		_basePrice = getProduct().getLowestPrice() * getProductQuantity() - getInsertionPrice();

		if (_basePrice < 0)
			_effectivePrice = 0;
		else
			_effectivePrice = _basePrice;
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
