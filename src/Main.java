import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

//        int[] sequence = {47, 85, 10, 45, 16, 34, 67, 80, 34, 4, 0, 99};
//        System.out.println("Original: " + Arrays.toString(sequence));
//        bucketsort(sequence, 10);
//        System.out.println("Sortiert: " + Arrays.toString(sequence));


        int[] sequence = {124, 523, 483, 128, 923, 584};
        System.out.println("Original: " + Arrays.toString(sequence));
        radixsort(sequence, 3, 10);
        System.out.println("Sortiert: " + Arrays.toString(sequence));

    }

    private static void radixsort(int[] array, int len, int keys) {

        //Init Buckets
        List<Integer>[] buckets = new List[keys];
        for (int i = 0; i < keys; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Sorting
        for (int exp = 0; exp < len; exp++) {

            clearBuckets(buckets);

            // Partitioning
            for (int e : array) {
                buckets[hash(e, exp)].add(e);
            }

            // Collecting
            int i = 0;
            for (List<Integer> bucket : buckets) {
                for (Integer e : bucket) {
                    array[i++] = e;
                }
            }
            System.out.println("Array nach Exp " + exp + ": " + Arrays.toString(array));
        }


    }

    private static int hash(int num, int exp) {
        return (num / (int)Math.pow(10, exp)) % 10;
    }

    private static void clearBuckets(List<Integer>[] buckets) {
        for (List<Integer> bucket : buckets) {
            bucket.clear();
        }
    }

    private static void bucketsort(int[] array, int k) {

        // Init Buckets
        List<Integer>[] buckets = new List[k];
        for (int i = 0; i < k; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Scatter
        for (int e : array) {
            buckets[hash(e)].add(e);
        }

        // Sort
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
        }

        // Gather
        int i = 0;
        for (List<Integer> bucket : buckets) {
            for (Integer num : bucket) {
                array[i++] = num;
            }
        }
    }

    private static int hash(int num) {
        return num / 10;
    }

}
