package ggc.core;

import java.util.Collection;
import java.util.Map;

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

/** Façade for access. */
public class WarehouseManager {

	/** Name of file storing current warehouse. */
	private String _filename = "";

	/** The warehouse itself. */
	private Warehouse _warehouse = new Warehouse();

	public String getFilename() {
		return _filename;
	}

	public void setFilename(String filename) {
		_filename = filename;
	}

	public int getDate() {
		return _warehouse.getDate();
	}

	public void fowardDate(int increment) throws InvalidDateIncrementException {
		_warehouse.fowardDate(increment);
	}

	public double getAvailableBalance() {
		return _warehouse.getAvailableBalance();
	}

	public double getAccountingBalance() {
		return _warehouse.getAccountingBalance();
	}

	public Collection getProducts() {
		return _warehouse.getProducts();
	}

	public Collection getBatches() {
		return _warehouse.getBatches();
	}

	public Collection getBatchesByProduct(String id) throws UnknownProductException {
		return _warehouse.getBatchesByProduct(id);
	}

	public Partner getPartner(String id) throws UnknownPartnerException {
		return _warehouse.getPartner(id);
	}

	public Collection getPartners() {
		return _warehouse.getPartners();
	}

	public void registerPartner(String id, String name, String address) throws DuplicatePartnerException {
		_warehouse.registerPartner(id, name, address);
	}

	/**
	 * @@throws IOException
	 * @@throws FileNotFoundException
	 * @@throws MissingFileAssociationException
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
	 * @@param filename
	 * @@throws MissingFileAssociationException
	 * @@throws IOException
	 * @@throws FileNotFoundException
	 */
	public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
		_filename = filename;
		save();
	}

	/**
	 * @@param filename
	 * @@throws UnavailableFileException
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
	 * @param textfile
	 * @throws ImportFileException
	 */
	public void importFile(String textfile) throws ImportFileException {
		try {
			_warehouse.importFile(textfile);
		} catch (IOException | BadEntryException | UnknownPartnerException | DuplicatePartnerException | UnknownProductException e) {
			throw new ImportFileException(textfile, e);
		}
	}

}
