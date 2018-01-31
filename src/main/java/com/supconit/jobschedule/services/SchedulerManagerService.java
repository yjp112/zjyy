package com.supconit.jobschedule.services;

import java.util.Date;

import com.supconit.jobschedule.entities.ScheduleJob;

public interface SchedulerManagerService {
	public static final int STATUS_READY = 0;// 运行
	public static final int STATUS_PAUSING = 1;// 暂停
	public static final int STATUS_STOP = 2;// 停止
	public static final int STATUS_DELETE = -1;// 删除

	/**停止job
	 * @param scheduleJob
	 */
	public void stopJob(ScheduleJob scheduleJob);

	/**删除job
	 * @param scheduleJob
	 */
	public void deleteJob(ScheduleJob scheduleJob);

	/**暂停job
	 * @param scheduleJob
	 */
	public void pauseJob(ScheduleJob scheduleJob);

	/**继续job
	 * @param scheduleJob
	 */
	public void resumeJob(ScheduleJob scheduleJob);

	/**开始job
	 * @param scheduleJob
	 * @return
	 */
	public Date scheduleJob(ScheduleJob scheduleJob);

	/**重新开始调度job
	 * @param scheduleJob
	 * @return
	 */
	public Date rescheduleJob(ScheduleJob scheduleJob);
	
	/**立即执行job
	 * @param scheduleJob
	 * @return
	 */
	public boolean runImmediately(ScheduleJob scheduleJob);
}
