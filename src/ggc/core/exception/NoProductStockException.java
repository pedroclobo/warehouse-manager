package ggc.core.exception;

/** Exception thrown when a product is unavailable. */
public class NoProductStockException extends Exception {

	/**
	 * @param id        The requested product id.
	 * @param requested Requested amount.
	 * @param available Available amount.
	 */
	public NoProductStockException(String id, int requested, int available) {
		super(Message.unavailable(id, requested, available));
	}

}
