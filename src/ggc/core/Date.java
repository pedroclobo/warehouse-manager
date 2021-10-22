package ggc.core;

public class Date {
	private int _date;

	public Date() {
		_date = 0;
	}

	public Date(int date) {
		_date = date;
	}

	public void add(int increment) {
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
