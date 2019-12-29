package com.yuanxi.starrysky.nexteditor.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.yuanxi.starrysky.nexteditor.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private EditorFragment editorFragment;
    private ActionBar actionBar;
    private ActionBarDrawerToggle toggle;
    private String title;
    private int index = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        drawerLayout = getView().findViewById(R.id.drawer_layout);
        navigationView = getView().findViewById(R.id.navigation_view );
        navigationView.setItemIconTintList(null);
        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileFragment fileFragment = (FileFragment) getActivity().getSupportFragmentManager().findFragmentByTag("file");
                if(fileFragment == null) {
                    if (!toggle.isDrawerIndicatorEnabled()) {
                        getActivity().onBackPressed();
                    }
                } else {
                    fileFragment.backLast();
                }
            }
        });
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
                item.setChecked(true);
                actionBar.setTitle(item.getTitle());
                drawerLayout.closeDrawers();
                for (int i = 0; i < index; i++){
                    if (getActivity()
                            .getSupportFragmentManager()
                            .findFragmentByTag(String.valueOf(i)) != null) {
                        EditorFragment fragment = (EditorFragment) getActivity()
                                .getSupportFragmentManager()
                                .findFragmentByTag(String.valueOf(i));
                        if (i == item.getItemId()) {
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .show(fragment)
                                    .commit();
                            editorFragment = fragment;
                        } else {
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .hide(fragment)
                                    .commit();
                        }
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu,menu);
        MenuItem settings = menu.findItem(R.id.settings);
        MenuItem edit = menu.findItem(R.id.edit);
        MenuItem file = menu.findItem(R.id.file);
        if (getActivity().getSupportFragmentManager().findFragmentByTag("settings") != null || getActivity().getSupportFragmentManager().findFragmentByTag("file") != null){
            settings.setVisible(false);
            edit.setVisible(false);
            file.setVisible(false);
            toggle.setDrawerIndicatorEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            title = (String) actionBar.getTitle();
            if (getActivity().getSupportFragmentManager().findFragmentByTag("settings") != null){
                actionBar.setTitle("设置");
            } else if (getActivity().getSupportFragmentManager().findFragmentByTag("file") != null){
                actionBar.setTitle("文件");
            }
        } else {
            settings.setVisible(true);
            edit.setVisible(true);
            file.setVisible(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(true);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            actionBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FileFragment fileFragment;
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.settings:
                startFragment(R.id.fragment_container, new SettingsFragment(), "settings");
                getActivity().invalidateOptionsMenu();
                break;
            case R.id.new_file:
                newFile(new EditorFragment());
                break;
            case R.id.save:
                if (editorFragment != null) {
                    if (editorFragment.getFile() != null) {
                        editorFragment.save();
                    } else {
                        fileFragment = new FileFragment();
                        fileFragment.setEditorFragment(editorFragment);
                        fileFragment.setOperation(false);
                        startFragment(R.id.fragment_container, fileFragment, "file");
                        getActivity().invalidateOptionsMenu();
                    }
                }
                break;
            case R.id.save_as:
                if (editorFragment != null) {
                    fileFragment = new FileFragment();
                    fileFragment.setEditorFragment(editorFragment);
                    fileFragment.setOperation(false);
                    startFragment(R.id.fragment_container, fileFragment, "file");
                    getActivity().invalidateOptionsMenu();
                }
                break;
            case R.id.close:
                if (editorFragment != null) {
                    int num = Integer.parseInt(editorFragment.getTag());
                    getActivity().getSupportFragmentManager().beginTransaction().remove(editorFragment).commit();
                    navigationView.getMenu().removeItem(num);
                    List<Fragment> list = new ArrayList<>();
                    for (int i = 0; i < getActivity().getSupportFragmentManager().getFragments().size(); i++) {
                        if (!getActivity().getSupportFragmentManager().getFragments().get(i).getTag().equals(String.valueOf(num)) && !getActivity().getSupportFragmentManager().getFragments().get(i).getTag().equals("main") && !getActivity().getSupportFragmentManager().getFragments().get(i).getTag().equals("file") && getActivity().getSupportFragmentManager().getFragments().get(i) != null) {
                            list.add(getActivity().getSupportFragmentManager().getFragments().get(i));
                        }
                    }
                    if (list.size() >= 1) {
                        num = 0;
                        for (int i = 0; i < list.size(); i++) {
                            if (num < Integer.parseInt(list.get(i).getTag())) {
                                num = Integer.parseInt(list.get(i).getTag());
                            }
                        }
                        editorFragment = (EditorFragment) getActivity().getSupportFragmentManager().findFragmentByTag(String.valueOf(num));
                        getActivity().getSupportFragmentManager().beginTransaction().show(editorFragment).commit();
                        navigationView.getMenu().findItem(num).setCheckable(true).setChecked(true);
                        actionBar.setTitle(navigationView.getMenu().findItem(num).getTitle());
                    } else {
                        editorFragment = null;
                        actionBar.setTitle("");
                    }
                }
                break;
            case R.id.open_file:
                fileFragment = new FileFragment();
                fileFragment.setEditorFragment(editorFragment);
                startFragment(R.id.fragment_container,fileFragment,"file");
                getActivity().invalidateOptionsMenu();
                break;
            case R.id.undo:
                if (editorFragment != null){
                    editorFragment.undo();
                }
                break;
            case R.id.redo:
                if (editorFragment != null){
                    editorFragment.redo();
                }
                break;
            case R.id.read_only:
                if (editorFragment != null){
                    if (item.isChecked()){
                        item.setChecked(false);
                        editorFragment.readOnly(false);
                    } else {
                        item.setChecked(true);
                        editorFragment.readOnly(true);
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    public void startFragment(int i, Fragment fragment, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(i, fragment, tag);
        List<Fragment> list = fragmentManager.getFragments();
        for (int j = 0; j < list.size(); j++){
            if (list.get(j).getTag() != null && !list.get(j).getTag().equals("main")
                    && !list.get(j).getTag().equals("file")){
                fragmentTransaction.hide(list.get(j));
            }
        }
        if (fragment.getTag().equals("settings") || fragment.getTag().equals("file")){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        try{
            editorFragment = (EditorFragment) fragment;
        } catch (Exception ignored){
        }
    }

    public void openFile(final File file){
        boolean flag = true;
        List list = getActivity().getSupportFragmentManager().getFragments();
        for (int i = 0; i < list.size(); i++){
            try {
                EditorFragment fragment = (EditorFragment) list.get(i);
                if (file.getPath().equals(fragment.getFile().getPath())){
                    Toast.makeText(getActivity(),"文件已经打开",Toast.LENGTH_SHORT).show();
                    flag = false;
                    break;
                }
            } catch (Exception ignored){

            }
        }
        if (flag) {
            String name = file.getName();
            MenuItem item = navigationView.getMenu().add(1, index, 0, name);
            item.setIcon(R.drawable.untitled);
            startFragment(R.id.fragment_container, new EditorFragment(), String.valueOf(index));
            navigationView.getMenu().findItem(index).setCheckable(true).setChecked(true);
            title = (String) navigationView.getMenu().findItem(index).getTitle();
            index++;
            editorFragment.setFile(file);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    editorFragment.open();
                }
            }, 500);
        }
    }

    public void saveAs(File file){
        final String name = file.getName();
        MenuItem item = navigationView.getMenu().findItem(Integer.parseInt(editorFragment.getTag()));
        item.setTitle(name);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                actionBar.setTitle(name);
            }
        }, 500);
    }

    public void newFile(Fragment fragment){
        navigationView.getMenu().add(1,index,0,"untitled").setIcon(R.drawable.untitled);
        startFragment(R.id.fragment_container,fragment,String.valueOf(index));
        navigationView.getMenu().findItem(index).setCheckable(true).setChecked(true);
        actionBar.setTitle(navigationView.getMenu().findItem(index).getTitle());
        title = "untitled";
        index++;
    }

    public void setEditorFragment(EditorFragment editorFragment) {
        this.editorFragment = editorFragment;
    }

    public int getIndex() {
        return index;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
