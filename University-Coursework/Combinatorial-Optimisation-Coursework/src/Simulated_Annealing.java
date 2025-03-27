import java.util.*;
import java.io.*;

public class Simulated_Annealing {
	
	public static void main( String[] args ) {
		String filename = args[0];
		start(filename);
	}
	
	public static void start(String fileName){
		List<String> list = readFile(fileName);
		int fileLength = list.size();
		int totalParticipants = Integer.parseInt(list.get(0));
		System.out.println("Please input the number of iterations you want to run before ");
		System.out.println("displaying the current solution and the best solution : ");
		try {
			//Reads the user's input and passes it to a function to create the SA object
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input = br.readLine();
			int m = Integer.parseInt(input);
			System.out.println("You have selected " + m + " iterations");
			long start = System.currentTimeMillis();
			
			//Creates new Tournament object
			SA sa = new SA(totalParticipants);
			/*Set initial parameters for SA 
			*
			*
			*Change these values to tune the SA algorithm
			*
			*
			[iterations,initial temperature,temperature length,cooling ratio]*/
			sa.setParameters(25, 20, 15, 0.5);
			//converts and stores tournament data into a 2d array
			for (int i = totalParticipants + 2; i < fileLength ;i++){
				sa.setScore(list.get(i));
			}
			//Sets up initial rankings and gets it's current cost
			int[] intialRankings = new int[totalParticipants];
			int overallScore = 0;
			for (int i=0; i< totalParticipants;i++){
				//Places names of participants into array
				sa.setParticipants(list.get(i+1));
				intialRankings[i] = i+1;
				for (int j = i+1; j<totalParticipants; j++){
					overallScore += sa.getScore(j, i);
				}
			}
			sa.setBestScore(overallScore);
			sa.setKemenyArray(intialRankings);
			//Runs Simulated Annealing
			int[] best = runSA(sa, m);
			
			
			//Converts integer rankings into named rankings
			String[] bestOutputs = new String[totalParticipants];
			for (int i = 0; i < best.length;i++){
				bestOutputs[i] = sa.getParticipants(best[i]);
			}
			
			//Outputs information
			System.out.println(Arrays.toString(bestOutputs));
			System.out.println(Arrays.toString(best));
			System.out.println("Best Kenemy Score: " + sa.getBestScore());
			long end = System.currentTimeMillis();
			System.out.println("Algorithm run time was : " + (end - start));
		} 
		catch (IOException e) {
		   System.err.println("IO Exception: " + e.getMessage());
		} 		
	}
	
	public static int[] runSA(SA sa, int m){
		//Sets up initial SA parameters
		double[] parameters = sa.getParameters();
		double stop = parameters[0];
		double t = parameters[1];
		double tl = parameters[2];
		double f = parameters[3];
		//Sets up initial counters
		int count = 0;
		int stopCount = 0;
		int cost= 0;
		//Get information on current initial solution
		int currentCost = sa.getBestScore();
		int[] rankings = sa.getAllKemenyRankings();
		int [] currentNeighbourHood = new int[rankings.length];
		System.arraycopy( rankings, 0, currentNeighbourHood, 0, rankings.length );
		//While the stopping criteria has not been reached
		while (count < stop){
			//Prints out current and best solution after m iterations
			if (stopCount%m ==0){
				System.out.println("Current cost of solution: " + currentCost);
				System.out.println("Best cost of solution: " + sa.getBestScore());
			}
			//Temperature length of each iteration
			for(int i = 0; i < tl; i++){
				//Generate a random neighbour by swapping 2 ranks
				int[] tempNeighbour = generateRandomNeighbour(sa,currentNeighbourHood);
				//Gets the cost of the randomly generated neighbour
				int tempCost = calculateCost(tempNeighbour,sa,currentCost,currentNeighbourHood);
				cost = tempCost - currentCost;
				//Move to that neighbour if it's a better solution than the current neighbourhood
				if (cost <= 0){
					currentNeighbourHood = tempNeighbour;
					currentCost = tempCost;
					//Sets current Neighbour as best solution if it's lower than the current best Kemeny score
					if (tempCost < sa.getBestScore()){
						sa.setBestScore(tempCost);
						System.arraycopy( tempNeighbour, 0, rankings, 0, tempNeighbour.length );
						sa.setKemenyArray(rankings);
					}
					count = 0;
				}
				//Calculates the probability of moving to neighbour even if it's a worse solution
				else {
					double q = Math.random();
					if (q < Math.pow(Math.E, -cost/t)){
						currentNeighbourHood = tempNeighbour;
						currentCost = tempCost;
						//Resets stopping criteria counter
						count = 0;
					}
				}
			}
		//Set new temperature
		t = f*t;
		count++;
		stopCount++;
		}
		return sa.getAllKemenyRankings();
	}

	public static int[] generateRandomNeighbour(SA sa,int[] currentNeighbourHood){
		int[] randomNeighbour = new int[currentNeighbourHood.length];
		System.arraycopy( currentNeighbourHood, 0, randomNeighbour, 0, currentNeighbourHood.length );
		int secondRandomRanking;
		//Picks random rank to swap
		Random random = new Random();
		int firstRandomRanking = random.nextInt(currentNeighbourHood.length);
		//Keep picking a random number until a it's not the same as the first random number
		do {
			secondRandomRanking = random.nextInt(currentNeighbourHood.length);
		} while(secondRandomRanking == firstRandomRanking);
		int temp1 = currentNeighbourHood[firstRandomRanking];
		int temp2 = currentNeighbourHood[secondRandomRanking];
		//Swap the two rankings
		randomNeighbour[firstRandomRanking] = temp2;
		randomNeighbour[secondRandomRanking] = temp1;
		//For keeping track of the 2 ranks which were swapped
		sa.setRankSwaps(firstRandomRanking, secondRandomRanking);
		return randomNeighbour;
	}
  	
	public static int calculateCost (int[] randomNeighbourhood, SA sa,int currentCost,int[] currentNeighbourhood){
		int cost = 0;
		int addCost = 0;
		int subCost = 0;
		int fRank = sa.getRankSwaps(0);
		int sRank = sa.getRankSwaps(1);
		//For every rank between the 2 swapped ranks and themselves update the kemeny score
		for (int i= Math.min(fRank, sRank); i< Math.max(fRank, sRank); i++){
			int index = randomNeighbourhood[i];
			int curindex = currentNeighbourhood[i];
			for (int j = i+1; j<Math.max(fRank, sRank)+1; j++){
				int tempAddScore = sa.getScore(randomNeighbourhood[j]-1, index-1);
				int tempSubScore = sa.getScore(currentNeighbourhood[j]-1, curindex-1);
				//Add cost of random neighbour section
				addCost += tempAddScore;
				//Remove cost of current neighbour section
				subCost += tempSubScore;
			}
		}
		//Overall cost of the new neighbour
		cost = currentCost - subCost + addCost ;
		return cost;
	}
	
	public static List<String> readFile(String fileName) {
		List<String> list = new ArrayList<String>();
		File file = new File(fileName);
		BufferedReader reader = null;
		//Reads the file
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;
		//Reads each line, if it's not empty it adds it to the arraylist
			while ((text = reader.readLine()) != null) {
			    list.add(text);
			}
		//Handle any errors in reading the file
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			//Close file
			try {
			    if (reader != null) {
			        reader.close();
			    }
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	} 
}
