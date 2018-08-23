package kidskeeper.sungshin.or.kr.kikee.Adult.Camera;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.R;

public class CameraActivity extends AppCompatActivity {
    @BindView(R.id.camera_webView_webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        setWebView();
    }

    void setWebView() {
        //배경색 설정
        webView.setBackgroundColor(0);
        //스크롤 설정
        webView.setHorizontalScrollBarEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        //HTML을 파싱하여 웹뷰에서 보여주거나 하는 작업에서
        //width , height 가 화면 크기와 맞지 않는 현상이 발생한다
        //이를 잡아주기 위한 코드
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //캐시파일 사용 금지(운영중엔 주석처리 할 것)
        //webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        //zoom 허용
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);

        //javascript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //javascript 허용
        webView.getSettings().setJavaScriptEnabled(true);

        //meta태그의 viewport사용 가능
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("http://www.google.com/");
        webView.setWebViewClient(new WishWebViewClient());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WishWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
