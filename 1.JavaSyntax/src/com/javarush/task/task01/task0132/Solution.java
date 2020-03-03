package com.javarush.task.task01.task0132;

/* 
Сумма цифр трехзначного числа
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(sumDigitsInNumber(546));
    }

    public static int sumDigitsInNumber(int number) {
        //напишите тут ваш код
        int numOnes = number % 10 ;
        int numTens = (number / 10) % 10;
        int numHundreds = number / 100;
        return numOnes + numTens + numHundreds;
    }
}