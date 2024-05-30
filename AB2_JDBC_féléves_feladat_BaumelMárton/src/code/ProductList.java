package code;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class ProductList extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private EmpTM etm;
	private JTable table;
	private static DbMethods dbm= new DbMethods();

	
	public ProductList(JFrame f, EmpTM betm) {
		super(f, "Product List", true);
		dbm.Connect();
		etm = betm;
		setBounds(100, 100, 1000, 300);
		getContentPane().setLayout(null);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setBounds(10, 227, 89, 23);
		getContentPane().add(btnExit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 970, 202);
		getContentPane().add(scrollPane);
		
		table = new JTable(etm);
		scrollPane.setViewportView(table);
		
		TableColumn tc = null;
		for (int i = 0; i < 6; i++) {
		tc = table.getColumnModel().getColumn(i);
		if (i==0 || i==1 || i==5) tc.setPreferredWidth(30);
		else if (i==2) tc.setPreferredWidth(400);
		else {tc.setPreferredWidth(100);}
		}
		
		table.setAutoCreateRowSorter(true);
		TableRowSorter<EmpTM> trs =
		(TableRowSorter<EmpTM>)table.getRowSorter();
		trs.setSortable(0, false);

	}

}
