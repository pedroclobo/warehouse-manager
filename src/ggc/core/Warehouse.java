package ggc.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;

import java.io.Serializable;
import java.io.IOException;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.InvalidDateIncrementException;
import ggc.core.exception.UnknownPartnerException;
import ggc.core.exception.DuplicatePartnerException;
import ggc.core.exception.UnknownProductException;
import ggc.core.exception.UnknownTransactionException;
import ggc.core.exception.NoProductStockException;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** Date to keep track of time. */
	private Date _date;

	/** Balance accounting with not yet paid transactions */
	private double _accountingBalance;

	/** Available balance */
	private double _availableBalance;

	/** Collection of all registered products */
	private TreeMap<String, Product> _products;

	/** Collection of all transactions */
	private HashMap<Integer, Transaction> _transactions;

	/** Next transaction identifier. */
	private int _nextTransactionId;

	/** Collection of all registered partners */
	private TreeMap<String, Partner> _partners;

	/**
	 * Create a new warehouse.
	 */
	Warehouse() {
		_date = new Date();
		_accountingBalance = 0;
		_availableBalance = 0;
		_products = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		_transactions = new HashMap<>();
		_nextTransactionId = 0;
		_partners = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	}

	/**
	 * @return the current date value.
	 */
	int getDate() {
		return _date.getDays();
	}

	/**
	 * Forwards time.
	 *
	 * @param increment amount to forward time by.
	 * @throws InvalidDateIncrementException if the amount is not positive.
	 */
	void forwardDate(int increment) throws InvalidDateIncrementException {
		_date.forwardDate(increment);

		updateCreditSalePrices();
		updateAccountingBalance();
	}

	/**
	 * @return the current warehouse's available balance.
	 */
	double getAvailableBalance() {
		return _availableBalance;
	}

	/**
	 * @return the current warehouse's accounting balance.
	 */
	double getAccountingBalance() {
		return _accountingBalance;
	}

	/**
	 * Decreases the warehouse's balance.
	 *
	 * @param value the value to decrease by.
	 */
	void decreaseBalance(double value) {
		_availableBalance -= value;
		updateAccountingBalance();
	}

	/**
	 * Increases the warehouse's balance.
	 *
	 * @param value the value to increase by.
	 */
	void increaseBalance(double value) {
		_availableBalance += value;
		updateAccountingBalance();
	}

	/**
	 * Update the accounting balance.
	 */
	void updateAccountingBalance() {
		double notPaidValue = 0;

		for (Transaction transaction: getTransactions()) {
			if (!transaction.isPaid()) {
				notPaidValue += transaction.getPrice();
			}
		}
		_accountingBalance = _availableBalance + notPaidValue;
	}

	/**
	 * Adds a product to the warehouse's collection.
	 * Also registers all partners as interested in being notified.
	 *
	 * @param product the product to add.
	 */
	void addProduct(Product product) {
		for (Partner partner: _partners.values()) {
			product.addNotifiable(partner);
		}

		_products.put(product.getKey(), product);
	}

	/**
	 * Determines if a product is registered.
	 *
	 * @param key the product key.
	 * @return true if the product with the given key is registered.
	 */
	boolean isRegisteredProduct(String key) {
		return _products.containsKey(key);
	}

	/**
	 * Registers a new simple product.
	 *
	 * @param key the product key.
	 */
	void registerSimpleProduct(String key) {
		addProduct(new SimpleProduct(key));
	}

	/**
	 * Registers a new aggregate product.
	 *
	 * @param key         the product key.
	 * @param aggravation the product aggravation factor.
	 * @param products    a list of products that compose the aggregate product.
	 * @param amounts     a list of the amounts of the products that compose the aggregate product.
	 */
	void registerAggregateProduct(String key, double aggravation, List<Product> products, List<Integer> amounts) {
		addProduct(new AggregateProduct(key, aggravation, products, amounts));
	}

	/**
	 * Returns the product with the given key.
	 *
	 * @param key the product key.
	 * @return the product with the given key.
	 * @throws UnknownProductException if there's no registered product with the given key.
	 */
	Product getProduct(String key) throws UnknownProductException {
		if (!_products.containsKey(key))
			throw new UnknownProductException(key);

		return _products.get(key);
	}

	/**
	 * @return a collection with registered all products.
	 */
	Collection<Product> getProducts() {
		return new ArrayList<>(_products.values());
	}

	/**
	 * @return a collection with all batches.
	 */
	Collection<Batch> getBatches() {
		Set<Batch> batches = new TreeSet<>();

		for (Product p: getProducts())
			batches.addAll(p.getBatches());

		return batches;
	}

	/**
	 * Returns a collection of all batches under the specified price.
	 *
	 * @param price the price to compare to.
	 */
	Collection<Batch> getBatchesUnderGivenPrice(double price) {
		Set<Batch> batches = new TreeSet<>();

		for (Product product: _products.values())
			batches.addAll(product.getBatchesUnderGivenPrice(price));

		return batches;
	}

	/**
	 * Adds a partner to the warehouse's collection.
	 *
	 * @param partner the partner to add.
	 */
	void addPartner(Partner partner) {
		for (Product product: _products.values()) {
			product.addNotifiable(partner);
		}

		_partners.put(partner.getKey(), partner);
	}

	/**
	 * Determines if a partner is registered.
	 *
	 * @param key the partner key.
	 * @return true if the partner with the given key is registered.
	 */
	boolean isRegisteredPartner(String key) {
		return _partners.containsKey(key);
	}

	/**
	 * Registers a partner.
	 *
	 * @param key     the partner key.
	 * @param name    the partner name.
	 * @param address the partner address.
	 * @throws DuplicatePartnerException if there's already a registered partner with the given key.
	 */
	void registerPartner(String key, String name, String address) throws DuplicatePartnerException {
		if (isRegisteredPartner(key))
			throw new DuplicatePartnerException(key);

		Partner partner = new Partner(key, name, address);
		addPartner(partner);
	}

	/**
	 * Returns the product with the given key.
	 *
	 * @param key the partner key.
	 * @return the partner with the given key.
	 * @throws UnknownPartnerException if there's no partner with the given key.
	 */
	Partner getPartner(String key) throws UnknownPartnerException {
		if (!isRegisteredPartner(key))
			throw new UnknownPartnerException(key);

		return _partners.get(key);
	}

	/**
	 * @return a collection with all partners.
	 */
	Collection<Partner> getPartners() {
		return new ArrayList<Partner>(_partners.values());
	}

	/**
	 * Adds a acquisition transaction to the warehouse's collection.
	 * This method also adds the transaction to the partner's collection of transactions.
	 *
	 * @param transaction the acquisition transaction to add.
	 */
	void addTransaction(Acquisition transaction) {
		_transactions.put(transaction.getKey(), transaction);
		transaction.getPartner().addTransaction(transaction);
	}

	/**
	 * Adds a credit sale transaction to the warehouse's collection.
	 * This method also adds the transaction to the partner's collection of transactions.
	 *
	 * @param transaction the credit sale transaction to add.
	 */
	void addTransaction(CreditSale transaction) {
		_transactions.put(transaction.getKey(), transaction);
		transaction.getPartner().addTransaction(transaction);
	}

	/**
	 * Adds a breakdown sale transaction to the warehouse's collection.
	 * This method also adds the transaction to the partner's collection of transactions.
	 *
	 * @param transaction the breakdown sale transaction to add.
	 */
	void addTransaction(BreakdownSale transaction) {
		_transactions.put(transaction.getKey(), transaction);
		transaction.getPartner().addTransaction(transaction);
	}

	/**
	 * Determines if a transaction is registered.
	 *
	 * @param key the transaction key.
	 * @return true if the transaction with the given key is registered.
	 */
	boolean isRegisteredTransaction(int key) {
		return _transactions.containsKey(key);
	}

	/**
	 * Registers a new acquisition transaction.
	 *
	 * @param partner the transaction's partner.
	 * @param product the transaction's product.
	 * @param amount  the transaction's product amount.
	 * @param price   the transaction's product unit price.
	 */
	void registerAcquisition(Partner partner, Product product, int amount, double price) {

		// Notify interested entities.
		if (!product.isNew() && !product.hasStock()) {
			product.sendNotification(new Notification("NEW", product));
		} else if (product.getLowestPrice() > price) {
			product.sendNotification(new Notification("BARGAIN", product));
		}

		// Add transaction to warehouse collection.
		Acquisition transaction = new Acquisition(_nextTransactionId++, partner, product, amount, Date.now(), price * amount);
		addTransaction(transaction);

		// Decrease warehouse's balance by the amount paid.
		decreaseBalance(transaction.getPrice());
	}

	/**
	 * Registers a new credit sale transaction.
	 *
	 * @param partner         the transaction's partner.
	 * @param paymentDeadline the transaction's payment deadline.
	 * @param product         the transaction's product.
	 * @param amount          the transaction's product amount.
	 * @throws NoProductStockException if there's not enough product stock.
	 */
	void registerCreditSale(Partner partner, int paymentDeadline, Product product, int amount) throws NoProductStockException {

		// Check if aggregation is possible.
		try {
			product.checkAggregation(amount);
			for (int i = 0; i < amount; i++) {
				product.aggregate();
			}
		} catch (NoProductStockException e) {
			throw e;
		}

		// Check for product stock.
		if (product.getStock() < amount) {
			throw new NoProductStockException(product.getKey(), amount, product.getStock());
		}

		addTransaction(new CreditSale(_nextTransactionId++, partner, product, amount, new Date(paymentDeadline)));

		updateAccountingBalance();
	}

	/**
	 * Registers a new breakdown sale transaction.
	 *
	 * @param partner the transaction's partner.
	 * @param product the transaction's product.
	 * @param amount  the transaction's product amount.
	 *
	 * @throws NoProductStockException if there's not enough product stock.
	 */
	public void registerBreakdownSale(Partner partner, Product product, int amount) throws NoProductStockException {

		// Check if the product can be disaggregated.
		if (!product.canBeDisaggregated()) {
			return;
		}

		// Register breakdown sale.
		BreakdownSale transaction = new BreakdownSale(_nextTransactionId++, partner, product, amount, _date);
		addTransaction(transaction);

		// Increase warehouse's balance.
		increaseBalance(transaction.getPrice());
	}

	/**
	 * @param key the transaction key.
	 * @return the transaction with the given key.
	 * @throws UnknownTransactionException if there's no transaction with the given key.
	 */
	public Transaction getTransaction(int key) throws UnknownTransactionException {
		if (!isRegisteredTransaction(key))
			throw new UnknownTransactionException(key);

		return _transactions.get(key);
	}

	/**
	 * @return a collection with all partners.
	 */
	public Collection<Transaction> getTransactions() {
		return new ArrayList<Transaction>(_transactions.values());
	}

	/**
	 * Update unpaid credit sale prices.
	 */
	public void updateCreditSalePrices() {
		for (Transaction transaction: getTransactions()) {
			transaction.updatePrice();
		}
	}

	/**
	 * Imports entities from a text file.
	 *
	 * @param txtfile filename to be loaded.
	 * @throws IOException
	 * @throws BadEntryException
	 * @throws UnknownPartnerException
	 * @throws DuplicatePartnerException
	 * @throws UnknownProductException
	 */
	void importFile(String txtfile) throws IOException, BadEntryException, UnknownPartnerException, DuplicatePartnerException, UnknownProductException {
		try {
			new Parser(this).parseFile(txtfile);
		} catch (IOException e) {
			throw e;
		} catch (BadEntryException e) {
			throw e;
		}

		// Clear all partner notifications.
		for (Partner partner: _partners.values()) {
			partner.getNotifications();
		}
	}

}
