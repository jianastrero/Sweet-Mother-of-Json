package com.jianastrero.sweetmotherofjson.exception;

/**
 * Created by Jian Astrero on 1/10/2017.
 */
public class MalformedRouteException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Route is malformed. please dont add slash in front nor slash at the back.";
    }
}
