
import java.util.ArrayList;
import java.util.Date;

public class Plan {
	int plan_id;
	String plan_name;
	float plan_amt;
	Date start_date;
	ArrayList<Customer> customers;

	public Plan() {}
	
	public Plan(String plan_name, float plan_amt) {
		this.plan_name = plan_name;
		this.plan_amt = plan_amt;
	}
	public Plan(int plan_id, String plan_name, float plan_amt) {
		this.plan_id = plan_id;
		this.plan_name = plan_name;
		this.plan_amt = plan_amt;
	}
	public int getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(int plan_id) {
		this.plan_id = plan_id;
	}
	public String getPlan_name() {
		return plan_name;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}
	public float getPlan_amt() {
		return plan_amt;
	}
	public void setPlan_amt(float plan_amt) {
		this.plan_amt = plan_amt;
	}
	public void print(){ 
		System.out.format("%9s%14.2f\n",plan_name,plan_amt);
		System.out.println("------------------------------");
	}
	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}
	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
}
