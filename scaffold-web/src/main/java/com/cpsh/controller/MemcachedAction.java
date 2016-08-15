package com.cpsh.controller;

import org.springframework.stereotype.Controller;
//import test.MemUser;

@Controller
public class MemcachedAction {
    /*
    @Autowired
    private SpyMemcachedClient spyMemcachedClient;
    
    @Autowired
    private MemcachedClient memcachedClient;
    
    @RequestMapping(value = "/memcache/allkeys")
    @ResponseBody
    public Object getAllKeys() throws Exception {
       // spyMemcachedClient
   //     MemcachedClient memcachedClient = new MemcachedClient(new InetSocketAddress("45.32.255.13",11211));

        String key = "key001";
        String value = "你好，中国";
        memcachedClient.set(key,3000,value);

    //    MemUser user = new MemUser("张三");
    //    client.set("MemUser",3000,user);

        String getVal = (String)memcachedClient.get(key);
        Map<Object, Object> map = new HashMap<Object, Object>();
        
        map.put(key, getVal);
        
        return map;
    }*/
}
