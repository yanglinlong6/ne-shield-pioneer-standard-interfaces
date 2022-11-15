package cn.com.glsx.stdinterface.modules.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件操作类
 *
 * @author longjuan@didihu.com.cn
 * @version 1.0
 * @history 2014-9-16
 */
public class FileUtil {

    private static final SimpleDateFormat FMT = new SimpleDateFormat("yyMMdd");

    /**
     * 判断文件是否存在
     */
    public static boolean existFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空
        if (file.isFile() && file.exists()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     * @author lidh@didihu.com.cn on 20171024
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        } else {
            System.out.println("文件不存在或路径为空");
        }
        return flag;
    }

    public static String byte2file(byte[] data, String imagePath, String imageName) {
        if (data == null || "".equals(imageName) || "".equals(imagePath)) {
            return null;
        }
        String path = imagePath + "/" + imageName;
        System.out.println("save paht ：" + path);
        FileOutputStream imageOutput = null;
        File dir = new File(imagePath);
        if (!dir.exists() && !dir.isDirectory()) {//判断文件目录是否存在
            dir.mkdirs();
        }
        try {
            imageOutput = new FileOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.flush();
        } catch (Exception ex) {
            path = null;
            ex.printStackTrace();
        } finally {
            if (imageOutput != null) {
                try {
                    imageOutput.close();
                } catch (IOException e) {
                    path = null;
                    e.printStackTrace();
                }
            }
        }
        return path;
    }

    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 下载excel模版
     *
     * @param response
     * @param downloadName 下载后显示的文件名，例如：无屏追踪器安装申请单-模版
     * @param fileName     要下载的文件名，例如：importVehicleWorkorder.xls
     */
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response, String downloadName, String fileName) throws Exception {
        OutputStream os = response.getOutputStream();
        response.reset();

        String agent = request.getHeader("USER-AGENT");
        if (null != agent) {
            if (agent.contains("Firefox")) {//Firefox
                downloadName = "=?UTF-8?B?" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(downloadName.getBytes("UTF-8")))) + "?=";
            } else if (agent.contains("Chrome")) {//Chrome
                downloadName = new String(downloadName.getBytes(), "ISO8859-1");
            } else {//IE7+
                downloadName = java.net.URLEncoder.encode(downloadName, "UTF-8");
                downloadName = StringUtils.replace(downloadName, "+", "%20");//替换空格
            }
        }

        response.setHeader("Content-Disposition", "attachment; filename=" + downloadName + ".xls");
//    	response.setContentType("application/binary;charset=GBK");
        //需要下载的路径
        String path = request.getSession().getServletContext().getRealPath("/") + File.separator + "excelTemplate" + File.separator + fileName;
        File file = new File(path);
        os.write(FileUtils.readFileToByteArray(file));
        os.flush();
        os.close();
    }

    /**
     * 下载指定类型的模版
     *
     * @param response
     * @param downloadName 下载后显示的文件名，例如：无屏追踪器安装申请单-模版
     * @param fileName     要下载的文件名，例如：importVehicleWorkorder.xls
     * @param fileType     指定类型,支持 .doc,.xls
     */
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response, String downloadName, String fileName, String fileType) throws Exception {
        OutputStream os = response.getOutputStream();
        response.reset();

        String agent = request.getHeader("USER-AGENT");
        if (null != agent) {
            if (agent.contains("Firefox")) {//Firefox
                downloadName = "=?UTF-8?B?" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(downloadName.getBytes("UTF-8")))) + "?=";
            } else if (agent.contains("Chrome")) {//Chrome
                downloadName = new String(downloadName.getBytes(), "ISO8859-1");
            } else {//IE7+
                downloadName = java.net.URLEncoder.encode(downloadName, "UTF-8");
                downloadName = StringUtils.replace(downloadName, "+", "%20");//替换空格
            }
        }

        response.setHeader("Content-Disposition", "attachment; filename=" + downloadName + fileType);
//    	response.setContentType("application/binary;charset=GBK");
        //需要下载的路径
        String path = request.getSession().getServletContext().getRealPath("/") + File.separator + "docTemplate" + File.separator + fileName;
        File file = new File(path);
        os.write(FileUtils.readFileToByteArray(file));
        os.flush();

        os.close();
    }

    /**
     * 导入文件
     *
     * @param request
     * @return
     * @throws Exception
     */
    public InputStream unloadFile(HttpServletRequest request, String fileName) throws Exception {
        //加载导入文件
        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = mulRequest.getFile(fileName);
        String filename = file.getOriginalFilename();
        if (filename == null || "".equals(filename)) {
            return null;
        }
        return file.getInputStream();
    }

    public String upload(InputStream fis, String savePath) throws IOException {
        StringBuilder tempFileName = new StringBuilder("pdf");
        tempFileName.append("-");
        tempFileName.append(FMT.format(new Date()));
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
