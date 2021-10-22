package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.util.Map;
import java.util.HashMap;

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
	private Map<String, Product> _products;
	private Map<Integer, Transaction> _transactions;
	private Map<String, Partner> _partners;

	public Warehouse() {
		_accountingBalance = 0;
		_availableBalance = 0;
		_date = new Date();
		_products = new HashMap<>();
		_transactions = new HashMap<>();
		_partners = new HashMap<>();
	}

	public int getDate() {
		return _date.toInt();
	}

	public void fowardDate(int increment) {
		_date.add(increment);
	}

	public void registerPurchase(Partner partner, Product product, int quantity, double price) {
		Purchase p = new Purchase(partner, product, quantity, _date.now(), price);
		addTransaction(p);
		partner.addPurchase(p);

		addProduct(product);
		product.add(quantity, partner, price);
	}

	/*
	public Set getProducts() {
		return new TreeSet<Product>(_products);
	}
	*/

	public void addProduct(Product product) {
		_products.put(product.getId(), product);
	}

	public void removeProduct(Product product) {
		_products.remove(product.getId());
	}

	public void addPartner(Partner partner) {
		_partners.put(partner.getId(), partner);
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
