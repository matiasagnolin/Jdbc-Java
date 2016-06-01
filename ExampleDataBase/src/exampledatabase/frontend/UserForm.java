package exampledatabase.frontend;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import exampledatabase.model.User;
import exampledatabase.service.UserService;

public class UserForm extends JFrame{
	
	JPanel topPanel;
	JPanel bottomPanel;
	JTable userTable;
	JButton btnAdd;
	
	UserService userService;
	
	public UserForm(){
		setTitle("Users");
		setSize(500, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		this.topPanel = new JPanel(new GridLayout(1, 0));
		bottomPanel = new JPanel(new FlowLayout());
		
		getContentPane().add(topPanel);
		getContentPane().add(bottomPanel);
		
		userService = new UserService();
		try {
			List<User> users = userService.getUsers();
			
			DefaultTableModel tModel = new DefaultTableModel();
			Object[] columnames = new Object[3];
			columnames[0] = "Id";
			columnames[1] = "Name";
			columnames[2] = "Age";
			
			tModel.setColumnIdentifiers(columnames);
			
			Object[] data = new Object[3];
			for(User us : users){
				data[0] = us.getId();
				data[1] = us.getName();
				data[2] = us.getAge();
				tModel.addRow(data);
			}
			
			userTable = new JTable(tModel);
			
			JScrollPane scrollPane = new JScrollPane(userTable);
			topPanel.add(scrollPane);
			
			btnAdd = new JButton("Agregar Usuario");
			btnAdd.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
//						userService.addUser("Maximiliano", 30);
						userService.addWithStoredProcedure("juan", 33);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			bottomPanel.add(btnAdd);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
