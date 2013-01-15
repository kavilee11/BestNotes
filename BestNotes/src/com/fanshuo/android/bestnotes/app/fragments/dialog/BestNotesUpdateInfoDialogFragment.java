package com.fanshuo.android.bestnotes.app.fragments.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.R;

/**
 * @author shuo
 * @date 2013-1-14 下午6:12:34
 * @version V1.0
 */
public class BestNotesUpdateInfoDialogFragment extends DialogFragment {

	String link;
	String versionInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		link = getArguments().getString(Constants.BundleKey.CHECK_UPDATE_LINK);
		versionInfo = getArguments().getString(
				Constants.BundleKey.CHECK_UPDATE_VERSION_INFO);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(versionInfo)
				.setTitle(R.string.update_info_title)
				.setPositiveButton(R.string.update,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent intent = new Intent();
								intent.setAction("android.intent.action.VIEW");
								Uri content_url = Uri.parse(link);
								intent.setData(content_url);
								intent.setClassName("com.android.browser",
										"com.android.browser.BrowserActivity");
								startActivity(intent);
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								BestNotesUpdateInfoDialogFragment.this
										.dismiss();
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}
}
