package com.nineya.lucene;

import com.nineya.lucene.entity.Post;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LuceneMain {
    private static final List<Post> POSTS = new ArrayList<Post>();
    // 索引存储位置
    private static final String INDEX_PATH = "./data/lucene/indexData";

    /**
     * 创建数据
     */
    private static void buildData() {
        POSTS.add(new Post(1L, "Dream 主题之 Halo 2.0 适配，以及适配前后的一些异同", "我的项目",
                new String[]{"dream"}, "经过一段时间的适配，目前 Dream 已经发布了基于 Halo 2.x 的第一个预发版本。"));

        POSTS.add(new Post(2L, "互联网新理念，对于WEB 3.0 你怎么看？", "生活",
                new String[]{"IDEA", "区块链"}, "WEB 3.0 这个名词走进大众视野已经有一段时间了，也曾在各个圈子里火热一时，至今各大互联网企业任旧在 WEB 3.0 上不断探索。"));

        POSTS.add(new Post(3L, "GCC编译环境升级部署", "运维",
                new String[]{"应用部署"}, "近期经常遇到使用源码编译的部署方式进行应用部署，在 GCC 编译环境上遇到各种问题，本文对升级部署 GCC 编译环境的流程以及遇到的一些问题进行记录。"));

        POSTS.add(new Post(4L, "有一片草原", "生活",
                new String[]{"故事"}, "从前，有一片广阔无垠的大草原，和风旭日，青草芳美。"));
    }

    // 创建索引
    public static void createIndex() {
        // 创建索引配置
        IndexWriterConfig config = new IndexWriterConfig(new SmartChineseAnalyzer());
        // 索引的打开方式：没有则创建，有则打开
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        // 指定目录创建索引
        try (Directory directory = FSDirectory.open(Paths.get(INDEX_PATH));
             IndexWriter indexWriter = new IndexWriter(directory, config)) {
            for (Post post : POSTS) {
                // 将数据转换成文档
                Document document = new Document();
                document.add(new StringField("title", post.getTitle(), Field.Store.YES));
                document.add(new TextField("content", post.getContent(), Field.Store.YES));
                // 加入到索引中
                indexWriter.addDocument(document);
            }
            // 将提交，保存到硬盘
            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询
     * @param keys
     * @return
     */
    public static void search(String keys) {
        try(DirectoryReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_PATH)))) {
            IndexSearcher searcher = new IndexSearcher(reader);
            // 组合查询
            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            // 给所title项指定查询关键词
            builder.add(new QueryParser("title", new SmartChineseAnalyzer()).parse(keys), BooleanClause.Occur.MUST);
            BooleanQuery query = builder.build();
            // 获取符合条件的前两条记录
            TopDocs docs = searcher.search(query, 2);

            System.out.println("符合条件的条数为：" + docs.totalHits);
            // 解析查询结果
            for (ScoreDoc scoreDoc : docs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                System.out.println("title = " + doc.get("title"));
                System.out.println("- content = " + doc.get("content"));
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 创建数据
        buildData();
        // 创建索引
        createIndex();
        // 查询
        Scanner in = new Scanner(System.in);
        System.out.print("请输入查询关键词：");
        while (in.hasNext()) {
            String keys = in.next();
            search(keys);
            System.out.print("请输入查询关键词：");
        }
    }
}
