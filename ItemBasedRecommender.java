import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class ItemBasedRecommender {
    public static void main(String[] args) throws Exception {
        DataModel model = new FileDataModel(new File("recommendation_sample.csv"));
        ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
        
        Scanner scanner = new Scanner(System.in);
        String input;
        
        System.out.println("Item Similarity System (type 'close' to exit)");
        
        while (true) {
            System.out.print("\nEnter item ID to find similar items: ");
            input = scanner.nextLine().trim();
            
            if ("close".equalsIgnoreCase(input)) {
                break;
            }
            
            try {
                long itemId = Long.parseLong(input);
                System.out.print("How many similar items? ");
                int howMany = Integer.parseInt(scanner.nextLine().trim());
                
                System.out.println("\nItems similar to " + itemId + ":");
                for (RecommendedItem item : recommender.mostSimilarItems(itemId, howMany)) {
                    System.out.println("Item: " + item.getItemID() + " Similarity: " + item.getValue());
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        scanner.close();
        System.out.println("System closed.");
    }
}