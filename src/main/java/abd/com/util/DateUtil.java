package abd.com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期类 封装日期方面的一些函数
 * 
 * @author Administrator
 * 
 */
public class DateUtil {
	/**
	 * 将日期格式化成指定格式的字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return
	 */
	public static String formatDateByFormet(Date date, String format) {
		SimpleDateFormat formatTmp = new SimpleDateFormat(format);
		return formatTmp.format(date);
	}

	/**
	 * 将日期格式字符串转化成指定格式的字符串
	 * 
	 * @param dateStr
	 *            日期格式字符串 格式为yyyy-MM-dd HH:mm:ss
	 * @param format
	 *            格式
	 * @return
	 */
	public static String getDateStrByFormet(String dateStr, String format) {
		return formatDateByFormet(parseStringToDate(dateStr, "yyyy-MM-dd HH:mm:ss"), format);
	}

	/**
	 * 将字符串转成指定格式的日期
	 * 
	 * @param dateStr
	 *            日期形式的字符创 格式为yyyy-MM-dd HH:mm:ss
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static Date parseStringToDate(String dateStr, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取指定日期范围内的日期集合
	 * 
	 * @param startDate
	 *            开始日期 格式为 yyyy-MM-dd
	 * @param endDate
	 *            结束日期 格式为 yyyy-MM-dd
	 * @return
	 */
	public static List<String> getDateList(String startDate, String endDate) {
		List<String> dates = new ArrayList<String>();
		Date sDate = parseStringToDate(startDate, "yyyy-MM-dd");
		Date eDate = parseStringToDate(endDate, "yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sDate);
		dates.add(formatDateByFormet(sDate, "yyyy-MM-dd"));
		while (sDate.getTime() < eDate.getTime()) {
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			sDate = calendar.getTime();
			dates.add(formatDateByFormet(sDate, "yyyy-MM-dd"));
		}
		return dates;
	}

	/**
	 * 获取指定时间加一定分钟数之后的时间
	 * 
	 * @param timeStr
	 *            时间 格式yyyy-MM-dd HH:mm:ss
	 * @param times
	 *            时间间隔
	 * @param format
	 *            返回的时间字符串格式
	 * @return
	 */
	public static String getNextTime(String timeStr, int times) {
		Date date = parseStringToDate(timeStr, "yyyy-MM-dd HH:mm:ss");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, times);
		return formatDateByFormet(calendar.getTime(), "yyyy-MM-dd HH:mm:00");
	}

	/**
	 * 获取指定时间加一定分钟数之后的时间
	 * 
	 * @param timeStr
	 *            时间 格式yyyy-MM-dd HH:mm:ss
	 * @param times
	 *            时间间隔
	 * @param format
	 *            返回的时间字符串格式
	 * @return
	 */
	public static Date getNextTime_Date(String timeStr, int times) {
		Date date = parseStringToDate(timeStr, "yyyy-MM-dd HH:mm:ss");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, times);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期的前nums天/月/年或者后nums天/月/年
	 * 
	 * @param date
	 *            日期
	 * @param nums
	 *            天数/月数 负数：前nums天/月 /年 正数：后nums天/月/年
	 * @param dayOrMonthOrYear
	 *            1:天数相加 2：月数相加 3：年数相加
	 * @param format
	 *            返回的字符串格式
	 * @return
	 */
	public static String getSpecifiedDate(Date date, int days, int dayOrMonthOrYear, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (dayOrMonthOrYear == 1)
			calendar.add(Calendar.DAY_OF_YEAR, days);
		else if (dayOrMonthOrYear == 2)
			calendar.add(Calendar.MONTH, days);
		else
			calendar.add(Calendar.YEAR, days);
		return formatDateByFormet(calendar.getTime(), format);
	}

	public static String getTimestamp() {
		String unixstr = "";
		Date date = new Date();
		long l = date.getTime();
		String str = String.valueOf(l);
		unixstr = str.substring(0, 10);

		return unixstr;
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.formatDateByFormet(new Date(), "yyyy"));
	}

	public static int getdiff(String start, String end) {
		Date startD = parseStringToDate(start, "yyyy-MM-dd HH:mm:ss");
		Date endD = parseStringToDate(end, "yyyy-MM-dd HH:mm:ss");
		return (int) ((endD.getTime() - startD.getTime()) / 1000);
	}

	public static String getNextSeconds(String recordTime, int stopSeconds) {
		Date date = parseStringToDate(recordTime, "yyyy-MM-dd HH:mm:ss");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, stopSeconds);
		return formatDateByFormet(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
	}
}
