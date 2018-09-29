package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name = "events")
public class EventModel {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = WorkProfile.class)
	@JoinColumn( name = "workProfile_id")
	private WorkProfile workProfile;
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
	@JoinColumn ( name = "user_id")
	private User user;
	@Column ( name = "beginTime")
	private Date beginTime;
	@Column ( name = "endTime")
	private Date endTime;
	@Transient
	private String beginDay;
	@Transient
	private String beginHour;
	@Transient
	private String beginMinute;
	@Transient
	private String endDay;
	@Transient
	private String endHour;
	@Transient
	private String endMinute;

	public EventModel() {
		user = CurrentUser.getUser();
	}
	public WorkProfile getWorkProfile() {
		return workProfile;
	}
	public void setWorkProfile(WorkProfile workProfile) {
		this.workProfile = workProfile;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getBeginDay() {
		return beginDay;
	}
	public void setBeginDay(String beginDay) {
		this.beginDay = beginDay;
	}
	public String getBeginHour() {
		return beginHour;
	}
	public void setBeginHour(String beginHour) {
		this.beginHour = beginHour;
	}
	public String getBeginMinute() {
		return beginMinute;
	}
	public void setBeginMinute(String beginMinute) {
		this.beginMinute = beginMinute;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	public String getEndMinute() {
		return endMinute;
	}
	public void setEndMinute(String endMinute) {
		this.endMinute = endMinute;
	}

}
