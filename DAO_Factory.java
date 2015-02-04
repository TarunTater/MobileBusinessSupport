import java.sql.*;

public class DAO_Factory {
	
	public enum TXN_STATUS {COMMIT, ROLLBACK}
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/mbss";
	static final String USER = "root";
	static final String PASS = "123456";
	Connection dbconnection = null;
	
	CustomerDAO customerDAO = null;
	PlanDAO planDAO = null;
	ComplaintDAO complaintDAO = null;
	BillDAO billDAO = null;
	
	boolean activeConnection = false;
	
	public DAO_Factory() {
		dbconnection = null;
		activeConnection = false;
	}
	
	public void activateConnection() throws Exception{
		if(activeConnection == true) 
			throw new Exception("Connection already active");
		
		System.out.println("Connection to Database...");
		try{
			Class.forName("com.mysql.jdbc.Driver");
			dbconnection = DriverManager.getConnection(DB_URL, USER, PASS);
			dbconnection.setAutoCommit(false);
			activeConnection = true;
		}catch(ClassNotFoundException ex) {
			System.out.println("Error: Unable to load driver class!");
			System.exit(1);
		}catch(SQLException ex) {
			System.out.println("SQL Exception: " + ex.getMessage());
			System.out.println("SQL State: " + ex.getSQLState());
			System.out.println("Vendor Exception: " + ex.getErrorCode());
		}
		
	}
	
	public CustomerDAO getCustomerDAO() throws Exception {
		if(activeConnection == false)
			throw new Exception("Connection not activated...");
		if(customerDAO == null)
			customerDAO = new CustomerDAO_JDBC( dbconnection );
		return customerDAO;
	}
	
	public PlanDAO getPlanDAO() throws Exception {
		if(activeConnection == false)
			throw new Exception("Connection not activated...");
		if(planDAO == null)
			planDAO = new PlanDAO_JDBC( dbconnection );
		return planDAO;
	}
	
	public ComplaintDAO getComplaintDAO() throws Exception {
		if(activeConnection == false)
			throw new Exception("Connection not activated...");
		if(complaintDAO == null)
			complaintDAO = new ComplaintDAO_JDBC( dbconnection );
		return complaintDAO;
	}
	
	public BillDAO getBillDAO() throws Exception {
		if(activeConnection == false)
			throw new Exception("Connection not activated...");
		if(billDAO == null)
			billDAO = new BillDAO_JDBC( dbconnection );
		return billDAO;
	}
	
	public void deactivateConnection(TXN_STATUS txn_status) {
		activeConnection = false;
		
		if(dbconnection != null) {
			try{
					if( txn_status == TXN_STATUS.COMMIT )
						dbconnection.commit();
					else
						dbconnection.rollback();

					dbconnection.close();
					dbconnection = null;
					
					customerDAO = null;
					planDAO = null;
					complaintDAO = null;
					billDAO = null;
					
			}catch(SQLException ex) {
				System.out.println("SQL Exception: " + ex.getMessage());
				System.out.println("SQL State: " + ex.getSQLState());
				System.out.println("Vendor Exception: " + ex.getErrorCode());	
			}
		}
	}
};
