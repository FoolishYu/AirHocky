package com.opengl.android.airhockey.object;

import android.opengl.GLES20;

import com.opengl.android.airhockey.data.VertexArray;
import com.opengl.android.airhockey.program.ColorShaderProgram;

import static com.opengl.android.airhockey.Constants.BYTE_PER_FLOAT;

/**
 * Created by yutao on 2017/11/29.
 */

public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE =
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTE_PER_FLOAT;

    private static final float[] VERTEX_DATA = {
            // order of coordinate  X, Y, R, G, B
            0f, -0.4f, 0f, 0f, 1f,
            0f,  0.4f, 1f, 0f, 0f
    };
    private final VertexArray vertexArray;

    public Mallet() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram program) {
        vertexArray.setVertexAttribPointer(
                0,
                program.getPositionAttribLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE
        );
        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                program.getColorAttribLocation(),
                COLOR_COMPONENT_COUNT,
                STRIDE
        );
    }
    public void draw() {
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 2);
    }
}
