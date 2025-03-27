import java.io.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class turingRun {

	public static void main( String[] args ) {
	newRun();
	}

	//Function handling new turing machine being tested
	public static void newRun(){
		System.out.println("Please enter the name of the file containing the TM");
		try {
		//Reads the user's input and passes it to a function to create the TM
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String fileName = br.readLine();
		createTM(fileName);
		} 
		catch (IOException e) {
		   System.err.println("IO Exception: " + e.getMessage());
		} 
	}

	//Function to create the turing machine which will be used
	public static void createTM(String fileName){
		//Uses the file name to create the array list holding TM ingredients
		List<String> list = readFile(fileName);

		int fileLength = list.size();
		//Creates new instance of a turing machine class
		turingMachine tm = new turingMachine();

		/* Each TM ingredient is created by getting the elements corresponding to them from the arraylist read from the file
		Each of these elements are split into their different parts, these parts are seperated by commas.
		These parts are checked to see if they are valid then added to their own arraylist corresponding to their TM ingredient.
		*/

		//Sets the possible states of the TM
		int stateNumber = Integer.parseInt(list.get(0));
		List<String> states = Arrays.asList(list.get(1).split(",[ ]*"));
		for (int i=0;i<stateNumber;i++){
			tm.setNewState(states.get(i));
		}

		//Sets the possible input symbols of the TM
		int inputAphNumber = Integer.parseInt(list.get(2));
		List<String> inputAph = Arrays.asList(list.get(3).split(",[ ]*"));
		for (int i=0;i<inputAphNumber;i++){
			tm.setNewInputAph(inputAph.get(i));
		}

		//Sets the possible tape symbols of the TM
		int tapeAphNumber = Integer.parseInt(list.get(4));
		List<String> tapeAph = Arrays.asList(list.get(5).split(",[ ]*"));
		for (int i=0;i<tapeAphNumber;i++){
			tm.setNewTapeAph(tapeAph.get(i));
		}

		//Sets the possible transitions of the TM
		int transitionsNumber = Integer.parseInt(list.get(6));
		for (int i = 7; i < fileLength - 3; i++){
			tm.setNewTransitionString(list.get(i));
		}

		//Sets the states of the turing machine
		tm.setStartState(list.get(fileLength - 3));
		tm.setAcceptState(list.get(fileLength - 2));
		tm.setRejectState(list.get(fileLength - 1));

		System.out.println("The starting state for this turing machine is : " + tm.startState);
		System.out.println("The accept state for this turing machine is : " + tm.acceptState);
		System.out.println("The reject state for this turing machine is : " + tm.rejectState);

		//Reads user input to begin testing a tape
		try {
		System.out.println("Please enter the tape you would like to test");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String tape = br.readLine();
		tm.testTape(tape);
		} 
		catch (IOException e) {
		   System.err.println("IO Exception: " + e.getMessage());
		} 
	}

	/*Function which takes the name of the file,
	reads it and places each line of the file into an ArrayList*/
	public static List<String> readFile(String fileName) {
		List<String> list = new ArrayList<>();
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
			}
		}
		return list;
	} 
}