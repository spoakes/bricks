package order;

/**
 * Stores constants for messages to be exposed to the user.
 */
public class Messages
{
	private Messages(){ /* This class is not intended to be instantiated */ }

	public static final String ERROR_COMMIT_NEW_WITH_ID = "The record already has an ID.";
	public static final String ERROR_QUERY_NOT_FOUND = "The requested ID was not found.";
	public static final String ERROR_UNABLE_TO_CREATE = "Unable to create order: %s";
	public static final String ERROR_UNABLE_TO_GET = "Unable to retrieve order: %s";
}
