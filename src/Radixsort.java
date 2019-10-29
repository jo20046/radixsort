import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Radixsort {

    private static final int KEYS_INT = 10;
    private static final int KEYS_STR = 40;


    // - - - - - - int LSD - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    static void LSD(int[] seq) {

        // Determine maximum element length
        int len = getMaxLength(seq);

        //Init Buckets
        List<Integer>[] buckets = new List[KEYS_INT];
        for (int i = 0; i < KEYS_INT; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Sorting
        for (int exp = 0; exp < len; exp++) {

            // Partitioning
            clearBuckets(buckets);
            for (int e : seq) {
                buckets[hashLSD(e, exp)].add(e);
            }

            // Collecting
            int i = 0;
            for (List<Integer> bucket : buckets) {
                for (Integer e : bucket) {
                    seq[i++] = e;
                }
            }
        }
    }

    private static int hashLSD(int num, int exp) {
        return (num / (int) Math.pow(10, exp)) % 10;
    }


    // - - - - - - int MSD - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    static void MSD(int[] seq) {
        intMSD_R(seq, 0, getMaxLength(seq));
    }

    private static void intMSD_R(int[] seq, int exp, int maxLen) {

        if (seq.length >= 2) {  // condition for recursion
            // Init Buckets
            List<Integer>[] buckets = new List[KEYS_INT];
            for (int i = 0; i < KEYS_INT; i++) {
                buckets[i] = new ArrayList<>();
            }

            // Partitioning
            for (int e : seq) {
                buckets[hashMSD(e, exp, maxLen)].add(e);
            }

            // Sort each bucket recursively
            for (List<Integer> bucket : buckets) {

                int[] bucketContent = bucket.stream().mapToInt(Integer::intValue).toArray();
                intMSD_R(bucketContent, exp + 1, maxLen); // recursive call

                bucket.clear(); // clear bucket and refill with sorted elements
                for (int i : bucketContent) {
                    bucket.add(i);
                }
            }

            // Collecting
            int i = 0;
            for (List<Integer> bucket : buckets) {
                for (Integer e : bucket) {
                    seq[i++] = e;
                }
            }
        }
    }

    private static int hashMSD(int num, int exp, int maxLen) {
        int len = getLength(num);
        exp = Math.max(maxLen - exp - 1, 0);
        if (len < maxLen && exp >= len) {
            return 0;
        }
        return (num / (int) Math.pow(10, exp)) % 10;
    }


    //  - - - - - - Helper - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private static void clearBuckets(List[] buckets) {
        for (List bucket : buckets) {
            bucket.clear();
        }
    }

    private static int getMaxLength(String[] seq) {
        int res = 0;
        for (String s : seq) {
            res = Math.max(res, s.length());
        }
        return res;
    }

    private static int getMaxLength(int[] seq) {
        int res = 0;
        for (int i : seq) {
            res = Math.max(res, getLength(i));
        }
        return res;
    }

    private static int getLength(int x) {
        int res = 1;
        int div = 10;
        while (x / div != 0) {
            res++;
            div *= 10;
        }
        return res;
    }
}
