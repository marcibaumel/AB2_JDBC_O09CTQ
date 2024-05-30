package code;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InsertBasement extends JDialog {

	private JTextField quantityField;
	private String selected="Choose";
	private EmpTM etm;
	private DbMethods dbm=new DbMethods();
	private Validations valid= new Validations();
	
	public InsertBasement(JFrame f, EmpTM betm, int UserId) {
		super(f, "Insert New Item", true);
		dbm.Connect();
		etm=betm;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Product");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(25, 33, 78, 31);
		getContentPane().add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected= comboBox.getSelectedItem().toString();	
			}
		});
		comboBox.addItem("Choose");
		for(int i =0; i < etm.getRowCount(); i++) {
			comboBox.addItem(RTM(i,1)+" - "+RTM(i,3));
		}
		comboBox.setBounds(124, 39, 201, 22);
		getContentPane().add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Quantity:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(25, 104, 100, 31);
		getContentPane().add(lblNewLabel_1);
		
		quantityField = new JTextField();
		quantityField.setBounds(124, 112, 86, 20);
		getContentPane().add(quantityField);
		quantityField.setColumns(10);
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(valid.IntformatChecker(RTF(quantityField))) {
					int index= selected.indexOf(" - ");
					String Id= selected.substring(0, index);
					
					//dbm.editBasemenet(Id, RTF(quantityField));
					
					dbm.insertBasement(UserId, Id, RTF(quantityField));
					
					JOptionPane.showMessageDialog(null, "Inserted!", "Message", 3);
					comboBox.setSelectedItem("Choose");
					quantityField.setText("");
				}
				else {
					JOptionPane.showMessageDialog(null, "You can't insert this!", "Message", 0);
				}
			}
		});
		btnInsert.setBounds(226, 227, 89, 23);
		getContentPane().add(btnInsert);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setBounds(335, 227, 89, 23);
		getContentPane().add(btnExit);

	}
	
	public String RTM(int row, int col) {
		return etm.getValueAt(row, col).toString();
	}
	
  
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}

}
