package androidessence.planner;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ArcMotion;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidessence.comman.JobItemPriorityCustomView;
import androidessence.comman.MorphDialogToView;
import androidessence.comman.MorphViewToDialog;

public class JobOverviewActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewGroup container;
    LinearLayout priorityLayout;
    private float dX;
    private float dY;
    ImageView callImageView;
    ImageView msgImageView;
    ImageView mapImageView;
    ImageView navigationImageView;
    TextView tvPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_overview_fragment);
        container = (ViewGroup) findViewById(R.id.container);
        setupSharedEelementTransitions();
        final GestureDetector gestureDetector = new GestureDetector(this, new JobOverviewActivity.JoboverviewGestureDetector());
        tvPhoneNumber = (TextView) findViewById(R.id.tv_phone_number);
        priorityLayout = (LinearLayout) findViewById(R.id.ll_job_priority);

        ArrayList<String> patientInfoList = getIntent().getExtras().getStringArrayList("PatientInfoList");
        TextView tvFirstName = (TextView) findViewById(R.id.tv_firstname) ;
        TextView tvLastName = (TextView) findViewById(R.id.tv_lastname);
        TextView tvDob = (TextView) findViewById(R.id.tv_dob);

        if (patientInfoList != null) {
            tvFirstName.setText(patientInfoList.get(0));
            tvLastName.setText(patientInfoList.get(1));
            tvDob.setText(patientInfoList.get(2));
        }

        callImageView = (ImageView) findViewById(R.id.img_call);
        callImageView.setOnClickListener(this);
        msgImageView = (ImageView) findViewById(R.id.img_msg);
        msgImageView.setOnClickListener(this);
        mapImageView = (ImageView) findViewById(R.id.img_map);
        mapImageView.setOnClickListener(this);
        navigationImageView = (ImageView)findViewById(R.id.img_nav);
        navigationImageView.setOnClickListener(this);

        JobItemPriorityCustomView priorityLabel = new JobItemPriorityCustomView(getApplicationContext(),
                -16744193, "R");
        priorityLayout.removeAllViews();
        priorityLayout.addView(priorityLabel);

    }

    private int calculateAge(String dob)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;
        try {
            date = formatter.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long timeDiff = new Date().getTime() - date.getTime();
        Date age = new Date(timeDiff);
        return age.getYear();
    }

    public void setupSharedEelementTransitions()
    {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);

        Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        MorphViewToDialog sharedEnter = new MorphViewToDialog();
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        MorphDialogToView sharedReturn = new MorphDialogToView();
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);

        if (container != null) {
            sharedEnter.addTarget(container);
            sharedReturn.addTarget(container);
        }
        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.img_call:
                onPhoneClicked();
                break;
            case R.id.img_msg:
                onMessageClicked();
                break;
            case R.id.img_map:
                onMapClicked();
                break;
            case R.id.img_nav:
                onNavigationClicked();
                break;
        }
    }

    private void onPhoneClicked()
    {
        Toast.makeText(getApplicationContext(), "Call icon clicked", Toast.LENGTH_SHORT).show();
    }

    private void onMessageClicked()
    {
        Toast.makeText(getApplicationContext(), "Message icon clicked", Toast.LENGTH_SHORT).show();
    }

    private void onMapClicked()
    {
        Toast.makeText(getApplicationContext(), "Map icon clicked", Toast.LENGTH_SHORT).show();
    }

    private void onNavigationClicked()
    {
        Toast.makeText(getApplicationContext(), "Navigation icon clicked", Toast.LENGTH_SHORT).show();
    }

    public class JoboverviewGestureDetector extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        /*// The current viewport. This rectangle represents the currently visible
        // chart domain and range.
        private RectF mCurrentViewport =
                new RectF(AXIS_X_MIN, AXIS_Y_MIN, AXIS_X_MAX, AXIS_Y_MAX);*/

        // The current destination rectangle (in pixel coordinates) into which the
        // chart data should be drawn.
        private Rect mContentRect;


        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            /*//return super.onScroll(e1, e2, distanceX, distanceY);
            // Scrolling uses math based on the viewport (as opposed to math using pixels).

            // Pixel offset is the offset in screen pixels, while viewport offset is the
            // offset within the current viewport.
            float viewportOffsetX = distanceX * mCurrentViewport.width()
                    / mContentRect.width();
            float viewportOffsetY = -distanceY * mCurrentViewport.height()
                    / mContentRect.height();

            // Updates the viewport, refreshes the display.
            setViewportBottomLeft(
                    mCurrentViewport.left + viewportOffsetX,
                    mCurrentViewport.bottom + viewportOffsetY);
*/
            return true;
        }

        /**
         * Sets the current viewport (defined by mCurrentViewport) to the given
         * X and Y positions. Note that the Y value represents the topmost pixel position,
         * and thus the bottom of the mCurrentViewport rectangle.
         */
      /*  private void setViewportBottomLeft(float x, float y)
        {
    *//*
     * Constrains within the scroll range. The scroll range is simply the viewport
     * extremes (AXIS_X_MAX, etc.) minus the viewport size. For example, if the
     * extremes were 0 and 10, and the viewport size was 2, the scroll range would
     * be 0 to 8.
     *//*

            float curWidth = mCurrentViewport.width();
            float curHeight = mCurrentViewport.height();
            x = Math.max(AXIS_X_MIN, Math.min(x, AXIS_X_MAX - curWidth));
            y = Math.max(AXIS_Y_MIN + curHeight, Math.min(y, AXIS_Y_MAX));

            mCurrentViewport.set(x, y - curHeight, x + curWidth, y);

            // Invalidates the View to update the display.
            ViewCompat.postInvalidateOnAnimation(view);
        }*/
        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            //dismiss();
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            return super.onContextClick(e);
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
           /* if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                // swipe right to left
                dismiss();
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                // swipe left to right
                dismiss();
            }

            return super.onFling(e1, e2, velocityX, velocityY);*/
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

}
