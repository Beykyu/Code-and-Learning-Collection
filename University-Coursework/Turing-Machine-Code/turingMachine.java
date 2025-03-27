import java.util.*;
import java.io.*;

public class turingMachine {
	String output;
	List<String> states;
	List<String> inputAph;
	List<String> tapeAph;
	ArrayList<Transition> transitions;
	String startState;
	String acceptState;
	String rejectState;
	String currentState;
	String currentAph;
	String inputTape;

	/* ------------------------------------------------------------------
	---Main-function-which-tests-whether-to-accept-or-reject-the-tape----
	-------------------------------------------------------------------*/

	/*It gets the current state of the TM, then loops through all of the transitions looking for a read state that is equal to it.
	If it matches it looks at the current read symbol of the tape and gets the read symbol of the transition and check if they match.
	If they match the read symbol on the tape is replaced with the write symbol of the transition and the current state is moved to the 
	write state of the transition.
	It then goes back to the beginning and repeats this until a accept or reject state is reached.
	Blank symbols are added to the tape if the pointer reaches either ends of the tape.
	*/

	public void testTape (String input){
		//Initialises the current state as the start state 
		currentState = startState;

		//Appends blank symbols to both ends of the input tape
		input = "_"  + input + "_";
		//Creates the tape as an arraylist and initialises the tape variables
		List<String> tape = Arrays.asList(input.split("(?!^)"));
		int tapeLength = tape.size();
		int tapePos = 1;
		currentAph = tape.get(tapePos);

		/*Checks that the input tape does not contain symbols that are not part of the input alphabet
		To prevent invalid tapes being entered and checked*/
		for (int i=0;i<tapeLength;i++){
			if (!inputAph.contains(tape.get(i)) && !tape.get(i).equals("_")){
				System.out.println("You have entered an invalid tape"); 
				System.out.println("It contains non-input alphabet symbols");
				System.out.println("Please enter a correct one.");
				try {
					//Reads the user's input and trys to see if it's valid
				    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					String newTape = br.readLine();
					testTape(newTape);
				} 
				catch (IOException e) {
					System.err.println("IO Exception: " + e.getMessage());
				}
			}
		}
		//For the user to know what is being used to test the tape
		System.out.println("You are testing the tape : " + input);
		
		//Loop until it reaches an accept or reject state
		while((!currentState.equals(acceptState)) && (!currentState.equals(rejectState))){
			//Number of transitions to check
			int tLength = transitions.size();

			//Exits the while loop
			/*For rejecting the tape if none of the transitions alter the tape, 
			non-specified transitions are assumed to led to a reject state  */
			boolean reject = true;

			//Checks each transition of the TM
			for (int i =0; i <tLength ; i++){
				//Get the read state of the current transition
				String tempState = transitions.get(i).currentState;
				System.out.println("The state of the " + (i+1) + "th transition is : " + tempState);
				//Checks if the state in the current transition is the same as the current state, executes statement if true
				if (tempState.equals(currentState)){
					//Gets the read symbol of the current transition
					char r = transitions.get(i).readAph;
					System.out.println("Checking if " + r + " equals " + currentAph);
					//Gets the current read symbol of the current state and converts it to a type char
					char c = currentAph.charAt(0);
					//Checks if the read symbol on the current transition matches the the current read symbol, executes if true
					if (r == c){
						//Gets the write symbol and converts it to string
						String replace = Character.toString(transitions.get(i).writeAph);
						System.out.println("Replacing " + currentAph + " with " + replace + " at position " + tapePos + " in the tape");
						//Sets the write symbol in the position of the current symbol replacing it
						tape.set(tapePos, replace);
						//Creates a string to display from the current elements of the tape arraylist
						output="";
						for(int j=0; j<tapeLength; j++){
							output += tape.get(j);
						}
						System.out.println("This is the current tape : " + output);
						System.out.println("Now moving to " + transitions.get(i).nextState);
						//Sets the current state to be the next state to move to
						currentState = transitions.get(i).nextState;
						System.out.println("The current state is : "+ currentState);
						//Sets the direction to move the pointer left or right
						char d = transitions.get(i).direction;
						/*Handles where the position of the pointer goes, R causes the 
						counter to be incremented so the pointer moves right otherwise it is decremented
						and the pointer moves left*/
						if (d == "R".charAt(0)){
							tapePos++;
						}
						else {tapePos--;
						}
						/*Handles if the pointer reaches the last element in the tape.
						It concats a blank symbol to the end and increases the length by 1 */
						if (tapePos == tapeLength){
							String tempOut = "";
							//Converts the elements in the arraylist to a string by adding each element in turn to a string
							for(int y=0; y<tapeLength; y++){
								tempOut += tape.get(y);
							}
							//Concats a blank symbol to the end of the string then converts back to an array list incrementing the length by 1
							tempOut += "_";
							tape = Arrays.asList(tempOut.split("(?!^)"));
							tapeLength++;
						}
						/*Handles if the pointer reaches the first element in the tape.
						It adds a blank symbol to the start and increases the length by 1 
						Essential adds a blank symbol to position 0 and moves the positions
						of existing elements up 1*/
						else if (tapePos == -1){
							String tempOut = "";
							for(int y=0; y<tapeLength; y++){
								tempOut += tape.get(y);
							}
							//Block of code does the same as above except the blank symbol is inserted at the start instead of the end
							tempOut = "_" + tempOut;
							tape = Arrays.asList(tempOut.split("(?!^)"));
							tapeLength++;
						}
						//Sets the current read symbol to be the symbol with the pointer on it
						currentAph = tape.get(tapePos);
						System.out.println("Tape pos is "  + tapePos);
						//Prevents tape from being rejected so it can keep checking the tape
						reject = false;
						//For leaving the loop to loop  through all of the transitions again
						break;
					}
					else {
						System.out.println("They are not equal, moving to the next transition");
					}
				}
			}
			//Rejects tape if no change to the 
			if (reject ==true){
				currentState = rejectState;
			}	
		}
		//Decides whether to reject or accept the tape
		if (currentState.equals(acceptState)){
			outputStatements(tape, "accepted");
		}
		else {
			outputStatements(tape, "rejected");
		}
	}


