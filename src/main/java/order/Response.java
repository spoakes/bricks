package order;

import java.util.ArrayList;

public abstract class Response
{
	public final ArrayList<String> errors = new ArrayList<String>();

	public void addError(String error)
	{
		errors.add(error);
	}
}
