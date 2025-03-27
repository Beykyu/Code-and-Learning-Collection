package players;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import moves.Move;
import players.QuoridorPlayer;
import quoridor.GameState2P;
import quoridor.Quoridor;


public class HeuristicSimulationPlayer extends QuoridorPlayer{
	
	private int indexOpponent;
	private int startDepth = 1;
	 
	public HeuristicSimulationPlayer(GameState2P state, int index, Quoridor game) {
		super(state, index, game);
		indexOpponent = (index + 1) % 2;
		// TODO Auto-generated constructor stub
	}
	
	public void doMove() {
        Move m = chooseMove();
        GameState2P newState = m.doMove(state);
        game.doMove(index, newState);
    }
	
	public Move chooseMove(){
		List<Move> legalMoves = GameState2P.getLegalMoves(state, index);
		//Used for storing the wins and loses of each legal move
		Map<Move, ArrayList<Double>> previousMoves = new HashMap<Move, ArrayList<Double>>();
	    //For initialising moves with 0 wins and loses when not previously added yet 
	    ArrayList<Double> intialiseCount = new ArrayList<Double>();
	    intialiseCount.add(0.0);
	    intialiseCount.add(0.0);
	    //intialiseCount.add(0.0);
	    //Used for the move which will be played
	    Move lastBest = null;
        long stop = (System.currentTimeMillis() + 5000);
        int depth = startDepth;
        //Each loop is a different simulation
	    for (; System.currentTimeMillis() < stop; depth++) {
	    		Move bestMove = null;
	    		Double score = 0.0;
        		//Determine if first move is random or not
        		Random random = new Random();
				int check = random.nextInt(10) + 1;
				if (check == 5){
					//Picks a random initial move
					random = new Random();
					int i = random.nextInt(legalMoves.size());
			    	Move m = legalMoves.get(i);
			    	GameState2P next = m.doMove(state);
			    	score = getScoreMin(next, depth - 1 , stop);
			    	bestMove = m;
				}
				else{
					//Finds the best initial move
		            double bestScore = 0.0;
		            //Loops through all legal moves while trying to find the move which maximises the score
					for (Move m : legalMoves) {
		            	GameState2P next = m.doMove(state);
		            	//Using the minmaxplayer
		            	score = getMinScoreAlphaBeta(next, 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, stop);
		            	if (bestMove == null || score > bestScore) {
			    	        bestMove = m;
			    	        bestScore = score;
			    	    }
					}
					GameState2P next = bestMove.doMove(state);
					score = getScoreMin(next, depth - 1, stop);
				}
				//Check for if played move exists inside 
				ArrayList<Double> check1 = previousMoves.get(bestMove);
			    //If current move has not been considered before add it to the hashmap of all initially played moves
		    	if (check1 == null) {
		    		ArrayList<Double> intialiseCountCopy = new ArrayList<Double>(intialiseCount);
		    		previousMoves.put(bestMove, intialiseCountCopy);
		    	}
		    	//Gets the wins/loses of current move and updates it based on the score of the played move
		    	ArrayList<Double> winsLoses = previousMoves.get(bestMove);
			    if (score > 0){
			      winsLoses.set(0, winsLoses.get(0) + 1);
			    }
			    else if(score < 0){
			    	winsLoses.set(1, winsLoses.get(1) + 1);
			    }
			    previousMoves.put(bestMove, winsLoses);
			    //For storing the best overall score
	    		Double best = 0.0;
			    //Looks through all legal moves with their wins/loses and calculates a score for it
			    for (Entry<Move, ArrayList<Double>> moves : previousMoves.entrySet()){
			    	ArrayList<Double> moveWinsLoses = moves.getValue();
			    	//System.out.println(moves + " move ");
			    	double wins = moveWinsLoses.get(0);
			    	double loses = moveWinsLoses.get(1);
			    	double total = wins+loses;
			    	double overallScore = (wins/total);
			    	//System.out.println("Wins : " + wins + " Loses : " + loses + " overall : " + overallScore + " " + moves.getKey());
	        		//Keeps track of the move which has the highest win ratio
			    	if (bestMove == null || overallScore > best){
	        			best = overallScore;
	        			bestMove = moves.getKey();
	        			//System.out.println(best +" : " + moves);
	        		}
	        	}
		        if (System.currentTimeMillis() < stop) {
			        lastBest = bestMove;
			    } 
		    }
	    return lastBest;
	}
	
