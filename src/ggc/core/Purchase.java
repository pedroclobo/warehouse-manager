package ggc.core;

public class Purchase extends Transaction {
	private float _price;

	public Purchase(int id, String type, int quantity, float price) {
		super(id, type, quantity);
		_price = price;
	}
}