	//function to print out the results of the turingMachine
	public void outputStatements(List<String> tape, String result){
		int j = tape.size();
		output="";
		//Creates an output string by appending all elements from the tape listarray together
		for(int i=0; i<j; i++){
			output += tape.get(i);
		}
		//Removes the blank symbols from the tape output
		output = output.replace("_", "");
		//Displays output and checks if user wants to continue
		System.out.println("The tape has been " + result + 
			", this is the following output : " + output + System.lineSeparator());
		checkToContinue();
	}


	//Once tape has been rejected or accepted this checks if the user wants to try a different TM or tape 
	public void checkToContinue(){
		System.out.println("Would you like to test another tape with the same TM? y/n");
		try {
			//For if the user wants to try using a different tape using the same TM
			//Reads the user's input and passes it to a function to being creating the TM
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String tmAns = br.readLine();
			if (tmAns.equals("y")){
					System.out.println("Please enter the tape you would like to test");
					String newTape = br.readLine();
					testTape(newTape);
			}
			else {
				//For if the user wants to try using a different TM
				//Checks if they want to try using a different TM
				System.out.println("Would you like to continue by testing another TM? y/n");
				String newTM = br.readLine();
				if (newTM.equals("y")){
					turingRun.newRun();
				}
				else{
					System.exit(1);
				}
			}
		} 
		catch (IOException e) {
		   System.err.println("IO Exception: " + e.getMessage());
		} 
	}


	//Creates a class for the turing machine 
	public turingMachine() {
		states = new ArrayList<String>();
		inputAph = new ArrayList<String>();
		tapeAph = new ArrayList<String>();
		transitions = new ArrayList<Transition>();
		startState = new String("");
		acceptState = new String("");
		rejectState = new String("");
		currentState = new String("");
		currentAph = new  String("");
		inputTape = new String("");
	}


