package order.get;

import order.model.Order;
import order.IDatabase;
import order.DatabaseException;
import order.Messages;

public class GetOrder
{
	public GetResponse run(String orderId, IDatabase db)
	{
		GetResponse response = new GetResponse();
		try
		{
			Order o = db.query(orderId);
			response.orderId = o.id;
			response.amount = o.amount;
		}
		catch (DatabaseException e)
		{
			response.addError(
				String.format(Messages.ERROR_UNABLE_TO_GET, e.getMessage())
			);
		}

		return response;
	}
}
