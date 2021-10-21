package ggc.core;

public class Purchase extends Transaction {
	private double _price;

	public Purchase(int id, Partner partner, Product product, int quantity, Date paymentDate, double price) {
		super(id, partner, product, quantity, paymentDate);
		_price = price;
	}

	public double getPrice() {
		return _price;
	}
}
