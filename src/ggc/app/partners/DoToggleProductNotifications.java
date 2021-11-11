package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.exception.UnknownPartnerException;
import ggc.core.exception.UnknownProductException;

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

	DoToggleProductNotifications(WarehouseManager receiver) {
		super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
		addStringField("partnerKey", Message.requestPartnerKey());
		addStringField("productKey", Message.requestProductKey());
	}

	@Override
	public void execute() throws CommandException {
		try {
			_receiver.toggleNotifications(stringField("partnerKey"), stringField("productKey"));
		} catch (UnknownPartnerException e) {
			throw new UnknownPartnerKeyException(e.getKey());
		} catch (UnknownProductException e) {
			throw new UnknownProductKeyException(e.getKey());
		}
	}

}
