package code;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private EmpTM etm;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private static DbMethods dbm= new DbMethods();
	

	
	public static void main(String[] args) {
		dbm.Connect();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 47, 112, 38);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(10, 100, 111, 43);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(132, 56, 241, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(132, 115, 241, 20);
		contentPane.add(passwordField);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				int UserId=dbm.UserLoged(RTF(textField));
				
				dbm.ReadAllUserData();
			
				
				int ans=dbm.CheckLogin(RTF(textField), RTF(passwordField));
				if(ans>=1) {	
					dispose();
					Menu jtp = new Menu(UserId);
					jtp.setVisible(true);
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Wrong name or password", "Message", 0);
				}
				
				
				
			}
		});
		
		btnNewButton.setBounds(21, 190, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Create a new Profil");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrationDialoge regD= new RegistrationDialoge(Login.this);
				regD.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(149, 190, 156, 23);
		contentPane.add(btnNewButton_1);
	}
	
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
}
