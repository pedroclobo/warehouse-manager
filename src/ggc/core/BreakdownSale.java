package ggc.core;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import ggc.core.exception.NoProductStockException;

/**
 * This public class represents a breakdown sale transaction.
 * In an breakdown sale transaction, a partner requisites a product disaggregation.
 */
public class BreakdownSale extends Sale {

	/** The transaction's base price. */
	private double _basePrice;

	/** The transaction's real, paid price. */
	private double _effectivePrice;

	/** Holds the transaction's main product component prices. */
	private List<Double> _productPrices;

	/**
	 * Constructor.
	 *
	 * @param id          the breakdown sale's identifier.
	 * @param partner     the breakdown sale's associated partner.
	 * @param product     the breakdown sale's processed product.
	 * @param amount      the amount of product processed.
	 * @param paymentDate the breakdown sale's payment date.
	 *
	 * @throws NoProductStockException if there's not enough product stock.
	 */
	BreakdownSale(int id, Partner partner, Product product, int amount, Date paymentDate) throws NoProductStockException {
		super(id, partner, product, amount, paymentDate);
		_basePrice = getProduct().getLowestPrice() * getProductAmount();
		_effectivePrice = 0;
		_productPrices = new ArrayList<>();

		// Check if there is enough product stock.
		if (getProduct().getStock() < getProductAmount())
			throw new NoProductStockException(getProduct().getKey(), getProductAmount(), getProduct().getStock());

		double productPrice = 0;

		// Iterate over all components, to know their insertion prices.
		Iterator<Product> prodIter = getProduct().getProductIterator();
		Iterator<Integer> quantIter = getProduct().getQuantityIterator();

		while (prodIter.hasNext() && quantIter.hasNext()) {
			Product component = prodIter.next();
			int componentAmount = quantIter.next();

			productPrice = (component.hasStock()) ? component.getLowestPrice() : component.getMaxPrice();
			_productPrices.add(productPrice);
			_basePrice -= productPrice * componentAmount * getProductAmount();
		}

		// Calculate effective price.
		_effectivePrice = (_basePrice < 0) ? 0 : _basePrice;

		// Disaggregate product.
		product.disaggregate(amount, partner);

		// Pay the transaction.
		this.pay();
	}

	/**
	 * @return the transaction's paid price.
	 */
	@Override
	double getPrice() {
		return _effectivePrice;
	}

	/**
	 * Updates the transaction price.
	 */
	void updatePrice() {}

	/**
	 * Pays the transaction.
	 */
	@Override
	void pay() {
		getPartner().payTransaction(this);
	}

	/**
	 * String representation of breakdown sale.
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder products = new StringBuilder();

		Iterator<Product> prodIter = getProduct().getProductIterator();
		Iterator<Integer> quantIter = getProduct().getQuantityIterator();
		Iterator<Double> priceIter = _productPrices.iterator();

		// Build products string representation.
		while (prodIter.hasNext() && quantIter.hasNext() && priceIter.hasNext()) {
			Product component = prodIter.next();
			int componentAmount = quantIter.next();
			double price = priceIter.next();

			products.append(component.getKey() + ":" +
					getProductAmount() * componentAmount + ":" +
					Math.round(getProductAmount() * price * componentAmount) + "#");
		}

		// Remove final "#".
		products.deleteCharAt(products.length() - 1);

		return
			"DESAGREGAÇÃO|" +
			getKey() + "|" +
			getPartner().getKey() + "|" +
			getProduct().getKey() + "|" +
			getProductAmount() + "|" +
			Math.round(_basePrice) + "|" +
			Math.round(_effectivePrice) + "|" +
			getPaymentDate() + "|" +
			products;
	}

}
