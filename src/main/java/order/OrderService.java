package order;

import order.create.*;
import order.get.*;

/**
 * Entry point for order functionality.
 * This class instantiates any dependencies which the business logic requires.
 */
public class OrderService
{
	private final IDatabase database;

	public OrderService()
	{
		this.database = new Database();
	}

	public CreateResponse create(CreateRequest request)
	{
		return new CreateOrder().run(request, database);
	}

	public GetResponse get(String orderId)
	{
		return new GetOrder().run(orderId, database);
	}
}
