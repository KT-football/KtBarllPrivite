package com.ktfootball.app.calendar.doim;

import android.content.Context;

import com.ktfootball.app.calendar.widget.CalendarView;

public class CalendarViewBuilder {
		private CalendarView[] calendarViews;

		public  CalendarView[] createMassCalendarViews(Context context, int count, int style, CalendarView.CallBack callBack){
			calendarViews = new CalendarView[count];
			for(int i = 0; i < count;i++){
				calendarViews[i] = new CalendarView(context, style,callBack);
			}
			return calendarViews;
		}
		
		public  CalendarView[] createMassCalendarViews(Context context, int count, CalendarView.CallBack callBack){
			
			return createMassCalendarViews(context, count, CalendarView.MONTH_STYLE,callBack);
		}
		/**
		 * �л�CandlendarView����ʽ
		 * @param style
		 */
		public void swtichCalendarViewsStyle(int style){
			if(calendarViews != null)
			for(int i = 0 ;i < calendarViews.length;i++){
				calendarViews[i].switchStyle(style);
			}
		}
		/**
		 * CandlendarView�ص���ǰ����
		 */
		
		public void backTodayCalendarViews(){
			if(calendarViews != null)
			for(int i = 0 ;i < calendarViews.length;i++){
				calendarViews[i].backToday();
			}
		}
}
