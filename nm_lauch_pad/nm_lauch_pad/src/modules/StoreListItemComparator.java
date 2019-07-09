package modules;

import java.util.Comparator;

import org.apache.log4j.Logger;

/**
 * A Comparison implementation providing custom logic as to how two
 * StoreListItem object values are compared to one another based on store
 * number.
 * 
 * @author rterrell
 *
 */
public class StoreListItemComparator implements Comparator<StoreListItem> {

    private static final Logger logger = Logger
            .getLogger(StoreListItemComparator.class);

    /**
     * Create a default StoreListItemComparator
     */
    public StoreListItemComparator() {

    }

    /**
     * Compares the storeNo property of two StoreListItem arguments for
     * ordering.
     * 
     * @param o1
     *            The first {@link StoreListItem} to be compared.
     * @param o2
     *            The second {@link StoreListItem} to be compared.
     * @return Returns a negative integer, zero, or a positive integer when the
     *         first argument is less than, equal to, or greater than the
     *         second, respectively.
     */
    public int compare(StoreListItem o1, StoreListItem o2) {
        if (o1 == null && o2 != null) {
            return -1;
        }
        if (o1 != null && o2 == null) {
            return 1;
        }
        if (o1 == null && o2 == null) {
            return 0;
        }
        try {
            int num1 = Integer.parseInt(o1.getStoreNo());
            int num2 = Integer.parseInt(o2.getStoreNo());
            if (num1 < num2) {
                return -1;
            }
            if (num1 == num2) {
                return 0;
            }
            if (num1 > num2) {
                return 1;
            }
        } catch (NumberFormatException e) {
            logger.warn(
                    "Nuermic String values are incompatigle to compare to each other ["
                            + o1.toString() + "] and [" + o2.toString()
                            + "].  Compare results will default to equals", e);
            return 0;
        }
        return 0;
    }

}
