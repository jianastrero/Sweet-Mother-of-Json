# Sweet-Mother-of-Json
The Sweetest way into connecting to your server

[![](https://jitpack.io/v/jianastrero/Sweet-Mother-of-Json.svg)](https://jitpack.io/#jianastrero/Sweet-Mother-of-Json)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Sweet--Mother--of--Json-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5121)
[![API](https://img.shields.io/badge/API-3%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=3)

##Installation
Add the JitPack repository to your build file then add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency
```
dependencies {
    compile 'com.github.jianastrero:Sweet-Mother-of-Json:v2.1'
}
```


##Usage - Youtube (v1.0) or Instruction v2.1
####The 1st difference of v1.0 to v2.1 is the name of classes
#####SweetConfig -> SweetJsonConfig; Sweet -> SweetJson;
####The 2nd is, the mid sdk is lowered till api 3
###Youtube
[![Sweet Mother of Json Library tutorial](http://img.youtube.com/vi/ClngqHc_1kM/0.jpg)](http://www.youtube.com/watch?v=ClngqHc_1kM)


###Instructions
Call SweetJsonConfig.setDomain atleast once (recomended to be on the first activity) in your application
```
SweetJsonConfig.setDomain("192.168.43.33/sweet"); //when using a local server. this may be mydomain.com
```


Extend SweetJson and declare public fields equivalent to post data names. Then call super for the constructor, set the route and set the subclass instance always to this
```
public class Connect extends SweetJson {
    public String postValue; //this is the value which in php will be taken from $_POST["postValue"]

    public Connect(String postValue) {
        super(); //always call super

        setRoute("sweet.php"); //set the route
        setSubclassInstance(this);  //never changers

        this.postValue=postValue;
    }
}
```
####the code above correspond to this php file:
```
    echo 'postValue = '.$_POST["postValue"];
```


Lastly, create an instance of your subclass then call submit. if wanted an `OnConnectionListener` can be set to the instance to listen `onBeforeConnectionStart` and `onAfterConnectionStopped`. `onAfterConnectionStopped` has parameters of result, jsonobject and json array, in which result will always have a value, jsonobject can be null if the result is not a jsonobject else not. and so as with the jsonarray.
```
Connect connect=new Connect("Sweet Mother Of Json");
connect.setOnConnectionListener(new Sweet.OnConnectionListener() {
    @Override
    public void onBeforeConnectionStart() {
        textView.setText("Connecting to server...");
    }

    @Override
    public void onAfterConnectionStopped(String result, JSONObject jsonObject, JSONArray jsonArray) {
        textView.setText(""+result);
    }
});
connect.submit();
```


##Thats it! Now you can easily connect to your server with a short and elegant code :)
