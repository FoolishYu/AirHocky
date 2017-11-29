package com.opengl.android.airhockey.program;

import android.content.Context;
import android.opengl.GLES20;

import com.opengl.android.airhockey.R;

/**
 * Created by yutao on 2017/11/29.
 */

public class ColorShaderProgram extends ShaderProgram{
    // uniform location
    private final int uMatrixLocation;
    // attribute location
    private final int aPositionLocation;
    private final int aColorLocation;

    public ColorShaderProgram(Context context) {
        super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
    }

    public void setUniformMatrix(float[] matrix) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    public int getPositionAttribLocation() {
        return aPositionLocation;
    }

    public int getColorAttribLocation() {
        return aColorLocation;
    }
}
