import java.awt.*;
import java.sql.*;

import java.awt.event.*;
import javax.swing.*;
import java.lang.Object;
import java.io.*;

public class ChatWindow implements ActionListener  {

    JPanel displayPanel, sendPanel, mainPanel;
    JButton sendButton;
    JTextArea messageBox;
    JTextArea showMessage;
    JScrollPane sp;
    JFrame CWFrame;

    final static String LOOKANDFEEL = "GTK+";
    NetworkInterface nw;
    
    public ChatWindow(NetworkInterface nw)
    {
    	mainPanel = new JPanel();
       /* try {
			displayPanel = new JPanel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        sendPanel = new JPanel();
        
        
        addWidgets();
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        
        mainPanel.add(sp);
        mainPanel.add(sendPanel);
        

        
        this.nw = nw;
        this.showHistory();
        
    }
    


    
    private void addWidgets()
    {
    	
    	showMessage = new JTextArea("",30,70);
    	showMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        showMessage.setEditable(false);  
        sp = new JScrollPane(showMessage);
        
        messageBox = new JTextArea(7,25);
        messageBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        
        //displayPanel.add(showMessage, BorderLayout.CENTER);
        
        sendPanel.add(messageBox, BorderLayout.CENTER);
        sendPanel.add(sendButton, BorderLayout.CENTER);        
    }
    
    
    public void actionPerformed(ActionEvent event)
    {
    		Message a = new Message(messageBox.getText(),nw.userDest,nw.c.mainUser);
    		showMessage.append(a + "\n");
    		messageBox.setText("");
    		System.out.println(a);
    		a.SendMessage(nw.out2);
        	sp.getVerticalScrollBar().setValue( sp.getVerticalScrollBar().getMaximum());


    		
    		
    }
    
	private static void initLookAndFeel() {
	        
	        // Swing allows you to specify which look and feel your program uses--Java,
	        // GTK+, Windows, and so on as shown below.
	        String lookAndFeel = null;
	        
	        if (LOOKANDFEEL != null) {
	            if (LOOKANDFEEL.equals("Metal")) {
	                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
	            } else if (LOOKANDFEEL.equals("System")) {
	                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
	            } else if (LOOKANDFEEL.equals("Motif")) {
	                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
	            } else if (LOOKANDFEEL.equals("GTK+")) { //new in 1.4.2
	                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
	            } else {
	                System.err.println("Unexpected value of LOOKANDFEEL specified: "
	                        + LOOKANDFEEL);
	                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
	            }
	            
	            try {
	                UIManager.setLookAndFeel(lookAndFeel);
	            } catch (ClassNotFoundException e) {
	                System.err.println("Couldn't find class for specified look and feel:"
	                        + lookAndFeel);
	                System.err.println("Did you include the L&F library in the class path?");
	                System.err.println("Using the default look and feel.");
	            } catch (UnsupportedLookAndFeelException e) {
	                System.err.println("Can't use the specified look and feel ("
	                        + lookAndFeel
	                        + ") on this platform.");
	                System.err.println("Using the default look and feel.");
	            } catch (Exception e) {
	                System.err.println("Couldn't get specified look and feel ("
	                        + lookAndFeel
	                        + "), for some reason.");
	                System.err.println("Using the default look and feel.");
	                e.printStackTrace();
	            }
	        }
	    }
    
    
    public void createAndShowGUI()
    {
    	initLookAndFeel();
    	JFrame.setDefaultLookAndFeelDecorated(true);
    	CWFrame = new JFrame("Chat Window");
    	CWFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	CWFrame.setContentPane(this.mainPanel);

        //Display the window.
    	CWFrame.pack();
    	CWFrame.setVisible(true);   	
    	
    	CWFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            		Message a = new Message("Deco",nw.c.mainUser,nw.c.mainUser);
            		a.setDeco();
            		a.SendMessage(nw.out2);
                }
    	});
        
    }
     
    public void  writeReceivedMessage(Message message)
    {
    	showMessage.append(message + "\n");
    	sp.getVerticalScrollBar().setValue( sp.getVerticalScrollBar().getMaximum());
 
    }
    
    public void  writeDecoMessage(Message message)
    {
    	showMessage.append("System : " + message.getUserSrc().getPseudo() + " s'est déconnecté \n");
    	sp.getVerticalScrollBar().setValue( sp.getVerticalScrollBar().getMaximum());
    	
    }
    
    public void showHistory()
    {
    	String res = Database.getHistory(nw.userDest);
    	try {
    		showMessage.append(res);
    		showMessage.append("\n ------------------------------------------------------- \n End History\n -------------------------------------------------------\n\n");
    	}
    	catch (Exception e )
    	{
    		System.out.println(e.getMessage());
    	}
    	
    }
    
    public void closeWindow()
    {
    	CWFrame.dispatchEvent(new WindowEvent(CWFrame, WindowEvent.WINDOW_CLOSING));
    }

}