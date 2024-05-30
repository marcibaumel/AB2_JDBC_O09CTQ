package code;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class DelBasement extends JDialog {

	private JTable table;
	private EmpTM etm;
	private DbMethods dbm = new DbMethods();
	
	
	public DelBasement(JFrame f, EmpTM betm) {
		super(f, "Basement Delete", true);
		getContentPane().setBackground(Color.ORANGE);
		dbm.Connect();
		etm = betm;
		setBounds(100, 100, 450, 300);
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
		scrollPane.setBounds(10, 11, 950, 202);
		getContentPane().add(scrollPane);
		
		table = new JTable(etm);
		scrollPane.setViewportView(table);
		
		TableColumn tc = null;
		for (int i = 0; i < 6; i++) {
		tc = table.getColumnModel().getColumn(i);
		if (i==0 || i==1 || i==5) tc.setPreferredWidth(30);
		else if (i==4) tc.setPreferredWidth(150);
		else {tc.setPreferredWidth(100);}
		}
		
		table.setAutoCreateRowSorter(true);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(int i=etm.getRowCount()-1; i>=0; i--) {
					if((Boolean)etm.getValueAt(i, 0)) {
						String Id=RTM(i,1);
						dbm.deleteBasementById(Id);
						etm.removeRow(i);
					}
				}
			}
		});
		btnDelete.setBounds(109, 227, 89, 23);
		getContentPane().add(btnDelete);
		TableRowSorter<EmpTM> trs =
		(TableRowSorter<EmpTM>)table.getRowSorter();
		trs.setSortable(0, false);

	}
	
	public String RTM(int row, int col) {
		return etm.getValueAt(row, col).toString();
	}


}
