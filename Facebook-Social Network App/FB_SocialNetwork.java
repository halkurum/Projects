package assignment2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


class LoginPanel extends JPanel{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JTextField username;
	JPasswordField password;
	JButton login;
	JButton signup;
	private String UserName;

	public void disablePanel(){
		if(this.getComponentCount()==0)
			return ;
		Component[] comps= new Component[this.getComponentCount()];
		comps = this.getComponents();
		for(int i=0;i<comps.length;i++){
				comps[i].disable();
		}
	}

	public void enablePanel(){
		if(this.getComponentCount()==0)
			return ;
		Component[] comps= new Component[this.getComponentCount()];
		comps = this.getComponents();
		for(int i=0;i<comps.length;i++){
				comps[i].enable();
		}
	}

	LoginPanel(){
		this.setBounds(810, 30, 250, 100);
		this.setLayout(new GridLayout(3,2));
		this.add(new Label("UserName: "));
		username = new JTextField();
        this.add(username);
		this.add(new Label("Password: "));
		password = new JPasswordField();
        this.add(password);
        login = new JButton("Login");
        signup = new JButton("Signup");
        this.add(login);
        this.add(signup);
	}
	public String getUserName(){
		return UserName;
	}
	public void setUserName(String user){
		UserName=user;
	}
}

class SignupPanel extends JPanel{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JButton login;
	JButton signup;
	JPasswordField password;
	JPasswordField password2;

	JTextField country;
	JTextField state;
	JTextField city;
	JTextField email;
	JTextField birthday;
	JTextField fname;
	JTextField lname;
	JTextField str_no;
	JTextField str_address;
	JTextField zip;

	public void disablePanel(){
		if(this.getComponentCount()==0)
			return ;
		Component[] comps= new Component[this.getComponentCount()];
		comps = this.getComponents();
		for(int i=0;i<comps.length;i++){
				comps[i].disable();
		}
		disableButton();
	}

	public void enablePanel(){
		if(this.getComponentCount()==0)
			return ;
		Component[] comps= new Component[this.getComponentCount()];
		comps = this.getComponents();
		for(int i=0;i<comps.length;i++){
				comps[i].enable();
		}
		enableButton();
	}

	SignupPanel(){
		this.setBounds(810, 150, 250, 300);
		this.setLayout(new GridLayout(11,2));
		this.add(new Label("Email: "));
		email = new JTextField();
        this.add(email);
		this.add(new Label("Password: "));
		password = new JPasswordField();
        this.add(password);
		this.add(new Label("ReEnter Password: "));
		password2 = new JPasswordField();
        this.add(password2);
		this.add(new Label("First Name: "));
		fname = new JTextField();
        this.add(fname);
		this.add(new Label("Last Name: "));
		lname = new JTextField();
        this.add(lname);
		this.add(new Label("City: "));
		city = new JTextField();
        this.add(city);
		this.add(new Label("Birthday : "));
		birthday = new JTextField();
        this.add(birthday);
		this.add(new Label("strNo : "));
		str_no = new JTextField();
        this.add(str_no);
		this.add(new Label("strAdress : "));
		str_address = new JTextField();
        this.add(str_address);
        this.add(new Label("Zip : "));
		zip = new JTextField();
        this.add(zip);
        this.add(new Label(""));
        signup = new JButton("signup");
        this.add(signup);
        disablePanel();
	}

	public void clearAllContents()
	{
		email.setText("");
		password.setText("");
		password2.setText("");
		fname.setText("");
		lname.setText("");
		city.setText("");
		birthday.setText("");
		str_no.setText("");
		str_address.setText("");
		zip.setText("");
	}
	
	public void disableButton(){
		signup.setEnabled(false);
	}
	public void enableButton(){
		signup.setEnabled(true);
	}
}

class SqlPanel extends JPanel{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JTextArea SQLArea = null;
	JScrollPane scrollPane = null;
	JLabel showLabel;
	SqlPanel(){
		setInputArea();
	}

	public void disablePanel(){
		if(this.getComponentCount()==0)
			return ;
		Component[] comps= new Component[this.getComponentCount()];
		comps = this.getComponents();
		for(int i=0;i<comps.length;i++){
				comps[i].disable();
		}
	}

	public void enablePanel(){
		if(this.getComponentCount()==0)
			return ;
		Component[] comps= new Component[this.getComponentCount()];
		comps = this.getComponents();
		for(int i=0;i<comps.length;i++){
				comps[i].enable();
		}
	}

	private void setInputArea(){
		setBounds(0, 495,810, 150);
		SQLArea = new JTextArea(6,68);
		SQLArea.setLineWrap(true);
		scrollPane = new JScrollPane(SQLArea);
		this.add(scrollPane);
	}
}

class Frame0 extends JFrame{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JTextField txtfield;
	JButton btn1;
	String lbltext;
	JLabel label;
	String FriendType[] = { "Reqular Friend", "Close Friend", "Family" };
	JComboBox combo;

	Frame0(String text,int mode){
		this.lbltext = text;
		 this.setSize(300, 120);
		 this.setResizable(false);
		setLayout(new FlowLayout());
		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((width-this.getWidth())/2,(height-this.getHeight())/2);
		label = new JLabel(text);
		add(label);
		txtfield = new JTextField(10);
        add(txtfield);
        combo= new JComboBox(FriendType);
        if(mode==1)
        	add(combo);
        btn1 = new JButton("OK");
        add(btn1);

	}
}

class Frame1 extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JTextField txtfield;
	JButton btn1;
	JButton btn2;
	String lbltext0;
	String lbltext1;
	JLabel lbl0;
	JLabel lbl1;
	JTextArea textArea = null;

	Frame1(String text0, String text1) {
		this.lbltext0 = text0;
		this.lbltext1 = text1;
		this.setResizable(false);
		setLayout(null);
		 this.setSize(300, 256);
		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((width-this.getWidth())/2,(height-this.getHeight())/2);
		lbl0 = new JLabel(text0);
		add(lbl0);
		lbl0.setBounds(10, 10, 70, 30);

		txtfield = new JTextField(10);
		add(txtfield);
		txtfield.setBounds(85, 10, 150, 30);

		lbl1 = new JLabel(text1);
		add(lbl1);
		lbl1.setBounds(10, 45, 70, 30);

		textArea = new JTextArea(8, 10);
		textArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		this.add(scrollPane);
		scrollPane.setBounds(85,55,180,90);

		btn1 = new JButton("OK");
		btn1.setBounds(90,165,90, 30);
		add(btn1);

	}
}


class Frame2 extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JTextField txtfield;
	JButton btn1;
	JButton btn2;
	String lbltext0;
	JLabel lbl0;

	Frame2(String text0) {
		this.setResizable(false);
		this.lbltext0 = text0;
        this.setSize(300, 150);
        this.setResizable(false);
		setLayout(null);

		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((width-this.getWidth())/2,(height-this.getHeight())/2);
		lbl0 = new JLabel(text0);
		add(lbl0);
		lbl0.setBounds(10, 10, 70, 30);

		txtfield = new JTextField(10);
		add(txtfield);
		txtfield.setBounds(85, 10, 100, 30);

		btn1 = new JButton("Decline");
		btn2 = new JButton("Accept ALL");

		btn1.setBounds(190,10,90, 30);
		btn2.setBounds(70,60,150, 30);
		add(btn1);
		add(btn2);

	}
}


class Frame3 extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JTextField []txtfield;
	JButton btn1;
	JLabel []lbl;
	Frame3(String text0) {
		this.setResizable(false);
		this.setTitle(text0);
        this.setSize(1000, 70);
        this.setResizable(false);
		setLayout(new GridLayout(1,9));

		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((width-this.getWidth())/2,(height-this.getHeight())/2);
		lbl = new JLabel[4];
		for(int i=0;i<4;i++)
			lbl[i] = new JLabel();
		lbl[0].setText("topleft x:");
		lbl[1].setText("topleft y:");
		lbl[2].setText("bottomright x:");
		lbl[3].setText("bottomright y:");

		txtfield = new JTextField[4];
		for(int i=0;i<4;i++)
			txtfield[i] = new JTextField();

		btn1 = new JButton("search");
		for(int i=0;i<4;i++)
		{
			add(lbl[i]);
			add(txtfield[i]);
		}
		add(btn1);
	}
}


class PostandSearchPanel extends JPanel{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JTextArea textArea = null;
	JScrollPane scrollPane = null;
	
	String PrivacyType[] = { "All: 2", "FRIENDS: 1", "SELF: 0" };
	JComboBox PrivacyCombo;
	JLabel PrivacyLabel;
	
	JTextField text;
	JLabel showLabel;
	JButton []buttons;

	public void disableButton(){
		for(int i=0;i<buttons.length;i++)
			buttons[i].setEnabled(false);
	}

	public void enableButton(){
		for(int i=0;i<buttons.length;i++)
			buttons[i].setEnabled(true);
	}

