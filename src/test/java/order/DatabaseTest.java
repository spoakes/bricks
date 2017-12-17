package order;

import order.model.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest
{
	private final Database db;

	DatabaseTest()
	{
		this.db = new Database();
	}

	@Test
	void commitNew_setsOrderId()
	{
		Order order = new Order(12);
		assertEquals(null, order.id, "Sanity: Order should not have an initial ID.");

		assertAll(() -> {
			db.commitNew(order);

			assertNotEquals(null, order.id, "Failed to set the order ID.");
		});
	}

	@Test
	void commitNew_setsUniqueOrderId()
	{
		Order order1 = new Order(12);
		Order order2 = new Order(99);

		assertAll(() -> {
			db.commitNew(order1);
			db.commitNew(order2);

			assertNotEquals(null, order1.id, "Failed to set the order ID.");
			assertNotEquals(null, order2.id, "Failed to set the order ID.");
			assertNotEquals(order1.id, order2.id, "Failed to assign unique IDs.");
		});
	}

	@Test
	void commitNew_withId_ThrowsException()
	{
		Order order = new Order(12);
		order.id = "0";

		DatabaseException result = assertThrows(DatabaseException.class, () -> db.commitNew(order));
		assertEquals("The record already has an ID.", result.getMessage(), "Failed to throw the correct exception.");
	}

	@Test
	void query_returnsOrderWithId()
	{
		Order order = new Order(12);

		assertAll(() -> {
			db.commitNew(order);
			Order result = db.query(order.id);

			verifyOrder(result, order.id, 12);
		});
	}

	@Test
	void query_noOrdersExist_throwsException()
	{
		DatabaseException result = assertThrows(DatabaseException.class, () -> db.query("0"));
		assertEquals("The requested ID was not found.", result.getMessage(), "Failed to throw the correct exception.");
	}

	@Test
	void query_invalidId_throwsException()
	{
		Order order = new Order(12);
		assertAll(() -> db.commitNew(order));

		DatabaseException result = assertThrows(DatabaseException.class, () -> db.query("not an order"));
		assertEquals("The requested ID was not found.", result.getMessage(), "Failed to throw the correct exception.");
	}

	@Test
	void query_multipleOrders_returnsOrderWithId()
	{
		Order order1 = new Order(12);
		Order order2 = new Order(99);
		Order order3 = new Order(0);

		assertAll(() -> {
			db.commitNew(order1);
			db.commitNew(order2);
			db.commitNew(order3);

			Order result1 = db.query(order1.id);
			Order result2 = db.query(order2.id);
			Order result3 = db.query(order3.id);

			verifyOrder(result1, order1.id, 12);
			verifyOrder(result2, order2.id, 99);
			verifyOrder(result3, order3.id, 0);
		});
	}

	private void verifyOrder(Order order, String orderId, Integer amount)
	{
		assertEquals(orderId, order.id, "Failed to return the correct order ID.");
		assertEquals(amount, order.amount, "Failed to return the correct amount.");
	}

	@Test
	void databaseException_initializesMessage()
	{
		DatabaseException dbe = new DatabaseException("Test message");
		assertEquals("Test message", dbe.getMessage(), "Failed to initialize the correct message.");
	}
}