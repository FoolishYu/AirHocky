package com.opengl.android.airhockey;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.opengl.android.airhockey.util.LoggerConfig;
import com.opengl.android.airhockey.util.MatrixHelper;
import com.opengl.android.airhockey.util.ShaderHelper;
import com.opengl.android.airhockey.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yutao on 2017/10/31.
 */

public class AirHockeyRenderer implements GLSurfaceView.Renderer {
    private static final int POSITION_COMPONENT_COUNT = 4;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE =
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private static final String U_MATRIX = "u_Matrix";
    private static final String A_POSITION = "a_Position";
    private static final String A_COLOR = "a_Color";
    private int aPositionLocation;
    private int aColorLocation;
    private final Context context;
    private final FloatBuffer vertexData;
    private int program;
    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private int uMatrixLocation;


    public AirHockeyRenderer(Context context) {
        this.context = context;
        float[] tableVertexWithTriangles = {
                // Order of coordinates X, Y, Z, W, R, G, B
                // triangle fan
                0f,     0f,   0f, 1.5f,  1f,   1f,   1f,
                -0.5f, -0.8f, 0f,  1f, 0.7f, 0.7f, 0.7f,
                0.5f,  -0.8f, 0f,  1f, 0.7f, 0.7f, 0.7f,
                0.5f,   0.8f, 0f,  2f, 0.7f, 0.7f, 0.7f,
                -0.5f,  0.8f, 0f,  2f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.8f, 0f,  1f, 0.7f, 0.7f, 0.7f,

                // line 1
                -0.5f,    0f, 0f,  1.5f, 1f,   0f,   0f,
                0.5f,     0f, 0f,  1.5f, 1f,   0f,   0f,

                // Mallets
                0f,  -0.4f,   0f,  1.25f, 0f,   0f,   1f,
                0f,   0.4f,   0f,  1.75f, 1f,   0f,   0f
        };
        vertexData = ByteBuffer.allocateDirect(tableVertexWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVertexWithTriangles);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context,
                R.raw.simple_vertex_shader);
        String fragShaderSource = TextResourceReader.readTextFileFromResource(context,
                R.raw.simple_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragShader = ShaderHelper.compileFragmentShader(fragShaderSource);
        program = ShaderHelper.linkProgram(vertexShader, fragShader);
        if(LoggerConfig.LOG_ON) {
            ShaderHelper.validateProgram(program);
        }
        GLES20.glUseProgram(program);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

        vertexData.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);

        vertexData.position(POSITION_COMPONENT_COUNT);
        GLES20.glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexData);
        GLES20.glEnableVertexAttribArray(aColorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float)width / (float)height, 1f, 10f);
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -3f);
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);

        final float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0);
        // draw table
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
        // draw lines
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);
        // draw mallet blue
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);
        // draw mallet red
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);
    }
}
