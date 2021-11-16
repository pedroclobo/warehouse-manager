package ggc.core;

/**
 * Intermediate status.
 */
public class SelectionStatus extends Status {

	/**
	 * Creates a new selection status.
	 */
	SelectionStatus(Partner partner, int points) {
		super(partner, points, Classification.SELECTION);
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
		if (timeDelay <= -2) {
			price *= 0.95;
		}

		return price;
	}

	/**
	 * Calculate the credit sale price in the 3rd period.
	 *
	 * @param basePrice the credit sale base price.
	 * @param timeDelay the payment delay.
	 */
	double getCreditSaleP3Price(double price, int timeDelay) {
		if (timeDelay > 1) {
			//price *= 1 + (timeDelay - 1) * 0.02;
			price *= 1 + timeDelay * 0.02;
		}

		return price;
	}

	/**
	 * Calculate the credit sale price in the 4th period.
	 *
	 * @param basePrice the credit sale base price.
	 * @param timeDelay the payment delay.
	 */
	double getCreditSaleP4Price(double price, int timeDelay) {
		price *= 1 + timeDelay * 0.05;

		return price;
	}

	/**
	 * Applies the punctuation penalties, according to the payment time delay.
	 *
	 * @param timeDelay the payment time delay.
	 */
	void applyPontuationPenalties(int timeDelay) {
		if (timeDelay > 2) {
			setPoints((int) (getPoints() * 0.1));
			getPartner().changeStatus(new NormalStatus(getPartner(), getPoints()));
		} else {
			updateStatus();
		}
	}

}
