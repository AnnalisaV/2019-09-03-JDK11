package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.CouplePortions;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	/**
	 * Lista di portion_name di cui ci sia almeno una volta una caloria < al parametro passato
	 * @param calories
	 * @return
	 */
	public List<String> portionName(int calories){
		String sql="SELECT DISTINCT portion_display_name as pname " + 
				"FROM `portion` " + 
				"WHERE calories<? "; 
		
		List<String> lista= new ArrayList<>(); 
		try {
			Connection conn = DBConnect.getConnection() ;
            PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, calories);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				lista.add(new String(res.getString("pname"))); 
			}
			
			conn.close();
			return lista ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
		
	}
	
	public List<CouplePortions> getCouples(){
		String sql="SELECT p1.portion_display_name, p2.portion_display_name, COUNT(DISTINCT p1.food_code) AS peso " + 
				"FROM `portion` p1, `portion` p2 " + 
				"WHERE p1.food_code=p2.food_code AND " + 
				"p1.portion_display_name != p2.portion_display_name " + 
				"GROUP BY p1.portion_display_name, p2.portion_display_name"; 
		List<CouplePortions> lista= new ArrayList<>(); 
		
		try {
			Connection conn = DBConnect.getConnection() ;
            PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				lista.add(new CouplePortions(res.getString("p1.portion_display_name"), 
						res.getString("p2.portion_display_name"), res.getInt("peso"))); 
			}
			
			conn.close();
			return lista ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
	}
}
