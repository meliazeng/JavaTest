package abd.com.pojo;

public class Param {

	private String deviceId;
	private int stopSeconds;
	private int filterSeconds;
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public int getStopSeconds() {
		return stopSeconds;
	}
	public void setStopSeconds(int stopSeconds) {
		this.stopSeconds = stopSeconds;
	}
	public int getFilterSeconds() {
		return filterSeconds;
	}
	public void setFilterSeconds(int filterSeconds) {
		this.filterSeconds = filterSeconds;
	}
	@Override
	public String toString() {
		return "Param [deviceId=" + deviceId + ", stopSeconds=" + stopSeconds + ", filterSeconds=" + filterSeconds
				+ "]";
	}
	
	
}
