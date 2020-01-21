import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.swing.table.*;
import java.text.SimpleDateFormat;
import java.util.Date;
class Intro extends JFrame implements ActionListener            //Intro Page
{
	JFrame frame;
	JButton b1,b2,b3,b4;
	Intro()
	{
		frame=new JFrame("Intro");
		b1=new JButton("Employee");
		b2=new JButton("Admin");
		b3=new JButton("Exit");
		b1.setBounds(200,110,200,30);
		b2.setBounds(200,150,200,30);
		b3.setBounds(200,190,200,30);
		frame.add(b1);
		frame.add(b2);
		frame.add(b3);
		frame.setSize(600,350);
		frame.setLayout(null);
		frame.setVisible(true);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource().equals(b1))
		{
		new LoginForm();
			frame.setVisible(false);
		}
		else if(ae.getSource().equals(b2))
		{
			new LoginFormAdmin();
			frame.setVisible(false);
		}
		else if(ae.getSource().equals(b3))
		{
			frame.setVisible(false);
		}
		
	}
}
class LoginForm extends JFrame implements ActionListener           //user login
{
	JFrame frame;
	JLabel l1,l2,l3;
	JTextField t1;
	JButton b1,b2;
	String n;
	JPasswordField p1;
	LoginForm()
	{
		frame=new JFrame("LogIn For Employee");
		l1=new JLabel("Employee's Login Form");
		l2=new JLabel("UserNAME");
		l3=new JLabel("PASSWORD");
		t1=new JTextField();
		p1=new JPasswordField();
		b1=new JButton("Enter");
		b2=new JButton("Exit");
		l1.setBounds(220,30,400,30);
		l2.setBounds(140,70,200,30);
		l3.setBounds(140,110,200,30);
		t1.setBounds(270,70,200,30);
		p1.setBounds(270,110,200,30);
		b1.setBounds(220,160,100,30);
		b2.setBounds(220,200,100,30);
		frame.add(l1);
		frame.add(l2);
		frame.add(b2);
		frame.add(t1);
		frame.add(l3);
		frame.add(p1);
		frame.add(b1);
		frame.setSize(600,350);
		frame.setLayout(null);
		frame.setVisible(true);
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae)
	{
		String user=t1.getText();
		String pass=p1.getText();
		if(ae.getSource().equals(b1))
		{
		if(user.equals("") || pass.equals(""))
			JOptionPane.showMessageDialog(null,"Enter all feilds");
		else 
		{
			try{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Connection c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
			Statement s=c.createStatement();
			String ss1="select password from emp where username="+"'"+user+"'";
			ResultSet rs=s.executeQuery(ss1);
			while(rs.next())
			{
				String s1=rs.getString(1);
				n=user;
				if(pass.equals(s1))
				{
					new Page1(n);
					frame.setVisible(false);
					String time = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
					String date = new SimpleDateFormat("dd.MM.yyyy").format(new java.util.Date());
					PreparedStatement ps1=c.prepareStatement("insert into payment values(?,'--',?,?,'--',0)");
					ps1.setString(1,time);
					ps1.setString(2,date);
					ps1.setString(3,n);
					int m=ps1.executeUpdate();
				}
				else
					JOptionPane.showMessageDialog(null,"Enter Correct Details");
			}
			rs.close();
			c.close();
			}
			catch(Exception ee)
			{
			}
		}
		}
		else if(ae.getSource().equals(b2))
		{
			new Intro();
			frame.setVisible(false);
		}
	}
}
class Page1 extends JFrame implements ActionListener               //user Main Menu
{
	JFrame frame;
	JButton b1,b2,b3,b4;
	String u;
	int hr1,min1,sec1;
	long amount=0;
	Page1(String n)
	{
		frame=new JFrame("Employee Main Menu");
		b1=new JButton("Personnel Details");
		b2=new JButton("Change Personnel Details");
		b3=new JButton("Check Payslips");
		b4=new JButton("LOGOUT");
		b1.setBounds(200,110,200,30);
		b2.setBounds(200,150,200,30);
		b3.setBounds(200,190,200,30);
		b4.setBounds(200,230,200,30);
		frame.add(b1);
		frame.add(b2);
		frame.add(b3);
		frame.add(b4);
		frame.setSize(700,500);
		frame.setLayout(null);
		frame.setVisible(true);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		u=n;
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource().equals(b1))
		{
			new Page2(u);
			frame.setVisible(false);
		}
		else if(ae.getSource().equals(b2))
		{
			new Page3(u);
			frame.setVisible(false);
		}
		else if(ae.getSource().equals(b4))
		{
			try{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Connection c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
			String time = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
			String date = new SimpleDateFormat("dd.MM.yyyy").format(new java.util.Date());
			PreparedStatement ps1=c.prepareStatement("update payment set out=? where dates=? and users=?");
			ps1.setString(1,time);
			ps1.setString(2,date);
			ps1.setString(3,u);
			String time1;
			int m=ps1.executeUpdate();
			String pruthvi="select incoming from payment where dates="+"'"+date+"'"+" and users="+"'"+u+"'";
			Statement s=c.createStatement();
			ResultSet rs=s.executeQuery(pruthvi);
			while(rs.next())
			{
				time1=rs.getString(1);
				SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
				Date date1 = format.parse(time1);
				Date date2 = format.parse(time);
				long difference = date2.getTime() - date1.getTime();
				long seconds=difference/1000;
				long p1 = seconds % 60;
				long p2 = seconds / 60;
				long p3 = p2 % 60;
				p2 = p2 / 60;
				String session=p2 + ":" + p3 + ":" + p1;
				if(p2>1)
				{
					amount=p2*42;
				}
				if(p3>30)
				{
					amount=amount+21;
				}
				else if(p3<30)
				{
					amount=amount;
				}
				PreparedStatement ps2=c.prepareStatement("update payment set sessionTime=?,payment=? where dates=? and users=?");
				ps2.setString(1,session);
				ps2.setLong(2,amount);
				ps2.setString(3,date);
				ps2.setString(4,u);
				ps2.executeUpdate();
			}
			c.close();
			ps1.close();
			}
			catch(Exception ee)
			{
			}
			
			new Intro();
			frame.setVisible(false);
		}
		else if(ae.getSource().equals(b3))
		{
			new Pay(u);
		}
		
	}
}
class Page2 extends JFrame implements ActionListener                  //user personnel details
{
	JFrame frame;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8;
	JTextField t1,t2,t3,t4,t5,t6,t7;
	JButton b1,b2;
	String n;
	Page2(String u)
	{
		frame=new JFrame("PersonnelDetails");
		t1=new JTextField(10);
		t2=new JTextField(10);
		t3=new JTextField(10);
		t4=new JTextField(10);
		t5=new JTextField(10);
		t6=new JTextField(10);
		t7=new JTextField(10);
		b1=new JButton("MainMenu");
		b2=new JButton("Display");
		l1=new JLabel("PERSONNEL DETAILS");
		l2=new JLabel("Name");
		l3=new JLabel("Age");
		l4=new JLabel("Job");
		l5=new JLabel("Salary");
		l6=new JLabel("Email");
		l7=new JLabel("pno");
		l8=new JLabel("UserName");
		frame.add(l1);
		frame.add(l2);
		frame.add(t1);
		frame.add(l3);
		frame.add(t2);
		frame.add(l4);
		frame.add(t3);
		frame.add(l5);
		frame.add(t4);
		frame.add(l6);
		frame.add(t5);
		frame.add(l7);
		frame.add(t6);
		frame.add(l8);
		frame.add(t7);
		frame.add(b2);
		frame.add(b1);
		frame.setSize(700,550);
		frame.setLayout(null);
		frame.setVisible(true);
		l1.setBounds(300,30,300,30);
		l2.setBounds(100,60,300,30);
		t1.setBounds(200,60,300,30);
		l3.setBounds(100,100,300,30);
		t2.setBounds(200,100,300,30);
		l4.setBounds(100,140,300,30);
		t3.setBounds(200,140,300,30);
		l5.setBounds(100,180,300,30);
		t4.setBounds(200,180,300,30);
		l6.setBounds(100,220,300,30);
		t5.setBounds(200,220,300,30);
		l7.setBounds(100,260,300,30);
		t6.setBounds(200,260,300,30);
		l8.setBounds(100,300,300,30);
		t7.setBounds(200,300,300,30);
		b2.setBounds(300,340,100,30);
		b1.setBounds(300,380,100,30);
		b1.addActionListener(this);
		b2.addActionListener(this);
		n=u;
		
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource().equals(b2))
		{
		try{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Connection c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
			Statement s=c.createStatement();
			String ss1="select name,age,job,salary,email,pno,username from emp where  username="+"'"+n+"'";
			ResultSet rs=s.executeQuery(ss1);
			while(rs.next())
			{
				t1.setText(rs.getString(1));
				t2.setText(rs.getString(2));
				t3.setText(rs.getString(3));
				t4.setText(rs.getString(4));
				t5.setText(rs.getString(5));
				t6.setText(rs.getString(6));
				t7.setText(rs.getString(7));
				
			}
			rs.close();
			c.close();
			}
			catch(Exception ee)
			{
			}
		}
		else if(ae.getSource().equals(b1))
		{
			new Page1(n);
			frame.setVisible(false);
		}
		
	}
}
class Page3 extends JFrame implements ActionListener               //changing Personel details
{
	
