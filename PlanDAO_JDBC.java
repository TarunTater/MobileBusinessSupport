import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class PlanDAO_JDBC implements PlanDAO {
	Connection dbConnection;
	
	public PlanDAO_JDBC(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public ArrayList<Plan> getAllPlans()
	{
		ArrayList<Plan> plans = new ArrayList<Plan>();
		String sql;
		Statement stmt = null;
		try {
			stmt = dbConnection.createStatement();
			sql = "select * from plan";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next()) {
				Plan plan = new Plan();
				int plan_id = res.getInt("plan_id");
				String plan_name = res.getString("plan_name");
				float plan_amt = res.getFloat("plan_amt");
				plan.setPlan_amt(plan_amt);
				plan.setPlan_id(plan_id);
				plan.setPlan_name(plan_name);
				plans.add(plan);
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
		return plans;
	}
	
	public Plan getPlanByName(String pname)
	{
		Plan plan = new Plan();
		plan.setPlan_name(pname);
		Statement stmt = null;
		String sql;
		try{
			stmt = dbConnection.createStatement();
			sql = "select * from plan where plan_name = '"+pname+"'";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next()){
				plan.setPlan_id(res.getInt("plan_id"));
				plan.setPlan_amt(res.getFloat("plan_amt"));
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
		return plan;
	}
	
	public void addPlan(Plan plan)
	{
		PreparedStatement preparedStatement = null;																																																																																																																																													
		String sql;
		sql = "insert into plan(plan_name, plan_amt) values (?,?)";
		
		
		try {
			preparedStatement = dbConnection.prepareStatement(sql);
 
			preparedStatement.setString(1, plan.getPlan_name());
			preparedStatement.setFloat(2, plan.getPlan_amt());
			
 
			// execute insert SQL statement
			preparedStatement.executeUpdate();
 
			System.out.println("Plan: Name " + plan.getPlan_name() 
				+ ", added to the database");
		} 
		catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}

		try{
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
 		}	
	}
	
	public void deletePlan(String plan_name) {
		// TODO Auto-generated method stub
		PreparedStatement preparedStatement = null;																																																																																																																																													
		String sql;
		
		sql = "delete from  plan where plan_name = '" + plan_name + "'";
		
		try {
			preparedStatement = dbConnection.prepareStatement(sql);
 			preparedStatement.executeUpdate();
 
			System.out.println("Plan: Name " + plan_name 
				+ ", removed from the database");
		} 
		catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}

		try{
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
 		}
	}

	@Override
	public Plan getPlanById(int pid) {
		Plan plan = new Plan();
		plan.setPlan_id(pid);
		Statement stmt = null;
		String sql;
		try{
			stmt = dbConnection.createStatement();
			sql = "select * from plan where plan_id = "+pid;
			ResultSet res = stmt.executeQuery(sql);
			while(res.next()){
				plan.setPlan_name(res.getString("plan_name"));
				plan.setPlan_amt(res.getFloat("plan_amt"));
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
		return plan;
	}
	
}
