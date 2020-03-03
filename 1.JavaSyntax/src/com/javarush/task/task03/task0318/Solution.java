package com.javarush.task.task03.task0318;

/* 
План по захвату мира
*/

import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        //напишите тут ваш код
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String nAge = bufferedReader.readLine();
        String name = bufferedReader.readLine();
        System.out.println(name + " захватит мир через " + nAge + " лет. Му-ха-ха!");
    }
}
