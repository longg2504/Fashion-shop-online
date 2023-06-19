package dao;

import context.DBContext;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillDAO extends DBContext {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void addOrder(User u, Cart cart, String payment, String address, String phone) {
        LocalDate curDate = LocalDate.now();
        String date = curDate.toString();

        try {
            String sql = "insert into bill (user_id, total, payment, address, createDate, phone) values(?,?,?,?,?,?);";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, u.getUser_id());
            ps.setDouble(2, cart.getTotalMoney(cart.getItems()));
            ps.setString(3, payment);
            ps.setString(4, address);
            ps.setString(5, date);
            ps.setString(6, phone);
            ps.executeUpdate();

            String sql1 = "select id from bill order by id desc limit 1;";
            ps = conn.prepareStatement(sql1);
            rs = ps.executeQuery();

            if (rs.next()) {
                int bill_id = rs.getInt(1);
                for (Item i : cart.getItems()) {
                    String sql2 = "insert into bill_detail (bill_id, product_id, quantity, size, price ) values(?,?,?,?,?);";
                    double total = i.getQuantity() * i.getProduct().getPrice();
                    ps = conn.prepareStatement(sql2);
                    ps.setInt(1, bill_id);
                    ps.setInt(2, i.getProduct().getId());
                    ps.setInt(3, i.getQuantity());
                    ps.setString(4, i.getSize());
                    ps.setDouble(5, total);
                    ps.executeUpdate();
                }
            }

            String sql3 = "update product set quantity = quantity - ? "
                    + "where id = ?;";
            ps = conn.prepareStatement(sql3);
            for (Item i : cart.getItems()) {
                ps.setInt(1, i.getQuantity());
                ps.setInt(2, i.getProduct().getId());
                ps.executeUpdate();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

        public List<Bill> getBillInfo() {
            List<Bill> list = new ArrayList<>();
            String sql = "select b.id, u.user_name,b.phone,b.address,b.createDate,b.total,b.payment from bill b join user u on b.user_id = u.id;";
            try {
                conn = new DBContext().getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    User u = new User(rs.getString(2));
                    list.add(new Bill(rs.getInt(1), u, rs.getFloat(6), rs.getString(7), rs.getString(4), rs.getDate(5), rs.getString(3)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
        public Bill getBill() {
            List<Bill> list = new ArrayList<>();
            String sql = "select id, total, createDate from bill order by id desc limit 1;";
            try {
                conn = new DBContext().getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    return (new Bill(rs.getInt(1), rs.getDouble(2), rs.getDate(3)));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }

    public List<BillDetail>getDetail(int bill_id) {
        List<BillDetail> list = new ArrayList<>();
        String sql = "select d.detail_id, p.id, p.name, p.image, d.quantity, d.size,d.price from bill_detail d join product p on d.product_id = p.id where d.bill_id = ?;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bill_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getInt(2), rs.getString(3), rs.getString(4));
                list.add(new BillDetail(rs.getInt(1), p, rs.getInt(5), rs.getString(6),rs.getDouble(7)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void updatePayment(String payment, int bill_id) {
        String sql = "update bill set payment= ? where id = ?;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(2, bill_id);
            ps.setString(1, payment);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }


    public List<Bill> getBillByID(int user_id) {
        List<Bill> list = new ArrayList<>();
        String sql = "select b.id, b.createDate,b.total,b.payment, b.address, b.phone from bill b where user_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Bill(rs.getInt(1), rs.getDouble(3), rs.getString(4), rs.getString(5), rs.getDate(2), rs.getString(6)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Bill> getBillByDay() {
        List<Bill> list = new ArrayList<>();
        String sql = "select b.id, u.user_name,b.phone,b.address,b.createDate,b.total,b.payment from bill b join `user` u on b.user_id = u.id where b.createDate = cast(CURDATE() as Date);";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User(rs.getString(2));
                list.add(new Bill(rs.getInt(1), u, rs.getDouble(6), rs.getString(7), rs.getString(4), rs.getDate(5), rs.getString(3)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public double revenue() {
        double revenue = 0;
        String sql = "select sum(total) from bill;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                revenue = Double.parseDouble(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return revenue;
    }
    public double revenueByMonth(int month) {
        double revenue = 0;
        String sql = "select sum(total) from bill where month(createDate) = ?;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, month);
            rs = ps.executeQuery();
            while (rs.next()) {
                revenue = Double.parseDouble(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return revenue;
    }
    }


