package org.basic.function;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.basic.Connection.Conn;
import org.basic.bean.CartItem;
import org.basic.bean.Order;
import org.basic.bean.Product;
import org.basic.bean.User;


public class FunctionDaoImpl implements FunctionDao {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Statement stmt = null;

    public int userRegister(User user) {
        String sql = "insert into user(USERID,USERACCOUNT,PASSWORD,MOBILE,ADDRESS,ICON) values (?,?,?,?,?,?)";
        int i = 0;
        try {
            conn = Conn.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserID());
            ps.setString(2, user.getUserAccount());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getMobile());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getIcon());
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.release(ps);
            Conn.release(conn);
        }
        return i;
    }

    public int userLogin(User user) {
        String sql = "select * from user where userid=? and password=?";
        int i = 0;
        try {
            conn = Conn.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserID());
            ps.setString(2, user.getPassword());
            rs = ps.executeQuery();
            while (rs.next()) i = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.release(ps);
            Conn.release(conn);
        }
        return i;
    }

    public int userUpdatePassword(User user, String newPassword) {
        String sql = "update user set password=? where userid=?";
        int i = 0;
        try {
            conn = Conn.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, user.getUserID());
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.release(ps);
            Conn.release(conn);
        }
        return i;
    }

    public int userUpdateProfile(User user, String newMobile, String newAddress, String newIcon) {
        String sql = "update user set mobile=? and address=? and icon=?";
        int i = 0;
        if (user.getMobile().equals(newMobile)) {

        } else {
            user.setMobile(newMobile);
        }
        if (user.getAddress().equals(newAddress)) {

        } else {
            user.setAddress(newAddress);
        }
        if (user.getIcon().equals(newIcon)) {

        } else {
            user.setIcon(newIcon);
        }
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getMobile());
            ps.setString(2, user.getAddress());
            ps.setString(3, user.getIcon());
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.release(ps);
            Conn.release(conn);
        }
        return i;
    }

    @Override
    public int deleteUser(User user) {
        String sql = "delete from user where userid=?";
        int i = 0;
        try {
            conn = Conn.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserID());
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.release(rs);
            Conn.release(ps);
            Conn.release(conn);
        }
        return i;
    }

    public ArrayList<Order> userCheckOrder(User user) {
        ArrayList<Order> order = new ArrayList<Order>();
        Order temp = new Order();
        String sql = "select * from order where userid=?";
        try {
            conn = Conn.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserID());
            rs = ps.executeQuery();
            while (rs.next()) {
                temp.setOrderID(rs.getInt(1));
                temp.setUserID(rs.getInt(2));
                temp.setItemID(rs.getInt(3));
                temp.setOrderTime(rs.getDate(4));
                temp.setOrderAddress(rs.getString(5));
                temp.setAmount(rs.getInt(6));
                temp.setPrice(rs.getDouble(7));
                order.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.release(rs);
            Conn.release(ps);
            Conn.release(conn);
        }
        return order;
    }

    public int newOrder(Order order) {
        String sql = "insert into order(userID,itemID,orderTime,orderAddress,amount,price,status) values (?,?,now(),?,?,?,?,?)";
        int i = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, order.getUserID());
            ps.setInt(2, order.getItemID());
            ps.setString(4, order.getOrderAddress());
            ps.setInt(5, order.getAmount());
            ps.setDouble(6, order.getPrice());
            ps.setInt(7, 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.release(ps);
            Conn.release(conn);
        }
        return i;
    }

    public int deleteOrder(Order order) {
        String sql1 = "update order set status=1 where orderid=?";
        String sql2 = "delete from order where orderid=?";
        int i = 0;
        try {
            if (order.getStatus() == 0 || order.getStatus() == 1) {
                ps = conn.prepareStatement(sql1);
                ps.setInt(1, order.getOrderID());
            } else if (order.getStatus() == 2) {
                ps = conn.prepareStatement(sql2);
                ps.setInt(1, order.getOrderID());
            }
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.release(rs);
            Conn.release(ps);
            Conn.release(conn);
        }
        return i;
    }

    public ArrayList<Product> showpage(int pageNum) {
        conn = Conn.getConnection();
        ArrayList<Product> c = new ArrayList<Product>();
        int i = -1;
        int a = i + pageNum;

        String sql = "select * from product limit " + a + ",2 ";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                //Product p = new Product(0, "0", 0, "0", "0", "0", "0", 0, 0, 0, "0");
                Product p = new Product();
                p.setProductid(rs.getInt("productid"));
                p.setProName(rs.getString("proname"));
                p.setPrice(rs.getInt("price"));
                p.setQuickOverview(rs.getString("quickoverview"));
                p.setProductDescription(rs.getString("productdescription"));
                p.setAddInformation(rs.getString("addinformation"));
                p.setReviews(rs.getString("reviews"));
                p.setP1(rs.getString("p1"));
                p.setP2(rs.getString("p2"));
                p.setP3(rs.getString("p3"));
                p.setType(rs.getString("type"));
                c.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.release(rs);
            Conn.release(ps);
            Conn.release(conn);
        }
        return c;
    }


    public Product searchProduct(String productID) {
        conn = Conn.getConnection();
        String sql = "select * from product where productid=?";
        Product p = new Product();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, productID);
            rs = ps.executeQuery();
            if (rs.next()) {
                p.setProductid(rs.getInt("productid"));
                p.setProName(rs.getString("proname"));
                p.setPrice(rs.getInt("price"));
                p.setQuickOverview(rs.getString("quickoverview"));
                p.setProductDescription(rs.getString("productdescription"));
                p.setAddInformation(rs.getString("addinformation"));
                p.setReviews(rs.getString("reviews"));
                p.setP1(rs.getString("p1"));
                p.setP2(rs.getString("p2"));
                p.setP3(rs.getString("p3"));
                p.setType(rs.getString("type"));
                //System.out.println(p.getProductid());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.release(rs);
            Conn.release(ps);
            Conn.release(conn);
        }

        return p;

    }

    public  ArrayList<CartItem> getCartItems(String userID) {
    	 ArrayList<CartItem> groups = new ArrayList<>();
        String sql = "select distinct * from cart,product where cart.userid=? and cart.productid=product.productid";
        
        try {
            conn = Conn.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
            	CartItem temp = new CartItem();
                Product p = new Product();
                temp.setCartItemID(rs.getInt("cart.cartItemID"));
                p.setProductid(rs.getInt("productid"));
                p.setProName(rs.getString("proName"));
                p.setPrice(rs.getInt("price"));
                p.setQuickOverview(rs.getString("quickOverview"));
                p.setProductDescription(rs.getString("productDescription"));
                p.setAddInformation(rs.getString("addinformation"));
                p.setReviews(rs.getString("reviews"));
                p.setP1(rs.getString("p1"));
                p.setP2(rs.getString("p2"));
                p.setP3(rs.getString("p3"));
                p.setType(rs.getString("type"));
                temp.setP(p);
                temp.setUserID(rs.getString("userID"));
                temp.setCount(rs.getInt("Count"));
                //System.out.println(rs.getInt("size"));
                temp.setSize(rs.getInt("size"));
                groups.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.release(rs);
            Conn.release(ps);
            Conn.release(conn);
        }
        //System.out.println("groups:"+groups);
        return groups;
    }

    public int addItem2Cart(Product p, String userID, int size, int count) {
        String sql = "insert into cart(productID,userID,size,count) values (?,?,?,?)";
        int i = 0;
        try {
            conn = Conn.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getProductid());
            ps.setString(2, userID);
            ps.setInt(3, size);
            ps.setInt(4, count);
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.release(ps);
            Conn.release(conn);
        }
        return i;
    }


    public int deleteFromCart(int cartID) {
        String sql = "delete from cart where cartItemID=?";
        int i = 0;

        try {
            conn = Conn.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cartID);
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conn.release(ps);
            Conn.release(conn);
        }
        return i;
    }
    
    
    	
    }

