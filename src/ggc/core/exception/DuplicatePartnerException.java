package ggc.core.exception;

/** Exception thrown when a partner key is duplicated. */
public class DuplicatePartnerException extends Exception {

	/** @param key the duplicated key */
	public DuplicatePartnerException(String key) {
		super("Duplicate partner key: " + key);
	}
}
