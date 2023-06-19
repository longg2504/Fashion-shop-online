package dao;

import context.DBContext;
import model.Category;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    Connection connection=null;
    PreparedStatement preparedStatement=null;
    ResultSet rs=null;
    private static final String SELECT_COUNT_PRODUCT="SELECT COUNT(*) as 'count' FROM product";
    private static final String SELECT_COUNT_USER="SELECT COUNT(*) as 'count' FROM user where isAdmin = 'False' or isAdmin = 'FALSE'";
    private static final String SELECT_COUNT_BILL="SELECT COUNT(*) as 'count' FROM bill";
    private static final String SELECT_COUNT_PRODUCT_LOW="SELECT COUNT(*) as 'count' FROM product where quantity < 10";
    private static final String SELECT_SEARCH_ALL="SELECT DISTINCT c.name , p.id , p.name, p.price, p.describle, p.quantity,p.image\n" +
            "                 FROM product p join category c on c.id = p.id\n" +
            "                WHERE p.name LIKE ? OR  p.price LIKE ? OR c.name LIKE ? ";

    public int CountProduct() {
        int count = 0;
        try {
            connection = new DBContext().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_COUNT_PRODUCT);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    public int CountUser() {
        int count = 0;
        try {
            connection = new DBContext().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_COUNT_USER);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int CountBill() {
        int count = 0;
        try {
            connection = new DBContext().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_COUNT_BILL);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return count;
    }

    public int CountProductLow() {
        int count = 0;
        try {
            connection = new DBContext().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_COUNT_PRODUCT_LOW);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return count;
    }

    public List<Product> getProductLow() {
        List<Product> list = new ArrayList<>();
        try {
            connection = new DBContext().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_COUNT_PRODUCT_LOW);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setName(rs.getString(1));
                list.add(new Product(rs.getInt(1),rs.getString(2), rs.getDouble(3),rs.getString(4), rs.getInt(5), rs.getString(6),category));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> SearchAll(String text) {
        List<Product> list = new ArrayList<>();
        try {
            connection = new DBContext().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_SEARCH_ALL);
            //"SELECT DISTINCT c.name , p.id , p.name, p.price, p.describle, p.quantity,p.image\n" +
            //            "                 FROM product p join category c on c.id = p.id\n" +
            //            "                WHERE p.name LIKE %?% OR  p.price LIKE %?% OR c.name LIKE %?% ";
            preparedStatement.setString(1,text);
            preparedStatement.setString(2,text);
            preparedStatement.setString(3,text);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(7));
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5), rs.getString(6),c));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
}
