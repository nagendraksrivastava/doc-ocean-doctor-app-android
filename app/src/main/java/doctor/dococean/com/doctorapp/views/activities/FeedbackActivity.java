package doctor.dococean.com.doctorapp.views.activities;

import android.os.Bundle;

import doctor.dococean.com.doctorapp.R;

public class FeedbackActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setUpToolBar();
        setToolbarTitle("Feedback");
        setHomeAsUpEnabled(true);
    }
}