	PostandSearchPanel(){
		setLayout(null);
		this.setBounds(0, 10,820, 120);
        setInputArea();

		buttons = new JButton[4];
		buttons[0]= new JButton("Post");
		buttons[1]= new JButton("Search For Friend");
		buttons[2]= new JButton("Clear ResultBox");
		buttons[3]= new JButton("Clear SQLBox");
		PrivacyCombo= new JComboBox(PrivacyType);
		PrivacyLabel = new JLabel("Privacy Level");
		
		text  = new JTextField(15);
		text.setLocation(10,3);
		text.setText("");
		text.setBounds(480, 30, 150, 25);
		PrivacyLabel.setBounds(380,50,100,25);
		PrivacyCombo.setBounds(380, 80,80, 25);
		buttons[0].setBounds(380,20,60,25);
		buttons[1].setBounds(640,30,130,25);
		buttons[2].setBounds(490,80,130,25);
		buttons[3].setBounds(640,80,130,25);
		
		this.add(buttons[0]);
		this.add(PrivacyLabel);
		this.add(PrivacyCombo);
		this.add(text);
		this.add(buttons[1]);
		this.add(buttons[2]);
		this.add(buttons[3]);
	}
	public void disablePanel(){
		if(this.getComponentCount()==0)
			return ;
		Component[] comps= new Component[this.getComponentCount()];
		comps = this.getComponents();
		for(int i=0;i<comps.length;i++){
				comps[i].disable();
		}
		disableButton();
		PrivacyCombo.setEnabled(false);
	}

	public void clearPanel(){
		text.setText("");
	    textArea.setText("");
	}

	public  void enablePanel(){
		if(this.getComponentCount()==0)
			return ;
		Component[] comps= new Component[this.getComponentCount()];
		comps = this.getComponents();
		for(int i=0;i<comps.length;i++){
				comps[i].enable();
		}
		enableButton();
		PrivacyCombo.setEnabled(true);
	}

	public void setInputArea(){
		textArea = new JTextArea(4,30);
		textArea.setLineWrap(true);
		textArea.setBounds(10, 15, 360, 90);
		this.add(textArea);
	}
 }

class ResultPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea resultArea = null;
	JScrollPane scrollPane = null;
	ResultPanel(){
		setResultArea();
	}

	public void setResultArea(){
		resultArea = new JTextArea(10,30);
		resultArea.setLineWrap(true);
		resultArea.setBounds(10, 140,750, 250);

		scrollPane = new JScrollPane(resultArea);
		add(scrollPane);

	}
}

class ButtonPanel extends JPanel{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JTextArea textArea = null;
	JScrollPane scrollPane = null;
	JLabel showLabel;
	JButton []buttons;

	ButtonPanel(){
	  this.setBounds(10, 415, 770, 40);
	 	buttons = new JButton[8];
		this.setLayout(new GridLayout(2,4));
        for(int i=0;i<8;i++){
        	buttons[i]= new JButton();
        	this.add(buttons[i]);
        }
        buttons[0].setText("Add Friend");
        buttons[1].setText("List all posts");
        buttons[2].setText("List all comments on a post");
        buttons[3].setText("Comment on A post");
        buttons[4].setText("List all events");
        buttons[5].setText("Friend request");   
        buttons[6].setText("Find nearest friend");
        buttons[7].setText("Range query");

	}

	public void disableButton(){
		for(int i=0;i<buttons.length;i++)
			buttons[i].setEnabled(false);
	}

	public void enableButton(){
		for(int i=0;i<buttons.length;i++)
			buttons[i].setEnabled(true);
	}

	public void disablePanel(){
		if(this.getComponentCount()==0)
			return ;
		Component[] comps= new Component[this.getComponentCount()];
		comps = this.getComponents();
		for(int i=0;i<comps.length;i++){
				comps[i].disable();
		}
		disableButton();
	}

	public void enablePanel(){
		if(this.getComponentCount()==0)
			return ;
		Component[] comps= new Component[this.getComponentCount()];
		comps = this.getComponents();
		for(int i=0;i<comps.length;i++){
			comps[i].enable();
		}
		enableButton();
	}
}


class MainFrame extends JFrame{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JLabel LogoLabel;
	JButton notifyButton;
	LoginPanel loginPanel;
	SignupPanel signUpPanel;
	SqlPanel sqlPanel;
	ButtonPanel buttonPanel;
	PostandSearchPanel postandsearch;
	ResultPanel resultPanel;
	Connection conn=null;
	ArrayList<String> requester = new ArrayList<String>();
	ArrayList<String> Relation= new ArrayList<String>();
	int countrequest=0;
	JTextArea resultArea = null;
	JScrollPane scrollPane = null;
	int trigger = 0;
	JLabel showLabel;
	int hasRequest = 0;
	StringBuffer SQLOut = new StringBuffer ();
	MainFrame(){
		setResizable(false);
		setLayout(null);
		setSize(1100, 700);
		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((width-1100)/2,(height-700)/2);
		setTitle("This is GUI for database homework");
		setNotifyLabel();
		SetLogo();
		setLoginPanel();
		setSignupPanel();
		setSqlPanel();
		setButtonPanel();
		setPostandSearchPanel();
		setResultPanel();
		postandsearch.disablePanel();
		buttonPanel.disablePanel();
	}

	public void disableResult(){
    	resultArea.setText("");
    	resultArea.setEditable(false);
    	resultArea.setEnabled(false);
    	scrollPane.setEnabled(false);
	}

	public void setResultPanel(){
		resultArea = new JTextArea(10,30);
		resultArea.setLineWrap(true);
		scrollPane = new JScrollPane(resultArea);
		add(scrollPane);
		scrollPane.setBounds(10, 140,770, 250);
	}

