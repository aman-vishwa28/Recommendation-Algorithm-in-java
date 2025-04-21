import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class ProductRecommender {
    public static void main(String[] args) throws Exception {
        DataModel model = new FileDataModel(new File("recommendation_sample.csv"));
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
        
        Scanner scanner = new Scanner(System.in);
        String input;
        
        System.out.println("Recommendation System (type 'close' to exit)");
        
        while (true) {
            System.out.print("\nEnter user ID for recommendations: ");
            input = scanner.nextLine().trim();
            
            if ("close".equalsIgnoreCase(input)) {
                break;
            }
            
            try {
                long userId = Long.parseLong(input);
                System.out.print("How many recommendations? ");
                int howMany = Integer.parseInt(scanner.nextLine().trim());
                
                System.out.println("\nRecommendations for User " + userId + ":");
                for (RecommendedItem item : recommender.recommend(userId, howMany)) {
                    System.out.println("Item: " + item.getItemID() + " Score: " + item.getValue());
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