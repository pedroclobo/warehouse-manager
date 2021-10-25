package ggc.core;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

import java.io.Serializable;
import java.io.IOException;
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

	private double _accountingBalance;
	private double _availableBalance;
	private Date _date;
	private TreeMap<String, Product> _products;
	private HashMap<Integer, Transaction> _transactions;
	private TreeMap<String, Partner> _partners;

	public Warehouse() {
		_accountingBalance = 0;
		_availableBalance = 0;
		_date = new Date();
		_products = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		_transactions = new HashMap<>();
		_partners = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	}

	public int getDate() {
		return _date.toInt();
	}

	public void fowardDate(int increment) throws InvalidDateIncrementException {
		_date.add(increment);
	}

	public double getAvailableBalance() {
		return _availableBalance;
	}

	public double getAccountingBalance() {
		return _accountingBalance;
	}

	public Collection getProducts() {
		return _products.values();
	}

	public Collection<Batch> getBatches() {
		TreeSet<Batch> batches = new TreeSet<>();

		for (Product p: _products.values())
			batches.addAll(p.getBatches());

		return batches;
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

	/* Return true if product is registered */
	public boolean isRegisteredProduct(String id) {
		return _products.containsKey(id);
	}

	public void registerSimpleProduct(String id) {
		_products.put(id, new SimpleProduct(id));
	}

	public void registerAggregateProduct(String id, double aggravation, List<Product> products, List<Integer> quantities) {
		_products.put(id, new AggregateProduct(id, aggravation, products, quantities));
	}

	public Product getProduct(String id) throws UnknownProductException {
		if (!_products.containsKey(id))
			throw new UnknownProductException(id);

		return _products.get(id);
	}

	public Collection getBatchesByProduct(String id) throws UnknownProductException {
		return getProduct(id).getBatches();
	}

	public void registerPartner(String id, String name, String address) throws DuplicatePartnerException {
		Partner partner = new Partner(id, name, address);

		if (_partners.containsKey(id))
			throw new DuplicatePartnerException(id);

		_partners.put(id, partner);
	}

	public Partner getPartner(String id) throws UnknownPartnerException {
		if (!_partners.containsKey(id))
			throw new UnknownPartnerException(id);

		return _partners.get(id);
	}

	public Collection getPartners() {
		return _partners.values();
	}

	public void addTransaction(Transaction transaction) {
		_transactions.put(transaction.getId(), transaction);
	}

	public boolean hasStock(Product product) {
		return product.hasStock();
	}

//	/**
//	 * @param finalProduct product to be aggregated.
//	 * @param finalQuantity quantity of finalProduct to be produced.
//	 */
//	public boolean aggregate(AggregateProduct finalProduct, int finalQuantity) {
//		ArrayList<Component> components = product.getComponents();
//
//		/* Check if there's enough product stock */
//		for (Component c: components) {
//			Product product = c.getProduct();
//			int quantity = finalQuantity * c.getQuantity();
//			if (product.getStock() < quantity)
//				return false;
//		}
//
//		for (Component c: components) {
//			Product product = c.getProduct();
//			int quantity = finalQuantity * c.getQuantity();
//			product.removeUnit(quantity);
//		}
//
//		/* TODO Price */
//		finalProduct.add(finalQuantity);
//
//		return true;
//	}

	/**
	 * @param txtfile filename to be loaded.
	 * @throws IOException
	 * @throws BadEntryException
	 */
	void importFile(String txtfile) throws IOException, BadEntryException, UnknownPartnerException, DuplicatePartnerException, UnknownProductException /* FIXME maybe other exceptions */ {
		Parser p = new Parser(this);
		try {
			p.parseFile(txtfile);
		} catch (IOException e1) {
			throw e1;
		} catch (BadEntryException e2) {
			throw e2;
		}
	}
}
