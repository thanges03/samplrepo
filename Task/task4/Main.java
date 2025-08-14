import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Movie> movies = List.of(
            new Movie(1, "Inception"),
            new Movie(2, "The Matrix"),
            new Movie(3, "Interstellar"),
            new Movie(4, "Avengers"),
            new Movie(5, "Titanic")
        );

        User alice = new User("Alice");
        alice.rateMovie(1, 5); // Inception
        alice.rateMovie(2, 3); // Matrix
        alice.rateMovie(4, 4); // Avengers

        User bob = new User("Bob");
        bob.rateMovie(1, 4);
        bob.rateMovie(3, 5); // Interstellar
        bob.rateMovie(4, 3);

        User charlie = new User("Charlie");
        charlie.rateMovie(2, 4);
        charlie.rateMovie(3, 4);
        charlie.rateMovie(5, 5); // Titanic

        User dave = new User("Dave");
        dave.rateMovie(1, 4);
        dave.rateMovie(2, 5);
        dave.rateMovie(5, 3);

        List<User> users = List.of(alice, bob, charlie, dave);

        Recommender recommender = new Recommender(users, movies);

        // Recommend for Alice
        recommender.recommend(alice);
    }
}
