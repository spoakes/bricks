package order;

import order.model.Order;

public interface IDatabase
{
	/**
	 * Store a new order.
	 * @param o The order to be stored.
	 * @throws DatabaseException The order already has an ID.
	 */
	void commitNew(Order o) throws DatabaseException;

	/**
	 * Retrieve an order if it has been stored.
	 * @param orderId The unique ID that identifies the order.
	 * @return The order with the given ID.
	 * @throws DatabaseException The order does not exist.
	 */
	Order query(String orderId) throws DatabaseException;
}
