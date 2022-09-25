/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.pro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Anuj
 */
public class MonthlySalesUpdate {
    static final String QUERY = "SELECT  saleid , INID ,Cid ,Customer_Name ,Total_Qty ,Total_Bill , Status  , Balance, purchaseDate"+" FROM  sales";
   static final String selectSales = "SELECT idmonthlySales,totalSales"+" FROM monthlysales";
   public static void main(String[] args) throws ClassNotFoundException {
      // Open a connection
      String date = null,currentDate = null;
      int i=0;
      Date currentMonth = new Date();
      //LocalDate localDate = currentMonth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      //int monthNumber = localDate.getMonthValue();  
      
      Class.forName("com.mysql.cj.jdbc.Driver");
      try(
         Statement stmt = db.mycon().createStatement();
         Statement salesstmt = db.mycon().createStatement();
         PreparedStatement psmt = db.mycon().prepareStatement("insert into monthlysales(totalSales,salesMonth) "+"values(? , ?)");
         PreparedStatement updateSales = db.mycon().prepareStatement("update monthlysales set totalsales=? where salesMonth =?");
              
              
         ResultSet rs = stmt.executeQuery(QUERY);
         ResultSet salesRs = salesstmt.executeQuery(selectSales);    
      ) {		      
          
         
         while(rs.next()){
            //Display values
           // System.out.print("ID: " + rs.getInt("saleid"));
            //System.out.print(", Invoice ID: " + rs.getInt("INID"));
           // System.out.print(", Name: " + rs.getString("Customer_Name"));
           // System.out.println(", Status: " + rs.getString("Status"));
            System.out.println(", Status: " + rs.getTimestamp("purchaseDate"));
            System.out.println(", Status: " + rs.getDate("purchaseDate"));
            date =  new SimpleDateFormat("MMMM").format(rs.getDate("purchaseDate"));
            
            currentDate = new SimpleDateFormat("MMMM").format(currentMonth);
            
            System.out.println(date);
            if(date.equalsIgnoreCase(currentDate)){
                System.out.println("True");
                i=i+1;
                System.out.println(i);
               
                
                
                
            }
            
            
            
         }
         
        
         System.out.println(i);
         
         if(date.equalsIgnoreCase(currentDate)){
                updateSales.setInt(1, i);
                updateSales.setString(2, currentDate);
                System.out.println("Sales updated");
            }
            else if(date != currentDate){
                psmt.setInt(1, i);
                psmt.setString(2, date);
                System.out.println("New Sales record inserted ");
            }
        while(salesRs.next()){
              System.out.println("Month: "+salesRs.getInt("idmonthlySales"));
              System.out.println("Total Sales: "+salesRs.getInt("totalSales"));
              
         }
         
         
         //System.out.println(monthNumber);
         // psmt.setInt(1, monthNumber);
         if(currentMonth.getDate() != 24){
        // System.out.println("Today is :"+currentMonth.getDate());
      
         }
             psmt.setInt(1, i);
          psmt.setString(2, date);
         psmt.execute();
      } catch (SQLException e) {
         e.printStackTrace();
      } 
   }
}
