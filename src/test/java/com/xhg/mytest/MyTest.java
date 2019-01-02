package com.xhg.mytest;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.*;
import java.util.List;

public class MyTest {
    @Test
    public void test() {
        User[] users = new User[]{new User(1, "admin", "admin@qq.com"),
                new User(2, "maco", "maco@qq,com"),
                new User(3, "kitty", "kitty@qq,com")};//初始化对象数组
        User[] target = new User[users.length];//新建一个目标对象数组
        System.arraycopy(users, 0, target, 0, users.length);//实现复制
        System.out.println("源对象与目标对象的物理地址是否一样：" + (users[0] == target[0] ? "浅复制" : "深复制"));
        target[0].setEmail("admin@sina.com");
        System.out.println("修改目标对象的属性值后源对象users：");
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 浅复制：通过clone方法复制时，对象的属性值的复制通过复制引用的方式完成的对象的复制就叫浅复制！
     */
    @Test
    public void testBook() {
        Chapter chapter1 = new Chapter("第一章", 2500, 15);
        Chapter chapter2 = new Chapter("第二章", 2600, 16);
        Book book = new Book(1l, "三国演义", "罗贯中", Lists.newArrayList(chapter1, chapter2));
        Book cloneBook = (Book) book.clone();
        System.out.println(book == cloneBook);  //false
        System.out.println(book.getChapterList() == cloneBook.getChapterList()); //true
        // 给book对象的chapterList加一个元素，可以看到cloneBook的chapter也变化了
        book.getChapterList().add(new Chapter("第三章", 2500, 15));
        System.out.println(cloneBook.getChapterList().size()); //3
    }

    /**
     * 运行的结果打印出false/false，说明此时与浅复制不一样，不是引用的复制了，而是完全不同的两个对象了！这就是深复制！
     * @throws Exception
     */
    @Test
    public void testDeepClone() throws Exception {
        Chapter chapter1 = new Chapter("第一章", 2500, 15);
        Chapter chapter2 = new Chapter("第二章", 2600, 16);
        Book book = new Book(1l, "三国演义", "罗贯中", Lists.newArrayList(chapter1, chapter2));
        //将对象转换为字节流写入流中
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(book);

        //从流里读出来
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Book cloneBook = (Book) ois.readObject();
        //关闭流
        baos.close();
        oos.close();
        bais.close();
        ois.close();
        /*
         * 此处如果没有实现Serializable接口，就会报错java.io.NotSerializableException
         * Library持有任何对象的类型都要实现Serializable接口，否则会报错
         */
        System.out.println(book == cloneBook);
        System.out.println(book.getChapterList() == cloneBook.getChapterList());
    }

}


class User {
    private Integer id;
    private String username;
    private String email;

    //无参构造函数
    public User() {
    }

    //有参的构造函数
    public User(Integer id, String username, String email) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", email=" + email
                + "]";
    }
}

/**
 * 图书类
 */
class Book implements Cloneable,Serializable {
    private Long id;
    private String bookname;
    private String author;
    private List<Chapter> chapterList;

    public Book() {
    }

    public Book(Long id, String bookname, String author, List<Chapter> chapterList) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.chapterList = chapterList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

    /**
     * 重写Object的clone方法
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

/**
 * 书的章节
 */
class Chapter implements Serializable {
    //章节的名称
    private String name;
    //章节的字数
    private Integer size;
    //章节的页数
    private Integer pageQty;

    public Chapter() {
    }

    public Chapter(String name, Integer size, Integer pageQty) {
        this.name = name;
        this.size = size;
        this.pageQty = pageQty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPageQty() {
        return pageQty;
    }

    public void setPageQty(Integer pageQty) {
        this.pageQty = pageQty;
    }
}
