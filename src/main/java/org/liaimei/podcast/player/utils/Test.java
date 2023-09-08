package org.liaimei.podcast.player.utils;

import com.hankcs.lucene.HanLPAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {


    public static void create() throws IOException {

        //read json file
//        File file = new File("D:\\podcast\\index");
        Directory directory = FSDirectory.open(Paths.get("D:\\podcast\\test_index"));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter writter = new IndexWriter(directory, indexWriterConfig);

        FieldType storedFieldType = new FieldType();
        storedFieldType.setStored(true); // 存储字段
        storedFieldType.setIndexOptions(IndexOptions.NONE); // 不索引字段

        FieldType multiValuedFieldType = new FieldType();
        multiValuedFieldType.setStored(false); // 存储字段


        String[] split = "more dynamic ways of finding information".split(" ");

        for (String key : split) {
            Document document = new Document();
            document.add(new StringField("id", key, TextField.Store.YES));
            document.add(new StringField("word", key, TextField.Store.YES));

            for (int j = 0; j < 3; j++) {
                document.add(new StringField("word", key + j, TextField.Store.YES));
            }
            try {
                writter.addDocument(document);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        writter.close();
        System.out.println("test");
    }

    public static String simpleIndexPath = "D:/podcast/index/simple";
    public static String websterIndexPath = "D:/podcast/index/webster";
    public static String inputDir = "C:/Users/ljs/BaiduSyncdisk/work/ECDICT";

    public static void search() throws IOException {
        Directory directory = FSDirectory.open(Paths.get(simpleIndexPath));
//            StandardAnalyzer analyzer = new StandardAnalyzer();
        ;

        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(indexReader);

        TermQuery query = new TermQuery(new Term("word", "manning"));


        TopDocs topDocs = searcher.search(query, 1000);
        List<Document> documents = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            System.out.printf("%s:%.2f => %s   translation %s\n", doc.get("word"), scoreDoc.score
                    , Arrays.stream(doc.getValues("alias")).collect(Collectors.joining(","))
                    , Arrays.stream(doc.getValues("translation")).collect(Collectors.joining(","))
            );
            documents.add(doc);
        }

    }


    public static void searchWebster() throws IOException {
        Directory directory = FSDirectory.open(Paths.get(websterIndexPath));
//            StandardAnalyzer analyzer = new StandardAnalyzer();
        ;

        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(indexReader);

        TermQuery query = new TermQuery(new Term("word", "man"));


        TopDocs topDocs = searcher.search(query, 1000);
        List<Document> documents = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            System.out.printf("%s => %s  \n", doc.get("word"),doc.get("html"));
            documents.add(doc);
        }

    }

    public static void hanlpTest() throws IOException {
        String text = "中华人民共和国很辽阔";
        for (int i = 0; i < text.length(); ++i) {
            System.out.print(text.charAt(i) + "" + i + " ");
        }
        System.out.println();
        Analyzer analyzer = new HanLPAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("field", text);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
            // 偏移量
            OffsetAttribute offsetAtt = tokenStream.getAttribute(OffsetAttribute.class);
            // 距离
            PositionIncrementAttribute positionAttr = tokenStream.getAttribute(PositionIncrementAttribute.class);
            // 词性
            TypeAttribute typeAttr = tokenStream.getAttribute(TypeAttribute.class);
            System.out.printf("[%d:%d %d] %s/%s\n", offsetAtt.startOffset(), offsetAtt.endOffset(), positionAttr.getPositionIncrement(), attribute, typeAttr.type());
        }
    }

    public static void main(String[] args) throws IOException {
//        create();
        searchWebster();
//        hanlpTest();
    }
}
