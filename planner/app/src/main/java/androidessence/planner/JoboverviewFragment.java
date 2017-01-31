package androidessence.planner;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidessence.comman.JobItemPriorityCustomView;
import androidessence.planner.R;

import static android.view.MotionEvent.INVALID_POINTER_ID;

public class JoboverviewFragment extends DialogFragment implements View.OnClickListener
{
    LinearLayout priorityLayout;
    private float dX;
    private float dY;
    ImageView callImageView;
    ImageView msgImageView;
    ImageView mapImageView;
    ImageView navigationImageView;
    TextView tvPhoneNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.getDialog().setCanceledOnTouchOutside(true);
        final GestureDetector gestureDetector = new GestureDetector(getActivity(), new JoboverviewGestureDetector());
        final View rootView = inflater.inflate(R.layout.fragment_joboverview, container, false);
        getDialog().getWindow().setWindowAnimations(
                R.style.dialog_animation_fade);
        /*rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });*/
        priorityLayout = (LinearLayout) rootView.findViewById(R.id.ll_job_priority);

        tvPhoneNumber = (TextView) rootView.findViewById(R.id.tv_phone_number);

        callImageView = (ImageView) rootView.findViewById(R.id.img_call);
        callImageView.setOnClickListener(this);
        msgImageView = (ImageView) rootView.findViewById(R.id.img_msg);
        msgImageView.setOnClickListener(this);
        mapImageView = (ImageView) rootView.findViewById(R.id.img_map);
        mapImageView.setOnClickListener(this);
        navigationImageView = (ImageView) rootView.findViewById(R.id.img_nav);
        navigationImageView.setOnClickListener(this);

        JobItemPriorityCustomView priorityLabel = new JobItemPriorityCustomView(getActivity().getApplicationContext(),
                -16744193, "R");
        priorityLayout.removeAllViews();
        priorityLayout.addView(priorityLabel);

        return rootView;
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
        Toast.makeText(getActivity(), "Call icon clicked", Toast.LENGTH_SHORT).show();
    }

    private void onMessageClicked()
    {
        Toast.makeText(getActivity(), "Message icon clicked", Toast.LENGTH_SHORT).show();
    }

    private void onMapClicked()
    {
        Toast.makeText(getActivity(), "Map icon clicked", Toast.LENGTH_SHORT).show();
    }

    private void onNavigationClicked()
    {
        Toast.makeText(getActivity(), "Navigation icon clicked", Toast.LENGTH_SHORT).show();
    }

    public class JoboverviewGestureDetector extends GestureDetector.SimpleOnGestureListener
    {
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
        public boolean onSingleTapUp(MotionEvent e)
        {
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e)
        {
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
        public void onShowPress(MotionEvent e)
        {
            super.onShowPress(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e)
        {
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e)
        {
            //dismiss();
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e)
        {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e)
        {
            return super.onContextClick(e);
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
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
        public boolean onDown(MotionEvent e)
        {
            return true;
        }
    }

}
