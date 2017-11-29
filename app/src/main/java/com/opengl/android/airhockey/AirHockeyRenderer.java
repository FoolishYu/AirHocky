package com.opengl.android.airhockey;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.opengl.android.airhockey.object.Mallet;
import com.opengl.android.airhockey.object.Table;
import com.opengl.android.airhockey.program.ColorShaderProgram;
import com.opengl.android.airhockey.program.TextureShaderProgram;
import com.opengl.android.airhockey.util.MatrixHelper;
import com.opengl.android.airhockey.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yutao on 2017/10/31.
 */

public class AirHockeyRenderer implements GLSurfaceView.Renderer {
    private final Context context;
    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Table table;
    private Mallet mallet;

    private TextureShaderProgram texShaderProgram;
    private ColorShaderProgram colorShaderProgram;
    private int texture;

    public AirHockeyRenderer(Context context) {
        this.context = context;
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        table = new Table();
        mallet = new Mallet();

        texShaderProgram = new TextureShaderProgram(context);
        colorShaderProgram = new ColorShaderProgram(context);

        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface);
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
        // Clear the rendering surface
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        // Draw the table
        texShaderProgram.useProgram();
        texShaderProgram.setUniforms(projectionMatrix, texture);
        table.bindData(texShaderProgram);
        table.draw();

        // Draw the mallet
        colorShaderProgram.useProgram();
        colorShaderProgram.setUniformMatrix(projectionMatrix);
        mallet.bindData(colorShaderProgram);
        mallet.draw();
    }
}
