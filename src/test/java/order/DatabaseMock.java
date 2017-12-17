package order;

import order.model.Order;

import java.util.HashMap;
import java.util.ArrayList;

/**
 *  Mock implementation of IDatabase for unit testing.
 *  This should be replaced by a mocking framework.
 */
public class DatabaseMock implements IDatabase
{
	public HashMap<String, ArrayList<Object>> calls;
	public HashMap<String, Object> responses;
	public HashMap<String, DatabaseException> errors;
	public String commitId;

	public DatabaseMock()
	{
		this.calls = new HashMap<String, ArrayList<Object>>();
		calls.put("commitNew", new ArrayList<Object>());
		calls.put("query", new ArrayList<Object>());

		this.responses = new HashMap<String, Object>();
		this.errors = new HashMap<String, DatabaseException>();
	}

	@Override
	public void commitNew(Order o) throws DatabaseException
	{
		calls.get("commitNew").add(o);
		doThrow("commitNew");
		o.id = commitId;
	}

	@Override
	public Order query(String orderId) throws DatabaseException
	{
		calls.get("query").add(orderId);
		doThrow("query");
		return (Order)responses.get("query");
	}

	private void doThrow(String method) throws DatabaseException
	{
		if (errors.get(method) != null)
		{
			throw errors.get(method);
		}
	}
}
