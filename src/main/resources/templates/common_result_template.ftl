package ${instance.packageName};

import lombok.Data;

@Data
public class CommonResult<T> {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private T data;

    public static <T> CommonResult<T> success() {
        CommonResult<T> commonResult = new CommonResult<T>();
        commonResult.setIsSuccess(Boolean.TRUE);
        commonResult.setCode(200);
        commonResult.setMessage("操作成功");
        return commonResult;
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> commonResult = new CommonResult<T>();
        commonResult.setIsSuccess(Boolean.TRUE);
        commonResult.setCode(200);
        commonResult.setMessage("操作成功");
        commonResult.setData(data);
        return commonResult;
    }

    public static <T> CommonResult<T> fail() {
        CommonResult<T> commonResult = new CommonResult<T>();
        commonResult.setIsSuccess(Boolean.FALSE);
        commonResult.setCode(500);
        commonResult.setMessage("未知错误");
        return commonResult;
    }

    public static <T> CommonResult<T> fail(Integer code, String message) {
        CommonResult<T> commonResult = new CommonResult<T>();
        commonResult.setIsSuccess(Boolean.FALSE);
        commonResult.setCode(code);
        commonResult.setMessage(message);
        return commonResult;
    }

}
