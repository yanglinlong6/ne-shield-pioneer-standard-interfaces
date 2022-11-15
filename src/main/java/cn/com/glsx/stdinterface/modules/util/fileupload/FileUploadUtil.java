package cn.com.glsx.stdinterface.modules.util.fileupload;

import com.alibaba.fastjson.JSONArray;
import com.glsx.plat.common.utils.DateUtils;
import com.glsx.plat.context.utils.PropertiesUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传操作类
 *
 * @author yll
 */
public class FileUploadUtil {

    /**
     * 校验文件类型
     *
     * @param fileType 文件类型
     * @return 是否是合法的文件类型
     */
    public static boolean verifyFileType(String fileType) {
        if (StringUtils.isBlank(fileType)) {
            return false;
        }
        List<String> fileTypes = Arrays.asList(FileConstants.UPLOAD_FILE_TYPE.split(","));
        return fileTypes.contains(fileType);
    }

    /**
     * 将文件上传至文件服务器
     *
     * @param fileName   文件名 (不需要加文件后缀)
     * @param uploadData 长传文件数据
     * @param fileType   文件类型
     * @return FileUploadServerData
     */
    public static FileUploadServerData uploadToFileSystem(String fileName, byte[] uploadData, String fileType) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("systemSign", "salesexcel");
        form.add("filePath", "");
        form.add("param1", fileName);
        form.add("param2", "");
        form.add("param3", "");
        form.add("files", fileType + "@" + Base64.encodeBase64String(uploadData));
        return restTemplate.postForObject(PropertiesUtils.getProperty(FileConstants.FILE_UPLOAD_URL), form, FileUploadServerData.class);
    }

    /**
     * 将文件压缩至文件服务器
     *
     * @param filePathList 文件路径的集合
     * @return FileUploadServerData
     */
    public static FileUploadServerData zipToFileSystem(List filePathList) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("systemSign", "salesexcelzip");
        form.add("filePath", "");
        form.add("param1", "");
        form.add("param2", "");
        form.add("param3", "");
        form.add("zipfiles", JSONArray.toJSON(filePathList).toString());
        return restTemplate.postForObject(PropertiesUtils.getProperty(FileConstants.FILE_ZIP_URL), form, FileUploadServerData.class);
    }

    public static String upload(InputStream fis, String savePath) throws IOException {
        StringBuffer tempFileName = new StringBuffer("pdf");
        tempFileName.append("-");
        tempFileName.append(DateUtils.format(new Date(), "yyMMdd"));
        tempFileName.append(UUID.randomUUID().toString().replace("-", "").substring(16));
        tempFileName.append(".pdf");
        File tempPackage = new File(savePath);
        if (!tempPackage.exists()) {
            tempPackage.mkdirs();
        }
        File tempFile = new File(savePath, tempFileName.toString());
        byte[] tempbytes = new byte[1024];
        int byteread = 0;
        FileOutputStream fos = new FileOutputStream(tempFile);
        while ((byteread = fis.read(tempbytes)) != -1) {
            fos.write(tempbytes, 0, byteread);
            fos.flush();
        }
        fos.close();
        fis.close();
        return tempFileName.toString();
    }

}
