public interface CustomerDAO {
	public int getCustomerId(String pnum);
	public void changePlan(int cid, Plan p);	
	public void editDetails(int cid, String naam, String Addr);
	public void addCustomer(Customer c);
	public void deleteCustomer(int cid);
	public Customer getDetailsByPhone(String pnum);
	public Customer getDetailsById(int cid);
}
