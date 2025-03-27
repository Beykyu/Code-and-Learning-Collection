# Simulated Annealing Algorithm for Kemeny Rankings

This program implements the **Simulated Annealing algorithm** to calculate optimized Kemeny rankings for participants, based on a given scoring system. The algorithm attempts to minimize the Kemeny score by iteratively improving rankings through a probabilistic optimization process.

## Features

- Calculates **Kemeny Rankings**: The program determines optimal rankings of participants to minimize conflicts based on input scores.
- Implements **Simulated Annealing**: Uses a probabilistic approach to explore and refine solutions.
- **Custom Parameters**: Users can specify iterations and fine-tune the algorithm for their use case.
- Efficient input handling via file-based participant and score data.

## Requirements

To run this program, you'll need:
- **Java JDK** version 8 or higher installed on your system.
- A valid **input file** formatted according to the program's requirements.

## Input File Format

The program expects an input file in the following format:
1. **First Line**: Number of participants.
2. **Next Lines**: Each participant's ID and name, separated by a comma.
3. **Following Lines**: Scores indicating preferences between participants, in the format `margin,winner,loser`.

### Example Input File (`input.txt`):

6
1, A
2, B
3, C
4, D
5, E
6, F
9,9,9
3,4,1
5,1,2
2,3,2
14,1,3
1,2,4
9,3,4
3,1,5
12,2,5
1,3,5
7,1,6
6,2,6
2,3,6
3,5,4
6,6,4
5,6,5

## How to Compile and Run

1. **Compile the Program**:
   Use the following commands to compile the source files:
   ```bash
   javac SA.java Simulated_Annealing.java

2. Run the Program: Provide the input file as a command-line argument:
   java Simulated_Annealing input.txt

3. Follow Prompts: The program will prompt you to input the number of iterations for the algorithm. Enter a positive        integer, and the results will be displayed after execution.

## Output

The program provides:
- Optimal Participant Rankings: A list of participants in the most optimal order.
- Kemeny Score: The minimum score calculated.
- Execution Time: Total runtime of the algorithm.

## Example Output:

[Alice, Bob, Charlie, Diana]
[1, 2, 3, 4]
Best Kemeny Score: 25
Algorithm run time was : 250ms

## File Structure

The program uses two main classes:
- SA.java : Contains the logic for handling rankings, scores, and participants.
- Simulated_Annealing.java : Manages the Simulated Annealing algorithm, including file reading and optimization steps