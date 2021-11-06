package ggc.core.exception;

/** Exception for unknown partner keys. */
public class UnknownPartnerException extends Exception {

	private String _key;

	/** @param key Unknown key to report. */
	public UnknownPartnerException(String key) {
		super(Message.unknownPartnerKey(key));
		_key = key;
	}

	public String getKey() {
		return _key;
	}

}
