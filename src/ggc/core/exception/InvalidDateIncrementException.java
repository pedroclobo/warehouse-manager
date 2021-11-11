package ggc.core.exception;

/** Exception for date-related problems. */
public class InvalidDateIncrementException extends Exception {

	/** Bad date to report. */
	private int _date;

	/** @param date bad date to report. */
	public InvalidDateIncrementException(int date) {
		super(Message.invalidDateIncrement(date));
		_date = date;
	}

	/** @return bad date to report. */
	public int getDate() {
		return _date;
	}

}
