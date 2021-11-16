package ggc.core;

/**
 * Low status.
 */
public class NormalStatus extends Status {

	/**
	 * Creates a new normal status.
	 */
	NormalStatus(Partner partner, int points) {
		super(partner, points, Classification.NORMAL);
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
		return price;
	}

	/**
	 * Calculate the credit sale price in the 3rd period.
	 *
	 * @param basePrice the credit sale base price.
	 * @param timeDelay the payment delay.
	 */
	double getCreditSaleP3Price(double price, int timeDelay) {
		price *= (1 + (timeDelay * 0.05));

		return price;
	}

	/**
	 * Calculate the credit sale price in the 4th period.
	 *
	 * @param basePrice the credit sale base price.
	 * @param timeDelay the payment delay.
	 */
	double getCreditSaleP4Price(double price, int timeDelay) {
		price *= (1 + (timeDelay * 0.10));

		return price;
	}

	/**
	 * Applies the punctuation penalties, according to the payment time delay.
	 *
	 * @param timeDelay the payment time delay.
	 */
	void applyPontuationPenalties(int timeDelay) {
		if (timeDelay > 0) {
			setPoints(0);
		}
		updateStatus();
	}

}
