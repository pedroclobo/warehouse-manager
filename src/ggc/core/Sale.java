package ggc.core;

public class Sale extends Transaction {
	private boolean _paid;
	private double _basePrice;
	private double _effectivePrice;

	public Sale(Partner partner, Product product, int quantity, Date paymentDate, double basePrice, double effectivePrice) {
		super(partner, product, quantity, paymentDate);
		_paid = false;
		_basePrice = basePrice;
		_effectivePrice = effectivePrice;
	}

	public boolean isPaid() {
		return _paid;
	}

	public double getBasePrice() {
		return _basePrice;
	}

	public double getEffectivePrice() {
		return _effectivePrice;
	}
}
