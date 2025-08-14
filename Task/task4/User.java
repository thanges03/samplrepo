import java.util.*;

public class User {
    private String name;
    private Map<Integer, Integer> ratings; // movieId → rating

    public User(String name) {
        this.name = name;
        ratings = new HashMap<>();
    }

    public void rateMovie(int movieId, int rating) {
        ratings.put(movieId, rating);
    }

    public Map<Integer, Integer> getRatings() {
        return ratings;
    }

    public String getName() {
        return name;
    }

    public boolean hasRated(int movieId) {
        return ratings.containsKey(movieId);
    }

    public int getRating(int movieId) {
        return ratings.getOrDefault(movieId, 0);
    }
}
