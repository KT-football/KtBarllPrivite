package com.ktfootball.app.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ktfootball.app.R;

import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by leo on 16/10/14.
 */

public class MyBGARefreshViewHolder extends BGARefreshViewHolder {
    private TextView mHeaderStatusTv;
    private ImageView mHeaderArrowIv;
    private ImageView mHeaderChrysanthemumIv;
//    private AnimationDrawable mHeaderChrysanthemumAd;
    private RotateAnimation mUpAnim;
    private RotateAnimation mDownAnim;

    private RotateAnimation mRotateAnimation;


    private RotateAnimation mFootRotateAnimation;


    private String mPullDownRefreshText = "下拉刷新..";
    private String mReleaseRefreshText = "释放更新..";
    private String mRefreshingText = "Loading...";
    /**
     * 整个加载更多控件的背景颜色资源id
     */
    private int mLoadMoreBackgroundColorRes = -1;

    /**
     * 是否开启加载更多功能
     */
    private boolean mIsLoadingMoreEnabled = true;
    public MyBGARefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
        mPullDownRefreshText = context.getString(R.string.pull_refresh);
        mReleaseRefreshText = context.getString(R.string.shfang_refresh);
        initAnimation();
    }

    private void initAnimation() {
        mUpAnim = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mUpAnim.setDuration(300);
        mUpAnim.setFillAfter(true);

        mDownAnim = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mDownAnim.setFillAfter(true);

        mRotateAnimation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(1000);
        mRotateAnimation.setRepeatCount(-1);

        mFootRotateAnimation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mFootRotateAnimation.setInterpolator(new LinearInterpolator());
        mFootRotateAnimation.setDuration(1000);
        mFootRotateAnimation.setRepeatCount(-1);

    }

    /**
     * 设置未满足刷新条件，提示继续往下拉的文本
     *
     * @param pullDownRefreshText
     */
    public void setPullDownRefreshText(String pullDownRefreshText) {
        mPullDownRefreshText = pullDownRefreshText;
    }


    /**
     * 整个加载更多控件的背景drawable资源id
     */
    private int mLoadMoreBackgroundDrawableRes = -1;

    /**
     * 设置满足刷新条件时的文本
     *
     * @param releaseRefreshText
     */
    public void setReleaseRefreshText(String releaseRefreshText) {
        mReleaseRefreshText = releaseRefreshText;
    }

    /**
     * 设置正在刷新时的文本
     *
     * @param refreshingText
     */
    public void setRefreshingText(String refreshingText) {
        mRefreshingText = refreshingText;
    }

    @Override
    public View getLoadMoreFooterView() {
        if (!mIsLoadingMoreEnabled) {
            return null;
        }
        if (mLoadMoreFooterView == null) {
            mLoadMoreFooterView = View.inflate(mContext, R.layout.view_normal_refresh_my_footer, null);
            mLoadMoreFooterView.setBackgroundColor(Color.TRANSPARENT);
            if (mLoadMoreBackgroundColorRes != -1) {
                mLoadMoreFooterView.setBackgroundResource(mLoadMoreBackgroundColorRes);
            }
            if (mLoadMoreBackgroundDrawableRes != -1) {
                mLoadMoreFooterView.setBackgroundResource(mLoadMoreBackgroundDrawableRes);
            }
            mFooterStatusTv = (TextView) mLoadMoreFooterView.findViewById(cn.bingoogolapple.refreshlayout.R.id.tv_normal_refresh_footer_status);
            mFooterChrysanthemumIv = (ImageView) mLoadMoreFooterView.findViewById(cn.bingoogolapple.refreshlayout.R.id.iv_normal_refresh_footer_chrysanthemum);
            mFooterChrysanthemumIv.setAnimation(mFootRotateAnimation);
            mFooterStatusTv.setText(mRefreshingText);
        }
        return mLoadMoreFooterView;
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, R.layout.view_refresh_my_header, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }
            mHeaderStatusTv = (TextView) mRefreshHeaderView.findViewById(cn.bingoogolapple.refreshlayout.R.id.tv_normal_refresh_header_status);
            mHeaderArrowIv = (ImageView) mRefreshHeaderView.findViewById(cn.bingoogolapple.refreshlayout.R.id.iv_normal_refresh_header_arrow);
            mHeaderChrysanthemumIv = (ImageView) mRefreshHeaderView.findViewById(cn.bingoogolapple.refreshlayout.R.id.iv_normal_refresh_header_chrysanthemum);
