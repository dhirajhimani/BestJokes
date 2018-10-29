package com.forthepeople.bestjokes;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.forthepeople.bestjokes.dummy.DummyContent;

/**
 * A fragment representing a single Joke detail screen. This fragment is either
 * contained in a {@link JokeListActivity} in two-pane mode (on tablets) or a
 * {@link JokeDetailActivity} on handsets.
 */
public class JokeDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public JokeDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_joke_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			WebView webView = ((WebView) rootView
					.findViewById(R.id.joke_detail_body));
			ProgressBar progressBar = (ProgressBar) rootView
					.findViewById(R.id.progressBar1);
			loadWebview(webView, progressBar);
		}

		return rootView;
	}

	private void loadWebview(final WebView wv, final ProgressBar progressBar) {
		WebSettings webSettings = wv.getSettings();
		//
		wv.setVisibility(View.INVISIBLE);
		webSettings.setDatabaseEnabled(true);
		webSettings.setGeolocationEnabled(true);
		webSettings.setAppCacheEnabled(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportMultipleWindows(false);
		wv.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);

				progressBar.setVisibility(View.GONE);
				wv.setVisibility(View.VISIBLE);
			}
		});
		String loadurl = "file:///android_asset/dirtyjokes/" + mItem.content;

		wv.loadUrl(loadurl);
	}
}
