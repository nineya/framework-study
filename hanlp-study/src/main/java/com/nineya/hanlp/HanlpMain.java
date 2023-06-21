package com.nineya.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.util.List;

public class HanlpMain {
    public static void main(String[] args) {
        String words = "逐梦2023：个人博客的维护之路——关于梦与浪漫的每一个决策，都让我满怀欣喜";
        List<Term> terms = HanLP.segment(words);
        System.out.println(terms);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            HanLP.segment(words);
        }
        long end = System.currentTimeMillis();
        System.out.printf("分词器性能：%dms\n", end - start);
    }
}
