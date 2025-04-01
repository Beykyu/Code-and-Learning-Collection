# Optimization Algorithms

This directory contains implementations of optimization algorithms. Each algorithm solves specific optimization problems efficiently.

## Maximum Subarray

### How it Works
1. Uses divide-and-conquer approach to find contiguous subarray with largest sum
2. Divides array into two halves recursively
3. Finds maximum subarray in:
   - Left half
   - Right half
   - Crossing the middle
4. Returns the maximum of these three possibilities

### Example
```python
Initial array: [-2, 1, -3, 4, -1, 2, 1, -5, 4]

Divide into halves:
Left: [-2, 1, -3, 4]
Right: [-1, 2, 1, -5, 4]
Cross middle: [4, -1, 2]

Maximum subarray: [4, -1, 2, 1]  # Sum = 6
```

### Characteristics
- Time Complexity: O(n log n)
- Space Complexity: O(log n)
- Algorithm Type: Divide and Conquer
- Best for: Finding maximum sum subsequence in array with mixed positive/negative numbers

### Usage
```python
# Initialize your array
array = [-2, 1, -3, 4, -1, 2, 1, -5, 4]

# Get maximum subarray (returns start_index, end_index, maximum_sum)
start, end, max_sum = find_maximum_subarray(array, 0, len(array) - 1)

# Extract the maximum subarray
max_subarray = array[start:end+1]
```


