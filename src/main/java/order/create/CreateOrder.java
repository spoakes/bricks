package order.create;

import order.IDatabase;
import order.DatabaseException;
import order.model.Order;
import order.Messages;

public class CreateOrder
{
	public CreateResponse run(CreateRequest request, IDatabase db)
	{
		CreateResponse response = new CreateResponse();
		Order o = new Order(request.amount);
		try
		{
			db.commitNew(o);
			response.orderId = o.id;
		}
		catch (DatabaseException e)
		{
			response.addError(
				String.format(Messages.ERROR_UNABLE_TO_CREATE, e.getMessage())
			);
		}

		return response;
	}
}
