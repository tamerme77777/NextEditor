package com.yuanxi.starrysky.nexteditor.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.yuanxi.starrysky.nexteditor.R;
import com.yuanxi.starrysky.nexteditor.util.Language;
import com.yuanxi.starrysky.nexteditor.util.WebInterface;

import java.io.File;

public class EditorFragment extends Fragment {

    private WebView webView;
    private File file;
    private boolean flag = false;
    private boolean as = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_editor, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webView = getView().findViewById(R.id.web_view);
        webView.loadUrl("file:///android_asset/index.html");
        WebSettings webSettings = webView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebInterface(this), "Android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                setTheme(preferences.getString("theme", "idea"));
                if (flag){
                    open();
                    flag = false;
                }
            }
        });
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void open() {
        setValue();
        setLanguage();
        as = true;
    }

    public void save() {
        getValue();
        setLanguage();
        MainFragment mainFragment = (MainFragment) getActivity().getSupportFragmentManager().findFragmentByTag("main");
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar.getTitle().charAt(0) == '*'){
            actionBar.setTitle(actionBar.getTitle().toString().substring(1));
            mainFragment.setTitle(actionBar.getTitle().toString().substring(1));
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void onChange() {
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar.getTitle().charAt(0) != '*' && !as){
            actionBar.setTitle("*" + actionBar.getTitle());
        }
        as = false;
    }

    public void setLanguage() {
        String string = Language.setLanguage(file);
        webView.evaluateJavascript("javascript:setLanguage('" + string + "')", null);
    }

    public void setTheme(String theme) {
        webView.evaluateJavascript("javascript:setTheme('" + theme + "')", null);
    }

    public void undo() {
        webView.evaluateJavascript("javascript:undo()", null);
    }

    public void redo() {
        webView.evaluateJavascript("javascript:redo()", null);
    }

    public void setValue() {
        webView.evaluateJavascript("javascript:setValue()", null);
    }

    public void getValue() {
        webView.evaluateJavascript("javascript:getValue()", null);
    }

    public void readOnly(boolean value) {
        webView.evaluateJavascript("javascript:readOnly('" + value + "')", null);
    }

}
