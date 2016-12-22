import java.util.ArrayList;
import java.util.Scanner;

public class MovieApp {
	
	public static void main(String[] args) {
		Scanner keyBoardInput = new Scanner(System.in);
		String movieGenre = null;
		boolean isValid = false;
		
		ArrayList<Movie> arrayMovies = new ArrayList<>();
		MovieIO myMovie = new MovieIO();
		
		System.out.println("Welcome to the Movie List Application.\n");
		System.out.println("There are 100 movies in the list.\n");
		while (isValid == false){
		System.out.println("\nWhat category are you interested in?");
		System.out.println("Choices are: comedy, drama, horror, musical, animated and scifi ");
		
			//filling array with movies
			for (int i = 0; i <= 100; i++)
			{
				arrayMovies.add(MovieIO.getMovie(i));
				
			}
				String choice = keyBoardInput.next();
		
			if (choice.equalsIgnoreCase("drama")){
				for (int i = 0; i <=100; i++)
				{
					if (arrayMovies.get(i).getCategory().equals(choice)){
						System.out.println(arrayMovies.get(i).getTitle());
					}
						
				}
			}
			if (choice.equalsIgnoreCase("musical")){
					for (int i = 0; i <=100; i++)
					{
						if (arrayMovies.get(i).getCategory().equals(choice)){
							System.out.println(arrayMovies.get(i).getTitle());
						}
							
					}
			}
			
			if (choice.equalsIgnoreCase("comedy")){
				for (int i = 0; i <=100; i++)
				{
					if (arrayMovies.get(i).getCategory().equals(choice)){
						System.out.println(arrayMovies.get(i).getTitle());
					}
						
				}
			
			}
			
			if (choice.equalsIgnoreCase("horror")){
				for (int i = 0; i <=100; i++)
				{
					if (arrayMovies.get(i).getCategory().equals(choice)){
						System.out.println(arrayMovies.get(i).getTitle());
					}
						
				}
			}
			if (choice.equalsIgnoreCase("animated")){
				for (int i = 0; i <=100; i++)
				{
					if (arrayMovies.get(i).getCategory().equals(choice)){
						System.out.println(arrayMovies.get(i).getTitle());
					}
						
				}
			
			}
			if (choice.equalsIgnoreCase("scifi")){
				for (int i = 0; i <=100; i++)
				{
					if (arrayMovies.get(i).getCategory().equals(choice)){
						System.out.println(arrayMovies.get(i).getTitle());
					}
						
				}
			
			}	
		}
		
	}

}

