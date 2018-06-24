/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

/**
 *
 * @author danielmunoz
 */
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * Chunker Example in Apache OpenNLP
 */
public class Inicio {

    public void SepararFrases() {
        SentenceDetector sentenceDetector = null;
        InputStream modelIn = null;

        try {
            modelIn = getClass().getResourceAsStream("en-sent.bin");
            final SentenceModel sentenceModel = new SentenceModel(modelIn);
            modelIn.close();
            sentenceDetector = new SentenceDetectorME(sentenceModel);
        } catch (final IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (modelIn != null) {
                try {
                    modelIn.close();
                } catch (final IOException e) {
                }
            }
        }
        String sentences[] = (sentenceDetector.sentDetect("I am Juan. I am engineer. I like travelling and driving"));
        for (int i = 0; i < sentences.length; i++) {
            System.out.println(sentences[i]);
        }
    }

    public static InputStream tokenModelIn;
    public static InputStream posModelIn = null;
    public static TokenizerModel tokenModel;
    public static TokenizerME tokenizer;

    public static POSModel posModel;
    public static POSTaggerME posTagger;

    Inicio() throws IOException {
        try {
            tokenModelIn = new FileInputStream("en-token.bin");
            tokenModel = new TokenizerModel(tokenModelIn);
            tokenizer = new TokenizerME(tokenModel);

            posModelIn = new FileInputStream("en-pos-maxent.bin");
            posModel = new POSModel(posModelIn);
            posTagger = new POSTaggerME(posModel);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String sentence;
    
    public static int ActivoPasivo(String frase) {
        sentence = frase;
        String tokens[] = tokenizer.tokenize(sentence);
        String tags[] = posTagger.tag(tokens);
        double probs[] = posTagger.probs();

        for (int i = 0; i < tokens.length; i++) {
            if (tags[i].equals("POS") || tags[i].equals("PRP") || tags[i].equals("PRP$")) {
                return 1;
            }
        }
        return 0;
    }

    public static String[] SepararPalabras(String frase) {
        SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;
        String tokens[] = simpleTokenizer.tokenize(frase);
        for (String token : tokens) {
            System.out.println(token);
        }
        return tokens;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

}
