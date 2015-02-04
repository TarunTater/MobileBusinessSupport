import java.util.*;
//import java.lang.*;

public class Complaint {
	
	int complaint_id;
	String complaint_type;
	//String status;
	String complaint_status;
	Customer customer;

	Date register_date;
	


public Complaint(){};
public Complaint(int cid, String type){
	complaint_id = cid;
	complaint_type = type;
	complaint_status = "PENDING";
	}
public int getComplaint_id() {
	return complaint_id;
}
public void setComplaint_id(int complaint_id) {
	this.complaint_id = complaint_id;
}
public String getComplaint_type() {
	return complaint_type;
}
public void setComplaint_type(String complaint_type) {
	this.complaint_type = complaint_type;
}
public String getComplaint_status() {
	return complaint_status;
}
public void setComplaint_status(String complaint_status2) {
	this.complaint_status = complaint_status2;
}

public Customer getCustomer() {
	return customer;
}
public void setCustomer(Customer customer) {
	this.customer = customer;
}

public void setRegister_date(Date register_date) {

	this.register_date = register_date;
}
public Date getRegister_date() {
	return register_date;
}
public void print()
{
System.out.format("%10d%20s%14s%25s%14s%14s\n",complaint_id,this.getCustomer().getCustomer_name(),this.getCustomer().getPhone(), complaint_type,complaint_status,register_date);
System.out.println("---------------------------------------------------------------------------------------------------");
}	

public void print1()
{
System.out.format("%14d%20s%14s%14s\n",complaint_id, complaint_type,complaint_status,register_date);
System.out.println("----------------------------------------------------------------");

}	

}
