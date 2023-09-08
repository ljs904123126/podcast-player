package org.liaimei.podcast.player.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hankcs.lucene.HanLPAnalyzer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LuceneCreateTools {

    public static String simpleIndexPath = "D:/podcast/index/simple";
    public static String websterIndexPath = "D:/podcast/index/webster";
    public static String inputDir = "C:/Users/ljs/BaiduSyncdisk/work/ECDICT";

    public static Map<String, String[]> getLemmaMap() throws IOException {
        String lemmaFile = inputDir + File.separator + "lemma.en.txt";
        Map<String, String[]> reMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(lemmaFile))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(";")) {
                    continue;
                }
                if (StringUtils.isEmpty(line)) {
                    continue;
                }
                String[] split = line.split("->");
                reMap.put(split[0].split("/")[0], split[1].trim().split(","));
            }
        }
        return reMap;
    }

    /*
        | 字段        | 解释                                                       |
        | ----------- | ---------------------------------------------------------- |
        | word        | 单词名称                                                   |
        | phonetic    | 音标，以英语英标为主                                       |
        | definition  | 单词释义（英文），每行一个释义                             |
        | translation | 单词释义（中文），每行一个释义                             |
        | pos         | 词语位置，用 "/" 分割不同位置                              |
        | collins     | 柯林斯星级                                                 |
        | oxford      | 是否是牛津三千核心词汇                                     |
        | tag         | 字符串标签：zk/中考，gk/高考，cet4/四级 等等标签，空格分割 |
        | bnc         | 英国国家语料库词频顺序                                     |
        | frq         | 当代语料库词频顺序                                         |
        | exchange    | 时态复数等变换，使用 "/" 分割不同项目，见后面表格          |
        | detail      | json 扩展信息，字典形式保存例句（待添加）                  |
        | audio       | 读音音频 url （待添加）                                    |
     */
    public static void initSimple() {
        String dicCsv = inputDir + File.separator + "ecdict.csv";
        IndexWriter writter = null;
        //read file by line
        try (Reader reader = new FileReader(dicCsv);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {


            Directory directory = FSDirectory.open(Paths.get(simpleIndexPath));
//            StandardAnalyzer analyzer = new StandardAnalyzer();
            Analyzer analyzer = new HanLPAnalyzer();
            ;
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            writter = new IndexWriter(directory, indexWriterConfig);

            FieldType storedFieldType = new FieldType();
            storedFieldType.setStored(true); // 存储字段
            storedFieldType.setIndexOptions(IndexOptions.NONE);

            Map<String, String[]> lemmaMap = getLemmaMap();

            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.getRecordNumber() == 1L) {
                    continue;
                }
                String word = csvRecord.get(0);
                String phonetic = csvRecord.get(1);
                String[] translations = csvRecord.get(3).split("\\\\n");
                String pos = csvRecord.get(4);
                String exchange = csvRecord.get(10);

                Document document = new Document();
                document.add(new StringField("word", word, TextField.Store.YES));
                if (lemmaMap.containsKey(word)) {
                    for (String s : lemmaMap.get(word)) {
                        document.add(new StringField("alias", s, TextField.Store.YES));
                    }
                }
                for (String translation : translations) {
                    document.add(new StringField("translation", translation, TextField.Store.YES));
                }
                //存储字段
                document.add(new Field("phonetic", phonetic, storedFieldType));
                document.add(new Field("pos", pos, storedFieldType));
                document.add(new Field("exchange", exchange, storedFieldType));
                writter.addDocument(document);
//                System.out.println("word: " + word + " phonetic: " + phonetic + " translations: " + csvRecord.get(3) + " pos: " + pos + " exchange: " + exchange); // 打印每一行
            }
            writter.flush();
            writter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void initWebster() {
        String websterPath = inputDir + File.separator + "webster.txt";
        IndexWriter writter = null;
        FieldType storedFieldType = new FieldType();
        storedFieldType.setStored(true); // 存储字段
        storedFieldType.setIndexOptions(IndexOptions.NONE);
        //read file by line
        try (BufferedReader br = new BufferedReader(new FileReader(websterPath))) {
            Directory directory = FSDirectory.open(Paths.get(websterIndexPath));
            StandardAnalyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            writter = new IndexWriter(directory, indexWriterConfig);
            String line;
            String[] temp = new String[2];
            while ((line = br.readLine()) != null) {


                if (StringUtils.equals(line, "</>")) {
                    Document document = new Document();
                    document.add(new StringField("word", temp[0], TextField.Store.YES));
                    document.add(new Field("html", temp[1], storedFieldType));
                    writter.addDocument(document);
                    continue;
                }
                if (StringUtils.startsWith(line, "<")) {
                    temp[1] = line;
                } else {
                    temp[0] = line;
                }
            }
            writter.flush();
            writter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void create() throws IOException {

        //read json file
//        File file = new File("D:/podcast/index");
        Directory directory = FSDirectory.open(Paths.get("D:/podcast/index"));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter writter = new IndexWriter(directory, indexWriterConfig);
        String json = FileUtils.readFileToString(new File("D:/work/java/podcast_player/data/collins.json"), "UTF-8");
        JSONObject jsonObject = JSON.parseObject(json);

        FieldType storedFieldType = new FieldType();
        storedFieldType.setStored(true); // 存储字段
        storedFieldType.setIndexOptions(IndexOptions.NONE);

        FieldType multiValuedFieldType = new FieldType();
        multiValuedFieldType.setStored(false);

        for (String key : jsonObject.keySet()) {
            JSONObject v = jsonObject.getJSONObject(key);

            Document document = new Document();

            document.add(new StringField("id", key, TextField.Store.YES));
            document.add(new StringField("word", key, TextField.Store.YES));


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
        Directory directory = FSDirectory.open(Paths.get("D:/podcast/index"));
        StandardAnalyzer analyzer = new StandardAnalyzer();
//        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
//        Query query = new QueryParser(inField, analyzer)
//                .parse(queryString);

        TermQuery query = new TermQuery(new Term(inField, queryString));

        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(indexReader);

//        query.setRewriteMethod(MultiTermQuery.SCORING_BOOLEAN_REWRITE);
//        ScoreDoc[] hits = searcher.search(query, 10).scoreDocs;
//        for (ScoreDoc hit : hits) {
//            ScoreDoc doc = searcher.doc(hit.doc);
//            System.out.printf("%s:%.2f => %s\n", doc.get("url"), hit.score, doc.get("title"));
//        }


        TopDocs topDocs = searcher.search(query, 1000);
        List<Document> documents = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            System.out.printf("%s:%.2f => %s\n", doc.get("id"), scoreDoc.score, doc.get("doc"));
            documents.add(doc);
        }

        return documents;
    }

    public static void main(String[] args) throws IOException, ParseException {
//        initSimple();
        initWebster();
    }

}
