package com.example.gymvision.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.gymvision.R;
import com.example.gymvision.overlay.CameraConnectionFragment;
import com.example.gymvision.overlay.Overlay;
import com.shinelw.library.ColorArcProgressBar;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CameraActivity extends AppCompatActivity implements OnImageAvailableListener {
    private static final String NAME = CameraActivity.class.getSimpleName();
    private static int maxWidth = 500;
    public static final String EXERCISE_KEY = "exercise_key";


    private Handler handler;
    private HandlerThread handlerThread;
    private static final Size previewSize = new Size(1280, 720);
    protected String cameraid;

   @RequiresApi(api = Build.VERSION_CODES.M)
   @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(null);


        setContentView(R.layout.activity_camera);


       if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {

           requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);

           fragment_set();


       }else{fragment_set();}

    }

    @Override
    public synchronized void onResume() {

        super.onResume();

        handlerThread = new HandlerThread("inf");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    @Override
    public synchronized void onPause() {

        if (!isFinishing()) {

            finish();
        }

        handlerThread.quitSafely();
        try {
            handlerThread.join();
            handlerThread = null;
            handler = null;
        } catch (final InterruptedException e) {
        }

        super.onPause();
    }


    protected synchronized void background(final Runnable r) {
        if (handler != null) {
            handler.post(r);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void fragment_set() {
        cameraid = chooseCamera();
        final CameraConnectionFragment fragment = CameraConnectionFragment.newInstance(CameraActivity.this::preview, this, R.layout.camera_connection_fragment,   getDesiredPreviewFrameSize());

        fragment.setCamera(cameraid);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.camera, fragment)
                .commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private String chooseCamera() {
        final CameraManager camera_manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            assert camera_manager != null;
            for (final String cameraId : camera_manager.getCameraIdList()) {
                final CameraCharacteristics characteristics = camera_manager.getCameraCharacteristics(cameraId);

                final Integer cameraOrientation = characteristics.get(CameraCharacteristics.LENS_FACING); //TODO: ADD FRONT FACING CAMERA CODE
                if (cameraOrientation != null && cameraOrientation == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }

                final StreamConfigurationMap map =
                        characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                if (map == null) {
                    continue;
                }

                return cameraId;
            }
        } catch (CameraAccessException e) {

        }
        return null;
    }


    public void overlay(final Overlay.DrawCallback callback) {
        final Overlay overlay = findViewById(R.id.overlay);
        if (overlay != null) {
            overlay.setCallback(callback);
        }
    }

    protected abstract void preview(final Size size, final Size camerasize, final int rotation);
    protected Size getDesiredPreviewFrameSize() {
        return previewSize;
    }
}

abstract class LiveActivity<ImageView> extends CameraActivity implements ImageReader.OnImageAvailableListener {

    private AtomicBoolean compute = new AtomicBoolean(false);

    public String exerciseName = "";
    TextView title;
    //TextView timer;
    ColorArcProgressBar timer;
    Button btnStart;
    boolean running = false;
    boolean overlay = true;
    ImageButton overlay_button;
    public static Integer COUNT = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFritz();
        setupPredictor();
    }


    public void preview(final Size size, final Size camerasize, final int rotation) {

        overlay_button = findViewById(R.id.overlay_switch);
        overlay_button.setOnClickListener(v -> {
            overlay(
                    canvas -> showResult(canvas, camerasize));
        });

    }

    public void onImageAvailable(final ImageReader reader) {
        Image image = reader.acquireLatestImage();


        if (image == null) {
            return;
        }

        if (!compute.compareAndSet(false, true)) {
            image.close();
            return;
        }

        setupImageForPrediction(image);
        image.close();
        background(
                () -> {
                    runInference();

                    final Overlay overlay = findViewById(R.id.overlay);
                    if (overlay != null) {
                        overlay.postInvalidate();
                    }
                    compute.set(false);
                });
    }

    protected abstract void initializeFritz();
    protected abstract void setupPredictor();
    protected abstract void setupImageForPrediction(Image image);
    protected abstract void runInference();
    protected abstract void showResult(Canvas canvas, Size cameraViewSize);
}


