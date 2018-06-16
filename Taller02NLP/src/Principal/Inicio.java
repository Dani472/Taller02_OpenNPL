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

    public static int ActivoPasivo(String frase) {
      InputStream tokenModelIn = null;
		InputStream posModelIn = null;
		
		try {
			String sentence = frase;
			tokenModelIn = new FileInputStream("en-token.bin");
			TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
			TokenizerME tokenizer = new TokenizerME(tokenModel);
			String tokens[] = tokenizer.tokenize(sentence);
			posModelIn = new FileInputStream("en-pos-maxent.bin");
			POSModel posModel = new POSModel(posModelIn);
			POSTaggerME posTagger = new POSTaggerME(posModel);
			String tags[] = posTagger.tag(tokens);
			double probs[] = posTagger.probs();
			
			//System.out.println("Token\t:\tTag\t:\tProbability\n---------------------------------------------");
			for(int i=0;i<tokens.length;i++){
				//System.out.println(tokens[i]+"\t:\t"+tags[i]+"\t:\t"+probs[i]);
                                if (tags[i].equals("POS") || tags[i].equals("PRP") || tags[i].equals("PRP$")){
                                    return 1; 
                                }
			}
			
		}catch (IOException e) {
			// Model loading failed, handle the error
			e.printStackTrace();
		}finally {
			if (tokenModelIn != null) {
				try {
					tokenModelIn.close();
				}catch (IOException e) {
			             
                                }
            }if (posModelIn != null) {
                try {
                    posModelIn.close();
                } catch (IOException e) {
                }
            }
        }
                return 0;
    }
    
    public static String [] SepararPalabras(String frase) {
        SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;
        String tokens[] = simpleTokenizer.tokenize(frase);
        for (String token : tokens) {
            System.out.println(token);
        }
        return tokens;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //Inicio interpretar = new Inicio();
        //interpretar.SepararFrases();
        //interpretar.SepararPalabras
        //Scanner Entrada = new Scanner(System.in);
        //String frase;
        //frase = Entrada.nextLine();
        //interpretar.ActivoPasivo(frase);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

}
