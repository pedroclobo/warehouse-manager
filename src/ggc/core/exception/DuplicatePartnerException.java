package ggc.core.exception;

/** Exception thrown when a partner key is duplicated. */
public class DuplicatePartnerException extends Exception {

	/** The duplicate key. */
	private String _key;

	/** @param key the duplicated key. */
	public DuplicatePartnerException(String key) {
		super(Message.duplicatePartner(key));
		_key = key;
	}

	/** @return the duplicate key. */
	public String getKey() {
		return _key;
	}

}
