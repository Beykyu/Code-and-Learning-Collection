/*
 * Attempted implementation of task 4, not completed
 */
package players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import moves.Move;
import quoridor.GameState2P;
import quoridor.Quoridor;

public class RecursiveUCB1SimulationPlayer extends QuoridorPlayer{
	private int indexOpponent; 
    private int startDepth = 1;
	
	public RecursiveUCB1SimulationPlayer(GameState2P state, int index, Quoridor game) {
		super(state, index, game);
		indexOpponent = (index + 1) % 2;
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
		Map<Move, ArrayList<Double>> opponentPreviousMoves = new HashMap<Move, ArrayList<Double>>();
	    //For initialising moves with 0 wins and loses when not previously added yet 
	    ArrayList<Double> intialiseCount = new ArrayList<Double>();
	    intialiseCount.add(0.0);
	    intialiseCount.add(0.0);
	    intialiseCount.add(0.0);
		long stop = (System.currentTimeMillis() + 5000);
		//Move which will be played
		Move lastBest = null;
		//Keep track of number of simulations
		double t = 0.0;
		//fixed depth
		double d = 2;
		int depth = startDepth;
		for (; System.currentTimeMillis() < stop; depth++) {
			//Initialise variables for current simulation
			Move bestMove = null;
			//Variable for holding the best overall score (wins/loses)
			Double best = 0.0;
			Double score = 0.0;
			if (previousMoves.isEmpty()){
				double bestucb1 = 0.0;
				//Check all legal moves and obtain an ucb1 score
				for (Move m : legalMoves) {
					t++;
					double w = 0.0;
					GameState2P next = m.doMove(state);
					score = getScoreMin(next, 10, stop);
					ArrayList<Double> intialiseCountCopy = new ArrayList<Double>(intialiseCount);
					//Initialise the the scores of all possible outcomes
					previousMoves.put(m, intialiseCountCopy);
					ArrayList<Double> winsLosesTies = previousMoves.get(m);
			    	//Keep track of wins/loses/ties of each legal move
					if (score > 0){
						winsLosesTies.set(0, winsLosesTies.get(0) + 1);
				    	w++;
				    }
					else if (score < 0){
						winsLosesTies.set(1, winsLosesTies.get(1) + 1);
					}
				    else {
				    	winsLosesTies.set(2, winsLosesTies.get(2) + 1);
				    }
					previousMoves.put(m, winsLosesTies);
					//Work out the ucb1 score of each move
				    double ucb1Score = (w/1) + Math.sqrt((2*Math.log(t))/1);
				    //System.out.println(w/1 + " wins " + " move " + m + " t is : " + t + " ucb1 score: " + ucb1Score);
				    //Keep track of the move with the current best ucb1 score
				    if (ucb1Score > bestucb1 || bestMove == null){
				    	bestucb1 = ucb1Score;
				    	bestMove = m;
				    }
				}
			}
			else {
				bestMove = returnStartingMoveUCB1 (legalMoves,depth,stop, previousMoves, t);
			}
			//Getting starting node
			GameState2P next = bestMove.doMove(state);
			double tNode= 0.0;
			ArrayList<Double> intialiseCountCopy = new ArrayList<Double>(intialiseCount);
			//Return move si
			Move m = getSiMin(d, next, tNode, stop, opponentPreviousMoves, previousMoves, intialiseCountCopy);
			next = m.doMove(state);
			score = getScoreMin(next, depth - 1, stop);
	    	//System.out.println(score + " move : " + bestMove + " and move is " + m);
	    	//System.out.println(" score : " + score + " move " + bestMove  );
	    	//Ensures that move exists in the hash maps 
	    	ArrayList<Double> check1 = previousMoves.get(bestMove);
	    	if (check1 == null) {
	    		previousMoves.put(bestMove, intialiseCount);
	    	}
	    	//Stores the wins/loses/ties of each move
	    	ArrayList<Double> winsLosesTie = previousMoves.get(bestMove);
	    	if (score > 0){
			      winsLosesTie.set(0, winsLosesTie.get(0) + 1);
			}
			else if(score < 0){
			    winsLosesTie.set(1, winsLosesTie.get(1) + 1);
			}
			else{
				winsLosesTie.set(2, winsLosesTie.get(2) + 1);
			}
			previousMoves.put(bestMove, winsLosesTie);
		    //Looks through all legal moves with their wins/loses and calculates a score for it
			for (Entry<Move, ArrayList<Double>> moves : previousMoves.entrySet()){
		    	ArrayList<Double> moveWinsLosesTies = moves.getValue();
		    	//System.out.println(moves + " move ");
		    	double wins = moveWinsLosesTies.get(0);
		    	double loses = moveWinsLosesTies.get(1);
		    	double ties = moveWinsLosesTies.get(2);
 		    	double total = wins+loses+ties;
		    	double overallScore = (wins/total);
		    	//System.out.println("Wins : " + wins + " Loses : " + loses + " overall : " + overallScore + " " + moves.getKey());
        		//Keeps track of the move which has the highest win ratio
		    	if (bestMove == null || overallScore > best){
        			best = overallScore;
        			bestMove = moves.getKey();
        			//System.out.println(best +" : " + bestMove);
        		}
        	}
	        if (System.currentTimeMillis() < stop) {
		        lastBest = bestMove;
		    } 
	        //Increment simulation counter
	        t++;
		}
		return lastBest;
	}

