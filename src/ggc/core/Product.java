package ggc.core;

import java.util.TreeSet;

public abstract class Product {
	private String _id;
	private double _maxPrice;
	private TreeSet<Batch> _batches;

	public Product(String id) {
		_id = id;
		_maxPrice = 0;
		_batches = new TreeSet<>(new BatchPriceSorter());
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
		return _id == p.getId();
	}

	public double getLowestPrice() {
		return _batches.first().getPrice();
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

	public void removeBatch(Batch b) {
		_batches.remove(b);
	}

	public void add(int units, Partner partner, double price) {
		if (price > _maxPrice)
			_maxPrice = price;

		// Add to existing batch
		Batch b = getBatchByPartnerAndPrice(partner, price);
		if (b != null)
			b.add(units);

		// Add to new batch
		_batches.add(new Batch(this, partner, price));
	}

	public boolean remove(int units) {
		Batch b = _batches.first();
		int removed = 0;

		/* Impossible operation */
		if (units > getStock())
			return false;

		/* Remove unit 1 by 1 */
		while (removed < units) {
			while (b.remove(1))
				;
			removeBatch(b);
			b = _batches.first();
		}

		return true;
	}
}
