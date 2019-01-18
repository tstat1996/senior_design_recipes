package graphbuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CosineSimilarity {

    private Graph graph;

    /**
     * The tf-idf weight vectors.
     * The hashmap maps a document to another hashmap.
     * The second hashmap maps a term to its tf-idf weight for this document.
     */
    private Map<Vertex, HashMap<String, Double>> tfIdfWeights;

    /**
     * The constructor.
     * It will take a corpus of documents.
     * Using the corpus, it will generate tf-idf vectors for each document.
     * @param graph the corpus of documents
     */
    public CosineSimilarity(Graph graph) {
        this.graph = graph;
        tfIdfWeights = new HashMap<Vertex, HashMap<String, Double>>();

        createTfIdfWeights();
    }

    private Set<String> commonWords(){
        BufferedReader reader = null;
        Set<String> commonWords = new HashSet<String>();

        try {
            reader = new BufferedReader(new FileReader("common_words.txt"));
            String line = reader.readLine();
            while(line!= null) {
                commonWords.add(line.trim());
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commonWords;
    }

    /**
     * This creates the tf-idf vectors.
     */
    private void createTfIdfWeights() {
        System.out.println("Creating the tf-idf weight vectors");
        Set<String> terms = graph.getInvertedIndex().keySet();
        Set<String> commonWords = commonWords();
        HashMap<String, Double> weights;
        for (Vertex vertex : graph.getDocuments()) {
            weights = new HashMap<String, Double>();

            for (String term : terms) {
                if(commonWords.contains(term)){
                    continue;
                }
                double tf = vertex.getTermFrequency(term);
                double idf = graph.getInverseDocumentFrequency(term);

                double weight = tf * idf;

                weights.put(term, weight);
            }
            tfIdfWeights.put(vertex, weights);
        }
    }

    /**
     * This method will return the magnitude of a vector.
     * @param vertex the document whose magnitude is calculated.
     * @return the magnitude
     */
    private double getMagnitude(Vertex vertex) {
        double magnitude = 0;
        HashMap<String, Double> weights = tfIdfWeights.get(vertex);

        for (double weight : weights.values()) {
            magnitude += weight * weight;
        }

        return Math.sqrt(magnitude);
    }

    /**
     * This will take two documents and return the dot product.
     * @param v1 Document 1
     * @param v2 Document 2
     * @return the dot product of the documents
     */
    private double getDotProduct(Vertex v1, Vertex v2) {
        double product = 0;
        HashMap<String, Double> weights1 = tfIdfWeights.get(v1);
        HashMap<String, Double> weights2 = tfIdfWeights.get(v2);

        for (String term : weights1.keySet()) {
            product += weights1.get(term) * weights2.get(term);
        }

        return product;
    }

    /**
     * This will return the cosine similarity of two documents.
     * This will range from 0 (not similar) to 1 (very similar).
     * @param v1 Document 1
     * @param v2 Document 2
     * @return the cosine similarity
     */
    public double cosineSimilarity(Vertex v1, Vertex v2) {
        return getDotProduct(v1, v2) / (getMagnitude(v1) * getMagnitude(v2));
    }
}
