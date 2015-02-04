import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ServiceProviderActor implements ServiceProviderInterface{
	
	DAO_Factory daofactory;
	
	ServiceProviderActor(DAO_Factory daofactory) {
		this.daofactory = daofactory;
	}
	

	@Override
	public void addCustomer(Customer customer) {
		try {
			daofactory.activateConnection();
			
			CustomerDAO customerDAO = daofactory.getCustomerDAO();
			PlanDAO planDAO = daofactory.getPlanDAO();
			String pname = customer.getPresent_plan().getPlan_name();
			Plan p = planDAO.getPlanByName(pname);
			if(p.getPlan_id() == 0)
				System.out.println("Invalid Plan - Customer Not Added");
			else{
				customer.setPresent_plan(p);
				Date d = new Date(System.currentTimeMillis());
				System.out.println(d);
				customer.getPresent_plan().setStart_date(d);
			
			customerDAO.addCustomer(customer);
			}
			daofactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
			
		}catch (Exception e) {
			daofactory.deactivateConnection( DAO_Factory.TXN_STATUS.ROLLBACK );
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCustomer(String ph) {
		try {
			daofactory.activateConnection();
			
			CustomerDAO customerDAO = daofactory.getCustomerDAO();
			int cid = customerDAO.getCustomerId(ph);
			if(cid< 1)
			{
				System.out.println("phone number not valid");
				return;
			}
			//System.out.println(cid);
			customerDAO.deleteCustomer(cid);
			
			daofactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
			
			
		}catch(Exception e) {
			daofactory.deactivateConnection( DAO_Factory.TXN_STATUS.ROLLBACK );
			e.printStackTrace();			
		}
		
	}

	@Override
	public float generateMonthlyRevenue(String month, String year) {
		float revenue = 0;
		try {
			daofactory.activateConnection();
			
			BillDAO billDAO = daofactory.getBillDAO();
			revenue = billDAO.getMonthlyRevenue(month, year);
			System.out.println("SP: " + revenue);
			daofactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
		}catch(Exception e) {
			daofactory.deactivateConnection( DAO_Factory.TXN_STATUS.ROLLBACK );
			e.printStackTrace();
		}
		
		return revenue;
	}


	@Override
	public void addPlan(Plan plan) {
		// TODO Auto-generated method stub
		try {
		daofactory.activateConnection();
		PlanDAO planDAO = daofactory.getPlanDAO();
		planDAO.addPlan(plan);
		daofactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
		}
		catch (Exception e) {
		daofactory.deactivateConnection( DAO_Factory.TXN_STATUS.ROLLBACK );
		e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<Complaint> viewPendingComplaints() {
	// TODO Auto-generated method stub
		ArrayList<Complaint> pending_complaints = new ArrayList<Complaint>();
		try {
			daofactory.activateConnection();
			ComplaintDAO complaintDAO = daofactory.getComplaintDAO();
			pending_complaints = complaintDAO.getPendingComplaints();
			CustomerDAO customerDAO = daofactory.getCustomerDAO();
			int cid=0;	
			
			for(int i = 0;i< pending_complaints.size();i++)
			{
				cid = pending_complaints.get(i).getCustomer().getCustomer_id();
				pending_complaints.get(i).setCustomer(customerDAO.getDetailsById(cid));
				
			//pending_complaints.get(i).print();
			}
			daofactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
			}
		catch (Exception e) {
		daofactory.deactivateConnection( DAO_Factory.TXN_STATUS.ROLLBACK );
		e.printStackTrace();
		}
		return pending_complaints;
	}

		
}
