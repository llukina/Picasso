package com.example.android.picasso;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.android.picasso.view.PicassoView;

public class MainActivity extends AppCompatActivity {

    private PicassoView picassoView;
    private AlertDialog.Builder currentAlertDialog;
    private ImageView widthImageView;
    private AlertDialog dialogLineWidth;
    private AlertDialog colorDialog;
//    private SensorManager mSensorManager;
//    private float mAccel; // acceleration apart from gravity
//    private float mAccelCurrent; // current acceleration including gravity
//    private float mAccelLast; // last acceleration including gravity

    private SeekBar alphaSeekBar;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private View colorView;
    private SeekBar.OnSeekBarChangeListener colorSeekBarChanged = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            picassoView.setBackgroundColor(Color.argb(
                    alphaSeekBar.getProgress(),
                    redSeekBar.getProgress(),
                    greenSeekBar.getProgress(),
                    blueSeekBar.getProgress()
            ));

            colorView.setBackgroundColor(Color.argb(
                    alphaSeekBar.getProgress(),
                    redSeekBar.getProgress(),
                    greenSeekBar.getProgress(),
                    blueSeekBar.getProgress()
            ));

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    private SeekBar.OnSeekBarChangeListener widthSeekBarChange = new SeekBar.OnSeekBarChangeListener() {
        Bitmap bitmap = Bitmap.createBitmap(400, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            Paint p = new Paint();
            p.setColor(picassoView.getDrawingColor());
            p.setStrokeCap(Paint.Cap.ROUND);
            p.setStrokeWidth(progress);

            bitmap.eraseColor(Color.WHITE);
            canvas.drawLine(30, 50, 370, 50, p);
            widthImageView.setImageBitmap(bitmap);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
//        mAccel = 0.00f;
//        mAccelCurrent = SensorManager.GRAVITY_EARTH;
//        mAccelLast = SensorManager.GRAVITY_EARTH;
        picassoView = findViewById(R.id.view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clear_id:
                picassoView.clear();
                break;
            case R.id.save_id:
                picassoView.saveToInternalStorage();
                break;
            case R.id.color_id:
                showColorDialog();
                break;
            case R.id.line_width:
                showLineWidthDialog();
                break;
//            case R.id.erase_id:
//                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;

    }

    void showColorDialog() {
        currentAlertDialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.color_dialog, null);
        alphaSeekBar = view.findViewById(R.id.alpha_seek_bar);
        redSeekBar = view.findViewById(R.id.red_seek_bar);
        greenSeekBar = view.findViewById(R.id.green_seek_bar);
        blueSeekBar = view.findViewById(R.id.blue_seek_bar);
        colorView = view.findViewById(R.id.color_view);

        alphaSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);
        redSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);
        greenSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);
        blueSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);


        final int color = picassoView.getDrawingColor();
        alphaSeekBar.setProgress(Color.alpha(color));
        greenSeekBar.setProgress(Color.green(color));
        redSeekBar.setProgress(Color.red(color));
        blueSeekBar.setProgress(Color.blue(color));

        Button setColorButton = (Button) view.findViewById(R.id.set_color_button);
        setColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picassoView.setDrawingColor(Color.argb(
                        alphaSeekBar.getProgress(),
                        redSeekBar.getProgress(),
                        greenSeekBar.getProgress(),
                        blueSeekBar.getProgress()
                ));

                colorDialog.dismiss();
            }
        });

        currentAlertDialog.setView(view);
        currentAlertDialog.setTitle("Choose color");
        colorDialog = currentAlertDialog.create();
        colorDialog.show();

    }

    void showLineWidthDialog() {
        currentAlertDialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.width_dialog, null);
        final SeekBar widthSeekBar = (SeekBar) view.findViewById(R.id.width_seek_bar);
        Button setLineWidthButton = (Button) view.findViewById(R.id.width_dialog_button);
        widthImageView = (ImageView) view.findViewById(R.id.width_image_view);
        setLineWidthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picassoView.setLineWidth(widthSeekBar.getProgress());
                dialogLineWidth.dismiss();
                currentAlertDialog = null;
            }
        });


        widthSeekBar.setOnSeekBarChangeListener(widthSeekBarChange);
        widthSeekBar.setProgress(picassoView.getLineWidth());


        currentAlertDialog.setView(view);
        dialogLineWidth = currentAlertDialog.create();
        dialogLineWidth.setTitle("Set Line Width:");
        dialogLineWidth.show();

    }

//    private  final SensorEventListener mSensorListener = new SensorEventListener() {
//        @Override
//        public void onSensorChanged(SensorEvent sensorEvent) {
//            float x = sensorEvent.values[0];
//            float y = sensorEvent.values[1];
//            float z = sensorEvent.values[2];
//            mAccelLast = mAccelCurrent;
//            mAccelCurrent = (float) Math.sqrt((double) x*x + y*y + z*z);
//            float delta = mAccelCurrent - mAccelLast;
//            mAccel = mAccel * 0.9f + delta; //perform low-cut filter
//
//            if(mAccel > 30 ){
//                picassoView.clear();
//            }
//            mAccel =0;
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        }
//    };
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mSensorManager.unregisterListener(mSensorListener);
//    }
}
