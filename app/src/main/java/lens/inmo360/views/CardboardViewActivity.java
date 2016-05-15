package lens.inmo360.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Log;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import java.util.Random;
import javax.microedition.khronos.egl.EGLConfig;

import lens.inmo360.R;
import lens.inmo360.cardboard.CardboardOverlayView;
import lens.inmo360.cardboard.UVSphere;

import static android.opengl.GLES20.glViewport;


public class CardboardViewActivity extends CardboardActivity implements CardboardView.StereoRenderer {

    final int TEXTURE_SHELL_RADIUS = 2;
    /** Number of sphere polygon partitions for photo, which must be an even number */
    final int SHELL_DIVIDES = 60;
    private CardboardView mCardboardView;
    private final String VSHADER_SRC =
            "attribute vec4 aPosition;\n" +
                    "attribute vec2 aUV;\n" +
                    "uniform mat4 uProjection;\n" +
                    "uniform mat4 uView;\n" +
                    "uniform mat4 uModel;\n" +
                    "varying vec2 vUV;\n" +
                    "void main() {\n" +
                    "  gl_Position = uProjection * uView * uModel * aPosition;\n" +
                    "  vUV = aUV;\n" +
                    "}\n";

    private final String FSHADER_SRC =
            "precision mediump float;\n" +
                    "varying vec2 vUV;\n" +
                    "uniform sampler2D uTex;\n" +
                    "void main() {\n" +
                    "  gl_FragColor = texture2D(uTex, vUV);\n" +
                    "}\n";

    private UVSphere mEastShell = null;
    private UVSphere mWestShell = null;
    private Bitmap mTexture;
    private boolean mTextureUpdate = false;
    private float mCameraPosX = 0.0f;
    private float mCameraPosY = 0.0f;
    private float mCameraPosZ = 0.0f;
    private int score = 0;
    private CardboardOverlayView overlayView;
    private float mCameraDirectionX = 0.0f;
    private float mCameraDirectionY = 0.0f;
    private float mCameraDirectionZ = 1.0f;
    private final float[] mViewProjectionMatrix = new float[16];
    private float[] mView = new float[16];
    private int[] mTextures = new int[2];
    private int mPositionHandle;
    private int mProjectionMatrixHandle;
    private int mViewMatrixHandle;
    private int mUVHandle;
    private int mTexHandle;
    private int mModelMatrixHandle;
    private int[] mResourceId = {R.drawable.photo_sphere_1, R.drawable.photo_sphere_2, R.drawable.photo_sphere_3,R.drawable.foto4,R.drawable.foto5,R.drawable.foto6,R.drawable.foto7,R.drawable.foto8,R.drawable.foto9};
    private final float[] mProjectionMatrix2 = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];
    private boolean mIsCardboardTriggered;
    private int mCurrentPhotoPos = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cardboard);
        mCardboardView = (CardboardView) findViewById(R.id.cardboard_view);
        overlayView = (CardboardOverlayView) findViewById(R.id.overlay);
        mCardboardView.setRenderer(this);
        setCardboardView(mCardboardView);
        overlayView.show3DToast("Mira a tu alrededor!");
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;    // No pre-scaling

        // Read in the resource

        Bitmap thumbnail = BitmapFactory.decodeResource(this.getResources(), getPhotoIndex(), options);
