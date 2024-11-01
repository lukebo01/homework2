package org.example;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class HtmlIndexer {
    public static void main(String[] args) {
        // Definire il percorso dell'indice
        String indexPath = "lucene-index"; // Cambia il percorso se necessario

        try {
            // Creare un Directory per l'indice
            Directory directory = FSDirectory.open(Paths.get(indexPath));

            // Configurare l'IndexWriter
            IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
            IndexWriter writer = new IndexWriter(directory, config);

            // Percorso della cartella contenente i file HTML
            File htmlDir = new File("all_htmls");
            File[] htmlFiles = htmlDir.listFiles((dir, name) -> name.endsWith(".html"));

            if (htmlFiles != null) {
                for (File htmlFile : htmlFiles) {

                    // Leggere il file HTML e creare un documento Lucene
                    org.jsoup.nodes.Document jsoupDoc = Jsoup.parse(htmlFile, "UTF-8");
                    String title = jsoupDoc.title(); // Estrai il titolo
                    String content = jsoupDoc.body().text(); // Estrai il contenuto

                    // Stampare in console il nome del file parsato, il titolo e il body estratto
                    System.out.println("File: " + htmlFile.getName());
                    System.out.println("Titolo: " + title);
                    System.out.println("Contenuto: " + content);
                    // Creare un documento Lucene
                    Document doc = new Document();
                    doc.add(new TextField("titolo", title, Field.Store.YES));
                    doc.add(new TextField("contenuto", content, Field.Store.YES));

                    // Aggiungere il documento all'indice
                    writer.addDocument(doc);
                }
            }

            // Commettere le modifiche e chiudere l'IndexWriter
            writer.commit();
            writer.close();
            directory.close();
            System.out.println("Indicizzazione completata!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
