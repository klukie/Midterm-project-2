import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Midterm {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		Random rand = new Random();
		int[] A = new int[100];
		int sumToBeFound = -201;
		int choice = 0;

		for (int i = 0; i < A.length; i++) {
			A[i] = rand.nextInt(200) - 100;
		}

		System.out.println("The array to be search is:");
		print(A);

		System.out.println("\n\nPlease enter the sum to be found. The range of number is [-100, 100].\n"
				+ "Therefore, you request should be no less that -100 and no greater than 100.");

		while (sumToBeFound < -200 || sumToBeFound > 200) {
			System.out.println("\nPlease as number in the range of [-200, 200].");
			sumToBeFound = keyboard.nextInt();
		}

		System.out.println("Would you like to search a sorted or unsorted array?");

		while (choice != 1 && choice != 2) {
			System.out.println("\nPlease enter [1] for sorted or, [2] for unsorted.");
			choice = keyboard.nextInt();
		}

		if (choice == 1) {
			A = countingSort(A, sumToBeFound, 200);
			print(A);

			System.out.println("\nUsing brute force.");
			bruteForce(A, sumToBeFound);

			System.out.println("\n\nUsing divide and conqure.");
			iterator(A, sumToBeFound);

			System.out.println("\n\nUsing Binary");
			binary(A, sumToBeFound);

		} else {

			System.out.println("\n\nUsing brute force.");
			bruteForce(A, sumToBeFound);

			System.out.println("\n\nUsing Hash table.");
			hash(A, sumToBeFound);

		}

	}

	/**
	 * This method uses a hash table to search for a pair that equals the explict sum.		Time complexity: O(n)
	 * @param a  The array to be searched
	 * @param sumToBeFound  The value to tp be found for a pair of indices
	 */
	private static void hash(int[] a, int sumToBeFound) {
		Map<Integer, Integer> myHashMap = new HashMap<>();

		for (int i = 0; i < a.length; i++) {
			if (myHashMap.containsKey(sumToBeFound - a[i])) {
				System.out.printf("The sum %d was found at indices %d and %d.", sumToBeFound, myHashMap.get(sumToBeFound - a[i]), i);
				return;
			}
			myHashMap.put(a[i], i);
		}
			System.out.println("The sum was not found.");
	}

	/**
	 * This method will search for a matching pair to the user input sum by
	 * looking sum - a[i] in a binary search 
	 * @param a  the array to be searched
	 * @param sumToBeFound  the user entered sum 
	 */
	private static void binary(int[] a, int sumToBeFound) { 
		int foundIndex = -1;
		for(int i = 0; i < a.length; i++) {
			foundIndex = binarySearch(a, i, sumToBeFound);
			if(foundIndex > 0) {
				System.out.printf("The sum %d was found at indices %d and %d.", sumToBeFound, i, foundIndex);
				return;
			}	
		}
		System.out.printf("The sum %d was not in the array.", sumToBeFound);
	}

	/**
	 * 
	 * @param a  the array to be searched  
	 * @param  loopIndex the index of the frst number to be used
	 * @param  sumToBeFound The user definded sum 
	 * @return the index that the number was found in or -1 if not found.
	 */
	private static int binarySearch(int[] a, int loopIndex, int sumToBeFound) {
		int low = 0;
		int high = a.length - 1;
		int mid;
		
		while (low <= high) {
			mid = (low + high) / 2;
			if(a[mid] > sumToBeFound - a[loopIndex]) {
				high = mid - 1;
			} else if (a[mid] < sumToBeFound - a[loopIndex]) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}
		

	/**
	 * Assuming array is already sorted. use this method that will compare a
	 * leftmost to the rightmost element. If the sum is > i + j i++ If the sum is <
	 * i + j j--
	 * 
	 * @param a   The array to be searched
	 * @param sum the sum to be searched for
	 */
	private static void iterator(int[] a, int sum) { // O(n log n)
		int i = 0;
		int j = a.length - 1;

		while (i < j && a[i] + a[j] != sum) {
			if (a[i] + a[j] > sum) {
				j--;
			} else {
				i++;
			}
		}
		if (a[i] + a[j] == sum && i != j) {
			System.out.printf("The sum %d was found at indices %d and %d.", sum, i, j);
		} else {
			System.out.printf("The sum %d was not in the array.", sum);
		}

	}

	/**
	 * Sort an array using quickSort method; Time complexity O(n + k)
	 * 
	 * @param a            A The array to be sorted
	 * @param sumToBeFound The highest number in the range
	 * @param range        the total range of the array a[]
	 * @return c The address of the sorted array
	 */
	private static int[] countingSort(int[] a, int sumToBeFound, int range) { // O(n)
		int b[] = new int[range + 1];
		int c[] = new int[a.length];

		c = Arrays.copyOf(a, a.length);

		for (int i = 0; i < c.length; i++) {
			c[i] += 100;
		}

		for (int i = 0; i < a.length; i++) { // count number of time each values appears in the array
			++b[c[i]];
		}

		for (int i = 1; i < b.length; i++) { // sum up the prefix values for the location indices for sorted array.
			b[i] += b[i - 1];
		}

		for (int i = (a.length - 1); i >= 0; i--) { // populate the array in a sorted order
			c[--b[a[i] + 100]] = a[i];
		}

		return c;
	}

	/**
	 * The first method to find two number is an array the equal a user specficed
	 * sum.
	 * 
	 * @param a The array to be searched
	 * @param s User entered sum to be found in array
	 * 
	 */

	private static void bruteForce(int[] a, int s) { // O(n^2)
		boolean flag = false;
		int iCount = 0;
		int jCount;

		while (flag != true && iCount < a.length) {
			jCount = iCount + 1;
			while (flag != true && (jCount) < a.length) {
				if (a[iCount] + a[jCount] == s) {
					System.out.printf("The sum %d was found at indices %d and %d.", s, iCount, jCount);
					flag = true;
				}
				jCount++;
			}
			iCount++;
		}
		if (flag == false) {
			System.out.printf("The sum %d was not in the array.", s);
		}

	}

	/**
	 * Will print an array of size n
	 * @param a The array to be printed
	 */
	private static void print(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print("[" + a[i] + "]");
		}

	}

}
