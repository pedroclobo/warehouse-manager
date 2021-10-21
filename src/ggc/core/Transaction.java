package ggc.core;

public abstract class Transaction {
	private int _id;
	private Partner _partner;
	private Product _product;
	private int _quantity;
	private Date _paymentDate;

	public Transaction(int id, Partner partner, Product product, int quantity, Date paymentDate) {
		_id = id;
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
