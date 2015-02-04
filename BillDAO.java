import java.util.ArrayList;

public interface BillDAO {
	int getCustomerID(int billID);
	void addBill(Bill bill);
	ArrayList<Bill> getBills(int cid);
	void payBill(int bill_id);
	float getMonthlyRevenue(String month, String year);

}
