package order.get;

import order.DatabaseException;
import order.model.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import order.DatabaseMock;

import java.util.ArrayList;

class GetOrderTest
{
	private final GetOrder get;
	private final DatabaseMock db;

	GetOrderTest()
	{
		this.get = new GetOrder();
		this.db = new DatabaseMock();

		// Default response
		Order order = new Order(12);
		order.id = "1";
		db.responses.put("query", order);
	}

	@Test
	void run_queriesOrderId()
	{
		get.run("123", db);
		ArrayList<Object> capturedIds = db.calls.get("query");
		assertEquals(1, capturedIds.size(), "Failed to query the correct number of times.");
		assertEquals("123", capturedIds.get(0), "Failed to query the correct ID.");
	}

	@Test
	void run_queryThrowsException_returnsPrefixedError()
	{
		db.errors.put("query", new DatabaseException("Test error"));
		GetResponse result = get.run("1", db);

		assertEquals(null, result.orderId, "Should not have returned an order ID.");
		assertEquals(null, result.amount, "Should not have returned an order ID.");
		assertEquals(1, result.errors.size(), "Failed to return the correct number of errors: " + result.errors);
		assertEquals("Unable to retrieve order: Test error", result.errors.get(0), "Failed to return the correct error.");
	}

	@Test
	void run_returnsResponseForOrder()
	{
		GetResponse result = get.run("1", db);
		assertEquals("1", result.orderId, "Failed to return the correct order ID.");
		assertEquals(Integer.valueOf(12), result.amount, "Failed to return the correct amount.");
		assertEquals(0, result.errors.size(), "Should not have returned any errors: " + result.errors);
	}
}