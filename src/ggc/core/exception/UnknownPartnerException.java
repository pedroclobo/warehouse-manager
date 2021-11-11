package ggc.core.exception;

/** Exception for unknown partner keys. */
public class UnknownPartnerException extends Exception {

	/** Unknow key to report. */
	private String _key;

	/** @param key unknown key to report. */
	public UnknownPartnerException(String key) {
		super(Message.unknownPartner(key));
		_key = key;
	}

	/** @return unknow key to report. */
	public String getKey() {
		return _key;
	}

}
