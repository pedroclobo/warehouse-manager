package ggc.core;

import java.util.List;
import java.util.ArrayList;

public abstract class Product {
	private String _id;
	private double _maxPrice;
	private List<Batch> _batches;

	public Product(String id) {
		_id = id;
		_maxPrice = 0;
		_batches = new ArrayList<>();
	}

	public final String getId() {
		return _id;
	}

	public boolean hasStock() {
		return getStock() > 0;
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

	public double getLowestPrice() {
		if (_batches.size() > 0) {
			double lowest = _batches.get(0).getPrice();
			for (Batch b: _batches)
				if (b.getPrice() < lowest)
					lowest = b.getPrice();
			return lowest;
		}
		return 0;
	}

	public String toString() {
		return "" + _id + "|" + _maxPrice + "|" + getStock();
	}

	public Batch getBatchByPartnerAndPrice(Partner partner, double price) {
		for (Batch b: _batches)
			if (b.getPartner().equals(partner) && b.getPrice() == price)
				return b;

		return null;
	}

	public void addUnit(int numberUnits, Partner partner, double price) {
		if (price > _maxPrice)
			_maxPrice = price;

		// Add to existing batch
		Batch b = getBatchByPartnerAndPrice(partner, price);
		if (b != null)
			b.addUnit(numberUnits);

		// Add to new batch
		_batches.add(new Batch(this, partner, price));
	}
}
