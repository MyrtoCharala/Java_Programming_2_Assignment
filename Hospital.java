import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.*;

public class Hospital {

	String[] bloodtype = {"A+","A-","AB+","AB-","B+","B-","0+","O-"};

    public void bloodBankStock() {

		//εξαιρεση για null ********* σε όλα τα παραθυράκια

		//Asking for BloodBank Update
		int update = JOptionPane.showConfirmDialog(null, "Would you like to update your blood-bank?", "BLOOD-BANK UPDATE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (update == JOptionPane.YES_OPTION) {

			//Asking which bloodType to update
			Object[] types = {"A+", "A-", "AB+", "AB-", "B+", "B-", "O+", "O-"};
			String type_update = (String)JOptionPane.showInputDialog(null, "BLOOD TYPE UPDATE","Choose a blood-type stock to update", JOptionPane.PLAIN_MESSAGE, null, types, "A+");

			//Asking for blood INCOME or OUTCOME
			String[] kind = {"INCOME", "OUTCOME"};
			int option = JOptionPane.showOptionDialog(null,"Choose the kind of the update", null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, kind, kind[0]);

			//Asking for the blood-amound
			double amound;
			do {
				amound = Double.parseDouble(JOptionPane.showInputDialog("Insert the amound of blood in liters: "));
			} while (amound < 0);

			//Updating the bloodStock
			for (int i=0; i<=7; i++) {

				if (bloodtype[i]  == type_update) {

					if (option == 0) {
						bloodStock[i] += amound;
					} else {
						bloodStock[i] -= amound;
					}

					//Checking if the bloodStock is under the allowed limit of its bloodType
				   	if(bloodStock[i] <= bloodtypeLimit[i]){ //ΜΑΡΙΑ --> σύνδεση με κατώτατο όριο για κάθε ομάδα

						//
						Messages m = new Messages();

						// Showing WARNING message
						m.shortageOfBlood(type_update); //κλήση μεθόδου shortageOfBlood SOS SOS SOS
					}
				}
			}

		}
	}
}
