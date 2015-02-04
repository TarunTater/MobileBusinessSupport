import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CustomerActor implements CustomerInterface {
	DAO_Factory daoFactory;
	Customer customer;
	
	public CustomerActor(DAO_Factory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public Customer getCustomer(){
		return customer;
	}
	
	@Override
	public int setCustomer(String pnumber){
		int status=0;
		try{
			daoFactory.activateConnection();
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			PlanDAO planDAO = daoFactory.getPlanDAO();
			this.customer = customerDAO.getDetailsByPhone(pnumber);
			if(customer.getCustomer_id() != 0){
				int pid = this.customer.getPresent_plan().getPlan_id();
				Plan plan = planDAO.getPlanById(pid);
				plan.setStart_date(this.customer.getPresent_plan().getStart_date());
				this.customer.setPresent_plan(plan);
				status=1;
			}
			daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.COMMIT );
		}
		
		catch(Exception e){
			// End transaction boundary with failure
			daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.ROLLBACK );
			e.printStackTrace();
		}
		return status;
	}
	
	
	@Override
	public void editPersonalDetails(String naam, String Addr)
	{
		try{
			// Start transaction boundary
			daoFactory.activateConnection();
			//String ph = this.customer.getPhone();
			// Carry out DB operations using DAO
			CustomerDAO cdao = daoFactory.getCustomerDAO();
            int cid = this.customer.getCustomer_id();
            cdao.editDetails(cid,naam,Addr);
            Customer temp = cdao.getDetailsByPhone(this.customer.getPhone());
            
            String updateAddr = temp.getAddr();
            String updateName = temp.getCustomer_name();
            this.customer.setAddr(updateAddr);
            this.customer.setCustomer_name(updateName);
            
			// End transaction boundary with success
			daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.COMMIT );
		}catch(Exception e){
				// End transaction boundary with failure
				daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.ROLLBACK );
				e.printStackTrace();
		}
	}
	public void payingBill(int bid)
	{
		try{
			daoFactory.activateConnection();
			// Carry out DB operations using DAO
			BillDAO bdao = daoFactory.getBillDAO();
			int cid = bdao.getCustomerID(bid);
			if (cid == customer.getCustomer_id()) {
				bdao.payBill(bid);
				System.out.println("Bill Paid.");
			}
			else
				System.out.println("Not a valid Bill Number.");

			// End transaction boundary with success
			daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.COMMIT );
		}catch(Exception e){
				// End transaction boundary with failure
				daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.ROLLBACK );
				e.printStackTrace();
		}
	}
	
	public ArrayList<Bill> getAllBills()
	{
		ArrayList<Bill> allBills = new ArrayList<Bill> ();
		try{
			daoFactory.activateConnection();
			CustomerDAO cdao = daoFactory.getCustomerDAO();
            int cid = this.customer.getCustomer_id();
			BillDAO bdao = daoFactory.getBillDAO();
			allBills = bdao.getBills(cid);
			for (Bill bill : allBills)
			{
				bill.setCustomer(customer);
			}
			// End transaction boundary with success
			daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.COMMIT );
		}catch(Exception e){
				// End transaction boundary with failure
				daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.ROLLBACK );
				e.printStackTrace();
		}
		return allBills;
	}

	
	@Override
	public ArrayList<Plan> viewPlans() {
		// TODO Auto-generated method stub
		ArrayList<Plan> plans = new ArrayList<Plan>();
		try{
			daoFactory.activateConnection();
			PlanDAO planDAO = daoFactory.getPlanDAO();
			plans = planDAO.getAllPlans();
			
			daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.COMMIT );
		}
		
		catch(Exception e){
			// End transaction boundary with failure
			daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.ROLLBACK );
			e.printStackTrace();
		}
		return plans;
	}

	@Override
	public void changePlan(String pname) {
		// TODO Auto-generated method stub

		try{
		    
	        Date todaysdate = new Date(System.currentTimeMillis());
	        daoFactory.activateConnection();
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Plan presPlan = customer.getPresent_plan();
			Date dstart = presPlan.getStart_date();
			Calendar startCalendar = Calendar.getInstance(); 
		    startCalendar.setTime(dstart);
		    Calendar endCalendar = Calendar.getInstance();
		    endCalendar.setTime(todaysdate);

		    int yearDiff = endCalendar.get(Calendar.YEAR)- startCalendar.get(Calendar.YEAR);
		    int monthsBetween = endCalendar.get(Calendar.MONTH)-startCalendar.get(Calendar.MONTH) +12*yearDiff;

		    if(endCalendar.get(Calendar.DAY_OF_MONTH) >= startCalendar.get(Calendar.DAY_OF_MONTH))
		        monthsBetween = monthsBetween + 1;
		    //return monthsBetween;
		    Bill bill = new Bill();
		    float amt = monthsBetween*presPlan.getPlan_amt();
		    bill.setCustomer(customer);
		    bill.setBill_date(todaysdate);
		    bill.setStatus("PENDING");
		    bill.setAmount(amt);
		    
		    endCalendar.add(Calendar.MONTH, 1);
		    Date n = new Date(endCalendar.getTimeInMillis());
		    bill.setDue_date(n);
		    
		    BillDAO billDAO = daoFactory.getBillDAO();
			billDAO.addBill(bill);
			
			PlanDAO planDAO = daoFactory.getPlanDAO();
			Plan plan = planDAO.getPlanByName(pname);
			if(plan.getPlan_id() == 0)
				System.out.println("Invalid Plan");
			else{
			plan.setStart_date(todaysdate);
			this.customer.setPresent_plan(plan);
			customerDAO.changePlan(customer.getCustomer_id(), plan);
			}
			daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.COMMIT );
		}
		
		catch(Exception e){
			// End transaction boundary with failure
			daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.ROLLBACK );
			e.printStackTrace();
		}
	}

	@Override
	public void registerComplaint(String ctype) {
		// TODO Auto-generated method stub
		try{
			//SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
	        Date todaysdate = new Date(System.currentTimeMillis());
			daoFactory.activateConnection();
			ComplaintDAO complaintDAO = daoFactory.getComplaintDAO();
			Complaint complaint = new Complaint();
			complaint.setComplaint_type(ctype);
			complaint.setComplaint_status("PENDING");
			complaint.setCustomer(customer);
			complaint.setRegister_date(todaysdate);
			complaintDAO.addComplaint(complaint);
			daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.COMMIT );
		}
		
		catch(Exception e){
			// End transaction boundary with failure
			daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.ROLLBACK );
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<Complaint> checkComplaintStatus(){
		ArrayList<Complaint> complaints = new ArrayList<Complaint>();
		try{
			daoFactory.activateConnection();
			ComplaintDAO complaintDAO = daoFactory.getComplaintDAO();
			complaints = complaintDAO.getComplainByCid(customer.getCustomer_id());
			daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.COMMIT );
		}

		catch(Exception e){
			// End transaction boundary with failure
			daoFactory.deactivateConnection( DAO_Factory.TXN_STATUS.ROLLBACK );
			e.printStackTrace();
		}
		return complaints;
	}
}
