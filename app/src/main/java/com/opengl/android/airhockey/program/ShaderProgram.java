package com.opengl.android.airhockey.program;

import android.content.Context;
import android.opengl.GLES20;

import com.opengl.android.airhockey.util.ShaderHelper;
import com.opengl.android.airhockey.util.TextResourceReader;

/**
 * Created by yutao on 2017/11/29.
 */

public class ShaderProgram {
    // uniform constants
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    // attribute constants
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    // shader program
    protected final int program;
    protected ShaderProgram(Context context, int vertexShaderResourceId, int fragShaderResourceId) {
        program = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFromResource(context,vertexShaderResourceId),
                TextResourceReader.readTextFileFromResource(context, fragShaderResourceId));
    }

    public void useProgram() {
        // Set the current OpenGl shader program to this program
        GLES20.glUseProgram(program);
    }
}
