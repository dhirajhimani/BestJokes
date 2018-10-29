package com.forthepeople.bestjokes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;

/**
 * An activity representing a list of Jokes. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link JokeDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link JokeListFragment} and the item details (if present) is a
 * {@link JokeDetailFragment}.
 * <p>
 * This activity also implements the required {@link JokeListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class JokeListActivity extends Activity implements
		JokeListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_joke_list);

		if (findViewById(R.id.joke_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			// ((JokeListFragment) getFragmentManager()
			// .findFragmentById(R.id.joke_list))
			// .setActivateOnItemClick(true);
		}
		if (mTwoPane) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					onItemSelected("1");
				}
			}, 1000);
		}
		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link JokeListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(JokeDetailFragment.ARG_ITEM_ID, id);
			JokeDetailFragment fragment = new JokeDetailFragment();
			fragment.setArguments(arguments);
			getFragmentManager().beginTransaction()
					.replace(R.id.joke_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, JokeDetailActivity.class);
			detailIntent.putExtra(JokeDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
