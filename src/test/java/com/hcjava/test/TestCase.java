package com.hcjava.test;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.hcjava.entity.Book;
import com.hcjava.entity.People;
import com.hcjava.entity.Student;
import com.hcjava.entity.Teacher;
import com.hcjava.entity.User;

public class TestCase {

	public static Session getSession() {
		// ��ȡ���ݿ�������Ϣ
		Configuration cf = new Configuration().configure("hibernate.cfg.xml");
		// ��ȡSession
		Session session = cf.buildSessionFactory().openSession();
		return session;
	}

	@Test
	public void testStudent() {
		Session session = getSession();
		Student student = (Student) session.get(Student.class, 2);
		System.out.println(student);
		session.close();
	}

	@Test
	public void save() {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		Student student = new Student();
		student.setAge(22);
		student.setName("qq");
		session.save(student);
		transaction.commit();
		session.close();
	}

	@Test
	public void update() {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		// �޸�Ӧ���Ȳ�ѯ���޸�
		Student student = (Student) session.get(Student.class, 4);
		student.setName("wb");
		student.setAge(30);
		session.update(student);
		transaction.commit();
		session.close();
	}

	@Test
	public void delete() {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		Student student = (Student) session.get(Student.class, 4);
		session.delete(student);
		transaction.commit();
		session.close();
	}

	@Test
	public void delete1() {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		// Student student = (Student) session.get(Student.class, 5);
		Student student = new Student();
		student.setId(5);
		student.setName("pp");
		student.setAge(21);
		session.saveOrUpdate(student);
		transaction.commit();
		session.close();
	}

	@Test
	public void save1() {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		Teacher teacher = new Teacher();
		teacher.setName("ll");
		teacher.setAge(23);
		session.save(teacher);
		transaction.commit();
		session.close();
	}

	@Test
	public void save2() {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		People people = new People();
		people.setName("ll");
		people.setAge(123);
		session.save(people);
		transaction.commit();
		session.close();
	}

	@Test
	public void testEvict() {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		Student student = (Student) session.get(Student.class, 2);
		System.out.println(student);
		session.evict(student);// session�����һ������
		student.setName("as");
		System.out.println(student);
		transaction.commit();
		session.close();
	}

	@Test
	public void testUpdate() {// ������̬��Ϊ�־�̬
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		// ��ȡ�־�״̬�Ķ���
		Student student = (Student) session.get(Student.class, 2);
		// ���־�̬�Ķ���ת��Ϊ����̬
		session.evict(student);
		// �ö���ص��־�̬
		student.setName("as");
		session.update(student);
		transaction.commit();
		session.close();
	}

	// Hibernate�Ĳ�ѯ���HQL
	@Test
	public void selectHQL() {
		// ��ȡSession
		Session session = getSession();
		// ��дHQL
		String hql = "from Student";
		// Sessionִ��HQL������һ�������
		Query query = session.createQuery(hql);
		// ���������
		List<Student> list = query.list();
		for (Student student : list) {
			System.out.println(student);
		}
		session.close();
	}

	@Test
	public void select1() {
		// ��ȡSession
		Session session = getSession();
		// ��дHQL
		String sql = "select * from student";
		// Sessionִ��HQL������һ�������
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		// ���������
		sqlQuery.addEntity(Student.class);
		List<Student> list = sqlQuery.list();
		for (Student student : list) {
			System.out.println(student);
		}
		session.close();
	}

	@Test
	public void whereHQL() {
		// ��ȡSession
		Session session = getSession();
		// ��дHQL���
		String hql = "from Student where name=:name";
		// sessionִ��HQL����ȡquery����
		Query query = session.createQuery(hql);
		// ���ò���
		query.setString("name", "as");
		// ���������
		List<Student> list = query.list();
		for (Student student : list) {
			System.out.println(student);
		}
		session.close();
	}

	@Test
	public void likeHQL() {
		// ��ȡSession
		Session session = getSession();
		// ��дHQL���
		String hql = "from Student where name like :name";
		// sessionִ��HQL����ȡquery����
		Query query = session.createQuery(hql);
		// ���ò���
		String name = "s";
		query.setString("name", "%" + name + "%");
		// ���������
		List<Student> list = query.list();
		for (Student student : list) {
			System.out.println(student);
		}
		session.close();
	}

	@Test
	public void orderHQL() {
		Session session = getSession();
		String hql = "from Student order by id desc";
		Query query = session.createQuery(hql);
		List<Student> list = query.list();
		for (Student student : list) {
			System.out.println(student);
		}
		session.close();
	}

	@Test
	public void oneToManyHQL() {
		Session session = getSession();
		User user = (User) session.get(User.class, 1);
		System.out.println(user.getName());
		Set<Book> books = user.getBooks();
		for (Book book : books) {
			System.out.println(book.getName());
		}
		session.close();
	}

	// ��֤һ������Ĵ���
	@Test
	public void cache() {
		Session session = getSession();
		User user = (User) session.get(User.class, 1);
		System.out.println(user.getName());
		System.out.println("----------------");
		User user2 = (User) session.get(User.class, 1);
		System.out.println(user2.getName());
		System.out.println("----------------");
		System.out.println(user == user2);
		session.close();
	}

	// ��֤�����ص�ԭ��(get�������أ�load������)
	@Test
	public void lazy() {
		Session session = getSession();
		Student student = (Student) session.get(Student.class, 1);
		System.out.println("------------");
		System.out.println(student.getName() + "," + student.getAge());
	}

	@Test
	public void lazy1() {
		Session session = getSession();
		Student student = (Student) session.load(Student.class, 1);
		System.out.println("------------");
		System.out.println(student.getName() + "," + student.getAge());
	}

}
