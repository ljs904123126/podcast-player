package org.liaimei.podcast.player.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LuceneCreateTools {


    public static void create() throws IOException {

        //read json file
//        File file = new File("D:\\podcast\\index");
        Directory directory = FSDirectory.open(Paths.get("D:\\podcast\\index"));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter writter = new IndexWriter(directory, indexWriterConfig);
        String json = FileUtils.readFileToString(new File("D:\\work\\java\\podcast_player\\data\\collins.json"), "UTF-8");
        JSONObject jsonObject = JSON.parseObject(json);

        FieldType storedFieldType = new FieldType();
        storedFieldType.setStored(true); // 存储字段
        storedFieldType.setIndexOptions(IndexOptions.NONE); // 不索引字段

        FieldType multiValuedFieldType = new FieldType();
        multiValuedFieldType.setStored(false); // 存储字段

        for (String key : jsonObject.keySet()) {
            JSONObject v = jsonObject.getJSONObject(key);

            Document document = new Document();

            document.add(new TextField("id", key, TextField.Store.YES));
            document.add(new TextField("word", key, TextField.Store.YES));


            JSONArray defs = v.getJSONArray("defs");
            for (int i = 0; i < defs.size(); i++) {
                JSONObject def = defs.getJSONObject(i);
                String defCn = def.getString("def_cn");
                if (StringUtils.isNotEmpty(defCn)) {
                    document.add(new TextField("cn_word", defCn, TextField.Store.YES));
                }
            }

            document.add(new Field("doc", v.toString(), storedFieldType));
            try {
                writter.addDocument(document);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        writter.close();
        System.out.println("test");
    }


    public static List<Document> searchIndex(String inField, String queryString) throws IOException, ParseException {
        Directory directory = FSDirectory.open(Paths.get("D:\\podcast\\index"));
        StandardAnalyzer analyzer = new StandardAnalyzer();
//        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        Query query = new QueryParser(inField, analyzer)
                .parse(queryString);

        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        TopDocs topDocs = searcher.search(query, 10);
        List<Document> documents = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            documents.add(searcher.doc(scoreDoc.doc));
        }

        return documents;
    }

    public static void main(String[] args) throws IOException, ParseException {
        create();
        List<Document> documents = searchIndex("cn_word", "中国");
        for (Document document : documents) {
            System.out.println("word: " + document.getValues("word")[0] + "\t" + document.getValues("doc")[0]);
        }
        System.out.println("----------------------------------------");

        documents = searchIndex("word", "China");
        for (Document document : documents) {
            System.out.println("word: " + document.getValues("word")[0] + "\t" + document.getValues("doc")[0]);
        }
    }

}
