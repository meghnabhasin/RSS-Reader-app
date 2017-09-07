package meghna.com.blogistic1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebActivity extends AppCompatActivity {
    private WebView wv;
    private String url;
    ProgressBar pb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getIntent() != null) {
            url = getIntent().getStringExtra("meghna.com.blogistic1.url");
            pb2 = (ProgressBar) findViewById(R.id.progressbar);
            pb2.setVisibility(View.GONE);
            wv = (WebView) findViewById(R.id.wv);
            wv.setWebViewClient(new WebViewClientDemo());
            wv.getSettings().setJavaScriptEnabled(true);
            wv.loadUrl(url);
        }
    }
    private class WebViewClientDemo extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            wv.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pb2.setVisibility(View.GONE);
            pb2.setProgress(100);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pb2.setVisibility(View.VISIBLE);
            pb2.setProgress(0);
        }
    }
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.oib) {
            Intent s = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(s);
        }
        return super.onOptionsItemSelected(item);
    }
}