package com.liust.myproject.MyFiter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class RequestWrapper extends HttpServletRequestWrapper {

    private final String body;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public RequestWrapper(HttpServletRequest request) {
        super(request);
        StringBuffer stringBuffer=null;
        ServletInputStream inputStream=null;
        BufferedReader bufferedReader=null;
        try {
            stringBuffer=new StringBuffer();
             inputStream = request.getInputStream();
             if(inputStream!=null){
                 bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                 char[] chars = new char[128];
                 int bytesRead=-1;
                 while((bytesRead=bufferedReader.read(chars))>0){
                     stringBuffer.append(chars, 0, bytesRead);
                 }
             }else{
                 stringBuffer.append("");
             }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        body=stringBuffer.toString();
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public int read()  {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };

        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBody() {
        return this.body;
    }
}
