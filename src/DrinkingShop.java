import java.awt.print.Printable;
import java.sql.*;
import javax.swing.JOptionPane;

public abstract class DrinkingShop {

	private String name, telephone, breakDay, operation, address, star, review;
	private Statement statement;

	/*
	 * @constructor
	 */
	public DrinkingShop(String name, String telephone, String breakDay, String operation, String address,
			Statement statement) {
		this.name = name;
		this.telephone = telephone;
		this.breakDay = breakDay;
		this.operation = operation;
		this.address = address;
		this.statement = statement;
	}

	/*
	 * @getter
	 */
	public String getName() {
		return this.name;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public String getBreakDay() {
		return this.breakDay;
	}

	public String getOperation() {
		return this.operation;
	}

	public String getAddress() {
		return this.address;
	}

	public Statement getStatement() {
		return this.statement;
	}

	/*
	 * @basicInfo:return the basic information about the specific drinking shop
	 */
	public final String basicInfo() {
		String message = null;
		message = String.format("店家名稱：%s\n電話號碼：%s\n公休日：%s\n營業時間：%s\n地址：%s\n", this.getName(), this.getTelephone(),
				this.getBreakDay(), this.getOperation(), this.getAddress());
		message += String.format("-".repeat(100)+"\n");
		return message;
	}

	/*
	 * @teaInfo:an abstract method which is defined in each sub-class with different
	 * query commands
	 */
	public abstract String teaInfo(String tea) throws SQLException;

	/*
	 * public void insertReview(int star, String review) { String query = String.
	 * format("INSERT INTO tuegroup12.評論 (店家, 評星, 評語) VALUES ('%s', %d, '%s')",
	 * this.getName(), star, review);
	 * 
	 * try { this.query(query); } catch (SQLException e) { e.printStackTrace(); } }
	 */
	
	public String reviewInfo() throws SQLException {
		String message = null;
		String query = String.format("SELECT * FROM tuegroup12.評論 WHERE 店家 = '%s'",  this.getName());
		try {
			this.statement.execute(query);
			ResultSet result = this.statement.getResultSet();
			message = String.format("%5s%50s\n", "評星", "評語");
			while (result.next()) {
				String review = result.getString("評語");
				if (result.getString("評語").contains("\n")) {
					review = result.getString("評語").replace("\n", "\n                ");
				}
				message += String.format("%5s          %-50s\n", result.getString("評星"), review);
			}
			message += ("-".repeat(100) + "\n");
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Connection Error", JOptionPane.WARNING_MESSAGE);
		}
		return message;
	}
	
	
	
	/*
	 * @ingredientsInfo: to execute a query for ingredients' prices which have M and
	 * L size
	 */
	public String ingredientsInfo() throws SQLException {
		String message = null;
		String query = "SELECT * FROM tuegroup12." + this.getName() + "加料 ORDER BY 中杯加料價格";
		message = ("您可以加以下的這些料：\n");

		try {
			this.statement.execute(query);
			ResultSet result = this.statement.getResultSet();
			message = String.format("%20s%40s%10s%10s\n", "品項", "英文", "中杯加料價格", "大杯加料價格");
			while (result.next()) {
				message += String.format("%20s%40s%10d%10d\n", result.getString("品項"), result.getString("English"),
						result.getInt("中杯加料價格"), result.getInt("大杯加料價格"));
			}
			message += ("-".repeat(100) + "\n");
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Connection Error", JOptionPane.WARNING_MESSAGE);
		}
		return message;
	}

	/*
	 * @specialIngredientsInfo: to execute a query for ingredients' prices which
	 * have only one size
	 */
	public String specialIngredientsInfo() {
		String message = null;
		String query = "SELECT * FROM tuegroup12." + this.getName() + "加料 ORDER BY 加料價格";
		message = ("您可以加以下的這些料：\n");

		try {
			this.statement.execute(query);
			ResultSet result = this.statement.getResultSet();
			message += String.format("%20s%40s%10s\n", "品項", "英文", "加料價格");
			while (result.next()) {
				message += String.format("%20s%40s%10d\n", result.getString("品項"), result.getString("English"),
						result.getInt("加料價格"));
			}
			message += ("-".repeat(100) + "\n");
			result.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return message;
	}

	/*
	 * @hasQueryResult: to know whether the shop has this kind of tea or not
	 */
	public boolean hasQueryResult(String tea) throws SQLException {
		String query = "SELECT * FROM tuegroup12." + this.getName() + " WHERE 茶種=\'" + tea + "\'";
		boolean guard = false;
		try {
			guard = this.statement.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return guard;
	}

	/*
	 * @discountInfo: to execute query for discounts for each DrinkingShop (for
	 * Mr.Liao)
	 */
//	public final String discountInfo() throws SQLException;

}

