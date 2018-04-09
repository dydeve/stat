package statistics.monitor.trace;

import java.util.Deque;
import java.util.LinkedList;

/**
 * not thread-safe.
 * Created by dy on 2018/4/9.
 */
public class Overall {

    private final String tid;
    private final Deque<Trace> traces;
    //tomcat 属性
    private String host;
    private String client;
    private String uri;
    private String queryString;

    private int httpStatus;

    //mq,rpc等 属性



    public Overall(String tid) {
        this.tid = tid;
        this.traces = new LinkedList<>();
    }

    public String getTid() {
        return tid;
    }

    public Deque<Trace> getTraces() {
        return traces;
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

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}
