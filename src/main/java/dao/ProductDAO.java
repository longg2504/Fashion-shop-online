package dao;

import context.DBContext;
import model.Category;
import model.Product;
import model.ProductSize;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DBContext {
    Connection conn = null;
    PreparedStatement pre = null;
    private static final String SELECT_ALL = "select  p.id , p.name, p.price, p.describle, p.quantity,p.image,c.name as type from product p join category c on p.category_id = c.id;"; //where
    private static final String SELECT_PRODUCT_LIMIT = "select  p.id , p.name, p.price, p.describle, p.quantity,p.image,c.name as type from product p join category c on p.category_id = c.id limit 9";
    private static final String SELECT_PRODUCT_LIMIT4 = "select  p.id , p.name, p.price, p.describle, p.quantity,p.image,c.name as type from product p join category c on p.category_id = c.id limit 4";
    private static final String INSERT_PRODUCT ="insert into product (category_id,name,price,describle,quantity,image) values(?,?,?,?,?,?);";
    private static final String INSERT_PRODUCT_SIZE = "insert into product_size values(?,?);";
    private static final String DELETE_SIZE = "delete from product_size where product_id=?";

    private static final String DELETE_PRODUCT = "delete from product where id=?";

    private static final String UPDATE_PRODUCT = "update product set category_id=? ,name=? ,price=? ,describle=? ,quantity=? ,image=? where id=?;";
    private static final String SELECT_PRODUCT_SIZE = "select * from product_size";
    private static final String SELECT_CATEGORY = "select * from category";

    private static final String SELECT_MAX_ID = "SELECT max(id) from product;";
    ResultSet rs = null;
    public List<Product> getProduct() throws Exception {
        List<Product> list =new ArrayList<>();
        conn = new DBContext().getConnection();
        pre =conn.prepareStatement(SELECT_ALL);
        rs = pre.executeQuery();
        while(rs.next()){
            Category category = new Category();
            category.setName(rs.getString(7));
            list.add(new Product(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getString(4),rs.getInt(5),rs.getString(6),category));
        }
        return list;
    }
    public List<Product> getProduct9() throws Exception {
        List<Product> list =new ArrayList<>();
        conn = new DBContext().getConnection();
        pre =conn.prepareStatement(SELECT_PRODUCT_LIMIT);
        rs = pre.executeQuery();
        while(rs.next()){
            Category category = new Category();
            category.setName(rs.getString(7));
            list.add(new Product(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getString(4),rs.getInt(5),rs.getString(6),category));
        }
        return list;
    }

    public List<Product> getProduct4() throws Exception {
        List<Product> list =new ArrayList<>();
        conn = new DBContext().getConnection();
        pre =conn.prepareStatement(SELECT_PRODUCT_LIMIT4);
        rs = pre.executeQuery();
        while(rs.next()){
            Category category = new Category();
            category.setName(rs.getString(7));
            list.add(new Product(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getString(4),rs.getInt(5),rs.getString(6),category));
        }
        return list;
    }

    public int getMaxId(Connection conn) throws Exception {
        pre =conn.prepareStatement(SELECT_MAX_ID);
        rs = pre.executeQuery();
        while (rs.next()) {
            return rs.getInt(1);
        }
       return  -1;
    }



    public void insertProduct(Product product) throws Exception {
        conn = getConnection();
        pre=conn.prepareStatement(INSERT_PRODUCT);
        pre.setInt(1,product.getCategory().getId());
        pre.setString(2,product.getName());
        pre.setDouble(3,product.getPrice());
        pre.setString(4,product.getDescrible());
        pre.setInt(5,product.getQuantity());
        pre.setString(6,product.getImage());
        pre.executeUpdate();

        int productId = getMaxId(conn);


        //them size cho bang ProductSize
        for(ProductSize i : product.getProductSizes()){
            pre=conn.prepareStatement(INSERT_PRODUCT_SIZE);
            pre.setInt(1,productId);
            pre.setString(2,i.getSize());
            pre.executeUpdate();
        }
        conn.close();
    }

    public void DeleteProduct(int id) throws Exception {
        // xoa trong bang product_size
        conn = new DBContext().getConnection();
        pre=conn.prepareStatement(DELETE_SIZE);
        pre.setInt(1,id);
        pre.executeUpdate();

        // xoa trong bang product
        conn = new DBContext().getConnection();
        pre=conn.prepareStatement(DELETE_PRODUCT);
        pre.setInt(1,id);
        pre.executeUpdate();
    }

    public void UpdateProduct(Product product) throws Exception {
        //xoa trong bang product_size truoc
        conn = new DBContext().getConnection();
        pre=conn.prepareStatement(DELETE_SIZE);
        pre.setInt(1,product.getId());
        pre.executeUpdate();

        //ghi lai size vao bang size
        for(ProductSize i : product.getProductSizes()) {
            pre = conn.prepareStatement(INSERT_PRODUCT_SIZE);
            pre.setInt(1, i.getProduct_id());
            pre.setString(2, i.getSize());
            pre.executeUpdate();
        }

        //update bang product
            pre = conn.prepareStatement(UPDATE_PRODUCT);
            pre.setInt(1, product.getCategory().getId());
            pre.setString(2, product.getName());
            pre.setDouble(3, product.getPrice());
            pre.setString(4, product.getDescrible());
            pre.setInt(5, product.getQuantity());
            pre.setString(6, product.getImage());
            pre.setInt(7, product.getId());
            pre.executeUpdate();

    }

        public List<ProductSize> getSize() throws Exception {
            List<ProductSize> list = new ArrayList<>();
            conn = new DBContext().getConnection();
            pre = conn.prepareStatement(SELECT_PRODUCT_SIZE);
            rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new ProductSize(rs.getInt(1), rs.getString(2)));
            }
            return list;
        }

    public List<Category> getCategory() {
        List<Category> list = new ArrayList<>();
        try {
            conn = new DBContext().getConnection();
            pre = conn.prepareStatement(SELECT_CATEGORY);
            rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
        return list;
    }

    public Category getCategoryByName(String name) {
        String sql = "select * from category where name = ?;";
        try {
            conn = new DBContext().getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, name);
            rs = pre.executeQuery();
            while (rs.next()) {
                return new Category(rs.getInt(1), rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertCategory(String name) {
        String sql = " insert into category (category_name) values(?);";
        try {
            conn = new DBContext().getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, name);
            pre.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product>getTop10Product() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.id , p.name, p.price, p.describle, p.quantity,p.image FROM product p ORDER BY p.id DESC LIMIT 10;";
        try {
            conn = new DBContext().getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5), rs.getString(6)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Product> getTrendProduct() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.id , p.name, p.price, p.describle, p.quantity,p.image FROM product p\n" +
                "join (select b.detail_id, count(b.product_id) as countProduct from bill_detail b group by b.product_id order by countProduct DESC LIMIT 5) as bd on p.id = bd.product_id;";
        try {
            conn = new DBContext().getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getInt(5), rs.getString(6)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getProductByPriceDesc() {
        List<Product> list = new ArrayList<>();
        String sql = "select  p.id , p.name, p.price, p.describle, p.quantity,p.image,c.name  from product p join category c on p.category_id = c.id ORDER BY p.product_price DESC;";
        try {
            conn = new DBContext().getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(7));
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5), rs.getString(6),c));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Product> getProductByPriceAsc() {
        List<Product> list = new ArrayList<>();
        String sql = "select  p.id , p.name, p.price, p.describle, p.quantity,p.image,c.name from product p join category c on p.category_id = c.id ORDER BY p.price ASC;";
        try {
            conn = new DBContext().getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getInt(7));
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getInt(5), rs.getString(6),c));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Product> getListByPage(List<Product> list, int start, int end) {
        ArrayList<Product> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }
    public List<Product> getProductByCategory(int category_id) {
        List<Product> list = new ArrayList<>();
        String sql = "select p.id, p.name, p.price, p.describle, p.quantity,p.image,c.name from product p join category c on p.category_id = c.id WHERE p.category_id=?;";
        try {
            conn = new DBContext().getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, category_id);
            rs = pre.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(7));
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getInt(5), rs.getString(6),c));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public Product getProductByID(int product_id) {
        List<Product> list = new ArrayList<>();
        String sql = "select c.id, c.name , p.id , p.name, p.price, p.describle, p.quantity,p.image from product p join category c on p.category_id = c.id WHERE p.id=?;";
        try {
            conn = new DBContext().getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, product_id);
            rs = pre.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getInt(1), rs.getString(2));
                return (new Product(rs.getInt(3), rs.getString(4), rs.getFloat(5), rs.getString(6), rs.getInt(7), rs.getString(8),c));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Product getProductByIDAndSize(int product_id , String size) {
        List<Product> list = new ArrayList<>();
        String sql = "select c.id, c.name , p.id , p.name, p.price, p.describle, p.quantity,p.image from product p join category c on p.category_id = c.id WHERE p.id=? and p.size=?;";
        try {
            conn = new DBContext().getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, product_id);
            pre.setString(2,size);
            rs = pre.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getInt(1), rs.getString(2));
                return (new Product(rs.getInt(3), rs.getString(4), rs.getFloat(5), rs.getString(6), rs.getInt(7), rs.getString(8),c));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<ProductSize> getSizeByID(int product_id) {
        List<ProductSize> list = new ArrayList<>();
        String sql = "select * from product_size where product_id=?;";
        try {
            conn = new DBContext().getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, product_id);
            rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new ProductSize(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> SearchAll(String text) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT DISTINCT p.id , p.name, p.price, p.describle, p.quantity,p.image,c.name\n" +
                "FROM product p join category c on c.id = p.category_id\n" +
                "WHERE p.name LIKE ? OR  p.price LIKE ? OR c.name LIKE ? ;";

        try {
            conn = new DBContext().getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, "%" + text + "%");
            pre.setString(2, text);
            pre.setString(3, "_%" + text + "%_");
            rs = pre.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(7));
                list.add(new Product(rs.getInt(2), rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getInt(5), rs.getString(6),c));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getProductByName() {
        List<Product> list = new ArrayList<>();
        String sql = "select  p.id , p.name, p.price, p.describle, p.quantity,p.image,c.name from product p join category c on p.category_id = c.id ORDER BY p.name;";
        try {
            conn = new DBContext().getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(7));
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5), rs.getString(6),c));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    

}