package ggc.core;

public class Sale extends Transaction {
		private double _basePrice;
		private double _effectivePrice;

		public Sale(Partner partner, Product product, int quantity, Date date, double basePrice, double effectivePrice) {
				super(partner, product, quantity, date);
				_basePrice = basePrice;
				_effectivePrice = effectivePrice;
		}
}
