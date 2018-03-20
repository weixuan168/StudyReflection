package xuan;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Xuan on 2017/3/31.
 */
public class ReflectDemo {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, NoSuchMethodException {

        //通过Java反射机制得到类的包名和类名
        Demo1();
        System.out.println("=============================================");

        //验证所有的类都是Class类的实例对象
        Demo2();
        System.out.println("=============================================");

        //用Class类创建类对象（反射的意义所在）
        Demo3();
        System.out.println("=============================================");

        //得到一个类的构造函数，并实现构造带参实例对象
        Demo4();
        System.out.println("=============================================");

        //操作成员变量，set和get
        Demo5();
        System.out.println("=============================================");

        //得到类的属性：继承的接口，父类，函数信息，成员信息，类型等
        Demo6();
        System.out.println("=============================================");

        //调用类中的方法
        Demo7();
        System.out.println("=============================================");

        //获得类加载器
        Demo8();
    }

    /**
     * Demo1: 通过Java反射机制得到类的包名和类名
     */
    public static void Demo1() {
        Person person = new Person();
        System.out.println("Demo1:包名：" + person.getClass().getPackage().getName() + "，" +
                "完整类名：" + person.getClass().getName());
    }

    /**
     * Demo2: 验证所有的类都是Class类的实例对象
     */
    public static void Demo2() throws ClassNotFoundException {
        //定义一个类型未知的Class，设初值为null
        Class class1 = null;

        class1 = Class.forName("xuan.ReflectDemo$Person");
        System.out.println("Demo2:包名：" + class1.getPackage().getName() + "，"
                + "完整类名:" + class1.getName());
    }

    /**
     * Demo3: 通过Java反射机制，用Class 创建类对象[这也就是反射存在的意义所在]
     */
    public static void Demo3() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class class1 = null;
        class1 = Class.forName("xuan.ReflectDemo$Person");
        //newInstance()不能带参数，所以一定要有无参构造函数
        Person person = (Person) class1.newInstance();
        person.setAge(20);
        person.setName("weixuan");
        System.out.println("Demo3:" + person.getName() + ":" + person.getAge());
    }

    /**
     * Demo4: 通过Java反射机制得到一个类的构造函数，并实现创建带参实例对象
     */
    public static void Demo4() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class class1 = null;
        Person person1 = null;
        Person person2 = null;

        class1 = Class.forName("xuan.ReflectDemo$Person");

        //获取构造函数
        Constructor[] constructors = class1.getConstructors();

        person1 = (Person) constructors[0].newInstance();
        person1.setAge(30);
        person1.setName("aa");

        person2 = (Person) constructors[1].newInstance(20, "weixuan");

        System.out.println("Demo4:" + person1.getName() + ":" + person1.getAge() +
                "," + person2.getName() + ":" + person2.getAge());
    }

    /**
     * Demo5: 通过Java反射机制操作成员变量, set 和 get
     */
    public static void Demo5() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Class class1 = null;
        class1 = Class.forName("xuan.ReflectDemo$Person");
        Object obj = class1.newInstance();

//        Field类代表类的成员变量，即属性
//        getFields()方法只能获取public的属性
        Field[] fields = class1.getFields();
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getName());
        }
//        getDeclaredFields()可以获取类的所有属性
        Field[] fields1 = class1.getDeclaredFields();
        for (int i = 0; i < fields1.length; i++) {
            System.out.println(fields1[i].getName());
        }
//        getDeclaredField(String s)获取特定的属性
        Field personNameField = class1.getDeclaredField("name");
        personNameField.setAccessible(true);
        personNameField.set(obj, "weixuan");

        System.out.println("Demo5:" + personNameField.get(obj));
    }

    /**
     * Demo6: 通过Java反射机制得到类的一些属性： 继承的接口，父类，函数信息，成员信息，类型等
     */

    public static void Demo6() throws ClassNotFoundException {
        Class class1 = null;
        class1 = Class.forName("xuan.ReflectDemo$SuperMan");

        //取得父类名称
        Class superClass = class1.getSuperclass();
        System.out.println("Demo6:SuperMan类的父类名：" + superClass.getName());

        //取得接口名称
        Class interFaces[] = class1.getInterfaces();
        for (int i = 0; i < interFaces.length; i++) {
            System.out.println("Demo6:SuperMan类实现的接口名：" + interFaces[i].getName());
        }

        //取得类方法
        Method method[] = class1.getDeclaredMethods();
    }

    /**
     * Demo7: 通过Java反射机制调用类方法
     *
     */
    public static void Demo7() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class class1 = null;
        class1 = Class.forName("xuan.ReflectDemo$SuperMan");

//        getDeclaredMethod()获取的是类自身声明的所有方法，包含public、protected和private方法。
//        getMethod()获取的是类的所有public方法，包括自身的 和从基类继承的、从接口实现的
        System.out.println("Demo7: \n调用无参方法fly()：");
        Method method = class1.getDeclaredMethod("fly");
        System.out.println(method.getName());
//        private方法只能获取信息，不能invoke
//        method.invoke(class1.newInstance());

        System.out.println("调用有参方法walk(int n)：");
        method = class1.getMethod("kill", int.class);
        method.invoke(class1.newInstance(), 100);
    }

    /**
     * Demo8: 通过Java反射机制得到类加载器信息
     */
    public static void Demo8() throws ClassNotFoundException {
        Class class1 = null;
        class1 = Class.forName("xuan.ReflectDemo$SuperMan");
        String nameString = class1.getClassLoader().getClass().getName();
        System.out.println("Demo8: 类加载器类名:" + nameString);
    }

    static class Person {

        private int age;
        private String name;

        public Person() {

        }

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class SuperMan extends Person implements ActionInterface {
        public boolean BlueBriefs;

        private void fly() {
            System.out.println("超人不会飞");
        }

        public boolean isBlueBriefs() {
            return BlueBriefs;
        }

        public void setBlueBriefs(boolean blueBriefs) {
            BlueBriefs = blueBriefs;
        }

        @Override
        public void kill(int n) {
            System.out.println("超人惩罚了" + n + "个坏蛋");
        }
    }

    interface ActionInterface {
        void kill(int n);
    }

}
