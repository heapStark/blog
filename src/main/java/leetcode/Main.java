package leetcode;

import java.util.Arrays;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/9.
 */
public class Main {

    public static void main(String... args) {
        System.out.println(new Main().candy(new int[]{5, 3, 1}));
    }

    /**
     * 从两头扫描
     *
     * @param ratings
     * @return
     */
    public int candy(int[] ratings) {
        int[] score = new int[ratings.length];
        score[0] = 1;
        for (int a = 1; a < ratings.length; a++) {
            if (ratings[a] == ratings[a - 1]) {
                score[a] = 1;
            } else if (ratings[a] > ratings[a - 1]) {
                score[a] = score[a - 1] + 1;
            } else if (ratings[a] < ratings[a - 1]) {
                score[a] = 1;
                if (score[a - 1] == 1) {
                    score[a - 1] = 2;
                    for (int i = a - 1; i > 0; i--) {
                        if (ratings[i - 1] > ratings[i] && score[i - 1] < score[i]) {
                            score[i - 1] = 1 + score[i];
                        }
                    }
                }
            }
        }
        int k = 0;
        for (int a = 0; a < ratings.length; a++) {
            k = k + score[a];
        }
        return k;
    }

    /**
     * 两头扫描，rate相等对大小没有要求
     *
     * @param ratings
     * @return
     */
    public int candy2(int[] ratings) {
        int n = ratings.length;
        if (n == 0 || ratings == null)
            return 0;
        int[] count = new int[n];
        int sum = 0;
        //初始，每个孩子至少有一颗糖
        Arrays.fill(count, 1);
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                count[i] = count[i - 1] + 1;
            }
        }
        for (int i = n - 1; i > 0; i--) {
            if (ratings[i] < ratings[i - 1] && count[i] >= count[i - 1]) {
                count[i - 1] = count[i] + 1;
            }
            sum += count[i];
        }
        sum += count[0];
        return sum;

    }

    /**
     * 回文字符串
     * @param s
     * @return
     */
    public boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            while ( ! isLetterOrNumber(s.charAt(i)) && i < j)
                i ++;
            while ( ! isLetterOrNumber(s.charAt(j)) && i < j)
                j --;
            if(i < j && Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j))) return false;
            i ++;
            j --;
        }
        return true;
    }

    public static boolean isLetterOrNumber(char i) {
        if(i >= '0' && i <= '9' || i >= 'a' && i <= 'z' || i >= 'A' && i <= 'Z') return true;
        return false;
    }
}
