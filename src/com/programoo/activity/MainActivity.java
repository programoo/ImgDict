package com.programoo.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.programoo.adapters.StaggeredAdapter;
import com.programoo.imgdict.R;
import com.programoo.models.Result;
import com.programoo.models.SearchImageOut;
import com.programoo.models.Word;
import com.programoo.utils.Global;

public class MainActivity extends Activity implements OnClickListener,
		OnInitListener {

	private GridView gridView;
	private Button imbBtnSearch;
	private EditText edtSearch;
	private TextView tvSearchSummary;
	private TextView tvDescMajor;
	private TextView tvSyn;
	private TextView tvOppo;
	private Activity mCtx;
	private final int MY_DATA_CHECK_CODE = 1;
	private TextToSpeech mTts;
	private Button imgBtnSpeak;
	private String currentWord;

	private static final String INT_UNIT_ID = "ca-app-pub-3516350933158110/7404587980";
	private InterstitialAd interstitial;

	final Handler handler = new Handler();
	private Timer timer;
	private boolean isAsyncLoadFinish = false;
	private ArrayAdapter<String> dropDownAdapter;
	private AutoCompleteTextView autoTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mCtx = this;

		// initialize text to speech
		initialAutoCompleteTextView();
		initializeTextToSpeech();

		edtSearch = (EditText) findViewById(R.id.edtSearchWord);
		imbBtnSearch = (Button) findViewById(R.id.imgBtnSearch);
		imgBtnSpeak = (Button) findViewById(R.id.imgBtnSpeak);
		imgBtnSpeak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mTts.speak(currentWord, TextToSpeech.QUEUE_FLUSH, null);
			}
		});

		tvSearchSummary = (TextView) findViewById(R.id.tvSearchSummary);
		tvDescMajor = (TextView) findViewById(R.id.tvDescMajor);
		tvSyn = (TextView) findViewById(R.id.tvSyn);
		tvOppo = (TextView) findViewById(R.id.tvOppo);

		imbBtnSearch.setOnClickListener(this);
		gridView = (GridView) this.findViewById(R.id.staggeredGridView1);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Result rs = Global.getInstance(mCtx).getImgModel()
						.get(position);

				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(rs
						.getUnescapedUrl()));
				startActivity(i);

			}
		});

		initialAdvertise();
	}

	@Override
	public void onClick(View v) {

		String keyword = edtSearch.getText().toString();
		queryAndShow(keyword);
		searchRelateImage(keyword);
	}

	public void initialAutoCompleteTextView() {
		List<String> eentryList = Global.getInstance(this).getMdb().getEentry();

		// In the onCreate method
		autoTextView = (AutoCompleteTextView) findViewById(R.id.edtSearchWord);
		autoTextView.setThreshold(1);
		
		dropDownAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, eentryList);
		autoTextView.setAdapter(dropDownAdapter);
		
		autoTextView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int pos,
					long id) {
				String keyword = (String) ((AdapterView<ListAdapter>) parent)
						.getItemAtPosition(pos);
				hideSoftKeyboard();
				queryAndShow(keyword);
				searchRelateImage(keyword);
			}
		});

		autoTextView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				currentWord = s.toString();
				queryAndShow(currentWord);
			}
		});

	}

	public void initialAdvertise() {
		/* --------------- Interstial Ads --------------------- */
		// Create the interstitial.
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(INT_UNIT_ID);

		interstitial.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				displayInterstitial();
			}
		});

		TimerTask timertask = new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						AdRequest adRequest2 = new AdRequest.Builder().build();
						interstitial.loadAd(adRequest2);
					}
				});
			}
		};
		timer = new Timer();
		timer.schedule(timertask, 20 * 1000, 10 * 60 * 1000);
		/* ------------ End Interstitial --------------- */
	}

	public void displayInterstitial() {
		if (interstitial.isLoaded()) {

			interstitial.show();

		}
	}

	public void initializeTextToSpeech() {
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// success, create the TTS instance
				mTts = new TextToSpeech(this, this);

				mTts.setLanguage(Locale.US);
			} else {
				// missing data, install it
				Intent installIntent = new Intent();
				installIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}

	public void searchRelateImage(String keyword) {
		String url = "http://www.google.com/uds/GnewsSearch?q=" + keyword
				+ "&v=1.0";
		Global.getInstance(this).getAq()
				.ajax(url, JSONObject.class, this, "jsonCallback");
	}

	public void jsonCallback(String url, JSONObject json, AjaxStatus status) {

		if (json != null) {
			SearchImageOut sIo = Global.getInstance(this).getgSon()
					.fromJson(json.toString(), SearchImageOut.class);
			try {

				List<Result> models = sIo.getResponseData().getResults();
				List<Result> newModels = new ArrayList<Result>();

				for (int i = 0; i < models.size(); i++) {
					Result rs = models.get(i);
					if (rs.getImage() != null) {
						if (rs.getImage().getUrl() != null) {
							newModels.add(rs);
						}
					}
				}

				Global.getInstance(this).setImgModel(newModels);

				StaggeredAdapter adapter = new StaggeredAdapter(
						MainActivity.this, Global.getInstance(this)
								.getImgModel());

				gridView.setAdapter(adapter);
				adapter.notifyDataSetChanged();

			} catch (Exception e) {
				Toast.makeText(this, "Can not connect to server.",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}

		} else {
			{
				Toast.makeText(this, "Can not connect to server.",
						Toast.LENGTH_LONG).show();
			}
		}

	}

	public void queryAndShow(String keyword) {
		if (!keyword.equalsIgnoreCase("")) {
			List<Word> wordResp = Global.getInstance(this).getMdb()
					.searchByExactKeyword(keyword);

			// check is in case word have to meaning
			for (int i = 1; i < Integer.MAX_VALUE; i++) {
				List<Word> wordRelate = Global.getInstance(this).getMdb()
						.searchByExactKeyword(keyword + "" + i);

				for (int j = 0; j < wordRelate.size(); j++) {
					wordResp.add(wordRelate.get(j));
				}

				if (wordRelate.size() == 0)
					break;
			}

			if (wordResp.size() > 0) {

				String desc = "";
				String syn = "";
				String oppo = "";

				for (int i = 0; i < wordResp.size(); i++) {
					Word w = wordResp.get(i);

					if (!w.getEthai().equalsIgnoreCase(""))
						desc += "(" + w.getEcat() + ") " + w.getEthai() + "\n";

					if (!w.getTentry().equalsIgnoreCase(""))
						desc += "(" + w.getEcat() + ") " + w.getTentry() + "\n";

					if (!w.getEsyn().equalsIgnoreCase(""))
						syn += "(" + w.getEcat() + ") " + w.getEsyn() + "\n";

					if (!w.getEant().equalsIgnoreCase(""))
						oppo += "(" + w.getEcat() + ") " + w.getEant() + "\n";
				}

				tvSearchSummary.setText(getResources().getString(
						R.string.translation_of)
						+ " " + keyword + "\n");

				if (!desc.equalsIgnoreCase("")) {
					tvDescMajor.setVisibility(View.VISIBLE);
					tvDescMajor.setText("Description: \n" + desc);
				} else {
					tvDescMajor.setVisibility(View.GONE);
				}

				if (!syn.equalsIgnoreCase("")) {
					tvSyn.setVisibility(View.VISIBLE);
					tvSyn.setText("Synonym: \n" + syn);
				} else {
					tvSyn.setVisibility(View.GONE);
				}
				if (!oppo.equalsIgnoreCase("")) {
					tvOppo.setVisibility(View.VISIBLE);
					tvOppo.setText("Opposite: \n" + oppo);
				} else {
					tvOppo.setVisibility(View.GONE);
				}

			} else {
				tvSearchSummary.setText(getResources().getString(
						R.string.translation_fail)
						+ " " + keyword);
				tvOppo.setVisibility(View.GONE);
				tvSyn.setVisibility(View.GONE);
				tvDescMajor.setVisibility(View.GONE);
			}

		}

	}

	@Override
	public void onInit(int status) {
		// initial TTS complete
	}

	public void hideSoftKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		// check if no view has focus:
		View view = this.getCurrentFocus();
		if (view == null)
			return;

		inputManager.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);

	}
}
