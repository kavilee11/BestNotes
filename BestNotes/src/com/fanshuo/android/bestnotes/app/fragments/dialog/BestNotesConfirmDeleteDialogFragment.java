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
public class BestNotesConfirmDeleteDialogFragment extends DialogFragment {

	private ConfirmDeleteDialogCallBack callBack;

	public interface ConfirmDeleteDialogCallBack {
		public void onPositiveButtonClick();
	}

	public BestNotesConfirmDeleteDialogFragment() {

	}

	public BestNotesConfirmDeleteDialogFragment(ConfirmDeleteDialogCallBack callBack) {
		super();
		this.callBack = callBack;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(getResources().getString(R.string.confirm_delete))
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								callBack.onPositiveButtonClick();
								BestNotesConfirmDeleteDialogFragment.this
										.dismiss();
							}
						})
				.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								BestNotesConfirmDeleteDialogFragment.this
										.dismiss();
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}
}
