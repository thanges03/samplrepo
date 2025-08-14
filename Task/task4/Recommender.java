import java.util.*;

public class Recommender {
    private List<User> users;
    private List<Movie> movies;

    public Recommender(List<User> users, List<Movie> movies) {
        this.users = users;
        this.movies = movies;
    }

    // Compute similarity using Pearson correlation
    private double similarity(User u1, User u2) {
        Set<Integer> common = new HashSet<>(u1.getRatings().keySet());
        common.retainAll(u2.getRatings().keySet());

        if (common.isEmpty()) return 0.0;

        double sum1 = 0, sum2 = 0, sumSq1 = 0, sumSq2 = 0, pSum = 0;
        int n = common.size();

        for (int movieId : common) {
            int r1 = u1.getRating(movieId);
            int r2 = u2.getRating(movieId);

            sum1 += r1;
            sum2 += r2;
            sumSq1 += r1 * r1;
            sumSq2 += r2 * r2;
            pSum += r1 * r2;
        }

        double numerator = pSum - (sum1 * sum2 / n);
        double denominator = Math.sqrt((sumSq1 - (sum1 * sum1 / n)) * (sumSq2 - (sum2 * sum2 / n)));

        return (denominator == 0) ? 0.0 : numerator / denominator;
    }

    public void recommend(User target) {
        Map<Integer, Double> scores = new HashMap<>();
        Map<Integer, Double> simSums = new HashMap<>();

        for (User other : users) {
            if (other == target) continue;

            double sim = similarity(target, other);
            if (sim <= 0) continue;

            for (Map.Entry<Integer, Integer> entry : other.getRatings().entrySet()) {
                int movieId = entry.getKey();
                int rating = entry.getValue();

                if (!target.hasRated(movieId)) {
                    scores.put(movieId, scores.getOrDefault(movieId, 0.0) + sim * rating);
                    simSums.put(movieId, simSums.getOrDefault(movieId, 0.0) + sim);
                }
            }
        }

        List<Map.Entry<Integer, Double>> ranked = new ArrayList<>();
        for (int movieId : scores.keySet()) {
            ranked.add(Map.entry(movieId, scores.get(movieId) / simSums.get(movieId)));
        }

        ranked.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        System.out.println("🎯 Recommended Movies for " + target.getName() + ":");
        for (int i = 0; i < Math.min(5, ranked.size()); i++) {
            int movieId = ranked.get(i).getKey();
            String title = movies.stream().filter(m -> m.getId() == movieId).findFirst().get().getTitle();
            System.out.printf("%d. %s (Score: %.2f)%n", i + 1, title, ranked.get(i).getValue());
        }
    }
}
