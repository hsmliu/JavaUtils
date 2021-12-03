package com.pubinfo.edu.bigdata.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * created By Liu WenCheng on 2021/11/5 2:50 下午
 * Description:
 */

public class AESUtil {
    /**
     * 数据服务加解密源码 *
     */
    // 密钥
    private static String charset = "utf-8";

    // 偏移量
    private static int offset = 16;

    private static String transformation = "AES/CBC/PKCS5Padding";

    private static String algorithm = "AES";

    private static String IV = "0000000000000000";

    /**
     * @param content 需要加密的内容 *
     * @param key     加密密码
     * @return
     */
    public static String encrypt(String content, String key, String IV) {
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            Cipher cipher = Cipher.getInstance(transformation);

            byte[] byteContent = content.getBytes(charset);
            cipher.init(Cipher.ENCRYPT_MODE, skey, iv);// 初始化
            byte[] result = cipher.doFinal(byteContent);

            return new Base64().encodeToString(result); // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES（256）解密
     *
     * @param content 待解密内容
     * @param key     解密密钥
     * @return 解密之后
     * @throws Exception
     */
    public static String decrypt(String content, String key, String IV) {
        try {

            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, skey, iv);// 初始化
            byte[] result = cipher.doFinal(new Base64().decode(content));
            return new String(result); // 解密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
//        String s = "hello world"; // 加密
        JSONObject params = new JSONObject();
        params.put("params1", "aaa");
        params.put("params2", "bbb");
        System.out.println(params);
        String s = params.toString();
        Object parse = JSONObject.parse(s);
        System.out.println(parse);
        //System.out.println("加密前：" + s);
        String encryptResultStr = AESUtil.encrypt(s, "1234567890123456", "0000000000000000");
        System.out.println("加密后：" + encryptResultStr); // 解密
        System.out.println(" 解 密 后 ： " + decrypt(encryptResultStr, "1234567890123456", "0000000000000000"));

        // key 1234567890qwerdf
    }
}
