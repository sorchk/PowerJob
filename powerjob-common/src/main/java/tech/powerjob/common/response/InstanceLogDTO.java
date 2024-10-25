package tech.powerjob.common.response;

import lombok.Data;
/**
 * 实例日志 对外输出对象
 *
 * @author sorc
 * @since 2024/10/24
 */
@Data
public class InstanceLogDTO {
      /**
     * 当前页数
     */
    private long index;
    /**
     * 总页数
     */
    private long totalPages;
    /**
     * 文本数据
     */
    private String data;
}
