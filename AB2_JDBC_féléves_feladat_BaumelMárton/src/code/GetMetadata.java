package code;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class GetMetadata extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField fileName;
	private DbMethods dbm= new DbMethods();
	private enum TableType{
		UserTable,
		Product,
		BasementTable
	}
	private String selected="UserTable";

	

	/**
	 * Create the dialog.
	 */
	
	public GetMetadata(JFrame f) {
		super(f, "Metadata", true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("File Name");
		lblNewLabel.setBounds(10, 23, 86, 26);
		contentPanel.add(lblNewLabel);
		
		fileName = new JTextField();
		fileName.setBounds(106, 26, 126, 20);
		contentPanel.add(fileName);
		fileName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Table Name:");
		lblNewLabel_1.setBounds(10, 72, 74, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel(".txt");
		lblNewLabel_2.setBounds(242, 29, 46, 14);
		contentPanel.add(lblNewLabel_2);
		
		JComboBox comboBox = new JComboBox(TableType.values());
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected= comboBox.getSelectedItem().toString();
			}
		});
		comboBox.setBounds(106, 68, 126, 22);
		contentPanel.add(comboBox);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dbm.Connect();
						String ChoosenType= selected.toString();
						
						if(StringFormat(RTF(fileName))  ) {
							
								
							dbm.GetMetaaData(RTF(fileName), ChoosenType );
							
						}
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}

	public boolean StringFormat(String str){
		boolean res=true;
		if(str != null && !str.trim().isEmpty()) { 
			return res;
			}
		else {
			res=false;
		}
		dbm.SM("Wrong name for the file");
		return res;
	}
}
