import java.lang.*;
import java.util.ArrayList;

public class Customer {
	int customer_id;
	String customer_name;
	String phone;
	String addr;
	Plan present_plan;
	ArrayList<Plan> plans = new ArrayList<Plan>();
	ArrayList<Complaint> complaints = new ArrayList<Complaint>();
	ArrayList<Bill> bills = new ArrayList<Bill>();
	
	public Customer() { }
	
	public Customer(int customer_id, String customer_name, String phone,
			String addr, Plan present_plan) {
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.phone = phone;
		this.addr = addr;
		this.present_plan = present_plan;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Plan getPresent_plan() {
		return present_plan;
	}

	public void setPresent_plan(Plan present_plan) {
		this.present_plan = present_plan;
	}

	public ArrayList<Plan> getPlans() {
		return plans;
	}

	public void setPlans(ArrayList<Plan> plans) {
		this.plans = plans;
	}

	public ArrayList<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(ArrayList<Complaint> complaints) {
		this.complaints = complaints;
	}

	public ArrayList<Bill> getBills() {
		return bills;
	}

	public void setBills(ArrayList<Bill> bills) {
		this.bills = bills;
	}

	public void print()
	{
		System.out.println("Customer Name: " + customer_name);
		System.out.println("Phone: " + phone);
	}
}
