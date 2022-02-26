package com.sceptive.forgiva.integrator.core.util;

import javax.servlet.http.HttpServletRequest;

import static com.sceptive.forgiva.integrator.core.Constants.IP_HEADERS;

public class Network {

    public static String get_ip_address(HttpServletRequest request) {

        for (String header : IP_HEADERS) {
            String ret = request.getHeader(header);
            if (ret != null && !ret.equalsIgnoreCase("unknown")
                    && ret.contains(".")) {
                return ret;
            }
        }

        return request.getRemoteAddr();

    }
}