	//Creates a class for a single transition
	class Transition{
		String currentState;
		char readAph;
		char writeAph;
		String nextState;
		char direction;
	}

	//Checks if each transition entered is correct
	public void setNewTransitionString (String newTransitionString){
		String error = "";
		boolean stop = false;
		//Gets the entire transition string and creates an arraylist to hold them. Each part is assigned to a variable.
		List<String> tempTrans = Arrays.asList(newTransitionString.split(",[ ]*"));
		String currentState = tempTrans.get(0);
		char readAph = tempTrans.get(1).charAt(0);
		String writeAph = tempTrans.get(2);
		String nextState = tempTrans.get(3);
		char direction = tempTrans.get(4).charAt(0);
		//Checks that the states in the transition are part of the valid states stops if they are not
		if(!states.contains(currentState) || !states.contains(nextState))
		{
			stop = true;
			error += "the states in the transition are incorrect, they are not part of the possible states";
		}
		//Checks to see if the write symbol is part of the tape alphabet stops if it isn't
		else if (!tapeAph.contains(writeAph)){
			stop = true;
			error += "the write symbol entered for this transition is not part of the tape alphabet";
		}
		//If there are no issues, create a new transition object using those variables
		if (stop == false ){
			Transition t = new Transition();
			t.currentState = currentState;
			t.readAph = readAph;
			t.writeAph = writeAph.charAt(0);
			t.nextState = nextState;
			t.direction = direction;
			transitions.add(t);
		}
		//Handles any issues
		else{
			System.out.println("There is a problem with your transition, " + error);
			System.exit(1);
		}
	}


	//Sets a new state and checks if it's valid by ensuring it does not contain duplicates
	public void setNewState(String newState){
		if (states.contains(newState)){
			System.out.println("There is a problem with your selected state : " + newState +
				" ,it has already been entered as a possible state");
			System.exit(1);
		}
		else{
			states.add(newState);
		}
	}
	

	//Sets a new symbol for the input alphabet and checks if it's valid by ensuring it does not contain duplicates
	public void setNewInputAph(String newInputAph){
		if (inputAph.contains(newInputAph)){
			System.out.println("There is a problem with your selected symbol : " + newInputAph +
				" ,it has already been entered as a possible input symbol");
			System.exit(1);
		}
		else{
			inputAph.add(newInputAph);
		}
	}


	//Sets a new symbol for the input alphabet and checks if it's valid by ensuring it does not contain duplicates
	public void setNewTapeAph(String newTapeAph){
		if (tapeAph.contains(newTapeAph)){
			System.out.println("There is a problem with your selected symbol : " + newTapeAph +
				" ,it has already been entered as a possible tape symbol");
			System.exit(1);
		}
		else{
			tapeAph.add(newTapeAph);
		}
	}


	//Sets a start state and check if it's valid by ensuring that the state exists as a possible state
	public void setStartState(String newStartState){
		if (states.contains(newStartState)){
			startState = newStartState;
		}
		else{
			System.out.println("There is a problem with your selected start state : " + newStartState +
				" ,it is not part of the possible states");
			System.exit(1);
		}		
	}
	

	//Sets a accept state and check if it's valid by ensuring that the state exists as a possible state
	public void setAcceptState(String newAcceptState){
		if (states.contains(newAcceptState) && (!rejectState.equals(newAcceptState))){
			acceptState = newAcceptState;
		}
		else{
			System.out.println("There is a problem with your selected accept state : " + newAcceptState +
				" ,either it is not part of the possible states or it is the same as the reject state");
			System.exit(1);
		}
	}
	

	//Sets a reject state and check if it's valid by ensuring that the state exists as a possible state
	public void setRejectState(String newRejectState){
		if (states.contains(newRejectState) && (!acceptState.equals(newRejectState))){
			rejectState = newRejectState;
		}
		else{
			System.out.println("There is a problem with your selected reject state : " + newRejectState +
				" ,either it is not part of the possible states or it is the same as the accept state");
			System.exit(1);
		}
	}
}
