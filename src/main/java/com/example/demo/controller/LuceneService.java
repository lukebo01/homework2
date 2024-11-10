package com.example.demo.controller;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;



import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.example.demo.*;
import com.example.demo.model.*;

public class LuceneService {
    public List<SearchResult> search(String searchField, String queryString) {
        List<SearchResult> resultsList = new ArrayList<>();
        try {
            Directory indexDirectory = FSDirectory.open(Paths.get("lucene-index"));
            Map<String, Analyzer> analyzerPerField = new HashMap<>();
            // Mappa degli analyzer per ciascun campo (presa da CustomAnalyzers)
            analyzerPerField.put("filename", CustomAnalyzers.getSimpleAnalyzer());
            analyzerPerField.put("title", CustomAnalyzers.getTitleAnalyzer());
            analyzerPerField.put("content", CustomAnalyzers.getContentAnalyzer());
            analyzerPerField.put("authors", CustomAnalyzers.getSimpleAnalyzer());
            analyzerPerField.put("abstract", CustomAnalyzers.getAbstractAnalyzer());
            analyzerPerField.put("bibliographies", CustomAnalyzers.getBibliographyAnalyzer());

            // Lettura dell'indice e creazione dell'IndexSearcher
            DirectoryReader reader = DirectoryReader.open(indexDirectory);
            IndexSearcher searcher = new IndexSearcher(reader);

            Analyzer analyzer = analyzerPerField.get(searchField);

            QueryParser parser = new QueryParser(searchField, analyzer);
            Query query = parser.parse(queryString);

            TopDocs results = searcher.search(query, 10);
            ScoreDoc[] hits = results.scoreDocs;

            for (ScoreDoc hit : hits) {
                Document doc = searcher.doc(hit.doc);

                String filename = doc.get("filename");
                float score = hit.score;
                String title = doc.get("title");
                String authors = doc.get("authors");
                String abstractText = doc.get("abstract") != null ? doc.get("abstract") : "N/A";
                String contentSnippet = doc.get("content") != null
                        ? doc.get("content").substring(0, Math.min(100, doc.get("content").length())) + "..."
                        : "N/A";
                String bibliographiesSnippet = doc.get("bibliographies") != null
                        ? doc.get("bibliographies").substring(0, Math.min(100, doc.get("bibliographies").length())) + "..."
                        : "N/A";

                // Create a SearchResult object and add it to the results list
                SearchResult searchResult = new SearchResult(filename, score, title, authors, abstractText, contentSnippet,
                        bibliographiesSnippet);
                resultsList.add(searchResult);
            }
            return resultsList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //------------------------------------------- RICERCHE AVANZATE PER IL TESTING ------------------------------------

    // funzione per List<SearchResult> results = luceneService.searchWithSimilarity("content", query, similarity);
    public List<SearchResult> searchWithSimilarity(String searchField, String queryString, Similarity similarity) {
        List<SearchResult> resultsList = new ArrayList<>();
        try {
            Directory indexDirectory = FSDirectory.open(Paths.get("lucene-index"));
            Map<String, Analyzer> analyzerPerField = new HashMap<>();
            // Mappa degli analyzer per ciascun campo (presa da CustomAnalyzers)
            analyzerPerField.put("filename", CustomAnalyzers.getSimpleAnalyzer());
            analyzerPerField.put("title", CustomAnalyzers.getTitleAnalyzer());
            analyzerPerField.put("content", CustomAnalyzers.getContentAnalyzer());
            analyzerPerField.put("authors", CustomAnalyzers.getSimpleAnalyzer());
            analyzerPerField.put("abstract", CustomAnalyzers.getAbstractAnalyzer());
            analyzerPerField.put("bibliographies", CustomAnalyzers.getBibliographyAnalyzer());

            // Lettura dell'indice e creazione dell'IndexSearcher
            DirectoryReader reader = DirectoryReader.open(indexDirectory);
            IndexSearcher searcher = new IndexSearcher(reader);
            searcher.setSimilarity(similarity);

            Analyzer analyzer = analyzerPerField.get(searchField);

            QueryParser parser = new QueryParser(searchField, analyzer);
            Query query = parser.parse(queryString);

            TopDocs results = searcher.search(query, 10);
            ScoreDoc[] hits = results.scoreDocs;

            for (ScoreDoc hit : hits) {
                Document doc = searcher.doc(hit.doc);

                String filename = doc.get("filename");
                float score = hit.score;
                String title = doc.get("title");
                String authors = doc.get("authors");
                String abstractText = doc.get("abstract") != null ? doc.get("abstract") : "N/A";
                String contentSnippet = doc.get("content") != null
                        ? doc.get("content").substring(0, Math.min(100, doc.get("content").length())) + "..."
                        : "N/A";
                String bibliographiesSnippet = doc.get("bibliographies") != null
                        ? doc.get("bibliographies").substring(0, Math.min(100, doc.get("bibliographies").length())) + "..."
                        : "N/A";

                // Create a SearchResult object and add it to the results list
                SearchResult searchResult = new SearchResult(filename, score, title, authors, abstractText, contentSnippet,
                        bibliographiesSnippet);
                resultsList.add(searchResult);

            }
            return resultsList;
        } catch (IOException e ) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();

            return null;
        }
        return null;
    }
}
