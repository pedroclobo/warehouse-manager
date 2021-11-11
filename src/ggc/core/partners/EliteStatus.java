package ggc.core.partners;

import ggc.core.products.SimpleProduct;
import ggc.core.transactions.Sale;
import ggc.core.transactions.CreditSale;
import ggc.core.transactions.BreakdownSale;

class EliteStatus extends Status {

	protected EliteStatus(Partner partner, int points) {
		super(partner, points, Classification.ELITE);
	}

	protected double getCreditSaleP1Price(double price, int timeDelay) {
		price *= 0.90;

		return price;
	}

	protected double getCreditSaleP2Price(double price, int timeDelay) {
		price *= 0.90;

		return price;
	}

	protected double getCreditSaleP3Price(double price, int timeDelay) {
		price *= 0.95;

		return price;
	}

	protected double getCreditSaleP4Price(double price, int timeDelay) {
		return price;
	}

	protected void applyPontuationPenalties(int timeDelay) {
		return;
	}

}
