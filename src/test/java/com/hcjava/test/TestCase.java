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
		// 读取数据库配置信息
		Configuration cf = new Configuration().configure("hibernate.cfg.xml");
		// 获取Session
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
		// 修改应该先查询后修改
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
		session.evict(student);// session中清除一个对象
		student.setName("as");
		System.out.println(student);
		transaction.commit();
		session.close();
	}

	@Test
	public void testUpdate() {// 将游离态变为持久态
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		// 获取持久状态的对象
		Student student = (Student) session.get(Student.class, 2);
		// 将持久态的对象转化为游离态
		session.evict(student);
		// 让对象回到持久态
		student.setName("as");
		session.update(student);
		transaction.commit();
		session.close();
	}

	// Hibernate的查询语句HQL
	@Test
	public void selectHQL() {
		// 获取Session
		Session session = getSession();
		// 编写HQL
		String hql = "from Student";
		// Session执行HQL，返回一个结果集
		Query query = session.createQuery(hql);
		// 遍历结果集
		List<Student> list = query.list();
		for (Student student : list) {
			System.out.println(student);
		}
		session.close();
	}

	@Test
	public void select1() {
		// 获取Session
		Session session = getSession();
		// 编写HQL
		String sql = "select * from student";
		// Session执行HQL，返回一个结果集
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		// 遍历结果集
		sqlQuery.addEntity(Student.class);
		List<Student> list = sqlQuery.list();
		for (Student student : list) {
			System.out.println(student);
		}
		session.close();
	}

	@Test
	public void whereHQL() {
		// 获取Session
		Session session = getSession();
		// 编写HQL语句
		String hql = "from Student where name=:name";
		// session执行HQL，获取query对象
		Query query = session.createQuery(hql);
		// 设置参数
		query.setString("name", "as");
		// 遍历结果集
		List<Student> list = query.list();
		for (Student student : list) {
			System.out.println(student);
		}
		session.close();
	}

	@Test
	public void likeHQL() {
		// 获取Session
		Session session = getSession();
		// 编写HQL语句
		String hql = "from Student where name like :name";
		// session执行HQL，获取query对象
		Query query = session.createQuery(hql);
		// 设置参数
		String name = "s";
		query.setString("name", "%" + name + "%");
		// 遍历结果集
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

	// 验证一级缓存的存在
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

	// 验证懒加载的原理(get不懒加载，load懒加载)
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
