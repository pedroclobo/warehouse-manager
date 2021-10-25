package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import ggc.core.WarehouseManager;

import java.io.IOException;
import java.io.FileNotFoundException;
import ggc.core.exception.MissingFileAssociationException;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

	/** @param receiver */
	DoSaveFile(WarehouseManager receiver) {
		super(Label.SAVE, receiver);
	}

	@Override
	public final void execute() throws CommandException {
		String filename = _receiver.getFilename();

		try {
			_receiver.saveAs(filename);
		} catch (MissingFileAssociationException | IOException e1) {
			filename = Form.requestString(Message.newSaveAs());
			_receiver.setFilename(filename);
			try {
				_receiver.saveAs(filename);
			} catch (MissingFileAssociationException | IOException e2) {
				return;
			}
		}

	}

}
