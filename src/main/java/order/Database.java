package order;

import java.util.HashMap;

import order.model.Order;

/**
 * A simple database implementation that stores all of the records in memory.
 * The records will be stored until the program is terminated.
 */
class Database implements IDatabase
{
	private final HashMap<String, Order> ordersById;
	private Integer orderEnumerator;

	Database()
	{
		this.ordersById = new HashMap<String, Order>();
		this.orderEnumerator = 0;
	}

	public void commitNew(Order o) throws DatabaseException
	{
		if (o.id != null)
		{
			throw new DatabaseException(Messages.ERROR_COMMIT_NEW_WITH_ID);
		}

		// In practice it may be preferable to assign a non sequential ID so that the user cannot infer the total number of orders.
		String orderId = (orderEnumerator++).toString();
		o.id = orderId;

		ordersById.put(orderId, getClone(o));
	}

	public Order query(String orderId) throws DatabaseException
	{
		Order o = ordersById.get(orderId);
		if (o == null)
		{
			throw new DatabaseException(Messages.ERROR_QUERY_NOT_FOUND);
		}

		return getClone(o);
	}

	private Order getClone(Order o) throws DatabaseException
	{
		// Store and retrieve clones of the records so that the stored records cannot be mutated from outside this class.
		try
		{
			// It is sufficient to use the default clone implementation as the order does not contain any mutable nested objects.
			return o.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new DatabaseException("Failed to clone.");
		}
	}
}
