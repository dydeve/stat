package statistics.monitor.util;

import java.util.UUID;

/**
 * Created by dy on 2018/4/9.
 */
public class UniqueId {

    public static String generate() {
        return objectId();
    }

    private static String objectId() {
        return new ObjectId().toHexString();
    }

    private static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
