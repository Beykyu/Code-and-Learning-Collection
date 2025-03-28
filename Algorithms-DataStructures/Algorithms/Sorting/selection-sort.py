from typing import List, Any, Callable

def ascending(a : Any, b : Any) -> bool:
    return a < b

def descending(a : Any, b: Any) -> bool:
    return a > b

# Selection Sort Algorithm:
# Selection sort works by dividing the input list into two parts:
# 1. A sorted portion at the beginning of the list
# 2. An unsorted portion containing the rest of the list
# In each iteration, it finds the smallest (or largest) element from the unsorted portion
# and adds it to the end of the sorted portion by swapping it with the first element of
# the unsorted portion. This process continues until the entire list is sorted.

def selection_sort(lst : List[Any], func_comp : Callable[[Any, Any], bool] = ascending) -> List[Any]:
    """
    Sorts a list using the selection sort algorithm.
    
    Args:
        lst (List[Any]): The input list to be sorted
        func_comp (Callable[[Any, Any], bool]): Comparison function that defines the sorting order.
            Default is ascending order (a < b).
    
    Returns:
        List[Any]: The sorted list
        
    Time Complexity:
        - Best Case: O(n^2)
        - Average Case: O(n^2)
        - Worst Case: O(n^2)
    
    Space Complexity:
        - O(1) as it sorts in-place
    """
    for i in range(len(lst)): #Marks the boundary of the sorted list
        swap_index : int = i #index for the current element considered as the next element in the sorted list
        for j in range(swap_index, len(lst)): #Searches through the unsorted list for next "sorted" element
            if func_comp(lst[j], lst[swap_index]): #Sets the current element as the one to swap if matches the comparison better
                swap_index = j
        lst[i], lst[swap_index] = lst[swap_index], lst[i]
    return lst


def main():
    # Test case 1: Regular integers
    test1 = [43, 123, 73, 123, 74, 23, 62, 73, 13, 8, 1, 45]
    print("Test 1 - Regular integers:")
    print("Before:", test1)
    print("After:", selection_sort(test1.copy()))
    
    # Test case 2: Already sorted list
    test2 = [1, 2, 3, 4, 5]
    print("\nTest 2 - Already sorted:")
    print("Before:", test2)
    print("After:", selection_sort(test2.copy()))
    
    # Test case 3: Reverse sorted list
    test3 = [5, 4, 3, 2, 1]
    print("\nTest 3 - Reverse sorted:")
    print("Before:", test3)
    print("After:", selection_sort(test3.copy()))
    
    # Test case 4: Empty list
    test4 = []
    print("\nTest 4 - Empty list:")
    print("Before:", test4)
    print("After:", selection_sort(test4.copy()))
    
    # Test case 5: List with one element
    test5 = [1]
    print("\nTest 5 - Single element:")
    print("Before:", test5)
    print("After:", selection_sort(test5.copy()))
    
    # Test case 6: List with duplicate elements
    test6 = [1, 1, 1, 1, 1]
    print("\nTest 6 - All duplicates:")
    print("Before:", test6)
    print("After:", selection_sort(test6.copy()))
    
    # Test case 7: List with negative numbers
    test7 = [-5, 3, -10, 0, 8, -2]
    print("\nTest 7 - Negative numbers:")
    print("Before:", test7)
    print("After:", selection_sort(test7.copy()))
    
    # Test case 8: Testing descending order
    test8 = [1, 5, 2, 8, 3]
    print("\nTest 8 - Descending order:")
    print("Before:", test8)
    print("After:", selection_sort(test8.copy(), descending))

if __name__ == "__main__":
    main()