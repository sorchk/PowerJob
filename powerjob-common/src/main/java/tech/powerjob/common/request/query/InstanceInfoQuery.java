package tech.powerjob.common.request.query;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import tech.powerjob.common.PowerQuery;

/**
 * Query InstanceInfo
 *
 * @author sorc
 * @since 2024/10/21
 */
@Getter
@Setter
@Accessors(chain = true)
public class InstanceInfoQuery  {

    /**
     * 任务所属应用ID
     */
    private Long appId;
    /**
     * 当前页码
     */
    private Integer index;
    /**
     * 页大小
     */
    private Integer pageSize;
    /**
     * 查询条件（NORMAL/WORKFLOW）
     */
    private String type;
    private Long instanceId;
    private Long jobId;
    private Long wfInstanceId;

    private String status;
}
