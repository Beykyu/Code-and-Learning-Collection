from typing import Any, Callable, List

# Radix Sort Implementation
# Radix sort is a non-comparative sorting algorithm that sorts integers by
# processing each digit position, starting from the least significant digit
# to the most significant digit. It uses counting sort as a subroutine to
# sort the elements according to their digits at different positions.

def radix_sort(lst: List[int]) -> List[int]:
    """
    Sort a list of integers using the Radix Sort algorithm.
    
    Args:
        lst (List[int]): The input list of non-negative integers to be sorted
        
    Returns:
        List[int]: The sorted list in ascending order
        
    Time Complexity: O(d * (n + k)) where d is the number of digits,
                    n is the number of elements, and k is the range of values (10 for decimal)
    Space Complexity: O(n + k)
    """
    max_val: int = max(lst)
    place: int = 1

    while max_val // place > 0:
        counting_sort(lst, place)
        place *= 10  # Checks the next digit
    return lst

def counting_sort(lst: List[int], place: int):
    """
    Helper function that performs counting sort on the digits at the specified place value.
    
    Args:
        lst (List[int]): The input list to be sorted
        place (int): The current digit position being sorted (1, 10, 100, etc.)
        
    Note:
        This is an in-place sorting algorithm that modifies the input list directly.
    """
    count: List[int] = [0] * 10
    output: List[int] = [0] * len(lst)

    for i in lst:
        digit: int = (i // place) % 10
        count[digit] += 1
    for i in range(1, 10):
        count[i] += count[i - 1]
    for num in lst[::-1]:
        digit: int = (num // place) % 10
        output[count[digit] - 1] = num
        count[digit] -= 1
    for i in range(len(lst)):
        lst[i] = output[i]

def main():
    # Test case 1: Regular integers
    test1 = [43, 123, 73, 123, 74, 23, 62, 73, 13, 8, 1, 45]
    print("Test 1 - Regular integers:")
    print("Before:", test1)
    print("After:", radix_sort(test1.copy()))

    # Test case 2: Regular integers
    test2 = [170, 45, 75, 90, 802, 24, 2, 66]
    print("Test 2 - Regular integers:")
    print("Before:", test2)
    print("After:", radix_sort(test2.copy()))

if __name__ == "__main__":
    main()