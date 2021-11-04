package ggc.core.transactions;

import ggc.core.products.Product;
import ggc.core.partners.Partner;
import ggc.core.Date;

public class Sale extends Transaction {

	public Sale(Partner partner, Product product, int quantity, Date paymentDate) {
		super(partner, product, quantity, paymentDate);

	}
}
