package statistics.monitor.trace;

import statistics.monitor.util.Json;

/**
 * Created by dy on 2018/4/9.
 */
public class TaskInfo {

    //tid 即 traceId  已在log中打出 by mdc
    //private final String tid;

    private final int sequence;

    private final String name;

    private final long startTime;
    private long lastTime;

    private String error;

    public TaskInfo(String name, int sequence) {
        this.sequence = sequence;
        this.name = name;
        this.startTime = System.currentTimeMillis();
    }

    public TaskInfo stop() {
        return setLastTime(System.currentTimeMillis() - startTime);
    }

    public int getSequence() {
        return sequence;
    }

    public String getName() {
        return name;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getLastTime() {
        return lastTime;
    }

    public TaskInfo setLastTime(long lastTime) {
        this.lastTime = lastTime;
        return this;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return Json.toJson(this);
    }

    /*public static void main(String[] args) {
TaskInfo a = new TaskInfo("afd", "af", 1);
        System.out.println(a);
    }*/
}
