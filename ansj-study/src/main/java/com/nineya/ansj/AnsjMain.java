package com.nineya.ansj;import org.ansj.splitWord.analysis.BaseAnalysis;import org.ansj.splitWord.analysis.NlpAnalysis;import org.ansj.splitWord.analysis.ToAnalysis;public class AnsjMain {    public static void main(String[] args) {        String words = "逐梦2023：个人博客的维护之路——关于梦与浪漫的每一个决策，都让我满怀欣喜";        System.out.println("基础分词器");        System.out.println(BaseAnalysis.parse(words).toStringWithOutNature());        System.out.println("精准分词器");        System.out.println(ToAnalysis.parse(words).toStringWithOutNature());        System.out.println("nlp分词器");        System.out.println(NlpAnalysis.parse(words).toStringWithOutNature());        long start = System.currentTimeMillis();        for (int i = 0; i < 10000; i++) {            BaseAnalysis.parse(words).toStringWithOutNature();        }        long end = System.currentTimeMillis();        System.out.printf("基础分词器性能：%dms\n", end - start);        start = end;        for (int i = 0; i < 10000; i++) {            ToAnalysis.parse(words).toStringWithOutNature();        }        end = System.currentTimeMillis();        System.out.printf("精准分词器性能：%dms\n", end - start);        start = end;        for (int i = 0; i < 10000; i++) {            NlpAnalysis.parse(words).toStringWithOutNature();        }        end = System.currentTimeMillis();        System.out.printf("nlp分词器性能：%dms\n", end - start);    }}