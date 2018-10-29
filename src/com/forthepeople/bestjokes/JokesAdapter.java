package com.forthepeople.bestjokes;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forthepeople.bestjokes.JokeListFragment.Callbacks;
import com.forthepeople.bestjokes.dummy.DummyContent.DummyItem;

public class JokesAdapter extends RecyclerView.Adapter<JokesAdapter.ViewHolder> {

	private List<DummyItem> jokes;
	private int rowLayout;
	private Context mContext;
	private Callbacks mCallbacks;

	public JokesAdapter(List<DummyItem> jokes, int rowLayout, Context context) {
		this.jokes = jokes;
		this.rowLayout = rowLayout;
		this.mContext = context;
		this.mCallbacks = (Callbacks) context;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout,
				viewGroup, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {
		String joke = jokes.get(i).content;
		viewHolder.jokeName.setText(joke);
	}

	@Override
	public int getItemCount() {
		return jokes == null ? 0 : jokes.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView jokeName;

		public ViewHolder(View itemView) {
			super(itemView);
			jokeName = (TextView) itemView.findViewById(android.R.id.text1);
		}

	}
}
