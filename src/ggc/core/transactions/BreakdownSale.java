package ggc.core.transactions;

import ggc.core.products.Product;
import ggc.core.partners.Partner;

public class BreakdownSale extends Sale {
	public BreakdownSale(Partner partner, Product product, int quantity, int paymentDate) {
		super(partner, product, quantity, paymentDate);
	}

	// TODO
	public String toString() {
		return null;
	}
}
