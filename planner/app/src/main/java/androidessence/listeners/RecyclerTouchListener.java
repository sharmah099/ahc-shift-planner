package androidessence.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidessence.planner.R;
import androidessence.planner.ShiftscreenActivity;

/**
 * Created by rashmi.ravikumar on 2/3/2017.
 */

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private ClickListener mListener;

    public interface ClickListener {

        public void onItemClick(View view, int position);

    }

    GestureDetector gestureDetector;

    public RecyclerTouchListener(Context context, ClickListener listener) {
        mListener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && gestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, rv.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {



    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}