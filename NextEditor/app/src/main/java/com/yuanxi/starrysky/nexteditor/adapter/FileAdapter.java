package com.yuanxi.starrysky.nexteditor.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxi.starrysky.nexteditor.R;
import com.yuanxi.starrysky.nexteditor.fragment.FileFragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private List<File> mList;
    private FileFragment mFragment;

    public FileAdapter(List<File> list, FileFragment fragment) {
        this.mList = list;
        this.mFragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.file_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(mFragment.getFile().getPath() + "/" + viewHolder.name.getText());
                mFragment.setFile(file);
                mFragment.load(file);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        File file = mList.get(i);
        viewHolder.name.setText(file.getName());
        String date = new SimpleDateFormat("yyyy/MM/dd")
                .format(new Date(file.lastModified()));
        String time = new SimpleDateFormat("HH:mm")
                .format(new Date(file.lastModified()));
        if (file.isDirectory()) {
            viewHolder.img.setImageResource(R.drawable.my_folder);
            viewHolder.info.setText(file.listFiles().length + " | " + date + " " + time);
        } else {
            viewHolder.info.setText(file.length() + " | " + date + " " + time);
            viewHolder.img.setImageResource(R.drawable.my_file);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout item;
        TextView name,info;
        ImageView img;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            name = itemView.findViewById(R.id.name);
            info = itemView.findViewById(R.id.info);
            img = itemView.findViewById(R.id.icon);

        }
    }
}
