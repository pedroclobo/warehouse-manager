package ggc.core.partners;

import ggc.core.products.SimpleProduct;
import ggc.core.transactions.Sale;
import ggc.core.transactions.CreditSale;
import ggc.core.transactions.BreakdownSale;

class NormalStatus extends Status {

	protected NormalStatus(Partner partner) {
		super(partner, 0, Classification.NORMAL);
	}

	protected NormalStatus(Partner partner, int points) {
		super(partner, points, Classification.NORMAL);
	}

	protected double getCreditSaleP1Price(double price, int timeDelay) {
		price *= 0.9;

		return price;
	}

	protected double getCreditSaleP2Price(double price, int timeDelay) {
		return price;
	}

	protected double getCreditSaleP3Price(double price, int timeDelay) {
		price *= 1 + timeDelay * 0.05;

		return price;
	}

	protected double getCreditSaleP4Price(double price, int timeDelay) {
		price *= 1 + timeDelay * 0.10;

		return price;
	}

	protected void applyPontuationPenalties(int timeDelay) {
		return;
	}

}
