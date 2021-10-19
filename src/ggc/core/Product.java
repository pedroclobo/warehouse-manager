package ggc.core;

import java.util.LinkedList;

public abstract class Product {
	private String _id;
	private double _maxPrice;
	private LinkedList<Batch> _batches;

	public Product(String id) {
		_id = id;
		_maxPrice = 0;
		_batches = new LinkedList<>();
	}

	public final String getId() {
		return _id;
	}

	public int getStock() {
		int stock = 0;

		for (Batch b: _batches) {
			stock += b.getStock();
		}

		return stock;
	}

	public boolean equals(Product p) {
		return _id == p._id;
	}

	public String toString() {
		return "" + _id + "|" + _maxPrice + "|" + getStock();
	}

	public void addUnit(int numberUnits, Partner supplier, double price) {

		// Update maxPrice
		if (price > _maxPrice)
			_maxPrice = price;

		// Add to existing batch
		for (Batch b: _batches) {
			if (b.getSupplier().equals(supplier) && b.getPrice() == price) {
				b.addUnit(numberUnits);
				return;
			}
		}

		// Add to new batch
		_batches.add(new Batch(this, supplier, price));
	}

	public abstract Product copy();
}