	public void SetLogo(){
	Image image;
	try {
		image = ImageIO.read(new File("usc_viterbi_logo.jpg"));
		ImageIcon icon = new ImageIcon(image);
		LogoLabel = new JLabel();
		LogoLabel.setIcon(icon);
		LogoLabel.setBounds(830,480,300,150);

		add(LogoLabel);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  //this generates an image file

	}

	public void setNotifyLabel(){
		Image image;
		try {
			image = ImageIO.read(new File("notify.png"));
			ImageIcon icon = new ImageIcon(image);
			notifyButton = new JButton();
			notifyButton.setIcon(icon);
			notifyButton.setBounds(750,3,30,30);

			notifyButton.addActionListener(new ActionListener() {
	           
	            public void actionPerformed(ActionEvent e) {
	            	/*Fill this function*/
	            	/*Press this notification button, you should list all the friend request on the ResultPanel*/
	            	/*the output format is like "... wants to add you as 'friend type'." */
	            	String ResultVal;
	            	String userEmail = loginPanel.username.getText();
	            	
	            	String checkFrdReqQuery = "SELECT SENDER, FRIEND_TYPE FROM FRIEND_REQUEST WHERE RECEIVER = \'" + userEmail + "\'";
					
	            	Connection conn = null; 
	
					Statement checkFrdReqStmt = null;
					ResultSet rs = null;
					try
					{
						conn = ConnectDB.openConnection();
						checkFrdReqStmt = conn.createStatement();
						sqlPanel.SQLArea.append(checkFrdReqQuery+"\n\n");
						rs = checkFrdReqStmt.executeQuery(checkFrdReqQuery);
						
						ResultVal = "You have the following friend Requests \n\n";
						boolean hasRequest = false;
						while(rs.next())
						{
							hasRequest = true;
							ResultVal += rs.getString("SENDER") + " wants to add you as ";
							ResultVal += rs.getString("FRIEND_TYPE") + "\n";
							resultArea.append(ResultVal);
							
							ResultVal = "";
						}
						if(hasRequest)
							resultArea.append("---------------------------------------------------\n");
					}
					catch(SQLException ex)
    				{
    					ex.printStackTrace();
    				}
    				catch(Exception ex1)
    				{
    				      ex1.printStackTrace();
    				}
    				finally
    				{
    				      try
    				      {
    				    	  if(checkFrdReqStmt != null)
    				    		  checkFrdReqStmt.close();
    				    	    	  
    				    	  if(conn != null)
    				            conn.close();
    				      }
    				      catch(SQLException se)
    				      {
    				    	  se.printStackTrace();
    				      }
    				 }
	            }
	        });

			add(notifyButton);
			notifyButton.setVisible(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  //this generates an image file


	}

	public void setButtonPanel(){
		buttonPanel = new ButtonPanel();
		this.add(buttonPanel);

		buttonPanel.buttons[0].addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
            	final Frame0 frame=new Frame0("User Email",1);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);

                frame.btn1.addActionListener(new ActionListener() {
                    
                    public void actionPerformed(ActionEvent e) {
                    	/*Fill this function*/
    	            	/*Press this add friend button, input user Email information, press OK, you should be able to send friend request*/
    	            	/*After press ok, you should also pop up a standard dialog box to show the request send status <succeed or failed>*/
                    	String friendEmail = frame.txtfield.getText();
                    	String friendType = (String)frame.combo.getSelectedItem();
                    	String userEmail = loginPanel.username.getText();
                    	
                    	friendEmail = friendEmail.trim();
                    	
                    	if(friendEmail.isEmpty())
                    	{
                    		JOptionPane.showMessageDialog(null, "Friend Email cannot be empty");
                    		return;
                    	}
                    	if(friendEmail.equals(userEmail))
                    	{
                    		JOptionPane.showMessageDialog(null, "You cannot add yourself as your friend :P");
                    		return;
                    	}
                    	frame.dispose();
                    	String checkEmailInMember = "SELECT M.EMAIL FROM MEMBER M WHERE M.EMAIL = \'" + friendEmail + "\'";
                    	String checkEmailInFriend1 = "SELECT F.USER1 FROM FRIENDSHIP F WHERE (F.USER1 = \'" + userEmail + "\' AND F.USER2 = \'" + friendEmail;
                    	String checkEmailInFriend2 = "\') OR (F.USER2 = \'" + userEmail + "\' AND F.USER1 = \'" + friendEmail + "\')";
                    	String checkEmailInFriend = checkEmailInFriend1 + checkEmailInFriend2;
                    	
                    	String friendRequest1 = "SELECT F.RECEIVER, F.FRIEND_TYPE FROM FRIEND_REQUEST F WHERE (F.SENDER = \'" + userEmail + "\'And F.RECEIVER = \'" + friendEmail;
                    	String friendRequest2 = "\') OR (F.RECEIVER = \'" + userEmail + "\' AND F.SENDER = \'"+ friendEmail + "\')";
                    	String friendRequest = friendRequest1 + friendRequest2;
                    	
                    	String insertQuery = "INSERT INTO \"FRIEND_REQUEST\" (SENDER, RECEIVER, FRIEND_TYPE) VALUES (\'" + userEmail + "\', \'" + friendEmail + "\', \'" +friendType + "\')";
                    	String updateQuery = "UPDATE \"FRIEND_REQUEST\" SET FRIEND_TYPE = \'" + friendType + "\' WHERE SENDER = \'" + userEmail + "\' AND RECEIVER = \'" + friendEmail + "\'";
          
                    	Connection conn = null; 
        				Statement checkMemberStmt = null;
        				Statement checkFriendStmt = null;
        				Statement checkFrdReqStmt = null;
        				Statement insertFrdReqStmt = null;
        				ResultSet rs = null;
        				try
        				{
        					conn = ConnectDB.openConnection();
        					checkMemberStmt = conn.createStatement();
        					sqlPanel.SQLArea.append(checkEmailInMember + "\n\n");
        					rs = checkMemberStmt.executeQuery(checkEmailInMember);
        					
        					if(!rs.next())
        					{
        						JOptionPane.showMessageDialog(null, "User with EMAIL \"" + friendEmail +"\" not present. Cannot add friend ", "alert", JOptionPane.INFORMATION_MESSAGE);
        						return;
        					}
        					
        					rs = null;
        					checkFriendStmt = conn.createStatement();
        					sqlPanel.SQLArea.append(checkEmailInFriend + "\n\n");
        					rs = checkFriendStmt.executeQuery(checkEmailInFriend);
        					
        					if(rs.next())
        					{
        						JOptionPane.showMessageDialog(null, "You are already friends with user who's EMAIL = \"" + friendEmail +"\"", "alert", JOptionPane.INFORMATION_MESSAGE);
        						return;
        					}
        					
        					rs = null;
        					checkFrdReqStmt = conn.createStatement();
        					sqlPanel.SQLArea.append(friendRequest + "\n\n");
        					rs = checkFrdReqStmt.executeQuery(friendRequest);
        					
        					boolean isUpdate = false;
        					if(rs.next())
        					{
        						isUpdate = true;
        						String frdReqReceiver = rs.getString("RECEIVER");
        						String frdReqType = rs.getString("FRIEND_TYPE");
        						if(frdReqReceiver.equals(userEmail))
        						{
        							JOptionPane.showMessageDialog(null, "User with EMAIL = \"" + friendEmail + "\" has already sent you a friend request", "alert", JOptionPane.INFORMATION_MESSAGE);
            						return;
        						}
        						if(frdReqType.equals(friendType))
        						{
        							JOptionPane.showMessageDialog(null, "Friend Request to User with EMAIL = \""+ friendEmail + "\" and Type = \"" + friendType + "\" already sent", "alert", JOptionPane.INFORMATION_MESSAGE);
            						return;
        						}
        					}
        					
        					rs = null;
        					insertFrdReqStmt = conn.createStatement();
        					if(!isUpdate)
        					{
        						sqlPanel.SQLArea.append(insertQuery + "\n\n");
        						insertFrdReqStmt.executeUpdate(insertQuery);
        						JOptionPane.showMessageDialog(null, "Friend Request to User with EMAIL = \""+ friendEmail + "\" and Type = \"" + friendType + "\" Sent Successfully");
        					}
        					else
        					{
        						sqlPanel.SQLArea.append(updateQuery + "\n\n");
        						insertFrdReqStmt.executeUpdate(updateQuery);
        						JOptionPane.showMessageDialog(null, "Friend Request to User with EMAIL = \""+ friendEmail + "\" and Type = \"" + friendType + "\" Updated Successfully");
        					}
        				}
        				catch(SQLException ex)
        				{
        					ex.printStackTrace();
        				}
        				catch(Exception ex1)
        				{
        				      ex1.printStackTrace();
        				}
        				finally
        				{
        				      try
        				      {
        				    	  if(checkMemberStmt != null)
        				    		  checkMemberStmt.close();
        				    	  
        				    	  if(checkFriendStmt != null)
        				    		  checkFriendStmt.close();
        				    	  
        				    	  if(checkFrdReqStmt != null)
        				    		  checkFrdReqStmt.close();
        				    	  
        				    	  if(insertFrdReqStmt != null)
        				    		  insertFrdReqStmt.close();
        				    	  
        				    	  if(conn != null)
        				            conn.close();
        				      }
        				      catch(SQLException se)
        				      {
        				    	  se.printStackTrace();
        				      }
        				 }
                    }
                });

            }
        });
		buttonPanel.buttons[1].addActionListener(new ActionListener() {
          
            public void actionPerformed(ActionEvent e) {
            	/*Fill this function*/
            	/*Press this list all post button, you should be able to list all the post which are visible to you*/
            	/*You can define the output format*/
            	
            	String userEmail = loginPanel.username.getText();
            	
            	String searchSelect1 = "SELECT P.PID, P.NOTE, P.SENDER, P.DATETIME FROM POST P ";
            	String searchWhere1 = "WHERE P.SENDER = \'" + userEmail + "\' OR P.PRIVACY = \'2\' OR (P.SENDER IN";
            	String searchSelect2 = "(SELECT F1.USER2 FROM FRIENDSHIP F1, MEMBER M1 ";
            	String searchWhere2 = "WHERE M1.EMAIL = F1.USER1 AND M1.EMAIL = \'" + userEmail + "\' UNION ";
            	String searchSelect3 = "SELECT F2.USER1 FROM FRIENDSHIP F2, MEMBER M2 ";
            	String searchWhere3 = "WHERE M2.EMAIL = F2.USER2 AND M2.EMAIL = \'" + userEmail + "\') ";
            	String searchEnd = "AND P.PRIVACY <> 0)";
            	String finalSearchQuery = searchSelect1 + searchWhere1 + searchSelect2 + searchWhere2 + searchSelect3 + searchWhere3 + searchEnd;
            	
				String ResultVal;
				Connection conn = null; 
				Statement postStmt = null;
				ResultSet rs = null;
				try
				{
					conn = ConnectDB.openConnection();
					postStmt = conn.createStatement();
					sqlPanel.SQLArea.append(finalSearchQuery+"\n\n");
					rs = postStmt.executeQuery(finalSearchQuery);
					ResultVal = "*****POSTS VISIBLE FOR " + userEmail + "*****\n\n";
					boolean hasPost = false;
					while(rs.next())
					{
						hasPost = true;
						ResultVal += "POST ID : " + rs.getString("PID") + "\n";
						ResultVal += "NOTE : " + rs.getString("NOTE") + "\n";
						ResultVal += "SENDER : " + rs.getString("SENDER") + "\n";
						ResultVal += "DATETIME : " + rs.getString("DATETIME") + "\n";
						resultArea.append(ResultVal);
						resultArea.append("---------------------------------------------------\n");
						ResultVal = "";
					}
					if(!hasPost)
					{
						JOptionPane.showMessageDialog(null, "There are no POSTS (OR) You do not have the privacy level to see the Post", "alert", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
				}
				catch(Exception ex1)
				{
				      ex1.printStackTrace();
				}
				finally
				{
				      try
				      {
				    	  if(postStmt != null)
				    		  postStmt.close();
				    	  
				    	  if(conn != null)
				            conn.close();
				      }
				      catch(SQLException se)
				      {
				    	  se.printStackTrace();
				      }
				 }
            	
            }
        });

		buttonPanel.buttons[2].addActionListener(new ActionListener() 
		{
          
            public void actionPerformed(ActionEvent e) 
            {
            	final Frame0 frame=new Frame0("Post ID: ",2);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);
                frame.btn1.addActionListener(new ActionListener() 
                {
                 
                    public void actionPerformed(ActionEvent e) 
                    {
                    	/*Fill this function*/
    	            	/*Press this List Comments Button, input Post ID, press OK, you should be able to list all the comment about this post*/
                        String post_ID = frame.txtfield.getText();
                    	frame.dispose();
                    	
                    	String userEmail = loginPanel.username.getText();
                    	
                    	String searchSelect1 = "SELECT P.PID FROM POST P ";
                    	String searchWhere1 = "WHERE P.SENDER = \'" + userEmail + "\' OR P.PRIVACY = \'2\' OR (P.SENDER IN";
                    	String searchSelect2 = "(SELECT F1.USER2 FROM FRIENDSHIP F1, MEMBER M1 ";
                    	String searchWhere2 = "WHERE M1.EMAIL = F1.USER1 AND M1.EMAIL = \'" + userEmail + "\' UNION ";
                    	String searchSelect3 = "SELECT F2.USER1 FROM FRIENDSHIP F2, MEMBER M2 ";
                    	String searchWhere3 = "WHERE M2.EMAIL = F2.USER2 AND M2.EMAIL = \'" + userEmail + "\') ";
                    	String searchEnd = "AND P.PRIVACY <> 0)";
                    	String finalSearchQuery = searchSelect1 + searchWhere1 + searchSelect2 + searchWhere2 + searchSelect3 + searchWhere3 + searchEnd;	             	
        				
        				String ResultVal;
        				Connection conn = null; 
        				Statement postStmt = null;
        				Statement commentStmt = null;
        				ResultSet rs = null;
        				try
        				{
        					boolean isPostIDValid = false;
        					conn = ConnectDB.openConnection();
        					postStmt = conn.createStatement();
        					sqlPanel.SQLArea.append(finalSearchQuery+"\n\n");
        					rs = postStmt.executeQuery(finalSearchQuery);
        					while(rs.next())
        					{
        						ResultVal = rs.getString("PID");
        						if(ResultVal.equals(post_ID))
        						{
        							isPostIDValid = true;
        							break;
        						}
        					}
        					if(!isPostIDValid)
        					{
        						JOptionPane.showMessageDialog(null, "You do not have the privacy level to see comments related to post/ (OR) Post with Post ID Does not exist", "alert", JOptionPane.ERROR_MESSAGE);
        	            		return;
        					}
        					isPostIDValid = false;
        					String commentQuery = "SELECT P.TEXT, P.MID, P.DATETIME FROM POST_COMMENT P WHERE P.PID = " + post_ID;
        					commentStmt = conn.createStatement();
        					sqlPanel.SQLArea.append(commentQuery+"\n\n");
        					rs = commentStmt.executeQuery(commentQuery);
        					ResultVal = "*****COMMENTS VISIBLE FOR " + userEmail + "*****\n\n";
        					while(rs.next())
        					{
        						isPostIDValid = true;
        						ResultVal += "COMMENT : " + rs.getString("TEXT") + "\n";
        						ResultVal += "USER : " + rs.getString("MID") + "\n";
        						ResultVal += "DATETIME : " + rs.getString("DATETIME") + "\n";
        						ResultVal += "---------------------------------------------------\n";
        						        						
        					}
        					if(!isPostIDValid)
        					{
        						JOptionPane.showMessageDialog(null, "No comments available for the given post.", "alert", JOptionPane.ERROR_MESSAGE);
        						return;
        					}
        					
        					resultArea.append(ResultVal);
        				}
        				catch(SQLException ex)
        				{
        					ex.printStackTrace();
        				}
        				catch(Exception ex1)
        				{
        				      ex1.printStackTrace();
        				}
        				finally
        				{
        				      try
        				      {
        				    	  if(postStmt != null)
        				    		  postStmt.close();
        				    	  
        				    	  if(commentStmt != null)
        				    		  commentStmt.close();
        				    	  
        				    	  if(conn != null)
        				            conn.close();
        				      }
        				      catch(SQLException se)
        				      {
        				    	  se.printStackTrace();
        				      }
        				}
                    }
                });
            }
        });
		
		buttonPanel.buttons[3].addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
            	final Frame1 frame=new Frame1("Post ID: ","Content: ");
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);
                frame.btn1.addActionListener(new ActionListener() {
                    
                    public void actionPerformed(ActionEvent e) {
                    	/*Fill this function*/
    	            	/*Press this Comment Button, input Post ID, and comment, press OK, you should be able to comment on this post*/
                    	/*After press ok, you should also pop up a standard dialog box to show the comment's status <succeed or failed>*/	
                    	String userEmail = loginPanel.username.getText();
                    	String pID = frame.txtfield.getText();
                    	String userComment = frame.textArea.getText();
                    	frame.dispose();
                    	if(pID.isEmpty())
                    	{
                    		JOptionPane.showMessageDialog(null, "POST ID cannot be empty", "alert", JOptionPane.ERROR_MESSAGE);
                    		return;
                    	}
                    	if(userComment.isEmpty())
                    	{
                    		JOptionPane.showMessageDialog(null, "Comment Field cannot be empty ", "alert", JOptionPane.ERROR_MESSAGE);
                    		return;
                    	}
                    	String searchSelect1 = "SELECT P.PID FROM POST P ";
                    	String searchWhere1 = "WHERE P.SENDER = \'" + userEmail + "\' OR P.PRIVACY = \'2\' OR (P.SENDER IN";
                    	String searchSelect2 = "(SELECT F1.USER2 FROM FRIENDSHIP F1, MEMBER M1 ";
                    	String searchWhere2 = "WHERE M1.EMAIL = F1.USER1 AND M1.EMAIL = \'" + userEmail + "\' UNION ";
                    	String searchSelect3 = "SELECT F2.USER1 FROM FRIENDSHIP F2, MEMBER M2 ";
                    	String searchWhere3 = "WHERE M2.EMAIL = F2.USER2 AND M2.EMAIL = \'" + userEmail + "\') ";
                    	String searchEnd = "AND P.PRIVACY <> 0)";
                    	String finalSearchQuery = searchSelect1 + searchWhere1 + searchSelect2 + searchWhere2 + searchSelect3 + searchWhere3 + searchEnd;
                    	
        				String ResultVal;
        				Connection conn = null; 
        				Statement getpostStmt = null;
        				Statement insertCommentStmt = null;
        				ResultSet rs = null;
        				try
        				{
        					conn = ConnectDB.openConnection();
        					getpostStmt = conn.createStatement();
        					sqlPanel.SQLArea.append(finalSearchQuery+"\n\n");
        					rs = getpostStmt.executeQuery(finalSearchQuery);
        					boolean hasPost = false;
        					boolean canComment = false;
        					while(rs.next())
        					{
        						hasPost = true;
        						ResultVal = rs.getString("PID");
        						if(ResultVal.equals(pID))
        						{
        							canComment = true;
        							break;
        						}
        					}
        					if(!hasPost)
        					{
        						JOptionPane.showMessageDialog(null, "There are no POSTS (OR) You do not have the privacy level to see and comment the Post", "alert", JOptionPane.ERROR_MESSAGE);
        						return;
        					}
        					if(!canComment)
        					{
        						JOptionPane.showMessageDialog(null, "You do not have Privacy level to comment on Post with PID : " + pID, "alert", JOptionPane.ERROR_MESSAGE);
        						return;
        					}
        					String dateTime = new SimpleDateFormat("dd-MM-YY").format(Calendar.getInstance().getTime());
        					
        					String insertCommentQuery = "INSERT INTO \"POST_COMMENT\" (PID, TEXT, MID, DATETIME) VALUES (\'" + pID + "\', \'" + userComment + "\' , \'" + userEmail + "\', \'" + dateTime + "\')";
        					
        					insertCommentStmt = conn.createStatement();
        					sqlPanel.SQLArea.append(insertCommentQuery+"\n\n");
        					getpostStmt.executeUpdate(insertCommentQuery);
        					JOptionPane.showMessageDialog(null, "Comment \"" + userComment + "\" for PID = " + pID + " Commented Successfully");
        				}
        				catch(SQLException ex)
        				{
        					ex.printStackTrace();
        				}
        				catch(Exception ex1)
        				{
        				      ex1.printStackTrace();
        				}
        				finally
        				{
        				      try
        				      {
        				    	  if(getpostStmt != null)
        				    		  getpostStmt.close();
        				    	  
        				    	  if(insertCommentStmt != null)
        				    		  insertCommentStmt.close();
        				    	  
        				    	  if(conn != null)
        				            conn.close();
        				      }
        				      catch(SQLException se)
        				      {
        				    	  se.printStackTrace();
        				      }
        				 }
                    }
                });
            }
        });
		
		buttonPanel.buttons[4].addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
            	/*Fill this function*/
            	/*Press this List all event Button, you should be able to list all available event*/
            	
            	String searchSelect = "SELECT E.TITLE, E.DESCRIPTION, Z.CITY, Z.STATE, Z.COUNTRY, E.STARTING_TIME, E.DURATION";
            	String searchFrom = " FROM EVENT E, ADDRESS A, ZIPCODE_REF Z";
            	String searchWhere = " WHERE E.ADDRESS_ID = A.AID AND A.ZIPCODE = Z.ZIPCODE AND TRUNC(SYSDATE) <= E.STARTING_TIME";
            	String finalSearchQuery = searchSelect + searchFrom + searchWhere;;
            	
				String ResultVal;
				Connection conn = null; 
				Statement eventStmt = null;
				ResultSet rs = null;
				try
				{
					conn = ConnectDB.openConnection();
					eventStmt = conn.createStatement();
					sqlPanel.SQLArea.append(finalSearchQuery+"\n\n");
					rs = eventStmt.executeQuery(finalSearchQuery);
					ResultVal = "*****EVENTS*****\n\n";
					while(rs.next())
					{		
						ResultVal += "TITLE : " + rs.getString("TITLE") + "\n";
						ResultVal += "DESCRIPTION : " + rs.getString("DESCRIPTION") + "\n";
						ResultVal += "CITY : " + rs.getString("CITY") + "\n";
						ResultVal += "STATE : " + rs.getString("STATE") + "\n";
						ResultVal += "COUNTRY : " + rs.getString("COUNTRY") + "\n";
						ResultVal += "STARTING_TIME : " + rs.getString("STARTING_TIME") + "\n";
						ResultVal += "DURATION : " + rs.getString("DURATION") + "\n";
						resultArea.append(ResultVal);
						resultArea.append("---------------------------------------------------\n");
						ResultVal = "";
					}
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
				}
				catch(Exception ex1)
				{
				      ex1.printStackTrace();
				}
				finally
				{
				      try
				      {
				    	  if(eventStmt != null)
				    		  eventStmt.close();
				    	  
				    	  if(conn != null)
				            conn.close();
				      }
				      catch(SQLException se)
				      {
				    	  se.printStackTrace();
				      }
				 }
				
            }
        });


		buttonPanel.buttons[5].addActionListener(new ActionListener() 
		{
           
			public void actionPerformed(ActionEvent e) 
			{
        	
				// Check if the user has Friend Request or not.
				// If the user does not have any friend request, do not show the Accept/Decline Box.
				String userEmail = loginPanel.username.getText();
				String frdReqQuery = "SELECT F.SENDER, F.FRIEND_TYPE FROM FRIEND_REQUEST F WHERE F.RECEIVER = \'" + userEmail + "\'";
								
				Connection conn = null; 
				Statement checkFrdReqStmt = null;
				ResultSet rs = null;
				try
				{
					conn = ConnectDB.openConnection();
					checkFrdReqStmt = conn.createStatement();
					sqlPanel.SQLArea.append(frdReqQuery+"\n\n");
					rs = checkFrdReqStmt.executeQuery(frdReqQuery);
					
					if(!rs.next())
					{
						JOptionPane.showMessageDialog(null, "There are currently no friend requests available");
						return;
					}
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
				}
				catch(Exception ex1)
				{
					ex1.printStackTrace();
				}
				finally
				{
					try
					{
						if(checkFrdReqStmt != null)
							checkFrdReqStmt.close();
				    	  
						if(conn != null)
							conn.close();
					}
					catch(SQLException se)
					{
						se.printStackTrace();
					}
				}
				
				final Frame2 frame=new Frame2("Decline: ");
	            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	            frame.setVisible(true);

            	frame.btn1.addActionListener(new ActionListener() 
            	{
            		public void actionPerformed(ActionEvent e) 
            		{
            			/*Fill this function*/
            			/*Press this decline Button, you should be able to decline friend request*/
            			/*You could decline one at a time, or decline many at a time. e.g. using comma to separate each request*/
            			/*pop up a standard dialog box to show <succeed or failed>*/
            			
            			String userEmail = loginPanel.username.getText();
            			String declineFrds = frame.txtfield.getText();
            			String []decFrdArr;
            			List<String> mylist = new ArrayList<String>();
            			
            			if(declineFrds.isEmpty())
            			{
            				JOptionPane.showMessageDialog(null, "Email of Friend to decline cannot be empty");
            				return;
            			}
            			
            			if(declineFrds.contains(","))
            			{
            				decFrdArr = declineFrds.split(",");
            				for(String str:decFrdArr)
            				{
            					mylist.add(str);
            				}
            			}
            			else
            			{
            				mylist.add(declineFrds);
            			}
            			
            			
            			Connection conn = null; 
        				Statement checkFrdReqStmt = null;
        				ResultSet rs = null;
        				int i = 0;
        				try
        				{
        					conn = ConnectDB.openConnection();
        					checkFrdReqStmt = conn.createStatement();
        					
        					for(i=0; i<mylist.size(); i++)
        					{
        						String str = mylist.get(i);
        						str = str.trim();
        						String frdReqQuery = "SELECT F.SENDER FROM FRIEND_REQUEST F WHERE F.SENDER = \'" + str + "\' AND F.RECEIVER = \'" + userEmail + "\'";
        						sqlPanel.SQLArea.append(frdReqQuery+"\n\n");
        						rs = checkFrdReqStmt.executeQuery(frdReqQuery);
        						if(!rs.next())
        						{
        							JOptionPane.showMessageDialog(null, "There is no friend request from User = \"" + str);
        							rs = null;
        							continue;
        						}
        						String delFrdReqQuery = "DELETE FROM FRIEND_REQUEST WHERE SENDER= \'" + str + "\' AND RECEIVER = \'" + userEmail + "\'";
        						sqlPanel.SQLArea.append(delFrdReqQuery+"\n\n");
        						checkFrdReqStmt.executeUpdate(delFrdReqQuery);
        						JOptionPane.showMessageDialog(null, "Friend Request for User with EMAIL = \"" + str + "\" Declined.");
        					}
        					
        					String frdReqQuery1 = "SELECT F.SENDER, F.FRIEND_TYPE FROM FRIEND_REQUEST F WHERE F.RECEIVER = \'" + userEmail + "\'";
        					sqlPanel.SQLArea.append(frdReqQuery1+"\n\n");
        					rs = checkFrdReqStmt.executeQuery(frdReqQuery1);
        					
        					if(!rs.next())
        					{
        						JOptionPane.showMessageDialog(null, "You have no more friend requests");
        						frame.dispose();
        						notifyButton.setVisible(false);
        					}
        				}
        				catch(SQLException ex)
        				{
        					ex.printStackTrace();
        				}
        				catch(Exception ex1)
        				{
        					ex1.printStackTrace();
        				}
        				finally
        				{
        					try
        					{
        						if(checkFrdReqStmt != null)
        							checkFrdReqStmt.close();
        				    	  
        						if(conn != null)
        							conn.close();
        					}
        					catch(SQLException se)
        					{
        						se.printStackTrace();
        					}
        				}
            		}
            	});


            	frame.btn2.addActionListener(new ActionListener() 
            	{
           
            		public void actionPerformed(ActionEvent e) 
            		{
            			/*Fill this function*/
            			/*Press this accept all Button, you should be able to accept all friend request and add this information into friend relationship table*/
            			/*pop up a standard dialog box to show <succeed or failed>*/
            			
            			String userEmail = loginPanel.username.getText();
        				String frdReqQuery = "SELECT F.SENDER, F.FRIEND_TYPE FROM FRIEND_REQUEST F WHERE F.RECEIVER = \'" + userEmail + "\'";
            			
            			String frdRequestor;
        				String frdReqType;
        				List<String> frdRequestorList = new ArrayList<String>();
        				List<String> frdReqTypeList = new ArrayList<String>();
        				int i = 0;
        								
        				Connection conn = null; 
        				Statement checkFrdReqStmt = null;
        				ResultSet rs = null;
        				try
        				{
        					conn = ConnectDB.openConnection();
        					checkFrdReqStmt = conn.createStatement();
        					sqlPanel.SQLArea.append(frdReqQuery+"\n\n");
        					rs = checkFrdReqStmt.executeQuery(frdReqQuery);
        					
        					boolean hasFrdReq = false;
        					while(rs.next())
        					{
        						frdRequestor = rs.getString("SENDER");
        						frdReqType = rs.getString("FRIEND_TYPE");
        						frdRequestorList.add(frdRequestor);
        						frdReqTypeList.add(frdReqType);
        						hasFrdReq = true;
        					}
        					if(!hasFrdReq)
        					{
        						JOptionPane.showMessageDialog(null, "You do not have any more friend requests");
        						frame.dispose();
        						notifyButton.setVisible(false);
        						return;
        					}
        					for(i=0; i<frdRequestorList.size(); i++)
        					{
        						frdRequestor = frdRequestorList.get(i);
        						frdReqType = frdReqTypeList.get(i);
        						String delFrdReqQuery = "DELETE FROM FRIEND_REQUEST WHERE SENDER= \'" + frdRequestor + "\' AND RECEIVER = \'" + userEmail + "\'";
        						sqlPanel.SQLArea.append(delFrdReqQuery+"\n\n");
        						checkFrdReqStmt.executeUpdate(delFrdReqQuery);
        						
        						String insertFrdQuery = "INSERT INTO \"FRIENDSHIP\" (USER1, USER2, TYPE) VALUES (\'" + userEmail + "\', \'" + frdRequestor + "\', \'" + frdReqType + "\')";
        						sqlPanel.SQLArea.append(delFrdReqQuery+"\n\n");
        						checkFrdReqStmt.executeUpdate(insertFrdQuery);
        						JOptionPane.showMessageDialog(null, "User with EMAIL = \"" + frdRequestor + "\" is now your friend with Friend Type = \"" + frdReqType + "\"");
        					}
        					frame.dispose();
        					notifyButton.setVisible(false);
        				}
        				catch(SQLException ex)
        				{
        					ex.printStackTrace();
        				}
        				catch(Exception ex1)
        				{
        					ex1.printStackTrace();
        				}
        				finally
        				{
        					try
        					{
        						if(checkFrdReqStmt != null)
        							checkFrdReqStmt.close();
        				    	  
        						if(conn != null)
        							conn.close();
        					}
        					catch(SQLException se)
        					{
        						se.printStackTrace();
        					}
        				}
            		}	
            	});
			}
		});

		buttonPanel.buttons[6].addActionListener(new ActionListener() {
         
            public void actionPerformed(ActionEvent e) {
            	/*Fill this function*/
            	/*Press this Button, you should be able list the information(including address information) about your friend who live nearest to you*/
            	/*This is a spatial query*/
            	String userEmail = loginPanel.username.getText();
            	String selectTop = "SELECT * FROM (SELECT M.EMAIL, M.FNAME, M.LNAME, A.STREET_NO, A.STREET_ADDRESS, Z.CITY, Z.STATE, Z.COUNTRY ";
            	String fromTop = "FROM MEMBER M, ADDRESS A, ZIPCODE_REF Z WHERE Z.ZIPCODE = A.ZIPCODE AND M.AID = A.AID AND M.EMAIL IN"; 
            	String frdQuery1 = "(SELECT F1.USER2 FROM FRIENDSHIP F1, MEMBER M1 WHERE M1.EMAIL = F1.USER1 AND M1.EMAIL = \'" + userEmail + "\'";
            	String frdQuery2 = " UNION SELECT F2.USER1 FROM FRIENDSHIP F2, MEMBER M2 WHERE M2.EMAIL = F2.USER2 AND M2.EMAIL = \'" + userEmail + "\')";
            	String sdoNN1 = "AND sdo_nn(A.COORDINATES, (SELECT A1.COORDINATES FROM ADDRESS A1, MEMBER M3 WHERE M3.EMAIL = \'" + userEmail;
            	String sdoNN2 = "\' AND M3.AID = A1.AID))=\'TRUE\') WHERE rownum = 1";
            	String finalSdoNNQuery = selectTop + fromTop + frdQuery1 + frdQuery2 + sdoNN1 + sdoNN2;
            	
            	Connection conn = null; 
				Statement nearestFrdStmt = null;
				ResultSet rs = null;
				String ResultVal;
				try
				{
					conn = ConnectDB.openConnection();
					nearestFrdStmt = conn.createStatement();
					sqlPanel.SQLArea.append(finalSdoNNQuery+"\n\n");
					rs = nearestFrdStmt.executeQuery(finalSdoNNQuery);
					boolean hasValue = false;
					ResultVal = "********* Nearest Friend to you **********\n\n";
					while(rs.next())
					{
						hasValue = true;
						ResultVal += "EMAIL : " + rs.getString("EMAIL") + "\n";
						ResultVal += "FIRSTNAME : " + rs.getString("FNAME") + "\n";
						ResultVal += "LASTNAME : " + rs.getString("LNAME") + "\n";
						ResultVal += "STREET_NO : " + rs.getString("STREET_NO") + "\n";
						ResultVal += "STREET_ADDRESS: " + rs.getString("STREET_ADDRESS") + "\n";
						ResultVal += "CITY : " + rs.getString("CITY") + "\n";
						ResultVal += "STATE : " + rs.getString("STATE") + "\n";
						ResultVal += "COUNTRY : " + rs.getString("COUNTRY") + "\n";
						resultArea.append(ResultVal);
						resultArea.append("---------------------------------------------------\n");
						ResultVal = "";
					}
					if(!hasValue)
					{
						JOptionPane.showMessageDialog(null, "You currently do not have any friends");
                		return;
					}
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
				}
				catch(Exception ex1)
				{
					ex1.printStackTrace();
				}
				finally
				{
					try
					{
						if(nearestFrdStmt != null)
							nearestFrdStmt.close();
				    	  
						if(conn != null)
							conn.close();
					}
					catch(SQLException se)
					{
						se.printStackTrace();
					}
				}
            }
        });
		
		buttonPanel.buttons[7].addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
            	final Frame3 frame=new Frame3("Please input coordinate: ");
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);

                frame.btn1.addActionListener(new ActionListener() {
                  
                    public void actionPerformed(ActionEvent e) {
                    	/*Fill this function*/
                    	/*Press this Button, input left top corner coordinate and right down corner coordinate*/
                    	/*press ok, you should be able list the information(including address information) about your friend who lives in this area. Close query window*/
                    	/*This is a spatial query*/
                    	String userEmail = loginPanel.username.getText();
                    	String topLeftX = frame.txtfield[0].getText();
                    	String topLeftY = frame.txtfield[1].getText();
                    	String bottomRightX = frame.txtfield[2].getText();
                    	String bottomRightY = frame.txtfield[3].getText();
                    	if(topLeftX.isEmpty() || topLeftY.isEmpty() || bottomRightX.isEmpty() || bottomRightY.isEmpty())
                    	{
                    		JOptionPane.showMessageDialog(null, "None of the coordinates can be empty");
                    		return;
                    	}
                    	
                    	try  
                    	{
                    	    double d;
                    	    d = Double.parseDouble(topLeftX);
                    	    d = Double.parseDouble(topLeftY);
                    	    d = Double.parseDouble(bottomRightX);
                    	    d = Double.parseDouble(bottomRightY);
                    	}  
                    	catch(NumberFormatException nfe)  
                    	{  
                    		JOptionPane.showMessageDialog(null, "All of the coordinates has to be a numeric value");
                    		return;  
                    	}
                    	
                    	String selectTop = "SELECT M.EMAIL, M.FNAME, M.LNAME, A.STREET_NO, A.STREET_ADDRESS, Z.CITY, Z.STATE, Z.COUNTRY ";
                    	String fromTop = "FROM MEMBER M, ADDRESS A, ZIPCODE_REF Z WHERE Z.ZIPCODE = A.ZIPCODE AND M.AID = A.AID AND M.EMAIL IN"; 
                    	String frdQuery1 = "(SELECT F1.USER2 FROM FRIENDSHIP F1, MEMBER M1 WHERE M1.EMAIL = F1.USER1 AND M1.EMAIL = \'" + userEmail + "\'";
                    	String frdQuery2 = " UNION SELECT F2.USER1 FROM FRIENDSHIP F2, MEMBER M2 WHERE M2.EMAIL = F2.USER2 AND M2.EMAIL = \'" + userEmail + "\')";
                    	String sdoInside1 = "AND sdo_inside(A.COORDINATES, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,3), SDO_ORDINATE_ARRAY(";
                    	String sdoInside2 = topLeftX + ", " + topLeftY + ", " + bottomRightX + ", " + bottomRightY + ")))=\'TRUE\'";
                    	String finalSdoInsideQuery = selectTop + fromTop + frdQuery1 + frdQuery2 + sdoInside1 + sdoInside2;
                    	
                    	Connection conn = null; 
        				Statement frdInRectStmt = null;
        				ResultSet rs = null;
        				String ResultVal;
        				try
        				{
        					conn = ConnectDB.openConnection();
        					frdInRectStmt = conn.createStatement();
        					sqlPanel.SQLArea.append(finalSdoInsideQuery+"\n\n");
        					rs = frdInRectStmt.executeQuery(finalSdoInsideQuery);
        					boolean hasFrds = false;
        					ResultVal = "\n********* Friend(s) in given coordinate region **********\n\n";
        					while(rs.next())
        					{
        						hasFrds = true;
        						ResultVal += "EMAIL : " + rs.getString("EMAIL") + "\n";
        						ResultVal += "FIRSTNAME : " + rs.getString("FNAME") + "\n";
        						ResultVal += "LASTNAME : " + rs.getString("LNAME") + "\n";
        						ResultVal += "STREET_NO : " + rs.getString("STREET_NO") + "\n";
        						ResultVal += "STREET_ADDRESS: " + rs.getString("STREET_ADDRESS") + "\n";
        						ResultVal += "CITY : " + rs.getString("CITY") + "\n";
        						ResultVal += "STATE : " + rs.getString("STATE") + "\n";
        						ResultVal += "COUNTRY : " + rs.getString("COUNTRY") + "\n";
        						ResultVal += "---------------------------------------------------\n";
        					}
        					if(!hasFrds)
        					{
        						JOptionPane.showMessageDialog(null, " No friends present in given coordinate region");
        						return;
        					}
        					resultArea.append(ResultVal);
    						frame.dispose();
        				}
        				catch(SQLException ex)
        				{
        					ex.printStackTrace();
        				}
        				catch(Exception ex1)
        				{
        					ex1.printStackTrace();
        				}
        				finally
        				{
        					try
        					{
        						if(frdInRectStmt != null)
        							frdInRectStmt.close();
        				    	  
        						if(conn != null)
        							conn.close();
        					}
        					catch(SQLException se)
        					{
        						se.printStackTrace();
        					}
        				}
                    	
                    }
                });
            }
        });
		
	}

	public void setPostandSearchPanel(){
		postandsearch = new PostandSearchPanel();
		this.add(postandsearch);
		StringBuffer result= new StringBuffer();


		postandsearch.buttons[0].addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) { 
            	/*Fill this function*/
            	/*Press this Button, you should be able post the information written in the textArea*/
            	/*pop up a standard dialog box to show <succeed or failed>*/
            	String postVal = postandsearch.textArea.getText();
            	String userEmail = loginPanel.username.getText();
            	if(postVal.isEmpty())
            	{
            		JOptionPane.showMessageDialog(null, "Post cannot be empty", "alert", JOptionPane.ERROR_MESSAGE);
            		return;
            	}

            	String privacyLevel = (String)postandsearch.PrivacyCombo.getSelectedItem();
            	if(privacyLevel.equals("All: 2"))
            	{
            		privacyLevel = "2";
            		
            	}
            	else if(privacyLevel.equals("FRIENDS: 1"))
            	{
            		privacyLevel = "1";
            	}
            	else
            	{
            		privacyLevel = "0";
            	}

            	String dateTime = new SimpleDateFormat("dd-MM-YY").format(Calendar.getInstance().getTime());
            	
            	String myPID = "1";
				Connection conn = null; 
				Statement getPIDStmt = null;
				Statement insertPostStmt = null;
				ResultSet rs = null;
				String getMaxPIDQuery = "SELECT * FROM (SELECT PID FROM POST ORDER BY PID DESC) WHERE rownum = 1";
            	
				try
				{
					conn = ConnectDB.openConnection();
					getPIDStmt = conn.createStatement();
					sqlPanel.SQLArea.append(getMaxPIDQuery+"\n\n");
					rs = getPIDStmt.executeQuery(getMaxPIDQuery);
										
					if(rs.next())
					{
						int pid = rs.getInt("PID");
						pid++;
						myPID = Integer.toString(pid);
					}
					
					String insertPostQuery = "INSERT INTO \"POST\" (PID, NOTE, SENDER, PRIVACY, DATETIME) VALUES (\'" + myPID + "\', \'" + postVal + "\', \'" + userEmail + "\', \'" + privacyLevel + "\', \'" + dateTime + "\')";
					insertPostStmt = conn.createStatement();
					sqlPanel.SQLArea.append(insertPostQuery);
					insertPostStmt.executeUpdate(insertPostQuery);
					JOptionPane.showMessageDialog(null, "Post \"" + postVal + "\" with PID = " + myPID + " Posted Successfully");
					postandsearch.textArea.setText("");
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
				}
				catch(Exception ex1)
				{
				      ex1.printStackTrace();
				}
				finally
				{
				      try
				      {
				    	  if(getPIDStmt != null)
				    		  getPIDStmt.close();
				    	  
				    	  if(conn != null)
				            conn.close();
				      }
				      catch(SQLException se)
				      {
				    	  se.printStackTrace();
				      }
				 }
            }
        });


		postandsearch.buttons[1].addActionListener(new ActionListener() 
		{
           
            public void actionPerformed(ActionEvent e) 
            {  
            	/*Fill this function*/
            	/*Press this Button, you should be able search user information, list the information on the result panel*/
            	/*The search should based on email, first name or last name*/
            	String searchFriend = postandsearch.text.getText();
            	searchFriend = searchFriend.toUpperCase();
            	if(searchFriend.isEmpty())
            	{
            		JOptionPane.showMessageDialog(null, "Search box cannot be empty", "alert", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	String searchSelect = "SELECT M.EMAIL, M.FNAME, M.LNAME, M.BIRTHDATE, A.STREET_NO, A.STREET_ADDRESS, Z.ZIPCODE, Z.CITY, Z.STATE, Z.COUNTRY";
            	String searchFrom = " FROM MEMBER M, ADDRESS A, ZIPCODE_REF Z";
            	String searchWhere = " WHERE (upper(M.EMAIL) = \'" + searchFriend + "\' OR upper(M.FNAME) = \'" + searchFriend + "\' OR upper(M.LNAME) = \'" + searchFriend + "\')";
            	String searchWhereExtra = " AND M.AID = A.AID AND A.ZIPCODE = Z.ZIPCODE";
            	String finalSearchQuery = searchSelect + searchFrom + searchWhere + searchWhereExtra;
				
            	String userEmail = loginPanel.username.getText();
				
				String ResultVal;
				Connection conn = null; 
				Statement checkNameStmt = null;
				ResultSet rs = null;
				try
				{
					conn = ConnectDB.openConnection();
					checkNameStmt = conn.createStatement();
					sqlPanel.SQLArea.append(finalSearchQuery+"\n\n");
					rs = checkNameStmt.executeQuery(finalSearchQuery);
					
					if(!rs.next())
					{
						JOptionPane.showMessageDialog(null, "No profile matches EMAIL/FNAME/LNAME given in serachbox", "alert", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					ResultVal = "EMAIL : " + rs.getString("EMAIL") + "\n";
					if(ResultVal.equals("EMAIL : " + userEmail + "\n"))
					{
						JOptionPane.showMessageDialog(null, "You searched yourself :P ;)", "alert", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					ResultVal += "FIRSTNAME : " + rs.getString("FNAME") + "\n";
					ResultVal += "LASTNAME : " + rs.getString("LNAME") + "\n";
					ResultVal += "BIRTHDATE : " + rs.getString("BIRTHDATE") + "\n";
					ResultVal += "STREET_NO : " + rs.getString("STREET_NO") + "\n";
					ResultVal += "STREET_ADDRESS: " + rs.getString("STREET_ADDRESS") + "\n";
					ResultVal += "CITY : " + rs.getString("CITY") + "\n";
					ResultVal += "STATE : " + rs.getString("STATE") + "\n";
					ResultVal += "ZIPCODE : " + rs.getString("ZIPCODE") + "\n";
					ResultVal += "COUNTRY : " + rs.getString("COUNTRY") + "\n";
					resultArea.append(ResultVal);
					resultArea.append("---------------------------------------------------\n");
					postandsearch.text.setText("");
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
				}
				catch(Exception ex1)
				{
				      ex1.printStackTrace();
				}
				finally
				{
				      try
				      {
				    	  if(checkNameStmt != null)
				    		  checkNameStmt.close();
				    	  
				    	  if(conn != null)
				            conn.close();
				      }
				      catch(SQLException se)
				      {
				    	  se.printStackTrace();
				      }
				 }
            }
        });
		
		postandsearch.buttons[2].addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent e) 
            {
            	resultArea.setText("");
            }
		});
		
		postandsearch.buttons[3].addActionListener(new ActionListener() 
		{  
            public void actionPerformed(ActionEvent e) 
            {
            	sqlPanel.SQLArea.setText("");
            }
		});
	}
	public void setSQLOutput(StringBuffer sb)
	{
		sqlPanel.SQLArea.setText(sb.toString());
		sqlPanel.SQLArea.setEnabled(true);
	}
	public void setSqlPanel(){
		sqlPanel = new SqlPanel();
		this.add(sqlPanel);
		showLabel = new JLabel("The corresponding SQL :");
		showLabel.setBounds(20, 470, 200, 20);
		this.add(showLabel);
	}

	public void setLoginPanel()
	{
		loginPanel = new LoginPanel();
		this.add(loginPanel);

		loginPanel.signup.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
		        signUpPanel.enablePanel();
			}
        });
        
		loginPanel.login.addActionListener(new ActionListener() 
        {
			public void actionPerformed(ActionEvent e) 
			{  
            	/*Fill this function*/
            	/*Press this Button, you should be able match the user information. If valid, keep the user email information(but can't modified) and clear the password*/
            	/*at the same time, you should scan the friend request table, to determine whether current logged in user has friend request, if has, set notification icon*/
            	/*If invalid, you should pop up a dialog box to notify user, then enable signup panel for user to register*/
            	/*After logged in, you should change this button's function as logout which means disable all the panel, return to the original state*/
				if(loginPanel.login.getText().equals("Login"))
				{
					String loginUserEmail = loginPanel.username.getText();
					if(loginUserEmail.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Please enter valid User Name", "alert", JOptionPane.ERROR_MESSAGE);
						return;
					}
					char []userPwd = loginPanel.password.getPassword();
					String pwd = new String(userPwd);
					if(pwd.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Please enter valid Password", "alert", JOptionPane.ERROR_MESSAGE);
						return;
					}
					loginPanel.password.setText("");
				
					String checkNameQuery = "SELECT EMAIL, PASSWORD FROM MEMBER WHERE EMAIL =\'" + loginUserEmail + "\' AND PASSWORD = \'" + pwd + "\'";
					
					String checkFrdReqQuery = "SELECT SENDER FROM FRIEND_REQUEST WHERE RECEIVER = \'" + loginUserEmail + "\'";
					
					
					Connection conn = null; 
					Statement checkNameStmt = null;
					Statement checkFrdReqStmt = null;
					ResultSet rs = null;
					try
					{
						conn = ConnectDB.openConnection();
						checkNameStmt = conn.createStatement();
						sqlPanel.SQLArea.append(checkNameQuery+"\n\n");
						rs = checkNameStmt.executeQuery(checkNameQuery);
						
						if(!rs.next())
						{
							JOptionPane.showMessageDialog(null, "UserName and Password do not match", "alert", JOptionPane.ERROR_MESSAGE);
							loginPanel.username.setText("");
							signUpPanel.enablePanel();
							return;
						}
						
						checkFrdReqStmt = conn.createStatement();
						sqlPanel.SQLArea.append(checkFrdReqQuery+"\n\n");
						rs = checkFrdReqStmt.executeQuery(checkFrdReqQuery);
						
						if(rs.next())
							notifyButton.setVisible(true);
						
						signUpPanel.disablePanel();
						loginPanel.signup.setEnabled(false);
						loginPanel.login.setText("Logout");
						loginPanel.username.disable();
						postandsearch.enablePanel();
						sqlPanel.enablePanel();
						buttonPanel.enablePanel();
						
					}
					catch(SQLException ex)
					{
						ex.printStackTrace();
					}
					catch(Exception ex1)
					{
					      ex1.printStackTrace();
					}
					finally
					{
					      try
					      {
					    	  if(checkNameStmt != null)
					    		  checkNameStmt.close();
					    	  
					    	  if(conn != null)
					            conn.close();
					      }
					      catch(SQLException se)
					      {
					    	  se.printStackTrace();
					      }
					 }
				}
				else
				{
					// Disable all panels and enter origin state.
					signUpPanel.disablePanel();
					notifyButton.setVisible(false);
					loginPanel.signup.setEnabled(true);
					loginPanel.login.setText("Login");
					loginPanel.username.enable();
					loginPanel.username.setText("");
					postandsearch.clearPanel();
					postandsearch.disablePanel();
					sqlPanel.disablePanel();
					sqlPanel.SQLArea.setText("");
					resultArea.setText("");
					buttonPanel.disablePanel();
				}
			}
        });
	}

	public void setSignupPanel()
	{

		signUpPanel = new SignupPanel();
		this.add(signUpPanel);
		signUpPanel.signup.addActionListener(new ActionListener() 
		{
            
            public void actionPerformed(ActionEvent e) 
            {  
            	/*Fill this function*/
            	/*Press this signup button, you should be able check whether current account is existed. If existed, pop up an error, if not check input validation(You can design this part according to your database table's restriction) create the new account information*/
            	/*pop up a standard dialog box to show <succeed or failed>*/
            	String newUserEmail = signUpPanel.email.getText();
            	if(newUserEmail.isEmpty())
            	{
            		JOptionPane.showMessageDialog(null, "EMAIL cannot be empty", "alert", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	char[] newUserPwdChar = signUpPanel.password.getPassword();
            	String newUserPwdStr = new String(newUserPwdChar);
            	if(newUserPwdStr.isEmpty())
            	{
            		JOptionPane.showMessageDialog(null, "Password field cannot be empty", "alert", JOptionPane.ERROR_MESSAGE);
					return;
            	}
            	char[] newUserPwdConfChar = signUpPanel.password2.getPassword();
            	String newUserPwdConfStr = new String(newUserPwdConfChar);
            	if(newUserPwdConfStr.isEmpty())
            	{
            		JOptionPane.showMessageDialog(null, "Password confirmation field cannot be empty", "alert", JOptionPane.ERROR_MESSAGE);
					return;
            	}
            	if(!(newUserPwdStr.equals(newUserPwdConfStr)))
            	{
            		JOptionPane.showMessageDialog(null, "Password does not match", "alert", JOptionPane.ERROR_MESSAGE);
					return;
            	}
            	String firstName = signUpPanel.fname.getText();
            	if(firstName.isEmpty())
            	{
            		JOptionPane.showMessageDialog(null, "First Name field cannot be empty", "alert", JOptionPane.ERROR_MESSAGE);
					return;
            	}
            	String lastName = signUpPanel.lname.getText();
            	if(lastName.isEmpty())
            	{
            		JOptionPane.showMessageDialog(null, "Last Name field cannot be empty", "alert", JOptionPane.ERROR_MESSAGE);
					return;
            	}
            	String city = signUpPanel.city.getText();
            	if(city.isEmpty())
            	{
            		JOptionPane.showMessageDialog(null, "City field cannot be empty", "alert", JOptionPane.ERROR_MESSAGE);
					return;
            	}
            	String DOB = signUpPanel.birthday.getText();
            	if(DOB.isEmpty())
            	{
            		JOptionPane.showMessageDialog(null, "Birthday field cannot be empty", "alert", JOptionPane.ERROR_MESSAGE);
					return;
            	}
            	String stNo = signUpPanel.str_no.getText();
            	if(stNo.isEmpty())
            	{
            		JOptionPane.showMessageDialog(null, "Street Number field cannot be empty", "alert", JOptionPane.ERROR_MESSAGE);
					return;
            	}
            	String stAddr = signUpPanel.str_address.getText();
            	if(stAddr.isEmpty())
            	{
            		JOptionPane.showMessageDialog(null, "Street Address field cannot be empty", "alert", JOptionPane.ERROR_MESSAGE);
					return;	
            	}
            	String zipCode = signUpPanel.zip.getText();
            	if(zipCode.isEmpty())
            	{
            		JOptionPane.showMessageDialog(null, "ZipCode field cannot be empty", "alert", JOptionPane.ERROR_MESSAGE);
					return;
            	}
            	
            	String checkNameQuery = "SELECT EMAIL FROM MEMBER WHERE EMAIL =\'" + newUserEmail + "\'";
				String insertQuery = "INSERT INTO \"MEMBER\" (EMAIL, PASSWORD, FNAME, LNAME, BIRTHDATE, AID) VALUES (\'" + newUserEmail + "\', \'" + newUserPwdStr + "\', \'" + firstName + "\', \'" + lastName + "\', TO_DATE(\'" + DOB + "\', 'DD-MM-RR'), '1')";		
            	
				Connection conn = null; 
				Statement checkNameStmt = null, insertMemberStmt = null;
				ResultSet rs = null;
				try
				{
					conn = ConnectDB.openConnection();
					checkNameStmt = conn.createStatement();
					sqlPanel.SQLArea.append(checkNameQuery+"\n\n");
					rs = checkNameStmt.executeQuery(checkNameQuery);
					
					if(rs.next())
					{
						JOptionPane.showMessageDialog(null, "User with Email already Exists", "alert", JOptionPane.ERROR_MESSAGE);
						signUpPanel.enablePanel();
						return;
					}
					
					insertMemberStmt = conn.createStatement();
					sqlPanel.SQLArea.append(insertQuery+"\n\n");
					insertMemberStmt.executeUpdate(insertQuery);
					
					loginPanel.signup.setEnabled(false);
					signUpPanel.clearAllContents();
					JOptionPane.showMessageDialog(null, "Account Created. Welcome to DB Assignment 2 :)");
					signUpPanel.disablePanel();
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
				}
				catch(Exception ex1)
				{
				      ex1.printStackTrace();
				}
				finally
				{
				      try
				      {
				    	  if(checkNameStmt != null)
				    		  checkNameStmt.close();
				    	  
				    	  if(insertMemberStmt != null)
				    		  insertMemberStmt.close();
				    	  
				    	  if(conn != null)
				            conn.close();
				      }
				      catch(SQLException se)
				      {
				    	  se.printStackTrace();
				      }
				 }
            }
        });
		
		signUpPanel.disablePanel();
	}
}

class ConnectDB
{
	public static Connection openConnection()
	{
		/*Fill this function*/
    	/*implement open  connection */
		try
		{
	         String driverName = "oracle.jdbc.driver.OracleDriver";
	         Class.forName(driverName);
	         String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	         String uname = "test";
	         String pwd = "test";
	         return DriverManager.getConnection(url, uname, pwd);
	    }
	    catch(ClassNotFoundException e)
	    {
	         System.out.println("Class Not Found");
	         e.printStackTrace();
	         return null;
	    }
	    catch(SQLException sqle)
	    {
	         System.out.println("Connection Failed");
	         sqle.printStackTrace();
	         return null;
	    }
		
		//return null;
	}
	public static void closeConnection(Connection conn)
	{
		/*Fill this function*/
    	/*implement close connection */
		try
		{
			  conn.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("connection closing error ");
		}
	}
}

public class Assignment2 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    	MainFrame frame = new MainFrame();
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
