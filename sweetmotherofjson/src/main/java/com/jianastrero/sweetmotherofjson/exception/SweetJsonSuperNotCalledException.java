package com.jianastrero.sweetmotherofjson.exception;

/**
 * Created by Jian Astrero on 1/28/2017.
 */
public class SweetJsonSuperNotCalledException extends RuntimeException {
    @Override
    public String getMessage() {
        return "SweetJson subclasses should call super()! please call super() on this subclass";
    }
}
