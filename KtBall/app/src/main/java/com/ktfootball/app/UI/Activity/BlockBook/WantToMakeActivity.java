package com.ktfootball.app.UI.Activity.BlockBook;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.utils.LogUtils;
import com.frame.app.utils.PhoneUtils;
import com.frame.app.utils.SharedPreferencesUtils;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.GetCityPrice;
import com.ktfootball.app.Entity.GetCitys;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.GetCityPriceRequest;
import com.ktfootball.app.Request.GetCitysRequest;
import com.ktfootball.app.Utils.DateUtil;
import com.ktfootball.app.Views.ListDialog;
import com.ktfootball.app.calendar.doim.CalendarViewBuilder;
import com.ktfootball.app.calendar.doim.CustomDate;
import com.ktfootball.app.calendar.widget.CalendarView;
import com.ktfootball.app.calendar.widget.CalendarViewPagerLisenter;
import com.ktfootball.app.calendar.widget.CustomViewPagerAdapter;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jy on 16/6/24.
 */
public class WantToMakeActivity extends BaseToolBarActivity2 implements View.OnClickListener, CalendarView.CallBack {

    private LinearLayout invoicetitle;
    private SwitchCompat switchCompat;
    private GetCitys citys;
    private TextView city;
    public static GetCitys.City currentCity;
    private ListDialog listDialog;
    private ViewPager viewPager;
//    private TextView showMonthView;
//    private TextView showYearView;
//    private TextView showWeekView;
    private CalendarView[] views;
    private CalendarViewBuilder builder = new CalendarViewBuilder();
    private CustomDate mClickDate;
    private static GetCityPrice cityPrice;
    private TextView next;
    private String clickDate;
    private EditText lianxiren;
    private EditText phone;
    private EditText num;
    private EditText dizhi;
    private EditText ftitle;
    private LinearLayout call;
    private static HashMap<String,String> dateMap;
    private CustomViewPagerAdapter<CalendarView> viewPagerAdapter;
    private RelativeLayout shangImg;
    private RelativeLayout nextImg;
    private TextView showDateView;
    private AppCompatSpinner province;
    private ArrayList<String> provinces = new ArrayList<>();

