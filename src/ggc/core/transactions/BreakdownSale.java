package ggc.core.transactions;

import ggc.core.products.Product;
import ggc.core.partners.Partner;
import ggc.core.Date;

public class BreakdownSale extends Sale {
	public BreakdownSale(Partner partner, Product product, int quantity, Date paymentDate) {
		super(partner, product, quantity, paymentDate);
	}

	// TODO
	public String toString() {
		return null;
	}
}
