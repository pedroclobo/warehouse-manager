package ggc.core;

public abstract class Transaction {
	private int _id;
	private String _type; // Really needed?
	private int _quantity;
	private Partner _partner;
	private Product _product;
	private Date _paymentDate;

	public Transaction(int id, String type, int quantity, Partner partner, Product product, Date paymentDate) {
		_id = id;
		_type = type;
		_quantity = quantity;
		_partner = partner;
		_product = product;
		_paymentDate = paymentDate;
	}

	public int getId() {
		return _id;
	}

	public String getType() {
		return _type;
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
