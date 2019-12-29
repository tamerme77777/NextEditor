package com.yuanxi.starrysky.nexteditor.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.yuanxi.starrysky.nexteditor.R;

import java.util.List;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundResource(android.R.color.white);
        return view;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        findPreference("theme").setOnPreferenceChangeListener(this);
        findPreference("lineNumbers").setOnPreferenceChangeListener(this);
        findPreference("matchBrackets").setOnPreferenceChangeListener(this);
        findPreference("smartIndent").setOnPreferenceChangeListener(this);
        findPreference("lineWrapping").setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        List<Fragment> list = getActivity().getSupportFragmentManager().getFragments();
        switch (preference.getKey()) {
            case "theme":
                for (int i = 0; i < list.size(); i++) {
                    try {
                        EditorFragment editorFragment = (EditorFragment) list.get(i);
                        editorFragment.setTheme(String.valueOf(newValue));
                    } catch (Exception ignored) {
                    }
                }
                break;
            case "lineNumbers":
                for (int i = 0; i < list.size(); i++) {
                    try {
                        EditorFragment editorFragment = (EditorFragment) list.get(i);
                        editorFragment.lineNumbers((boolean) newValue);
                    } catch (Exception ignored) {

                    }
                }
                break;
            case "matchBrackets":
                for (int i = 0; i < list.size(); i++) {
                    try {
                        EditorFragment editorFragment = (EditorFragment) list.get(i);
                        editorFragment.matchBrackets((boolean)newValue);
                    } catch (Exception ignored) {

                    }
                }
                break;
            case "smartIndent":
                for (int i = 0; i < list.size(); i++) {
                    try {
                        EditorFragment editorFragment = (EditorFragment) list.get(i);
                        editorFragment.smartIndent((boolean)newValue);
                    } catch (Exception ignored) {

                    }
                }
                break;
            case "lineWrapping":
                for (int i = 0; i < list.size(); i++) {
                    try {
                        EditorFragment editorFragment = (EditorFragment) list.get(i);
                        editorFragment.lineWrapping((boolean)newValue);
                    } catch (Exception ignored) {

                    }
                }
                break;
                default:
                    break;
        }
        return true;
    }
}
