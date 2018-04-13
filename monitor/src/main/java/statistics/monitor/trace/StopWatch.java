package statistics.monitor.trace;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import statistics.monitor.util.Json;

import java.util.Deque;
import java.util.LinkedList;

/**
 * not thread-safe.
 * 参考 spring-core StopWatch
 * Created by dy on 2018/4/9.
 */
public class StopWatch {

    private final String traceId;
    @JsonIgnore
    private final Deque<TaskInfo> taskInfos;
    //tomcat 属性
    private String host;
    private String client;
    private String uri;
    private String queryString;
    private int status;

    //mq,rpc等 属性



    public StopWatch(String traceId) {
        Preconditions.checkNotNull(traceId, "traceId can't be null");
        this.traceId = traceId;
        this.taskInfos = new LinkedList<>();
    }

    public TaskInfo start(String name) {
        TaskInfo taskInfo = new TaskInfo(name, taskInfos.size());
        taskInfos.push(taskInfo);
        return taskInfo;
    }

    public TaskInfo stop() {
        return taskInfos.pop().stop();
    }

    public String getTraceId() {
        return traceId;
    }

    public Deque<TaskInfo> getTaskInfos() {
        return taskInfos;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return Json.toJson(this);
    }

    /*public static void main(String[] args) {
        StopWatch a = new StopWatch("");
        System.out.println(a);
    }*/
}
