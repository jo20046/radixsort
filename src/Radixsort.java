import java.util.ArrayList;
import java.util.List;

public class Radixsort {

    private static final int KEYS_INT = 10;
    private static final int KEYS_STR = 40;

    static void intLSD(int[] seq) {

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
                buckets[intHash(e, exp)].add(e);
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

    static void strLSD(String[] seq) {

        // Determine maximum element length
        int len = getMaxLength(seq);

        //Init Buckets
        List<String>[] buckets = new List[KEYS_STR];
        for (int i = 0; i < KEYS_STR; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Sorting
        for (int exp = 0; exp < len; exp++) {

            // Partitioning
            clearBuckets(buckets);
            for (String s : seq) {
//                buckets[intHash(e, exp)].add(e);
                buckets[strHash(s, exp)].add(s);
            }

            // Collecting
            int i = 0;
            for (List<String> bucket : buckets) {
                for (String str : bucket) {
                    seq[i++] = str;
                }
            }
        }
    }

    private static int intHash(int num, int exp) {
        return (num / (int) Math.pow(10, exp)) % 10;
    }

    private static int strHash(String str, int exp) {
        int pos = str.length() - 1 - exp;
        if (pos < 0) {
            pos = 0;
        }
        char c = str.charAt(pos);
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 10;
        } else if (c >= 'a' && c <= 'z') {
            return c - 'a' + 10;
        } else if (c == 'ä' || c == 'Ä') {
            return 36;
        } else if (c == 'ö' || c == 'Ö') {
            return 37;
        } else if (c == 'ü' || c == 'Ü') {
            return 38;
        } else if (c == 'ß') {
            return 39;
        } else {
            return 0;
        }
    }

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
