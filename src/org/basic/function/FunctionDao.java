package org.basic.function;

import java.util.ArrayList;
import java.util.Collection;

import org.basic.bean.CartItem;
import org.basic.bean.Order;
import org.basic.bean.Product;
import org.basic.bean.User;

public interface FunctionDao {

    public int userRegister(User user);

    public int userLogin(User user);

    public int userUpdatePassword(User user, String newPassword);

    public int userUpdateProfile(User user, String newMobile, String newAddress, String newIcon);

    public Product searchProduct(String productID);

    public int deleteUser(User user);

    public int newOrder(Order order);

    public int deleteOrder(Order order);

    public ArrayList<Order> userCheckOrder(User user);

    public int addItem2Cart(Product p, String userID, int size, int count);

    public int deleteFromCart(int cartID);

    public Collection<CartItem> getCartItems(String userID);

	/*public int editItemAmount();

	public ArrayList<Cart> showCart();*/

}
