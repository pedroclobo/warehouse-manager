package ggc.core;

import java.util.Collection;
import java.util.Map;

import java.io.Serializable;
import java.io.IOException;
import java.io.FileNotFoundException;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.MissingFileAssociationException;

/** Façade for access. */
public class WarehouseManager {

	/** Name of file storing current warehouse. */
	private String _filename = "";

	/** The warehouse itself. */
	private Warehouse _warehouse = new Warehouse();

	/*
	public WarehouseManager(String filename) {
		_filename = filename;
	}
	*/

	public int getDate() {
		return _warehouse.getDate();
	}

	public void fowardDate(int increment) {
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

	/**
	 * @@throws IOException
	 * @@throws FileNotFoundException
	 * @@throws MissingFileAssociationException
	 */
	public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
		//FIXME implement serialization method
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
	public void load(String filename) throws UnavailableFileException, ClassNotFoundException	{
		//FIXME implement serialization method
	}

	/**
	 * @param textfile
	 * @throws ImportFileException
	 */
	public void importFile(String textfile) throws ImportFileException {
		try {
			_warehouse.importFile(textfile);
		} catch (IOException | BadEntryException /* FIXME maybe other exceptions */ e) {
			throw new ImportFileException(textfile, e);
		}
	}

}
