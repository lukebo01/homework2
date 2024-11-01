package org.example;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // 1. Creazione dell'indice
        Directory indexDirectory = FSDirectory.open(Paths.get("lucene-index")); // Dove salvi l'indice
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(indexDirectory, config);

        // 2. Indicizzazione dei documenti HTML
        File htmlDir = new File("all_htmls");  // Assumendo che html_all sia la cartella con i file HTML
        for (File file : htmlDir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".html")) {
                indexHTMLDocument(writer, file);
            }
        }
        writer.close();  // Chiudi l'IndexWriter

        // 3. Ricerca
        DirectoryReader reader = DirectoryReader.open(indexDirectory);
        IndexSearcher searcher = new IndexSearcher(reader);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci una query (o 'exit' per uscire): ");
        while (true) {
            String queryString = scanner.nextLine();
            if (queryString.equals("exit")) break;

            Query query = new QueryParser("contenuto", analyzer).parse(queryString);
            TopDocs results = searcher.search(query, 10);
            for (ScoreDoc hit : results.scoreDocs) {
                Document doc = searcher.doc(hit.doc);
                System.out.println("Risultato trovato: " + doc.get("titolo"));
            }
        }

        reader.close();
        scanner.close();
    }

    // Metodo per indicizzare un singolo documento HTML
    private static void indexHTMLDocument(IndexWriter writer, File file) throws IOException {
        org.jsoup.nodes.Document htmlDoc = Jsoup.parse(file, "UTF-8");
        String titolo = htmlDoc.title();
        String contenuto = htmlDoc.body().text();

        Document luceneDoc = new Document();
        luceneDoc.add(new TextField("titolo", titolo, TextField.Store.YES));
        luceneDoc.add(new TextField("contenuto", contenuto, TextField.Store.YES));

        writer.addDocument(luceneDoc);
    }
}


