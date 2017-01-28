package com.jianastrero.sweetmotherofjsonexample;

import com.jianastrero.sweetmotherofjson.SweetJson;

/**
 * Created by Jian Astrero on 1/21/2017.
 */
public class Connect extends SweetJson {
    public String postValue;

    public Connect(String postValue) {
        super();

        setRoute("sweet.php");
        setSubclassInstance(this);

        this.postValue=postValue;
    }
}
