import java.util.ArrayList;

public interface PlanDAO {
	public ArrayList<Plan> getAllPlans();
	public Plan getPlanByName(String pname);
	public Plan getPlanById(int pid);
	public void addPlan(Plan plan);
	public void deletePlan(String plan_name);
}
