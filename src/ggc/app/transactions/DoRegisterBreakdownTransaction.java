package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.core.WarehouseManager;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.UnavailableProductException;
import ggc.core.exception.UnknownPartnerException;
import ggc.core.exception.UnknownProductException;
import ggc.core.exception.NoProductStockException;

/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

	public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
		super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
		addStringField("partnerKey", Message.requestPartnerKey());
		addStringField("productKey", Message.requestProductKey());
		addIntegerField("amount", Message.requestAmount());
	}

	@Override
	public final void execute() throws CommandException {
		try {
			_receiver.registerBreakdownTransaction(
					stringField("partnerKey"),
					stringField("productKey"),
					integerField("amount")
					);

		} catch (NoProductStockException e) {
			throw new UnavailableProductException(e.getKey(), e.getRequested(), e.getAvailable());
		} catch (UnknownPartnerException e) {
			throw new UnknownPartnerKeyException(e.getKey());
		} catch (UnknownProductException e) {
			throw new UnknownProductKeyException(e.getKey());
		}
	}

}
