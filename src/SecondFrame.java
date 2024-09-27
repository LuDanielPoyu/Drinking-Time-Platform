import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

public class SecondFrame extends JFrame {
	private JLabel storeLabel;
	private JButton aButton, bButton, cButton, dButton, eButton, fButton, gButton, hButton;
	private JPanel storePanel, allPanel;
	protected static ThirdFrame frame; // it can't be defined as Parent Class!
	private Statement statement;
	private ArrayList<DrinkingShop> shopList;
	private ArrayList<JButton> buttonGroup = new ArrayList<>();
	private String tea;

	private static final String server = "jdbc:mysql://140.119.19.73:3315/";
	private static final String database = "tuegroup12";
	private static final String url = server + database + "?useSSL=false&characterEncoding=utf-8"; // changeEncoding
	private static final String username = "tuegroup12";
	private static final String password = "sbg6514";

	public SecondFrame(String tea) {
		this.tea = tea;
		createButton();
		createButtonGroup();
		try {
			setButton();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		createLabel();
		createPanel();

		setTitle("現在是飲料時間");
		setSize(500, 300);
	}

	public void createButton() {
		aButton = new JButton("可不可熟成紅茶");
		aButton.setFont(new Font("Serif", Font.BOLD, 15));
		bButton = new JButton("政大茶亭");
		bButton.setFont(new Font("Serif", Font.BOLD, 15));
		cButton = new JButton("迷客夏");
		cButton.setFont(new Font("Serif", Font.BOLD, 15));
		dButton = new JButton("50嵐");
		dButton.setFont(new Font("Serif", Font.BOLD, 15));
		eButton = new JButton("酪農");
		eButton.setFont(new Font("Serif", Font.BOLD, 15));
		fButton = new JButton("龍角");
		fButton.setFont(new Font("Serif", Font.BOLD, 15));
		gButton = new JButton("清心福全");
		gButton.setFont(new Font("Serif", Font.BOLD, 15));
		hButton = new JButton("CowBanana牛香蕉");
		hButton.setFont(new Font("Serif", Font.BOLD, 15));
	}

	public void createButtonGroup() {
		buttonGroup.add(aButton);
		buttonGroup.add(bButton);
		buttonGroup.add(cButton);
		buttonGroup.add(dButton);
		buttonGroup.add(eButton);
		buttonGroup.add(fButton);
		buttonGroup.add(gButton);
		buttonGroup.add(hButton);
	}

	public void unableButton(ArrayList<String> matchList) {
		for (JButton button : this.buttonGroup) {
			if (!matchList.contains(button.getText())) {
				button.setEnabled(false);
			}
		}
	}

	public void setButton() throws SQLException {
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			this.statement = connection.createStatement();
			this.shopList = new ShopList(this.statement).getShopList();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (JButton button : this.buttonGroup) {
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					DrinkingShop drinkingShop = null;
					for (DrinkingShop shop : SecondFrame.this.shopList) {
						if (button.getText().equals(shop.getName())) {
							drinkingShop = shop;
							break;
						}
					}
					SecondFrame.this.frame = new ThirdFrame(SecondFrame.this.tea);
					
					frame.setDrinkingShop(drinkingShop);
					frame.setLabel(drinkingShop.getName());
					try {
						if (drinkingShop.getName().equals("50嵐") || drinkingShop.getName().equals("清心福全")) {
							frame.setTextArea(drinkingShop.basicInfo() + drinkingShop.teaInfo(tea)
									+ drinkingShop.ingredientsInfo());
						} else {
							frame.setTextArea(drinkingShop.basicInfo() + drinkingShop.teaInfo(tea)
									+ drinkingShop.specialIngredientsInfo());
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

					frame.setResizable(false);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
					SecondFrame.this.setVisible(false);
				}
			});
		}
	}

	public void createLabel() {
		storeLabel = new JLabel("以下是有「」品項的店家，查看菜單麻煩請點進去");
		storeLabel.setFont(new Font("Serif", Font.BOLD, 20));
	}

	public void setLabel(String item) {
		storeLabel.setText("以下是有「" + item + "」品項的店家，查看菜單麻煩請點進去");
		storeLabel.setFont(new Font("Serif", Font.BOLD, 18));
	}

	public void createPanel() {
		storePanel = new JPanel(new GridLayout(4, 2));
		for (JButton button : this.buttonGroup) {
			storePanel.add(button);
		}
		allPanel = new JPanel(new GridLayout(2, 1));
		allPanel.add(storeLabel);
		allPanel.add(storePanel);
		add(allPanel);
	}
}
