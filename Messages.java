import javax.swing.JOptionPane;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
/**
 * This class displays messages to blood donors and hospitals*/
public class Messages{
	
	/**
	 * This method creates a connection to the data base*/
	public static Statement connect() {
		String url = "jdbc:sqlserver://195.251.249.161:1433;" + "databaseName=DB20;user=G520;password=94we99494;"
		Connection dbcon;
		Statement stmt = null;
		try {
			dbcon = DriverManager.getConnection(url);
			stmt = dbcon.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}
	
	/**
	 * This method informs volunteers about a specific donation day a hospital from their region has created
	 * @param date the donation day date the hospital has created
	 * @param username is the hospital's username*/
	public static void donationDay(String date, String username){
		String region = null;
		try {
			ResultSet rs = Messages.connect().executeQuery("SELECT Region, Username FROM Hospital WHERE Username = '" + username+ "'");
			while (rs.next()) {
				region = rs.getString("Region");
				//dispaly message to volunteers
			}
			rs.close();		
			Messages.connect().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;	
	}
	
	/**
	 * This method informs all volunteers about the default donation days*/
	public static void donationCalendar() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (java.lang.ClassNotFoundException e) {
			System.out.print("ClassNotFoundException: ");
			System.out.println(e.getMessage());
		}
		do(;;){
		try {	
			String[] month = new String[12];
               		month[0] = "January";
        	        month[1] = "February";
			month[2] = "March";
			month[3] = "April";
			month[4] = "May";
			month[5] = "June";
			month[6] = "July";
			month[7] = "August";
			month[8] = "September";
			month[9] = "October";
			month[10] = "November";
			month[11] = "December";
			ResultSet rs = Messages.connect().executeQuery("SELECT * FROM DonationDays");
			ResultSet RS = Messages.connect().executeQuery("SELECT * FROM BloodDonor");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			int d ,m;
			while ((rs.next())) {
				Date d_date = rs.getDate("D_Date");
				String strDate = formatter.format(d_date);
				String strDateDay = strDate.substring(8);//from current date get day
				String strDateMonth = strDate.substring(5,6);//from current date get month
				String strDateYear = strDate.substring(0,3);//from current date get year
				d = Integer.parseInt(strDateDay);
				m = Integer.parseInt(strDateMonth);
				if (d - 1 <= 0) {
					if(m == 2 || m == 4 || m == 6 || m == 9 || m == 11) {
						d = 30;
						m -= 1;
					} else {
						d = 31;
						if(m == 1){
							m = 12;
						}else{
							m -= 1;
						}
					}
				}
				String strd = Integer.toString(d); 
				String strm = Integer.toString(m);
				if (d < 10) {
					 strd = "0" + d;	 
				}
				if (m < 10) {
					 strm = "0" + m;
				}   
				
				String messageDate = strDateYear + "-" + strm + "-" + strd;
				//if tomorrow is a Blood Donation day
				if (formatter.format(date).equals(messageDate)) {
					String day = rs.getString("D_Day");
					String message = "Tommorow is " + month[m-1] + " " + d + "th: " + day;
					JFrame dialogExample = new DialogExample(message);
					dialogExample.setVisible(true);
				}
				rs.close();
				Messages.connect().close();
			}
			break;
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		return;	
	}

	/**
	 * This method reutrns hospital's region
	 * @rparam username is the hospital' username */
	public static String getRegion(String username) {
		String region = null;
        try {
                Messages.connect();
                Connection dbcon = null;
                Statement stmt = dbcon.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Region, Username FROM Hospital WHERE Username ='" + username +"'");
                while (rs.next()) {
                        region = rs.getString("Region");
                }
                stmt.close();
                Messages.connect().close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        return region;
	}

		/**
	 * This method informs a hospital about it's shortage of blood in a specific blood type
	 * @param bloodtype the specific blood type which is lacking in the specific hospital
	 * @param username the hospital's username*/
	public static void shortageOfBlood(String bloodtype, String username) {
		JOptionPane.showMessageDialog(null, "SHORTAGE OF BLOOD TYPE " + bloodtype, "ALERT MESSAGE", JOptionPane.WARNING_MESSAGE);
		boolean flag = true;
		while(flag) {
			String[] options = {"Blood borrow ", "Create donation day "};
			int choice = JOptionPane.showOptionDialog (null, "You can ask for blood borrow or create a blood donation day",
					"Message",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				    null,options,options[1]);
			if (choice == 0) {
				flag = false;
				Messages.bloodBorrow(Messages.getRegion(username),bloodtype, username);
			} else if (choice == 1) {
				flag = false;
				donationDay(Hospital.makeDonationDay(), username);
			} else {
				JOptionPane.showMessageDialog(null, "You have to choose one of the above!", "ALERT MESSAGE", JOptionPane.WARNING_MESSAGE);
			}
		}
		return;
	}

	/**sends message to hospitals in order to borrow blood to a hospital in the same region
	 * @param region is the region the hospital belongs to
	 * @param bloodtype the type of blood the hospital is lacking
	 * @param username the hospital's username*/
	public static void bloodBorrow(String region, String bloodtype, String username) {
		String r = null;
		try {
                        Messages.connect();
                        Connection dbcon = null;
                        Statement stmt = dbcon.createStatement(); 
                        ResultSet rs = stmt.executeQuery("SELECT H_name, Region, Username FROM Hospital WHERE Username ='" + username +"'");
			while (rs.next()) {
				String name = rs.getString("H_name");
				r = rs.getString("Region");
				if (r.equals(region)) {
					//display message to hospitals
					JOptionPane.showMessageDialog(null, name + " has blood type " + bloodtype+ " shortage. Can you help?", "ALERT MESSAGE", JOptionPane.WARNING_MESSAGE);
				}
			}
                        stmt.close();
                        Messages.connect().close();
                } catch (Exception e) {
                        e.printStackTrace();
                }
		return;
	}

}
