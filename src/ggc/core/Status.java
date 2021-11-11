package ggc.core;

import java.io.Serializable;








/** Levels of status classification. */
enum Classification {
	NORMAL, SELECTION, ELITE;
}

/**
 * This class is responsible for the partner's point accounting.
 * The partner's status depends on its delay paying its sales.
 */
abstract class Status implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** Each status has a corresponding partner. */
	private Partner _partner;

	/** Number of points accumulated. */
	private int _points;

	/** Classification status. */
	private Classification _classification;

	/**
	 * Registers new status.
	 *
	 * @param partner the partner that holds the status.
	 * @param points the status's number of points.
	 * @param classification the status's classification.
	 */
	protected Status(Partner partner, int points, Classification classification) {
		_partner = partner;
		_points = points;
		_classification = classification;
	}

	/**
	 * @return the number of points.
	 */
	protected int getPoints() {
		return _points;
	}

	/**
	 * Sets the status's point number to the specified value.
	 *
	 * @param points the number of points to set number of points to.
	 */
	protected void setPoints(int points) {
		_points = points;
	}

	/**
	 * Updates the status classification, based on the number of points.
	 */
	protected void updateStatus() {
		if (_points < 2000)
			_classification = Classification.NORMAL;

		else if (2000 <= _points && _points < 25000)
			_classification = Classification.SELECTION;

		else
			_classification = Classification.ELITE;
	}

	/**
	 * Processes the payment of a acquisition transaction.
	 *
	 * @param transaction the acquisition transaction to pay.
	 */
	public void payTransaction(Acquisition transaction) {
		return;
	}

	/**
	 * TODO
	 *
	 * @param sale the credit sale.
	 */
	protected double getTransactionPrice(CreditSale sale) {
		int nFactor = sale.getProduct().getNTimeFactor();
		int timeDelay = Date.now().difference(sale.getPaymentDeadline());
		double price = sale.getBasePrice();

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

		return price;
	}

	/**
	 * Processes the payment of a credit sale.
	 *
	 * @param transaction the credit sale to pay.
	 */
	protected void payTransaction(CreditSale sale) {
		int nFactor = sale.getProduct().getNTimeFactor();
		int timeDelay = Date.now().difference(sale.getPaymentDeadline());
		double price = sale.getBasePrice();

		int points = getPoints();

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

		// TODO
		if (timeDelay <= 0) {
			points += 10 * price;
			setPoints(points);
		}

		applyPontuationPenalties(timeDelay);
	}

	/**
	 * Processes the payment of a breakdown sale.
	 *
	 * @param transaction the breakdown sale to pay.
	 */
	protected void payTransaction(BreakdownSale transaction) {
		_points += 10 * transaction.getPrice();
		updateStatus();
	}

	protected abstract double getCreditSaleP1Price(double basePrice, int timeDelay);
	protected abstract double getCreditSaleP2Price(double basePrice, int timeDelay);
	protected abstract double getCreditSaleP3Price(double basePrice, int timeDelay);
	protected abstract double getCreditSaleP4Price(double basePrice, int timeDelay);
	protected abstract void applyPontuationPenalties(int timeDelay);

	/**
	 * String representation of status.
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return _classification + "|" + _points;
	}

}
