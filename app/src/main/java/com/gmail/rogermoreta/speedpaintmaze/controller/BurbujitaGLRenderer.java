package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.graphics.Bitmap;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.support.v4.content.ContextCompat;

import com.gmail.rogermoreta.speedpaintmaze.R;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class BurbujitaGLRenderer implements GLSurfaceView.Renderer {


    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    @SuppressWarnings("FieldCanBeLocal")
    private float cameraX = 0f;
    @SuppressWarnings("FieldCanBeLocal")
    private float cameraY = 0f;
    /** The texture pointer */
    @SuppressWarnings("unused")
    private static int[] textures = new int[1];
    private static int mProgram;
    private static MainManager MM;
    private static Bitmap bitmap;
    // Sampler location
    private int mBaseMapLoc;
    @SuppressWarnings("unused")
    private int mLightMapLoc;
    // Texture handle
    private int mBaseMapTexId;
    @SuppressWarnings("unused")
    private int mLightMapTexId;

    public BurbujitaGLRenderer() {
        MM = MainManager.getInstance();
        bitmap = Pintor.drawableToBitmap(ContextCompat.getDrawable(MM.getContext(), R.drawable.hierba1));
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Load the texture for the square

        final String vertexShaderCode =
                "attribute vec4 vPosition;" +
                        "uniform mat4 uMVPMatrix;" +
                        "void main() {" +
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "}";

        final String fragmentShaderCode =
                "precision mediump float;" +
                        "uniform vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vColor;" +
                        "}";

        int vertexShader = BurbujitaGLRenderer.loadShader(GLES30.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = BurbujitaGLRenderer.loadShader(GLES30.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES30.glCreateProgram();

        // add the vertex shader to program
        GLES30.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES30.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES30.glLinkProgram(mProgram);



        // Get the sampler locations
        mBaseMapLoc = GLES30.glGetUniformLocation ( mProgram, "s_baseMap" );
        //mLightMapLoc = GLES30.glGetUniformLocation ( mProgram, "s_lightMap" );

        // Load the texture images from 'assets'
        mBaseMapTexId = loadTexture();

        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);    //Black Background

    }

    @Override
    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        //cameraX += 0.1;
        //if (cameraX > 1) cameraX = -1;

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, cameraX, cameraY, -10, cameraX, cameraY, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //Pintor.drawOpenGLCasilla(mMVPMatrix, mProgram, new Casilla(TipoCasilla.CESPED, 0, 0));
        Pintor.drawOpenGLMap(mMVPMatrix, mProgram, MM.getBurbujitaMap(),mBaseMapTexId,mBaseMapLoc);

    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES30.glViewport(0, 0, width, height);

        float aspectRatio = (float) width / (float) height;
        float aspectRatio2 = (float) height / (float) width;

        if (aspectRatio > aspectRatio2) {
            Matrix.orthoM(mProjectionMatrix, 0, -aspectRatio, aspectRatio, -1, 1, -10, 10);
        }
        else {
            Matrix.orthoM(mProjectionMatrix, 0, -1, 1, -aspectRatio2, aspectRatio2, -10, 10);
        }

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        //Matrix.frustumM(mProjectionMatrix, 0, -aspectRatio, aspectRatio, -1, 1, 3, 7);
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES30.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);

        return shader;
    }

    ///
    //  Load texture from asset
    //
    private int loadTexture()
    {
        int[] textureId = new int[1];

        GLES30.glGenTextures ( 1, textureId, 0 );
        GLES30.glBindTexture ( GLES30.GL_TEXTURE_2D, textureId[0] );

        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);

        GLES30.glTexParameteri ( GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR );
        GLES30.glTexParameteri ( GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR );
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);

        return textureId[0];
    }

}