package tech.powerjob.client;

import tech.powerjob.common.request.http.SaveJobInfoRequest;
import tech.powerjob.common.request.http.SaveWorkflowNodeRequest;
import tech.powerjob.common.request.http.SaveWorkflowRequest;
import tech.powerjob.common.request.query.InstanceInfoQuery;
import tech.powerjob.common.request.query.JobInfoQuery;
import tech.powerjob.common.request.query.WorkflowInstanceInfoQuery;
import tech.powerjob.common.response.*;

import java.util.List;

/**
 * PowerJobClient, the client for OpenAPI.
 *
 * @author tjq
 * @since 2023/3/5
 */
public interface IPowerJobClient {

    /* ************* Job 区 ************* */

    ResultDTO<SaveJobInfoRequest> exportJob(Long jobId);

    ResultDTO<Long> saveJob(SaveJobInfoRequest request);

    ResultDTO<Long> copyJob(Long jobId);

    ResultDTO<JobInfoDTO> fetchJob(Long jobId);

    ResultDTO<List<JobInfoDTO>> fetchAllJob();

    ResultDTO<List<JobInfoDTO>> queryJob(JobInfoQuery powerQuery);

    ResultDTO<Void> disableJob(Long jobId);

    ResultDTO<Void> enableJob(Long jobId);

    ResultDTO<Void> deleteJob(Long jobId);

    ResultDTO<Long> runJob(Long jobId, String instanceParams, long delayMS);

    /* ************* Instance API list ************* */

    ResultDTO<Void> stopInstance(Long instanceId);

    ResultDTO<Void> cancelInstance(Long instanceId);

    ResultDTO<Void> retryInstance(Long instanceId);

    ResultDTO<Integer> fetchInstanceStatus(Long instanceId);

    ResultDTO<InstanceInfoDTO> fetchInstanceInfo(Long instanceId);
    /**
     * 查询实例列表
     * @param query
     * @return
     */
    ResultDTO<PageResult<InstanceInfoDTO>> queryPageInstanceInfo(InstanceInfoQuery query);
    /**
     * 查询实例日志，返回日志内容（字符串分页）
     * @param instanceId
     * @return
     */
    ResultDTO<PageResult<String>> queryPageInstanceLog(Long instanceId,Long index);

    /* ************* Workflow API list ************* */
    ResultDTO<Long> saveWorkflow(SaveWorkflowRequest request);

    ResultDTO<Long> copyWorkflow(Long workflowId);

    ResultDTO<List<WorkflowNodeInfoDTO>> saveWorkflowNode(List<SaveWorkflowNodeRequest> requestList);

    ResultDTO<WorkflowInfoDTO> fetchWorkflow(Long workflowId);

    ResultDTO<Void> disableWorkflow(Long workflowId);

    ResultDTO<Void> enableWorkflow(Long workflowId);

    ResultDTO<Void> deleteWorkflow(Long workflowId);

    ResultDTO<Long> runWorkflow(Long workflowId, String initParams, long delayMS);

    /* ************* Workflow Instance API list ************* */

    ResultDTO<Void> stopWorkflowInstance(Long wfInstanceId);

    ResultDTO<Void> retryWorkflowInstance(Long wfInstanceId);

    ResultDTO<Void> markWorkflowNodeAsSuccess(Long wfInstanceId, Long nodeId);

    ResultDTO<WorkflowInstanceInfoDTO> fetchWorkflowInstanceInfo(Long wfInstanceId);
    /**
     * 查询工作流实例列表
     * @param query
     * @return
     */
    ResultDTO<PageResult<WorkflowInstanceInfoDTO>> queryPageWorkflowInstanceInfo(WorkflowInstanceInfoQuery query);

}
