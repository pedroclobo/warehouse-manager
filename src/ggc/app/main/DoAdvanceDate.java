package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.exception.InvalidDateException;
//FIXME import classes

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
		int increment = integerField("increment");

		if (increment < 0)
			throw new InvalidDateException(increment);

		_receiver.fowardDate(increment);
	}

}
