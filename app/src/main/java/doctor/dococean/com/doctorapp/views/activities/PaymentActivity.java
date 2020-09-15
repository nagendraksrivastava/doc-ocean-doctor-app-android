package doctor.dococean.com.doctorapp.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import doctor.dococean.com.doctorapp.R;

public class PaymentActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setToolbarTitle("Payment");
        setHomeAsUpEnabled(true);
    }
}
