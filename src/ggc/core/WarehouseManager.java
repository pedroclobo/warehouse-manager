package ggc.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import ggc.core.products.Product;
import ggc.core.products.AggregateProduct;
import ggc.core.products.SimpleProduct;
import ggc.core.products.Batch;
import ggc.core.partners.Partner;
import ggc.core.transactions.Transaction;
import ggc.core.transactions.Acquisition;
import ggc.core.transactions.Sale;
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

	public Product getProduct(String id) throws UnknownProductException {
		return _warehouse.getProduct(id);
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
		_warehouse.registerAggregateProduct(id, aggravation, productIds, quantities);
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
	 * Returns a collection of all batches under the specified price.
	 *
	 * @param price the price to compare to.
	 */
	public Collection<Batch> getBatchesUnderGivenPrice(double price) {
		return _warehouse.getBatchesUnderGivenPrice(price);
	}

	/**
	 * @param key the partner key.
	 * @return a collection with all batches supplied by the given partner.
	 * @throws UnknownPartnerException if there's no product with the given id.
	 */
	public Collection<Batch> getBatchesByPartner(String key) throws UnknownPartnerException {
		return _warehouse.getBatchesByPartner(key);
	}

	/**
	 * @param id the product id.
	 * @return a collection with all batches that hold a product.
	 * @throws UnknownProductException if there's no product with the given id.
	 */
	public Collection<Batch> getBatchesByProduct(String id) throws UnknownProductException {
		return _warehouse.getBatchesByProduct(id);
	}

	/**
	 * @param id the partner id.
	 * @return the partner with the given id.
	 * @throws UnknownPartnerException if there's no partner with the given id.
	 */
	public Partner getPartner(String id) throws UnknownPartnerException {
		return _warehouse.getPartner(id);
	}

	public Collection<Notification> getPartnerNotifications(String key) throws UnknownPartnerException {
		return _warehouse.getPartnerNotifications(key);
	}

	/**
	 * @return a collection with all partners
	 */
	public Collection<Partner> getPartners() {
		return _warehouse.getPartners();
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
		_warehouse.registerPartner(id, name, address);
	}

	/**
	 * Gets a collection of all partner's acquisitions.
	 *
	 * @param id the partner id.
	 * @return a collection of acquisitions.
	 * @throws UnknownPartnerException if there's no partner with the given id.
	 */
	public Collection<Acquisition> getAcquisitionsByPartner(String id) throws UnknownPartnerException {
		return _warehouse.getAcquisitionsByPartner(id);
	}

	/**
	 * Gets a collection of all partner's sales.
	 *
	 * @param id the partner id.
	 * @return a collection of sales.
	 * @throws UnknownPartnerException if there's no partner with the given id.
	 */
	public Collection<Sale> getSalesByPartner(String id) throws UnknownPartnerException {
		return _warehouse.getSalesByPartner(id);
	}

	public Transaction getTransaction(int id) throws UnknownTransactionException {
		return _warehouse.getTransaction(id);
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
		_warehouse.registerAcquisition(partnerKey, productKey, amount, price);
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
		_warehouse.registerCreditSale(partnerKey, paymentDeadline, productKey, amount);
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
		_warehouse.registerBreakdownSale(partnerKey, productKey, amount);
	}

	public void receivePayment(int transactionKey) throws UnknownTransactionException {
		_warehouse.receiveCreditSalePayment(transactionKey);
	}

	public Collection<Sale> getPartnerPaidTransactions(String key) throws UnknownPartnerException {
		return _warehouse.getPartnerPaidTransactions(key);
	}

	public void toggleNotifications(String partnerKey, String productKey) throws UnknownPartnerException, UnknownProductException {
		_warehouse.toggleNotifications(partnerKey, productKey);
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
