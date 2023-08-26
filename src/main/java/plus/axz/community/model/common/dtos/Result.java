package plus.axz.community.model.common.dtos;

import plus.axz.community.model.common.enums.ResultEnum;

import java.io.Serializable;

/**
 * 通用的结果返回类
 * @param <T>
 */
public class Result<T> implements Serializable {

    private String host;

    private Integer code;

    private String message;

    private T data;

    public Result() {
        this.code = 200;
    }

    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public static Result errorResult(int code, String msg) {
        Result result = new Result();
        return result.error(code, msg);
    }

    public static Result okResult(int code, String msg) {
        Result result = new Result();
        return result.ok(code, null, msg);
    }

    public static Result okResult(Object data) {
        Result result = setResultEnum(ResultEnum.SUCCESS, ResultEnum.SUCCESS.getMessage());
        if(data!=null) {
            result.setData(data);
        }
        return result;
    }

    public static Result errorResult(ResultEnum enums){
        return setResultEnum(enums,enums.getMessage());
    }

    public static Result errorResult(ResultEnum enums, String message){
        return setResultEnum(enums,message);
    }

    public static Result setResultEnum(ResultEnum enums){
        return okResult(enums.getCode(),enums.getMessage());
    }

    private static Result setResultEnum(ResultEnum enums, String message){
        return okResult(enums.getCode(),message);
    }

    public Result<?> error(Integer code, String msg) {
        this.code = code;
        this.message = msg;
        return this;
    }

    public Result<?> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public Result<?> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.message = msg;
        return this;
    }

    public Result<?> ok(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


    public static void main(String[] args) {
        //前置
        /*ResultEnum success = ResultEnum.SUCCESS;
        System.out.println(success.getCode());
        System.out.println(success.getmessage());*/
        /*0 操作成功*/

        //查询一个对象
        /*Map map = new HashMap();
        map.put("name","zhangsan");
        map.put("age",18);
        ResponseResult result = ResponseResult.okResult(map);
        System.out.println(JSON.toJSONString(result));*/
        /*{"code":0,"data":{"name":"zhangsan","age":18},"message":"操作成功"}*/


        //新增，修改，删除  在项目中统一返回成功即可
        /*ResponseResult result = ResponseResult.errorResult(ResultEnum.SUCCESS);
        System.out.println(JSON.toJSONString(result));*/
        /*{"code":0,"message":"操作成功"}*/


        //根据不用的业务返回不同的提示信息  比如：当前操作需要登录、参数错误
        /*ResponseResult result = ResponseResult.errorResult(ResultEnum.NEED_LOGIN);
        System.out.println(JSON.toJSONString(result));*/
        /*{"code":1,"message":"需要登录后操作"}*/

        //查询分页信息
        /*PageResponseResult responseResult = new PageResponseResult(1,5,50);
        List list = new ArrayList();
        list.add("axz");
        list.add("xz");
        responseResult.setData(list);
        System.out.println(JSON.toJSONString(responseResult));*/
        /*{"code":200,"currentPage":1,"data":["axz","xz"],"size":5,"total":50}*/

    }

}
