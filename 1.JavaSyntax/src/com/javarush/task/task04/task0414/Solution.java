package com.javarush.task.task04.task0414;

/* 
Количество дней в году
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
    public static void main(String[] args) throws Exception {
        //напишите тут ваш код
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int yearNumber = Integer.parseInt(bufferedReader.readLine());
        if (yearNumber % 4 == 0 && !(yearNumber % 100 == 0 && yearNumber % 400 != 0))
            System.out.println("количество дней в году: 366");
        else
            System.out.println("количество дней в году: 365");

    }
}