package ggc.core.exception;

/** Exception for date-related problems. */
public class InvalidDateIncrementException extends Exception {

	/** @param date bad date to report. */
	public InvalidDateIncrementException(int date) {
		super("Invalid date: " + date);
	}

}
