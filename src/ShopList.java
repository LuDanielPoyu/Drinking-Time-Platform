import java.sql.*;
import java.util.ArrayList;

public class ShopList {

	private ArrayList<DrinkingShop> shopList = new ArrayList<DrinkingShop>();
	private ArrayList<String> shopNameList = new ArrayList<String>();

	public ShopList(Statement statement) {
		DrinkingShop D50Lan = new ML("50嵐", "(02)8661-7571", "無", "10:00-22:00", "台北市文山區指南路二段63號", statement);
		DrinkingShop Kebuke = new ML("可不可熟成紅茶", "(02)2936-3030", "無", "10:00-21:30", "台北市文山區指南路二段91號", statement);
		DrinkingShop QingXin = new ML("清心福全", "(02)8661-0017", "無", "09:30-22:15", "台北市文山區指南路二段61號", statement);
		DrinkingShop MilkShop = new ML("迷客夏", "(02)2936-3629", "無", "10:30-21:30", "台北市文山區指南路二段59號", statement);
		DrinkingShop CowBanana = new SML("CowBanana牛香蕉", "(02)2234-0101", "無", "08:00-17:00(平日), 09:00-17:00(假日)",
				"台北市文山區萬壽路5號", statement);
		DrinkingShop Dragon = new SML("龍角", "(02)8661-7729", "無", "11:00-22:00(平日), 11:00-21:00(假日)", "台北市文山區指南路33號",
				statement);
		DrinkingShop TeaShop = new L("政大茶亭", "(02)8661-0884(一店), (02)2936-6420(二店)", "週六、週日(一店), 無(二店)",
				"10:00-21:30(一店), 10:00-21:00(二店)", "台北市文山區指南路二段12號(一店), 台北市文山區指南路二段119巷117號(二店)", statement);
		DrinkingShop Farmer = new L("酪農", "0919272932", "無", "10:00-20:00", "台北市文山區指南路二段58號", statement);
		
		this.shopList.add(D50Lan);
		this.shopList.add(Kebuke);
		this.shopList.add(QingXin);
		this.shopList.add(MilkShop);
		this.shopList.add(CowBanana);
		this.shopList.add(Dragon);
		this.shopList.add(TeaShop);
		this.shopList.add(Farmer);
		
		for (DrinkingShop shop : this.shopList) {
			this.shopNameList.add(shop.getName());
		}
	}

	public ArrayList<DrinkingShop> getShopList() {
		return this.shopList;
	}

	public ArrayList<String> getNameList() {
		return this.shopNameList;
	}
}