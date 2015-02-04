import java.lang.*;
import java.util.Date;


public class Bill {
	int bill_id;
	String bill_status;
	float amount;
	Date bill_date;
	Date due_date;
	Customer customer;
	
	public Bill(){ }
	
	public Bill(int bill_id, String bill_status, float amount, Customer customer,
			Date bill_date, Date due_date) {
		this.bill_id = bill_id;
		this.customer = customer;
		this.bill_status = bill_status;
		this.amount = amount;
		this.bill_date = bill_date;
		this.due_date = due_date;
	}
	public int getBill_id() {
		return bill_id;
	}
	public void setBill_id(int bill_id) {
		this.bill_id = bill_id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getStatus() {
		return bill_status;
	}
	public void setStatus(String bill_status) {
		this.bill_status = bill_status;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Date getBill_date() {
		return bill_date;
	}
	public void setBill_date(Date bill_date) {
		this.bill_date = bill_date;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	public void print() { 
		System.out.format("%9d%14d%20s%14.2f%14s%14s\n",this.bill_id,this.getCustomer().getCustomer_id(), bill_status,amount,bill_date, due_date);
		System.out.println("---------------------------------------------------------------------");
	}
	public void print1() {
		System.out.format("%9d%14s%16.2f%14s%14s\n",this.bill_id, bill_status,amount,bill_date, due_date);
		System.out.println("---------------------------------------------------------------------");

	}
}
