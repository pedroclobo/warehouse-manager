package ggc.core;

public class Batch implements Comparable {
	private Product _product;
	private Partner _partner;
	private int _stock;
	private double _price;

	public Batch(Product product, Partner partner, double price) {
		_product = product;
		_partner = partner;
		_stock = 0;
		_price = price;
	}

	public Product getProduct() {
		return _product;
	}

	public Partner getPartner() {
		return _partner;
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

	public String toString() {
		return _product.getId() + "|" + _partner.getId() + "|" + (int)_price + "|" + _stock;
	}

	public void add(int units) {
		_stock += units;
	}

	public boolean remove(int units) {
		if (_stock >= units) {
			_stock -= units;
			return true;
		}

		return false;
	}

	public int compareTo(Object o) {
		Batch other = (Batch) o;

		if (_product.compareTo(other.getProduct()) == 0) {
			if (_partner.compareTo(other.getPartner()) == 0) {
				if (_price - other.getPrice() == 0) {
					return _stock - other.getStock();
				}
				return (int) (_price - other.getPrice());
			}
			return _partner.compareTo(other.getPartner());

		}
		return _product.compareTo(other.getProduct());
	}
}
