package cn.com.glsx.stdinterface.modules.util;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;

/**
 * 平安租赁加密工具类
 *
 * @author yll
 */
@Slf4j
public class Sm4EncryptUtils {

    static {
        Security.addProvider(new BouncyCastleProvider());
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
        byte[] zero = new byte[len];
        return new IvParameterSpec(zero);
    }

    /**
     * sm4加密
     *
     * @param hexKey   16进制密钥（忽略大小写）
     * @param paramStr 待加密字符串
     * @return 返回16进制的加密字符串
     * @throws Exception
     * @explain 加密模式：CBC
     * 密文长度不固定，会随着被加密字符串长度的变化而变化
     */
    public static String encryptCbc(String hexKey, String paramStr) {
        String cipherText = "";
        // 16进制字符串-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // String-->byte[]
        byte[] srcData = paramStr.getBytes(StandardCharsets.UTF_8);
        // 加密后的数组
        byte[] cipherArray = encryptCbcPadding(keyData, srcData, "SM4/CBC/PKCS7Padding");
        // byte[]-->hexString
        cipherText = ByteUtils.toHexString(cipherArray);
        return cipherText;
    }

    /**
     * SM4 Cbc模式 加密
     *
     * @param key       密钥
     * @param data      明文
     * @param algorithm 加密模式+填充模式
     * @return 密文
     */
    public static byte[] encryptCbcPadding(byte[] key, byte[] data, String algorithm) {
        byte[] res = null;
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(algorithm, "BC");
            SecretKeySpec secretKeySpec = getSm4Key(key);
            IvParameterSpec ivParameterSpec = getIv(cipher.getBlockSize());
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            res = cipher.doFinal(data);
            return res;
        } catch (Exception e) {
            log.error("Fail: Sm4 Cbc Encrypt {1}", e);
        }
        return res;
    }
}
