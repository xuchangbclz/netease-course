package com.netease.course.neteasecourse.JDK8.FunctionInterface;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 函数式接口
 *
 * @Author daituo
 * @Date
 **/
public class FunctionalInterfaceTest {

    public static void main(String[] args) {

        getBoolean("hello", value -> value.length() > 2, value -> value.length() > 6);


    }

    public static boolean getBoolean(String str1, Predicate<String> predicate1, Predicate<String> predicate2){
        boolean test = predicate1.or(predicate2).test(str1);
        System.out.println(test); //输出true , 分别是||,&&和取反操作

        test = predicate1.and(predicate2).test(str1);
        System.out.println(test);//输出false

        test = predicate1.negate().test(str1);
        System.out.println(test);//输出false
        return test;
    }



}
