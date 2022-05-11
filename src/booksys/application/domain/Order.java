package booksys.application.domain;

public class Order {
	private int r_oid;
	private int m_oid;
	
	public Order(int r_oid, int m_oid)
	{
		this.r_oid = r_oid;
		this.m_oid = m_oid;
	}
	
	public int getR_oid()
	{
		return r_oid;
	}
	
	public int getM_oid()
	{
		return m_oid;
	}
}
