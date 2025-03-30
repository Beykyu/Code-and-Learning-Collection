from typing import Any, Callable, List

def ascending(a, b) -> bool:
    return a < b

def descending(a , b) -> bool:
    return a > b

# Merge Sort:
# 1. The algorithm follows a divide-and-conquer strategy
# 2. Recursively divides the input array into two halves until single elements remain
# 3. Merges back the divided arrays while sorting them
# 4. During merging, compares elements from both arrays and places them in correct order
# 5. Continues merging until the entire array is sorted

def merge_sort(lst : List[Any], start : int, end : int, comp_func : Callable[[Any,Any], bool] = ascending) -> List[Any]:
    """
    Sort a list using the merge sort algorithm.
    
    Args:
        lst (List[Any]): The input list to be sorted
        start (int): Starting index of the sublist
        end (int): Ending index of the sublist
        comp_func (Callable[[Any, Any], bool], optional): Comparison function that defines the sort order.
            Defaults to ascending order.
    
    Returns:
        List[Any]: The sorted list
        
    Time Complexity: 
    - All cases: O(n log n)

    Space Complexity: 
    O(n) due to auxiliary space needed for merging
    """
    if start < end - 1:
        #Continously divides the array into 2 subsets until there's only 1 element and then merges all subsets into order 1 by 1
        mid : int = (start + end) // 2 
        merge_sort(lst, start, mid, comp_func)
        merge_sort(lst, mid, end, comp_func)
        merge(lst, start, mid, end, comp_func)
    return lst

def merge(lst: List[Any], start: int, mid: int, end: int, comp_func : Callable[[Any,Any], bool] = ascending) -> None:
    """
    Merge two sorted sublists into a single sorted list.
    
    Args:
        lst (List[Any]): The input list containing the sublists to merge
        start (int): Starting index of the first sublist
        mid (int): Ending index of the first sublist and starting index of the second sublist
        end (int): Ending index of the second sublist
        comp_func (Callable[[Any, Any], bool], optional): Comparison function that defines the sort order.
            Defaults to ascending order.
    
    Returns:
        None: The list is modified in-place
    """
    left = lst[start : mid]
    right = lst[mid: end ]

    i = j = 0 #Initialises indexes for current elements to compare from both subsets

    #Sorts the elements in both left and right subarray in the correct order using comparison function
    for k in range(start, end):
        #Selects next element from left if it's not empty and (right is exhausted or comparison prefers left)
        if i < len(left) and (j >= len(right) or comp_func(left[i], right[j])):
            lst[k] = left[i]
            i += 1
        else:
            lst[k] = right[j]
            j += 1


def main():
    # Test case 1: Regular integers
    test1 = [43, 123, 73, 123, 74, 23, 62, 73, 13, 8, 1, 45]
    print("Test 1 - Regular integers:")
    print("Before:", test1)
    print("After:", merge_sort(test1.copy(), 0, len(test1)))
    
    # Test case 2: Already sorted list
    test2 = [1, 2, 3, 4, 5]
    print("\nTest 2 - Already sorted:")
    print("Before:", test2)
    print("After:", merge_sort(test2.copy(), 0, len(test2)))
    
    # Test case 3: Reverse sorted list
    test3 = [5, 4, 3, 2, 1]
    print("\nTest 3 - Reverse sorted:")
    print("Before:", test3)
    print("After:", merge_sort(test3.copy(), 0, len(test3)))
    
    # Test case 4: Empty list
    test4 = []
    print("\nTest 4 - Empty list:")
    print("Before:", test4)
    print("After:", merge_sort(test4.copy(), 0, len(test4)))
    
    # Test case 5: List with one element
    test5 = [1]
    print("\nTest 5 - Single element:")
    print("Before:", test5)
    print("After:", merge_sort(test5.copy(), 0, len(test5)))
    
    # Test case 6: List with duplicate elements
    test6 = [1, 1, 1, 1, 1]
    print("\nTest 6 - All duplicates:")
    print("Before:", test6)
    print("After:", merge_sort(test6.copy(), 0, len(test6)))
    
    # Test case 7: List with negative numbers
    test7 = [-5, 3, -10, 0, 8, -2]
    print("\nTest 7 - Negative numbers:")
    print("Before:", test7)
    print("After:", merge_sort(test7.copy(), 0, len(test7)))
    
    # Test case 8: Testing descending order
    test8 = [1, 5, 2, 8, 3]
    print("\nTest 8 - Descending order:")
    print("Before:", test8)
    print("After:", merge_sort(test8.copy(), 0, len(test8) , descending))

if __name__ == "__main__":
    main()