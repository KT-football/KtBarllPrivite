package com.ktfootball.app.UI.Activity.train;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.kt.ktball.App;
import com.ktfootball.app.Adapter.TrainingLevelAdapter;
import com.ktfootball.app.Base.BaseRecyclerView;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.UserAppCartoonsStudy;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.UserAppCartoonsStudyRequest;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by jy on 16/6/16.
 */
public class TrainingLevelActivity extends BaseRecyclerView {

    @Override
    protected void initToolBar() {
        setToolBarTitle(getString(R.string.train_lv));
    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doUserAppCartoonsStudy();
            }
        });
        getToolbar().setOnMenuItemClickListener(onMenuItemClick);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setBackgroundResource(R.drawable.bg_train);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getThis());
        setLayoutManager(mLayoutManager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        doUserAppCartoonsStudy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_i:
                    showPopupWindow(getToolbar());
                    break;
            }
            return true;
        }
    };

    private void showPopupWindow(View view) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getThis()).inflate(
                R.layout.pop_window_train, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("mengdd", "onTouch : ");
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_null));
        // 设置好参数之后再show
//        int[] location = new int[2];
//        view.getLocationOnScreen(location);
        popupWindow.showAsDropDown(view);
//        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1] - popupWindow.getHeight());
    }

    private void doUserAppCartoonsStudy() {
        setRefreshing(true);
        UserAppCartoonsStudyRequest request = new UserAppCartoonsStudyRequest(Constants.USER_APP_CARTOONS_STUDY, RequestMethod.GET);
        request.add("user_id", App.getUserLogin().user_id + "");
        CallServer.getRequestInstance().add(this, 0, request, httpListener, false, false);
    }

    private HttpListener httpListener = new HttpListener<UserAppCartoonsStudy>() {
        @Override
        public void onSucceed(int what, Response<UserAppCartoonsStudy> response) {
            setRefreshing(false);
            UserAppCartoonsStudy userAppCartoons = response.get();
            if (userAppCartoons.response.equals("success")) {
                init(userAppCartoons);
            } else {
                showToast(R.string.request_failed);
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            setRefreshing(false);
        }
    };

    private void init(UserAppCartoonsStudy userAppCartoons) {
        TrainingLevelAdapter adapter = new TrainingLevelAdapter(userAppCartoons.app_cartoons_study, getThis());
        setAdapter(adapter);
    }
}
