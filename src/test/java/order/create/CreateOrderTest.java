package order.create;

import order.DatabaseException;
import order.DatabaseMock;
import order.model.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CreateOrderTest
{
	private final CreateOrder create;
	private final DatabaseMock db;

	CreateOrderTest()
	{
		this.create = new CreateOrder();
		this.db = new DatabaseMock();

		// Default behaviour
		db.commitId = "123";
	}

	@Test
	void run_commitsOrderWithAmount()
	{
		CreateRequest request = new CreateRequest(12);
		create.run(request, db);

		ArrayList<Object> capturedOrders = db.calls.get("commitNew");
		assertEquals(1, capturedOrders.size(), "Failed to commit the correct number of orders.");
		assertEquals(Integer.valueOf(12), ((Order)capturedOrders.get(0)).amount, "Failed to commit order with the correct amount.");
	}

	@Test
	void run_commitThrowsException_returnsPrefixedError()
	{
		db.errors.put("commitNew", new DatabaseException("Test error"));

		CreateRequest request = new CreateRequest(12);
		CreateResponse response = create.run(request, db);

		assertEquals(null, response.orderId, "Should not have returned an order ID.");
		assertEquals(1, response.errors.size(), "Failed to return the correct number of errors: " + response.errors);
		assertEquals("Unable to create order: Test error", response.errors.get(0), "Failed to return the correct error.");
	}

	@Test
	void run_returnsOrderId()
	{
		CreateResponse response = create.run(new CreateRequest(12), db);
		assertEquals("123", response.orderId, "Failed to return the correct order ID.");
		assertEquals(0, response.errors.size(), "Should not have returned any errors: " + response.errors);
	}
}