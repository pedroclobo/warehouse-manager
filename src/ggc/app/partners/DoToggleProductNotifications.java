package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.UnknownPartnerException;

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

	DoToggleProductNotifications(WarehouseManager receiver) {
		super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
		addStringField("partnerKey", Message.requestPartnerKey());
	}

	@Override
	public void execute() throws CommandException {
		/*
		try {

		} catch (UnknownPartnerException e) {
			throw new UnknownPartnerKeyException(e.getKey());
		}
	*/
	}

}
