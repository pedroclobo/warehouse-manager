package ggc.core.transactions;

import ggc.core.products.Product;
import ggc.core.partners.Partner;
import ggc.core.Date;

public abstract class Transaction {
	private static int _numberTransactions = 0;
	private int _id;
	private Partner _partner;
	private Product _product;
	private int _quantity;
	private Date _paymentDate;

	public Transaction(Partner partner, Product product, int quantity, Date paymentDate) {
		_id = _numberTransactions++;
		_partner = partner;
		_product = product;
		_quantity = quantity;
		_paymentDate = paymentDate;
	}

	public int getId() {
		return _id;
	}

	public int getQuantity() {
		return _quantity;
	}

	public Partner getPartner() {
		return _partner;
	}

	public Product getProduct() {
		return _product;
	}

	public Date getPaymentDate() {
		return _paymentDate;
	}
}
