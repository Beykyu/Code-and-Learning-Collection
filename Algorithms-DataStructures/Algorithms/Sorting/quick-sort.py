from typing import Any, Callable, List

def ascending(a, b) -> bool:
    return a < b

def descending(a , b) -> bool:
    return a > b

# QuickSort is a divide-and-conquer algorithm that works by:
# 1. Selecting a pivot element
# 2. Partitioning the array around the pivot
# 3. Recursively sorting the sub-arrays

def quick_sort(lst : List[Any], lower : int, upper : int, comp_func : Callable[[Any, Any],Any] = ascending) -> List[Any]:
    """
    Sorts a list using the QuickSort algorithm.
    
    Args:
        lst: List to be sorted
        lower: Starting index of the sublist
        upper: Ending index of the sublist (exclusive)
        comp_func: Comparison function to determine order (default: ascending)
    
    Returns:
        List : Sorted list

    Time Complexity:
        - Best/Average: O(n log n)
        - Worst: O(n²) when array is already sorted
    Space Complexity: 
        O(log n) due to recursive call stack
    """
    if upper > lower:
        # Partition array and get pivot index
        pivot_index : int = partition(lst, lower, upper, comp_func)
        # Sort subarrays recursively
        quick_sort(lst, lower, pivot_index, comp_func)        # Sort left of pivot
        quick_sort(lst, pivot_index + 1, upper, comp_func)    # Sort right of pivot
    return lst

# The partition process:
# 1. Selects last element as pivot
# 2. Places all elements that should be left of pivot (according to comp_func)
#    at the beginning of the array
# 3. Places pivot in its final position

def partition(lst : List[Any], lower : int, upper : int, comp_func : Callable[[Any, Any],Any] = ascending) -> int:
    """
    Partitions the list around a pivot element.
    
    Args:
        lst: List to be partitioned
        lower: Starting index of partition
        upper: Ending index of partition (exclusive)
        comp_func: Comparison function to determine order (default: ascending)
    
    Returns:
        Index of the pivot element after partitioning
    """
    pivot = lst[upper-1]    # Choose last element as pivot
    i = lower - 1          # Index for smaller element
    
    # Compare each element with pivot
    for j in range(lower, upper - 1):
        if comp_func(lst[j], pivot):    # If current element should be left of pivot
            i += 1                       # Increment index of smaller element
            lst[i], lst[j] = lst[j], lst[i]    # Swap elements
            
    # Place pivot in its final position
    lst[i+1], lst[upper - 1] = lst[upper - 1], lst[i+1]
    return i + 1

def main():
    # Test case 1: Regular integers
    test1 = [43, 123, 73, 123, 74, 23, 62, 73, 13, 8, 1, 45]
    print("Test 1 - Regular integers:")
    print("Before:", test1)
    print("After:", quick_sort(test1.copy(), 0, len(test1)))
    
    # Test case 2: Already sorted list
    test2 = [1, 2, 3, 4, 5]
    print("\nTest 2 - Already sorted:")
    print("Before:", test2)
    print("After:", quick_sort(test2.copy(), 0, len(test2)))
    
    # Test case 3: Reverse sorted list
    test3 = [5, 4, 3, 2, 1]
    print("\nTest 3 - Reverse sorted:")
    print("Before:", test3)
    print("After:", quick_sort(test3.copy(), 0, len(test3)))
    
    # Test case 4: Empty list
    test4 = []
    print("\nTest 4 - Empty list:")
    print("Before:", test4)
    print("After:", quick_sort(test4.copy(), 0, len(test4)))
    
    # Test case 5: List with one element
    test5 = [1]
    print("\nTest 5 - Single element:")
    print("Before:", test5)
    print("After:", quick_sort(test5.copy(), 0, len(test5)))
    
    # Test case 6: List with duplicate elements
    test6 = [1, 1, 1, 1, 1]
    print("\nTest 6 - All duplicates:")
    print("Before:", test6)
    print("After:", quick_sort(test6.copy(), 0, len(test6)))
    
    # Test case 7: List with negative numbers
    test7 = [-5, 3, -10, 0, 8, -2]
    print("\nTest 7 - Negative numbers:")
    print("Before:", test7)
    print("After:", quick_sort(test7.copy(), 0, len(test7)))
    
    # Test case 8: Testing descending order
    test8 = [1, 5, 2, 8, 3]
    print("\nTest 8 - Descending order:")
    print("Before:", test8)
    print("After:", quick_sort(test8.copy(), 0, len(test8) , descending))

if __name__ == "__main__":
    main()