package booksys.application.persistency;
import java.util.StringTokenizer;

import booksys.application.domain.Booking;
import booksys.storage.* ;
import java.sql.* ;

public class OrderMapper {
	
		public int[] divideMenu(String menu)
		{
			StringTokenizer st = new StringTokenizer(menu, ",");
			int [] m_oid = new int[st.countTokens()];
			int i = 0;
			while(st.hasMoreTokens())
			{
				m_oid[i++] = Integer.parseInt(st.nextToken());
			}
			
			return m_oid;
		}
		
		public void createOrder(int r_oid, String menu)
		{
			int [] m_oid = divideMenu(menu);
			for(int i = 0; i < m_oid.length; i++)
			{
				performUpdate("INSERT INTO `ORDER` (r_oid, m_oid) VALUES (" + r_oid + "," + m_oid[i] + ")");
			}
		}
		
		public void deleteOrder(Booking b)
		{
			performUpdate("DELETE FROM `ORDER` WHERE r_oid = '"
					  + ((PersistentBooking) b).getId() + "'" ) ;
		}
		
		private void performUpdate(String sql)
		  {
		    try {
		      Statement stmt
			= Database.getInstance().getConnection().createStatement() ;
		      int updateCount
			= stmt.executeUpdate(sql) ;
		      stmt.close() ;
		    }
		    catch (SQLException e) {
		      e.printStackTrace() ;
		    }
		  }
}
