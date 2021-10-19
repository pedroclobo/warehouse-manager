package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.util.Set;
import java.util.HashSet;

import java.io.Serializable;
import java.io.IOException;
import ggc.core.exception.BadEntryException;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	private double _accountingBalance;
	private double _availableBalance;
	private Date _date;
	private Set<Product> _products;
	private Set<Transaction> _transactions;
	private Set<Partner> _partners;

	public Warehouse() {
		_accountingBalance = 0;
		_availableBalance = 0;
		_date = new Date();
		_products = new HashSet<>();
		_transactions = new HashSet<>();
		_partners = new HashSet<>();
	}

	public void addProduct(Product product) {
		_products.add(product);
	}

	public void removeProduct(Product product) {
		_products.remove(product);
	}

	public void addPartner(Partner partner) {
		_partners.add(partner);
	}

	public void addTransaction(Transaction transaction) {
		_transactions.add(transaction);
	}

	public boolean hasStock(Product product) {
		return product.hasStock();
	}

	public boolean aggregate(AggregateProduct product) {}

	public void fowardTime(int increment) {
		_date.add(increment);
	}


	/**
	 * @param txtfile filename to be loaded.
	 * @throws IOException
	 * @throws BadEntryException
	 */
	void importFile(String txtfile) throws IOException, BadEntryException /* FIXME maybe other exceptions */ {
		//FIXME implement method
	}
}
