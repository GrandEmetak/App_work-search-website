package ru.job4j.dream.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Temp {
    public static int sum(int start, int finish) {
        int sum = 0;
        for (start = 0; start <= finish; start++) {
            sum = sum + start;

        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(sum(0, 5));
        System.out.println(sum(3, 8));
        System.out.println(sum(1, 1));
        System.out.println(sum(7, 11));
        System.out.println(sum(7, 3));
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE, dd. MMM yyyy.");
       var f = localDateTime.format(dateTimeFormatter);
        System.out.println(f);
        LocalDateTime localDateTime1 = LocalDateTime.now();
        //localDateTime1 = f;
    }
}
