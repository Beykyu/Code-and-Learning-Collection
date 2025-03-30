from typing import List, Any

def counting_sort(unsorted: List[int], highest: int) -> List[int]:
    """
    Sorts an array of integers using the Counting Sort algorithm.
    
    Args:
        unsorted (List[int]): List of positive integers to be sorted
        highest (int): The maximum value in the input array
        
    Returns:
        List[int]: A new sorted list
        
    Time Complexity: O(n + k) where k is the range of input
    Space Complexity: O(n + k)
    Stable: Yes
    In-place: No
    """
    count_list: List[int] = [0] * (highest + 1) # list for storing counts of each number
    for i in unsorted:
        count_list[i] += 1 # increments the total times a value shows up using the index
    for j in range(1,highest + 1):
        count_list[j] += count_list[j - 1] # builds frequency array, updates element to be sum of itself and previous element
    sorted_list: List[int] = [0] * len(unsorted) # Holds the sorted elements
    for i in unsorted[::-1]: #iterates through the list in reverse order to ensure stable sorting for duplicate values
        sorted_list[count_list[i] - 1] = i # Places the element 'i' at its correct position in the sorted list
        count_list[i] -= 1 # Decrements the count for the current element to handle duplicates correctly
    return sorted_list

def main():
    # Test cases
    test_array = [5, 2, 4, 1, 3]
    print("Initial array:", test_array)
    print("Sorted array:", counting_sort(test_array, max(test_array)))
    
    # Test with duplicates
    test_array = [4, 2, 4, 1, 3, 2]
    print("\nArray with duplicates:", test_array)
    print("Sorted array:", counting_sort(test_array, max(test_array)))
    
    # Test with larger numbers
    test_array = [64, 34, 25, 12, 22, 11, 90]
    print("\nLarger numbers:", test_array)
    print("Sorted array:", counting_sort(test_array, max(test_array)))

    # Test with 0s
    test_array = [64, 34, 25, 12, 22, 11, 90, 1, 0, 5, 0]
    print("\nLarger numbers:", test_array)
    print("Sorted array:", counting_sort(test_array, max(test_array)))
if __name__ == "__main__":
    main()