package com.hcjava.entity;
/**
 * 	������ϵ�����ݿ������������ʽ���֣�
 * ʵ����������ֶ���֮�������ע��
 * @author huachuang
 *
 */

public class Book {

	private Integer id;
	private String name;
	private User user;// <many - to - one>���һ

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
