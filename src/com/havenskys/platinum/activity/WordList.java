package com.havenskys.platinum.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.havenskys.platinum.DbAdapter;
import com.havenskys.platinum.R;

public class WordList extends Activity implements OnClickListener,
		OnCheckedChangeListener, OnLongClickListener {

	/*
	 * @Override protected void onListItemClick(ListView l, View v, int
	 * position, long id) { // TODO Auto-generated method stub
	 * super.onListItemClick(l, v, position, id);
	 * 
	 * 
	 * String word = mDataStore.getString("wordStore", id, "word"); Intent i =
	 * this.getIntent(); i.putExtra("word", word); setResult(79, i);
	 * mDataStore.close(); finish();
	 * 
	 * 
	 * }//
	 */
	private static final String TAG = "RSASMS WordList";
	private DbAdapter mDataStore;
	private Button mBaseButton;
	private SharedPreferences mSharedPreferences;
	private Editor mPreferencesEditor;

	private Button mTriggerOne;
	// private Button mTriggerTwo;
	// private Button mTriggerThree;
	private LinearLayout mWordList;
	private ScrollView mScrollView;

	private LinearLayout mLinearLayout;

	private final static int BUTTON_ALPHA = 1;
	private final static int BUTTON_BETA = 2;
	private final static int BUTTON_GAMMA = 3;

	private LinearLayout mButtonbar, mContactview;
	private long mMessageId;
	private Bundle mIntentExtras;

	private int mTempInt;

	/*
	 * @Override protected void onCreate(Bundle savedInstanceState) { // TODO
	 * Auto-generated method stub super.onCreate(savedInstanceState);
	 * requestWindowFeature(Window.FEATURE_NO_TITLE);
	 * 
	 * //Log.i(TAG,"layout"); setContentView(R.layout.list);
	 * 
	 * //Log.i(TAG,"database"); mDataStore = new DbAdapter(this);
	 * mDataStore.loadDb();
	 * 
	 * loadList();
	 * 
	 * }//
	 */

	/*
	 * private void loadList() { // TODO Auto-generated method stub Cursor
	 * lCursor = mDataStore.detailQuery("wordStore", new String[] {"_id",
	 * "word"}, "status > 0", null, null, null, "updated desc", "20"); //Cursor
	 * lCursor = mDataStore.detailQuery("wordStore", new String[] {"_id",
	 * "word"}, "status > 0", null, null, null, "updated", null);
	 * 
	 * startManagingCursor(lCursor);
	 * 
	 * String[] from = new String[]{"word" }; int[] to = new
	 * int[]{R.id.listrowword};
	 * 
	 * //setTitle("Manual Sending, " + lCursor.getCount() + " Entries");
	 * SimpleCursorAdapter entries = new SimpleCursorAdapter(this,
	 * R.layout.listrowword, lCursor, from, to); setListAdapter(entries);
	 * getListView().setTextFilterEnabled(true);
	 * 
	 * }//
	 */

	@Override
	protected void onStop() {
		// Log.i(TAG,"onStop() ++++++++++++++++++++++++++++++++++++++++s");
		String sentence = mSharedPreferences.contains("sentence") ? mSharedPreferences
				.getString("sentence", "") : "";
		Intent i = this.getIntent();
		i.putExtra("sentence", sentence);
		setResult(Activity.RESULT_OK, i);
		mDataStore.close();
		// finish();
		super.onStop();
	}

	@Override
	protected void onPause() {
		// Log.i(TAG,"onPause() ++++++++++++++++++++++++++++++++++++++++s");
		mDataStore.close();
		super.onPause();
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Log.i(TAG,"layout");
		setContentView(R.layout.wordlist);

		mSharedPreferences = getSharedPreferences("Preferences",
				MODE_WORLD_WRITEABLE);
		mPreferencesEditor = mSharedPreferences.edit();
		mPreferencesEditor.putString("sentence", "").commit();

		// Log.i(TAG,"database");
		mDataStore = new DbAdapter(this);
		mDataStore.loadDb();

		// Log.i(TAG,"layout views");
		mScrollView = (ScrollView) this.findViewById(R.id.wordlist_scrollview);
		mWordList = (LinearLayout) this.findViewById(R.id.wordlist);
		mLinearLayout = (LinearLayout) this
				.findViewById(R.id.wordlist_linearlayout);
		// mLinearLayout.setLayoutParams( new
		// LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		mLinearLayout.setVisibility(LinearLayout.VISIBLE);
		mBaseButton = (Button) this.findViewById(R.id.wordlist_button);
		// mBaseButton.setVisibility(Button.VISIBLE);

		// Log.i(TAG,"Create Contact View");
		mContactview = new LinearLayout(this);
		mContactview.setLayoutParams(mLinearLayout.getLayoutParams());
		mContactview.setVisibility(LinearLayout.VISIBLE);
		// mContactview.setLayoutParams( new
		// LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		mContactview.setOrientation(LinearLayout.HORIZONTAL);
		mContactview.setPadding(5, 5, 0, 0);

		// Log.i(TAG,"layout Buttonbar");
		// mButtonbar = new LinearLayout(this);
		// mButtonbar.setLayoutParams( new
		// LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.WRAP_CONTENT) );
		// mButtonbar.setOrientation(LinearLayout.HORIZONTAL);
		// mButtonbar.setVisibility(LinearLayout.VISIBLE);
		mButtonbar = (LinearLayout) this.findViewById(R.id.wordlist_buttonbar);
		// mToList.addView(mButtonbar);
		mTriggerOne = new Button(this);
		LayoutParams params = mBaseButton.getLayoutParams();
		mTriggerOne.setLayoutParams(params);

		// mTriggerOne.setPadding(10, 10, 0, 0);
		mTriggerOne.setId(BUTTON_ALPHA);
		mTriggerOne.setVisibility(Button.VISIBLE);
		// mTriggerOne.setLayoutParams( new
		// LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		mButtonbar.addView(mTriggerOne);
		mButtonbar.setPadding(5, 0, 5, 0);
		/**//*
			 * mTriggerTwo = new Button(this);
			 * mTriggerTwo.setLayoutParams(mBaseButton.getLayoutParams());
			 * mTriggerTwo.setId(BUTTON_BETA);
			 * mTriggerTwo.setVisibility(Button.VISIBLE);
			 * //mTriggerTwo.setLayoutParams( new
			 * LayoutParams(LayoutParams.FILL_PARENT,
			 * LayoutParams.WRAP_CONTENT)); mButtonbar.addView(mTriggerTwo);
			 * mTriggerThree = new Button(this);
			 * mTriggerThree.setLayoutParams(mBaseButton.getLayoutParams());
			 * mTriggerThree.setVisibility(Button.VISIBLE);
			 * mTriggerThree.setId(BUTTON_GAMMA);
			 * //mTriggerThree.setLayoutParams( new
			 * LayoutParams(LayoutParams.FILL_PARENT,
			 * LayoutParams.WRAP_CONTENT)); mButtonbar.addView(mTriggerThree);
			 * /*
			 */

		// mButtonbar = (LinearLayout) this.findViewById(R.id.listbuttonbar);

		// mRadioButton = (RadioButton)
		// this.findViewById(R.id.listrowrsa_active);
		// mRSAToggle = (ToggleButton)
		// this.findViewById(R.id.listrowrsa_usersa);

		mIntentExtras = getIntent().getExtras();
		mMessageId = mIntentExtras != null ? mIntentExtras.getLong("messageid")
				: 0;

		// Log.i(TAG,"get entries from recipientStore where messageid="+mMessageId);

		mTriggerOne.setText("Close");
		mTriggerOne.setOnClickListener(this);

		populateList();
		// Log.i(TAG,"ready for action");

		// mTriggerTwo.setText("");
		// mTriggerTwo.setOnClickListener(this);
		// mTriggerThree.setText("");
		// mTriggerThree.setOnClickListener(this);

		// mRadioButton.setOnClickListener(this);
		// mRSAToggle.setOnClickListener(this);
		/**/

	}

	private void populateList() {
		// Log.i(TAG,"populateList() -----------------------");

		Cursor rowData = null;
		// Log.i(TAG,"recipientStore GET _id,name,tel,useRSA,status where messageid=mMessageId");
		rowData = mDataStore.detailQuery("wordStore", new String[] { "_id",
				"word", "status" }, "status > 0", null, null, null,
				"updated desc", "1024");
		// rowData = mDataStore.getEntry("recipientStore", new String[]
		// {"_id","name","tel","useRSA","status"}, "messageid="+mMessageId );
		if (rowData == null) {
			Log.e(TAG,
					"wordStore is null, Setting Title to Message, leaving populateList()");
			setTitle("Nothing to Report.");
			return;
		}
		if (!rowData.moveToFirst()) {
			Log.e(TAG,
					"wordStore is empty, Setting Title to Message, leaving populateList()");
			setTitle("Nothing to Report.");
			rowData.close();
			return;
		}
		startManagingCursor(rowData);

		ToggleButton tempToggleButton = new ToggleButton(this);
		LinearLayout tempLinearLayout = new LinearLayout(this);
		// tempLinearLayout.setLayoutParams(mLinearLayout.getLayoutParams());
		LayoutParams buttonParams = tempToggleButton.getLayoutParams();
		// LayoutParams layoutParams = tempLinearLayout.getLayoutParams();
		LayoutParams scrollViewParams = mScrollView.getLayoutParams();

		Display display = getWindowManager().getDefaultDisplay();
		// boolean isPortrait = display.getWidth() < display.getHeight();
		int width = display.getWidth() > 0 ? display.getWidth() : 100;// couldn't
																		// be
																		// smaller
		int height = display.getHeight() > 0 ? display.getHeight() : 100;// couldn't
																			// be
																			// smaller
		// final int width = isPortrait ? display.getWidth() :
		// display.getHeight();
		// final int height = isPortrait ? display.getHeight() :
		// display.getWidth();
		if (buttonParams != null) {
			buttonParams.width = buttonParams.height * 2;
			tempToggleButton.setLayoutParams(buttonParams);
		} else {
			buttonParams = new LayoutParams(160, 80);
			// buttonParams.width = 160;
			// buttonParams.height = 80;
			tempToggleButton.setLayoutParams(buttonParams);
		}

		// scrollViewParams.width = ScrollView.;
		// scrollViewParams.height = height - 160;
		// params.height = height;

		tempLinearLayout.setWeightSum((float) 1.0);
		mScrollView.setLayoutParams(scrollViewParams);
		tempLinearLayout.setOrientation(LinearLayout.VERTICAL);
		tempLinearLayout.setVisibility(LinearLayout.VISIBLE);

		tempLinearLayout.setEnabled(true);
		int rowCount = rowData.getCount();
		mTempInt = 0;
		// int numOfColumns = isPortrait ? 30 : 30;
		double rd = new Integer(rowCount).doubleValue();
		double numOfColumns = Math.sqrt(rowCount);
		double maxNumOfColumns = width / 125;
		if (numOfColumns > maxNumOfColumns) {
			numOfColumns = maxNumOfColumns;
		}

		int columnEntryCount = (int) (rowCount / numOfColumns);
		// colCount = colCount > 1 ? colCount : 1;
		// colCount = 3;
		int diff = rowData.getCount() - ((int) numOfColumns * columnEntryCount);

		int thisColumn = 1;

		for (int i = 0; i < rowData.getCount(); i++) {
			rowData.moveToPosition(i);
			String word = (rowData.getColumnIndex("word") > -1) ? rowData
					.getString(rowData.getColumnIndex("word")) : "";
			Long rowId = (rowData.getColumnIndex("_id") > -1) ? rowData
					.getLong(rowData.getColumnIndex("_id")) : 0;
			int status = (rowData.getColumnIndex("status") > -1) ? rowData
					.getInt(rowData.getColumnIndex("status")) : 0;
			// Boolean useRSA =
			// rowData.getString(rowData.getColumnIndex("useRSA")).contentEquals("true");
			Boolean useRSA = false;
			// Log.i(TAG,"Recipient name("+name+")");
			// April 17 2009, possibly this should be tempToggleButton =
			// baseButtonDesign.copy();
			tempToggleButton = new ToggleButton(this);
			tempToggleButton.setLayoutParams(mBaseButton.getLayoutParams());
			// tempToggleButton.setLayoutParams( new
			// LayoutParams(LayoutParams.FILL_PARENT,
			// LayoutParams.WRAP_CONTENT));
			tempToggleButton.setEllipsize(TextUtils.TruncateAt.MIDDLE);
			tempToggleButton.setText(word);
			tempToggleButton.setMinWidth(120);
			tempToggleButton.setMaxWidth(120);
			tempToggleButton.setMinHeight(80);
			tempToggleButton.setMaxHeight(80);
			tempToggleButton.setTextOn(word);
			tempToggleButton.setTextOff(word);
			// tempToggleButton.setEnabled(true);
			// tempToggleButton.setClickable(true);
			tempToggleButton.setChecked(useRSA);
			tempToggleButton.setOnCheckedChangeListener(this);
			tempToggleButton.setOnClickListener(this);
			tempToggleButton.setOnLongClickListener(this);
			tempToggleButton.setTag(rowId);

			tempLinearLayout.addView(tempToggleButton);
			mTempInt++;

			if (mTempInt >= columnEntryCount && thisColumn < numOfColumns) {
				thisColumn++;
				mTempInt = 0;
				mContactview.addView(tempLinearLayout);
				tempLinearLayout = new LinearLayout(this);
				// tempLinearLayout.setLayoutParams(mLinearLayout.getLayoutParams());
				tempLinearLayout.setWeightSum((float) 1.0);
				// tempLinearLayout.setLayoutParams(layoutParams);
				tempLinearLayout.setOrientation(LinearLayout.VERTICAL);
				tempLinearLayout.setVisibility(LinearLayout.VISIBLE);
				tempLinearLayout.setEnabled(true);
			}

		}

		if (mTempInt > 0) {
			mContactview.addView(tempLinearLayout);
		}

		rowData.close();
		mLinearLayout.addView(mContactview);

		mScrollView.scrollTo(width + 210, 10);

	}

	/*
	 * @Override protected void onPause() { // TODO Auto-generated method stub
	 * super.onPause(); mDataStore.close(); }//
	 */
	/*
	 * @Override protected void onResume() { // TODO Auto-generated method stub
	 * super.onResume(); mDataStore = new DbAdapter(this); mDataStore.loadDb();
	 * loadList(); }//
	 */

	public void onClick(View v) {

		mTempInt = v.getId();

		switch (v.getId()) {
		case BUTTON_ALPHA:
			// Log.i(TAG,"onClick() close mTempInt("+mTempInt+") alphaId("+BUTTON_ALPHA+")");
			finish();
			break;
		case BUTTON_BETA:
			// Log.i(TAG,"onClick() trigger beta");

			int rowId = Integer.parseInt(v.getTag().toString());
			if (rowId > 0) {
				// mDataStore.updateEntry("recipientStore", rowId, new String[]
				// {"useRSA"}, new String[] {useRSA});
				mDataStore.deleteEntry("recipientStore", rowId);
				v.setVisibility(Button.INVISIBLE);
				// mTriggerTwo.setText("");
				// mTriggerTwo.setTag(0);
			}
			// Open an editing pane for a reply to this originator
			// Intent i2 = new Intent(Intent.ACTION_SEND);
			// i2.putExtra(Intent.EXTRA_TEXT, "Body");
			// i2.putExtra(Intent.EXTRA_SUBJECT, "Header");
			// i2.putExtra("sms_body", mText);
			// i2.setType("vnd.android-dir/mms-sms");
			// startActivity(Intent.createChooser(i2, "SMS"));
			// int rowId = this.
			// Toast.makeText(this,
			// "getSelectedItemPosition("+this.getSelectedItemPosition()+")\ngetSelectedItemId("+this.getSelectedItemId()+")",
			// 1500).show();
		case BUTTON_GAMMA:
			// Log.i(TAG,"onClick() trigger gamma");
			// Send an acknowledgment to the message originator
			Intent sendIntent = new Intent(this, RSASMS.class);
			sendIntent.putExtra("messageid", mMessageId);
			// sendIntent.setType("message/rfc822");
			// startActivity(Intent.createChooser(sendIntent, "RSASMS"));

			startActivity(sendIntent);
		}

	}

	public void onCheckedChanged(CompoundButton v, boolean isChecked) {
		int rowId = Integer.parseInt(v.getTag().toString());
		String word = mDataStore.getString("wordStore", rowId, "word");
		String sentence = mSharedPreferences.contains("sentence") ? mSharedPreferences
				.getString("sentence", "") : "";
		if (isChecked) {
			// Add
			sentence = sentence.replaceFirst("\\.$", ". ").replaceFirst(
					"[A-Z][a-z]+,$", "\n  ");
			sentence += " " + word;

		} else {
			// Remove
			sentence = sentence.replaceFirst(" " + word, "").replaceFirst(word,
					"");
		}
		mPreferencesEditor.putString("sentence", sentence).commit();
		this.setTitle(sentence.replaceAll("\n", " ").trim());
		// Log.i(TAG,"onCheckedChange() rowId("+rowId+") isSelected("+v.isSelected()+") isClickable("+v.isClickable()+") isFocused("+v.isFocused()+")");
		// String useRSA = "true";
		// if( !isChecked ){ useRSA = "false"; }
		// mDataStore.updateEntry("recipientStore", rowId, new String[]
		// {"useRSA"}, new String[] {useRSA});
	}

	public boolean onLongClick(View v) {
		int rowId = Integer.parseInt(v.getTag().toString());
		String word = mDataStore.getString("wordStore", rowId, "word");
		Toast.makeText(
				this,
				word
						+ ": Definition goes here.  This can possibly be large so confirm the ability to read a longer sentence, such as this one.  Of course you can always longpress the word again.",
				3 * 1880).show();
		// Long Press should show definition, maybe in Toast for 5 * 1880
		// seconds.
		/*
		 * int status = 0; status = mDataStore.getInteger("recipientStore",
		 * rowId, "status");
		 * 
		 * //Log.w(TAG,"First time using getInteger command. value=" + status);
		 * if( status == 0 ){ mDataStore.updateEntry("recipientStore", rowId,
		 * "status", "1"); v.setVisibility(Button.VISIBLE);
		 * //v.setEnabled(true); }else{ mDataStore.deleteEntry("recipientStore",
		 * rowId); v.setVisibility(Button.INVISIBLE); //v.setEnabled(false);
		 * //v.clearFocus(); //v.setClickable(true);
		 * mTriggerOne.requestFocusFromTouch(); } v.clearFocus();
		 * //mTriggerTwo.setText(""); //mTriggerTwo.setTag(0); //
		 */
		return false;
	}

}
