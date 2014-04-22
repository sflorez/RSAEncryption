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
		
		encryptPanel.add(myEncryptInputBox);
		encryptPanel.add(myPublicKeyDisplay);
		encryptPanel.add(myEncryptMessageBtn);
		encryptPanel.add(myEncryptOutputBox);
		
		
		/*
		 * Create second panel, which allows the user to view the encrypted/decrypted messages.
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
		
		decryptPanel.add(myDecryptInputBox);
		decryptPanel.add(myDecryptMessageBtn);
		decryptPanel.add(myPrivateKeyDisplay);
		decryptPanel.add(myDecryptOutputBox);

		
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

	private void createTextLabels() 
	{
		/*
		 * To-Do's: Customize look and feel of each textbox for better 
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
	
	public String getUserMessage()
	{
		String userMessage = "";
		userMessage = myEncryptInputBox.getText();
		return userMessage;
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
}