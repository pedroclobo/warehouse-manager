package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.UnknownPartnerException;

/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

	public DoLookupPaymentsByPartner(WarehouseManager receiver) {
		super(Label.PAID_BY_PARTNER, receiver);
		addStringField("partnerKey", Message.requestPartnerKey());
	}

	@Override
	public void execute() throws CommandException {
		try {
			_display.popup(_receiver.getPartnerPaidTransactions(stringField("partnerKey")));
		} catch (UnknownPartnerException e) {
			throw new UnknownPartnerKeyException(e.getKey());
		}
	}

}
