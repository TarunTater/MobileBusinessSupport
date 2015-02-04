import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CustomerDAO_JDBC implements CustomerDAO {
	Connection dbConnection;
	
	public CustomerDAO_JDBC(Connection dbc) {
		dbConnection = dbc;
	}
	
	public int getCustomerId(String pnum){
	    Statement stmt = null;
	    String sql;
	    int cid=0;
	    try {
	            sql = "select customer_id from customer where phone="+ pnum;
	            stmt = dbConnection.createStatement();
	            ResultSet res = stmt.executeQuery(sql);
	            while(res.next())
	            	cid=res.getInt("customer_id");
	        }
	    
	    catch(SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	    }
	    return cid;
	}

	public Customer getDetailsById(int cid){

		Customer c = new Customer();
		Plan p = new Plan();
	    Statement stmt = null;
	    String sql;
	    try {
	            sql = "select * from customer where customer_id="+ cid;
	            stmt = dbConnection.createStatement();
	            ResultSet res = stmt.executeQuery(sql);
	            while(res.next()){
	            	c.setCustomer_id(res.getInt(1));
	            	c.setCustomer_name(res.getString(2));
	            	c.setPhone(res.getString(3));
	            	c.setAddr(res.getString(4));
	            	p.setPlan_id(res.getInt(5));
	            	Date d = res.getDate(6);
	            	p.setStart_date(d);
	            	c.setPresent_plan(p);
	        }
	    }
	    catch(SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	    }
	    return c;
		
	}
	
	@Override
	public void changePlan(int cid, Plan p) {
		
		Statement stmt = null;
		String sql;
		int presplanId=0,pid=p.getPlan_id();
		DateFormat dformat =  new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			Date d = p.getStart_date();
			String stringDate = dformat.format(date);
			//String stringDate = d.toString();
			sql = "update customer set present_plan_id = " + pid + ", plan_start_date = '" + stringDate + "' where customer_id = " + cid;
			stmt = dbConnection.createStatement();
			stmt.executeUpdate(sql);
			try{
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
	 			System.out.println(e.getMessage());
	 		}
			
		}
		catch(SQLException ex) {
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
	
	public void editDetails(int cid, String naam, String Addr){
	    Statement stmt = null;
	    String sql="";
	    
	    try {    
	        if (naam.length() > 0)
	        {
	            sql = "update customer set customer_name = '"+ naam+"' where customer_id = "+cid;
	            stmt = dbConnection.createStatement();
		        stmt.executeUpdate(sql);
		        try{
					if (stmt != null) {
						stmt.close();
					}
				} catch (SQLException e) {
		 			System.out.println(e.getMessage());
		 		}
	        }
	        if (Addr.length() > 0)
	        {
	            sql = "update customer set addr = '"+ Addr+"' where customer_id = "+cid;
	            stmt = dbConnection.createStatement();
		        stmt.executeUpdate(sql);
		        try{
					if (stmt != null) {
						stmt.close();
					}
				} catch (SQLException e) {
		 			System.out.println(e.getMessage());
		 		}
	        }
	    
	    }
	    catch(SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	    }        
	    
	    }
	
	@Override
	public void addCustomer(Customer c) 
	{
		PreparedStatement preparedStatement = null;
		String sql, sql1;
		sql = "insert into customer(customer_name, phone, addr, present_plan_id, plan_start_date) values (?,?,?,?,?)";
		try{
			preparedStatement = dbConnection.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, c.getCustomer_name());
			preparedStatement.setString(2, c.getPhone());
			preparedStatement.setString(3, c.getAddr());

			int pid = c.getPresent_plan().getPlan_id();
			preparedStatement.setInt(4, pid);
			Date d = c.getPresent_plan().getStart_date();
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date dd = new java.sql.Date(d.getTime());
			preparedStatement.setString(5,sFormat.format(dd));
			
			if(preparedStatement.executeUpdate() > 0) {
				System.out.println("Customer added successfully.");
				ResultSet key = preparedStatement.getGeneratedKeys();
				while(key.next()){
					c.setCustomer_id(key.getInt(1));
				}				
			}
			
		}catch (SQLException e){
			System.out.println(e.getMessage());
		}
		try {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
				
		try {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public Customer getDetailsByPhone(String pnum)
	{
		Customer c = new Customer();
		Plan p = new Plan();
	    Statement stmt = null;
	    String sql;
	    try {
	            sql = "select * from customer where phone="+ pnum;
	            stmt = dbConnection.createStatement();
	            ResultSet res = stmt.executeQuery(sql);
	            while(res.next()){
	            	c.setCustomer_id(res.getInt(1));
	            	c.setCustomer_name(res.getString(2));
	            	c.setPhone(res.getString(3));
	            	c.setAddr(res.getString(4));
	            	p.setPlan_id(res.getInt(5));
	            	Date d = res.getDate(6);
	            	p.setStart_date(d);
	            	c.setPresent_plan(p);
	        }
	    }
	    catch(SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	    }
	    return c;
	}

	@Override
	public void deleteCustomer(int cid)
	{
		String sql;
		int pending=0;
		Statement ps = null;
		try {
			ps = dbConnection.createStatement();
			sql = "select * from bill where customer_id=" + cid + " and bill_status = 'PENDING'";
			//System.out.println(sql);
			ResultSet result = ps.executeQuery(sql);
			result = ps.executeQuery(sql);
			
			if (result.next() ) {    
				pending=1; 
				} 
			if(pending == 1) {
				System.out.println("Bills still pending! Can't delete customer.");
				return;
			}
			try {
				if(ps != null) {
					ps.close();
				}
			}catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			
			
			ps = dbConnection.createStatement();
			sql = "delete from bill where customer_id=" + cid;
			ps.executeUpdate(sql);
			try {
				if(ps != null) {
					ps.close();
				}
			}catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			
			ps = dbConnection.createStatement();
			sql = "delete from complaint where customer_id=" + cid;
			ps.executeUpdate(sql);
			try {
				if(ps != null) {
					ps.close();
				}
			}catch(SQLException e) {
				System.out.println(e.getMessage());
			}
						
			ps = dbConnection.createStatement();
			sql = "delete from customer where customer_id=" + cid;
			if (ps.executeUpdate(sql) > 0)
				System.out.println("Successfully deleted customer");
			try {
				if(ps != null) {
					ps.close();
				}
			}catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
