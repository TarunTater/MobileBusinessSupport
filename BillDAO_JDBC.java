import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BillDAO_JDBC implements BillDAO{
	
	Connection dbConnection;
	
	public BillDAO_JDBC(Connection dbconn) {
		dbConnection = dbconn;
	}

	@Override
	public void addBill(Bill bill) {
		PreparedStatement ps = null;
		String sql;
		try {
			sql = "insert into bill(customer_id, bill_status, amount, bill_date, due_date) VALUES (?,?,?,?,?)";
			ps = dbConnection.prepareStatement(sql);
			
			ps.setInt(1, bill.customer.getCustomer_id());
			ps.setString(2, "PENDING");
			ps.setFloat(3, bill.getAmount());
			Date d = bill.getBill_date();
			SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date date = new java.sql.Date(d.getTime());
			ps.setString(4, sformat.format(date));
			Date due = bill.getDue_date();
			ps.setString(5, sformat.format(due));
			
			ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Customer Billed!");
	}
	
		

	@Override
	public ArrayList<Bill> getBills(int cid) {
		ArrayList<Bill> bills = new ArrayList<Bill>();
		Statement ps = null;
		String sql;
		try {
			sql = "select * from bill where customer_id=" + cid + " and bill_status = 'PENDING'";
			ps = dbConnection.createStatement();
            ps.executeQuery(sql);
			ResultSet result = ps.executeQuery(sql);
			while(result.next()) {
				Bill b = new Bill();
				b.setBill_id(result.getInt(1));
				b.setStatus(result.getString(3));
				b.setAmount(result.getFloat(4));
				b.setBill_date((java.sql.Date) result.getDate(5));
				b.setDue_date((java.sql.Date) result.getDate(6));
				bills.add(b);
			}			
		}catch(SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
			
		}
		return bills;
	}

	@Override
	public void payBill(int bill_id) {
		Statement stmt = null;
		String sql;
		try {
            sql = "update bill set bill_status = 'PAID' where bill_id =" +bill_id;
            stmt = dbConnection.createStatement();
            stmt.executeUpdate(sql);
        
        }catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }        
        try{
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
 		}
		
	}

	@Override
	public float getMonthlyRevenue(String month, String year) {
		PreparedStatement preparedStatement = null;																																																																																																																																													
		String sql;
		sql = "select sum(amount) from bill where bill_date like '%" + year + "-"+ month + "%'";
		float total_revenue = 0;
		try {
			preparedStatement = dbConnection.prepareStatement(sql);
 
			// execute insert SQL stetement
			float total_amount = 0;
			ResultSet rs =preparedStatement.executeQuery(sql); 
			while(rs.next())
			{
				total_amount = rs.getFloat(1);
			}
			total_revenue = total_amount;
			return total_revenue;
			
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
 		}

		try{
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
 		}
		return total_revenue;
	}

	@Override
	public int getCustomerID(int billID) {
		Statement s = null;
		String sql = "select customer_id from bill where bill_id = " + billID;
		 int cid = 0;
		try {
			s = dbConnection.createStatement();
			ResultSet result = s.executeQuery(sql);
            while(result.next()) {
            	cid = result.getInt(1);
            }
			
		}catch(SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());			
		}
		
		try{
			if (s != null) {
				s.close();
			}
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
 		}
		return cid;
	}
	

}
