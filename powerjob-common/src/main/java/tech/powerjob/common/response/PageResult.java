package tech.powerjob.common.response;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class PageResult<T> implements Serializable {
    
    /**
     * 当前页数
     */
    private int index;
    /**
     * 页大小
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPages;
    /**
     * 总数据量
     */
    private long totalItems;
    /**
     * 数据
     */
    private List<T> data; 
}
