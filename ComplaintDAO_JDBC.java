import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ComplaintDAO_JDBC implements ComplaintDAO {
	Connection dbConnection;
	public ComplaintDAO_JDBC(Connection dbconn){
		// JDBC driver name and database URL
 		//  Database credentials
		dbConnection = dbconn;
	}

	@Override
	public void addComplaint(Complaint complaint) {
		// TODO Auto-generated method stub
		DateFormat dformat =  new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		
		//int cid=0;
		String sql;
		Statement stmt = null;
		try {
			
			sql = "insert into complaint (complaint_type, status, customer_id, register_date)" +
					" values ( '" + complaint.getComplaint_type() + "','" + complaint.complaint_status + "'," + complaint.getCustomer().getCustomer_id() + ", '" + dformat.format(date) + "')";
			stmt = dbConnection.createStatement();
			stmt.executeUpdate(sql);
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

	@Override
	public ArrayList<Complaint> getComplainByCid(int cid) {			
		String sql;
		Statement stmt = null;
		//int cid = Customer.getCustomer_id();
		sql = "select * from  complaint where customer_id =" + cid;
		ArrayList<Complaint> all_complaints = new ArrayList<Complaint>();
		try {
		stmt = dbConnection.createStatement();
		
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
		int complaint_id = rs.getInt(1);
		String complaint_type = rs.getString(2);
		String status = rs.getString(3);
		int customer_id  = rs.getInt(4);
		Customer customer = new  Customer();
		customer.setCustomer_id(customer_id);
		Date register_date = rs.getDate(5);
		Complaint comp = new Complaint();
		comp.setComplaint_id(complaint_id);
		comp.setComplaint_type(complaint_type);
		comp.setComplaint_status(status);
		//comp.setCustomer_id(customer_id);
		comp.setRegister_date(register_date);
		comp.setCustomer(customer);
		all_complaints.add(comp);
		
		//System.out.println("complaint id" + complaint_id);
		//System.out.println("complaint type" + complaint_type);
		//System.out.println("complaint status" + status);
		}
		return all_complaints;
		//preparedStatement.setInt(1, plan.getPlan_id());
		//preparedStatement.setString(2, plan.getPlan_name());
		//preparedStatement.setInt(3, plan.getPlan_amt());
		
		
		// execute delete SQL statement
		//preparedStatement.executeUpdate();
		
		
		} catch (SQLException ex) {
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
		return all_complaints;
	}

	@Override
	public ArrayList<Complaint> getPendingComplaints() {
		
		String sql;
		Statement stmt = null;
		//int cid = Customer.getCustomer_id();
		sql = "select * from  complaint where status  = 'PENDING'" ;
		ArrayList<Complaint> all_complaints = new ArrayList<Complaint>();
		try {
		stmt = dbConnection.createStatement();

		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
		int complaint_id = rs.getInt(1);
		String complaint_type = rs.getString(2);
		String status = rs.getString(3);
		int customer_id  = rs.getInt(4);
		Customer customer = new  Customer();
		customer.setCustomer_id(customer_id);
		
		Date register_date = rs.getDate(5);
		Complaint comp = new Complaint();
		comp.setComplaint_id(complaint_id);
		comp.setComplaint_type(complaint_type);
		comp.setComplaint_status(status);
		comp.setCustomer(customer);
		comp.setRegister_date(register_date);
		all_complaints.add(comp);

		//System.out.println("complaint id" + complaint_id);
		//System.out.println("complaint type" + complaint_type);
		//System.out.println("complaint status" + status);
		}
		return all_complaints;
		} catch (SQLException ex) {
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
			return all_complaints;
	}

	

	
	
}
