package com.opengl.android.airhockey.program;

import android.content.Context;
import android.opengl.GLES20;

import com.opengl.android.airhockey.R;

/**
 * Created by yutao on 2017/11/29.
 */

public class TextureShaderProgram extends ShaderProgram{
    // Uniform location
    private final int uMatrixLocation;
    private final int uTextureLocation;

    // Attribute locations
    private final int aPositionLocation;
    private final int aTextureCoordinatesLocation;

    public TextureShaderProgram(Context context) {
        super(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        uTextureLocation = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);

        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aTextureCoordinatesLocation = GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    public void setUniforms(float[] matrix, int textureId) {
        // Pass the matrix into the shader program
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        // Set the active texture unit to texture unit 0
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // Bind the texture to this unit
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        // Tell the texture uniform sampler to use this texture in the shader by
        // telling it to read from texture unit 0
        GLES20.glUniform1i(uTextureLocation, 0);
    }

    public int getTextureCoordinateLoaction() {
        return aTextureCoordinatesLocation;
    }

    public int getPositionAttribLocation() {
        return aPositionLocation;
    }
}
