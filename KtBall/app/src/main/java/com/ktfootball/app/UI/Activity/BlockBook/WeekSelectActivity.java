package com.ktfootball.app.UI.Activity.BlockBook;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.utils.LogUtils;
import com.kt.ktball.App;
import com.ktfootball.app.Adapter.DateAdapter;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.CreateOrder;
import com.ktfootball.app.Entity.GetCityPrice;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.CreateOrderRequest;
import com.ktfootball.app.Utils.SpecialCalendar;
import com.ktfootball.app.calendar.doim.CalendarViewBuilder;
import com.ktfootball.app.calendar.doim.CustomDate;
import com.ktfootball.app.calendar.widget.CalendarView;
import com.ktfootball.app.calendar.widget.CalendarViewPagerLisenter;
import com.ktfootball.app.calendar.widget.CustomViewPagerAdapter;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jy on 16/6/27.
 */
public class WeekSelectActivity extends BaseToolBarActivity2 implements GestureDetector.OnGestureListener {

    private GetCityPrice cityPrice;
    private TextView riqi;
    private TextView price_1;
    private TextView price_2;
    private TextView price_3;
    private String am;
    private String pm;
    private String night;
    private String clickDate;
    private String cityid;
    private String lianxirenStr;
    private String phoneStr;
    private String numStr;
    private String dizhiStr;
    private String ftitleStr;
    private LinearLayout price_1_ll;
    private LinearLayout price_2_ll;
    private LinearLayout price_3_ll;

    private ViewFlipper flipper1 = null;
    private GridView gridView = null;
    private GestureDetector gestureDetector = null;
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private int week_c = 0;
    private int week_num = 0;
    private DateAdapter dateAdapter;
    private int daysOfMonth = 0; // 某月的天数
    private int dayOfWeek = 0; // 具体某一天是星期几
    private int weeksOfMonth = 0;
    private SpecialCalendar sc = null;
    private boolean isLeapyear = false; // 是否为闰年
    private int selectPostion = 0;
    private String dayNumbers[] = new String[7];
    private int currentYear;
    private int currentMonth;
    private int currentWeek;
    private int currentDay;
    private int currentNum;
    private String currentClickDay;


    @Override
    protected void initToolBar() {
        setToolBarTitle(getString(R.string.choose_time));
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
        price_1_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCreateOrder("0",am);
            }
        });

        price_2_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCreateOrder("1",pm);
            }
        });

        price_3_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCreateOrder("2",night);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        cityPrice = (GetCityPrice) getIntent().getSerializableExtra("GetCityPrice");
        cityid = getIntent().getStringExtra("cityid");
        lianxirenStr = getIntent().getStringExtra("lianxirenStr");
        phoneStr = getIntent().getStringExtra("phoneStr");
        numStr = getIntent().getStringExtra("numStr");
        dizhiStr = getIntent().getStringExtra("dizhiStr");
        ftitleStr = getIntent().getStringExtra("ftitleStr");
        clickDate = getIntent().getStringExtra("clickDate");

        year_c = Integer.parseInt(clickDate.split("-")[0]);
        month_c = Integer.parseInt(clickDate.split("-")[1]);
        day_c = Integer.parseInt(clickDate.split("-")[2]);
        currentYear = year_c;
        currentMonth = month_c;
        currentDay = day_c;
        sc = new SpecialCalendar();
        getCalendar(year_c, month_c);
        week_num = getWeeksOfMonth();
        currentNum = week_num;
        if (dayOfWeek == 7) {
            week_c = day_c / 7 + 1;
        } else {
            if (day_c <= (7 - dayOfWeek)) {
                week_c = 1;
            } else {
                if ((day_c - (7 - dayOfWeek)) % 7 == 0) {
                    week_c = (day_c - (7 - dayOfWeek)) / 7 + 1;
                } else {
                    week_c = (day_c - (7 - dayOfWeek)) / 7 + 2;
                }
            }
        }
        currentWeek = week_c;
        getCurrent();


        riqi.setText(year_c + getString(R.string.year) + month_c + getString(R.string.month) + day_c + getString(R.string.day));
        gestureDetector = new GestureDetector(this);
        clickDate(year_c,month_c,day_c);
        dateAdapter = new DateAdapter(this, getResources(), currentYear,
                currentMonth, currentWeek, currentNum, selectPostion,
                currentWeek == 1 ? true : false);
        currentClickDay = year_c + getString(R.string.year) + month_c + getString(R.string.month) + day_c + getString(R.string.day);
        dateAdapter.setClickDay(year_c + getString(R.string.year) + month_c + getString(R.string.month) + day_c + getString(R.string.day));
        addGridView();
        dayNumbers = dateAdapter.getDayNumbers();
        gridView.setAdapter(dateAdapter);
        selectPostion = dateAdapter.getTodayPosition();
        gridView.setSelection(selectPostion);
        flipper1.addView(gridView, 0);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.layout_weekselect);
        setBackgroundResource(R.drawable.bg_blockbook);
        riqi = (TextView) this.findViewById(R.id.layout_weekselect_riqi);
        price_1 = (TextView) this.findViewById(R.id.layout_weekselect_price_1);
        price_2 = (TextView) this.findViewById(R.id.layout_weekselect_price_2);
        price_3 = (TextView) this.findViewById(R.id.layout_weekselect_price_3);
        price_1_ll = (LinearLayout) this.findViewById(R.id.layout_weekselect_price_1_ll);
        price_2_ll = (LinearLayout) this.findViewById(R.id.layout_weekselect_price_2_ll);
        price_3_ll = (LinearLayout) this.findViewById(R.id.layout_weekselect_price_3_ll);
        flipper1 = (ViewFlipper) findViewById(R.id.flipper1);
    }

    public void clickDate(int year,int month,int day) {
        String monthStr = ""+month;
        String dayStr = ""+day;
        if (month < 10) {
            monthStr = "0" + month;
        }
        if (day < 10) {
            dayStr = "0" + day;
        }
        String dataStr = year + "-" + monthStr + "-" + dayStr;
        LogUtils.e(dataStr);
        for (GetCityPrice.CityPrice c : cityPrice.city_price) {
            if (dataStr.equals(c._date)) {
                setPrice(c.prices);
            }
        }
    }

    private void setPrice(List<String> prices) {
        am = prices.get(0);
        pm = prices.get(1);
        night = prices.get(2);
        price_1.setText("￥" + am);
        price_2.setText("￥" + pm);
        price_3.setText("￥" + night);
    }