    @Override
    protected void initToolBar() {
        setToolBarTitle(getString(R.string.write_info));
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
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    invoicetitle.setVisibility(View.VISIBLE);
                } else {
                    invoicetitle.setVisibility(View.GONE);
                }
            }
        });
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lianxirenStr = lianxiren.getText().toString();
                String phoneStr = phone.getText().toString();
                String numStr = num.getText().toString();
                String dizhiStr = dizhi.getText().toString();
                String ftitleStr = ftitle.getText().toString();
                if (currentCity == null) {
                    showToast(getString(R.string.choose_city));
                } else if (lianxirenStr == null || "".equals(lianxirenStr)) {
                    showToast(getString(R.string.write_user));
                } else if (phoneStr == null || "".equals(phoneStr)) {
                    showToast(getString(R.string.write_type));
                } else if (numStr == null || "".equals(numStr)) {
                    showToast(getString(R.string.write_people_cont));
                } else if (dizhiStr == null || "".equals(dizhiStr)) {
                    showToast(getString(R.string.write_address));
                } else if (switchCompat.isChecked() && (ftitleStr == null || "".equals(ftitleStr))) {
                    showToast(getString(R.string.write_taitou));
                } else {
                    Intent intent = new Intent(getThis(), WeekSelectActivity.class);
                    intent.putExtra("GetCityPrice", cityPrice);
                    intent.putExtra("cityid", currentCity.city_id);
                    intent.putExtra("lianxirenStr", lianxirenStr);
                    intent.putExtra("phoneStr", phoneStr);
                    intent.putExtra("numStr", numStr);
                    intent.putExtra("dizhiStr", dizhiStr);
                    intent.putExtra("ftitleStr", ftitleStr);
                    intent.putExtra("clickDate", clickDate);
                    startActivityForResult(intent, Constants.TO_WEEKSELECT_AVTIVITY);
                }
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用intent启动拨打电话
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4000062666"));
                startActivity(intent);
            }
        });
        nextImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager != null){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
            }
        });
        shangImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager != null){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
                }
            }
        });
    }

    private void showListDialog() {
//        listDialog = new ListDialog(getThis(), citys.citys, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                listDialog.dismiss();
//            }
//        });
//        listDialog.show();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showLoadingDiaglog();
        doGetCitys();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.layout_wanttomake);
        setBackgroundResource(R.drawable.bg_blockbook);
        invoicetitle = (LinearLayout) findViewById(R.id.layout_wanttomake_invoicetitle);
        switchCompat = (SwitchCompat) findViewById(R.id.layout_wanttomake_switchcompat);
        city = (TextView) findViewById(R.id.layout_wanttomake_city);
        next = (TextView) findViewById(R.id.layout_wanttomake_next);
        lianxiren = (EditText) findViewById(R.id.layout_wanttomake_lianxiren);
        phone = (EditText) findViewById(R.id.layout_wanttomake_phone);
        num = (EditText) findViewById(R.id.layout_wanttomake_num);
        dizhi = (EditText) findViewById(R.id.layout_wanttomake_dizhi);
        ftitle = (EditText) findViewById(R.id.layout_wanttomake_title);
        call = (LinearLayout) findViewById(R.id.layout_wanttomake_call);
        province = (AppCompatSpinner) findViewById(R.id.spinner_province);

        lianxiren.setText(SharedPreferencesUtils.getString(getThis(),"lianxiren",""));
        phone.setText(SharedPreferencesUtils.getString(getThis(),"phone",""));
        num.setText(SharedPreferencesUtils.getString(getThis(),"num",""));
        dizhi.setText(SharedPreferencesUtils.getString(getThis(),"dizhi",""));
        ftitle.setText(SharedPreferencesUtils.getString(getThis(),"ftitle",""));

        viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        showDateView = (TextView) this.findViewById(R.id.show_date_view);
        nextImg = (RelativeLayout) findViewById(R.id.show_date_view_next);
        shangImg = (RelativeLayout) findViewById(R.id.show_date_view_shang);
        views = builder.createMassCalendarViews(this, 5, this);//数字为viewpager页数（为3时，到当前月份才加载）
        setViewPager();
    }

    private void setViewPager() {
        viewPagerAdapter = new CustomViewPagerAdapter<CalendarView>(views);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(498);
        viewPager.setOnPageChangeListener(new CalendarViewPagerLisenter(viewPagerAdapter));
    }

    private void doGetCityPrice(String city_id, String start_date, String end_date) {
        GetCityPriceRequest request = new GetCityPriceRequest(Constants.GET_CITY_PRICE, RequestMethod.GET);
        request.add("city_id", city_id);
        request.add("start_date", start_date);
        request.add("end_date", end_date);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<GetCityPrice>() {
            @Override
            public void onSucceed(int what, Response<GetCityPrice> response) {
                closeLoadingDialog();
                cityPrice = response.get();
                dateMap = new HashMap<>();
                for (GetCityPrice.CityPrice c : cityPrice.city_price) {
                    dateMap.put(c._date,c.prices.get(0));
                }
                if(viewPagerAdapter != null){
                    viewPagerAdapter.getPrimaryItem().invalidate();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                closeLoadingDialog();
            }
        }, false, false);
    }

    private void doGetCitys() {
        GetCitysRequest request = new GetCitysRequest(Constants.GET_CITYS, RequestMethod.GET);
        CallServer.getRequestInstance().add(this, 0, request, httpListener, false, false);
    }

    private HttpListener httpListener = new HttpListener<GetCitys>() {
        @Override
        public void onSucceed(int what, Response<GetCitys> response) {
            closeLoadingDialog();
            citys = response.get();
            for(GetCitys.City city :citys.citys){
                provinces.add(city.name);
            }
            province.setAdapter(new ArrayAdapter<String>(getThis(), R.layout.item_spinner, R.id.tv,
                    provinces));
            province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                city.setAdapter(new ArrayAdapter<String>(getThis(), R.layout.item_spinner, R.id.tv,
//                        cities.get(position)));
                    currentCity = citys.citys.get(position);
                    String currentDate = DateUtil.getYear() + "-" + DateUtil.getMonth() + "-" + DateUtil.getCurrentMonthDay();
                    String end_date = (DateUtil.getYear() + 1) + "-" + DateUtil.getMonth() + "-" + DateUtil.getCurrentMonthDay();
                    LogUtils.e(currentDate);
                    city.setText(currentCity.name);
                    showLoadingDiaglog();
                    doGetCityPrice(currentCity.city_id, currentDate, end_date);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            closeLoadingDialog();
        }
    };

    @Override
    public void clickDate(CustomDate date) {
        mClickDate = date;
        clickDate = date.year + "-" + date.month + "-" + date.day;
        LogUtils.e(clickDate);
    }

    @Override
    public void onMesureCellHeight(int cellSpace) {

    }

    @Override
    public void changeDate(CustomDate date) {
        setShowDateViewText(date.year, date.month);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public void setShowDateViewText(int year, int month) {
//        showYearView.setText();
//        showMonthView.setText(month + "月");
//        showWeekView.setText(DateUtil.weekName[DateUtil.getWeekDay() - 1]);
        showDateView.setText(year + getString(R.string.year)+month+getString(R.string.month));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.TO_WEEKSELECT_AVTIVITY && resultCode == Constants.PAY_SUCCESS) {
            setResult(Constants.PAY_SUCCESS);
            finish();
        }
    }

    public static String selectPay(String date) {
        if(cityPrice != null && dateMap != null){
            String d = dateMap.get(date);
            if(d == null){
                d = "";
            }else{
                d = "￥" + d;
            }
            return d;
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentCity = null;
        dateMap = null;

        SharedPreferencesUtils.saveString(getThis(),"lianxiren",lianxiren.getText().toString());
        SharedPreferencesUtils.saveString(getThis(),"phone",phone.getText().toString());
        SharedPreferencesUtils.saveString(getThis(),"num",num.getText().toString());
        SharedPreferencesUtils.saveString(getThis(),"dizhi",dizhi.getText().toString());
        SharedPreferencesUtils.saveString(getThis(),"ftitle",ftitle.getText().toString());
    }
}
