package ggc.core.transactions;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import ggc.core.Date;
import ggc.core.products.Product;
import ggc.core.products.AggregateProduct;
import ggc.core.partners.Partner;
import ggc.core.exception.NoProductStockException;

/**
 * TODO
 */
public class BreakdownSale extends Sale {

	private double _basePrice;
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
	 */
	public BreakdownSale(int id, Partner partner, Product product, int amount, Date paymentDate) throws NoProductStockException {
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

	@Override
	public double getPrice() {
		return _effectivePrice;
	}

	/**
	 * Updates the transaction price, accouting for discounts.
	 *
	 * @param date the current date.
	 */
	public void updatePrice() {}

	/**
	 * TODO
	 */
	@Override
	public void pay() {
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

		while (prodIter.hasNext() && quantIter.hasNext() && priceIter.hasNext()) {
			Product component = prodIter.next();
			int componentAmount = quantIter.next();
			double price = priceIter.next();

			products.append(component.getKey() + ":" +
					getProductAmount() * componentAmount + ":" +
					(int) (getProductAmount() * price * componentAmount) + "#");
		}

		// Remove final "#".
		products.deleteCharAt(products.length() - 1);

		return
			"DESAGREGAÇÃO|" +
			getKey() + "|" +
			getPartner().getKey() + "|" +
			getProduct().getKey() + "|" +
			getProductAmount() + "|" +
			(int) _basePrice + "|" +
			(int) _effectivePrice + "|" +
			getPaymentDate() + "|" +
			products;
	}

}
