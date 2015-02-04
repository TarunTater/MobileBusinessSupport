import java.util.ArrayList;

public interface ServiceProviderInterface {
	public void addCustomer(Customer customer);
	public void deleteCustomer(String ph);
	public float generateMonthlyRevenue(String month, String year);
	public void addPlan(Plan plan);
	public ArrayList<Complaint> viewPendingComplaints();
}
