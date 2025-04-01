from typing import Any, List
import sys

"""
Implementation of the Maximum Subarray problem using divide-and-conquer approach.
Time Complexity: O(n log n)
Space Complexity: O(log n) due to recursion stack

The maximum subarray problem is to find a contiguous subarray within a list 
of numbers that has the largest sum.
"""

def find_max_crossing_subarray(lst: List[int], low: int, mid: int, high: int):
    """
    Find the maximum subarray that crosses the midpoint of the array.
    
    Args:
        lst: Input list of integers
        low: Starting index of the subarray
        mid: Middle index of the subarray
        high: Ending index of the subarray
    
    Returns:
        Tuple containing (left_index, right_index, maximum_sum)
    """
    left_sum: int = -sys.maxsize - 1
    sum: int = 0
    for i in range(mid, low - 1, -1):
        sum += lst[i]
        if sum > left_sum:
            left_sum = sum
            max_left: int = i
    right_sum: int = -sys.maxsize - 1
    sum = 0
    for j in range(mid + 1, high + 1):
        sum += lst[j]
        if sum > right_sum:
            right_sum = sum
            max_right: int = j
    return (max_left, max_right, left_sum + right_sum)

def find_maximum_subarray(lst: List[int], low: int, high: int):
    """
    Find the contiguous subarray with maximum sum using divide and conquer.
    
    The algorithm works by:
    1. Dividing the array into two halves
    2. Recursively finding the maximum subarray in left and right halves
    3. Finding the maximum subarray that crosses the middle
    4. Returning the maximum of these three possibilities
    
    Args:
        lst: Input list of integers
        low: Starting index of the subarray
        high: Ending index of the subarray
    
    Returns:
        Tuple containing (start_index, end_index, maximum_sum)
    """
    if high == low:
        return (low, high, lst[low])
    else:
        mid: int = (low + high) // 2
        left_low, left_high, left_sum = find_maximum_subarray(lst, low, mid)
        right_low, right_high, right_sum = find_maximum_subarray(lst, mid + 1, high)
        cross_low, cross_high, cross_sum = find_max_crossing_subarray(lst, low, mid, high)
        if left_sum >= right_sum and left_sum >= cross_sum:
            return (left_low, left_high, left_sum)
        elif right_sum >= left_sum and right_sum >= cross_sum:
            return (right_low, right_high, right_sum)
        else:
            return (cross_low, cross_high, cross_sum)

def main():
    # Test case 1: Mixed positive and negative numbers
    test1 = [-5, 3, -10, 0, 8, -2]
    print("Test 1 - Mixed positive and negative:")
    print("Input array:", test1)
    start, end, max_sum = find_maximum_subarray(test1, 0, len(test1) - 1)
    print(f"Maximum subarray: {test1[start:end+1]}")
    print(f"Start index: {start}, End index: {end}")
    print(f"Sum: {max_sum}\n")
    
    # Test case 2
    test2 = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
    print("Test 2 - Classic example:")
    print("Input array:", test2)
    start, end, max_sum = find_maximum_subarray(test2, 0, len(test2) - 1)
    print(f"Maximum subarray: {test2[start:end+1]}")
    print(f"Start index: {start}, End index: {end}")
    print(f"Sum: {max_sum}\n")
    
    # Test case 3
    test3 = [13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7]
    print("Test 3 - CLRS book example:")
    print("Input array:", test3)
    start, end, max_sum = find_maximum_subarray(test3, 0, len(test3) - 1)
    print(f"Maximum subarray: {test3[start:end+1]}")
    print(f"Start index: {start}, End index: {end}")
    print(f"Sum: {max_sum}\n")
    
    # Test case 4: All negative numbers
    test4 = [-2, -3, -4, -1, -7, -5]
    print("Test 4 - All negative numbers:")
    print("Input array:", test4)
    start, end, max_sum = find_maximum_subarray(test4, 0, len(test4) - 1)
    print(f"Maximum subarray: {test4[start:end+1]}")
    print(f"Start index: {start}, End index: {end}")
    print(f"Sum: {max_sum}")

if __name__ == "__main__":
    main()