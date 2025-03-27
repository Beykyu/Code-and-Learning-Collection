# Quoridor AI Implementation

This project implements various AI strategies for the board game Quoridor. The core game engine was developed by Steven (university lecturer), while I focused on developing different AI approaches to play the game.

## Game Overview
Quoridor is a strategy board game where players try to reach the opposite side of the board while blocking opponents with walls. The base game implementation includes:
- A 5x5 board
- 2 players
- Wall placement mechanics
- Basic movement rules
- Path finding algorithms

## My AI Implementations

### 1. Random Simulation Player
- Uses random simulations
- Tracks win/loss ratios for each possible move
- Chooses moves based on best performing simulation results
- Implements iterative deepening to maximize search in available time

### 2. Heuristic Simulation Player
- Combines random and heuristic-based move selection
- Uses MinMax with Alpha-Beta pruning for heuristic moves
- Randomly switches between random and heuristic moves during simulation
- Maintains statistics about move performance

### 3. UCB1 Simulation Player 
- Implements the UCB1 (Upper Confidence Bound) algorithm
- Balances exploration and exploitation of move options
- Uses UCB1 formula to select promising moves
- Combines with MinMax for opponent modeling

### 4. Recursive UCB1 Simulation Player (Experimental)
- Attempted to implement a recursive version of UCB1
- Aims to look deeper into the game tree
- Note: Implementation is incomplete/experimental

## Technical Details
- All AI players extend the base QuoridorPlayer class
- Each implementation uses 5 second time limits per move
- Different search depths and strategies are used to optimize performance
- Move selection is tracked and analyzed to inform future decisions

## Running the Game
Launch through the Quoridor class which creates a graphical interface and sets up an AI vs AI game by default.

## Implementation Details

### 1. Random Simulation Player
- Implements pure random simulation without any heuristics
- For each possible move:
  - Performs random playouts to a certain depth
  - Tracks win/loss ratio in a HashMap
  - Uses iterative deepening within 5 second time limit
  - Selects move with best win ratio after simulations
- Key features:
  - Uses HashMap<Move, ArrayList<Double>> to track move statistics
  - First index stores wins, second stores losses
  - Updates statistics after each playout
  - Handles null game states by retrying random moves

### 2. Heuristic Simulation Player  
- Hybrid approach combining random and heuristic-guided play
- For each simulation:
  - 10% chance to make random move
  - 90% chance to use MinMax with Alpha-Beta pruning
  - Maintains win/loss statistics like Random Player
- Advantages:
  - More intelligent than pure random
  - Still maintains exploration through random moves
  - Better performance against deterministic opponents

### 3. UCB1 Simulation Player
- Implements Upper Confidence Bound 1 algorithm
- UCB1 formula: vi + sqrt(2*ln(N)/ni)
  - vi = wins/plays for move i
  - N = total number of plays
  - ni = number of times move i was played
- Features:
  - Auto-balances exploration vs exploitation
  - Favors moves with high win rates
  - Explores under-sampled moves
  - Uses MinMax for opponent modeling

### 4. Recursive UCB1 Player (Experimental)
- Attempted to extend UCB1 recursively through game tree
- Challenges faced:
  - Maintaining separate statistics at each level
  - Balancing recursive depth vs time constraints
  - Managing memory usage for deep trees
- Current limitations:
  - Incomplete implementation
  - Performance issues with deep recursion
  - Time management problems

## Performance Comparison
- Random Player: Good baseline, unpredictable but sub-optimal
- Heuristic Player: Better than random, more consistent
- UCB1 Player: Best performance, balances exploration/exploitation
- Recursive UCB1: Promising concept but needs optimization

## Running Different AIs
Modify the Quoridor.java file to use different AI players:
```java
players[0] = new RandomSimulationPlayer(state, 0, this);
players[1] = new UCB1SimulationPlayer(state, 1, this);
```

## Code Structure & Implementation
The AI players are located in the `players` package:
- RandomSimulationPlayer.java: Pure Random simulation approach
- HeuristicSimulationPlayer.java: Hybrid random/heuristic implementation
- UCB1SimulationPlayer.java: UCB1 algorithm implementation
- RecursiveUCB1SimulationPlayer.java: Experimental recursive UCB1 approach

### Key Classes
- QuoridorPlayer: Base abstract class for all AI implementations
- GameState2P: Maintains game state and provides evaluation methods
- Wall: Represents wall placements
- Move: Interface for all possible moves

### Common AI Features
- Time Management: All AIs use 5-second time limits per move
- Move Statistics: Track performance in HashMaps
- Iterative Deepening: Gradually increase search depth
- State Evaluation: Use GameState2P.evaluateState() for position scoring

## Building & Running

1. Compile all Java files in the project
2. Run the Quoridor class to start the game
3. To change AI players, modify the player initialization in Quoridor.java:

```java
players[0] = new RandomSimulationPlayer(state, 0, this);
players[1] = new UCB1SimulationPlayer(state, 1, this);
```

## Observations & Results
- UCB1 player shows best overall performance
- Random simulation provides good baseline
- Heuristic approach balances exploration/exploitation
- Recursive UCB1 shows promise but needs optimization

## Credits
- Core game engine: Steven (University Lecturer)
- AI implementations: Jason
