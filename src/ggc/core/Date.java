package ggc.core;

import java.io.Serializable;
import ggc.core.exception.InvalidDateIncrementException;

public class Date implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** Global date. */
	private static Date _now;

	private int _days;

	public Date() {
		_now = this;
	}

	public Date(int days) {
		_days = days;
	}

	public int getDays() {
		return _days;
	}

	public int difference(Date other) {
		return _days - other._days;
	}

	/**
	 * @return the current date.
	 */
	public static Date now() {
		return new Date(_now.getDays());
	}

	/**
	 * Fowards the date by the amount specified.
	 */
	public void forwardDate(int days) throws InvalidDateIncrementException {
		if (days <= 0) {
			throw new InvalidDateIncrementException(days);
		}

		_days += days;
	}

	@Override
	public String toString() {
		return "" + _days;
	}

}
