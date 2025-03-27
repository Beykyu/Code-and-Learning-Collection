# Turing Machine Simulator

A Java implementation of a Turing Machine simulator that reads machine configurations from text files and executes them.

## Overview

This program simulates a Turing Machine by:
- Reading machine configurations from text files
- Processing input tapes according to specified transition rules
- Determining if input strings are accepted or rejected

## Components

### Main Classes
1. **turingRun.java**
   - Main driver class
   - Handles file input/output
   - Creates and manages Turing Machine instances

2. **turingMachine.java**
   - Core Turing Machine implementation
   - Manages states, transitions, and tape operations
   - Processes input strings

### Input Files
The program uses text files (e.g., `turingTest.txt` (accept strings of unary numbers and doubles them e.g 111), `turingTest2.txt` ( accepts strings that represent powers of 2 in unary notation)) that define:
- Number of states
- State names (q1, q2, etc.)
- Input alphabet
- Tape alphabet
- Transition functions
- Start, accept, and reject states

## File Format

Input files must follow this structure:
```
[number of states]
[state names, comma-separated]
[size of input alphabet]
[input alphabet symbols]
[size of tape alphabet]
[tape alphabet symbols]
[number of transitions]
[transitions in format: current_state,read_symbol,write_symbol,next_state,direction]
[start state]
[accept state]
[reject state]
```

### Example Configuration
```
5
q1,q2,q3,q4,qr
2
0,1
4
x,1,0,_
12
q1,1,x,q1,R
...
q1
q4
qr
```

## Usage

1. Compile the Java files:
```bash
javac turingRun.java turingMachine.java
```

2. Run the program:
```bash
java turingRun
```

3. When prompted:
   - Enter the configuration file name (e.g., turingTest.txt)
   - Input the tape string to be processed (e.g, 111)

## Features

- Supports multiple states and transitions
- Handles blank symbols (_) automatically
- Left/Right tape movement
- Dynamic tape expansion
- Input validation
- Interactive testing interface

## Error Handling

The program validates:
- State definitions
- Alphabet symbols
- Transition rules
- Input tapes
- File formats

## Test Files Included

1. **turingTest.txt**
   - Basic configuration
   - Tests binary string processing

2. **turingTest2.txt**
   - More complex configuration
   - Tests multi-state transitions

## Requirements

- Java JDK 8 or higher
- Text editor for creating/modifying machine configurations
- Command-line interface for running the simulator
