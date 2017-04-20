package org.basic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.basic.bean.CartItem;
import org.basic.bean.Product;
import org.basic.bean.User;
import org.basic.function.*;

@Controller
public class Hello {
    @RequestMapping("/hello")
    public String hello() {
        return "index";
    }

    @RequestMapping("/loginPage")
    public String loginPage(HttpSession session) {
        String tempUserID = (String) session.getAttribute("userID");
        if(tempUserID!=null) {
            return "index";
        }
        return "login";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest res, HttpSession session) {
        User user = new User();
        FunctionDaoImpl u = new FunctionDaoImpl();
        String userID = res.getParameter("userID");
        String password = res.getParameter("password");
        user.setUserID(userID);
        user.setPassword(password);
        int i = u.userLogin(user);
        if (i == 1) {
            session.setAttribute("userID",userID);
            return "index";
        } else {
            String action="login";
            session.setAttribute("action",action);
            return "error";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest res, HttpSession session) {
        session.putValue("userID",null);
        return "index";
    }

    @RequestMapping("/registerPage")
    public String registerPage(HttpSession session) {
        String tempUserID = (String) session.getAttribute("userID");
        if(tempUserID!=null) {
            return "index";
        }
        return "register";
    }

    @RequestMapping("/register")
    public String register(HttpServletRequest res, HttpSession session) {
        User user = new User();
        FunctionDaoImpl u = new FunctionDaoImpl();

        String userID = res.getParameter("userID");
        String password = res.getParameter("password");
        String userAccount = res.getParameter("userAccount");
        String address = res.getParameter("address");
        String mobile = res.getParameter("mobile");
        user.setUserID(userID);
        user.setPassword(password);
        user.setAddress(address);
        user.setMobile(mobile);
        user.setUserAccount(userAccount);
        user.setIcon("000001");
        int i = u.userRegister(user);
        if (i == 1) {
            return "index";
        } else {
            String action = "register";
            session.setAttribute("action",action);
            return "error";
        }
    }

    @RequestMapping("/sales")
    public String sales(HttpServletRequest res, Model model) throws SQLException {
        int pageNum = 0;
        if (null == res.getParameter("pageNum"))
            pageNum = 1;
        else
            pageNum = Integer.parseInt(res.getParameter("pageNum"));
        FunctionDaoImpl n = new FunctionDaoImpl();
        ArrayList<Product> inform = n.showpage(pageNum);
        model.addAttribute("products", inform);

        return "sales";
    }

    @RequestMapping("/single")
    public String single(HttpServletRequest res,HttpSession session) throws SQLException {
        FunctionDaoImpl n = new FunctionDaoImpl();
        String productID = res.getParameter("productID");
        Product p = n.searchProduct(productID);
        session.setAttribute("p", p);
        return "single";
    }

    @RequestMapping("/cart")
    public String cart(HttpServletRequest request, HttpSession session,Model model) {
    	  FunctionDaoImpl fdi = new FunctionDaoImpl();
    	  String userID=(String)session.getAttribute("userID");
    	  ArrayList<CartItem> ci = fdi.getCartItems(userID);
          model.addAttribute("ci", ci);
		return "cart";
    }

    @RequestMapping("/deleteFromCart")
    public String deleteFromCart(HttpServletRequest request, HttpSession session,Model model) {
        FunctionDaoImpl fdi = new FunctionDaoImpl();
        String cartID = request.getParameter("cartID");
        fdi.deleteFromCart(Integer.parseInt(cartID));
        String userID= (String)session.getAttribute("userID");
        Collection<CartItem> ci = fdi.getCartItems(userID);
        model.addAttribute("ci", ci);
        return "cart";
    }

    @RequestMapping("/add2Cart")
    public String add2Cart(HttpServletRequest request, HttpSession session,Model model) {
        FunctionDaoImpl fdi = new FunctionDaoImpl();
        String userID = (String) session.getAttribute("userID");
        Product p = (Product) session.getAttribute("p");
        int count = Integer.parseInt(request.getParameter("count"));
        int size = Integer.parseInt(request.getParameter("size"));
        if(fdi.addItem2Cart(p,userID,size,count)==1) {
        	Collection<CartItem> ci = fdi.getCartItems(userID);
        	model.addAttribute("ci", ci);
        	return "cart";
        } else {
            session.setAttribute("action",null);
            return "error";
        }

    }
}
