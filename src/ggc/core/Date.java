package ggc.core;

import java.io.Serializable;
import ggc.core.exception.InvalidDateIncrementException;

/**
 * Represents the date.
 */
public class Date implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** Global date. */
	private static Date _now;

	/** The current day. */
	private int _days;

	/**
	 * Initial constructor.
	 *
	 * Set the static Date _now to the initialized date.
	 * This allows the use of a "global" date.
	 */
	Date() {
		_now = this;
	}

	/**
	 * Creates a new date.
	 *
	 * @param days the current number of days.
	 */
	Date(int days) {
		_days = days;
	}

	/**
	 * @return the current number of days.
	 */
	int getDays() {
		return _days;
	}

	/**
	 * Calculates the time difference between two dates.
	 *
	 * @param other the date to compare to.
	 * @return the time difference in days, to the other date.
	 */
	int difference(Date other) {
		return _days - other._days;
	}

	/**
	 * @return the current date.
	 */
	static Date now() {
		return new Date(_now.getDays());
	}

	/**
	 * Forwards the date by the amount specified.
	 *
	 * @param days the number of days to forward the date by.
	 * @throws InvalidDateIncrementException if the number of specified days in invalid.
	 */
	void forwardDate(int days) throws InvalidDateIncrementException {
		if (days <= 0) {
			throw new InvalidDateIncrementException(days);
		}

		_days += days;
	}

	static void update(Date date) {
		_now = date;
	}

	@Override
	public String toString() {
		return "" + _days;
	}

}
