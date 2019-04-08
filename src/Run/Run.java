package Run;


import javax.swing.UIManager;

import GUI.login;

public class Run {

	public static void main(String[] args) throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
			e.printStackTrace();
			}
		User user=new User();
		new login(user);
		
	}

}
