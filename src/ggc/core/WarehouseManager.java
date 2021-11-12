package ggc.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.MissingFileAssociationException;
import ggc.core.exception.InvalidDateIncrementException;
import ggc.core.exception.UnknownPartnerException;
import ggc.core.exception.DuplicatePartnerException;
import ggc.core.exception.UnknownProductException;
import ggc.core.exception.UnknownTransactionException;
import ggc.core.exception.NoProductStockException;

/** Façade for access. */
public class WarehouseManager {

	/** Name of file storing current warehouse. */
	private String _filename = "";

	/** The warehouse itself. */
	private Warehouse _warehouse = new Warehouse();

	/**
	 * @return the associated file name.
	 */
	public String getFilename() {
		return _filename;
	}

	/**
	 * Sets the file name.
	 *
	 * @param filename name of file to store current warehouse in.
	 */
	public void setFilename(String filename) {
		_filename = filename;
	}

	/**
	 * @return the current date value.
	 */
	public int getDate() {
		return _warehouse.getDate();
	}

	/**
	 * Forwards time.
	 *
	 * @param increment amount to forward time by.
	 * @throws InvalidDateIncrementException if the amount is not positive.
	 */
	public void forwardDate(int increment) throws InvalidDateIncrementException {
		_warehouse.forwardDate(increment);
	}

	/**
	 * @return the current warehouse's available balance.
	 */
	public double getAvailableBalance() {
		return _warehouse.getAvailableBalance();
	}

	/**
	 * @return the current warehouse's accounting balance.
	 */
	public double getAccountingBalance() {
		return _warehouse.getAccountingBalance();
	}

	/**
	 * Registers a new simple product.
	 *
	 * @param id the product id.
	 */
	public void registerSimpleProduct(String id) {
		_warehouse.registerSimpleProduct(id);
	}

	/**
	 * Registers a new aggregate product.
	 *
	 * @param id the product id.
	 * @param aggravation the product aggravation factor.
	 * @param products a list of products ids that compose the aggregate product.
	 * @param quantities a list of the quantities of the products that compose the aggregate product.
	 */
	public void registerAggregateProduct(String id, double aggravation, Collection<String> productIds, List<Integer> quantities)throws UnknownProductException {

		List<Product> products = new ArrayList<>();

		for (String productId: productIds) {
			products.add(getProduct(productId));
		}

		_warehouse.registerAggregateProduct(id, aggravation, products, quantities);
	}

	/**
	 * Returns the product with the given key.
	 *
	 * @param key the product key.
	 * @return the product with the given key.
	 * @throws UnknownProductException if there's no registered product with the given key.
	 */
	public Product getProduct(String id) throws UnknownProductException {
		return _warehouse.getProduct(id);
	}


	/**
	 * @return a collection with all registered products.
	 */
	public Collection<Product> getProducts() {
		return _warehouse.getProducts();
	}

	/**
	 * @return a collection with all batches.
	 */
	public Collection<Batch> getBatches() {
		return _warehouse.getBatches();
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
		return _warehouse.getBatchesUnderGivenPrice(price);
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
		_warehouse.registerPartner(key, name, address);
	}

	/**
	 * Returns the product with the given key.
	 *
	 * @param key the partner key.
	 * @return the partner with the given key.
	 * @throws UnknownPartnerException if there's no partner with the given key.
	 */
	public Partner getPartner(String key) throws UnknownPartnerException {
		return _warehouse.getPartner(key);
	}

	/**
	 * @return a collection with all partners
	 */
	public Collection<Partner> getPartners() {
		return _warehouse.getPartners();
	}

	/**
	 * Return a collection with all partner's notifications.
	 *
	 * @param key the partner's key.
	 * @return a collection of the partner's notifications.
	 */
	public Collection<Notification> getPartnerNotifications(String key) throws UnknownPartnerException {
		return getPartner(key).getNotifications();
	}