//            mHeaderChrysanthemumAd = (AnimationDrawable) mHeaderChrysanthemumIv.getDrawable();
            mHeaderChrysanthemumIv.setAnimation(mRotateAnimation);
            mHeaderStatusTv.setText(mPullDownRefreshText);
        }
        return mRefreshHeaderView;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
    }

    @Override
    public void changeToIdle() {
    }

    @Override
    public void changeToPullDown() {
        mHeaderStatusTv.setText(mPullDownRefreshText);
        mHeaderChrysanthemumIv.setVisibility(View.INVISIBLE);
//        mHeaderChrysanthemumAd.stop();
        mHeaderArrowIv.setVisibility(View.VISIBLE);
        mDownAnim.setDuration(150);
        mHeaderArrowIv.startAnimation(mDownAnim);
        mRotateAnimation.cancel();

    }

    @Override
    public void changeToReleaseRefresh() {
        mHeaderStatusTv.setText(mReleaseRefreshText);
        mHeaderChrysanthemumIv.setVisibility(View.INVISIBLE);
//        mHeaderChrysanthemumAd.stop();
        mHeaderArrowIv.setVisibility(View.VISIBLE);
        mRotateAnimation.cancel();
        mHeaderArrowIv.startAnimation(mUpAnim);
    }

    @Override
    public void changeToRefreshing() {
        mHeaderStatusTv.setText(mRefreshingText);
        // 必须把动画清空才能隐藏成功
        mHeaderArrowIv.clearAnimation();
        mHeaderArrowIv.setVisibility(View.INVISIBLE);
        mHeaderChrysanthemumIv.setVisibility(View.VISIBLE);
       mRotateAnimation.startNow();


//        mHeaderChrysanthemumAd.start();
    }


    /**
     * 设置整个加载更多控件的背景drawable资源id
     *
     * @param loadMoreBackgroundDrawableRes
     */
    public void setLoadMoreBackgroundDrawableRes(@DrawableRes int loadMoreBackgroundDrawableRes) {
        mLoadMoreBackgroundDrawableRes = loadMoreBackgroundDrawableRes;
    }



    /**
     * 设置整个加载更多控件的背景颜色资源id
     *
     * @param loadMoreBackgroundColorRes
     */
    public void setLoadMoreBackgroundColorRes(@ColorRes int loadMoreBackgroundColorRes) {
        mLoadMoreBackgroundColorRes = loadMoreBackgroundColorRes;
    }


    @Override
    public void onEndRefreshing() {
        mHeaderStatusTv.setText(mPullDownRefreshText);
        mHeaderChrysanthemumIv.setVisibility(View.INVISIBLE);
//        mHeaderChrysanthemumAd.stop();
        mHeaderArrowIv.setVisibility(View.VISIBLE);
        mRotateAnimation.cancel();
        mDownAnim.setDuration(0);
        mHeaderArrowIv.startAnimation(mDownAnim);
    }

    /**
     * 进入加载更多状态
     */
    public void changeToLoadingMore() {
        if (mIsLoadingMoreEnabled && mFootRotateAnimation != null) {
            mFootRotateAnimation.start();
        }
    }

    /**
     * 结束上拉加载更多
     */
    public void onEndLoadingMore() {
        if (mIsLoadingMoreEnabled && mFootRotateAnimation != null) {
            mFootRotateAnimation.cancel();
        }
    }
}

