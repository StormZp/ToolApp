package com.storm.tool.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * 文件操作工具类
 *
 * @author gfuil
 */
public class FileUtil {

    /**
     * 判读SD卡是否存在
     *
     * @return true/false
     */
    public static boolean isSDCardExists() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /***
     * 判断文件是否存在
     * @param folderName
     * @param fileName
     * @return
     */
    public static File getEmptyFile(String folderName,String fileName) {
        File folder = FileUtil.createFolders(folderName);
        if (folder != null) {
            if (folder.exists()) {
                File file = new File(folder, fileName);
                return file;
            }
        }
        return null;
    }

    public static File getEmptyFile(String name) {
        File folder = FileUtil.createFolders();
        if (folder != null) {
            if (folder.exists()) {
                File file = new File(folder, name);
                return file;
            }
        }
        return null;
    }


    /**
     * 获取存贮文件的文件夹路径
     *
     * @return
     */
    public static File createFolders() {
        File baseDir;
        if (Build.VERSION.SDK_INT < 8) {
            baseDir = Environment.getExternalStorageDirectory();
        } else {
            //			baseDir = Environment
            //					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            baseDir = Environment.getExternalStorageDirectory();
        }
        if (baseDir == null)
            return Environment.getExternalStorageDirectory();
        File aviaryFolder = new File(Environment.getExternalStorageDirectory() + "/ephotos/xinlanedit");
        if (aviaryFolder.exists())
            return aviaryFolder;
        if (aviaryFolder.isFile())
            aviaryFolder.delete();
        if (aviaryFolder.mkdirs())
            return aviaryFolder;
        return Environment.getExternalStorageDirectory();
    }
    /**
     * 获取存贮文件的文件夹路径
     *
     * @return
     * @param name
     */
    public static File createFolders(String name) {
        File baseDir;
        if (Build.VERSION.SDK_INT < 8) {
            baseDir = Environment.getExternalStorageDirectory();
        } else {
//			baseDir = Environment
//					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            baseDir = Environment.getExternalStorageDirectory();
        }
        if (baseDir == null)
            return Environment.getExternalStorageDirectory();
        File aviaryFolder = new File(name);
        if (aviaryFolder.exists())
            return aviaryFolder;
        if (aviaryFolder.isFile())
            aviaryFolder.delete();
        if (aviaryFolder.mkdirs())
            return aviaryFolder;
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 获取SD卡目录File对象
     *
     * @return SD卡目录File对象
     */
    public static File getSDCard() {
        File file = null;
        if (isSDCardExists()) {
            file = Environment.getExternalStorageDirectory();
        }
        return file;
    }

    /**
     * 删除文件或目录
     *
     * @return 是否成功
     */
    public static boolean deleteFile(File file) {
        boolean isSuccess = false;
        if (file.exists()) {
            if (file.isFile()) {
                isSuccess = file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();

                if (files != null || files.length == 0) {
                    isSuccess = file.delete();
                }
                for (File f : files) {
                    deleteFile(f);
                }

            }
        }
        return isSuccess;
    }

    /**
     * 删除文件或目录
     *
     * @return 是否成功
     */
    public static boolean deleteFile(String filename) {
        return deleteFile(new File(filename));
    }

    /**
     * 获取文件字节数组
     *
     * @param file
     * @return byte数组
     */
    public static byte[] getFileBytes(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
            fis.close();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    /**
     * 获取文件后缀名
     *
     * @return
     */
    public static String getFilePrefix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).trim();
    }

    /**
     * 获取文件的MIMETYPE
     *
     * @return
     */
    public static String getMimeType(String fileName) {
        String result = "application/octet-stream";
        int extPos = fileName.lastIndexOf(".");
        if (extPos != -1) {
            String ext = fileName.substring(extPos + 1);
            result = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
        }
        return result;
    }


    /**
     * 写文件到SDcard
     *
     * @param file
     * @param string
     */
    public static void writeFileToSDCard(File file, String string) {
        if (isSDCardExists()) {
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                FileOutputStream fos = new FileOutputStream(file, true);
                fos.write(string.getBytes());

                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    public static Boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                Boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        }
        return true;
    }

    /**
     * 获取缓存大小
     *
     * @param context *
     * @return *
     * @throws Exception
     */
//        @Throws(Exception::class)
    public static String getTotalCacheSize(Context context) {
        Long cacheSize = getDirSize(context.getCacheDir());
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            cacheSize += getDirSize(context.getExternalCacheDir());
        }
        return formatFileSize(cacheSize);
    }

    /**
     * 获取目录文件大小
     *
     * @param dir *
     * @return
     */

    public static Long getDirSize(File dir) {
        if (dir == null) {
            return 0L;
        }
        if (!dir.isDirectory()) {
            return 0L;
        }
        Long dirSize = 0L;
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                dirSize += files[i].length();
            } else {
                dirSize += files[i].length();
                dirSize += getDirSize(files[i]); // 递归调用继续统计
            }
        }

        return dirSize;
    }

    /**
     * 转换文件大小
     *
     * @param fileS *
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(Long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");

//        val df = DecimalFormat("#.00")
        String fileSizeString = null;
        if (fileS < 1024) {
            fileSizeString = df.format(Double.valueOf(fileS)) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format(Double.valueOf(fileS) / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format(Double.valueOf(fileS) / 1048576) + "MB";
        } else {
            fileSizeString = df.format(Double.valueOf(fileS) / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
