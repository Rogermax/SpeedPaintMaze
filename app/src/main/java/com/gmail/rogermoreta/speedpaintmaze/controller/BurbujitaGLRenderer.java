package com.gmail.rogermoreta.speedpaintmaze.controller;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.gmail.rogermoreta.speedpaintmaze.R;
import com.gmail.rogermoreta.speedpaintmaze.enums.TipoCasilla;
import com.gmail.rogermoreta.speedpaintmaze.javaandroid.Trace;
import com.gmail.rogermoreta.speedpaintmaze.model.Bullet;
import com.gmail.rogermoreta.speedpaintmaze.model.BurbujitaMap;
import com.gmail.rogermoreta.speedpaintmaze.model.Casilla;
import com.gmail.rogermoreta.speedpaintmaze.model.Enemy;
import com.gmail.rogermoreta.speedpaintmaze.model.Turret;
import com.gmail.rogermoreta.speedpaintmaze.openGLCommons.RawResourceReader;
import com.gmail.rogermoreta.speedpaintmaze.openGLCommons.ShaderHelper;
import com.gmail.rogermoreta.speedpaintmaze.openGLCommons.TextureHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class BurbujitaGLRenderer implements GLSurfaceView.Renderer {

    private final Context mActivityContext;
    private long lasTtime;

    /**
     * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
     * of being located at the center of the universe) to world space.
     */
    private float[] mModelMatrix = new float[16];

    /**
     * Store the view matrix. This can be thought of as our camera. This matrix transforms world space to eye space;
     * it positions things relative to our eye.
     */
    private float[] mViewMatrix = new float[16];

    /** Store the projection matrix. This is used to project the scene onto a 2D viewport. */
    private float[] mProjectionMatrix = new float[16];

    /** Allocate storage for the final combined matrix. This will be passed into the shader program. */
    private float[] mMVPMatrix = new float[16];

    /**
     * Stores a copy of the model matrix specifically for the light position.
     */
    //private float[] mLightModelMatrix = new float[16];

    /** Store our model data in a float buffer. */
    private FloatBuffer mQuadPositions;
    private FloatBuffer mQuadColors;
    private FloatBuffer mQuadNormals;
    private FloatBuffer mQuadTextureCoordinates;

    /** This will be used to pass in the transformation matrix. */
    @SuppressWarnings("FieldCanBeLocal")
    private int mMVPMatrixHandle;

    /** This will be used to pass in the modelview matrix. */
    @SuppressWarnings("FieldCanBeLocal")
    private int mMVMatrixHandle;

    /** This will be used to pass in the light position. */
    //private int mLightPosHandle;

    /** This will be used to pass in the texture. */
    @SuppressWarnings("FieldCanBeLocal")
    private int mTextureUniformHandle;

    /** This will be used to pass in model position information. */
    @SuppressWarnings("FieldCanBeLocal")
    private int mPositionHandle;

    /** This will be used to pass in model color information. */
    @SuppressWarnings("FieldCanBeLocal")
    private int mColorHandle;

    /** This will be used to pass in model normal information. */
    @SuppressWarnings("FieldCanBeLocal")
    private int mNormalHandle;

    /** This will be used to pass in model texture coordinate information. */
    @SuppressWarnings("FieldCanBeLocal")
    private int mTextureCoordinateHandle;

    /** How many bytes per float. */
    @SuppressWarnings("FieldCanBeLocal")
    private final int mBytesPerFloat = 4;

    /** Size of the position data in elements. */
    @SuppressWarnings("FieldCanBeLocal")
    private final int mPositionDataSize = 3;

    /** Size of the color data in elements. */
    @SuppressWarnings("FieldCanBeLocal")
    private final int mColorDataSize = 4;

    /** Size of the normal data in elements. */
    @SuppressWarnings("FieldCanBeLocal")
    private final int mNormalDataSize = 3;

    /** Size of the texture coordinate data in elements. */
    @SuppressWarnings("FieldCanBeLocal")
    private final int mTextureCoordinateDataSize = 2;

    /** Used to hold a light centered on the origin in model space. We need a 4th coordinate so we can get translations to work when
     *  we multiply this by our transformation matrices. */
    //private final float[] mLightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f};

    /** Used to hold the current position of the light in world space (after transformation via model matrix). */
    //private final float[] mLightPosInWorldSpace = new float[4];

    /** Used to hold the transformed position of the light in eye space (after transformation via modelview matrix) */
    //private final float[] mLightPosInEyeSpace = new float[4];

    /** This is a handle to our quad shading program. */
    private int mProgramHandle;

    /** This is a handle to our light point program. */
    private int mPointProgramHandle;

    /** This is a handle to our texture data. */
    private int[] textures;
    private BurbujitaControllerOpenGL burbujitaControllerOpenGL;
    private MainManager MM = MainManager.getInstance();
    private int i = 0;
    @SuppressWarnings("FieldCanBeLocal")
    private float[] quadPositionData;
    @SuppressWarnings("FieldCanBeLocal")
    private float[] quadColorData;
    @SuppressWarnings("FieldCanBeLocal")
    private float[] quadNormalData;
    @SuppressWarnings("FieldCanBeLocal")
    private float[] quadTextureCoordinateData;
    @SuppressWarnings("FieldCanBeLocal")
    private BurbujitaMap burbujitaMap;

    /**
     * Initialize the model data.
     */
    public BurbujitaGLRenderer(final Context activityContext)
    {
        lasTtime = SystemClock.uptimeMillis();
        mActivityContext = activityContext;
        burbujitaControllerOpenGL = MM.getBurbujitaControllerOpenGL();
    }

    protected String getVertexShader()
    {
        return RawResourceReader.readTextFileFromRawResource(mActivityContext, R.raw.per_pixel_vertex_shader);
    }

    protected String getFragmentShader()
    {
        return RawResourceReader.readTextFileFromRawResource(mActivityContext, R.raw.per_pixel_fragment_shader);
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config)
    {
        lasTtime = SystemClock.uptimeMillis();
        burbujitaControllerOpenGL = MM.getBurbujitaControllerOpenGL();
        // Set the background clear color to black.
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Use culling to remove back faces.
        GLES30.glEnable(GLES30.GL_CULL_FACE);

        // Enable depth testing
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);


        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
        // The below glEnable() call is a holdover from OpenGL ES 1, and is not needed in OpenGL ES 2.
        // Enable texture mapping
        // GLES30.glEnable(GLES30.GL_TEXTURE_2D);

        // Position the eye in front of the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 10f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = 0.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        final String vertexShader = getVertexShader();
        final String fragmentShader = getFragmentShader();

        final int vertexShaderHandle = ShaderHelper.compileShader(GLES30.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = ShaderHelper.compileShader(GLES30.GL_FRAGMENT_SHADER, fragmentShader);

        mProgramHandle = ShaderHelper.createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
                new String[]{"a_Position", "a_Color", "a_Normal", "a_TexCoordinate"});

        // Define a simple shader program for our point.
        final String pointVertexShader = RawResourceReader.readTextFileFromRawResource(mActivityContext, R.raw.point_vertex_shader);
        final String pointFragmentShader = RawResourceReader.readTextFileFromRawResource(mActivityContext, R.raw.point_fragment_shader);

        final int pointVertexShaderHandle = ShaderHelper.compileShader(GLES30.GL_VERTEX_SHADER, pointVertexShader);
        final int pointFragmentShaderHandle = ShaderHelper.compileShader(GLES30.GL_FRAGMENT_SHADER, pointFragmentShader);
        mPointProgramHandle = ShaderHelper.createAndLinkProgram(pointVertexShaderHandle, pointFragmentShaderHandle, new String[]{"a_Position"});

        textures = new int[5];
        // Load the texture
        textures[0] = TextureHelper.loadTexture(mActivityContext, R.drawable.texturemap);
        textures[1] = TextureHelper.loadTexture(mActivityContext, R.drawable.textureturrets);
        textures[2] = TextureHelper.loadTexture(mActivityContext, R.drawable.texturebichos);
        textures[3] = TextureHelper.loadTexture(mActivityContext, R.drawable.texturebullets);
        textures[4] = TextureHelper.loadTexture(mActivityContext, R.drawable.textureinterface);

        //GLES30.glGenTextures(4, textures, 0);

    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height)
    {
        lasTtime = SystemClock.uptimeMillis();
        burbujitaControllerOpenGL = MM.getBurbujitaControllerOpenGL();
        MM.burbujitaOpenGLViewChanged(width, height);

        // Set the OpenGL viewport to the same size as the surface.
        GLES30.glViewport(0, 0, width, height);

        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        final float ratio;
        final float left;
        final float right;
        final float bottom;
        final float top;

        if (width > height){
            ratio = (float) width / height;
            left = -ratio;
            right = ratio;
            bottom = -1.0f;
            top = 1.0f;
        }
        else {
            ratio = (float) height / width;
            left = -1.0f;
            right = 1.0f;
            bottom = -ratio;
            top = ratio;
        }

        final float near = 10.0f;
        final float far = 100.0f;

        Matrix.orthoM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    @SuppressWarnings("unused")
    private void trace(String str) {
        Trace.write(" BurbujitaGLRenderer::" + str);
    }

    @Override
    public void onDrawFrame(GL10 glUnused)
    {
        burbujitaControllerOpenGL.update();
        limpiamosPantalla();
        traceamosFPS();
        lasTtime = SystemClock.uptimeMillis();
        burbujitaMap = burbujitaControllerOpenGL.getBurbujitaMap();

        int numFilas = burbujitaMap.getMapHeight();
        int numColumnas = burbujitaMap.getMapWidth();
        llenaStructuraConMapa(numFilas, numColumnas, -1f);
        drawStructure(numColumnas * numFilas, 0);


        //llenaStructuraConMapa(numFilas, numColumnas, -0.9f);
        //drawStructure(numColumnas * numFilas,1);


        ArrayList<Turret> listaTorretas = burbujitaMap.getTurrets();

        llenaStructuraConTurrets(listaTorretas, -0.9f);
        drawStructure(listaTorretas.size(),1);
        //drawTurrets(listaTorretas);

        ArrayList<Enemy> listaEnemigos = burbujitaMap.getEnemies();
        llenaStructuraConEnemies(listaEnemigos, -0.8f);
        drawStructure(listaEnemigos.size(),2);
        //drawEnemies(listaEnemigos);

        /*ArrayList<Bullet> listaBalas = burbujitaMap.getBullets();
        llenaStructuraConBullets(listaBalas, -0.7f);
        drawStructure(listaBalas.size(),3);
        //drawBullets(listaBalas);

        llenaStructuraConBullets(burbujitaControllerOpenGL.getInterface(), -0.6f);
        drawStructure(burbujitaControllerOpenGL.getInterface().getButtons().size()+1,4);
        //drawInterface(burbujitaControllerOpenGL.getInterface());
*/

    }


    private void traceamosFPS() {
        i = (i+1)%30;
        if (i == 0) {
            trace("tiempo entre draws: "+(SystemClock.uptimeMillis()-lasTtime));
        }
    }

    private void limpiamosPantalla() {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
    }

    @SuppressWarnings("unused")
    private void drawTurrets(ArrayList<Turret> listaTorretas) {
        int size = listaTorretas.size();
        //noinspection StatementWithEmptyBody
        for (int i = 0; i < size; i++) {

        }
    }

    private void llenaStructuraConMapa(int numFilas, int numColumnas, float prof) {
        ArrayList<Casilla> listaCasillas = burbujitaMap.getCasillaMap();
        quadPositionData = new float[6*3*numFilas*numColumnas];
        quadColorData = new float[6*4*numFilas*numColumnas];
        quadNormalData = new float[6*3*numFilas*numColumnas];
        quadTextureCoordinateData =  new float[6*2*numFilas*numColumnas];
        final float maxFilCol = Math.max(numColumnas, numFilas);
        float ancho = 2f/maxFilCol;
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                Casilla cas = listaCasillas.get(i*numColumnas+j);
                if (cas.getTipoCasilla() == TipoCasilla.CESPED) {
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)] = 0f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+1] = 1f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+2] = 0.5f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+3] = 0.5f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+4] = 0f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+5] = 0.5f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+6] = 0f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+7] = 1f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+8] = 0.5f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+9] = 1f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+10] = 0.5f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+11] = 0.5f;
                }
                else {
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)] = 0.5f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+1] = 1f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+2] = 1f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+3] = 0.5f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+4] = 0.5f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+5] = 0.5f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+6] = 0.5f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+7] = 1f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+8] = 1f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+9] = 1f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+10] = 1f;
                    quadTextureCoordinateData[6*2*(i*numColumnas+j)+11] = 0.5f;
                }
                //primer triangulo
                //primer vertice
                quadPositionData[6*3*(i*numColumnas+j)] = j*2/maxFilCol-1;
                quadPositionData[6*3*(i*numColumnas+j)+1] = i*2/maxFilCol-1;
                quadPositionData[6*3*(i*numColumnas+j)+2] = prof;

                //segundo vertice
                quadPositionData[6*3*(i*numColumnas+j)+3] = j*2/maxFilCol-1+ancho;
                quadPositionData[6*3*(i*numColumnas+j)+4] = i*2/maxFilCol-1+ancho;
                quadPositionData[6*3*(i*numColumnas+j)+5] = prof;

                //tercer vertice
                quadPositionData[6*3*(i*numColumnas+j)+6] = j*2/maxFilCol-1;
                quadPositionData[6*3*(i*numColumnas+j)+7] = i*2/maxFilCol-1+ancho;
                quadPositionData[6*3*(i*numColumnas+j)+8] = prof;

                //segundo triangulo
                //segundo vertice
                quadPositionData[6*3*(i*numColumnas+j)+9] = j*2/maxFilCol-1;
                quadPositionData[6*3*(i*numColumnas+j)+10] = i*2/maxFilCol-1;
                quadPositionData[6*3*(i*numColumnas+j)+11] = prof;

                //cuarto vertice
                quadPositionData[6*3*(i*numColumnas+j)+12] = j*2/maxFilCol-1+ancho;
                quadPositionData[6*3*(i*numColumnas+j)+13] = i*2/maxFilCol-1;
                quadPositionData[6*3*(i*numColumnas+j)+14] = prof;

                //tercer vertice
                quadPositionData[6*3*(i*numColumnas+j)+15] = j*2/maxFilCol-1+ancho;
                quadPositionData[6*3*(i*numColumnas+j)+16] = i*2/maxFilCol-1+ancho;
                quadPositionData[6*3*(i*numColumnas+j)+17] = prof;

                for (int k = 0; k < 4*6; ++k) {
                    quadColorData[6*4*(i*numColumnas+j)+k] = 1f;
                }

                for (int k = 0; k < 6; ++k) {
                    quadNormalData[6*3*(i*numColumnas+j)+3*k] = 0f;
                    quadNormalData[6*3*(i*numColumnas+j)+3*k+1] = 0f;
                    quadNormalData[6*3*(i*numColumnas+j)+3*k+2] = 1f;
                }
            }
        }
        // Initialize the buffers.
        mQuadPositions = ByteBuffer.allocateDirect(quadPositionData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadPositions.put(quadPositionData).position(0);

        mQuadColors = ByteBuffer.allocateDirect(quadColorData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadColors.put(quadColorData).position(0);

        mQuadNormals = ByteBuffer.allocateDirect(quadNormalData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadNormals.put(quadNormalData).position(0);

        mQuadTextureCoordinates = ByteBuffer.allocateDirect(quadTextureCoordinateData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadTextureCoordinates.put(quadTextureCoordinateData).position(0);
    }


    private void llenaStructuraConTurrets(ArrayList<Turret> listaTorretas, float prof) {
        int size = listaTorretas.size();
        quadPositionData = new float[6*3*size];
        quadColorData = new float[6*4*size];
        quadNormalData = new float[6*3*size];
        quadTextureCoordinateData =  new float[6*2*size];
        float w = burbujitaMap.getMapWidth()*100;
        float h = burbujitaMap.getMapHeight()*100;
        final float maxFilCol = Math.max(w, h);
        float ancho = 2f/maxFilCol;
        for (int i = 0; i < size; i++) {
            Turret turret = listaTorretas.get(i);
            float x = turret.getX()/(burbujitaMap.getMapWidth()*100);
            float y = turret.getY()/(burbujitaMap.getMapHeight()*100);
            quadTextureCoordinateData[6*2*i] = 0.5f;
            quadTextureCoordinateData[6*2*i+1] = 1f;
            quadTextureCoordinateData[6*2*i+2] = 1f;
            quadTextureCoordinateData[6*2*i+3] = 0.5f;
            quadTextureCoordinateData[6*2*i+4] = 0.5f;
            quadTextureCoordinateData[6*2*i+5] = 0.5f;
            quadTextureCoordinateData[6*2*i+6] = 0.5f;
            quadTextureCoordinateData[6*2*i+7] = 1f;
            quadTextureCoordinateData[6*2*i+8] = 1f;
            quadTextureCoordinateData[6*2*i+9] = 1f;
            quadTextureCoordinateData[6*2*i+10] = 1f;
            quadTextureCoordinateData[6*2*i+11] = 0.5f;

            //primer triangulo
            //primer vertice
            quadPositionData[6*3*i] = y*2-1;
            quadPositionData[6*3*i+1] = x*2-1;
            quadPositionData[6*3*i+2] = prof;

            //segundo vertice
            quadPositionData[6*3*i+3] = y*2-1+ancho;
            quadPositionData[6*3*i+4] = x*2-1+ancho;
            quadPositionData[6*3*i+5] = prof;

            //tercer vertice
            quadPositionData[6*3*i+6] = y*2-1;
            quadPositionData[6*3*i+7] = x*2-1+ancho;
            quadPositionData[6*3*i+8] = prof;

            //segundo triangulo
            //segundo vertice
            quadPositionData[6*3*i+9] = y*2-1;
            quadPositionData[6*3*i+10] = x*2-1;
            quadPositionData[6*3*i+11] = prof;

            //cuarto vertice
            quadPositionData[6*3*i+12] = y*2-1+ancho;
            quadPositionData[6*3*i+13] = x*2-1;
            quadPositionData[6*3*i+14] = prof;

            //tercer vertice
            quadPositionData[6*3*i+15] = y*2-1+ancho;
            quadPositionData[6*3*i+16] = x*2-1+ancho;
            quadPositionData[6*3*i+17] = prof;

            for (int k = 0; k < 4*6; ++k) {
                quadColorData[6*4*i+k] = 1f;
            }

            for (int k = 0; k < 6; ++k) {
                quadNormalData[6*3*i+3*k] = 0f;
                quadNormalData[6*3*i+3*k+1] = 0f;
                quadNormalData[6*3*i+3*k+2] = 1f;
            }
        }
        // Initialize the buffers.
        mQuadPositions = ByteBuffer.allocateDirect(quadPositionData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadPositions.put(quadPositionData).position(0);

        mQuadColors = ByteBuffer.allocateDirect(quadColorData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadColors.put(quadColorData).position(0);

        mQuadNormals = ByteBuffer.allocateDirect(quadNormalData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadNormals.put(quadNormalData).position(0);

        mQuadTextureCoordinates = ByteBuffer.allocateDirect(quadTextureCoordinateData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadTextureCoordinates.put(quadTextureCoordinateData).position(0);
    }

    private void llenaStructuraConEnemies(ArrayList<Enemy> listaEnemigos, float prof) {
        int size = listaEnemigos.size();
        quadPositionData = new float[6*3*size];
        quadColorData = new float[6*4*size];
        quadNormalData = new float[6*3*size];
        quadTextureCoordinateData =  new float[6*2*size];
        float w = burbujitaMap.getMapWidth()*100;
        float h = burbujitaMap.getMapHeight()*100;
        final float maxFilCol = Math.max(w, h);
        float ancho = (2f*100f)/maxFilCol;
        for (int i = 0; i < size; i++) {
            Enemy enemy = listaEnemigos.get(i);
            float x = enemy.getX()/(burbujitaMap.getMapWidth()*100);
            float y = enemy.getY()/(burbujitaMap.getMapHeight()*100);
            quadTextureCoordinateData[6*2*i] = 0.5f;
            quadTextureCoordinateData[6*2*i+1] = 1f;
            quadTextureCoordinateData[6*2*i+2] = 1f;
            quadTextureCoordinateData[6*2*i+3] = 0.5f;
            quadTextureCoordinateData[6*2*i+4] = 0.5f;
            quadTextureCoordinateData[6*2*i+5] = 0.5f;
            quadTextureCoordinateData[6*2*i+6] = 0.5f;
            quadTextureCoordinateData[6*2*i+7] = 1f;
            quadTextureCoordinateData[6*2*i+8] = 1f;
            quadTextureCoordinateData[6*2*i+9] = 1f;
            quadTextureCoordinateData[6*2*i+10] = 1f;
            quadTextureCoordinateData[6*2*i+11] = 0.5f;

            //primer triangulo
            //primer vertice
            quadPositionData[6*3*i] = y*2-1;
            quadPositionData[6*3*i+1] = x*2-1;
            quadPositionData[6*3*i+2] = prof;

            //segundo vertice
            quadPositionData[6*3*i+3] = y*2-1+ancho;
            quadPositionData[6*3*i+4] = x*2-1+ancho;
            quadPositionData[6*3*i+5] = prof;

            //tercer vertice
            quadPositionData[6*3*i+6] = y*2-1;
            quadPositionData[6*3*i+7] = x*2-1+ancho;
            quadPositionData[6*3*i+8] = prof;

            //segundo triangulo
            //segundo vertice
            quadPositionData[6*3*i+9] = y*2-1;
            quadPositionData[6*3*i+10] = x*2-1;
            quadPositionData[6*3*i+11] = prof;

            //cuarto vertice
            quadPositionData[6*3*i+12] = y*2-1+ancho;
            quadPositionData[6*3*i+13] = x*2-1;
            quadPositionData[6*3*i+14] = prof;

            //tercer vertice
            quadPositionData[6*3*i+15] = y*2-1+ancho;
            quadPositionData[6*3*i+16] = x*2-1+ancho;
            quadPositionData[6*3*i+17] = prof;

            for (int k = 0; k < 4*6; ++k) {
                quadColorData[6*4*i+k] = 1f;
            }

            for (int k = 0; k < 6; ++k) {
                quadNormalData[6*3*i+3*k] = 0f;
                quadNormalData[6*3*i+3*k+1] = 0f;
                quadNormalData[6*3*i+3*k+2] = 1f;
            }
        }
        // Initialize the buffers.
        mQuadPositions = ByteBuffer.allocateDirect(quadPositionData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadPositions.put(quadPositionData).position(0);

        mQuadColors = ByteBuffer.allocateDirect(quadColorData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadColors.put(quadColorData).position(0);

        mQuadNormals = ByteBuffer.allocateDirect(quadNormalData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadNormals.put(quadNormalData).position(0);

        mQuadTextureCoordinates = ByteBuffer.allocateDirect(quadTextureCoordinateData.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadTextureCoordinates.put(quadTextureCoordinateData).position(0);
    }

    /**
     * Draws a quad.
     * @param numeroCasillas numero de casillas a pintar
     */
    private void drawStructure(int numeroCasillas, int numTexture)
    {

        // Set our per-vertex lighting program.
        GLES30.glUseProgram(mProgramHandle);

        // Set program handles for quad drawing.
        mMVPMatrixHandle = GLES30.glGetUniformLocation(mProgramHandle, "u_MVPMatrix");
        mMVMatrixHandle = GLES30.glGetUniformLocation(mProgramHandle, "u_MVMatrix");
        mTextureUniformHandle = GLES30.glGetUniformLocation(mProgramHandle, "u_Texture");
        mPositionHandle = GLES30.glGetAttribLocation(mProgramHandle, "a_Position");
        mColorHandle = GLES30.glGetAttribLocation(mProgramHandle, "a_Color");
        mNormalHandle = GLES30.glGetAttribLocation(mProgramHandle, "a_Normal");
        mTextureCoordinateHandle = GLES30.glGetAttribLocation(mProgramHandle, "a_TexCoordinate");

        // Set the active texture unit to texture unit 0.
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);

        // Bind the texture to this unit.
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textures[numTexture]);

        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES30.glUniform1i(mTextureUniformHandle, 0);

        Matrix.setIdentityM(mModelMatrix, 0);

        // Pass in the position information
        mQuadPositions.position(0);
        GLES30.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES30.GL_FLOAT, false, 0, mQuadPositions);
        GLES30.glEnableVertexAttribArray(mPositionHandle);

        // Pass in the color information
        mQuadColors.position(0);
        GLES30.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES30.GL_FLOAT, false, 0, mQuadColors);
        GLES30.glEnableVertexAttribArray(mColorHandle);

        // Pass in the normal information
        mQuadNormals.position(0);
        GLES30.glVertexAttribPointer(mNormalHandle, mNormalDataSize, GLES30.GL_FLOAT, false, 0, mQuadNormals);
        GLES30.glEnableVertexAttribArray(mNormalHandle);

        // Pass in the texture coordinate information
        mQuadTextureCoordinates.position(0);
        GLES30.glVertexAttribPointer(mTextureCoordinateHandle, mTextureCoordinateDataSize, GLES30.GL_FLOAT, false, 0, mQuadTextureCoordinates);

        GLES30.glEnableVertexAttribArray(mTextureCoordinateHandle);

        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        // (which currently contains model * view).
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);

        // Pass in the modelview matrix.
        GLES30.glUniformMatrix4fv(mMVMatrixHandle, 1, false, mMVPMatrix, 0);

        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        // (which now contains model * view * projection).
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

        // Pass in the combined matrix.
        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        // Pass in the light position in eye space.        
        // GLES30.glUniform3f(mLightPosHandle, mLightPosInEyeSpace[0], mLightPosInEyeSpace[1], mLightPosInEyeSpace[2]);

        // Draw the quad.
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 6*numeroCasillas); //6 vertices x numeroCasillas caras + 6 vertices * 2 caras
    }

    /**
     * Draws a point representing the position of the light.
     */
    //private void drawLight()
    //{
        //final int pointMVPMatrixHandle = GLES30.glGetUniformLocation(mPointProgramHandle, "u_MVPMatrix");
        //final int pointPositionHandle = GLES30.glGetAttribLocation(mPointProgramHandle, "a_Position");

        // Pass in the position.
        //GLES30.glVertexAttrib3f(pointPositionHandle, mLightPosInModelSpace[0], mLightPosInModelSpace[1], mLightPosInModelSpace[2]);

        // Since we are not using a buffer object, disable vertex arrays for this attribute.
        //GLES30.glDisableVertexAttribArray(pointPositionHandle);

        // Pass in the transformation matrix.
        //Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mLightModelMatrix, 0);
        //Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        //GLES30.glUniformMatrix4fv(pointMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        // Draw the point.
        //GLES30.glDrawArrays(GLES30.GL_POINTS, 0, 1);
    //}
}


