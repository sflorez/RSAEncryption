/*
 * The View leverages a JTabbedPane to present the user with a clean organized window for sending and
 * receiving secure messages. Tab one is used to enter a message to encrypt, and the second tab allows
 * the user to decrypt their message.
 */
package view;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import controller.RSAController;

public class RSAUserInterface extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JTabbedPane myViewPane;
	private RSAController myController;
	private Vector<byte[]> myEncryption;
	private JButton myDecryptMessageBtn, myEncryptMessageBtn;
	private JTextArea myPublicKeyDisplay, myPrivateKeyDisplay, myEncryptInputBox, myEncryptOutputBox, myDecryptInputBox, 
	  				  myDecryptOutputBox;

	public RSAUserInterface(RSAController controller) 
	{
		myController = controller;
		myEncryption = new Vector<byte[]>();
		displayUI();
	}
	
	/*
	 * Assemble the user interface, and set it visible for the user.
	 */
	private void displayUI()
	{
		setTitle("Send Secure Data - RSA Encryption");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createTextLabels();
		
		/*
		 * Create first panel, which is the panel that allows the user to enter in a message to encrypt.
		 */
		JPanel encryptPanel = new JPanel();
		encryptPanel.setLayout(new BoxLayout(encryptPanel, BoxLayout.Y_AXIS));

		myEncryptMessageBtn = new JButton("Encrypt Message");
		myEncryptMessageBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		myEncryptMessageBtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent event) 
			{
				String message = getUserMessage();
				
				if(message != "")
				{
					message = getUserMessage();
					myEncryption = myController.encryptMessage(message);
					setEncryptedMessageDisplay();
				}
			 }
		});
		
		JScrollPane scrollPane1 = new JScrollPane(myEncryptInputBox);
		JScrollPane scrollPane2 = new JScrollPane(myEncryptOutputBox);
		JScrollPane scrollPane3 = new JScrollPane(myPublicKeyDisplay);
		encryptPanel.add(scrollPane1);
		encryptPanel.add(scrollPane3);
		encryptPanel.add(myEncryptMessageBtn);
		encryptPanel.add(scrollPane2);
		
		
		/*
		 * Create second panel, which allows the user to decrypt their messages.
		 */
		JPanel decryptPanel = new JPanel();
		decryptPanel.setLayout(new BoxLayout(decryptPanel, BoxLayout.Y_AXIS));

		myDecryptMessageBtn = new JButton("Decrypt Message");
		myDecryptMessageBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		myDecryptMessageBtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent event) 
			{
				 String decryptedData = myController.decryptMessage(myEncryption);
				 setDecryptedMessageDisplay(decryptedData);
			}
		});
		
		JScrollPane scrollPane4 = new JScrollPane(myDecryptInputBox);
		JScrollPane scrollPane5 = new JScrollPane(myPrivateKeyDisplay);
		JScrollPane scrollPane6 = new JScrollPane(myDecryptOutputBox);
		decryptPanel.add(scrollPane4);
		decryptPanel.add(myDecryptMessageBtn);
		decryptPanel.add(scrollPane5);
		decryptPanel.add(scrollPane6);

		
		/*
		 * Add all the panels to the tab pane for easy access and organization
		 */
		myViewPane = new JTabbedPane();
		myViewPane.addTab("RSA Encrypt", encryptPanel);
		myViewPane.addTab("RSA Decrypt", decryptPanel);
		add(myViewPane);

		pack();
		setVisible(true);
	}

	/*
	 * Function used to avoid duplicate code when creating all the JTextArea's needed for the View.
	 */
	private void createTextLabels() 
	{
		/*
		 * To-Do's: Customize look and feel of each JTextArea for better 
		 * overall user experience.
		 */
		myPublicKeyDisplay = createTextBox("Public Key");
		myPublicKeyDisplay.setRows(5);
		myEncryptInputBox = createTextBox("Enter a Message");
		myEncryptOutputBox = createTextBox("Encrypted Message Display");

		myPrivateKeyDisplay = createTextBox("Private Key");
		myPrivateKeyDisplay.setRows(5);
		myDecryptInputBox = createTextBox("Encrypted Message");
		myDecryptOutputBox = createTextBox("Decrypted Message Display");
		
		/*
		 * Do not let user edit fields that should not be altered, so 
		 * disable option to edit text.
		 */
		myPublicKeyDisplay.setEditable(false);
		myPrivateKeyDisplay.setEditable(false);
		myEncryptOutputBox.setEditable(false);
		myDecryptInputBox.setEditable(false);
	}
	
	/*
	 * Utility method used to handle the various text area creations. This method
	 * is used mainly to avoid duplicate code.
	 */
	private JTextArea createTextBox(String title) 
	{
		JTextArea textArea = new JTextArea(10, 62);
		textArea.setBorder(BorderFactory.createTitledBorder(title));
		textArea.setLineWrap(true);
		return textArea;
	}
	
	/*
	 * Below are all the accessor/mutator functions for displaying text to the UI, and for grabbing
	 * any text that the user has entered.
	 */
	public String getUserMessage()
	{
		String userMessage = "";
		userMessage = myEncryptInputBox.getText();
		return userMessage;
	}
	
	public void setPublicKeyDisplay(String currentPublicKey)
	{
		myPublicKeyDisplay.setText(currentPublicKey);
		validate();
	}
	
	public void setPrivateKeyDisplay(String currentPrivateKey)
	{
		myPrivateKeyDisplay.setText(currentPrivateKey);
		validate();
	}
	
	public void setEncryptedMessageDisplay()
	{
		myEncryptOutputBox.setText(myEncryption.toString());
		myDecryptInputBox.setText(myEncryption.toString());
	}
	
	public void setDecryptedMessageDisplay(String decryptedData)
	{
		myDecryptOutputBox.setText(decryptedData);
	}
}