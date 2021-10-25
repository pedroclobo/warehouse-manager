package ggc.core;

import java.io.Serializable;
import ggc.core.exception.InvalidDateIncrementException;

public class Date implements Serializable {
	private static final long serialVersionUID = 202109192006L;
	private int _date;

	public Date() {
		_date = 0;
	}

	public Date(int date) {
		_date = date;
	}

	public void add(int increment) throws InvalidDateIncrementException {
		if (increment <= 0)
			throw new InvalidDateIncrementException(increment);

		_date += increment;
	}

	public int difference(Date other) {
		return _date - other._date;
	}

	public Date now() {
		return new Date(_date);
	}

	public int toInt() {
		return _date;
	}
}
