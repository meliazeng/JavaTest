package abd.com.pojo;

public class Result {

	private int status = -1;
	private String deviceId;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "Result [status=" + status + ", deviceId=" + deviceId + "]";
	}

}
