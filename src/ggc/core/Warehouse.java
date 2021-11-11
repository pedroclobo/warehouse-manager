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

import ggc.core.products.Product;
import ggc.core.products.AggregateProduct;
import ggc.core.products.SimpleProduct;
import ggc.core.products.Batch;
import ggc.core.partners.Partner;
import ggc.core.transactions.Transaction;
import ggc.core.transactions.Acquisition;
import ggc.core.transactions.Sale;
import ggc.core.transactions.CreditSale;
import ggc.core.transactions.BreakdownSale;
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
	public Warehouse() {
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
	public int getDate() {
		return _date.getDays();
	}

	/**
	 * Forwards time.
	 *
	 * @param increment amount to forward time by.
	 * @throws InvalidDateIncrementException if the amount is not positive.
	 */
	public void forwardDate(int increment) throws InvalidDateIncrementException {
		_date.forwardDate(increment);

		// TODO
		updateCreditSalePrices();
		updateAccountingBalance();
	}

	/**
	 * @return the current warehouse's available balance.
	 */
	public double getAvailableBalance() {
		return _availableBalance;
	}

	/**
	 * @return the current warehouse's accounting balance.
	 */
	public double getAccountingBalance() {
		return _accountingBalance;
	}

	/**
	 * Decreases the warehouse's balance.
	 *
	 * @param value the value to decrease by.
	 */
	public void decreaseBalance(double value) {
		_availableBalance -= value;
		_accountingBalance -= value;
	}

	/**
	 * Increases the warehouse's balance.
	 *
	 * @param value the value to increase by.
	 */
	public void increaseBalance(double value) {
		_availableBalance += value;
		_accountingBalance += value;
	}

	/**
	 * Update the accounting balance.
	 */
	public void updateAccountingBalance() {
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
	 *
	 * @param product the product to add.
	 */
	public void addProduct(Product product) {
		_products.put(product.getKey(), product);
	}

	/**
	 * Determines if a product is registered.
	 *
	 * @param key the product key.
	 * @return true if the product with the given key is registered.
	 */
	public boolean isRegisteredProduct(String key) {
		return _products.containsKey(key);
	}

	/**
	 * Registers a new simple product.
	 *
	 * @param key the product key.
	 */
	public void registerSimpleProduct(String key) {
		addProduct(new SimpleProduct(key));
	}

	/**
	 * Registers a new aggregate product.
	 *
	 * @param key         the product key.
	 * @param aggravation the product aggravation factor.
	 * @param products    a list of products that compose the aggregate product.
	 * @param quantities  a list of the quantities of the products that compose the aggregate product.
	 */
	public void registerAggregateProduct(String key, double aggravation, List<Product> products, List<Integer> quantities) {
		addProduct(new AggregateProduct(key, aggravation, products, quantities));
	}

	/**
	 * Registers a new aggregate product.
	 *
	 * @param key         the product key.
	 * @param aggravation the product aggravation factor.
	 * @param products    a list of products ids that compose the aggregate product.
	 * @param quantities  a list of the quantities of the products that compose the aggregate product.
	 *
	 * @throws UnknownProductException if one of the products that compose the aggregate product isn't registered.
	 */
	public void registerAggregateProduct(String key, double aggravation, Collection<String> productIds, List<Integer> quantities)throws UnknownProductException {

		List<Product> products = new ArrayList<>();
		for (String productId: productIds)
			products.add(getProduct(productId));

		registerAggregateProduct(key, aggravation, products, quantities);
	}

	/**
	 * @param key the product key.
	 * @return the product with the given key.
	 * @throws UnknownProductException if there's no registered product with the given key.
	 */
	public Product getProduct(String key) throws UnknownProductException {
		if (!_products.containsKey(key))
			throw new UnknownProductException(key);

		return _products.get(key);
	}

	/**
	 * @return a collection with registered all products.
	 */
	public Collection<Product> getProducts() {
		return new ArrayList<Product>(_products.values());
	}

	/**
	 * @return a collection with all batches.
	 */
	public Collection<Batch> getBatches() {
		Set<Batch> batches = new TreeSet<>();

		for (Product p: getProducts())
			batches.addAll(p.getBatches());

		return batches;
	}

	/**
	 * Returns a collection of all batches holding the specified product.
	 *
	 * @param key the product key.
	 * @return a collection with all batches that hold the specified product.
	 * @throws UnknownProductException if there's no registered product with the given identifier.
	 */
	public Collection<Batch> getBatchesByProduct(String key) throws UnknownProductException {
		return getProduct(key).getBatches();
	}

	/**
	 * Returns a collection of all batches supplied by the specified partner.
	 *
	 * @param key the product key.
	 * @return a collection with all batches supplied by the specified partner.
	 * @throws UnknownPartnerException if there's no registered partner with the given identifier.
	 */
	public Collection<Batch> getBatchesByPartner(String key) throws UnknownPartnerException {
		return getPartner(key).getBatches();
	}

	/**
	 * Returns a collection of all batches under the specified price.
	 *
	 * @param price the price to compare to.
	 */
	public Collection<Batch> getBatchesUnderGivenPrice(double price) {
		Set<Batch> batches = new TreeSet<>();

		for (Product product: getProducts())
			batches.addAll(product.getBatchesUnderGivenPrice(price));

		return batches;
	}

	/**
	 * Adds a partner to the warehouse's collection.
	 *
	 * @param partner the partner to add.
	 */
	public void addPartner(Partner partner) {
		_partners.put(partner.getKey(), partner);
	}

	/**
	 * Determines if a partner is registered.
	 *
	 * @param key the partner key.
	 * @return true if the partner with the given key is registered.
	 */
	public boolean isRegisteredPartner(String key) {
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
	public void registerPartner(String key, String name, String address) throws DuplicatePartnerException {
		if (isRegisteredPartner(key))
			throw new DuplicatePartnerException(key);

		addPartner(new Partner(key, name, address));
	}

	/**
	 * @param key the partner key.
	 * @return the partner with the given key.
	 * @throws UnknownPartnerException if there's no partner with the given key.
	 */
	public Partner getPartner(String key) throws UnknownPartnerException {
		if (!isRegisteredPartner(key))
			throw new UnknownPartnerException(key);

		return _partners.get(key);
	}

	/**
	 * @return a collection with all partners.
	 */
	public Collection<Partner> getPartners() {
		return new ArrayList<Partner>(_partners.values());
	}

	/**
	 * Returns a collection of all partner's acquisitions.
	 *
	 * @param key the partner key.
	 * @return a collection of acquisitions.
	 * @throws UnknownPartnerException if there's no partner with the given key.
	 */
	public Collection<Acquisition> getAcquisitionsByPartner(String key) throws UnknownPartnerException {
		return getPartner(key).getAcquisitionTransactions();
	}

	/**
	 * Returns a collection of all partner's sales.
	 *
	 * @param key the partner key.
	 * @return a collection of sales.
	 * @throws UnknownPartnerException if there's no partner with the given key.
	 */
	public Collection<Sale> getSalesByPartner(String key) throws UnknownPartnerException {
		return getPartner(key).getSaleTransactions();
	}

	/**
	 * Adds a transaction to the warehouse's collection.
	 * This method also adds the transaction to the partner's collection of transactions.
	 *
	 * @param transaction the transaction to add.
	 */
	public void addTransaction(Transaction transaction) {
		_transactions.put(transaction.getKey(), transaction);
		transaction.getPartner().addTransaction(transaction);
	}

	/**
	 * Determines if a transaction is registered.
	 *
	 * @param key the transaction key.
	 * @return true if the transaction with the given key is registered.
	 */
	public boolean isRegisteredTransaction(int key) {
		return _transactions.containsKey(key);
	}

	/**
	 * Registers a new acquisition transaction.
	 *
	 * @param partnerKey the partner key.
	 * @param productKey the product key.
	 * @param amount     the transaction's product amount.
	 * @param price      the transaction's product unit price.
	 *
	 * @throws UnknownPartnerException if there's no registered partner with the given key.
	 * @throws UnknownProductException if there's no registered product with the given key.
	 */
	public void registerAcquisition(String partnerKey, String productKey, int amount, double price) throws UnknownPartnerException, UnknownProductException {

		// Check if partner and product are registered.
		Partner partner = getPartner(partnerKey);
		Product product = getProduct(productKey);

		// Add transaction to warehouse collection.
		Acquisition transaction = new Acquisition(_nextTransactionId++, partner, product, amount, Date.now(), price * amount);
		addTransaction(transaction);

		// Decrease warehouse's balance by the amount paid.
		decreaseBalance(transaction.getPrice());
	}

	/**
	 * Registers a new credit sale transaction.
	 *
	 * @param partnerKey      the partner key.
	 * @param paymentDeadline the transaction's payment deadline.
	 * @param productKey      the product key.
	 * @param amount          the transaction's product amount.
	 *
	 * @throws UnknownPartnerException if there's no registered partner with the given key.
	 * @throws UnknownProductException if there's no registered product with the given key.
	 * @throws NoProductStockException if there's not enough product stock.
	 */
	public void registerCreditSale(String partnerKey, int paymentDeadline, String productKey, int amount) throws UnknownPartnerException, UnknownProductException, NoProductStockException {

		// Check if partner and product are registered.
		Partner partner = getPartner(partnerKey);
		Product product = getProduct(productKey);

		// Check for product stock.
		if (product.getStock() < amount) {
			throw new NoProductStockException(product.getKey(), amount, product.getStock());
		}

		// Check if aggregation is possible.
		try {
			product.checkAggregation(amount);
		} catch (NoProductStockException e) {
			throw e;
		}

		addTransaction(new CreditSale(_nextTransactionId++, partner, product, amount, new Date(paymentDeadline)));
	}

	/**
	 * Registers a new breakdown sale transaction.
	 *
	 * @param partnerKey the partner key.
	 * @param productKey the product key.
	 * @param amount     the transaction's product amount.
	 *
	 * @throws UnknownPartnerException if there's no registered partner with the given key.
	 * @throws UnknownProductException if there's no registered product with the given key.
	 * @throws NoProductStockException if there's not enough product stock.
	 */
	public void registerBreakdownSale(String partnerKey, String productKey, int amount) throws UnknownPartnerException, UnknownProductException, NoProductStockException {

		// Check if partner and product are registered.
		Partner partner = getPartner(partnerKey);
		Product product = getProduct(productKey);

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
	 * Processes the credit sale payment.
	 *
	 * @param key the transaction key.
	 * @throws UnknownTransactionException if there's no transaction with the given key.
	 */
	public void receiveCreditSalePayment(int key) throws UnknownTransactionException {
		getTransaction(key).pay();
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
	 * Returns a collection of all partner's paid transactions.
	 *
	 * @param key the partner key.
	 * @return a collection of sales.
	 * @throws UnknownPartnerException if there's no partner with the given key.
	 */
	public Collection<Sale> getPartnerPaidTransactions(String key) throws UnknownPartnerException {
		return getPartner(key).getPaidTransactions();
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
	}

}
