## Autori
- **Luca Borrelli, matricola 559443**
- **Rainer Cabral Ilao, matricola 560695**

Descrizione del Progetto
Questo progetto ha come obiettivo la creazione di un motore di ricerca per documenti HTML utilizzando Apache Lucene. 
I documenti HTML sono indicizzati e possono essere successivamente ricercati tramite un'interfaccia a riga di comando. 
L'applicazione è progettata per indicizzare i documenti presenti in una directory specificata e fornire un'interfaccia per eseguire query sui contenuti indicizzati.

## Struttura del Progetto
Il progetto è strutturato in tre package principali:
- **indexer**: contiene le classi per l'indicizzazione dei documenti HTML
- **searcher**: contiene le classi per la ricerca dei documenti indicizzati
- **utils**: contiene le classi di utilità per la gestione dei file e delle directory
- **main**: contiene la classe principale dell'applicazione
- **resources**: contiene i file di configurazione dell'applicazione
- **test**: contiene le classi di test per i package indexer e searcher
- **all_htmls**: contiene i documenti HTML da indicizzare
- **lib**: contiene le librerie esterne necessarie per l'esecuzione del progetto

## Requisiti
Per eseguire il progetto è necessario avere installato Java 23 SDK e Maven.

## Installazione
Per installare il progetto è necessario clonare la repository da GitHub e importare il progetto in un IDE come IntelliJ IDEA.
Successivamente, è necessario eseguire il comando `mvn clean install` per scaricare le dipendenze del progetto.

## Utilizzo
Per eseguire il progetto è necessario eseguire la classe `Main` presente nel package `main`.
L'applicazione fornisce un'interfaccia a riga di comando per indicizzare i documenti HTML presenti nella directory `all_htmls` e per eseguire query sui documenti indicizzati.

## Esempio
Esempio di esecuzione dell'applicazione:
```
Benvenuto in Search Engine!
Cosa vuoi fare?

1. Indicizzare i documenti HTML
2. Eseguire una query
3. Esci

Inserisci il numero corrispondente all'azione che vuoi eseguire: 1
Indicizzazione dei documenti HTML in corso...
Documenti indicizzati con successo!
Cosa vuoi fare?

1. Indicizzare i documenti HTML
2. Eseguire una query
3. Esci

Inserisci il numero corrispondente all'azione che vuoi eseguire: 2
Inserisci la query da eseguire: search engine
Risultati della query:
- Documento: 1
- Documento: 2
- Documento: 3
- Documento: 4
- Documento: 5
...
```

