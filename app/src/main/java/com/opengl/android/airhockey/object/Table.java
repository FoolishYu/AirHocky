package com.opengl.android.airhockey.object;

import android.opengl.GLES20;

import com.opengl.android.airhockey.data.VertexArray;
import com.opengl.android.airhockey.program.TextureShaderProgram;

import static com.opengl.android.airhockey.Constants.BYTE_PER_FLOAT;

/**
 * Created by yutao on 2017/11/29.
 */

public class Table {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXUTRE_COORDINATE_COMPONENT_COUNT = 2;
    private static final int STRIDE =
            (POSITION_COMPONENT_COUNT + TEXUTRE_COORDINATE_COMPONENT_COUNT) * BYTE_PER_FLOAT;

    private static final float[] VERTEX_DATA = {
            // order of coordinates: X, Y, S, T

            // Triangle Fan
               0f,    0f, 0.5f, 0.5f,
            -0.5f, -0.8f,   0f, 0.9f,
             0.5f, -0.8f,   1f, 0.9f,
             0.5f,  0.8f,   1f, 0.1f,
            -0.5f,  0.8f,   0f, 0.1f,
            -0.5f, -0.8f,   0f, 0.9f
    };
    private final VertexArray vertexArray;

    public Table() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(TextureShaderProgram program) {
        vertexArray.setVertexAttribPointer(
                0,
                program.getPositionAttribLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE
        );
        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                program.getTextureCoordinateLoaction(),
                TEXUTRE_COORDINATE_COMPONENT_COUNT,
                STRIDE
        );
    }

    public void draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
    }
}
