package com.yuanxi.starrysky.nexteditor.util;

import android.webkit.JavascriptInterface;

import com.yuanxi.starrysky.nexteditor.fragment.EditorFragment;

import java.io.IOException;

public class WebInterface {

    private EditorFragment editorFragment;

    public WebInterface(EditorFragment editorFragment){
        this.editorFragment = editorFragment;
    }

    @JavascriptInterface
    public String read() {
        String string = "";
        try {
            string = FileService.read(editorFragment.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    @JavascriptInterface
    public void write(String string){
        FileService.write(string.substring(0,string.length()-1),editorFragment.getFile());
    }

    @JavascriptInterface
    public void onChange(){
        editorFragment.onChange();
    }
}
