package com.storm.tool.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;

import com.meidp.tool.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lht on 16/10/17.
 */

public class ImageUtil {
    public static boolean saveBitmap(Bitmap bitmap, String path, boolean recyle) {
        if (bitmap == null || TextUtils.isEmpty(path)) {
            return false;
        }

        BufferedOutputStream bos = null;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);

            return true;
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
            if (recyle) {
                bitmap.recycle();
            }
        }
    }

    private final static String SHARE_IMAGE_DIR = "image";
    private final static String SHARE_IMAGE_NAME = "share";
    private final static String SHARE_IMAGE_EXTENSION = ".jpg";
    public static Uri saveShareImage(Context context, Bitmap bitmap) {
        String imageDir = Environment.getExternalStorageDirectory() + File.separator +
                context.getResources().getString(R.string.app_name) + File.separator +
                SHARE_IMAGE_DIR + File.separator;
        File bitmapDir = new File(imageDir);

        if (!bitmapDir.exists()) {
            bitmapDir.mkdirs();
        }

        File bitmapFile = new File (imageDir + SHARE_IMAGE_NAME + SHARE_IMAGE_EXTENSION);
        saveBitmap(bitmap, bitmapFile.getAbsolutePath(), false);

        return Uri.fromFile(bitmapFile);
    }

    public static Bitmap getBitmapByUri(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }

        Bitmap bitmap = null;
        try {
            InputStream input = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(input);
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap convertStringToIcon(String st)
    {
        // OutputStream out;
        Bitmap bitmap = null;
        try
        {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st,  Base64.URL_SAFE);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
//             bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
