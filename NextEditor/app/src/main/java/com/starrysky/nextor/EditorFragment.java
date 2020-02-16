package com.starrysky.nextor;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;

public class EditorFragment extends Fragment implements TextWatcher {

    private EditText editText;
    private String filename;
    private File file;
    private boolean flag = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_editor, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editText = getView().findViewById(R.id.editText);
        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (flag) {
            if (filename.charAt(0) != '*') {
                filename = "*" + filename;
                ((MainActivity) getActivity()).setName(filename);
            }
        } else {
            flag = true;
        }
    }

    public void read(){
        if (file != null){
            try {
                String str = FileService.read(file);
                setText(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(){
        if (file != null){
            FileService.write(getText(),file);
            if (filename.charAt(0) == '*'){
                filename = filename.substring(1);
            }
            ((MainActivity) getActivity()).setName(filename);
        }
    }

    public String getText(){
        return String.valueOf(editText.getText());
    }

    public void setText(String str){
        editText.setText(str);
    }

    public String getFilename(){
        return filename;
    }

    public void setFilename(String filename){
        this.filename = filename;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file){
        this.file = file;
    }
}
