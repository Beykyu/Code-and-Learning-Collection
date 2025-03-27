import java.util.*;

public class SA {
	
	private double[] saParameters;
	private int[][] sa_array;
	private int[] kemeny_rankings;
	private int bestKemenyScore;
	private int[] swap;
	Map<Integer, String> participants;
	
	public SA(int val){
		participants = new HashMap<Integer, String>();
		sa_array = new int[val][val];
		saParameters = new double[4];
		kemeny_rankings = new int[val];
		bestKemenyScore = Integer.MAX_VALUE;
		swap = new int[2];
	}
	
	//Getters and setters for KemenyRankings
	public int getKemenyRankings(int index){
		return kemeny_rankings[index];
	}
	
	public void setKemenyArray(int[] rankings){
		//System.out.println(Arrays.toString(rankings));
		kemeny_rankings = rankings;
	}
	
	public int[] getAllKemenyRankings(){
		//System.out.println(Arrays.toString(kemeny_rankings));
		return kemeny_rankings;
	}
	
	//Getters and setters for best kemenyScore
	public int getBestScore(){
		return bestKemenyScore;
	}
	
	public void setBestScore(int score){
		bestKemenyScore = score;
	}


	//Getters and setters for scores of individuals
	public void setScore(String Score){
		String temp[] = Score.split("[,]");
		int winner = Integer.parseInt(temp[1]);
		int loser = Integer.parseInt(temp[2]);
		int margin = Integer.parseInt(temp[0]);
		sa_array[winner - 1][loser - 1] = margin;
	}
	
	public int getScore(int dancers1, int dancers2){
		int score = sa_array[dancers1][dancers2];
		return score;
	}
	
	//Getters and Setters for participants
	public void setParticipants(String dancers){
		String temp[] = dancers.split("[,]");
		participants.put(Integer.parseInt(temp[0]), temp[1]);
	}
	
	public String getParticipants(int index){
		return participants.get(index);
	}
	
	
	//Getters and Setters for iterations
	public void setParameters (int num, double t, int tl, double f){
		saParameters[0] = num;
		saParameters[1] = t;
		saParameters[2] = tl;
		saParameters[3]	= f;	
	}
	
	public double[] getParameters (){
		return saParameters;
	}
	
	public void setRankSwaps(int rank1, int rank2){
		swap[0]= rank1;
		swap[1]= rank2;
	}
	
	public int getRankSwaps(int index){
		return swap[index];
	}
}