/*
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos2);
        byte[] thumbnailImage = baos2.toByteArray();
        ByteArrayInputStream inputStreamThumbnail = new ByteArrayInputStream(thumbnailImage);
        Drawable thumbnail2 = BitmapDrawable.createFromStream(inputStreamThumbnail, null);
*/
        setTexture(thumbnail);

    }



    @Override
    public void onNewFrame(HeadTransform headTransform) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.setIdentityM(mViewMatrix, 0);

        Matrix.setLookAtM(mViewMatrix, 0, mCameraPosX, mCameraPosY, mCameraPosZ, mCameraDirectionX, mCameraDirectionY, mCameraDirectionZ, 0.0f, 1.0f, 0.0f);
        if (mTextureUpdate && null != mTexture && !mTexture.isRecycled()) {
            Log.d("", "load texture1");
            loadTexture(mTexture);
            mTextureUpdate = false;
        }

        GLES20.glUniformMatrix4fv(mModelMatrixHandle, 1, false, mModelMatrix, 0);
        GLES20.glUniformMatrix4fv(mProjectionMatrixHandle, 1, false, mViewProjectionMatrix, 0);
        GLES20.glUniformMatrix4fv(mViewMatrixHandle, 1, false, mViewMatrix, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);
        GLES20.glUniform1i(mTexHandle, 0);

        mEastShell.draw(mPositionHandle, mUVHandle);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[1]);
        GLES20.glUniform1i(mTexHandle, 0);

        mWestShell.draw(mPositionHandle, mUVHandle);

    }

    @Override
    public void onDrawEye(Eye eye) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        /** Camera should move based on the user movement **/
        Matrix.multiplyMM(mView, 0, eye.getEyeView(), 0, mViewMatrix, 0);
        /** setting the view projection matrix **/
        Matrix.multiplyMM(mViewProjectionMatrix, 0, mProjectionMatrix2, 0, mView, 0);

        /** Drawing the sphere  and apply the projection to it**/

        if (mTextureUpdate && null != mTexture && !mTexture.isRecycled()) {
            Log.d("", "load texture1");
            loadTexture(mTexture);
            mTextureUpdate = false;
        }


        GLES20.glUniformMatrix4fv(mModelMatrixHandle, 1, false, mModelMatrix, 0);
        GLES20.glUniformMatrix4fv(mProjectionMatrixHandle, 1, false, mViewProjectionMatrix, 0);
        GLES20.glUniformMatrix4fv(mViewMatrixHandle, 1, false, mViewMatrix, 0);


        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);
        GLES20.glUniform1i(mTexHandle, 0);

        mEastShell.draw(mPositionHandle, mUVHandle);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[1]);
        GLES20.glUniform1i(mTexHandle, 0);

        mWestShell.draw(mPositionHandle, mUVHandle);

        if (mIsCardboardTriggered) {
            mIsCardboardTriggered = false;
            resetTexture();
        }
    }

    @Override
    public void onFinishFrame(Viewport viewport) {

    }

    @Override
    public void onCardboardTrigger() {
        /* Flag to sync with onDrawEye */
        mIsCardboardTriggered = true;
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(100);
        overlayView.show3DToast("Dimensiones: " + randomInt + " " + "mm2");
    }

    @Override
    public void onSurfaceChanged(final int width, final int height) {

        glViewport(0, 0, width, height);
        /** Setting the projection Matrix for the view **/
        Matrix.perspectiveM(mProjectionMatrix2,0, 100, (float) width
                / (float) height, 1f, 10f);
    }

    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {

        int vShader;
        int fShader;
        int program;

        vShader = loadShader(GLES20.GL_VERTEX_SHADER, VSHADER_SRC);
        fShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FSHADER_SRC);

        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vShader);
        GLES20.glAttachShader(program, fShader);
        GLES20.glLinkProgram(program);

        GLES20.glUseProgram(program);

        mPositionHandle = GLES20.glGetAttribLocation(program, "aPosition");
        mUVHandle = GLES20.glGetAttribLocation(program, "aUV");
        mProjectionMatrixHandle = GLES20.glGetUniformLocation(program, "uProjection");
        mViewMatrixHandle = GLES20.glGetUniformLocation(program, "uView");
        mTexHandle = GLES20.glGetUniformLocation(program, "uTex");
        mModelMatrixHandle = GLES20.glGetUniformLocation(program, "uModel");

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        mEastShell = new UVSphere(TEXTURE_SHELL_RADIUS, SHELL_DIVIDES, true);
        mWestShell = new UVSphere(TEXTURE_SHELL_RADIUS, SHELL_DIVIDES, false);
    }

    @Override
    public void onRendererShutdown() {

    }

    public void setTexture(Bitmap texture) {
        mTexture = texture;
        mTextureUpdate = true;
        return;
    }

    public void loadTexture(final Bitmap texture) {

        final Bitmap bitmap = texture;
        int dividedWidth = bitmap.getWidth() / 2;

        GLES20.glGenTextures(2, mTextures, 0);

        for (int textureIndex = 0; textureIndex < 2; textureIndex++) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[textureIndex]);

            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            Bitmap dividedBitmap = Bitmap.createBitmap(bitmap, (dividedWidth * textureIndex), 0, dividedWidth, bitmap.getHeight());

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, dividedBitmap, 0);
            dividedBitmap.recycle();
        }

        return;
    }

    private int getPhotoIndex() {
        return mResourceId[mCurrentPhotoPos++ % mResourceId.length];
    }

    private void resetTexture() {
        GLES20.glDeleteTextures(mTextures.length, mTextures, 0);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap thumbnail = BitmapFactory.decodeResource(this.getResources(), getPhotoIndex(),options);
        mTexture = thumbnail;
        loadTexture(mTexture);
    }

    private int loadShader(int type, String shaderCode){

        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
