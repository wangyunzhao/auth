package com.example.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class AuthApplicationTests {

    @Test
    void contextLoads() {

    }

    public static void main(String[] args) {
//        String[] a = "wangy,unzhao,007/9*//*/".split(",");
//
//        List<String> b = new ArrayList<>();
//
//        for (String c: a
//             ) {
//            System.out.println(c);
//        }
//        System.out.println();


        String d = "//w";
        Pattern p = Pattern.compile(".*abc.*");
        System.out.println(Pattern.compile(".*abc.*").matcher(d).matches());
    }

}
