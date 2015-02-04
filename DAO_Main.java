import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class DAO_Main {

	public static void main(String[] args) throws IOException{
		
		DAO_Factory daofactory = new DAO_Factory();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Scanner scanner = new Scanner (System.in);
		while(true) 
		{
			System.out.println("Please select one of the options:");
			System.out.println("1. Customer");
			System.out.println("2. Service Provider");		
			
			int opt;
			do{
				System.out.print("Your Option:");
				opt = scanner.nextInt();
				if (opt != 1 && opt != 2) 
					System.out.println("Invalid Option. Please Enter Again.");
			}while(opt != 1 && opt != 2);
	
	
			if (opt == 1)
			{
				
				System.out.print("Your Phone Number:");
				String pnumber = in.readLine();
				CustomerActor customerActor = new CustomerActor(daofactory);
				if(customerActor.setCustomer(pnumber) == 0)
				{
					System.out.println("Invalid Phone Number");
				}
				else{
					String welcome = "Welcome " + customerActor.getCustomer().getCustomer_name() + "! Please select an operation to perform:" ;
					System.out.println(welcome);
					int check = 0;
					while(check == 0)
					{
						System.out.println("1. Edit Details");
						System.out.println("2. Get Bills");
						System.out.println("3. Pay Bills");
						System.out.println("4. View Plans");
						System.out.println("5. Change Plan");
						System.out.println("6. Register Complaint");
						System.out.println("7. Check Complaint Status");
						System.out.println("8. Exit");
						System.out.print("Your Option:");
						int opt1 = scanner.nextInt();
						
						switch(opt1) 
						{
							case 1:				
								System.out.println("Change name(yes/no)?");
					            String yn = in.readLine();
					            String name;
					            if(yn.equalsIgnoreCase("yes"))
					            {    System.out.println("input name");
					                name = in.readLine();
					            }
					            else
					                name="";
					            System.out.println("change address(yes/no)?");
					            String yna = in.readLine();
					            String address;
					            if(yna.equalsIgnoreCase("yes"))
					            {    System.out.println("input address");
					                address = in.readLine();
					            }
					            else
					                address="";
					            customerActor.editPersonalDetails(name,address);
					            break;
					            
							case 2:
								System.out.println("Your pending bills are :");
								ArrayList<Bill> allBills = customerActor.getAllBills();
								System.out.println("---------------------------------------------------------------------");	
								System.out.println("     Bill No    Bill Status      Amount      Bill Date	   Due Date");
								System.out.println("---------------------------------------------------------------------");
	
								for (Bill bill : allBills)
									bill.print1();
								break;
								
							case 3:
								System.out.println("Which bill do you want to pay? Please input bill number");
								int bid = scanner.nextInt();
								customerActor.payingBill(bid);
								break;
								
							case 4:
								System.out.println("Our plans for you : ");
								ArrayList<Plan> plans = customerActor.viewPlans();
								System.out.println("------------------------------");	
								System.out.println("  Plan Name    Plan Amount ");
								System.out.println("------------------------------");
	
								for (Plan p : plans)
									p.print();
								break;
								
							case 5:
								System.out.println("Which plan do you want to choose?");
					            String cp = in.readLine();
					            customerActor.changePlan(cp);
					            break;
					            
							case 6:
								System.out.println("You have a complaint :o, we will be onto it!");
								System.out.println("Please input the complaint type : Network Problem / Plan Not Changed  ");						
								String comp = in.readLine();
								customerActor.registerComplaint(comp);
								break;
								
							case 7:
								System.out.println("Complaints Status - ");
								ArrayList<Complaint> complaints = customerActor.checkComplaintStatus();
								System.out.println("----------------------------------------------------------------");	
								System.out.println("     Complaint No    Complaint            Status    RegisterDate");
								System.out.println("----------------------------------------------------------------");
	
								for (Complaint c : complaints)
									c.print1();
								break;
								
							case 8:
								System.out.println("Hope you were satisfied with our services!");
								check = 1;
								break;
								
							default:
								System.out.println("Invalid Option!");
								
						}
					}
				}		
			}
			
			else if (opt == 2) {
				System.out.println("Welcome Service Provider!");
				int check = 0;
				while (check == 0) {
					System.out.println("Please select an operation to perform:");
					System.out.println("1. Add a customer.");
					System.out.println("2. Delete a customer.");
					System.out.println("3. Add a plan.");
					System.out.println("4. View Pending Complaints.");
					System.out.println("5. Generate revenue for a month.");
					System.out.println("6. Exit");
					System.out.print("Your Option:");
					int opt1 = scanner.nextInt();
					
					ServiceProviderActor serviceProviderActor = new ServiceProviderActor(daofactory);
					
					switch(opt1) {
					case 1:
						Customer customer = new Customer();
						Plan plan = new Plan();	
						scanner.nextLine();
						System.out.print("Enter Name:");
						String inp = scanner.nextLine();
	
						customer.setCustomer_name(inp);
						//System.out.println("");
						System.out.print("Enter 10 digit Phone number: ");
						
						String num = scanner.nextLine();
						while (!(num.length() == 10 && num.matches("[0-9]+")))		
						{
							System.out.print("Number should be of 10 digits ");
							 num = scanner.nextLine();
						}					
						customer.setPhone(num);
						
						System.out.print("Enter Address: ");
						customer.setAddr(scanner.nextLine());
						
						System.out.print("Enter Plan Name: ");
						plan.setPlan_name(scanner.nextLine());
						customer.setPresent_plan(plan);
						
						serviceProviderActor.addCustomer(customer);
						break;
						
					case 2:
						System.out.print("Enter Customer's Phone Number:");
						scanner.nextLine();
						String ph = scanner.nextLine();
						serviceProviderActor.deleteCustomer(ph);
						//System.out.println("Customer Deleted!");
						break;
						
					case 3:
						Plan plan1 = new Plan();
						System.out.println("Enter Plan Name: ");
						String s1 = scanner.next();
						plan1.setPlan_name(s1);
						System.out.println("Enter Plan amount: ");
						float g =scanner.nextFloat();
						plan1.setPlan_amt(g);
						serviceProviderActor.addPlan(plan1);
						break;
						
					case 4:
						ArrayList<Complaint> pending_complaints = serviceProviderActor.viewPendingComplaints();
						System.out.println("-----------------------------------------------------------------------------------------------");	
						System.out.println("     ComplaintID         Name        Phone     	      ComplaintType         Status     RegisterDate");
						System.out.println("-----------------------------------------------------------------------------------------------------");
						
						for(int i = 0;i< pending_complaints.size();i++)
						{
							pending_complaints.get(i).print();
						}
						break;
						
					case 5:
						scanner.nextLine();
						System.out.print("Enter Month(mm): ");
						String month = scanner.nextLine();
						System.out.print("Enter Year(yyyy): ");
						String year = scanner.nextLine();
	
						float revenue = serviceProviderActor.generateMonthlyRevenue(month, year);
						System.out.println("The Revenue For " + month + "-" + year + " is: Rs." + revenue);
						break;
						
					case 6:
						check = 1;
						System.out.println("Good Bye!");
						break;
						
					default:
						System.out.println("Invalid Option!");
						
					}
				}
			}
		}	
	}
}
