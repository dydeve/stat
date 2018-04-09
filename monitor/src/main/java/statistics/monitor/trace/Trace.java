package statistics.monitor.trace;

/**
 * Created by dy on 2018/4/9.
 */
public class Trace {

    private final String tid;

    private final int sequence;

    private final String name;

    private long startTime;
    private long lastTime;

    private String error;

    Trace(String tid, String name) {
        this.tid = tid;
        this.sequence = 0;
        this.name = name;
        this.startTime = System.currentTimeMillis();
    }

    private Trace(String tid, String name, int sequence) {
        this.tid = tid;
        this.sequence = sequence;
        this.name = name;
        this.startTime = System.currentTimeMillis();
    }

    public Trace next(String name) {
        return new Trace(this.tid, name, this.sequence + 1);
    }
}
