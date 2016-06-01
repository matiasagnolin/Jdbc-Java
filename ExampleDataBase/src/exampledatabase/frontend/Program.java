package exampledatabase.frontend;

import javax.swing.SwingUtilities;

import com.mysql.jdbc.StringUtils;

public class Program {
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				UserForm form = new UserForm();
				form.setVisible(true);
			}
		});
	}
}