//    contact: 联系人,
//    mobile: 联系电话,
//    number_of_people: 参与人数,
//    address: 活动地址,
//    date: 活动日期,
//    time: 活动时间 0: 上午 1: 中午 2: 晚上,
//    invoice_title: 发票抬头,
//    city_id: 城市id,
//    kuaidi_address: 快递地址,
//    kuaidi_contact: 收货人,
//    kuaidi_mobile: 收货人电话,
//    user_id: 当前下单用户id

    private void doCreateOrder(String time, final String pay){
        CreateOrderRequest request = new CreateOrderRequest(Constants.CREATE_ORDER, RequestMethod.POST);
        request.add("user_id", App.getUserLogin().user_id);
//        request.add("contact", "蒋昱");
//        request.add("mobile", "18521565624");
//        request.add("number_of_people", "4");
//        request.add("address", "万科十一区");
//        request.add("date", clickDate);
//        request.add("time", time);
//        request.add("invoice_title", "");
//        request.add("city_id", cityid);
//        request.add("kuaidi_address", "万科十一区");
//        request.add("kuaidi_contact", "蒋昱");
//        request.add("kuaidi_mobile", "18521565624");
        request.add("contact", lianxirenStr);
        request.add("mobile", phoneStr);
        request.add("number_of_people", numStr);
        request.add("address", dizhiStr);
        request.add("date", clickDate);
        request.add("time", time);
        request.add("invoice_title", ftitleStr);
        request.add("city_id", cityid);
        request.add("kuaidi_address", dizhiStr);
        request.add("kuaidi_contact", lianxirenStr);
        request.add("kuaidi_mobile", phoneStr);
        showLoadingDiaglog();
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<CreateOrder>() {
            @Override
            public void onSucceed(int what, Response<CreateOrder> response) {
                closeLoadingDialog();
                if(response.get().response.equals("success")){
                    LogUtils.e(response.get().bc_order_number+"");
                    Intent intent = new Intent(getThis(),PaymentActivity.class);
                    intent.putExtra("orderid",response.get().bc_order_number);
                    intent.putExtra("pay",pay);
                    startActivityForResult(intent,Constants.TO_PAY_AVTIVITY);
                }else{
                    LogUtils.e(response.get().msg+"");
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                closeLoadingDialog();
            }
        }, false, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.TO_PAY_AVTIVITY && resultCode == Constants.PAY_SUCCESS){
            setResult(Constants.PAY_SUCCESS);
            finish();
        }
    }



    /**
     * 判断某年某月所有的星期数
     *
     * @param year
     * @param month
     */
    public int getWeeksOfMonth(int year, int month) {
        // 先判断某月的第一天为星期几
        int preMonthRelax = 0;
        int dayFirst = getWhichDayOfWeek(year, month);
        int days = sc.getDaysOfMonth(sc.isLeapYear(year), month);
        if (dayFirst != 7) {
            preMonthRelax = dayFirst;
        }
        if ((days + preMonthRelax) % 7 == 0) {
            weeksOfMonth = (days + preMonthRelax) / 7;
        } else {
            weeksOfMonth = (days + preMonthRelax) / 7 + 1;
        }
        return weeksOfMonth;

    }

    /**
     * 判断某年某月的第一天为星期几
     *
     * @param year
     * @param month
     * @return
     */
    public int getWhichDayOfWeek(int year, int month) {
        return sc.getWeekdayOfMonth(year, month);

    }

    /**
     *
     * @param year
     * @param month
     */
    public int getLastDayOfWeek(int year, int month) {
        return sc.getWeekDayOfLastMonth(year, month,
                sc.getDaysOfMonth(isLeapyear, month));
    }

    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year); // 是否为闰年
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
        dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
    }

    public int getWeeksOfMonth() {
        // getCalendar(year, month);
        int preMonthRelax = 0;
        if (dayOfWeek != 7) {
            preMonthRelax = dayOfWeek;
        }
        if ((daysOfMonth + preMonthRelax) % 7 == 0) {
            weeksOfMonth = (daysOfMonth + preMonthRelax) / 7;
        } else {
            weeksOfMonth = (daysOfMonth + preMonthRelax) / 7 + 1;
        }
        return weeksOfMonth;
    }

    private void addGridView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        gridView = new GridView(this);
        gridView.setNumColumns(7);
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return WeekSelectActivity.this.gestureDetector.onTouchEvent(event);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectPostion = position;
                dateAdapter.setSeclection(position);
                currentClickDay = dateAdapter.getCurrentYear(selectPostion) + getString(R.string.year)
                        + dateAdapter.getCurrentMonth(selectPostion) + getString(R.string.month)
                        + dayNumbers[position] + getString(R.string.day);
                riqi.setText(currentClickDay);
                dateAdapter.setClickDay(currentClickDay);
                clickDate = dateAdapter.getCurrentYear(selectPostion)+"-"+dateAdapter.getCurrentMonth(selectPostion) + "-"+dayNumbers[position];
                dateAdapter.notifyDataSetChanged();
                clickDate(dateAdapter.getCurrentYear(selectPostion),dateAdapter.getCurrentMonth(selectPostion),Integer.parseInt(dayNumbers[position]));

            }
        });
        gridView.setLayoutParams(params);
    }

    @Override
    public boolean onDown(MotionEvent e) {

        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 重新计算当前的年月
     */
    public void getCurrent() {
        if (currentWeek > currentNum) {
            if (currentMonth + 1 <= 12) {
                currentMonth++;
            } else {
                currentMonth = 1;
                currentYear++;
            }
            currentWeek = 1;
            currentNum = getWeeksOfMonth(currentYear, currentMonth);
        } else if (currentWeek == currentNum) {
            if (getLastDayOfWeek(currentYear, currentMonth) == 6) {
            } else {
                if (currentMonth + 1 <= 12) {
                    currentMonth++;
                } else {
                    currentMonth = 1;
                    currentYear++;
                }
                currentWeek = 1;
                currentNum = getWeeksOfMonth(currentYear, currentMonth);
            }

        } else if (currentWeek < 1) {
            if (currentMonth - 1 >= 1) {
                currentMonth--;
            } else {
                currentMonth = 12;
                currentYear--;
            }
            currentNum = getWeeksOfMonth(currentYear, currentMonth);
            currentWeek = currentNum - 1;
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        int gvFlag = 0;
        if (e1.getX() - e2.getX() > 80) {
            // 向左滑
            addGridView();
            currentWeek++;
            getCurrent();
            dateAdapter = new DateAdapter(this, getResources(), currentYear,
                    currentMonth, currentWeek, currentNum, selectPostion,
                    currentWeek == 1 ? true : false);
            dateAdapter.setClickDay(currentClickDay);
            dayNumbers = dateAdapter.getDayNumbers();
            gridView.setAdapter(dateAdapter);
            gvFlag++;
            flipper1.addView(gridView, gvFlag);
            dateAdapter.setSeclection(selectPostion);
            this.flipper1.setInAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.push_left_in));
            this.flipper1.setOutAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.push_left_out));
            this.flipper1.showNext();
            flipper1.removeViewAt(0);
            return true;

        } else if (e1.getX() - e2.getX() < -80) {
            addGridView();
            currentWeek--;
            getCurrent();
            dateAdapter = new DateAdapter(this, getResources(), currentYear,
                    currentMonth, currentWeek, currentNum, selectPostion,
                    currentWeek == 1 ? true : false);
            dateAdapter.setClickDay(currentClickDay);
            dayNumbers = dateAdapter.getDayNumbers();
            gridView.setAdapter(dateAdapter);
            gvFlag++;
            flipper1.addView(gridView, gvFlag);
            dateAdapter.setSeclection(selectPostion);
            this.flipper1.setInAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.push_right_in));
            this.flipper1.setOutAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.push_right_out));
            this.flipper1.showPrevious();
            flipper1.removeViewAt(0);
            return true;
            // }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }

}
