package com.opengl.android.airhockey.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by yutao on 2017/10/31.
 */

public class TextResourceReader {
    public static String readTextFileFromResource(Context context, int resId) {
        StringBuilder body = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(resId);
            InputStreamReader isReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(isReader);
            String nextLine;
            while((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            //e.printStackTrace();
            throw new RuntimeException(
                    "Could not open resource:" + resId, e);
        } catch (Resources.NotFoundException e) {
            //e.printStackTrace();
            throw new RuntimeException(
                    "Resource not found:" + resId, e);
        }
        return body.toString();
    }
}
