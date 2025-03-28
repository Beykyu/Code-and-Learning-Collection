from typing import Any, Callable, List

def ascending (a : Any, b : Any) -> bool:
    return a < b

def descending(a : Any, b: Any) -> bool:
    return a > b

# Insertion Sort:
# 1. The algorithm maintains a sorted portion on the left and unsorted portion on the right
# 2. It iterates through the unsorted portion, taking one element at a time
# 3. For each element, it compares it with elements in the sorted portion from right to left
# 4. Elements in the sorted portion that meet the condition in comparison are shifted one position to the right
# 5. Once the correct position is found, the current element is inserted 

def insertion_sort(lst: List[Any], func_comp : Callable[[Any, Any], bool] = ascending) -> List[Any]:
    """
    Sort a list using the insertion sort algorithm.
    
    Args:
        lst (List[Any]): The input list to be sorted
        func_comp (Callable[[Any, Any], bool], optional): Comparison function that defines the sort order.
            Defaults to ascending order.
    
    Returns:
        List[Any]: The sorted list
        
    Time Complexity: 
    - Average case: O(n^2) 
    - Worst case: O(n^2)
    - Best case : O(n)

    Space Complexity: 
    O(1) as it sorts in-place
    """
    for i in range(1, len(lst)):
        val = lst[i] # Value of unsorted list being checked
        j = i - 1 #starts at last element in sorted list and works its way left
        while j >= 0 and not func_comp(lst[j], val): # Shift element to the right if comparision is true
            lst[j + 1] = lst[j] 
            j -= 1 # Checks next element on left
        lst[j + 1] = val # Insert the current element into its correct position after shifting larger elements. j + 1 is used because j has been decremented during comparisons.
    return lst

def main():
    # Test case 1: Regular integers
    test1 = [43, 123, 73, 123, 74, 23, 62, 73, 13, 8, 1, 45]
    print("Test 1 - Regular integers:")
    print("Before:", test1)
    print("After:", insertion_sort(test1.copy()))
    
    # Test case 2: Already sorted list
    test2 = [1, 2, 3, 4, 5]
    print("\nTest 2 - Already sorted:")
    print("Before:", test2)
    print("After:", insertion_sort(test2.copy()))
    
    # Test case 3: Reverse sorted list
    test3 = [5, 4, 3, 2, 1]
    print("\nTest 3 - Reverse sorted:")
    print("Before:", test3)
    print("After:", insertion_sort(test3.copy()))
    
    # Test case 4: Empty list
    test4 = []
    print("\nTest 4 - Empty list:")
    print("Before:", test4)
    print("After:", insertion_sort(test4.copy()))
    
    # Test case 5: List with one element
    test5 = [1]
    print("\nTest 5 - Single element:")
    print("Before:", test5)
    print("After:", insertion_sort(test5.copy()))
    
    # Test case 6: List with duplicate elements
    test6 = [1, 1, 1, 1, 1]
    print("\nTest 6 - All duplicates:")
    print("Before:", test6)
    print("After:", insertion_sort(test6.copy()))
    
    # Test case 7: List with negative numbers
    test7 = [-5, 3, -10, 0, 8, -2]
    print("\nTest 7 - Negative numbers:")
    print("Before:", test7)
    print("After:", insertion_sort(test7.copy()))
    
    # Test case 8: Testing descending order
    test8 = [1, 5, 2, 8, 3]
    print("\nTest 8 - Descending order:")
    print("Before:", test8)
    print("After:", insertion_sort(test8.copy(), descending))

if __name__ == "__main__":
    main()