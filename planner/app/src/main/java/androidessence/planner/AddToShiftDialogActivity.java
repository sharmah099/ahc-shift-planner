package androidessence.planner;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ArcMotion;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidessence.comman.MainApp;
import androidessence.comman.MorphDialogToView;
import androidessence.comman.MorphViewToDialog;
import androidessence.listeners.AddToShiftListener;

public class AddToShiftDialogActivity extends AppCompatActivity implements View.OnClickListener
{
    private ViewGroup container;

    private AddToShiftListener addToShiftListener;

    Button addToShiftButton;

    TextView cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_shift_dialog);
        container = (ViewGroup) findViewById(R.id.container);
        setupSharedEelementTransitions();

        addToShiftButton = (Button) findViewById(R.id.btn_add_shift);
        cancelButton = (TextView) findViewById(R.id.tv_cancel) ;

        addToShiftButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

       /* View.OnClickListener dismissListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        };
        container.findViewById(R.id.btn_add_shift).setOnClickListener(dismissListener);
        container.findViewById(R.id.tv_cancel).setOnClickListener(dismissListener);*/
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    public void dismiss()
    {
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
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
    public void onClick(View v) {
        addToShiftListener = ((MainApp)this.getApplicationContext()).getAddToShiftListener();
        switch (v.getId())
        {
            case R.id.tv_cancel:
                finish();
                addToShiftListener.onAddShiftCanclled();
                break;
            case R.id.btn_add_shift:
                finish();
                addToShiftListener.addToShift();
                break;

        }
    }
}
