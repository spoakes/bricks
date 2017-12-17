package order;

import java.util.Scanner;

import com.google.gson.Gson;

import order.create.CreateRequest;
import order.create.CreateResponse;
import order.get.GetResponse;

import static spark.Spark.*;

public class Application
{
	public static void main(String[] args)
	{
		enableEndPoints();

		System.out.println("REST API is ready -- http://localhost:4567");
		System.out.println("Press enter to stop the server.");
		Scanner console = new Scanner(System.in);
		console.nextLine();

		stop();
		System.exit(0);
	}

	private static void enableEndPoints()
	{
		Gson gson = new Gson();
		OrderService service = new OrderService();

		path("/order", () -> {
			post("/create", (request, response) -> {
				CreateRequest createRequest = gson.fromJson(request.body(), CreateRequest.class);
				CreateResponse createResponse = service.create(createRequest);
				if (createResponse.errors.isEmpty())
				{
					response.status(400);
				}
				return gson.toJson(createResponse);
			});

			get("/get", (request, response) -> {
				String orderId = request.queryMap().get("orderId").value();
				GetResponse getResponse = service.get(orderId);
				if (!getResponse.errors.isEmpty())
				{
					response.status(400);
				}
				return gson.toJson(getResponse);
			});
		});
	}
}
