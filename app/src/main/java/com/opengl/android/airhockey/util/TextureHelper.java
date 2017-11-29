package com.opengl.android.airhockey.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

/**
 * Created by yutao on 2017/11/29.
 */

public class TextureHelper {
    private static final String TAG = "TextureHelper";
    public static int loadTexture(Context context, int resId) {
        final int[] textureObjIds = new int[1];
        GLES20.glGenTextures(1, textureObjIds, 0);
        if(textureObjIds[0] == 0) {
            if(LoggerConfig.LOG_ON) {
                Log.w(TAG, "Could not generate a new OpenGL Texture Object");
            }
            return 0;
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        final Bitmap bitmap = BitmapFactory.decodeResource(
                context.getResources(), resId, options);
        if(bitmap == null) {
            if(LoggerConfig.LOG_ON) {
                Log.w(TAG, "Resource ID " + resId + " could not be decoded");
            }
            return 0;
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjIds[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        return textureObjIds[0];
    }
}
