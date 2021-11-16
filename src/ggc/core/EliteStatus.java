package ggc.core;

/**
 * This public class represents the highest possible status.
 */
public class EliteStatus extends Status {

	/**
	 * Creates a new elite status.
	 */
	EliteStatus(Partner partner, int points) {
		super(partner, points, Classification.ELITE);
	}

	/**
	 * Calculate the credit sale price in the 1st period.
	 *
	 * @param basePrice the credit sale base price.
	 * @param timeDelay the payment delay.
	 */
	double getCreditSaleP1Price(double price, int timeDelay) {
		price *= 0.9;

		return price;
	}

	/**
	 * Calculate the credit sale price in the 2nd period.
	 *
	 * @param basePrice the credit sale base price.
	 * @param timeDelay the payment delay.
	 */
	double getCreditSaleP2Price(double price, int timeDelay) {
		price *= 0.9;

		return price;
	}

	/**
	 * Calculate the credit sale price in the 3rd period.
	 *
	 * @param basePrice the credit sale base price.
	 * @param timeDelay the payment delay.
	 */
	double getCreditSaleP3Price(double price, int timeDelay) {
		price *= 0.95;

		return price;
	}

	/**
	 * Calculate the credit sale price in the 4th period.
	 *
	 * @param basePrice the credit sale base price.
	 * @param timeDelay the payment delay.
	 */
	double getCreditSaleP4Price(double price, int timeDelay) {
		return price;
	}

	/**
	 * Applies the punctuation penalties, according to the payment time delay.
	 *
	 * @param timeDelay the payment time delay.
	 */
	void applyPontuationPenalties(int timeDelay) {
		if (timeDelay > 15) {
			setPoints((int) (getPoints() * 0.25));
			getPartner().changeStatus(new SelectionStatus(getPartner(), getPoints()));
		} else {
			updateStatus();
		}
	}

}
