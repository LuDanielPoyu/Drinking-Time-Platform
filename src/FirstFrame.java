import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

public class FirstFrame extends JFrame {
	private JPanel drinkPanel, overallPanel;
	private JLabel nameLabel, questionLabel, optionLabel;
	private JComboBox<String> drinkCombo;
	private ArrayList<String> nameList;
	private Statement statement;
	private JButton searchButton;
	private SecondFrame frame;

	private static final String server = "jdbc:mysql://140.119.19.73:3315/";
	private static final String database = "tuegroup12";
	private static final String url = server + database + "?useSSL=false&characterEncoding=utf-8"; // changeEncoding
	private static final String username = "tuegroup12";
	private static final String password = "sbg6514";

	public FirstFrame() {

		createLabel();
		createDrinkCombo();
		createButton();
		createPanel();

		setTitle("現在是飲料時間");
		setSize(500, 300);
	}

	public void createLabel() {
		nameLabel = new JLabel("現在是飲料時間", JLabel.CENTER);
		nameLabel.setFont(new Font("Serif", Font.BOLD, 30));
		questionLabel = new JLabel("今天想喝什麼？");
		questionLabel.setFont(new Font("Serif", Font.PLAIN, 20));
	}

	public void createButton() {
		searchButton = new JButton("搜尋");
		searchButton.setFont(new Font("Serif", Font.BOLD, 30));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) drinkCombo.getSelectedItem();
				try {
					ArrayList<String> matchShop = matchShop(selectedItem);
					frame = new SecondFrame(selectedItem);
					frame.setLabel(selectedItem);
					frame.unableButton(matchShop);
					frame.setResizable(false);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
					FirstFrame.this.setVisible(false);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public void createDrinkCombo() {
		drinkCombo = new JComboBox<String>();
		drinkCombo.addItem("紅茶");
		drinkCombo.addItem("綠茶");
		drinkCombo.addItem("奶茶");
		drinkCombo.addItem("鮮奶茶");
		drinkCombo.addItem("烏龍茶");
		drinkCombo.addItem("青茶");
		drinkCombo.addItem("牛奶");
		drinkCombo.addItem("豆奶");
		drinkCombo.addItem("冬瓜茶");
		drinkCombo.addItem("冰沙");
		drinkCombo.addItem("醋");
		drinkCombo.addItem("可可");
		drinkCombo.addItem("咖啡");
		drinkCombo.addItem("普洱茶");
		drinkCombo.addItem("果汁");
		drinkCombo.addItem("其它");
		drinkCombo.setFont(new Font("Serif", Font.PLAIN, 15));
	}
//python team27.py wordle-answers-alphabetical.txt tests.txt team27_first.txt
	public void createPanel() {
		drinkPanel = new JPanel(new GridLayout(1, 2));
		drinkPanel.add(questionLabel);
		drinkPanel.add(drinkCombo);
		overallPanel = new JPanel(new GridLayout(3, 1));
		overallPanel.add(nameLabel);
		overallPanel.add(drinkPanel);
		overallPanel.add(searchButton);
		add(overallPanel);
	}

	@SuppressWarnings("finally")
	public ArrayList<String> matchShop(String item) throws SQLException {
		ArrayList<String> matchShop = new ArrayList<String>();
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			this.statement = connection.createStatement();
			this.nameList = new ShopList(this.statement).getNameList();
			for (String shop : nameList) {
				int count = 0;
				String query = String.format("SELECT COUNT(*) FROM tuegroup12.%s WHERE 茶種 = '%s' ", shop, item);
				this.statement.execute(query);
				ResultSet result = this.statement.getResultSet();
				while (result.next()) {
					count = result.getInt("COUNT(*)");
				}
				if (count > 0) {
					matchShop.add(shop);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return matchShop;
		}

	}
}
