import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class FourthFrame extends JFrame{

	private static final String server = "jdbc:mysql://140.119.19.73:3315/";
	private static final String database = "tuegroup12";
	private static final String url = server + database + "?useSSL=false&characterEncoding=utf-8"; // changeEncoding
	private static final String username = "tuegroup12";
	private static final String password = "sbg6514";
	private JTextArea textArea = new JTextArea();
	private JScrollPane scrollPane;
	private JButton btnNewButton1, btnNewButton2;
	private JTextField startextField;
	private JTextField reviewtextField;
	private JLabel resultTextField;
	private DrinkingShop shop;
	private JLabel titleLabel;
	/**
	 * Create the application.
	 */
	public FourthFrame(DrinkingShop shop) {
		setTitle("現在是飲料時間");
		createTextArea();
		setTextArea(shop);
		createButton();
		setBounds(100, 100, 450, 350);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("評星");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 359, 78, 22);
		getContentPane().add(lblNewLabel);
		
		startextField = new JTextField();
		startextField.setBounds(82, 360, 65, 21);
		getContentPane().add(startextField);
		startextField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("評語");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(157, 359, 78, 22);
		getContentPane().add(lblNewLabel_1);
		
		reviewtextField = new JTextField();
		reviewtextField.setColumns(10);
		reviewtextField.setBounds(235, 360, 235, 21);
		getContentPane().add(reviewtextField);
		
		resultTextField = new JLabel("");
		resultTextField.setHorizontalAlignment(SwingConstants.CENTER);
		resultTextField.setBounds(10, 277, 225, 22);
		getContentPane().add(resultTextField);
		
		titleLabel = new JLabel(shop.getName() + "評論區");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(235, 0, 175, 22);
		getContentPane().add(titleLabel);
		setSize(700, 500);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public void createTextArea() {
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(10, 24, 670, 300);
		textArea.setEditable(false);
		textArea.setBounds(10, 24, 670, 300);
		this.getContentPane().add(scrollPane);
	}
	
	public void setTextArea(DrinkingShop shop) {
		try {
			textArea.setText(shop.reviewInfo());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private void createButton() {
		btnNewButton1 = new JButton("新增評論");
		btnNewButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					Statement stat = conn.createStatement();
					String query;
					query = String.format("INSERT INTO tuegroup12.評論 (店家, 評星, 評語) VALUES ('%s', %s, '%s')", shop.getName()
							, startextField.getText(), reviewtextField.getText());
					stat.execute(query);
					resultTextField.setText("成功新增評論！");
					
				}catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		});
		btnNewButton1.setBounds(395, 405, 101, 29);
		this.getContentPane().add(btnNewButton1);
		
		btnNewButton2 = new JButton("回上一頁");
		btnNewButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SecondFrame.frame.setVisible(true);
				FourthFrame.this.setVisible(false);
			}
		});
		btnNewButton2.setBounds(537, 405, 101, 29);
		this.getContentPane().add(btnNewButton2);
	}
	
	public void setDrinkingShop(DrinkingShop shop) {
		this.shop = shop;
	}
}
