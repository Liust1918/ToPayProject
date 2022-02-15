package com.liust.myproject.MyStatusCode;

/**
 * @author liuyulong
 * @create 2022-02-15 9:07
 * @create 2022-二月  星期二
 * @project MyProject
 */
public enum MyHttpState {

    Successful_Run(2000,"运行成功");

    private Integer code;
    private String msg;

    MyHttpState(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
