package ggc.core;

import java.io.Serializable;

/** Levels of status public classification. */
enum Classification {
	NORMAL, SELECTION, ELITE;
}

/**
 * This public class is responsible for the partner's point accounting.
 * The partner's status depends on its delay paying its sales.
 */
public abstract class Status implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** The status's partner. */
	private Partner _partner;

	/** Number of points accumulated. */
	private int _points;

	/** Classification status. */
	private Classification _classification;

	/**
	 * Creates a new status.
	 */
	Status(Partner partner, int points, Classification classification) {
		_partner = partner;
		_points = points;
		_classification = classification;
	}

	/**
	 * @return the partner.
	 */
	Partner getPartner() {
		return _partner;
	}

	/**
	 * @return the number of points.
	 */
	int getPoints() {
		return _points;
	}

	/**
	 * Sets the status's point number to the specified value.
	 *
	 * @param points the number of points to set number of points to.
	 */
	void setPoints(int points) {
		_points = points;
	}

	/**
	 * Updates the status public classification, based on the number of points.
	 */
	void updateStatus() {
		if (_points < 2000)
			_partner.changeStatus(new NormalStatus(_partner, _points));

		else if (2000 <= _points && _points < 25000)
			_partner.changeStatus(new SelectionStatus(_partner, _points));

		else
			_partner.changeStatus(new EliteStatus(_partner, _points));
	}

	/**
	 * Calculates the credit sale price, according to the public classification and
	 * payment delay.
	 *
	 * @param transaction the credit sale.
	 */
	double getTransactionPrice(CreditSale transaction) {
		int nFactor = transaction.getProduct().getNTimeFactor();
		int timeDelay = Date.now().difference(transaction.getPaymentDeadline());
		double price = transaction.getBasePrice();

		// P1
		if (timeDelay <= -nFactor)
			price = getCreditSaleP1Price(price, timeDelay);

		// P2
		else if (timeDelay <= 0 && timeDelay > -nFactor) {
			price = getCreditSaleP2Price(price, timeDelay);
		}

		// P3
		else if (0 < timeDelay && timeDelay <= nFactor)
			price = getCreditSaleP3Price(price, timeDelay);

		// P4
		else
			price = getCreditSaleP4Price(price, timeDelay);

		return price;
	}

	/**
	 * Processes the payment of a credit sale.
	 *
	 * @param transaction the credit sale to pay.
	 */
	void payTransaction(CreditSale transaction) {
		int nFactor = transaction.getProduct().getNTimeFactor();
		int timeDelay = Date.now().difference(transaction.getPaymentDeadline());
		double price = transaction.getBasePrice();

		// P1
		if (timeDelay <= -nFactor)
			price = getCreditSaleP1Price(price, timeDelay);

		// P2
		else if (timeDelay <= 0 && timeDelay > -nFactor)
			price = getCreditSaleP2Price(price, timeDelay);

		// P3
		else if (0 < timeDelay && timeDelay <= nFactor)
			price = getCreditSaleP3Price(price, timeDelay);

		// P4
		else
			price = getCreditSaleP4Price(price, timeDelay);

		// If there's no delay.
		if (timeDelay <= 0) {
			_points += 10 * price;
		}

		// Apply pontuation penalties.
		applyPontuationPenalties(timeDelay);
	}

	/**
	 * Processes the payment of a breakdown sale.
	 *
	 * @param transaction the breakdown sale to pay.
	 */
	void payTransaction(BreakdownSale transaction) {
		_points += 10 * transaction.getPrice();
		updateStatus();
	}

	/**
	 * Calculate the credit sale price in the 1st period.
	 *
	 * @param basePrice the credit sale base price.
	 * @param timeDelay the payment delay.
	 */
	abstract double getCreditSaleP1Price(double basePrice, int timeDelay);

	/**
	 * Calculate the credit sale price in the 2nd period.
	 *
	 * @param basePrice the credit sale base price.
	 * @param timeDelay the payment delay.
	 */
	abstract double getCreditSaleP2Price(double basePrice, int timeDelay);

	/**
	 * Calculate the credit sale price in the 3rd period.
	 *
	 * @param basePrice the credit sale base price.
	 * @param timeDelay the payment delay.
	 */
	abstract double getCreditSaleP3Price(double basePrice, int timeDelay);

	/**
	 * Calculate the credit sale price in the 4th period.
	 *
	 * @param basePrice the credit sale base price.
	 * @param timeDelay the payment delay.
	 */
	abstract double getCreditSaleP4Price(double basePrice, int timeDelay);

	/**
	 * Applies the pontuation penalties, according to public classification and payment time delay.
	 *
	 * @param timeDelay the payment time delay.
	 */
	abstract void applyPontuationPenalties(int timeDelay);

	/**
	 * String representation of status.
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return _classification + "|" + _points;
	}

}
