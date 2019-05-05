package com.longer.test;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {

    public static void test () throws Exception {
        // 连接地址（通过阅读html源代码获得，即为登陆表单提交的URL）
        String surl = "http://10.32.5.48/login/VerifyLogin.jsp";

        /**
         * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using
         * java.net.URL and //java.net.URLConnection
         */
        URL url = new URL(surl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        /**
         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
         */
        connection.setDoOutput(true);  //打开输出，向服务器输出参数（POST方式、字节）（写参数之前应先将参数的字节长度设置到配置"Content-Length"<字节长度>）
        connection.setDoInput(true);//打开输入，从服务器读取返回数据
        connection.setRequestMethod("POST"); //设置登录模式为POST（字符串大写）
        connection.setInstanceFollowRedirects(false);
        connection.connect();
        /**
         * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
         */
        OutputStreamWriter out = new OutputStreamWriter(connection
                .getOutputStream(), "utf-8");
        //其中的loginName和loginPassword也是阅读html代码得知的，即为表单中对应的参数名称
        out.write("loginfile=%2fwui%2ftheme%2fecology8%2fpage%2flogin.jsp%3ftemplateId%3d3%26logintype%3d1%26gopage%3d&logintype=1&" +
                "formmethod=post&isie=false&islanguid=7&submit=%e7%99%bb%e5%bd%95&loginid=adminnb&userpassword=85017668"); // post的关键所在！
        //remember to clean up
        out.flush();
        out.close();

        //取得cookie，相当于记录了身份，供下次访问时使用
        //HttpURLConnection.getHeaderFields()).get("Set-Cookie")用于迭代读取Cookie，为以后使用
        //HttpURLConnection.getHeaderField("Set-Cookie")也可用于读取Cookie，但不一定能读取完全
        String cookieVal = connection.getHeaderField("Set-Cookie");  //格式:JSESSIONID=541884418E77E7F07363CCEE91D4FF7E; Path=/
        connection.disconnect();

        //登陆成功后，即可访问其他URL了。
        String s = "http://10.32.5.48/workflow/request/PrintRequest.jsp?f_weaver_belongto_userid=1234&f_weaver_belongto_usertype=&requestid=365314&isprint=1&fromFlowDoc=1&urger=0&ismonitor=0&relaterequest=&isrequest=0";
        //重新打开一个连接
        url = new URL(s);
        HttpURLConnection resumeConnection = (HttpURLConnection) url
                .openConnection();
        if (cookieVal != null) {
            //发送cookie信息上去，以表明自己的身份，否则会被认为没有权限
            resumeConnection.setRequestProperty("Cookie", cookieVal);//设置登陆配置
        }
        resumeConnection.connect();
        InputStream urlStream = resumeConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlStream));
        String ss = null;
        String total = "";
        while ((ss = bufferedReader.readLine()) != null) {
            System.err.println(ss);
            total += ss;
        }
        String wpath = System.getProperty("user.dir");
        IOUtils.write(total, new FileOutputStream(wpath + "/index.html"));
        bufferedReader.close();
    }


    public static void main(String[] args) {
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
