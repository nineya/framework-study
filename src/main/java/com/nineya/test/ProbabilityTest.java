package com.nineya.test;

/**
 * @author 殇雪话诀别
 * @date 2023/8/12 23:56
 */
public class ProbabilityTest {

    /**
     * 倍投法测试
     * @param args
     */
    public static void main(String[] args) {
        double win = 0;
        int totalMoney = 0;
        for (int k = 0; k < 1000; k++) {
            int money = 0;
            int infuse = 10;
            double i = 0;
            double n = 0;
            while (money >-300000 && money < 300000) {
                n++;
                if (Math.random() <= 0.5) {
                    money -= infuse;
                    System.out.printf("输：money = %d, infuse = %d, rate = %.2f%%, n = %.0f, win = %.2f, k = %d, totalMoney = %d\n", money, infuse,  i * 100 / n, n, (win * 100 / k), k, totalMoney);
                    infuse *= 2;
                } else {
                    money += infuse;
                    i++;
                    System.out.printf("赢：money = %d, infuse = %d, rate = %.2f%%, n = %.0f, win = %.2f, k = %d, totalMoney = %d\n", money, infuse,  i * 100 / n, n, (win * 100 / k), k, totalMoney);
                    infuse = 10;
                }
            }
            totalMoney += money;
            if (money > 0) {
                win++;
            }
        }
    }
}
