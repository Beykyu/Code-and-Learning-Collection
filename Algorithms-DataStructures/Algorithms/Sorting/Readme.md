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