	Move returnStartingMoveUCB1 (List<Move> legalMoves, int depth, long stop, Map<Move, ArrayList<Double>> previousMoves, double t){
		
		Move bestMove = null;
		double best = 0.0;
		double ucb1Score = 0.0;
		for (Entry<Move, ArrayList<Double>> moves : previousMoves.entrySet()){
			ArrayList<Double> winsLosesTies = moves.getValue();
	    	double wins = winsLosesTies.get(0);
	    	double loses = winsLosesTies.get(1);
	    	double ties = winsLosesTies.get(2);
	    	double n = (wins + loses + ties);
	    	//System.out.println("wins : " + moves.getValue() +  " loses : " + loses + " total " + n + " move : " + moves.getKey());
	    	//Prevents n being 0 if no loses or no wins
	    	if (n==0){n++;}
	    	//System.out.println("wins : " + wins + " total : " + n + " sims : " + t);
	    	//System.out.println(wins/n + " start " + " end " + Math.sqrt((2*Math.log(t))/n));
	    	ucb1Score = (wins/n) + Math.sqrt((2*Math.log(t))/n);
	    	//System.out.println(ucb1Score + " : score " + " move : " + moves.getKey());
	    	if (ucb1Score > best || bestMove == null){
	    		best = ucb1Score;
	    		bestMove = moves.getKey();
	    		System.out.println(moves);
	    	}
	    	//System.out.println(best + " best score " + " best Move : " + bestMove);
    	}
		return bestMove;
	}

Move getSiMin(double d, GameState2P s, double t, long stop, Map<Move, ArrayList<Double>> opponentPreviousMoves,Map<Move,ArrayList<Double>> previousMoves, ArrayList<Double> intialiseCount){
	Move si = null;
	Move bestMove = null;
	double bestucb1 = 0.0;
	double score = 0.0;
	List<Move> opponentMoves = GameState2P.getLegalMoves(s, indexOpponent);
	for (Move m : opponentMoves) {
		t++;
		double w = 0.0;
		GameState2P next = m.doMove(state);
		if (next != null){
			score = getScoreMin(next, 5, stop);
			ArrayList<Double> intialiseCountCopy = new ArrayList<Double>(intialiseCount);
			//Initialise the the scores of all possible outcomes
			opponentPreviousMoves.put(m, intialiseCountCopy);
			ArrayList<Double> winsLosesTies = opponentPreviousMoves.get(m);
	    	//Keep track of wins/loses/ties of each legal move
			if (score > 0){
				winsLosesTies.set(0, winsLosesTies.get(0) + 1);
		    	w++;
		    }
			else if (score < 0){
				winsLosesTies.set(1, winsLosesTies.get(1) + 1);
			}
		    else {
		    	winsLosesTies.set(2, winsLosesTies.get(2) + 1);
		    }
			opponentPreviousMoves.put(m, winsLosesTies);
			//Work out the ucb1 score of each move
		    double ucb1Score = (w/1) + Math.sqrt((2*Math.log(t))/1);
		    //System.out.println(w/1 + " wins " + " move " + m + " t is : " + t + " ucb1 score: " + ucb1Score);
		    //Keep track of the move with the current best ucb1 score
		    if (ucb1Score > bestucb1 || bestMove == null){
		    	bestucb1 = ucb1Score;
		    	bestMove = m;
		    }
		}
	}
	GameState2P next = bestMove.doMove(s);
	if (next.isGameOver() == false){
		if ((d -1 ) == 0){
			return bestMove;
		}
		si = getSiMax(d -1, next, t, stop, opponentPreviousMoves, previousMoves, intialiseCount);
	}
	return si;
}


Move getSiMax(double depth, GameState2P s, double t, long stop, Map<Move, ArrayList<Double>> opponentPreviousMoves,Map<Move,ArrayList<Double>> previousMoves, ArrayList<Double> intialiseCount){
	Move si = null;
	Move bestMove = null;
	double bestucb1 = 0.0;
	double score = 0.0;
	List<Move> myMoves = GameState2P.getLegalMoves(s, index);
	for (Move m : myMoves) {
		t++;
		double w = 0.0;
		//System.out.println(m);
		GameState2P next = m.doMove(state);
		if (next != null){
			score = getScoreMin(next, 5, stop);
			ArrayList<Double> intialiseCountCopy = new ArrayList<Double>(intialiseCount);
			//Initialise the the scores of all possible outcomes
			previousMoves.put(m, intialiseCountCopy);
			ArrayList<Double> winsLosesTies = previousMoves.get(m);
	    	//Keep track of wins/loses/ties of each legal move
			if (score > 0){
				winsLosesTies.set(0, winsLosesTies.get(0) + 1);
		    	w++;
		    }
			else if (score < 0){
				winsLosesTies.set(1, winsLosesTies.get(1) + 1);
			}
		    else {
		    	winsLosesTies.set(2, winsLosesTies.get(2) + 1);
		    }
			previousMoves.put(m, winsLosesTies);
			//Work out the ucb1 score of each move
		    double ucb1Score = (w/1) + Math.sqrt((2*Math.log(t))/1);
		    //System.out.println(w/1 + " wins " + " move " + m + " t is : " + t + " ucb1 score: " + ucb1Score);
		    //Keep track of the move with the current best ucb1 score
		    if (ucb1Score > bestucb1 || bestMove == null){
		    	bestucb1 = ucb1Score;
		    	bestMove = m;
		    }
		}
	}
	GameState2P next = bestMove.doMove(s);
	if (next.isGameOver() == false){
		if ((depth -1 ) == 0){
			return bestMove;
		}
		si = getSiMin(depth -1, next, t, stop, opponentPreviousMoves, previousMoves, intialiseCount);
	}
	return si;
}

double getScoreMin (GameState2P s, int depth, long stop){
	double res; double score;
	//System.out.println(s);
		if (depth == 0 || s.isGameOver()) {
            res = s.evaluateState(index);
        }
        else{
        	List<Move> opponentMoves = GameState2P.getLegalMoves(s, indexOpponent);
        	res = Double.POSITIVE_INFINITY;
        	 if (System.currentTimeMillis() > stop) {
                    return -1;
                }
        	//Checks if it use a random or heuristic move
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
	        	GameState2P next = tempNext;
		    	score = getScoreMax(next, depth -1, stop);
		    	res = Math.min(res, score);
			}
		    else{
		    	// Using the minmax for heuristically best move
		    	Move bestMove = null;
		        double bestScore = 0;
		        for (Move m : opponentMoves) {
		        	GameState2P next = m.doMove(state);
		        	//System.out.println(" move : " + m + " next:  " +  next );
		        	if (next != null){
		        		score = getMinScoreAlphaBeta(next, 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, stop);
			            if (bestMove == null || score > bestScore) {
			            	bestMove = m;
			            	bestScore = score;
				    	}
		        	}
		        }
		        GameState2P next = bestMove.doMove(state);
		        score = getScoreMax(next, depth -1, stop);
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
    	 if (System.currentTimeMillis() > stop) {
             return -1;
         }
    	Random random = new Random();
		int check = random.nextInt(10) + 1;
		if (check == 5){
	        GameState2P tempNext = null;
        	while (tempNext == null){
        		int i = random.nextInt(myMoves.size());
            	Move m = myMoves.get(i);
	        	if (System.currentTimeMillis() > stop) {
	                return -1;
	            }
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
	        	GameState2P next = m.doMove(state);
	        	if (next != null){
	        		score = getMinScoreAlphaBeta(next, 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, stop);
		            if (bestMove == null || score > bestScore) {
		            	bestMove = m;
		            	bestScore = score;
			    	}
		            //System.out.println(m + " : " + " score : " + score);
	        	}
	        }
	        GameState2P next = bestMove.doMove(state);
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