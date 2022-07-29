package tech.powerjob.server.core.handler.webhook;

import tech.powerjob.common.request.TaskTrackerReportInstanceStatusReq;

/**
 * 任务完成后回调通知接口
 *  @author sorc
 *  @since 2022/7/27
 */
public interface TaskFinishWebHook {
    /**
     * 任务执行完成后回调通知
     * @param req 任务状态
     */
    void notify(TaskTrackerReportInstanceStatusReq req);
}
