package order.model;

public class Order implements Cloneable
{
	public String id;
	public Integer amount;

	public Order(Integer amount)
	{
		this.amount = amount;
	}

	public Order clone() throws CloneNotSupportedException
	{
		return (Order)super.clone();
	}
}
