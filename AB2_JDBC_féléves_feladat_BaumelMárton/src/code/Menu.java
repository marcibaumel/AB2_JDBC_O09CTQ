package code;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

public class Menu extends JFrame {

	private JPanel contentPane;
	private static DbMethods dbm = new DbMethods();
	private EmpTM UserEtm;
	private EmpTM ProductEtm;
	private EmpTM BasementEtm;
	private JTextField minPrice;
	private JTextField maxPrice;
	

	/**
	 * Create the frame.
	 */
	public Menu(int UserId) {
		
		//dbm.SM(""+UserId);
		//dbm.Reg();
		
		dbm.Connect();
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Catalog", null, panel, null);
		panel.setLayout(null);
		
		JButton btnCatalog = new JButton("Catalog");
		btnCatalog.setBackground(Color.GREEN);
		btnCatalog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ProductEtm=dbm.ReadAllProduct();
				ProductList pl= new ProductList(Menu.this, ProductEtm);
				pl.setVisible(true);
				
			}
		});
		btnCatalog.setBounds(10, 11, 89, 23);
		panel.add(btnCatalog);
		
		JButton btnInsertToCatalog = new JButton("Insert Into Catlog");
		btnInsertToCatalog.setBackground(Color.GREEN);
		btnInsertToCatalog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertProduct iP= new InsertProduct(Menu.this);
				iP.setVisible(true);
			}
		});
		btnInsertToCatalog.setBounds(10, 45, 198, 23);
		panel.add(btnInsertToCatalog);
		
		JButton btnEditCatalog = new JButton("Edit Catalog");
		btnEditCatalog.setBackground(Color.GREEN);
		btnEditCatalog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductEtm=dbm.ReadAllProduct();
				
				EditProduct eP= new EditProduct(Menu.this, ProductEtm);
				eP.setVisible(true);
			}
		});
		btnEditCatalog.setBounds(10, 111, 110, 23);
		panel.add(btnEditCatalog);
		
		JButton btnPriceRangeCatalog = new JButton("Price Range");
		btnPriceRangeCatalog.setBackground(Color.GREEN);
		btnPriceRangeCatalog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isNumeric(RTF(minPrice))==true &&isNumeric(RTF(maxPrice))==true ){
					dbm.priceRange2(RTF(minPrice), RTF(maxPrice));
				}
				else {
					dbm.SM("Something Wrong!");
				}
				
			}
		});
		btnPriceRangeCatalog.setBounds(130, 111, 110, 23);
		panel.add(btnPriceRangeCatalog);
		
		JButton btnDeleteFromCatalog = new JButton("Delete From Catalog");
		btnDeleteFromCatalog.setBackground(Color.GREEN);
		btnDeleteFromCatalog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ProductEtm=dbm.ReadAllProduct();
				
				DelProduct ul= new DelProduct(Menu.this, ProductEtm);
				
				ul.setVisible(true);
			}
		});
		btnDeleteFromCatalog.setBounds(10, 79, 198, 23);
		panel.add(btnDeleteFromCatalog);
		
		JButton btnExportToCsv = new JButton("Export to .csv");
		btnExportToCsv.setBackground(Color.GREEN);
		btnExportToCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.WriteAllProductDataToFile("Product.csv");
				JOptionPane.showMessageDialog(null, "Export Finished", "Message", 1);
			}
		});
		btnExportToCsv.setBounds(10, 151, 228, 23);
		panel.add(btnExportToCsv);
		
		minPrice = new JTextField();
		minPrice.setBounds(250, 112, 55, 20);
		panel.add(minPrice);
		minPrice.setColumns(10);
		
		maxPrice = new JTextField();
		maxPrice.setBounds(315, 112, 55, 20);
		panel.add(maxPrice);
		maxPrice.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Min");
		lblNewLabel.setBounds(250, 93, 46, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Max");
		lblNewLabel_1.setBounds(315, 93, 46, 14);
		panel.add(lblNewLabel_1);
		
		JButton exitBtn = new JButton("Exit");
		exitBtn.setBackground(Color.GREEN);
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exitBtn.setBounds(320, 189, 89, 23);
		panel.add(exitBtn);
		
		JLabel imgCatalogLabel = new JLabel("");
		imgCatalogLabel.setIcon(new ImageIcon(Menu.class.getResource("/Catalog.gif")));
		imgCatalogLabel.setBounds(234, 11, 175, 78);
		panel.add(imgCatalogLabel);
		
		
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Basement", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("User", null, panel_2, null);
		panel_2.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Extra Functions", null, panel_3, null);
		panel_3.setLayout(null);
		
		JButton btnUserList = new JButton("User List");
		btnUserList.setBackground(Color.GREEN);
		btnUserList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				UserEtm=dbm.ReadAllUserData2();
				UserList ul= new UserList(Menu.this, UserEtm);
				ul.setVisible(true);
			}
		});
		btnUserList.setBounds(22, 88, 97, 23);
		panel_2.add(btnUserList);
		
		JButton btnMetadata = new JButton("Metadata");
		btnMetadata.setBackground(Color.GREEN);
		btnMetadata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GetMetadata meta= new GetMetadata(Menu.this);
				meta.setVisible(true);
				
			}
		});
		btnMetadata.setBounds(10, 21, 102, 23);
		panel_3.add(btnMetadata);
		
		JButton btnExportToTXT = new JButton("Export to TXT");
		btnExportToTXT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panel_3.add(btnExportToTXT);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(Color.GREEN);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.DisConnect();
				dispose();
			}
		});
		btnExit.setBounds(320, 189, 89, 23);
		panel_3.add(btnExit);
		
		JButton btnDeleteUser = new JButton("Delete a User");
		btnDeleteUser.setBackground(Color.GREEN);
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserEtm=dbm.ReadAllUserData2();
				DelUserList ul= new DelUserList(Menu.this, UserEtm);
				ul.setVisible(true);
				
			}
		});
		btnDeleteUser.setBounds(151, 88, 111, 23);
		panel_2.add(btnDeleteUser);
		
		JButton btnEditUser = new JButton("Edtit User");
		btnEditUser.setBackground(Color.GREEN);
		btnEditUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserEtm=dbm.ReadAllUserData2();
				EditUser eU= new EditUser(Menu.this, UserEtm);
				eU.setVisible(true);
			}
		});
		
		JButton exitBtn_2 = new JButton("Exit");
		exitBtn_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exitBtn_2.setBackground(Color.GREEN);
		exitBtn_2.setBounds(320, 189, 89, 23);
		panel_2.add(exitBtn_2);
		btnEditUser.setBounds(283, 88, 89, 23);
		panel_2.add(btnEditUser);
		
		JButton exitBtn_1 = new JButton("Exit");
		exitBtn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exitBtn_1.setBackground(Color.GREEN);
		exitBtn_1.setBounds(320, 189, 89, 23);
		panel_1.add(exitBtn_1);
		
		JButton btnDeleteFromBasement = new JButton("Delete From Basement");
		btnDeleteFromBasement.setBackground(Color.GREEN);
		btnDeleteFromBasement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BasementEtm=dbm.ReadAllBasement(UserId);
				
				DelBasement dB= new DelBasement(Menu.this, BasementEtm);
				
				dB.setVisible(true);
			}
		});
		btnDeleteFromBasement.setBounds(239, 53, 170, 23);
		panel_1.add(btnDeleteFromBasement);
		
		JButton btnBasement = new JButton("Basement");
		btnBasement.setBackground(Color.GREEN);
		btnBasement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasementEtm=dbm.ReadAllBasement(UserId);
				BasementList bL= new BasementList(Menu.this, BasementEtm);
				bL.setVisible(true);
				
			}
		});
		btnBasement.setBounds(10, 53, 99, 23);
		panel_1.add(btnBasement);
		
		JLabel img2 = new JLabel("");
		img2.setIcon(new ImageIcon(Menu.class.getResource("/Food2.gif")));
		
		img2.setBounds(10, 136, 289, 76);
		panel_1.add(img2);
		
		JButton btnInsertToBasement = new JButton("Edit");
		btnInsertToBasement.setBackground(Color.GREEN);
		btnInsertToBasement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasementEtm=dbm.ReadAllBasement(UserId);
				EditBasement eB= new EditBasement(Menu.this, BasementEtm);
				eB.setVisible(true);
			}
		});
		btnInsertToBasement.setBounds(128, 53, 89, 23);
		panel_1.add(btnInsertToBasement);
		
		JButton btnNewButton = new JButton("Insert");
		btnNewButton.setBackground(Color.GREEN);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//BasementEtm=dbm.ReadAllBasement(UserId);
				ProductEtm=dbm.ReadAllProduct();
				InsertBasement iB= new InsertBasement(Menu.this, ProductEtm, UserId);
				iB.setVisible(true);
			}
		});
		btnNewButton.setBounds(10, 102, 89, 23);
		panel_1.add(btnNewButton);
		
		Object UserEmptmn[] = {"BOX","UserId", "Username", "Email", "Password", "Birthday"};
		UserEtm=new EmpTM(UserEmptmn, 0);
		
		
		
		
	}
	
	public boolean IntFormatChecker(int Value) {
		boolean res=true;
		
		if(Value == (int)Value && Value>=0) {
			return res;
		}
		else {
			res=false;
			
		}
		
		
		return res;
		
	}
	
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
}
