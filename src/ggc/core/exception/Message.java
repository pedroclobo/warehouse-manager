package ggc.core.exception;

/** Messages for error reporting. */
interface Message {

	/**
	 * @param date bad date.
	 * @return string with problem description.
	 */
	public static String invalidDateIncrement(int date) {
		return "Data inválida: " + date;
	}

	/**
	 * @param key
	 * @return string with problem description.
	 */
	static String unknownPartner(String key) {
		return "O parceiro '" + key + "' não existe.";
	}

	/**
	 * @param key partner key
	 * @return string reporting a duplicate partner
	 */
	static String duplicatePartner(String key) {
		return "O parceiro '" + key + "' já existe.";
	}

	/**
	 * @param key
	 * @return string with problem description.
	 */
	static String unknownProduct(String key) {
		return "O produto '" + key + "' não existe.";
	}

	/**
	 * @param product	 key
	 * @param requested
	 * @param available
	 * @return string with requested quantity.
	 */
	static String unavailable(String key, int requested, int available) {
		return "Produto '" + key + "': pedido=" + requested + ", existências=" + available;
	}

	/**
	 * @param key
	 * @return string with problem description.
	 */
	static String unknownTransaction(int key) {
		return "A transacção '" + key + "' não existe.";
	}

	/**
	 * @param key
	 * @return string with problem description.
	 */
	public static String duplicateTransaction(int key) {
		return "A transacção '" + key + "' já existe.";
	}

}
