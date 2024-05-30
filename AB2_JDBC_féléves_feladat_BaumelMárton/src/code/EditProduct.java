package code;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class EditProduct extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private DbMethods dbm=new DbMethods();
	private Validations valid= new Validations();
	private EmpTM etm;
	private String selected="Choose";
	private String valueSelected="Meat";
	private JTextField sourceField;
	private JTextField priceField;
	private boolean checker=true;
	
	private enum ProductType{
		Meat,
		Grain,
		Milk,
		Drink,
		Bakery
	}

	
	
	public EditProduct(JFrame f, EmpTM betm) {
		
		super(f, "Edit Product", true);
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
			comboBox.addItem(RTM(i,1)+" - "+RTM(i,3));
		}
		comboBox.setBounds(132, 44, 231, 22);
		getContentPane().add(comboBox);
		
		
		
		

		
		JLabel lblNewLabel = new JLabel("Choose a Product:");
		lblNewLabel.setBounds(18, 48, 104, 14);
		getContentPane().add(lblNewLabel);
		
		
		JLabel lblNewLabel_1 = new JLabel("Price");
		lblNewLabel_1.setBounds(18, 89, 72, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Source");
		lblNewLabel_3.setBounds(18, 114, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Product Type:");
		lblNewLabel_4.setBounds(18, 139, 96, 14);
		getContentPane().add(lblNewLabel_4);
		
		sourceField = new JTextField();
		sourceField.setColumns(10);
		sourceField.setBounds(113, 111, 215, 20);
		getContentPane().add(sourceField);
		
		
		JComboBox typeBox = new JComboBox(ProductType.values());
		typeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				valueSelected= typeBox.getSelectedItem().toString();
			}
		});
		typeBox.setBounds(113, 135, 215, 22);
		getContentPane().add(typeBox);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String Choosen=selected.toString();
				
				String ChoosenProductType= valueSelected.toString();
				
				System.out.println(ChoosenProductType);
				
				int test;
				
				try {
					test=Integer.parseInt(RTF(priceField));
					checker=true;
				}
				catch(NumberFormatException nE)
				{
					test = 0;
					dbm.SM("Please rewrite the Price");
					checker=false;
				}
				
				Boolean intTest=valid.IntFormatChecker(test, checker);
				
				System.out.println(Choosen+" "+ ChoosenProductType+" "+test +" "+intTest);
					
				if(intTest==true && valid.StringFormat(RTF(sourceField))==true && valid.StringFormat(RTF(priceField))==true) {
					
					int index= selected.indexOf(" - ");
					String Id= selected.substring(0, index);
					
					System.out.println(Id + RTF(priceField) + ChoosenProductType+ RTF(sourceField));
					
					dbm.EditProduct(Id, RTF(priceField), ChoosenProductType, RTF(sourceField));
					JOptionPane.showMessageDialog(null, "You edited a product successfully!" , "Message", 3);
					priceField.setText("");
					sourceField.setText("");
					
					
				}
				else {
					JOptionPane.showMessageDialog(null, "You can't insert this!", "Message", 0);
				}}});
		
		priceField = new JTextField();
		priceField.setColumns(10);
		priceField.setBounds(113, 86, 215, 20);
		getContentPane().add(priceField);
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
