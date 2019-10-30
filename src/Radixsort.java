import java.util.ArrayList;
import java.util.List;

class Radixsort {

    private static final int KEYS_INT = 10;


    // - - - - - - int LSD - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * LSD variant for Radixsort
     * @param seq the array to be sorted
     */
    static void LSD(int[] seq) {

        // Determine maximum element length
        int len = getMaxLength(seq);

        //Init Containers
        List<Integer>[] containers = new List[KEYS_INT];
        for (int i = 0; i < KEYS_INT; i++) {
            containers[i] = new ArrayList<>();
        }

        // Sorting
        for (int exp = 0; exp < len; exp++) {

            // Partitioning
            clearContainers(containers);
            for (int e : seq) {
                containers[hashLSD(e, exp)].add(e);
            }

            // Collecting
            int i = 0;
            for (List<Integer> container : containers) {
                for (Integer e : container) {
                    seq[i++] = e;
                }
            }
        }
    }

    /**
     * Hash function for LSD Radixsort
     * @param num the number for which the hash value is to be generated
     * @param exp the exponent for the hash function (equal to current iteration, counting from 0)
     * @return hash value for num
     */
    private static int hashLSD(int num, int exp) {
        return (num / (int) Math.pow(10, exp)) % 10;
    }


    // - - - - - - int MSD - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * MSD variant for Radixsort
     * @param seq the array to be sorted
     */
    static void MSD(int[] seq) {
        MSD_R(seq, 0, getMaxLength(seq));
    }

    /**
     * recursive implementation for MSD
     * @param seq the array (current sub-array) to be sorted
     * @param exp recursion depth
     * @param maxLen maximum element length in the current array
     */
    private static void MSD_R(int[] seq, int exp, int maxLen) {

        if (seq.length >= 2 && !allElementsAreEqual(seq)) {  // condition for recursion

            // Init Containers
            List<Integer>[] containers = new List[KEYS_INT];
            for (int i = 0; i < KEYS_INT; i++) {
                containers[i] = new ArrayList<>();
            }

            // Partitioning
            for (int e : seq) {
                containers[hashMSD(e, exp, maxLen)].add(e);
            }

            // Sort each bucket recursively
            for (List<Integer> container : containers) {

                int[] containerContent = container.stream().mapToInt(Integer::intValue).toArray();
                MSD_R(containerContent, exp + 1, maxLen); // recursive call

                container.clear(); // clear container and refill with sorted elements
                for (int i : containerContent) {
                    container.add(i);
                }
            }

            // Collecting
            int i = 0;
            for (List<Integer> container : containers) {
                for (Integer e : container) {
                    seq[i++] = e;
                }
            }
        }
    }

    /*
    exp = Math.max(maxLen - exp - 1, 0);
    "Umdrehen" des Exponenten. exp ist einfach die mitlaufende Variable für die Rekursionstiefe. Da aber beim MSD begonnen
    wird, muss der Exponent für die Hashfunktion umgekehrt werden. Aus exp = 0 wird exp = maxLen ( -1 weil der Exponent
    bei 0 anfängt zu zählen, die Länge aber bei 1

    if (len < maxLen && exp >= len) {
    Falls ein Element weniger Stellen als das größte Element hat, muss für dieses Element in den ersten Durchläufen 0
    zurückgegeben werden (z.B. Zahl 47 und das größte Element ist 3-stellig; 47 muss im ersten Umlauf in den 0-Container,
    nicht in den 4-Container

     */

    /**
     * Hash function for MSD variant
     * @param num the number for which the hash value is to be generated
     * @param exp the exponent for the hash function (equal to current recusrion depth, counting from 0)
     * @param maxLen maximum element length of the current array
     * @return hash value for num
     */
    private static int hashMSD(int num, int exp, int maxLen) {
        int len = getLength(num);
        exp = Math.max(maxLen - exp - 1, 0);
        if (len < maxLen && exp >= len) {
            return 0;
        }
        return (num / (int) Math.pow(10, exp)) % 10;
    }


    //  - - - - - - Helper - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Completely clears a given array of lists
     * @param containers: The array of lists to be cleared
     */
    private static void clearContainers(List[] containers) {
        for (List container : containers) {
            container.clear();
        }
    }

    /**
     * Checks if all elements of a given int array are equal
     * @param seq the array to check
     * @return true if all elements are equal, false otherwise or when there are no elements in the array
     */
    private static boolean allElementsAreEqual(int[] seq) {
        if (seq.length == 0) {
            return false;
        }
        int tmp = seq[0];
        for (int i : seq) {
            if (i != tmp) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks the maximum element length in a given array ("length" as in "number of digits")
     * @param seq the array to check
     * @return maximum element length
     */
    private static int getMaxLength(int[] seq) {
        int res = 0;
        for (int i : seq) {
            res = Math.max(res, getLength(i));
        }
        return res;
    }

    /**
     * Get the number of digits for a given integer
     * @param x the integer to check
     * @return number of digits for x
     */
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
