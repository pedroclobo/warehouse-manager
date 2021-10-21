package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;

import java.io.Serializable;
import java.io.IOException;
import ggc.core.exception.BadEntryException;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	private int _numberTransactions;
	private double _accountingBalance;
	private double _availableBalance;
	private Date _date;
	private Set<Product> _products;
	private Set<Transaction> _transactions;
	private Set<Partner> _partners;

	public Warehouse() {
		_numberTransactions = 0;
		_accountingBalance = 0;
		_availableBalance = 0;
		_date = new Date();
		_products = new HashSet<>();
		_transactions = new HashSet<>();
		_partners = new HashSet<>();
	}

	public void registerPurchase(Partner partner, Product product, int quantity, double price) {
		Purchase p = new Purchase(_numberTransactions++, partner, product, quantity, _date.now(), price);
		_transactions.add(p);
		partner.addPurchase(p);

		addProduct(product);
		product.addUnit(quantity, partner, price);
	}

	public Set getProducts() {
		return new TreeSet<Product>(_products);
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
//		finalProduct.addUnit(finalQuantity);
//
//		return true;
//	}

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
