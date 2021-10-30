package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.core.WarehouseManager;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.exception.UnknownProductException;

/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> {

	DoShowBatchesByProduct(WarehouseManager receiver) {
		super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
		addStringField("id", Message.requestProductKey());
	}

	@Override
	public final void execute() throws CommandException {
		try {
			_display.popup(_receiver.getBatchesByProduct(stringField("id")));
		} catch (UnknownProductException e) {
			throw new UnknownProductKeyException(stringField("id"));
		}
	}

}
