package code;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditBasement extends JDialog {
	private JTextField quantityField;
	private String selected="Choose";
	private EmpTM etm;
	private DbMethods dbm=new DbMethods();
	private Validations valid= new Validations();

	
	public EditBasement(JFrame f, EmpTM betm) {
		
		super(f, "Edit Basement", true);
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
			comboBox.addItem(RTM(i,1)+" - "+RTM(i,2));
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
		
		JButton btnInsert = new JButton("Edit");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(valid.IntformatChecker(RTF(quantityField))) {
					int index= selected.indexOf(" - ");
					String Id= selected.substring(0, index);
					
					dbm.editBasemenet(Id, RTF(quantityField));
					JOptionPane.showMessageDialog(null, "Edited!", "Message", 3);
					comboBox.setSelectedItem("Choose");
					quantityField.setText("");
				}
				else {
					JOptionPane.showMessageDialog(null, "You can't edit this!", "Message", 0);
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
