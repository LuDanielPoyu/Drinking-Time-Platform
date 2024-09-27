import java.sql.*;
import javax.swing.JOptionPane;

public class ML extends DrinkingShop{
	/*
	 * @ML:constructor for 50Lan,Kebuke,QingXin,MilkShop
	 */
	public ML(String name, String telephone, String breakDay, String operation, String address, Statement statement) {
		super(name, telephone, breakDay, operation, address, statement);
	}

	public String teaInfo(String tea) throws SQLException {
		String message = null;
		String query = "SELECT 品項,English,中杯價格,大杯價格 FROM tuegroup12." + super.getName() + " WHERE 茶種=\'" + tea
				+ "\'ORDER BY 中杯價格";
		message = "飲料查詢結果如下：\n";

		try {
			super.getStatement().execute(query);
			ResultSet result = super.getStatement().getResultSet();
			message += String.format("%20s%30s%10s%10s\n", "品項", "英文", "中杯價格", "大杯價格");
			while (result.next()) {
				message += String.format("%20s%30s%10d%10d\n", result.getString("品項"), result.getString("English"),
						result.getInt("中杯價格"), result.getInt("大杯價格"));
			}
			message += ("-".repeat(100) + "\n");
			result.close();

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Connection Error", JOptionPane.WARNING_MESSAGE);
		}
		return message;
	}
}

