package com.fanshuo.android.bestnotes.inapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.fanshuo.android.bestnotes.R;
import com.fanshuo.android.bestnotes.app.model.ProductInfo;
import com.fanshuo.android.bestnotes.app.utils.ActivityUtil;
import com.fanshuo.android.bestnotes.app.utils.Debug;
import com.fanshuo.android.bestnotes.inapp.utils.IabHelper;
import com.fanshuo.android.bestnotes.inapp.utils.IabResult;
import com.fanshuo.android.bestnotes.inapp.utils.Inventory;
import com.fanshuo.android.bestnotes.inapp.utils.Purchase;

/**
 * @author fanshuo
 * @date 2012-12-21
 */
public class InAppUtil {

	Context context;
	public static ProductInfo checkPointsProduct = new ProductInfo("38.0", "HKD", "捐助小白笔记", "bestnotes_donate");
	IabHelper mHelper;
	public static final int REQUEST_CODE_PURCHASE = 10001;
	private String checkProductId;//要检查的产品ID

	public InAppUtil(final Context context, String checkProductId) {
		super();
		this.context = context;
		this.checkProductId = checkProductId;
		String base64EncodedPublicKey = "";
		mHelper = new IabHelper(context, base64EncodedPublicKey);
		mHelper.enableDebugLogging(true);
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				if (!result.isSuccess()) {
					// TODO 改成多语言，并屏蔽专业字眼
					System.out.println("[Pay ERROR]" + "Problem setting up in-app billing: " + result);
					return;
				}
				mHelper.queryInventoryAsync(mGotInventoryListener);
			}
		});
	}

	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result,
				Inventory inventory) {
			if (result.isFailure()) {
				// complain("Failed to query inventory: " + result);
				// 失败，不开启关卡
				alert("失败");
				return;
			}
			// 如果购买过
			if (inventory.hasPurchase(checkProductId)) {
				// 已经购买过，提示已经购买
				alert("成功");
			} else {
				// 没有购买过，进入购买页面
			}

		}
	};

	/**
	 * 购买关卡
	 */
	public void purchaseCheckPoints() {
		mHelper.launchPurchaseFlow((Activity) context, checkPointsProduct.getProductId(),
				REQUEST_CODE_PURCHASE, mPurchaseFinishedListener_checkpoints);
	}

	// 购买关卡成功回调
	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener_checkpoints = new IabHelper.OnIabPurchaseFinishedListener() {

		@Override
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			if (result.isFailure()) {
				ActivityUtil.showCenterShortToast(context, R.string.faild_to_donate);
				return;
			}
			if (purchase.getSku().equals(checkPointsProduct.getProductId())) {
				alert(context.getResources().getString(R.string.thank_for_donate));
			}
		}

	};

	void alert(String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(context);
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		bld.create().show();
	}

	public boolean handleResult(int requestCode, int resultCode, Intent data) {
		return mHelper.handleActivityResult(requestCode, resultCode, data);
	}


}
