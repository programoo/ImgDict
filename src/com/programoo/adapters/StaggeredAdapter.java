package com.programoo.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.programoo.imgdict.R;
import com.programoo.loaders.ImageLoader;
import com.programoo.models.Result;
import com.programoo.utils.Global;

public class StaggeredAdapter extends BaseAdapter {

	private ImageLoader mLoader;
	private Context mCtx;
	private List<Result> models;

	public StaggeredAdapter(Context context, List<Result> models) {
		super();
		mCtx = context;
		mLoader = new ImageLoader(context);
		this.models = models;
	}

	@Override
	public Result getItem(int position) {
		return models.get(position);
	}

	@Override
	public int getCount() {
		return models.size();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(mCtx);
			convertView = layoutInflator.inflate(R.layout.row_staggered_demo,
					parent, false);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.imageView1);
			holder.tvImageTitle = (TextView) convertView
					.findViewById(R.id.tvImageTitle);

			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();
		Result rs = getItem(position);
		try {
			if (rs.getImage() != null)
				Global.getInstance(mCtx).getAq().id(holder.imageView)
						.image(rs.getImage().getUrl());
			// fetch and set the image from internet, cache with file and memory
			holder.tvImageTitle.setText(rs.getTitleNoFormatting());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return convertView;
	}

	static class ViewHolder {
		ImageView imageView;
		TextView tvImageTitle;
	}

}
