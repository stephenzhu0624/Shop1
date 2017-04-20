package org.basic.order;

import java.util.Collection;
import java.util.Iterator;

import org.basic.bean.CartItem;
import org.basic.function.FunctionDao;
import org.basic.function.FunctionDaoImpl;

public class Test {

	public static void main(String[] args) {
		FunctionDao f=new FunctionDaoImpl();
		
		Collection<CartItem> g=f.getCartItems("abcde");Iterator<CartItem> ici = g.iterator();
		while(ici.hasNext()){
			 CartItem c = (CartItem) ici.next();
			System.out.print(c.getCartItemID());
		}
		
		

	}

}
