import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        int[] sequence = {47, 85, 10, 45, 16, 34, 67, 80, 34, 4, 0, 99};
        System.out.println("Original: " + Arrays.toString(sequence));
        bucketsort(sequence, 10);
        System.out.println("Sortiert: " + Arrays.toString(sequence));

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
