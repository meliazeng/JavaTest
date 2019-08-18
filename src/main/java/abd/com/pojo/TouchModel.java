package abd.com.pojo;

public class TouchModel {

	private String deviceId;
	private int fieldStrength;
	private float vol;
	private String recordTime;
	private String mac;
	private String message;
	private int status;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getFieldStrength() {
		return fieldStrength;
	}

	public void setFieldStrength(int fieldStrength) {
		this.fieldStrength = fieldStrength;
	}

	public float getVol() {
		return vol;
	}

	public void setVol(float vol) {
		this.vol = vol;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Override
	public String toString() {
		return "TouchModel [deviceId=" + deviceId + ", fieldStrength=" + fieldStrength + ", vol=" + vol
				+ ", recordTime=" + recordTime + ", mac=" + mac + ", message=" + message + ", status=" + status + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
