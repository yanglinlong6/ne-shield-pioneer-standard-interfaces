package cn.com.glsx.stdinterface.modules.util;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Arrays;

/**
 * 平安租赁解密工具类
 *
 * @author yll
 */
@Slf4j
public class Sm4DecryptUtils {

    /**
     * sm4解密
     *
     * @param hexKey     16进制密钥
     * @param cipherText 16进制的加密字符串（忽略大小写）
     * @return 解密后的字符串
     * @explain 解密模式：采用CBC
     */
    public static String decryptCbc(String hexKey, String cipherText) {
        // 用于接收解密后的字符串
        String decryptStr = "";
        // hexString-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // hexString-->byte[]
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        // 解密
        byte[] srcData = decryptCbcPadding(keyData, cipherData, "SM4/CBC/PKCS7Padding");
        // byte[]-->String
        decryptStr = new String(srcData, StandardCharsets.UTF_8);
        return decryptStr;
    }

    /**
     * SM4 Cbc模式 解密
     *
     * @param key       密钥
     * @param data      密文
     * @param algorithm 加密模式+填充模式
     * @return 明文
     */
    public static byte[] decryptCbcPadding(byte[] key, byte[] data, String algorithm) {
        byte[] res = null;
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(algorithm, "BC");
            SecretKeySpec secretKeySpec = getSm4Key(key);
            IvParameterSpec ivParameterSpec = getIv(cipher.getBlockSize());
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            res = cipher.doFinal(data);
            return res;
        } catch (Exception e) {
            log.error("Fail: Sm4 Cbc Decrypt {1}", e);
        }
        return res;
    }

    /**
     * 校验加密前后的字符串是否为同一数据
     *
     * @param hexKey     16进制密钥（忽略大小写）
     * @param cipherText 16进制加密后的字符串
     * @param paramStr   加密前的字符串
     * @return 是否为同一数据
     * @throws Exception
     * @explain
     */
    public static boolean verifyCbc(String hexKey, String cipherText, String paramStr) {
        // 用于接收校验结果
        boolean flag = false;
        // hexString-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // 将16进制字符串转换成数组
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        // 解密
        byte[] decryptData = decryptCbcPadding(keyData, cipherData, "SM4/CBC/PKCS7Padding");
        // 将原字符串转换成byte[]
        byte[] srcData = paramStr.getBytes(StandardCharsets.UTF_8);
        // 判断2个数组是否一致
        flag = Arrays.equals(decryptData, srcData);
        return flag;
    }

    /**
     * 生成国密Key：SM4，密钥为 128bit， 16byte
     */
    public static SecretKeySpec getSm4Key(byte[] key) {
        if (key.length != 16) {
            log.warn("SM4's key should be 16bytes, 128bits");
        }
        return new SecretKeySpec(key, "SM4");
    }

    /**
     * 初始化向量
     *
     * @param len 长度
     * @return
     */
    public static IvParameterSpec getIv(int len) {
        //使用 IV 的例子是反馈模式中的密码，如，CBC 模式中的 DES 和使用 OAEP 编码操作的 RSA 密码
        byte[] zero = new byte[len];
        return new IvParameterSpec(zero);
    }
}
