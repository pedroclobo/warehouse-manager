package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.UnknownPartnerException;

/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

	DoShowPartner(WarehouseManager receiver) {
		super(Label.SHOW_PARTNER, receiver);
		addStringField("id", Message.requestPartnerKey());
	}

	@Override
	public void execute() throws CommandException {
		try {
			_display.popup(_receiver.getPartner(stringField("id")));
		} catch (UnknownPartnerException e) {
			throw new UnknownPartnerKeyException(stringField("id"));
		}
	}

}
