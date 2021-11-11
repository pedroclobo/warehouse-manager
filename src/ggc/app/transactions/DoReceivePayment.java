package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.exception.UnknownTransactionKeyException;
import ggc.core.exception.UnknownTransactionException;

/**
 * Receive payment for sale transaction.
 */
public class DoReceivePayment extends Command<WarehouseManager> {

	public DoReceivePayment(WarehouseManager receiver) {
		super(Label.RECEIVE_PAYMENT, receiver);
		addIntegerField("transactionKey", Message.requestTransactionKey());
	}

	@Override
	public final void execute() throws CommandException {
		try {
			_receiver.receivePayment(integerField("transactionKey"));
		} catch (UnknownTransactionException e) {
			throw new UnknownTransactionKeyException(e.getKey());
		}
	}

}
