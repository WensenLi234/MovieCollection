import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private ArrayList<String> allCast;
    private ArrayList<String> genres;
    private Scanner scanner;
    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        allCast = getAllCast();
        genres = getAllGenres();
        sortString(allCast);
        sortString(genres);
        sortResults(movies);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        switch (option) {
            case "t" -> searchTitles();
            case "c" -> searchCast();
            case "k" -> searchKeywords();
            case "g" -> listGenres();
            case "r" -> listHighestRated();
            case "h" -> listHighestRevenue();
            default -> System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (Movie movie : movies) {
            String movieTitle = movie.getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.contains(searchTerm)) {
                //add the Movie object to the results list
                results.add(movie);
            }
        }

        // now, display them all to the user
        displayMovies(results);
        System.out.println("\n Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }
    private void sortString(ArrayList<String> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            String string = listToSort.get(j);

            int possibleIndex = j;
            while (possibleIndex > 0 && string.compareTo(listToSort.get(possibleIndex - 1)) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, string);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast search term: ");
        String searchCast = scanner.nextLine().toLowerCase();
        ArrayList<String> possibleCasts = new ArrayList<String>();
        for(String cast: allCast) {
            if(cast.toLowerCase().contains(searchCast)) {
                possibleCasts.add(cast);
            }
        }
        for(int i = 0; i < possibleCasts.size(); i++) {
            System.out.println(i + 1 + ". " + possibleCasts.get(i));
        }
        System.out.println("Which cast member would you like to know more about? ");
        System.out.print("Enter a number: ");
        searchCast = possibleCasts.get(scanner.nextInt() - 1);
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getCast().contains(searchCast)) {
                results.add(movie);
            }
        }
        displayMovies(results);
        System.out.println("\n Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchKeyword = scanner.nextLine().toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getKeywords().contains(searchKeyword)) {
                results.add(movie);
            }
        }
        sortResults(results);
        displayMovies(results);
        System.out.println("\n Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        for(int i = 0; i < genres.size(); i++) {
            System.out.println(i + 1 + ". " + genres.get(i));
        }
        System.out.println("Which genre would you like to know more about? ");
        System.out.print("Enter number: ");
        int input = scanner.nextInt();
        scanner.nextLine();
        String genre = genres.get(input - 1);
        ArrayList<Movie> results = new ArrayList<Movie>();
        for(Movie movie : movies) {
            if(movie.getGenres().contains(genre)) {
                results.add(movie);
            }
        }
        displayMovies(results);
        System.out.println("\n Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Movie> topRated = new ArrayList<Movie>();
        for(int i = 0; i <= 50; i++) {
            Movie movieToAdd = movies.get(0);
            for(Movie movie : movies) {
                if(!topRated.contains(movie) && movieToAdd.getUserRating() < movie.getUserRating()) {
                    movieToAdd = movie;
                }
            }
            topRated.add(movieToAdd);
        }
        for(int i = 0; i < topRated.size(); i++) {
            System.out.println(i + 1 + ". " + topRated.get(i).getTitle() + ": " + topRated.get(i).getUserRating());
        }
        System.out.println("Which movie would you like to know more about? ");
        System.out.print("Enter number: ");
        int input = scanner.nextInt();
        scanner.nextLine();
        displayMovieInfo(topRated.get(input - 1));
        System.out.println("\n Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> topRevenue = new ArrayList<Movie>();
        for(int i = 0; i <= 49; i++) {
            Movie movieToAdd = movies.get(0);
            for(Movie movie : movies) {
                if(!topRevenue.contains(movie) && movieToAdd .getRevenue() < movie.getRevenue()) {
                    movieToAdd = movie;
                }
            }
            topRevenue.add(movieToAdd);
        }
        for(int i = 0; i < topRevenue.size(); i++) {
            System.out.println(i + 1 + ". " + topRevenue.get(i).getTitle() + ": $" + topRevenue.get(i).getRevenue());
        }
        System.out.println("Which movie would you like to know more about? ");
        System.out.print("Enter number: ");
        int input = scanner.nextInt();
        scanner.nextLine();
        displayMovieInfo(topRevenue.get(input - 1));
        System.out.println("\n Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
    public ArrayList<String> getAllCast() {
        ArrayList<String> allCast = new ArrayList<String>();
        for(Movie movie : movies) {
            String cast = movie.getCast();
            while(cast.contains("|")) {
                String curCast = cast.substring(0, cast.indexOf("|"));
                if(!allCast.contains(curCast)) {
                    allCast.add(curCast);
                }
                cast = cast.substring(cast.indexOf("|") + 1);
            }
            if(!allCast.contains(cast)) {
                allCast.add(cast);
            }
        }
        return allCast;
    }
    public ArrayList<String> getAllGenres() {
        ArrayList<String> allGenres = new ArrayList<String>();
        for(Movie movie : movies) {
            String genre = movie.getGenres();
            while(genre.contains("|")) {
                String curCast = genre.substring(0, genre.indexOf("|"));
                if(!allGenres.contains(curCast)) {
                    allGenres.add(curCast);
                }
                genre = genre.substring(genre.indexOf("|") + 1);
            }
            if(!allGenres.contains(genre)) {
                allGenres.add(genre);
            }
        }
        return allGenres;
    }
    public void displayMovies(ArrayList<Movie> movies) {
        for(int i = 0; i < movies.size(); i++) {
            System.out.println(i + 1 + ". " + movies.get(i).getTitle());
        }
        System.out.println("Which movie would you like to know more about? ");
        System.out.print("Enter number: ");
        int input = scanner.nextInt();
        scanner.nextLine();
        displayMovieInfo(movies.get(input - 1));
    }
}