package tech.powerjob.server.core.handler.webhook.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import tech.powerjob.common.OmsConstant;
import tech.powerjob.common.request.TaskTrackerReportInstanceStatusReq;
import tech.powerjob.common.utils.HttpUtils;

import java.util.List;

/**
 * http通知
 *
 * @author sorc
 * @since 2022/7/27
 */
@Slf4j
public class SimpleTaskFinishWebHookRunnable implements Runnable {
    /**
     * 任务状态
     */
    final TaskTrackerReportInstanceStatusReq req;
    /**
     * 应用名
     */
    final String appName;

    public SimpleTaskFinishWebHookRunnable(TaskTrackerReportInstanceStatusReq req, String appName) {
        this.req = req;
        this.appName = appName;
    }

    @Override
    public void run() {
        if (req == null) {
            return;
        }
        String json = JSONObject.toJSONString(req);
        MediaType jsonType = MediaType.parse(OmsConstant.JSON_MEDIA_TYPE);
        RequestBody requestBody = RequestBody.create(jsonType, json);
        List<String> webHooks = SimpleTaskFinishWebHook.getWebHooks();
        webHooks.forEach(webHook -> {
            if(webHook.toLowerCase().startsWith("http://")||webHook.toLowerCase().startsWith("https://")) {
                try {
                    log.debug("post {}/{}",webHook,appName);
                    // 通知地址后追加appName 方便独立管理回调通知
                    String response = HttpUtils.post(webHook + "/" + appName, requestBody);
                    log.info("[WebHookService] invoke webhook[url={}] successfully, response is {}", webHook, response);
                } catch (Exception e) {
                    log.warn("[WebHookService] invoke webhook[url={}] failed!", webHook, e);
                }
            }else{
                log.warn("[WebHookService] webhook[url={}] format error!", webHook);
            }
        });
    }
}
