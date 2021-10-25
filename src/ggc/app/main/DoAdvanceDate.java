package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.exception.InvalidDateIncrementException;
import ggc.app.exception.InvalidDateException;

/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

	DoAdvanceDate(WarehouseManager receiver) {
		super(Label.ADVANCE_DATE, receiver);
		addIntegerField("increment", Message.requestDaysToAdvance());
	}

	@Override
	public final void execute() throws CommandException {
		try {
			_receiver.fowardDate(integerField("increment"));
		} catch (InvalidDateIncrementException e) {
			throw new InvalidDateException(integerField("increment"));
		}
	}

}
