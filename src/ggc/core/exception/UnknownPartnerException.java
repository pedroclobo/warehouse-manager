package ggc.core.exception;

/** Exception for unknown partner keys. */
public class UnknownPartnerException extends Exception {

	/** @param key Unknown key to report. */
	public UnknownPartnerException(String key) {
		super("Unknown partner key: " + key);
	}
}
