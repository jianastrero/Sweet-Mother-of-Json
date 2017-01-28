package com.jianastrero.sweetmotherofjson;

import com.jianastrero.sweetmotherofjson.exception.MalformedDomainException;
import com.jianastrero.sweetmotherofjson.exception.MalformedRouteException;

/**
 * Created by Jian Astrero on 1/10/2017.
 */
public class SweetJsonConfig {
    private static String DOMAIN="http://localhost/";

    public static void setDomain(String domain) {
        if (
                domain.charAt(0)=='/' ||
                domain.charAt(domain.length()-1)=='/' ||
                (
                    !domain.equals("localhost") &&
                    !domain.matches(".+\\..+") &&
                    !domain.matches(".+\\..+/.+") &&
                    !domain.matches("localhost/.+")
                )
            ) throw new MalformedDomainException();

        SweetJsonConfig.DOMAIN="http://"+domain+"/";
    }

    public static String getUrl(String route) {
        if (route.matches("/.+")) throw new MalformedRouteException();

        return DOMAIN+route;
    }
}
