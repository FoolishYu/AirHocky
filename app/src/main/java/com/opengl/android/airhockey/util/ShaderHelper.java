package com.opengl.android.airhockey.util;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by yutao on 17-11-24.
 */
public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }

    public static int linkProgram(int vertexShaderId, int fragShaderId) {
        final int programObjectId = GLES20.glCreateProgram();
        if(programObjectId == 0) {
            if(LoggerConfig.LOG_ON) {
                Log.w(TAG, "Could not create new program");
            }
            return 0;
        }
        GLES20.glAttachShader(programObjectId, vertexShaderId);
        GLES20.glAttachShader(programObjectId, fragShaderId);
        GLES20.glLinkProgram(programObjectId);

        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if(LoggerConfig.LOG_ON) {
            Log.d(TAG, "Results of linking program:\n"
                    + GLES20.glGetProgramInfoLog(programObjectId));
        }
        if(linkStatus[0] == 0) {
            GLES20.glDeleteProgram(programObjectId);
            if(LoggerConfig.LOG_ON) {
                Log.w(TAG, "Linking program failed");
            }
            return 0;
        }
        return programObjectId;
    }

    private static int compileShader(int shaderType, String shaderCode) {
        final int shaderObjId = GLES20.glCreateShader(shaderType);
        if(shaderObjId == 0) {
            if(LoggerConfig.LOG_ON)
                Log.w(TAG, "Could not create new shader");
            return 0;
        }

        GLES20.glShaderSource(shaderObjId, shaderCode);
        GLES20.glCompileShader(shaderObjId);

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        if(compileStatus[0] == 0) {
            GLES20.glDeleteShader(shaderObjId);
            if(LoggerConfig.LOG_ON) {
                Log.w(TAG, "Compilation of shader failed");
            }

            return 0;
        }

        if(LoggerConfig.LOG_ON) {
            Log.v(TAG, "Resule of compiling source :" + shaderCode + "\n"
                    +GLES20.glGetShaderInfoLog(shaderObjId));
        }

        return shaderObjId;

    }
}
