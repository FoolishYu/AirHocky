package com.opengl.android.airhockey;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.opengl.android.airhockey.util.LoggerConfig;
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
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private final Context context;
    private final FloatBuffer vertexData;
    private int program;
    public AirHockeyRenderer(Context context) {
        this.context = context;
        float[] tableVertexWithTriangles = {
                // triangle 1
                0f, 0f,
                9f, 14f,
                0f, 14f,
                // triangle 2
                0f, 0f,
                9f, 0f,
                9f, 14f,

                // line 1
                0f, 7f,
                9f, 7f,

                // Mallets
                4.5f, 2f,
                4.5f, 12f
        };
        vertexData = ByteBuffer.allocateDirect(tableVertexWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.4f, 0.0f, 0.0f, 0.0f);
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
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
}