	/**
	 * Toggles partner notifications for a certain product.
	 *
	 * @param partnerKey the partner key.
	 * @param productKey the product key to toggle notifications on or off.
	 * @throws UnknownPartnerException if there's no partner with the given key.
	 * @throws UnknownProductException if there's no product with the given key.
	 */
	public void toggleNotifications(String partnerKey, String productKey) throws UnknownPartnerException, UnknownProductException {
		Product product = getProduct(productKey);
		Partner partner = getPartner(partnerKey);

		if (product.isRegisteredNotifiable(partner)) {
			getProduct(productKey).removeNotifiable(partner);
		} else {
			getProduct(productKey).addNotifiable(partner);
		}
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
	public void registerAcquisitionTransaction(String partnerKey, String productKey, int amount, double price) throws UnknownPartnerException, UnknownProductException {

		// Check if partner and product are registered.
		Partner partner = getPartner(partnerKey);
		Product product = getProduct(productKey);

		_warehouse.registerAcquisition(partner, product, amount, price);
	}

	/**
	 * Registers a new sale transaction.
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
	public void registerSaleTransaction(String partnerKey, int paymentDeadline, String productKey, int amount) throws UnknownPartnerException, UnknownProductException, NoProductStockException {

		// Check if partner and product are registered.
		Partner partner = getPartner(partnerKey);
		Product product = getProduct(productKey);

		_warehouse.registerCreditSale(partner, paymentDeadline, product, amount);
	}

	/**
	 * Processes the credit sale payment.
	 *
	 * @param key the transaction key.
	 * @throws UnknownTransactionException if there's no transaction with the given key.
	 */
	public void receivePayment(int key) throws UnknownTransactionException {
		getTransaction(key).pay();
	}

	/**
	 * Registers a new breakdown transaction.
	 *
	 * @param partnerKey      the partner key.
	 * @param productKey      the product key.
	 * @param amount          the transaction's product amount.
	 *
	 * @throws UnknownPartnerException if there's no registered partner with the given key.
	 * @throws UnknownProductException if there's no registered product with the given key.
	 * @throws NoProductStockException if there's not enough product stock.
	 */
	public void registerBreakdownTransaction(String partnerKey, String productKey, int amount) throws UnknownPartnerException, UnknownProductException, NoProductStockException {

		// Check if partner and product are registered.
		Partner partner = getPartner(partnerKey);
		Product product = getProduct(productKey);

		_warehouse.registerBreakdownSale(partner, product, amount);
	}

	/**
	 * @param key the transaction key.
	 * @return the transaction with the given key.
	 * @throws UnknownTransactionException if there's no transaction with the given key.
	 */
	public Transaction getTransaction(int id) throws UnknownTransactionException {
		return _warehouse.getTransaction(id);
	}

	/**
	 * Saves the current state of the warehouse to the associated filename.
	 *
	 * @@throws IOException
	 * @@throws FileNotFoundException
	 * @@throws MissingFileAssociationException if there's no file association.
	 */
	public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
		if (_filename == null) {
			throw new MissingFileAssociationException();
		}

		try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(_filename))) {
			objOut.writeObject(_filename);
			objOut.writeObject(_warehouse);
		}
	}

	/**
	 * Saves the current state of the warehouse to the new file.
	 *
	 * @@param filename the new file name to associate.
	 * @@throws MissingFileAssociationException
	 * @@throws IOException
	 * @@throws FileNotFoundException
	 */
	public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
		_filename = filename;
		save();
	}

	/**
	 * Loads a previously saved binary file.
	 *
	 * @@param filename the filename to load from.
	 * @@throws UnavailableFileException if the file is not available.
	 */
	public void load(String filename) throws UnavailableFileException, ClassNotFoundException {
		try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(filename))) {
			_filename = (String) objIn.readObject();
			_warehouse = (Warehouse) objIn.readObject();

		} catch (IOException e) {
			throw new UnavailableFileException(filename);
		}
	}

	/**
	 * Imports entities from a text file.
	 *
	 * @param textfile name of file to import from.
	 * @throws ImportFileException if there's an error while importing.
	 */
	public void importFile(String textfile) throws ImportFileException {
		try {
			_warehouse.importFile(textfile);
		} catch (IOException | BadEntryException | UnknownPartnerException | DuplicatePartnerException | UnknownProductException e) {
			throw new ImportFileException(textfile, e);
		}
	}

}
