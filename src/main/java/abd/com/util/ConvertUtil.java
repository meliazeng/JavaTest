package abd.com.util;

import java.util.Date;

public class ConvertUtil {

	public static String toHexString(byte[] byteArray) {
		if (byteArray == null || byteArray.length < 1)
			throw new IllegalArgumentException("this byteArray must not be null or empty");

		final StringBuilder hexString = new StringBuilder();
		if ((byteArray[0] & 0xff) < 0x10)// 0~F前面补零
			hexString.append("0");
		hexString.append(Integer.toHexString(0xFF & byteArray[0]));
		for (int i = 1; i < byteArray.length; i++) {
			hexString.append(",");
			if ((byteArray[i] & 0xff) < 0x10)// 0~F前面补零
				hexString.append("0");
			hexString.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return hexString.toString().toUpperCase();
	}

	public static byte getCheckSum(byte[] bytes, int start, int len) {
		int b = 0;

		for (int i = 0; i < len; i++) {
			b += bytes[i + start];
		}

		return (byte) (b % 256);
	}

	public static String toHexString(byte[] bytes, int start, int len) {
		if (bytes == null || bytes.length < 1)
			throw new IllegalArgumentException("this byteArray must not be null or empty");

		final StringBuilder hexString = new StringBuilder();

		for (int i = 0; i < len; i++) {
			if ((bytes[i + start] & 0xff) < 0x10)// 0~F前面补零
				hexString.append("0");
			hexString.append(Integer.toHexString(0xFF & bytes[i + start]));
		}
		return hexString.toString().toUpperCase();
	}

	public static String getTime(byte[] bytes, int start) {

		Date d = new Date();
		d.setYear(bytes[start]);
		d.setMonth(bytes[start + 1] - 1);
		d.setDate(bytes[start + 2]);
		d.setHours(bytes[start + 3]);
		d.setMinutes(bytes[start + 4]);
		d.setSeconds(bytes[start + 5]);

		return DateUtil.formatDateByFormet(d, "yyyy-MM-dd HH:mm:ss");
	}

	public static float getFloat(byte[] bytes, int start) {
		return Float.intBitsToFloat(getInt(bytes, start));
	}

	private static int getInt(byte[] bytes, int start) {
		return (0xff000000 & (bytes[start + 0] << 24)) | (0x00ff0000 & (bytes[start + 1] << 16))
				| (0x0000ff00 & (bytes[start + 2] << 8)) | (0x000000ff & bytes[start + 3]);
	}

	public static String toMacString(byte[] bytes, int start, int len) {
		if (bytes == null || bytes.length < 1)
			throw new IllegalArgumentException("this byteArray must not be null or empty");

		final StringBuilder hexString = new StringBuilder();
		if ((bytes[start] & 0xff) < 0x10)// 0~F前面补零
			hexString.append("0");
		hexString.append(Integer.toHexString(0xFF & bytes[start]));
		for (int i = 1; i < len; i++) {
			hexString.append(":");
			if ((bytes[i + start] & 0xff) < 0x10)// 0~F前面补零
				hexString.append("0");
			hexString.append(Integer.toHexString(0xFF & bytes[i + start]));
		}
		return hexString.toString().toUpperCase();
	}
}
