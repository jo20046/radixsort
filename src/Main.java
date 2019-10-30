import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

//        int[] sequence = {47, 85, 10, 45, 16, 34, 67, 80, 34, 4, 0, 99};
//        System.out.println("Original: " + Arrays.toString(sequence));
//        bucketsort(sequence, 10);
//        System.out.println("Sortiert: " + Arrays.toString(sequence));


//        int[] sequence = {124, 523, 483, 128, 923, 584};
//        System.out.println("Original: " + Arrays.toString(sequence));
//        radixsortLSD(sequence, 3, 10);
//        System.out.println("Sortiert: " + Arrays.toString(sequence));

        int[] sequence1 = {124, 523, 483, 128, 923, 584, 123};
        int[] sequence2 = {47, 85, 10, 45, 456, 16, 16, 34, 67, 80, 0, 4, 99};
        int[] sequence3 = {42, 42, 190};

        Radixsort.LSD(sequence1);
        System.out.println("Arrays.toString(sequence1) = " + Arrays.toString(sequence1));
        Radixsort.LSD(sequence2);
        System.out.println("Arrays.toString(sequence2) = " + Arrays.toString(sequence2));

        Radixsort.MSD(sequence1);
        System.out.println("Arrays.toString(sequence1) = " + Arrays.toString(sequence1));
        Radixsort.MSD(sequence2);
        System.out.println("Arrays.toString(sequence2) = " + Arrays.toString(sequence2));
        Radixsort.MSD(sequence3);
        System.out.println("Arrays.toString(sequence3) = " + Arrays.toString(sequence3));

//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("german.dic"), StandardCharsets.ISO_8859_1));
//        List<String> wordList = new ArrayList<>();
//        for (String s = br.readLine(); s != null; s = br.readLine()) {
//            wordList.add(s);
//        }
//
//        String[] sequence3 = new String[10];
//        int len = wordList.size();
//        for (int i = 0; i < 10; i++) {
//            sequence3[i] = wordList.get(ThreadLocalRandom.current().nextInt(0, len));
//        }
//
//        System.out.println("Arrays.toString(sequence2) = " + Arrays.toString(sequence3));
//        Radixsort.strLSD(sequence3);
//        System.out.println("Arrays.toString(sequence2) = " + Arrays.toString(sequence3));


    }

    private static void radixsortLSD(int[] array, int len, int keys) {

        //Init Buckets
        List<Integer>[] buckets = new List[keys];
        for (int i = 0; i < keys; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Sorting
        for (int exp = 0; exp < len; exp++) {

            // Partitioning
            clearBuckets(buckets);
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
        }
    }

    private static void radixsortMSD(int[] array, int pos, int keys) {

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
