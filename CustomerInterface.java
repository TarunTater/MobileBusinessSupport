import java.util.ArrayList;

public interface CustomerInterface{
	public void editPersonalDetails(String naam, String Addr);
	void payingBill(int bill_id);
	public ArrayList<Bill> getAllBills();
	public int setCustomer(String pnumber);
	public Customer getCustomer();//whatever you need to use outside, is written here.
	public ArrayList<Plan> viewPlans();
	public void changePlan(String pname);
	public void registerComplaint(String ctype);
	public ArrayList<Complaint> checkComplaintStatus();
}
