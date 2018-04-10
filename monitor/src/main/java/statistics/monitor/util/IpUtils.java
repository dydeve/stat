package statistics.monitor.util;

import statistics.monitor.log.Log;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by dy on 2018/4/10.
 */
public class IpUtils {

    private static final String XFF = "X-Forwarded-For";

    /**
     * X-Forwarded-For
     * Proxy-Client-IP
     * WL-Proxy-Client-IP
     * HTTP_CLIENT_IP
     * HTTP_X_FORWARDED_FOR
     */

    private static final String[] LOOP_HEAD_NAMES = new String[] {XFF};

    public static boolean isIpInvalid(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equals(ip);
    }

    public static String getIpAddress(HttpServletRequest httpRequest) {
        String ip;
        for (String headName : LOOP_HEAD_NAMES) {
            ip = httpRequest.getHeader(headName);
            if (!isIpInvalid(ip)) {
                ip = getFirstValidIp(ip);
                if (ip != null) {
                    return ip;
                }
            }
        }

        ip = httpRequest.getRemoteAddr();

        //can't get ip from head, show all headers
        Log.debug("remote upstream ip is : {}, headers are : {}", ip, getHeaders(httpRequest));


        return ip;

    }

    public static String getHeaders(HttpServletRequest httpRequest) {
        Enumeration<String> headerNaames = httpRequest.getHeaderNames();
        String currentHeaderName;
        StringBuilder buf = new StringBuilder();
        while (headerNaames.hasMoreElements()) {
            currentHeaderName = headerNaames.nextElement();
            buf.append(currentHeaderName).append(":");
            buf.append(httpRequest.getHeader(currentHeaderName)).append(",").append("\n");
        }
        buf.delete(buf.length() - 2, buf.length());
        return buf.toString();
    }

    public static String getFirstValidIp(String ipString) {
        String[] ips = ipString.split(",");
        for (String ip : ips) {
            if (!isIpInvalid(ip)) {
                return ip;
            }
        }
        return null;
    }

    public static boolean isLocalIp(String ip) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            return inetAddress.isSiteLocalAddress() || inetAddress.isLoopbackAddress();
        } catch (UnknownHostException e) {
            Log.error("parse ip {} fail. e.message {}",  ip, e.getMessage());
            return false;
        }
    }

    //From HBase Addressing.Java getIpAddress
    public static InetAddress getLocalInetAddress() throws SocketException {
        // Before we connect somewhere, we cannot be sure about what we'd be bound to; however,
        // we only connect when the message where client ID is, is long constructed. Thus,
        // just use whichever IP address we can find.
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface current = interfaces.nextElement();
            if (!current.isUp() || current.isLoopback() || current.isVirtual()) {
                continue;
            }
            Enumeration<InetAddress> addresses = current.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                if (!addr.isLoopbackAddress()) {
                    return addr;
                }
            }
        }

        throw new SocketException("Can't get our ip address, interfaces are: " + interfaces);
    }

    public static String getLocalIpAddress() {
        try {
            return getLocalInetAddress().getHostAddress();
        } catch (SocketException e) {
            Log.error("can't get localhost ip address.", e);
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e1) {
                Log.error("unknown host.", e);
            }
            return "127.0.0.1";
        }
    }

}