	JFrame frame;
	JButton b1,b2,b3,b4,b5,b6;
	String n;
	Page3(String u)
	{
		frame=new JFrame("Change Details");
		b1=new JButton("Name");
		b2=new JButton("Age");		
		b3=new JButton("Email");
		b4=new JButton("Pno");
		b5=new JButton("Main Menu");
		b6=new JButton("Password");
		frame.setSize(700,500);
		frame.setLayout(null);
		frame.add(b1);
		frame.add(b2);
		frame.add(b3);
		frame.add(b4);
		frame.add(b5);
		frame.add(b6);
		b1.setBounds(200,110,200,30);
		b2.setBounds(200,150,200,30);
		b3.setBounds(200,190,200,30);
		b4.setBounds(200,230,200,30);
		b5.setBounds(200,310,200,30);
		b6.setBounds(200,270,200,30);
		frame.setVisible(true);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		n=u;
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource().equals(b1))
		{
			new Name(n);
			frame.setVisible(false);
		}
		else if(ae.getSource().equals(b2))
		{
			new Age(n);
			frame.setVisible(false);
		}
		else if(ae.getSource().equals(b3))
		{
			new Email(n);
			frame.setVisible(false);
		}
		else if(ae.getSource().equals(b4))
		{
			new Pno(n);
			frame.setVisible(false);
		}
		else if(ae.getSource().equals(b5))
		{
			new Page1(n);
			frame.setVisible(false);
		}
		else if(ae.getSource().equals(b6))
		{
			String u=n;
			new Change(u);
			frame.setVisible(false);
		}
	}
}
class Name extends JFrame implements ActionListener       //changing Name
{
	JFrame frame;
	JLabel l2,l3,l4,l5,l6,l7,l8;
	JTextField t2,t3,t4,t5,t6,t7;
	JButton b1,b2;
	String u;
	Name(String n)
	{
		frame=new JFrame("Change Name");
		t2=new JTextField(10);
		b1=new JButton("MainMenu");
		l2=new JLabel("New Name");
		b2=new JButton("Change ");
		frame.add(l2);
		frame.add(t2);
		frame.add(b2);
		frame.add(b1);
		frame.setSize(600,300);
		frame.setLayout(null);
		frame.setVisible(true);
		l2.setBounds(70,30,300,30);
		t2.setBounds(170,30,300,30);
		b2.setBounds(250,70,100,30);
		b1.setBounds(250,110,100,30);
		b1.addActionListener(this);
		b2.addActionListener(this);
		u=n;
	}
	public void actionPerformed(ActionEvent ae)
	{
		String s1=t2.getText();
		
		if(ae.getSource().equals(b2))
		{
			if(s1.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Enter all feilds");
			}
			else
			{
			try
			{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Connection c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
			PreparedStatement ps=c.prepareStatement("update emp set name=? where username=?");
			ps.setString(1,s1);
			ps.setString(2,u);
			int m=ps.executeUpdate();
			if(m==1)
				JOptionPane.showMessageDialog(null,"updated");
			ps.close();
			c.close();
			}
			catch(Exception ee)
			{
			}
			new Page3(u);
			frame.setVisible(false);
			}
		}
		else
		{
			String n=u;
			frame.setVisible(false);
			new Page1(n);
		}
		
	}
}
class Age extends JFrame implements ActionListener       //changing Age
{
	JFrame frame;
	JLabel l2,l3,l4,l5,l6,l7,l8;
	JTextField t2,t3,t4,t5,t6,t7;
	JButton b1,b2;
	String u;
	Age(String n)
	{
		frame=new JFrame("Change Age");
		t2=new JTextField(10);
		b1=new JButton("MainMenu");
		l2=new JLabel("New Age");
		b2=new JButton("Change ");
		frame.add(l2);
		frame.add(t2);
		frame.add(b2);
		frame.add(b1);
		frame.setSize(600,300);
		frame.setLayout(null);
		frame.setVisible(true);
		l2.setBounds(70,30,300,30);
		t2.setBounds(170,30,300,30);
		b2.setBounds(250,70,100,30);
		b1.setBounds(250,110,100,30);
		b1.addActionListener(this);
		b2.addActionListener(this);
		u=n;
	}
	public void actionPerformed(ActionEvent ae)
	{
		String s1=t2.getText();
		
		if(ae.getSource().equals(b2))
		{
			if(s1.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Enter all feilds");
			}
			else
			{
			try
			{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Connection c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
			PreparedStatement ps=c.prepareStatement("update emp set age=? where username=?");
			ps.setString(1,s1);
			ps.setString(2,u);
			int m=ps.executeUpdate();
			if(m==1)
				JOptionPane.showMessageDialog(null,"updated");
			ps.close();
			c.close();
			}
			catch(Exception ee)
			{
			}
			new Page3(u);
			frame.setVisible(false);
			}
		}
		else
		{
			String n=u;
			frame.setVisible(false);
			new Page1(n);
		}
		
	}
}
class Email extends JFrame implements ActionListener       //changing Email
{
	JFrame frame;
	JLabel l2,l3,l4,l5,l6,l7,l8;
	JTextField t2,t3,t4,t5,t6,t7;
	JButton b1,b2;
	String u;
	Email(String n)
	{
		frame=new JFrame("Change Email");
		t2=new JTextField(10);
		b1=new JButton("MainMenu");
		l2=new JLabel("New Email");
		b2=new JButton("Change ");
		frame.add(l2);
		frame.add(t2);
		frame.add(b2);
		frame.add(b1);
		frame.setSize(600,300);
		frame.setLayout(null);
		frame.setVisible(true);
		l2.setBounds(70,30,300,30);
		t2.setBounds(170,30,300,30);
		b2.setBounds(250,70,100,30);
		b1.setBounds(250,110,100,30);
		b1.addActionListener(this);
		b2.addActionListener(this);
		u=n;
	}
	public void actionPerformed(ActionEvent ae)
	{
		String s1=t2.getText();
		
		if(ae.getSource().equals(b2))
		{
			if(s1.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Enter all feilds");
			}
			else
			{
			try
			{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Connection c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
			PreparedStatement ps=c.prepareStatement("update emp set email=? where username=?");
			ps.setString(1,s1);
			ps.setString(2,u);
			int m=ps.executeUpdate();
			if(m==1)
				JOptionPane.showMessageDialog(null,"updated");
			ps.close();
			c.close();
			}
			catch(Exception ee)
			{
			}
			new Page3(u);
			frame.setVisible(false);
			}
		}
		else
		{
			String n=u;
			frame.setVisible(false);
			new Page1(n);
		}
		
	}
}
class Pno extends JFrame implements ActionListener       //changing Pno
{
	JFrame frame;
	JLabel l2,l3,l4,l5,l6,l7,l8;
	JTextField t2,t3,t4,t5,t6,t7;
	JButton b1,b2;
	String u;
	Pno(String n)
	{
		frame=new JFrame("Change Pno");
		t2=new JTextField(10);
		b1=new JButton("MainMenu");
		l2=new JLabel("New Pno");
		b2=new JButton("Change ");
		frame.add(l2);
		frame.add(t2);
		frame.add(b2);
		frame.add(b1);
		frame.setSize(600,300);
		frame.setLayout(null);
		frame.setVisible(true);
		l2.setBounds(70,30,300,30);
		t2.setBounds(170,30,300,30);
		b2.setBounds(250,70,100,30);
		b1.setBounds(250,110,100,30);
		b1.addActionListener(this);
		b2.addActionListener(this);
		u=n;
	}
	public void actionPerformed(ActionEvent ae)
	{
		String s1=t2.getText();
		
		if(ae.getSource().equals(b2))
		{
			if(s1.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Enter all feilds");
			}
			else
			{
			try
			{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Connection c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
			PreparedStatement ps=c.prepareStatement("update emp set pno=? where username=?");
			ps.setString(1,s1);
			ps.setString(2,u);
			int m=ps.executeUpdate();
			if(m==1)
				JOptionPane.showMessageDialog(null,"updated");
			ps.close();
			c.close();
			}
			catch(Exception ee)
			{
			}
			new Page3(u);
			frame.setVisible(false);
			}
		}
		else
		{
			String n=u;
			frame.setVisible(false);
			new Page1(n);
		}
		
	}
}
class Change extends JFrame implements ActionListener          //password change
{
	JFrame frame;
	JLabel l2,l3,l4,l5,l6,l7,l8;
	JTextField t2,t3,t4,t5,t6,t7;
	JButton b1,b2;
	String n;
	Change(String u)
	{
		frame=new JFrame("Change Password");
		t2=new JTextField(10);
		b1=new JButton("MainMenu");
		l2=new JLabel("New Password");
		b2=new JButton("Change ");
		frame.add(l2);
		frame.add(t2);
		frame.add(b2);
		frame.add(b1);
		frame.setSize(600,300);
		frame.setLayout(null);
		frame.setVisible(true);
		l2.setBounds(70,30,300,30);
		t2.setBounds(170,30,300,30);
		b2.setBounds(250,70,100,30);
		b1.setBounds(250,110,100,30);
		b1.addActionListener(this);
		b2.addActionListener(this);
		n=u;
	}
	public void actionPerformed(ActionEvent ae)
	{
		String s1=t2.getText();
		
		if(ae.getSource().equals(b2))
		{
			if(s1.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Enter all feilds");
			}
			else
			{
			try
			{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Connection c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
			PreparedStatement ps=c.prepareStatement("update emp set password=? where username=?");
			ps.setString(1,s1);
			ps.setString(2,n);
			int m=ps.executeUpdate();
			if(m==1)
				JOptionPane.showMessageDialog(null,"updated");
			ps.close();
			c.close();
			}
			catch(Exception ee)
			{
			}
			new Page1(n);
			frame.setVisible(false);
			}
		}
		else
		{
			frame.setVisible(false);
			new Page1(n);
		}
		
	}
}
class Pay         //user payslips
{
	Pay(String u)
	 {
		 String n=u;
		Vector columnNames = new Vector();
		Vector data = new Vector();
		JPanel p=new JPanel();
		try
		{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
			String sql = "select incoming,out,dates,sessiontime,payment from payment"+" where users="+"'"+n+"'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			for (int i = 1; i <= columns; i++)
			{
				columnNames.addElement( md.getColumnName(i) );
			}
			while (rs.next())
			{
				Vector row = new Vector(columns);
				for (int i = 1; i <= columns; i++)
				{
					row.addElement( rs.getObject(i) );
				}
				data.addElement( row );
			}
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		JTable table = new JTable(data, columnNames);
		TableColumn col;
		for (int i = 0; i < table.getColumnCount(); i++)
		{
			col = table.getColumnModel().getColumn(i);
			col.setMaxWidth(370);
		}
		JScrollPane scrollPane = new JScrollPane( table );
		p.add( scrollPane );
		JFrame f=new JFrame();
		f.add(p);
		f.setSize(600,550);
		f.setVisible(true);
		}
 }
class LoginFormAdmin extends JFrame implements ActionListener           //Admin login
{
	JFrame frame;
	JLabel l1,l2,l3;
	JTextField t1;
	JButton b1;
	JPasswordField p1;
	LoginFormAdmin()
	{
		frame=new JFrame("LogIn For Admin");
		l1=new JLabel("Admin's Login Form");
		l2=new JLabel("UserNAME");
		l3=new JLabel("PASSWORD");
		t1=new JTextField();
		p1=new JPasswordField();
		b1=new JButton("Enter");
		l1.setBounds(220,30,400,30);
		l2.setBounds(140,70,200,30);
		l3.setBounds(140,110,200,30);
		t1.setBounds(270,70,200,30);
		p1.setBounds(270,110,200,30);
		b1.setBounds(220,160,100,30);
		frame.add(l1);
		frame.add(l2);
		frame.add(t1);
		frame.add(l3);
		frame.add(p1);
		frame.add(b1);
		frame.setSize(600,350);
		frame.setLayout(null);
		frame.setVisible(true);
		b1.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae)            
	{
		String user=t1.getText();
		String pass=p1.getText();
		if(ae.getSource().equals(b1))
		{
		if(user.equals("") || pass.equals(""))
			JOptionPane.showMessageDialog(null,"Enter all feilds");
		else 
		{
			try{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Connection c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
			Statement s=c.createStatement();
			String ss1="select password from admin where username="+"'"+user+"'";
			ResultSet rs=s.executeQuery(ss1);
			while(rs.next())
			{
				String s1=rs.getString(1);
				if(pass.equals(s1))
				{
					new A_Page1();
					frame.setVisible(false);
				}
				else
		{
			JOptionPane.showMessageDialog(null,"Not An Admin");
		}
			}
			rs.close();
			c.close();
			}
			catch(Exception ee)
			{
			}
		}
		}
	}
}
class A_Page1 extends JFrame implements ActionListener            //admin main menu
{
	JFrame frame;
	JButton b1,b2,b3,b4,b5,b6,b7;
	A_Page1()
	{
		frame=new JFrame("Admin Main Menu");
		b1=new JButton("Add Employee");
		b5=new JButton("Delete Employee");
		b2=new JButton("Employee Details");
		b3=new JButton("Payslips");
		b6=new JButton("Search Payslips");
		b7=new JButton("Search Employee");
		b4=new JButton("LOGOUT");
		b1.setBounds(200,110,200,30);
		b5.setBounds(200,150,200,30);
		b2.setBounds(200,190,200,30);
		b3.setBounds(200,230,200,30);
		b6.setBounds(200,270,200,30);
		b7.setBounds(200,310,200,30);
		b4.setBounds(200,350,200,30);
		frame.add(b1);
		frame.add(b2);
		frame.add(b3);
		frame.add(b4);
		frame.add(b5);
		frame.add(b6);
		frame.add(b7);
		frame.setSize(600,500);
		frame.setLayout(null);
		frame.setVisible(true);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource().equals(b1))
		{
			new A_Page2();
			frame.setVisible(false);
		}
		else if(ae.getSource().equals(b5))
		{
			new A_Page4();
			frame.setVisible(false);
		}
		else if(ae.getSource().equals(b2))
		{
			new A_Page3();
			//frame.setVisible(false);
		}
		else if(ae.getSource().equals(b3))
		{
			new A_Page5();
			//frame.setVisible(false);
		}
		else if(ae.getSource().equals(b4))
		{
			new Intro();
			frame.setVisible(false);
		}
		else if(ae.getSource().equals(b6))
		{
			new A_Page6();
			frame.setVisible(false);
		}
		else if(ae.getSource().equals(b7))
		{
			new A_Page7();
			frame.setVisible(false);
		}
		
	}
}
class A_Page2 extends JFrame implements ActionListener                //Admin adding employee
{
	JFrame frame;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10;
	JTextField t1,t2,t3,t4,t5,t6,t7,t8,t9;
	JButton b1,b2;
	A_Page2()
	{
		frame=new JFrame("Adding Employee");
		t1=new JTextField(10);
		t2=new JTextField(10);
		t3=new JTextField(10);
		t4=new JTextField(10);
		t5=new JTextField(10);
		t6=new JTextField(10);
		t7=new JTextField(10);
		t8=new JTextField(10);
		t9=new JTextField(10);
		b1=new JButton("Add employee");
		b2=new JButton("Main menu");
		l1=new JLabel("Employee DETAILS");
		l2=new JLabel("Name");
		l3=new JLabel("Age");
		l4=new JLabel("Job");
		l5=new JLabel("salary");
		l6=new JLabel("email");
		l7=new JLabel("pno");
		l8=new JLabel("username");
		l9=new JLabel("password");
		l10=new JLabel("ID");
		frame.add(l1);
		frame.add(l2);
		frame.add(t1);
		frame.add(l3);
		frame.add(t2);
		frame.add(l4);
		frame.add(t3);
		frame.add(l5);
		frame.add(t4);
		frame.add(l6);
		frame.add(t5);
		frame.add(l7);
		frame.add(t6);
		frame.add(l8);
		frame.add(t7);
		frame.add(l9);
		frame.add(t8);
		frame.add(b1);
		frame.add(b2);
		frame.add(l10);
		frame.add(t9);
		frame.setSize(700,600);
		frame.setLayout(null);
		frame.setVisible(true);
		l1.setBounds(300,30,300,30);
		l2.setBounds(100,60,300,30);
		t1.setBounds(200,60,300,30);
		l3.setBounds(100,100,300,30);
		t2.setBounds(200,100,300,30);
		l4.setBounds(100,140,300,30);
		t3.setBounds(200,140,300,30);
		l5.setBounds(100,180,300,30);
		t4.setBounds(200,180,300,30);
		l6.setBounds(100,220,300,30);
		t5.setBounds(200,220,300,30);
		l7.setBounds(100,260,300,30);
		t6.setBounds(200,260,300,30);
		l8.setBounds(100,300,300,30);
		t7.setBounds(200,300,300,30);
		l9.setBounds(100,340,300,30);
		t8.setBounds(200,340,300,30);
		l10.setBounds(100,380,300,30);
		t9.setBounds(200,380,300,30);
		b1.setBounds(300,420,100,30);
		b2.setBounds(300,460,100,30);
		b1.addActionListener(this);
		b2.addActionListener(this);
		
	}
	public void actionPerformed(ActionEvent ae)
	{
		try{
			
		if(ae.getSource().equals(b1))
		{
			String s1=t1.getText();
				int s2=Integer.parseInt(t2.getText());
				String s3=t3.getText();
				int s4=Integer.parseInt(t4.getText());
				String s5=t5.getText();
				int s6=Integer.parseInt(t6.getText());
				String s7=t7.getText();
				String s8=t8.getText();
				int s9=Integer.parseInt(t9.getText());
			try
			{
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
				Connection c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
				PreparedStatement ps=c.prepareStatement("insert into emp values(?,?,?,?,?,?,?,?,?)");
				ps.setInt(1,s9);
				ps.setString(2,s1);
				ps.setInt(3,s2);
				ps.setString(4,s3);
				ps.setInt(5,s4);
				ps.setString(6,s5);
				ps.setInt(7,s6);
				ps.setString(8,s8);
				ps.setString(9,s5);
				int m=ps.executeUpdate();
				JOptionPane.showMessageDialog(null,"successfully registered");
				if(m==1)
				{
					ps.close();
					c.close();
					frame.setVisible(false);
					new A_Page2();
				}
			}
			catch(Exception aa){}
		}
		else if(ae.getSource().equals(b2))
		{
			new A_Page1();
			frame.setVisible(false);
		}
		}
		catch(Exception ee)
		{
			new A_Page1();
			frame.setVisible(false);
		}
	}
}
 class A_Page3         //Admin  displaying employee details
 {
	 A_Page3()
	 {
		Vector columnNames = new Vector();
		Vector data = new Vector();
		JPanel p=new JPanel();
		try
		{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
			String sql = "select id,name,age,job,salary,email,pno from emp";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			for (int i = 1; i <= columns; i++)
			{
				columnNames.addElement( md.getColumnName(i) );
			}
			while (rs.next())
			{
				Vector row = new Vector(columns);
				for (int i = 1; i <= columns; i++)
				{
					row.addElement( rs.getObject(i) );
				}
				data.addElement( row );
			}
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		JTable table = new JTable(data, columnNames);
		TableColumn col;
		for (int i = 0; i < table.getColumnCount(); i++)
		{
			col = table.getColumnModel().getColumn(i);
			col.setMaxWidth(370);
		}
		JScrollPane scrollPane = new JScrollPane( table );
		p.add( scrollPane );
		JFrame f=new JFrame();
		f.add(p);
		f.setSize(600,550);
		f.setVisible(true);
		}
 }
class A_Page4 extends JFrame implements ActionListener          //delete employee by admin
{
	JFrame frame;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8;
	JTextField t1,t2,t3,t4,t5,t6,t7;
	JButton b1,b2;
	A_Page4()
	{
		frame=new JFrame("Delete Employee");
		t1=new JTextField(10);
		b1=new JButton("MainMenu");
		l1=new JLabel("UserName");
		b2=new JButton("Delete");
		frame.add(l1);
		frame.add(t1);
		frame.add(b2);
		frame.add(b1);
		frame.setSize(600,300);
		frame.setLayout(null);
		frame.setVisible(true);
		l1.setBounds(100,60,300,30);
		t1.setBounds(200,60,300,30);
		b2.setBounds(250,140,100,30);
		b1.setBounds(250,180,100,30);
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae)
	{
		String s1=t1.getText();
		
		if(ae.getSource().equals(b2))
		{
			if(s1.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Enter all feilds");
			}
			else
			{
			try
			{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Connection c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
			PreparedStatement ps=c.prepareStatement("delete from emp where name=?");
			ps.setString(1,s1);
			int m=ps.executeUpdate();
			if(m==1)
				JOptionPane.showMessageDialog(null,"Deleted");
			ps.close();
			c.close();
			}
			catch(Exception ee)
			{
			}
			frame.setVisible(false);
			new A_Page4();
			}
		}
		else
		{
			frame.setVisible(false);
			new A_Page1();
		}
		
	}
}
class A_Page5              //Admin payslips
{
	 A_Page5()
	 {
		Vector columnNames = new Vector();
		Vector data = new Vector();
		JPanel p=new JPanel();
		try
		{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
			String sql = "select incoming,out,dates,users,sessionTime,payment from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			for (int i = 1; i <= columns; i++)
			{
				columnNames.addElement( md.getColumnName(i) );
			}
			while (rs.next())
			{
				Vector row = new Vector(columns);
				for (int i = 1; i <= columns; i++)
				{
					row.addElement( rs.getObject(i) );
				}
				data.addElement( row );
			}
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		JTable table = new JTable(data, columnNames);
		TableColumn col;
		for (int i = 0; i < table.getColumnCount(); i++)
		{
			col = table.getColumnModel().getColumn(i);
			col.setMaxWidth(500);
		}
		JScrollPane scrollPane = new JScrollPane( table );
		p.add( scrollPane );
		JFrame f=new JFrame();
		f.add(p);
		f.setSize(600,550);
		f.setVisible(true);
		}
}
class A_Page6 extends JFrame implements ActionListener          //searching Payslips
{
	JFrame frame;
	JLabel l2,l3,l4,l5,l6,l7,l8;
	JTextField t2,t3,t4,t5,t6,t7;
	JButton b1,b2;
	String n;
	A_Page6()
	{
		frame=new JFrame("Search Payslips");
		t2=new JTextField(10);
		b1=new JButton("MainMenu");
		l2=new JLabel("Username");
		b2=new JButton("Search");
		frame.add(l2);
		frame.add(t2);
		frame.add(b2);
		frame.add(b1);
		frame.setSize(600,300);
		frame.setLayout(null);
		frame.setVisible(true);
		l2.setBounds(70,30,300,30);
		t2.setBounds(170,30,300,30);
		b2.setBounds(250,70,100,30);
		b1.setBounds(250,110,100,30);
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae)
	{
		String u=t2.getText();
		
		if(ae.getSource().equals(b2))
		{
			if(u.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Enter all feilds");
			}
			else
			{
				Vector columnNames = new Vector();
				Vector data = new Vector();
				JPanel p=new JPanel();
				try
				{
					DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
					String sql = "select incoming,out,dates,sessiontime,payment from payment"+" where users="+"'"+u+"'";
					Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery( sql );
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			for (int i = 1; i <= columns; i++)
			{
				columnNames.addElement( md.getColumnName(i) );
			}
			while (rs.next())
			{
				Vector row = new Vector(columns);
				for (int i = 1; i <= columns; i++)
				{
					row.addElement( rs.getObject(i) );
				}
				data.addElement( row );
			}
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		JTable table = new JTable(data, columnNames);
		TableColumn col;
		for (int i = 0; i < table.getColumnCount(); i++)
		{
			col = table.getColumnModel().getColumn(i);
			col.setMaxWidth(370);
		}
		JScrollPane scrollPane = new JScrollPane( table );
		p.add( scrollPane );
		JFrame f=new JFrame();
		f.add(p);
		f.setSize(600,550);
		f.setVisible(true);
		}
			}
		else if(ae.getSource().equals(b1))
		{
			new A_Page1();
			frame.setVisible(false);
		}
	}
}
class A_Page7 extends JFrame implements ActionListener                  //seraching user details
{
	JFrame frame;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9;
	JTextField t1,t2,t3,t4,t5,t6,t7,t8;
	JButton b1,b2;
	String n;
	A_Page7()
	{
		frame=new JFrame("Employee Details");
		t1=new JTextField(10);
		t2=new JTextField(10);
		t3=new JTextField(10);
		t4=new JTextField(10);
		t5=new JTextField(10);
		t6=new JTextField(10);
		t7=new JTextField(10);
		t8=new JTextField(10);
		b1=new JButton("MainMenu");
		b2=new JButton("Search");
		l1=new JLabel("PERSONNEL DETAILS");
		l2=new JLabel("Name");
		l3=new JLabel("Age");
		l4=new JLabel("Job");
		l5=new JLabel("Salary");
		l6=new JLabel("Email");
		l7=new JLabel("pno");
		l8=new JLabel("Id");
		l9=new JLabel("UserName");
		frame.add(l1);
		frame.add(l2);
		frame.add(t1);
		frame.add(l3);
		frame.add(t2);
		frame.add(l4);
		frame.add(t3);
		frame.add(l5);
		frame.add(t4);
		frame.add(l6);
		frame.add(t5);
		frame.add(l7);
		frame.add(t6);
		frame.add(l8);
		frame.add(t7);
		frame.add(b2);
		frame.add(b1);
		frame.add(t8);
		frame.add(l9);
		frame.setSize(700,550);
		frame.setLayout(null);
		frame.setVisible(true);
		l9.setBounds(100,0,300,30);
		t8.setBounds(200,0,300,30);
		l1.setBounds(300,30,300,30);
		l2.setBounds(100,60,300,30);
		t1.setBounds(200,60,300,30);
		l3.setBounds(100,100,300,30);
		t2.setBounds(200,100,300,30);
		l4.setBounds(100,140,300,30);
		t3.setBounds(200,140,300,30);
		l5.setBounds(100,180,300,30);
		t4.setBounds(200,180,300,30);
		l6.setBounds(100,220,300,30);
		t5.setBounds(200,220,300,30);
		l7.setBounds(100,260,300,30);
		t6.setBounds(200,260,300,30);
		l8.setBounds(100,300,300,30);
		t7.setBounds(200,300,300,30);
		b2.setBounds(300,340,100,30);
		b1.setBounds(300,380,100,30);
		b1.addActionListener(this);
		b2.addActionListener(this);
		
	}
	public void actionPerformed(ActionEvent ae)
	{
		String s1=t8.getText();
		
		if(ae.getSource().equals(b2))
		{
			if(s1.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Enter all feilds");
			}
			else
			{
		try{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Connection c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
			Statement s=c.createStatement();
			String ss1="select name,age,job,salary,email,pno,id from emp where  username="+"'"+s1+"'";
			ResultSet rs=s.executeQuery(ss1);
			while(rs.next())
			{
				t1.setText(rs.getString(1));
				t2.setText(rs.getString(2));
				t3.setText(rs.getString(3));
				t4.setText(rs.getString(4));
				t5.setText(rs.getString(5));
				t6.setText(rs.getString(6));
				t7.setText(rs.getString(7));
				
			}
			rs.close();
			c.close();
			}
			catch(Exception ee)
			{
			}
		}
		}
		else if(ae.getSource().equals(b1))
		{
			new A_Page1();
			frame.setVisible(false);
		}
		
	}
}

class A
{
	public static void main(String p[])
	{
		new Intro();
	}
}

