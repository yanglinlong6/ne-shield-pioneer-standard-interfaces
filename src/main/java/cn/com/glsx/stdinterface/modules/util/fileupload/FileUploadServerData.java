package cn.com.glsx.stdinterface.modules.util.fileupload;

import lombok.Data;

import java.io.Serializable;

/**
 * 上传文件返回结果实体类
 *
 * @author yll
 */
@SuppressWarnings("serial")
@Data
public class FileUploadServerData implements Serializable {

    public static final int UPLOAD_SUCCSSS = 0; // "上传文件成功！",
    public static final int UPLOAD_FAILURE = 1; // "上传文件失败！"),
    //private static final int UPLOAD_TYPE_ERROR = 2; // "上传文件类型错误！"),
    //private static final int UPLOAD_OVERSIZE = 3; // "上传文件过大！"),
    //private static final int UPLOAD_ZEROSIZE = 4; // "上传文件为空！"),
    //private static final int UPLOAD_NOTFOUND = 5; // "上传文件路径错误！")

    private Integer status = 0;
    private String message = "";
    private String url = "";

    private String systemSign;
    private String filePath;
    private String param1;
    private String param2;
    private String param3;

    private String secureUrl;
}
