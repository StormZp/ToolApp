package com.storm.tool.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间操作工具
 *
 * @author gfuil
 */
public class TimeUtil {

    /**
     * 获取当前时间
     *
     * @param format
     *            时间格式：例如yyyy-MM-dd HH:mm:ss
     * @return 格式化后的时间
     */
    public static String getSystemTime(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(System.currentTimeMillis());
    }

    /**
     * 时间戳转换成时间
     *
     * @param format
     *            时间格式：例如yyyy-MM-dd HH:mm:ss
     * @return 格式化后的时间
     */
    public static String convertTime(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }

    /**
     * 时间转换成时间戳
     *
     * @param time 格式化后的时间
     * @param format 时间格式：例如yyyy-MM-dd HH:mm:ss
     * @return 时间戳
     */
    public static long timeStringToTime(String time,String format){
        long t = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            Date date = dateFormat.parse(time);
            t = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 时间重新格式化
     * @param time 时间
     * @param oldFormat 原来的时间格式
     * @param newFormat 新的时间格式
     * @return 格式化后的时间
     */
    public static String reformatTime(String time,String oldFormat,String newFormat){
        return convertTime(timeStringToTime(time,oldFormat),newFormat);
    }

    /**
     * 对比两个时间相差的天数
     * @param begin 开始时间戳
     * @param end 结束时间戳
     * @return 天数
     */
    public static long getDayTwoTime(long begin,long end) {
        long beginTime = new Date(begin).getTime();
        long endTime = new Date(end).getTime();

        return (long)((endTime - beginTime) / (1000 * 60 * 60 * 24));
    }

    /**
     * 对比两个时间相差的小时数
     * @param begin 开始时间戳
     * @param end 结束时间戳
     * @return 小时数
     */
    public static long getHourTwoTime(long begin,long end) {
        long beginTime = new Date(begin).getTime();
        long endTime = new Date(end).getTime();

        return (long)((endTime - beginTime) / (1000 * 60 * 60 ));
    }

    public static String setVoiceFormat(int duration) {
        int m = duration / 60 % 60;
        int s = duration % 60;
        StringBuffer sb = new StringBuffer();

        if (m == 0) {
            sb.append("00:");
        } else if (m < 10) {
            sb.append("0" + m + ":");
        } else if (m > 10 || m == 10) {
            sb.append(m + ":");
        }
        if (s == 0) {
            sb.append("00");
        } else if (s < 10) {
            sb.append("0" + s);
        } else if (s >= 10) {
            sb.append(s);
        }

        return sb.toString();
    }

}
