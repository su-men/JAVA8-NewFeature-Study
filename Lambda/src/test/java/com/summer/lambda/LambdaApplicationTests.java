package com.summer.lambda;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SpringBootTest
class LambdaApplicationTests {

    /*-----------------------------------------test 1:lambda-------------------------------------------------------*/

    //自定义定义函数式接口
    @FunctionalInterface
    public interface MyFuncInterf<T>{
         void work(String origin);
    }

    //自定义定义调用函数式接口的方法1
    public void printString(MyFuncInterf<String> mf, String origin){
        mf.work(origin);
    }

    //自定义定义调用函数式接口的方法2
    public void toLowerString(MyFuncInterf<String> mf, String origin){
        mf.work(origin);
    }




    @Test
    void test1() {
        //业务通过Lambda表达式传入
        toLowerString((str)->{
            System.out.println(str.toLowerCase());
        },"ABC");

        printString(System.out::println, "ABC");
    }


    /*-----------------------------------------test 2:consumer-------------------------------------------------------*/

    @Test
    public void test2(){
        Consumer<String> consumer1 = (str) -> {System.out.println("Tips1：" + str + " 写入日志");};
        Consumer<String> consumer2 = (str) -> {System.out.println("Tips2：" + str + " 写入数据库");};
        Consumer<String> consumer3 = (str) -> {System.out.println("Tips3：" + str + " 给前端返回结果");};

        Consumer<String> consumer = consumer1.andThen(consumer2).andThen(consumer3);

        consumer.accept("修改用户名操作");
    }



    /*-----------------------------------------test 3:Supplier-------------------------------------------------------*/
    static class Person{
        Integer id;
        String name;
        String sex;
        Integer age;

        public Person(Integer id, String name, String sex, Integer age) {
            this.id = id;
            this.name = name;
            this.sex = sex;
            this.age = age;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSex() {
            return sex;
        }

        public Integer getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", age=" + age +
                    '}';
        }
    }


    @Test
    void test3() {
        Supplier<List<Person>> supplier= ()->{
            List<Person> personList = new ArrayList<Person>();
            personList.add(new Person(1,"张三","男",38));
            personList.add(new Person(2,"小小","女",2));
            personList.add(new Person(3,"李四","男",65));
            return personList;
        };

        List<Person> personList = supplier.get();
        personList.forEach(System.out::println);

    }
    /*-----------------------------------------test 4:function-------------------------------------------------------*/
    @Test
    void test4() {
        Consumer<Integer> print = System.out::println;
        Function<Integer[],Integer> and = (args) -> (args[0]) + (args[1]);
        Function<Integer[],Integer> multi = (args) -> (args[0]) * (args[1]);

        //calculate: 4+5
        Integer[] integers1 = {4,5};
        print.accept(and.apply(integers1));

        //calculate: 2*3
        Integer[] integers2 = {2,3};
        print.accept(multi.apply(integers2));

    }



















    /*-----------------------------------------test 4:function-------------------------------------------------------*/
    @Test
    void test13() {
        Comparator<Integer> com= Integer::compare;
        System.out.println(com.compare(1,2));


        Map<String, List<String>> map = new HashMap<>();
        List<String> list;

        list = map.get("key");
        if (list == null) {
            list = new LinkedList<>();
            map.put("key", list);
        }
        list.add("11");
// 使用 computeIfAbsent 可以这样写 如果key返回部位空则返回该集合 ，为空则创建集合后返回
        list = map.computeIfAbsent("key", k -> new ArrayList<>());
        list.add("11");



        List<Person> persionList = new ArrayList<Person>();
        persionList.add(new Person(1,"张三","男",38));
        persionList.add(new Person(2,"小小","女",2));
        persionList.add(new Person(3,"李四","男",65));


        //1、只取出该集合中所有姓名组成一个新集合（将Person对象转为String对象）
        List<String> nameList=persionList.stream().map(x -> x.getName()).collect(Collectors.toList());
        System.out.println(nameList.toString());

        // 已知的知识来解决需求
        List<String> list1 = new ArrayList<>();
        list1.add("张老三");
        list1.add("张小三");
        list1.add("李四");
        list1.add("赵五");
        list1.add("张六");
        list1.add("王八");

        List<String> list2 = list1.stream().filter(x -> x.startsWith("张")).filter(x -> x.length() == 3).collect(Collectors.toList());
        list2.forEach(x -> System.out.println(x));
    }

}
