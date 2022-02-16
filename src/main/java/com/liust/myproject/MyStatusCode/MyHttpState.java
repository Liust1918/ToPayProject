package com.liust.myproject.MyStatusCode;


public enum MyHttpState {

    Successful_Run(2000,"运行成功"),
    Login_fail(5001,"登录错误"),
    Login_Successful(2001,"登录成功"),
    token_dumn(5002, "token失效"),
    Value_Null(5000,"数据为空");

    private final Integer code;
    private final String msg;

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
