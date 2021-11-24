package com.example.demo.util;

import com.google.common.base.Stopwatch;
import com.google.common.primitives.Ints;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Haoran.Hua
 * @Description: TODO
 * @date 2021/11/23 3:39 下午
 */
public class NumberUtil {
    public static String fill(int num, int min, int max) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMinimumIntegerDigits(min);
        numberFormat.setMaximumFractionDigits(max);
        return numberFormat.format(num);
    }

    public static List<Integer> permutation(List<Integer> list) {
//        List<Integer> list = Ints.asList(a);
        Collections.shuffle(list);
        return list;
    }


    public static String transformNumber(Integer num) {
        String[] a = new String[]{"A", "B", "C", "D", "E"};
        int mod = num % 22;
        int div = num / 22;
        return a[div] + fill(mod, 2, 2);
    }

    public static int random(int min, int max, List<Integer> used) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Set<Integer> usedSet = new HashSet<>(used);
        int randomNumber = (int) Math.round(Math.random() * (max - min) + min);
        while (usedSet.contains(randomNumber)) {
            randomNumber = (int) Math.round(Math.random() * (max - min) + min);
        }
        stopwatch.stop();
        System.out.println(stopwatch);
        return randomNumber;
    }

    public static void main(String[] args) {
//        System.out.println(fill(10,2,2));
//        System.out.println(permutation(Ints.asList(0,1,2,3)));
//        List<Integer> list = new ArrayList<>();
//        for (int i = 1; i < 88; i++) {
//            list.add(i);
//        }
//        System.out.println(random(0,88,list));

        System.out.println(transformNumber(23));
    }
}
