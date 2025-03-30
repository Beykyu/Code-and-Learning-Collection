# Sorting Algorithms

This directory contains implementations of common sorting algorithms. Each algorithm has its own characteristics, making them suitable for different scenarios.

## Insertion Sort

### How it Works
1. Divides the array into sorted (left) and unsorted (right) portions
2. Takes one element from unsorted portion and finds its correct position in sorted portion
3. Shifts elements right to make space for the new element
4. Repeats until all elements are sorted

### Example
```python
Initial array: [5, 2, 4, 1, 3]

Pass 1: [2, 5, 4, 1, 3]  # 2 is inserted before 5
Pass 2: [2, 4, 5, 1, 3]  # 4 is inserted between 2 and 5
Pass 3: [1, 2, 4, 5, 3]  # 1 is inserted at start
Pass 4: [1, 2, 3, 4, 5]  # 3 is inserted between 2 and 4
```

### Characteristics
- Time Complexity: O(n²) average/worst, O(n) best
- Space Complexity: O(1)
- Stable: Yes
- In-place: Yes
- Best for: Small arrays or nearly sorted arrays

## Bubble Sort

### How it Works
1. Repeatedly steps through the list
2. Compares adjacent elements and swaps them if they're in wrong order
3. Continues until no swaps are needed

### Example
```python
Initial array: [5, 2, 4, 1, 3]

Pass 1: [2, 4, 1, 3, 5]  # 5 bubbles to end
Pass 2: [2, 1, 3, 4, 5]  # 4 bubbles to correct position
Pass 3: [1, 2, 3, 4, 5]  # List gets sorted
Pass 4: [1, 2, 3, 4, 5]  # No swaps needed, done!
```

### Characteristics
- Time Complexity: O(n²) average/worst, O(n) best
- Space Complexity: O(1)
- Stable: Yes
- In-place: Yes
- Best for: Educational purposes, very small arrays

## Selection Sort

### How it Works
1. Divides array into sorted (left) and unsorted (right) portions
2. Finds minimum element in unsorted portion
3. Swaps it with first element of unsorted portion
4. Repeats until array is sorted

### Example
```python
Initial array: [5, 2, 4, 1, 3]

Pass 1: [1, 2, 4, 5, 3]  # 1 is smallest, swapped with 5
Pass 2: [1, 2, 4, 5, 3]  # 2 is already in position
Pass 3: [1, 2, 3, 5, 4]  # 3 swapped with 4
Pass 4: [1, 2, 3, 4, 5]  # 4 swapped with 5
```

### Characteristics
- Time Complexity: O(n²) all cases
- Space Complexity: O(1)
- Stable: No
- In-place: Yes
- Best for: Small arrays, when memory is limited

## Merge Sort

### How it Works
1. Divides the array recursively into two halves until size becomes 1
2. Merges the smaller sorted arrays into new sorted arrays
3. Continues merging until the entire array is sorted
4. Uses auxiliary space to merge the sorted subarrays

### Example
```python
Initial array: [5, 2, 4, 1, 3]

Divide: [5, 2] [4, 1, 3]
Divide: [5] [2] [4] [1, 3]
Divide: [5] [2] [4] [1] [3]

Merge: [2, 5] [1, 4] [3]
Merge: [2, 5] [1, 3, 4]
Merge: [1, 2, 3, 4, 5]
```

### Characteristics
- Time Complexity: O(n log n) all cases
- Space Complexity: O(n)
- Stable: Yes
- In-place: No
- Best for: Large datasets, when stable sorting is needed

## Quick Sort

### How it Works
1. Selects a 'pivot' element from the array
2. Partitions other elements into two sub-arrays:
   - Elements less than pivot
   - Elements greater than pivot
3. Recursively applies the same process to the sub-arrays
4. Combines the sorted sub-arrays with the pivot

### Example
```python
Initial array: [5, 2, 4, 1, 3]

Choose pivot (3):
Partition: [2, 1] [3] [5, 4]
Recursion: [1, 2] [3] [4, 5]
Result: [1, 2, 3, 4, 5]
```

### Characteristics
- Time Complexity: O(n log n) average, O(n²) worst
- Space Complexity: O(log n)
- Stable: No
- In-place: Yes
- Best for: General-purpose sorting, works well in practice

## Counting Sort

### How it Works
1. Creates a count array to store the frequency of each element
2. Modifies count array to store actual positions of elements
3. Builds the output array using the count array
4. Places each element in its sorted position

### Example
```python
Initial array: [4, 2, 4, 1, 3]
Range: 1 to 4 (size of count array: 4)

Count array: [0, 1, 1, 1, 2]   # Frequencies: 1 appears once, 2 once, 3 once, 4 twice
Count array: [0, 1, 2, 3, 5]   # Cumulative sum to get positions

Building result (right to left):
- Reading 3: place at position count[3] = 3 - 1 = 2, decrease count[3] to 2
- Reading 1: place at position count[1] = 1 - 1 = 0, decrease count[1] to 0
- Reading 4: place at position count[4] = 5 - 1 = 4, decrease count[4] to 4
- Reading 2: place at position count[2] = 2 - 1 = 1, decrease count[2] to 1
- Reading 4: place at position count[4] = 4 - 1 = 3, decrease count[4] to 3

Result: [1, 2, 3, 4, 4]
```

### Characteristics
- Time Complexity: O(n + k) where k is the range of input
- Space Complexity: O(k)
- Stable: Yes
- In-place: No
- Best for: Integer sorting with known range, counting frequencies

## Usage
Each sorting algorithm can be used with either ascending (default) or descending order:

```python
# Ascending order (default)
sorted_list = insertion_sort(my_list)

# Descending order
sorted_list = insertion_sort(my_list, descending)
```

## Choosing the Right Algorithm

- **Insertion Sort**: Use when the array is small or nearly sorted
- **Bubble Sort**: Rarely used in practice, mainly for educational purposes
- **Selection Sort**: Use when memory writes are expensive
- **Merge Sort**: Use when stable sorting is needed and extra space is available
- **Quick Sort**: Best general-purpose sorting algorithm, efficient in practice
- **Counting Sort**: Use for integer sorting with a known range
