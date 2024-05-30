package code;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class EditUser extends JDialog {

	private DbMethods dbm=new DbMethods();
	private Validations valid= new Validations();
	private EmpTM etm;
	private String selected="Choose";
	private JTextField birth;
	private JTextField email;
	private JPasswordField passwordField;

	/**
	 * Create the dialog.
	 */
	public EditUser(JFrame f, EmpTM betm) {
		
		super(f, "Edit User", true);
		dbm.Connect();
		etm=betm;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setBounds(25, 227, 89, 23);
		getContentPane().add(btnExit);
		
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected= comboBox.getSelectedItem().toString();
			}
		});
		comboBox.addItem("Choose");
		for(int i =0; i < etm.getRowCount(); i++) {
			comboBox.addItem(RTM(i,1)+" - "+RTM(i,2));
		}
		comboBox.setBounds(132, 44, 231, 22);
		getContentPane().add(comboBox);
		
		
		
		

		
		JLabel lblNewLabel = new JLabel("Choose a user:");
		lblNewLabel.setBounds(18, 48, 104, 14);
		getContentPane().add(lblNewLabel);
		
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setBounds(18, 89, 72, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setBounds(18, 114, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Birthday:");
		lblNewLabel_4.setBounds(18, 139, 72, 14);
		getContentPane().add(lblNewLabel_4);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(113, 111, 215, 20);
		getContentPane().add(email);
		
		birth = new JTextField();
		birth.setColumns(10);
		birth.setBounds(113, 136, 215, 20);
		getContentPane().add(birth);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(113, 86, 215, 20);
		getContentPane().add(passwordField);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(valid.yearValidation(RTF(birth))==true && valid.emailValidation(RTF(email))==true && !selected.contentEquals("Choose")) {
					int index= selected.indexOf(" - ");
					String UserId= selected.substring(0, index);
					
					
					dbm.EditUser(UserId, RTF(email), RTF(passwordField), RTF(birth));
					JOptionPane.showMessageDialog(null, "Edited!", "Message", 3);
					comboBox.setSelectedItem("Choose");
					passwordField.setText("");
					birth.setText("");
					email.setText("");
					
					
				}
				else {
					JOptionPane.showMessageDialog(null, "You can't edit this!", "Message", 0);
				}
				
			}
		});
		btnEdit.setBounds(132, 227, 89, 23);
		getContentPane().add(btnEdit);
		
	}
	
	public String RTM(int row, int col) {
		return etm.getValueAt(row, col).toString();
	}
	
  
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
}
