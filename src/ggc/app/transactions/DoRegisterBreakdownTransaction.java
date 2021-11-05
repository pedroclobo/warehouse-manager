package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.exception.UnavailableProductException;
import ggc.core.exception.NoProductStockException;

/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

	public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
		super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
		addStringField("partnerId", Message.requestPartnerKey());
		addStringField("productId", Message.requestProductKey());
		addIntegerField("amount", Message.requestAmount());
	}

	@Override
	public final void execute() throws CommandException {
		//FIXME implement command
	}

}
