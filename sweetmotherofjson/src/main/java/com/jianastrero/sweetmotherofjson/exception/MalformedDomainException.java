package com.jianastrero.sweetmotherofjson.exception;

/**
 * Created by Jian Astrero on 1/10/2017.
 */
public class MalformedDomainException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Domain name is malformed. please dont add the final slash and http://";
    }
}
