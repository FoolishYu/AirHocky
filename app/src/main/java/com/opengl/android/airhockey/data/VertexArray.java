package com.opengl.android.airhockey.data;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static com.opengl.android.airhockey.Constants.BYTE_PER_FLOAT;

/**
 * Created by yutao on 2017/11/29.
 */

public class VertexArray {
    private final FloatBuffer floatBuffer;

    public VertexArray(float[] vertexData) {
        floatBuffer = ByteBuffer.allocateDirect(vertexData.length * BYTE_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
    }

    public void setVertexAttribPointer(int dataOffset, int attribLocation,
                                        int componentCount, int stride) {
        floatBuffer.position(dataOffset);
        GLES20.glVertexAttribPointer(attribLocation, componentCount, GLES20.GL_FLOAT,
                        false, stride, floatBuffer);
        GLES20.glEnableVertexAttribArray(attribLocation);
        floatBuffer.position(0);
    }
}
