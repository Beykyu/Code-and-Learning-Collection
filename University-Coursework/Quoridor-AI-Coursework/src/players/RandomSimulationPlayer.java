package players;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import moves.Move;
import quoridor.GameState2P;
import quoridor.Quoridor;


public class RandomSimulationPlayer extends QuoridorPlayer{
	
	public static Random random = new Random();
	private int startDepth=1;
	private int indexOpponent;  
	private int timeAllocated = 5000;
	
	 
	public RandomSimulationPlayer(GameState2P state, int index, Quoridor game) {
		super(state, index, game);
        indexOpponent = (index + 1) % 2;
	}

	public void doMove() {
		//Initialise starting variables
	    List<Move> legalMoves = GameState2P.getLegalMoves(state, index);
	    //Stores the wins/loses/ties of each initial move played. Wins stored in index 0, loses in 1 and ties will be in 2
	    Map<Move, ArrayList<Double>> previousMoves = new HashMap<Move, ArrayList<Double>>();
	    //For initialising moves with 0 wins and loses when not previously added yet 
	    ArrayList<Double> intialiseCount = new ArrayList<Double>();
	    intialiseCount.add(0.0);
	    intialiseCount.add(0.0);
	    //intialiseCount.add(0.0);
	    long timeup = System.currentTimeMillis() + timeAllocated;
	    int depth = startDepth;
	    Move lastBest = null;
	    Move bestMove = null;
        //Execute code as long as time limit is not up. Allow AI to search deeper for each loop
	    for (; System.currentTimeMillis() < timeup; depth++) {
	    	//Picks a random legal move to use 
	    	//Random whole number generated from 0-size of legal move list.   
	    	int i = random.nextInt(legalMoves.size());
        	Move m = legalMoves.get(i);
		    GameState2P next = m.doMove(state);
		    double score = getMinScoreAlphaBeta(next, depth, timeup);
		    if (System.currentTimeMillis() > timeup) {
		        break;
		    }
		    //System.out.println(score + " : " + depth);
		    // Keeps track of wins/loses for each legal move selected
		    ArrayList<Double> check = previousMoves.get(m);
		    //If current move has not been considered before add it to the hashmap of all initially played moves
	    	if (check == null) {
	    		ArrayList<Double> intialiseCountCopy = new ArrayList<Double>(intialiseCount);
	    		previousMoves.put(m, intialiseCountCopy);
	    	}
	    	//Gets the wins/loses of current move and updates it based on the score of the played move
	    	ArrayList<Double> winsLoses = previousMoves.get(m);
		    if (score > 0){
		      winsLoses.set(0, winsLoses.get(0) + 1);
		      previousMoves.put(m, winsLoses);
		    }
		    else if(score < 0){
		    	winsLoses.set(1, winsLoses.get(1) + 1);
		    	previousMoves.put(m, winsLoses);
		    }
		    //Looks through each played moves, getting wins and loses then figuring out which is the best move with wins/loses
		    Double bestScore = 0.0;
		    for (Entry<Move, ArrayList<Double>> moves : previousMoves.entrySet()){
		    	ArrayList<Double> moveWinsLoses = moves.getValue();
		    	//System.out.println(moves + " move ");
		    	double wins = moveWinsLoses.get(0);
		    	double loses = moveWinsLoses.get(1);
		    	double total = wins+loses;
		    	double overallScore = (wins/total);
		    	//Keeps track of the best move to play
        		if (bestMove == null || overallScore > bestScore){
        			bestScore = overallScore;
        			bestMove = moves.getKey();
        		}
        	} 
	        if (System.currentTimeMillis() < timeup) {
		        lastBest = bestMove;
		    } 
	    }
	    System.out.println("The random move which led to the best wins was : " + lastBest);
	    GameState2P newState = lastBest.doMove(state);
	    game.doMove(index, newState);
	}
	
	private double getMinScoreAlphaBeta(GameState2P s, int depth, long timeup) {
	    double res;
	    if (depth == 0 || s.isGameOver()) {
	        res = s.evaluateState(index);
	    }
	    else {
	        List<Move> opponentMoves = GameState2P.getLegalMoves(s, indexOpponent);
	        res = Double.POSITIVE_INFINITY;
	        boolean loop = true;
	        GameState2P tempNext = null;
	        //Prevents game state being returned as null 
        	while (loop == true){
        		int i = random.nextInt(opponentMoves.size());
            	Move m = opponentMoves.get(i);
	        	if (System.currentTimeMillis() > timeup) {
	                return -1;
	            }
	        	tempNext = m.doMove(s);
	        	if (tempNext != null){
	        		break;
	        	}
			}
		    GameState2P next = tempNext;
		    double score = getMaxScoreAlphaBeta(next, depth - 1, timeup);
		    res = Math.min(res, score);   
        }
	    return res;
	}
	
	private double getMaxScoreAlphaBeta(GameState2P s, int depth, long timeup) {
	    double res;
	    if (depth == 0 || s.isGameOver()) {
	        res = s.evaluateState(index);
	    }
	    else {
	        List<Move> myMoves = GameState2P.getLegalMoves(s, index);
	        res = Double.NEGATIVE_INFINITY;
	        boolean loop = true;
	        GameState2P tempNext = null;
        	while (loop == true){
        		int i = random.nextInt(myMoves.size());
            	Move m = myMoves.get(i);
	        	if (System.currentTimeMillis() > timeup) {
	                return -1;
	            }
	        	tempNext = m.doMove(s);
	        	if (tempNext != null){
	        		break;
	        	}
			}
		    GameState2P next = tempNext;
		    double score = getMinScoreAlphaBeta(next, depth - 1, timeup);
		    res = Math.max(res, score);
        }
	return res;
	}
}

