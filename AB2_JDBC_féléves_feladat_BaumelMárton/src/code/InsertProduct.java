package code;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;

public class InsertProduct extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField name;
	private JTextField source;
	private String selected="Meat";
	private boolean checker=true;	
	private enum ProductType{
		Meat,
		Grain,
		Milk,
		Drink,
		Bakery
	}
	
	DbMethods dbm= new DbMethods();
	Validations valid= new Validations();
	private JTextField priceText;
		public InsertProduct(JFrame f) {
		
		super(f, "Insert New Product", true);
		
		
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel nameLable = new JLabel("Product Name:");
		nameLable.setBounds(10, 27, 102, 14);
		getContentPane().add(nameLable);
		
		JLabel priceLabel = new JLabel("Price ");
		priceLabel.setBounds(10, 50, 72, 14);
		getContentPane().add(priceLabel);
		
		JLabel lblNewLabel_3 = new JLabel("Product Type");
		lblNewLabel_3.setBounds(10, 75, 102, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Source ");
		lblNewLabel_4.setBounds(10, 107, 72, 14);
		getContentPane().add(lblNewLabel_4);
		
		
		
		name = new JTextField();
		name.setBounds(113, 24, 215, 20);
		getContentPane().add(name);
		name.setColumns(10);
		
		source = new JTextField();
		source.setColumns(10);
		source.setBounds(113, 104, 253, 57);
		getContentPane().add(source);
		
		JButton btnCreateProduct = new JButton("Insert a new Product");
		btnCreateProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.Connect();
				
				int actualProduct=dbm.CountProduct();
				String ChoosenProductType= selected.toString();
				int test;
				
				try {
					test=Integer.parseInt(RTF(priceText));
					checker=true;
				}
				catch(NumberFormatException nE)
				{
					test = 0;
					dbm.SM("Please rewrite the Price");
					checker=false;
				}
				
				Boolean intTest=valid.IntFormatChecker(test, checker);
				
				System.out.println(actualProduct+" "+ ChoosenProductType+" "+test +" "+intTest);
					
				if(intTest==true && valid.StringFormat(RTF(name))==true) {
					int index= selected.indexOf(" - ");
					//String Id= selected.substring(0, index);
					
					dbm.InsertNewProduct(dbm.CountProduct(), RTF(name), test, ChoosenProductType, RTF(source));
					name.setText("");
					source.setText("");
					priceText.setText("");
					
				}
				else {
					JOptionPane.showMessageDialog(null, "You can't insert this!", "Message", 0);
				}
				
				
			}
		});
		btnCreateProduct.setBounds(23, 216, 187, 23);
		getContentPane().add(btnCreateProduct);
		
		JButton btnMgsem = new JButton("Back!");
		btnMgsem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnMgsem.setBounds(239, 216, 89, 23);
		getContentPane().add(btnMgsem);
		
		JComboBox comboBox = new JComboBox(ProductType.values());
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected= comboBox.getSelectedItem().toString();
			}
		});
		comboBox.setBounds(113, 71, 215, 22);
		getContentPane().add(comboBox);
		
		
		priceText = new JTextField();
		priceText.setBounds(113, 47, 215, 20);
		getContentPane().add(priceText);
		priceText.setColumns(10);
		
		
	
		
	}


	
	
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
	
	
}
