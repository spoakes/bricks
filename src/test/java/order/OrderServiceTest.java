package order;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import order.create.*;
import order.get.*;

class OrderServiceTest
{
	private final OrderService service;

	public OrderServiceTest()
	{
		this.service = new OrderService();
	}

	@Test
	void create_get_returnsCreatedOrder()
	{
		CreateResponse createResponse = service.create(new CreateRequest(12));
		verifyCreateResponse(createResponse);

		String orderId = createResponse.orderId;

		GetResponse getResponse = service.get(orderId);
		verifyGetResponse(getResponse, orderId, 12);
	}

	@Test
	void create_multipleOrders_get_returnsOrderWithId()
	{
		CreateResponse createResponse1 = service.create(new CreateRequest(12));
		CreateResponse createResponse2 = service.create(new CreateRequest(9999));
		CreateResponse createResponse3 = service.create(new CreateRequest(0));

		verifyCreateResponse(createResponse1);
		verifyCreateResponse(createResponse2);
		verifyCreateResponse(createResponse3);

		GetResponse getResponse1 = service.get(createResponse1.orderId);
		GetResponse getResponse2 = service.get(createResponse2.orderId);
		GetResponse getResponse3 = service.get(createResponse3.orderId);

		verifyGetResponse(getResponse1, createResponse1.orderId, 12);
		verifyGetResponse(getResponse2, createResponse2.orderId, 9999);
		verifyGetResponse(getResponse3, createResponse3.orderId, 0);
	}

	@Test
	void create_invalidId_returnsError()
	{
		service.create(new CreateRequest(12));

		GetResponse getResponse = service.get("not an order");
		verifyGetResponseFailure(getResponse, "Unable to retrieve order: The requested ID was not found.");
	}

	/**
	 * Helper methods
	 */

	private void verifyCreateResponse(CreateResponse response)
	{
		assertNotEquals(null, response.orderId, "Should have returned an order ID.");
		verifyResponseSuccess(response);
	}

	private void verifyGetResponse(GetResponse response, String orderId, Integer amount)
	{
		assertEquals(orderId, response.orderId, "Failed to return the correct order ID.");
		assertEquals(amount, response.amount, "Failed to return the correct amount.");
		verifyResponseSuccess(response);
	}

	private void verifyGetResponseFailure(GetResponse response, String expectedError)
	{
		assertEquals(null, response.orderId, "Should not have returned an order ID.");
		assertEquals(null, response.amount, "Should not have returned an amount.");
		verifyResponseFailure(response, expectedError);
	}

	private void verifyResponseSuccess(Response response)
	{
		assertEquals(0, response.errors.size(), "Should not have returned any errors: " + response.errors);
	}

	private void verifyResponseFailure(GetResponse response, String expectedError)
	{
		assertEquals(1, response.errors.size(), "Failed to return the correct number of errors: " + response.errors);
		assertEquals(expectedError, response.errors.get(0), "Failed to return the correct error.");
	}
}