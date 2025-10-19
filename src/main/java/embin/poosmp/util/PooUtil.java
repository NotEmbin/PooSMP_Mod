package embin.poosmp.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PooUtil {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException("PooSMP: Cannot round to less than 0 places");
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static double roundTwo(double value) {
        return round(value, 2);
    }
}
