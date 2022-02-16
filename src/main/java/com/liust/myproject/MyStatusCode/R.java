package com.liust.myproject.MyStatusCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class R  {
    private Integer code;
    private String msg;
    private Object data;

    public static R setResult(Object data,MyHttpState httpState){
        return new R(httpState.getCode(),httpState.getMsg(),data);
    }

    public static R valueIsNull(Object data){
        MyHttpState myHttpState =MyHttpState.Value_Null;
        return new R(myHttpState.getCode(),myHttpState.getMsg(),data);
    }

}
