package ggc.core;

public class Batch {
	private Product _product;
	private Partner _supplier;
	private int _stock;
	private double _price;

	// Quantity starts at 0?
	public Batch(Product product, Partner supplier, double price) {
		_product = product;
		_supplier = supplier;
		_stock = 0;
		_price = price;
	}

	// To avoid privacy leaks
	public Product getProduct() {
		return _product.copy();
	}

	// To avoid privacy leaks
	public Partner getSupplier() {
		return _supplier.copy();
	}

	public int getStock() {
		return _stock;
	}

	public double getPrice() {
		return _price;
	}

	public boolean isEmpty() {
		return _stock == 0;
	}

	public void addUnit(int numberUnits) {
		_stock += numberUnits;
	}

	public boolean removeUnit(int numberUnits) {
		if (_stock >= numberUnits) {
			_stock -= numberUnits;
			return true;
		}

		return false;
	}
}
