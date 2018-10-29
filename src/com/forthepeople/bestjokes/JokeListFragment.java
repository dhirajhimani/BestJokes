package com.forthepeople.bestjokes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;

import org.lucasr.twowayview.ItemClickSupport;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.forthepeople.bestjokes.dummy.DummyContent;
import com.forthepeople.bestjokes.dummy.DummyContent.DummyItem;
import com.forthepeople.bestjokes.helper.DividerItemDecoration;
import com.forthepeople.bestjokes.helper.Utils;

/**
 * A list fragment representing a list of Jokes. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link JokeDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class JokeListFragment extends Fragment implements
		ItemClickSupport.OnItemClickListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	String TAG = "JokeListFragment";

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(String id);
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {

		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public JokeListFragment() {

	}

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	//
	// // TODO: replace with a real list adapter.
	// setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
	// android.R.layout.simple_list_item_activated_1,
	// android.R.id.text1, DummyContent.ITEMS));
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View child = inflater.inflate(R.layout.list_jokes, container, false);

		return child;

	}

	private RecyclerView mRecyclerView;

	// private ListView mRecyclerView;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.addItemDecoration(new DividerItemDecoration(
				getActivity(), DividerItemDecoration.VERTICAL_LIST));
		mRecyclerView.setHasFixedSize(true);
		ArrayList<String> jokesinassets = Utils.listAssetFiles(getActivity(),
				"dirtyjokes");
		DummyContent.ITEMS.clear();
		for (int i = 0; i < jokesinassets.size(); i++) {
			String joke = getjokefilename(jokesinassets.get(i));
			int fileindex = getjokefileindex(jokesinassets.get(i));
			DummyContent.ITEMS.add(new DummyContent.DummyItem(fileindex + "",
					joke));
			DummyContent.addItem(new DummyContent.DummyItem(fileindex + "",
					jokesinassets.get(i)));
		}
		sortDummyItems();
		JokesAdapter jokesAdapter = new JokesAdapter(DummyContent.ITEMS,
				R.layout.row_joke, getActivity());
		mRecyclerView.setAdapter(jokesAdapter);
		// mRecyclerView.setAdapter(new ArrayAdapter<DummyContent.DummyItem>(
		// getActivity(), android.R.layout.simple_list_item_activated_1,
		// android.R.id.text1, DummyContent.ITEMS));

		final ItemClickSupport itemClick = ItemClickSupport
				.addTo(mRecyclerView);
		itemClick.setOnItemClickListener(this);
		// Restore the previously serialized activated item position.
		// if (savedInstanceState != null
		// && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
		// setActivatedPosition(savedInstanceState
		// .getInt(STATE_ACTIVATED_POSITION));
		// }
		
	}

	private void sortDummyItems() {
		Collections.sort(DummyContent.ITEMS,
				new Comparator<DummyContent.DummyItem>() {

					@Override
					public int compare(DummyItem lhs, DummyItem rhs) {
						// TODO Auto-generated method stub
						int lhs_index = Integer.parseInt(lhs.id);
						int rhs_index = Integer.parseInt(rhs.id);
						if (lhs_index > rhs_index) {
							return 1;
						} else if (lhs_index < rhs_index) {
							return -1;
						} else {
							return 0;
						}

					}
				});
	}

	private String getjokefilename(String jokefilename) {

		String filename = "";
		String tokens[] = null;
		String splitPattern = "_|.htm";
		Pattern p = Pattern.compile(splitPattern);
		tokens = p.split(jokefilename);
		for (int i = 0; i < tokens.length; i++) {
			filename += tokens[i] + " ";
		}

		return filename.trim();
	}

	private int getjokefileindex(String jokefilename) {

		String filename = "";
		String tokens[] = null;
		String splitPattern = "_|.htm";
		Pattern p = Pattern.compile(splitPattern);
		tokens = p.split(jokefilename);
		for (int i = 0; i < tokens.length; i++) {
			filename = tokens[i] + " ";
		}
		filename = filename.trim();
		return Integer.parseInt(filename);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	// /**
	// * Turns on activate-on-click mode. When this mode is on, list items will
	// be
	// * given the 'activated' state when touched.
	// */
	// public void setActivateOnItemClick(boolean activateOnItemClick) {
	// // When setting CHOICE_MODE_SINGLE, ListView will automatically
	// // give items the 'activated' state when touched.
	// getListView().setChoiceMode(
	// activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
	// : ListView.CHOICE_MODE_NONE);
	// }
	//
	// private void setActivatedPosition(int position) {
	// if (position == ListView.INVALID_POSITION) {
	// getListView().setItemChecked(mActivatedPosition, false);
	// } else {
	// getListView().setItemChecked(position, true);
	// }
	//
	// mActivatedPosition = position;
	// }

	@Override
	public void onItemClick(RecyclerView parent, View view, int position,
			long id) {
		mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
	}
}
