package com.liust.myproject.MyStatusCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author liuyulong
 * @create 2022-02-15 9:06
 * @create 2022-二月  星期二
 * @project MyProject
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class R implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public static R setResult(Object data,MyHttpState httpState){
        return new R(httpState.getCode(),httpState.getMsg(),data);
    }

}
