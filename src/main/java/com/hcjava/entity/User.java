package com.hcjava.entity;
/**
 * 	������ϵ�����ݿ������������ʽ���֣�
 * ʵ����������ֶ���֮�������ע��
 * @author huachuang
 *
 */

import java.util.HashSet;
import java.util.Set;

public class User {

	private Integer id;
	private String name;
	// <one - to many>һ�Զ�
	private Set<Book> books = new HashSet<Book>();

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

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", books=" + books + "]";
	}

}
