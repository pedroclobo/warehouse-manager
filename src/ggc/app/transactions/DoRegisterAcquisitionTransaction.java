package ggc.app.transactions;

import java.util.List;
import java.util.ArrayList;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import ggc.core.WarehouseManager;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.UnknownPartnerException;
import ggc.core.exception.UnknownProductException;

/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

	public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
		super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
		addStringField("partnerId", Message.requestPartnerKey());
		addStringField("productId", Message.requestProductKey());
		addRealField("price", Message.requestPrice());
		addIntegerField("quantity", Message.requestAmount());
	}

	@Override
	public final void execute() throws CommandException {
		try {
			_receiver.registerAcquisition(
					_receiver.getPartner(stringField("partnerId")),
					_receiver.getProduct(stringField("productId")),
					integerField("quantity"),
					realField("price"));

		} catch (UnknownPartnerException e1) {
			throw new UnknownPartnerKeyException(stringField("partnerId"));
		} catch (UnknownProductException e2) {
			boolean isAggregateProduct = Form.confirm(Message.requestAddRecipe());

			try {

				// Create new simple product and register acquisition
				if (!isAggregateProduct) {
					_receiver.registerSimpleProduct(stringField("productId"));
					_receiver.registerAcquisition(
							_receiver.getPartner(stringField("partnerId")),
							_receiver.getProduct(stringField("productId")),
							integerField("quantity"),
							realField("price"));

				// Create new aggregate product and register acquisition
				} else {
					List<String> products = new ArrayList<>();
					List<Integer> quantities = new ArrayList<>();
					int numberComponents = Form.requestInteger(Message.requestNumberOfComponents());
					double alpha = Form.requestReal(Message.requestAlpha());

					for (int i = 0; i < numberComponents; i++) {
						products.add(Form.requestString(Message.requestProductKey()));
						quantities.add(Form.requestInteger(Message.requestAmount()));
					}

					_receiver.registerAggregateProduct(
							stringField("productId"),
							alpha,
							_receiver.getListOfProducts(products),
							quantities
							);

				}

			} catch (UnknownPartnerException e3) {
				throw new UnknownPartnerKeyException(stringField("partnerId"));
			} catch (UnknownProductException e4) {
				return;
			}
		}
	}

}
