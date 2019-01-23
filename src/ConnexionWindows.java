import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.net.InetAddress;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.*;

//Cette classe est l'interface graphique de la fen�tre de connexion qui recueille le pseudo que l'utilisateur choisi.

public class ConnexionWindows {

	private JFrame frame;
	private JTextField textField;
	private JTextPane txtpnNetchatV;
	private Controler c;
	private JCheckBox RemoteClientCheckBox;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public ConnexionWindows(Controler c) {
		this.c = c;
		this.initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					c.mainUser=new User(textField.getText().substring(0, 1).toUpperCase().concat(textField.getText().substring(1)), InetAddress.getByName("localhost"));
					c.presence_server = RemoteClientCheckBox.isSelected();
				}
				catch(Exception e) {}
				c.ready=false;
				frame.dispose();
			}
		});
		btnNewButton.setBounds(173, 180, 105, 30);
		frame.getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(95, 98, 251, 41);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JTextArea txtrPseudo = new JTextArea();
		txtrPseudo.setBackground(SystemColor.control);
		txtrPseudo.setEditable(false);
		txtrPseudo.setText("Pseudo :");
		txtrPseudo.setBounds(95, 65, 60, 22);
		frame.getContentPane().add(txtrPseudo);
		
		txtpnNetchatV = new JTextPane();
		txtpnNetchatV.setFont(new Font("Tahoma", Font.BOLD, 19));
		txtpnNetchatV.setBackground(SystemColor.control);
		txtpnNetchatV.setText("NETCHAT v1.0");
		txtpnNetchatV.setEditable(false);
		txtpnNetchatV.setBounds(95, 11, 245, 43);
		frame.getContentPane().add(txtpnNetchatV);
		
		RemoteClientCheckBox = new JCheckBox("Use presence server", false);
		RemoteClientCheckBox.setBounds(95, 140, 200, 20);
		frame.getContentPane().add(RemoteClientCheckBox);

		//RemoteClientCheckBox.setSelected(false);
		/*RemoteClientCheckBox.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e)
			{
				
		        JCheckBox cbLog = (JCheckBox) e.getSource();
			    if(cbLog.isSelected())
			    {

			    	WarningWindow.ShowWarn("Tu as choisi le remote client !");
			    }
			    else 
			    {
			    	WarningWindow.ShowWarn("Enlevé !");

			    }
			}
		});*/
		
		frame.setVisible(true);
	}
	


	
}
