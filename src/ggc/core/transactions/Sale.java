package ggc.core.transactions;

import ggc.core.products.Product;
import ggc.core.partners.Partner;

public class Sale extends Transaction {

	public Sale(Partner partner, Product product, int quantity, int paymentDate) {
		super(partner, product, quantity, paymentDate);

	}
}
