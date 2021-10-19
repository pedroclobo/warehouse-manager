package ggc.core;

public abstract class Transaction {
	private int _id;
	private String _type; // Really needed?
	private int _quantity;

	public Transaction(int id, String type, int quantity) {
		_id = id;
		_type = type;
		_quantity = quantity;
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
}
