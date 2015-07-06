package wu.a.wuliu.model;

/**
 * <pre>
 * 司机
 * @author junxu.wang
 * @d2015年7月6日
 * </pre>
 *
 */
public class Driver {
	private String name;
	private String comment;
	private String carNo;
	private String city;
	private int times;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	

}
