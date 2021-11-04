package ggc.core.transactions;

import ggc.core.products.Product;
import ggc.core.partners.Partner;
import ggc.core.Date;

public class Acquisition extends Transaction {
	private double _price;

	public Acquisition(Partner partner, Product product, int quantity, Date paymentDate, double price) {
		super(partner, product, quantity, paymentDate);
		_price = price;
	}

	public double getPrice() {
		return _price;
	}

	public String toString() {
		return "COMPRA|" + getId() + "|" + getProduct().getId() + "|" + getQuantity() + "|" +
			getPrice() + "|" + getPaymentDate();
	}
}
