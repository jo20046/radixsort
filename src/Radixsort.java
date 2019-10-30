import java.util.ArrayList;
import java.util.List;

public class Radixsort {

    private static final int KEYS_INT = 10;
    private static final int KEYS_STR = 40;


    // - - - - - - int LSD - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

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

    private static int hashLSD(int num, int exp) {
        return (num / (int) Math.pow(10, exp)) % 10;
    }


    // - - - - - - int MSD - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    static void MSD(int[] seq) {
        intMSD_R(seq, 0, getMaxLength(seq));
    }

    private static void intMSD_R(int[] seq, int exp, int maxLen) {

        if (seq.length >= 2) {  // condition for recursion

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
                intMSD_R(containerContent, exp + 1, maxLen); // recursive call

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
    private static int hashMSD(int num, int exp, int maxLen) {
        int len = getLength(num);
        exp = Math.max(maxLen - exp - 1, 0);
        if (len < maxLen && exp >= len) {
            return 0;
        }
        return (num / (int) Math.pow(10, exp)) % 10;
    }


    //  - - - - - - Helper - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private static void clearContainers(List[] containers) {
        for (List container : containers) {
            container.clear();
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
