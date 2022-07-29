package tech.powerjob.server.core.handler.webhook.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.powerjob.common.request.TaskTrackerReportInstanceStatusReq;
import tech.powerjob.server.core.handler.webhook.TaskFinishWebHook;
import tech.powerjob.server.core.service.JobService;
import tech.powerjob.server.persistence.remote.repository.AppInfoRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务完成后回调通知 简单网页回调钩子实现
 *  @author sorc
 *  @since 2022/7/27
 */
@Slf4j
@Component
public class SimpleTaskFinishWebHook implements TaskFinishWebHook {
    /**
     * webhook地址列表，多个使用逗号分割 在配额制文件中修改配置 process.finish.webhook
     *
     * 在application.properties文件最后添加
     * process.finish.webhook=http://localhost:8080/test/webhook,http://domo.io/test/webhook
     */
    private static List<String> webHooks;
    private static ExecutorService executorService;

    public static List<String> getWebHooks() {
        if (webHooks == null) {
            return new ArrayList<>();
        }
        return webHooks;
    }

    @Value("#{'${process.finish.webhook:}'.split(',')}")
    public void setWebHooks(List<String> webHooks) {
        SimpleTaskFinishWebHook.webHooks = webHooks;
    }
    @Resource
    private AppInfoRepository appInfoRepository;
    @Resource
    private JobService jobService;

    @Override
    public void notify(TaskTrackerReportInstanceStatusReq req){
        log.debug("========SimpleTaskFinishWebHook===notify==={}" , JSONObject.toJSONString(req));
        Long appid = jobService.fetchJob(req.getJobId()).getAppId();
        // 应用名
        String appName =  appInfoRepository.findById(appid).get().getAppName();
        // 独立线程通知 不影响业务继续
        SimpleTaskFinishWebHook.getThreadPool().submit(new SimpleTaskFinishWebHookRunnable(req, appName));
    }
    public static ExecutorService getThreadPool() {
        if (executorService == null) {
            // 最多2线程 防止过多占用系统资源
            executorService = new ThreadPoolExecutor(1, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
        }
        return executorService;
    }
}
