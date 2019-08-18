package abd.com.pojo;

public class TouchStatus {

	private String deviceId;
	private int status;
	private String recordTime;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	@Override
	public String toString() {
		return "TouchStatus [deviceId=" + deviceId + ", status=" + status + ", recordTime=" + recordTime + "]";
	}

}
