package com.longer.core;

import com.alibaba.fastjson.JSON;
import com.longer.bean.DPFBean;
import com.longer.bean.DPFResponse;
import com.longer.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Core {

    Log log = LogFactory.getLog(Core.class);

    public synchronized DPFResponse execute(DPFBean dpfBean, BindingResult bindingResult) {
        DPFResponse result = new DPFResponse();
        StringBuilder stringBuilder = new StringBuilder();
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for(ObjectError error : list) {
                stringBuilder.append(error.getDefaultMessage() + ";");
            }
        }
        if(!stringBuilder.toString().equals("")) {
            result.setFilepath("");
            result.setMsg(stringBuilder.toString());
            result.setStatus("fail");
            return result;
        }
        String filename = dpfBean.getFilename();
        String htmlpath = dpfBean.getHtmlpath();
        String isecology = dpfBean.getIsecology();
        String loginVeri = dpfBean.getLoginVeri();
        String loginid = dpfBean.getLoginid();
        String password = dpfBean.getPassword();
        String ecologyurl = dpfBean.getEcologyurl();
        if(isecology.equals("0")) {
            if(loginVeri.equals("") || loginVeri == null) {
                result.setFilepath("");
                result.setMsg("当isecology为0时，loginVeri必填");
                result.setStatus("fail");
                return result;
            }
            if(loginid.equals("") || loginid == null) {
                result.setFilepath("");
                result.setMsg("当isecology为0时，loginid必填");
                result.setStatus("fail");
                return result;
            }
            if(password.equals("") || password == null) {
                result.setFilepath("");
                result.setMsg("当isecology为0时，password必填");
                result.setStatus("fail");
                return result;
            }
            if(ecologyurl.equals("") || ecologyurl == null) {
                result.setFilepath("");
                result.setMsg("当isecology为0时，ecologyurl必填");
                result.setStatus("fail");
                return result;
            }
            return execEcology(loginVeri,loginid,password,htmlpath,filename,ecologyurl);
        }


        return exe(filename,htmlpath);
    }

    public synchronized DPFResponse exe(String filename, String htmlpath) {
        DPFResponse result = new DPFResponse();
        String currentDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String basepath = System.getProperty("user.dir") + File.separator + "file" + File.separator + currentDateTime;
        if(Util.judeDirExists(new File(basepath))) {
            filename = basepath + File.separator + filename;
        }
        Process process;
        try {
            log.info("execute cmd is " + "wkhtmltopdf "+htmlpath+" "+filename+"");
            process = Runtime.getRuntime().exec("wkhtmltopdf "+htmlpath+" "+filename+"");
            int code = process.waitFor();
            log.info("write file " + filename + " from "+htmlpath+" return code is " + code);
            if (code == 0) {
                result.setStatus("success");
                result.setFilepath(filename);
                result.setMsg("");
            }
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus("fail");
            result.setMsg(e.getMessage());
            result.setFilepath("");
        }
        log.info("write file " + filename + " from "+htmlpath+" result is " + JSON.toJSON(result));
        return result;
    }

    private synchronized DPFResponse execEcology(String surl, String loginid, String password, String redurl, String filename, String ecologyurl) {
        DPFResponse result = new DPFResponse();
        HttpURLConnection resumeConnection = null;
        String wpath = ecologyurl + File.separator + "request.html";
        try {
            URL url = new URL(surl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setInstanceFollowRedirects(false);
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection
                    .getOutputStream(), "utf-8");
            out.write("loginfile=%2fwui%2ftheme%2fecology8%2fpage%2flogin.jsp%3ftemplateId%3d3%26logintype%3d1%26gopage%3d&logintype=1&" +
                    "formmethod=post&isie=false&islanguid=7&submit=%e7%99%bb%e5%bd%95&loginid="+loginid+"&userpassword="+password+""); // post的关键所在！
            out.flush();
            out.close();
            String cookieVal = connection.getHeaderField("Set-Cookie");
            connection.disconnect();
            url = new URL(redurl);
            resumeConnection = (HttpURLConnection) url
                    .openConnection();
            if (cookieVal != null) {
                resumeConnection.setRequestProperty("Cookie", cookieVal);
            }
            resumeConnection.connect();
            InputStream urlStream = resumeConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlStream));
            String ss = null;
            String total = "";
            while ((ss = bufferedReader.readLine()) != null) {
                total += ss;
            }
            if(total.startsWith("<script")) {
                result.setStatus("fail");
                result.setMsg("账号或者密码错误.");
                result.setFilepath("");
                return result;
            }
            IOUtils.write(total, new FileOutputStream(wpath));
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            result.setStatus("fail");
            result.setMsg(e.getMessage());
            result.setFilepath("");
            return result;
        }
        return exe(filename,wpath);
    }
}
