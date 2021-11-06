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
		addStringField("partnerId", Message.requestPartnerKey());
		addStringField("productId", Message.requestProductKey());
		addIntegerField("amount", Message.requestAmount());
	}

	@Override
	public final void execute() throws CommandException {
		try {
			_receiver.registerBreakdownSale(
					_receiver.getPartner(stringField("partnerId")),
					_receiver.getProduct(stringField("productId")),
					integerField("amount")
					);
		} catch (NoProductStockException e1) {
			try {
				throw new UnavailableProductException(
						stringField("productId"),
						integerField("amount"),
						_receiver.getProductStock("productId")
						);
			} catch (UnknownProductException e2) {
				throw new UnknownProductKeyException(stringField("productId"));
			}

		} catch (UnknownPartnerException e3) {
			throw new UnknownPartnerKeyException(stringField("partnerId"));
		} catch (UnknownProductException e4) {
			throw new UnknownProductKeyException(stringField("productId"));
		}
	}

}
