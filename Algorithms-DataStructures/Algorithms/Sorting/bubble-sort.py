from typing import Any, Callable, List

def ascending(a, b) -> bool:
    return a > b

def descending(a , b) -> bool:
    return a < b


# Bubble Sort Algorithm:
# Bubble sort repeatedly iterates through the list, comparing adjacent elements and swapping
# them if they are in the wrong order. 
# After each pass, one less element needs to be checked since the last elements gradually become sorted.
# The algorithm includes an optimization where it can stop early if no swaps were needed during a pass,
# indicating the list is already sorted.

def bubble_sort(lst : List[Any], comp_func : Callable[[Any, Any], bool] = ascending) -> List[Any]:
    """
    Sorts a list using the bubble sort algorithm.
    
    Args:
        lst (List[Any]): The input list to be sorted
        comp_func (Callable[[Any, Any], bool]): Comparison function that defines the sorting order.
            Default is ascending order (a > b).
    
    Returns:
        List[Any]: The sorted list
        
    Time Complexity:
        - Best Case: O(n) when list is already sorted
        - Average Case: O(n^2)
        - Worst Case: O(n^2)

    Space Complexity:
        - O(1) as it sorts in-place
    """
    for i in range(len(lst)): #Controls number of passes through the list. Each pass "bubbles up" an element to it's sorted position
        swapped : bool = False #Flag for ending early if there are no swaps as this implies that the list is sorted
        for j in range(len(lst) - i - 1 ): #Inner loop for comparing adjacent elements. Each pass reduces the comparisons needed as the list is more sorted
            if comp_func(lst[j], lst[j+1]):
                lst[j], lst[j+1] = lst[j+1], lst[j] #Swap elements 
                swapped = True
        if not swapped:
            break
    return lst

def main():
    # Test case 1: Regular integers
    test1 = [43, 123, 73, 123, 74, 23, 62, 73, 13, 8, 1, 45]
    print("Test 1 - Regular integers:")
    print("Before:", test1)
    print("After:", bubble_sort(test1.copy()))
    
    # Test case 2: Already sorted list
    test2 = [1, 2, 3, 4, 5]
    print("\nTest 2 - Already sorted:")
    print("Before:", test2)
    print("After:", bubble_sort(test2.copy()))
    
    # Test case 3: Reverse sorted list
    test3 = [5, 4, 3, 2, 1]
    print("\nTest 3 - Reverse sorted:")
    print("Before:", test3)
    print("After:", bubble_sort(test3.copy()))
    
    # Test case 4: Empty list
    test4 = []
    print("\nTest 4 - Empty list:")
    print("Before:", test4)
    print("After:", bubble_sort(test4.copy()))
    
    # Test case 5: List with one element
    test5 = [1]
    print("\nTest 5 - Single element:")
    print("Before:", test5)
    print("After:", bubble_sort(test5.copy()))
    
    # Test case 6: List with duplicate elements
    test6 = [1, 1, 1, 1, 1]
    print("\nTest 6 - All duplicates:")
    print("Before:", test6)
    print("After:", bubble_sort(test6.copy()))
    
    # Test case 7: List with negative numbers
    test7 = [-5, 3, -10, 0, 8, -2]
    print("\nTest 7 - Negative numbers:")
    print("Before:", test7)
    print("After:", bubble_sort(test7.copy()))
    
    # Test case 8: Testing descending order
    test8 = [1, 5, 2, 8, 3]
    print("\nTest 8 - Descending order:")
    print("Before:", test8)
    print("After:", bubble_sort(test8.copy(), descending))

if __name__ == "__main__":
    main()