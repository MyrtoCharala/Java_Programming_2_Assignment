import javax.swing.JOptionPane;
/**
 * This class displays the main menu of the application
 * and the custom menus of each category of user;
 * Blood donor and Hospital*/
public class HomeMenu {

	public static void main(String[] args) {
				boolean f = true;
				while (f) {
					String[] kind = {"Donor", "Hospital"};
					int ans = JOptionPane.showOptionDialog(null, "In which way would you like to use the application?",
							"MAIN MENU", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, kind, kind[0]);
					if (ans == 0) {
						HomeMenu.printDonorMenu();
						f = false;
						break;
					} else if (ans == 1) {
						HomeMenu.printHospitalMenu();
						f = false;
						break;
					}
				}	
	}
	
	/**
	 * This method displays blood donors' main menu,
	 * which includes sign up, log in fill out and update questionnaire*/
	public static void printDonorMenu() {
		String[] q = {"Fill out our questionnaire", "Log in"};
		int ans = JOptionPane.showOptionDialog(null, "Please choose one of the following",
                                                                "DONOR'S MENU", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, q, q[0]);
		switch (ans) {
			case 0:
				BloodDonor.questionnaire();
				JOptionPane.showMessageDialog(null, "Congratulations! You've already done the first step to become a Blood Donor");
				JOptionPane.showMessageDialog(null, "Now press OK to sign up to our application");
				BloodDonor.signUp();
				JOptionPane.showMessageDialog(null, "Select log in option in the following menu");
				printDonorMenu();
			case 1:
				String username = (BloodDonor.logIn());
				JOptionPane.showMessageDialog(null, "Welcome " + username);
				BloodDonor.printDonationCalendar();
				long start = System.currentTimeMillis();
				System.out.println("before thread");
				try {
					Thread.sleep(8000);
					System.out.println("between");
					HomeMenu.donorSecondMenu(username);
					System.out.println("under thread");

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			default:
				HomeMenu.printDonorMenu();
		}
	}
	
	/***/
	public static void donorSecondMenu(String username) {
		boolean f = true;
		String choice = null;
		while (f) {
			String[] kind = {"Update questionnaire", "Log out"};
			choice = (String) JOptionPane.showInputDialog(null,"Please choose one of the following " +username,
					"DONOR MENU", JOptionPane.PLAIN_MESSAGE, null, kind, "Update questionnaire");
			try {
				if (choice.equals("Update questionnaire") ) {
					BloodDonor.updateQuestionnaire();
				} else if (choice.equals("Log out")) {
					JOptionPane.showMessageDialog(null, "You have successfully log out. Press OK to return to main menu");
	    			String [] args = null;
	    			HomeMenu.main(args);
	    			f = false;
				} else {
					f = true;
				}
				if (choice.equals("null")) {
					throw new NullPointerException();
				}
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(null, "Please choose one of the options", "ALERT MESSAGE", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	/**
	 * This method displays hospitals' main menu, 
	 * which inclues sign up, log in, create new donation day,
	 * borrow blood from another hospital and update blood bank stcok*/
	public static void printHospitalMenu() {
		String username = null;
		String[] kind = {"Sign up", "Log in"};
		int ans = JOptionPane.showOptionDialog(null, "Please choose one of the following",
                                                                "HOSPITAL MENU", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, kind, kind[0]);
		switch (ans) {
			case 0:
				Hospital.signUp();
				username = (Hospital.logIn());
				JOptionPane.showMessageDialog(null, "Now we would like you to inform us about your blood bank stock");
				Hospital.bloodBankStock(username);
				hospitalSecondMenu(username);
			case 1:
				HomeMenu.hospitalSecondMenu(Hospital.logIn());
				HomeMenu.printHospitalMenu();
			default:
				HomeMenu.printHospitalMenu();	
		}	
		
	}
	
	/**
	 * This method displays hospital's second menu,
	 * meaning after they succesfully log in
	 * @param username is the hospital's username*/
	public static void hospitalSecondMenu(String username) {
		boolean f = true;
		String g = null;
		do {
			try {
				Object[] opt = {"Update blood bank stock","Borrow Blood Units from other hospitals", "Create new donation day", "Log out"};
        	        	g = (String) JOptionPane.showInputDialog(null,"Please choose one of the following ",
					"HOSPITAL MENU", JOptionPane.PLAIN_MESSAGE, null, opt, "Update blood bank stock");
			
                		if (g == "Update blood bank stock") {
                			Hospital.bloodBankStock(username);
                			f = false;
                		} else if (g.equals ("Borrow Blood Units from other hospitals")) {
                			String bloodtype = null;
                        	String[] bloodtypes = { "O+" , "O-" , "A+" , "A-" , "B+" , "B-" , "AB+" , "AB-" };
                        	boolean flag = true;
                        	while (flag) {
                        		bloodtype = (String) JOptionPane.showInputDialog(null, "Choose the blood type", 
                        			"SIGN UP", JOptionPane.PLAIN_MESSAGE, null, bloodtypes , "O+");
                	            try {
                	            	if (bloodtype.equals(null)) {
                	            		
                	            		throw new NullPointerException();
                	            	} else {
                	            		flag = false;
                                    }		
                	            } catch (NullPointerException e) {
                	            	JOptionPane.showMessageDialog(null, "Please choose the blood type",
                                                 	"ALERT MESSAGE" , JOptionPane.WARNING_MESSAGE);
                                }
                        	}	
                        	Messages.bloodBorrow(Messages.getRegion(username),bloodtype,username);
                        	HomeMenu.hospitalSecondMenu(username);
                		} else if (g.equals ("Create new donation day")) {
                			Messages.donationDay(Hospital.makeDonationDay(), username);
                        	JOptionPane.showMessageDialog(null, "Thank you! You help us strengthen our action");
                		} else if (g.equals ("Log out")){
                			JOptionPane.showMessageDialog(null, "You have successfully log out. Press OK to return to main menu");
                			String [] args = null;
                			HomeMenu.main(args);
                			f = false;
                		} else {
                			throw new NullPointerException();
                		}
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(null, "Please choose one of the options", "ALERT MESSAGE", JOptionPane.WARNING_MESSAGE);
			}
        } while (f);
	}

}