	//Determine if opponent will pick a random or heuristic move and gets a score
	double getScoreMin (GameState2P s, int depth, long stop){
		double res; double score;
			if (depth == 0 || s.isGameOver()) {
	            res = s.evaluateState(index);
	        }
	        else{
	        	List<Move> opponentMoves = GameState2P.getLegalMoves(s, indexOpponent);
	        	res = Double.POSITIVE_INFINITY;
	        	if (System.currentTimeMillis() > stop) {
	                return -1;
	            }
	        	//Checks if it will use a random or heuristic move
	        	Random random = new Random();
				int check = random.nextInt(10) + 1;
				//Number used to check does not matter as long as it's between 1 and 10
				if (check == 5){
					//Prevents the random move that is selected from returning a null state
			        GameState2P tempNext = null;
		        	while (tempNext == null){
		        		//Select random move 
		        		int i = random.nextInt(opponentMoves.size());
		            	Move m = opponentMoves.get(i);
			        	tempNext = m.doMove(s);
					}
		        	//Play the move and get back the score
		        	GameState2P next = tempNext;
			    	score = getScoreMax(next, depth -1, stop);
			    	res = Math.min(res, score);
			    	//System.out.println(res);
				}
			    else{
			    	// Using the minmax for heuristically best move
			    	Move bestMove = null;
			        double bestScore = 0;
			        for (Move m : opponentMoves) {
			        	if (System.currentTimeMillis() > stop) {
			                return -1;
			            }
			        	GameState2P next = m.doMove(s);
			        	if (next != null){
			        		score = getMinScoreAlphaBeta(next, 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, stop);
			        		//Since this is the opponent we want to get the move which minimises the score
			        		if (bestMove == null || score < bestScore) {
				            	bestMove = m;
				            	bestScore = score;
					    	}
			        	}
			        }
			        GameState2P next = bestMove.doMove(s);
			        score = getScoreMax(next, depth -1, stop);
			        //System.out.println(score);
			    	res = Math.min(res, score);
			    }
	        }
		return res;
	}
	
	//Determine if AI will pick a random or heuristic move and gets a score
	//Does the same as the getScoreMin except used for AI moves not the opponent
	double getScoreMax (GameState2P s, int depth, long stop){
		double res; double score;
		if (depth == 0 || s.isGameOver()) {
            res = s.evaluateState(index);
        }
        else{
        	List<Move> myMoves = GameState2P.getLegalMoves(s, index);
        	res = Double.NEGATIVE_INFINITY;
        	Random random = new Random();
			int check = random.nextInt(10) + 1;
			if (check == 5){
		        GameState2P tempNext = null;
	        	while (tempNext == null){
	        		int i = random.nextInt(myMoves.size());
	            	Move m = myMoves.get(i);
		        	tempNext = m.doMove(s);
				}
	        	GameState2P next = tempNext;
		    	score = getScoreMin(next, depth -1, stop);
		    	res = Math.max(res, score);
			}
		    else{
		    	Move bestMove = null;
		        double bestScore = 0;
		        for (Move m : myMoves) {
		        	if (System.currentTimeMillis() > stop) {
		                return -1;
		            }
		        	GameState2P next = m.doMove(s);
		        	if (next != null){
		        		score = getMaxScoreAlphaBeta(next, 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, stop);
			            //Keep track of the move which maximises the score
		        		if (bestMove == null || score > bestScore) {
			            	bestMove = m;
			            	bestScore = score;
				    	}
		        	}
		        }
		        GameState2P next = bestMove.doMove(s);
		        score = getScoreMin(next, depth -1, stop);
		    	res = Math.max(res, score);
		    }
        }
		return res;
	}
	
	//Just the given minmax code
	private double getMinScoreAlphaBeta(GameState2P s, int depth, double alpha, double beta, double stop) {
        double res;
        if (depth == 0 || s.isGameOver()) {
            res = s.evaluateState(index);
        }
        else {
            List<Move> opponentMoves = GameState2P.getLegalMoves(s, indexOpponent);
            res = Double.POSITIVE_INFINITY;
            for (Move move : opponentMoves) {
                if (System.currentTimeMillis() > stop) {
                    return -1;
                }
                GameState2P next = move.doMove(s);
                double score = getMaxScoreAlphaBeta(next, depth - 1, alpha, beta, stop);
                res = Math.min(res, score);
                beta = Math.min(beta, score);
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return res;
    }

    private double getMaxScoreAlphaBeta(GameState2P s, int depth, double alpha, double beta, double timeup) {
        double res;
        if (depth == 0 || s.isGameOver()) {
            res = s.evaluateState(index);
        }
        else {
            List<Move> myMoves = GameState2P.getLegalMoves(s, index);
            res = Double.NEGATIVE_INFINITY;
            for (Move move : myMoves) {
                if (System.currentTimeMillis() > timeup) {
                    return -1;
                }
                GameState2P next = move.doMove(s);
                double score = getMinScoreAlphaBeta(next, depth - 1, alpha, beta, timeup);
                res = Math.max(res, score);
                alpha = Math.max(alpha, score);
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return res;
    }
}