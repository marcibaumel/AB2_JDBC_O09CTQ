package code;


import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class RegistrationDialoge extends JDialog {
	private JTextField name;
	private JTextField birth;
	private JTextField email;

	
	
	DbMethods dbm= new DbMethods();
	Validations valid= new Validations();
	private JPasswordField passwordField;
	
	
	
	
	public RegistrationDialoge(JFrame f) {
		
		super(f, "Regisration", true);
		
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel nameLable = new JLabel("Username:");
		nameLable.setBounds(10, 27, 72, 14);
		getContentPane().add(nameLable);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setBounds(10, 50, 72, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setBounds(10, 75, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Birthday:");
		lblNewLabel_4.setBounds(10, 107, 72, 14);
		getContentPane().add(lblNewLabel_4);
		
		
		
		name = new JTextField();
		name.setBounds(113, 24, 215, 20);
		getContentPane().add(name);
		name.setColumns(10);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(113, 72, 215, 20);
		getContentPane().add(email);
		
		birth = new JTextField();
		birth.setColumns(10);
		birth.setBounds(113, 104, 215, 20);
		getContentPane().add(birth);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(113, 47, 215, 20);
		getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("Create a new user!");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.Connect();
				
				int actualUser=dbm.CountUser();
				
				
				if(valid.yearValidation(RTF(birth)) && valid.emailValidation(RTF(email)) && dbm.ExistingUser(RTF(name)) && valid.StringFormat(RTF(passwordField))&& valid.StringFormat(RTF(name))) {
					
					dbm.CreateANewUser(actualUser, RTF(name), RTF(email), RTF(passwordField), RTF(birth));
					dispose();
				}
				else {
					dbm.ESM("Something wrong!");
				}	
			}
		});
		btnNewButton.setBounds(23, 216, 142, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnMgsem = new JButton("Back!");
		btnMgsem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnMgsem.setBounds(196, 216, 89, 23);
		getContentPane().add(btnMgsem);
		
	
		
	}
	
	
  
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
	
	
	
}
