package com.hcjava.entity;
/**
 * 	关联关系，数据库中以外键的形式提现，
 * 实体类对象提现对象之间的依赖注入
 * @author huachuang
 *
 */

public class Book {

	private Integer id;
	private String name;
	private User user;// <many - to - one>多对一

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {

		return "Book [id=" + id + ", name=" + name + ", user=" + user + "]";

	}

}
