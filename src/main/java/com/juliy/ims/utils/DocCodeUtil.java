package com.juliy.ims.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 单据编号生成类
 * @author JuLiy
 * @date 2022/10/1 18:09
 * @生成规则 1位单号类型+17位时间戳+14位(用户id加密&随机数)
 */
public class DocCodeUtil {
    /** 订单类别头 */
    private static final String ORDER_CODE = "1";
    /** 退货类别头 */
    private static final String RETURN_ORDER = "2";
    /** 退款类别头 */
    private static final String REFUND_ORDER = "3";
    /** 未付款重新支付别头 */
    private static final String AGAIN_ORDER = "4";
    /** 随即编码 */
    private static final int[] RANDOM_ARRAY = new int[]{7, 9, 6, 2, 8, 1, 3, 0, 5, 4};
    /** 用户id和随机数总长度 */
    private static final int MAX_LENGTH = 14;

    private static final Random RANDOM = new Random();

    private DocCodeUtil() {}


    /**
     * 更具id进行加密+加随机数组成固定长度编码
     */
    private static String toCode(Long id) {
        String idStr = id.toString();
        StringBuilder idSb = new StringBuilder();
        for (int i = idStr.length() - 1; i >= 0; i--) {
            idSb.append(RANDOM_ARRAY[idStr.charAt(i) - '0']);
        }
        return idSb.append(getRandom((long) MAX_LENGTH - idStr.length())).toString();
    }

    /**
     * 生成时间戳
     */
    private static String getDateTime() {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    /**
     * 生成固定长度随机码
     * @param n 长度
     */
    private static long getRandom(long n) {
        long min = 1;
        long max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        return ((long) (RANDOM.nextDouble() * (max - min))) + min;
    }

    /**
     * 生成不带类别标头的编码
     * @param userId 用户id
     */
    private static synchronized String getCode(Long userId) {
        userId = userId == null ? 10000 : userId;
        return getDateTime() + toCode(userId);
    }

    /**
     * 生成订单单号编码
     * @param userId 用户id
     */
    public static String getOrderCode(Long userId) {
        return ORDER_CODE + getCode(userId);
    }

    /**
     * 生成退货单号编码
     * @param userId 用户id
     */
    public static String getReturnCode(Long userId) {
        return RETURN_ORDER + getCode(userId);
    }

    /**
     * 生成退款单号编码
     * @param userId 用户id
     */
    public static String getRefundCode(Long userId) {
        return REFUND_ORDER + getCode(userId);
    }

    /**
     * 未付款重新支付
     * @param userId 用户id
     */
    public static String getAgainCode(Long userId) {
        return AGAIN_ORDER + getCode(userId);
    }
}
