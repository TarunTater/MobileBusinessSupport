import java.util.ArrayList;

public interface ComplaintDAO {
	public void addComplaint(Complaint complaint);
	public ArrayList<Complaint> getComplainByCid(int cid);
	public ArrayList<Complaint> getPendingComplaints();
}
