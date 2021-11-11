package ggc.core.partners;

import ggc.core.products.SimpleProduct;
import ggc.core.transactions.Sale;
import ggc.core.transactions.CreditSale;
import ggc.core.transactions.BreakdownSale;

class SelectionStatus extends Status {

	protected SelectionStatus(Partner partner, int points) {
		super(partner, points, Classification.SELECTION);
	}

	protected double getCreditSaleP1Price(double price, int timeDelay) {
		price *= 0.9;

		return price;
	}

	protected double getCreditSaleP2Price(double price, int timeDelay) {
		if (timeDelay <= 2)
			price *= 0.95;

		return price;
	}

	protected double getCreditSaleP3Price(double price, int timeDelay) {
		if (timeDelay > -1)
			price *= 1 + timeDelay * 0.02;

		return price;
	}

	protected double getCreditSaleP4Price(double price, int timeDelay) {
		price *= 1 + timeDelay * 0.05;

		return price;
	}

	protected void applyPontuationPenalties(int timeDelay) {
		return;
	}

}
