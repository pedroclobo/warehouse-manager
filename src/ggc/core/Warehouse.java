package ggc.core;

import java.util.Collection;
import java.util.Collections;
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
import ggc.core.transactions.Purchase;
import ggc.core.transactions.Sale;
import ggc.core.exception.BadEntryException;
import ggc.core.exception.InvalidDateIncrementException;
import ggc.core.exception.UnknownPartnerException;
import ggc.core.exception.DuplicatePartnerException;
import ggc.core.exception.UnknownProductException;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** Balance accounting with not yet paid transactions */
	private double _accountingBalance;

	/** Available balance */
	private double _availableBalance;

	/** Date to keep track of time */
	private Date _date;

	/** Collection of all registered products */
	private TreeMap<String, Product> _products;

	/** Collection of all transactions */
	private HashMap<Integer, Transaction> _transactions;

	/** Collection of all registered partners */
	private TreeMap<String, Partner> _partners;

	/**
	 * Create a new warehouse.
	 */
	public Warehouse() {
		_accountingBalance = 0;
		_availableBalance = 0;
		_date = new Date();
		_products = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		_transactions = new HashMap<>();
		_partners = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	}

	/**
	 * @return the current date value.
	 */
	public int getDate() {
		return _date.toInt();
	}

	/**
	 * Fowards time.
	 *
	 * @param increment amount to foward time by.
	 * @throws InvalidDateIncrementException if the amount is not positive.
	 */
	public void fowardDate(int increment) throws InvalidDateIncrementException {
		_date.add(increment);
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
	 * @return a collection with registered all products.
	 */
	public Collection<Product> getProducts() {
		return _products.values();
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
	 * @return a collection with all the batches of the given partner.
	 */
	public Collection<Batch> getBatchesByPartner(String id) throws UnknownPartnerException {
		return getPartner(id).getBatches();
	}

	/*
	public void registerPurchase(Partner partner, Product product, int quantity, double price) {
		Purchase p = new Purchase(partner, product, quantity, _date.now(), price);
		addTransaction(p);
		partner.addPurchase(p);

		addProduct(product);
		product.add(quantity, partner, price);
	}
	*/

	/**
	 * @param id the product id.
	 * @return true if the product with the given id is registered.
	 */
	public boolean isRegisteredProduct(String id) {
		return _products.containsKey(id);
	}

	/**
	 * Registers a new simple product.
	 *
	 * @param id the product id.
	 */
	public void registerSimpleProduct(String id) {
		_products.put(id, new SimpleProduct(id));
	}

	/**
	 * Registers a new aggregate product.
	 *
	 * @param id the product id.
	 * @param aggravation the product aggravation factor.
	 * @param products a list of products that compose the aggregate product.
	 * @param quantities a list of the quantities of the products that compose the aggregate product.
	 */
	public void registerAggregateProduct(String id, double aggravation, List<Product> products, List<Integer> quantities) {
		_products.put(id, new AggregateProduct(id, aggravation, products, quantities));
	}

	/**
	 * @param id the product id.
	 * @return the product with the given id.
	 * @throws UnknownProductException if there's no product with the given id.
	 */
	public Product getProduct(String id) throws UnknownProductException {
		if (!_products.containsKey(id))
			throw new UnknownProductException(id);

		return _products.get(id);
	}

	/**
	 * @param id the product id.
	 * @return a collection with all batches that hold a product.
	 * @throws UnknownProductException if there's no product with the given id.
	 */
	public Collection<Batch> getBatchesByProduct(String id) throws UnknownProductException {
		return getProduct(id).getBatches();
	}

	/**
	 * Registers a partner.
	 *
	 * @param id      the partner id.
	 * @param name    the partner name.
	 * @param address the partner address.
	 * @throws DuplicatePartnerException if there's already a partner with the given id.
	 */
	public void registerPartner(String id, String name, String address) throws DuplicatePartnerException {
		Partner partner = new Partner(id, name, address);

		if (_partners.containsKey(id))
			throw new DuplicatePartnerException(id);

		_partners.put(id, partner);
	}

	/**
	 * @param id the partner id.
	 * @return the partner with the given id.
	 * @throws UnknownPartnerException if there's no partner with the given id.
	 */
	public Partner getPartner(String id) throws UnknownPartnerException {
		if (!_partners.containsKey(id))
			throw new UnknownPartnerException(id);

		return _partners.get(id);
	}

	/**
	 * @return a collection with all partners.
	 */
	public Collection<Partner> getPartners() {
		return _partners.values();
	}

	/**
	 * Gets a collection of all partner's purchases.
	 *
	 * @param id the partner id.
	 * @return a collection of purchases.
	 * @throws UnknownPartnerException if there's no partner with the given id.
	 */
	public Collection<Purchase> getPurchasesByPartner(String id) throws UnknownPartnerException {
		return getPartner(id).getPurchases();
	}

	/**
	 * Gets a collection of all partner's sales.
	 *
	 * @param id the partner id.
	 * @return a collection of sales.
	 * @throws UnknownPartnerException if there's no partner with the given id.
	 */
	public Collection<Sale> getSalesByPartner(String id) throws UnknownPartnerException {
		return getPartner(id).getSales();
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
		} catch (IOException e1) {
			throw e1;
		} catch (BadEntryException e2) {
			throw e2;
		}
	}

}
