package com.prempal.crypt;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prempal on 30/5/16.
 */
public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.ViewHolder> {

    private JSONArray directory;
    private NetworkInterface listener;

    public DirectoryAdapter(NetworkInterface listener, JSONArray directory) {
        this.listener = listener;
        this.directory = directory;
    }

    @Override
    public DirectoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DirectoryAdapter.ViewHolder holder, int position) {
        try {
            JSONObject object = directory.getJSONObject(position);
            final String itemName = object.getString("name");
            holder.itemName.setText(itemName);
            final String itemType = object.getString("type");
            if (itemType.equals("dir")) {
                holder.itemType.setImageResource(R.drawable.ic_folder_blue_36dp);
            } else {
                holder.itemType.setImageResource(R.drawable.ic_insert_drive_file_blue_36dp);
            }
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemType.equals("dir")) {
                        listener.updateCurrentPath(itemName);
                        listener.fetchDirectoryListing();
                    } else {
                        listener.downloadFile(itemName);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return directory.length();
    }

    public void updateDirectory(JSONArray newDir) {
        directory = newDir;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemType;
        TextView itemName;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemType = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
