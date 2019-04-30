package com.myfruit.java.dao;

import java.util.ArrayList;
import java.util.List;

import com.myfruit.java.ov.Cart;
import com.myfruit.java.ov.CartList;
import com.myfruit.java.ov.Fruit;
import com.myfruit.java.ov.MyAddress;
import com.myfruit.java.ov.MyUser;
import com.myfruit.java.service.LoginService;

public class DBHelper {
	//根据用户名，以及密码去表单中查询是否有此人，返回null 或者Myuser某一个用户
	public static MyUser findMe(String username,String password){
		String sql = "select * from myuser where phone = '"+username+"'";
		List<MyUser> users = DBTool.search(sql);
		return users.size()==0?null:users.get(0);
	}
	//跟据用户useid 去查询是否存在，如果存在，再重新注册，不存在，添加新用户
	public static MyUser findUser(int useid) {
		String sql = "select * from myuser where useid = "+useid;
		List<MyUser> users = DBTool.search(sql);
		return users.size()==0?null:users.get(0);
	}
	
	public static Fruit findIt(int fid) {
		String sql = "select * from fruit where fid = "+fid;
		List<Fruit> fruit = DBTool.searchFruit(sql);
		return fruit.size()==0?null:fruit.get(0);
	}
	//对水果进行全查，返回一个水果集合
	public static List<Fruit> findItAll() {
		String sql = "select * from fruit";
		return DBTool.searchFruit(sql);
	}
	//对购物车里的东西进行全查询
	public static List<Cart> findCart(){
		String sql = "selcet * from cartform";
		return DBTool.searchCart(sql);
	}
	
	//根据用户名id，及水果的id判断购物车表中是否存在
	public static boolean isHave(int fid,int useid) {
		String sql = "select * from cartform where fid="+fid+" and useid="+useid;
		List<Cart> carts = DBTool.searchCart(sql);
		if(carts.size() == 0) {
			return false;
		}
		return true;
	}
	//如果存在，只更新购物车表中的数量(增加或者减少)；
	public static int updateNum(int num,int useid,int fid) {
		String sql ="update cartform set quantity = quantity+"+num+" where useid = "+useid+" and fid = "+fid;
		return DBTool.updateNum(sql);
	}
	//改变购物车里的数量，用现在的直接替换
	public static int replaceNum(int quantity,int useid,int fid) {
		String sql ="update cartform set quantity = "+quantity+" where useid = "+useid+" and fid = "+fid;
		return DBTool.updateNum(sql);
	}
	//如果不存在，给购物车表中添加新的数据
	public static int addCart(int fid,int useid,int quantity) {
		String sql ="insert into cartform values("+fid+","+useid+","+quantity+")";
		return DBTool.updateCart(sql);
	}
	
	//购物车列表页面，对fruit表跟cartform表进行，联查， fid相关联,根据用户的id查询，此用户购买的所有商品；
	public static List<CartList> allSearch(int useid) {
		String sql = "select * from cartform a,fruit b where a.fid = b.fid and useid ="+useid;
		return DBTool.allSearch(sql);
	}
	//用户注册添加信息
	public static void register(int useid,String uname,String phone,String pass) {
		String sql = "insert into myuser (useid,uname,phone,pass) values("+useid+",'"+uname+"','"+phone+"','"+pass+"')";
		DBTool.register(sql);
		System.out.println("添加完毕！");
	}
	//通过id查用户，只有一个返回值
	public static MyUser searchApart(int useid){
		String sql = "select useid,uname,phone,pass,realname,email from myuser where useid = "+useid;
		List<MyUser> user = DBTool.searchApart(sql);
		return user.size()==0?null:user.get(0);
		
	}
	//修改完善用户信息，并添加到DB表中
	public static void registered(String uname,String phone,String pass,String realname,String sex, String email,int useid) {
		String sql = "update myuser set uname='"+uname+"',phone='"+phone+"',pass='"+pass+"',realname='"+realname+"',sex='"+sex+"',email='"+email+"' where useid="+useid;
		DBTool.register(sql);
	}
	//给用户添加地址信息，以及其他一些
	public static void registered(String poster,String phone,String address,String realname,String tele,int useid) {
		String sql = "update myuser set poster='"+poster+"',phone='"+phone+"',address='"+address+"',realname='"+realname+"',tele='"+tele+"' where useid="+useid;
		DBTool.register(sql);
	}
	//给新建的用户地址表添加信息（可重复添加）
	public static void addAddress(String poster,String phone,String address,String realname,String tele,String email,int useid,int addNum) {
		String sql = "insert into myaddress values ("+useid+",'"+realname+"','"+address+"','"+tele+"','"+email+"','"+phone+"','"+poster+"',"+addNum+")";
		DBTool.addAddress(sql);
	}
	//根据用户id查询他的(默认，第一个)地址；
	public static MyAddress searchAddress(int useid) {
		String sql = "select * from myaddress where useid ="+useid;
		List<MyAddress> address = DBTool.searchAddr(sql);
		return address.size()==0?null:address.get(0);
	}
	//根据用户id查询他的(多个)地址；
	public static List<MyAddress> searchAddressAll(int useid) {
		String sql = "select * from myaddress where useid ="+useid;
		List<MyAddress> address = DBTool.searchAddr(sql);
		return address.size()==0?null:address;
	}
	//根据用户id删除购物表的信息
	public static void deleteAll(int useid) {
		String sql = "delete from cartform where useid = "+useid;
		DBTool.delete(sql);
	}
	//根据用户id与地址编码addNum删除地址
	public static void deleteAdd(int useid,int addNum) {
		String sql = "delete from myaddress where useid = "+useid+" and addnum ="+addNum;
		DBTool.delete(sql);
	}
	
	//根据用户，与水果id删除信息
	public static void delete(int useid,int fid) {
		String sql = "delete from cartform where useid = "+useid+" and fid = "+fid;
		DBTool.delete(sql);
	}
	public static void updatePass(String pass,int useid) {
		String sql = "update myuser set pass = '"+pass+"' where useid = "+useid;
		DBTool.updateNum(sql);
	}
	//先给用户的地址栏增加一个用户id
	public static void addID(int useid) {
		String sql = "insert into myaddress (useid) value ("+useid+")";
		DBTool.addAddress(sql);
	}
	
	public static void main(String[] args) {
		int useid =1001;
		int addNum = 2;
		DBHelper.deleteAdd(useid, addNum);
	}
}

