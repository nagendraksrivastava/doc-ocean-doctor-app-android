package doctor.dococean.com.doctorapp.views.activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import doctor.dococean.com.doctorapp.R;

public class AboutUsActivity extends ToolbarActivity {

    private WebView mAboutUsWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        intializeValues();
        setToolbarTitle(" About Us");
        setHomeAsUpEnabled(true);
        mAboutUsWebView.loadUrl("http://dococean.com");

    }

    private void intializeValues() {
        mAboutUsWebView = (WebView) findViewById(R.id.terms_and_condition_web_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setMax(100);
        mAboutUsWebView.setWebChromeClient(new MyBrowser());
        mAboutUsWebView.getSettings().setJavaScriptEnabled(true);
    }


    private class MyBrowser extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
            if (newProgress >= 100) {
                mProgressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
