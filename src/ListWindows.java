import java.awt.EventQueue;
import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

// Cette classe est l'interface graphique qui affiche les Users connect�s, le pseudo choisi ( et que l'on peut modifier ).

public class ListWindows {

	private static JTextField pseudo;
	private static JFrame frame;
	private static ArrayList<JButton> button_chat;
	private static JButton button_exit;
	private static JPanel panel;
	private static JButton changeButton;
	private static JButton decoButton;
	private Controler c;
	
	/**
	 * Create the application.
	 */
	public ListWindows() {
		this.c = c;
		initialize(c);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public static void initialize(Controler c) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 294, 422);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		pseudo = new JTextField();
		pseudo.setBounds(22, 24, 86, 28);
		frame.getContentPane().add(pseudo);
		pseudo.setColumns(10);
		
		changeButton = new JButton("CHANGE");
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.changeUsername(pseudo.getText());
			}
		});
		changeButton.setBounds(134, 24, 129, 28);
		frame.getContentPane().add(changeButton);
		
		decoButton = new JButton("INVISIBLE");
		decoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.deconnect();
			}
		});
		decoButton.setBounds(134, 60, 129, 28);
		frame.getContentPane().add(decoButton);
		
		button_chat = new ArrayList <JButton>();
		
		panel = new JPanel();
		panel.setBounds(22, 77, 241, 295);
		frame.getContentPane().add(panel);
		GridBagLayout gridLayout = new GridBagLayout();
		panel.setLayout(gridLayout);
		frame.setVisible(true);
		panel.setVisible(true);
		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(frame, 
		            "Are you sure you want to close this window?", "Close Window?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		        	c.deconnect();
		            System.exit(0);
		        }
		    }
		});
	}
	
	
	public static void PrintConnectedUser(Controler c)
	{
		//textArea_1.setText("");
        //frame.setContentPane(panel);
		GridBagConstraints fieldConstraints = new GridBagConstraints();
		
		int i = 1;
		for(User u :c.userList.connectedUser)
		{
			System.out.println("Button "+i);
			JButton current_button = new JButton();
			button_chat.add(current_button);
			current_button.setText(u.getPseudo());			
			
			fieldConstraints.gridx=1;
			fieldConstraints.gridy=i-1;
			fieldConstraints.weightx=1;
			fieldConstraints.fill=GridBagConstraints.BOTH;

			panel.add(current_button, fieldConstraints);
			current_button.setVisible(true);
			current_button.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					new Client(u, c);
				}
			});
			i++;
			frame.setVisible(true);
			panel.setVisible(true);
			//pseudo.setVisible(true);
			//changeButton.setVisible(true);
			//textArea_1.append(u.getPseudo() + "\n");
		}
	}
	
	public static void RemoveConnectedUser(User u)
	{
		for(JButton jb : button_chat)
		{
			System.out.println(jb.getText());
			if(jb.getText().equals(u.getPseudo()))
			{
				jb.remove(frame);
				jb.setVisible(false);
			}
		}
		
	}
	
	public static void PrintMainUser(User u)
	{
		pseudo.setText(u.getPseudo());
	}
}
