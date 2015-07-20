package com.wolai.platform.bean;

import java.util.List;

import com.wolai.platform.constant.Constant;


/**
 * 
 * 
 * 分页使用的组件
 * @author xuxiang
 * @version $Id$
 * @since
 * @see
 * @param <T>
 */
public class Page<T> {

    /**
     * 下一页步长
     */
    private static final int NEXT_PAGE_INDEX = 2;
    
    public Page() {

    };
    
    public Page(int start, int limit) {
        this.start = start;
        this.limit = limit;
    }
    
    public Page(int start, int limit, int total, List<T> items) {
        this.start = start;
        this.total = total;
        this.items = items;
        this.limit = limit;
        this.pageNo=getCurrentPage();
    }

    /**
     * 分页大小
     */
    private int limit=Constant.PAGE_SIZE;
    
    /**
     * 分页开始索引
     */
    private int start;
    
    /**
     * 总记录数
     */
    private int total;
    
    /**
     * 分页记录
     */
    private List<T> items;
    
    private int pageNo;
    
    /**
     * 计算总页数.
     * @return 返回int
     */
    public int getTotalPages() {
        if (total == -1) {
            return -1;
        }
        
        int count = total / limit;
        if (total % limit > 0) {
            count++;
        }
        return count;
    }

    /**
     * 是否还有下一页.
     * @return boolea
     */
    public boolean hasNextPage() {
    	if(total<1){
    		return false;
    	}
        return getTotalPages() > start / limit + 1;
    }

    /**
     * 获取页数,从1开始计数
     * @return int
     */
    public int getNextPage() {
        if (hasNextPage()) {
            return start / limit + NEXT_PAGE_INDEX;
        } else {
            return start / limit + 1;
        }
    }

    /**
     * 是否还有上一页. 
     * @return boolean
     */
    public boolean hasPrePage() {
        return start > 0;
    }

    /**
     * 返回上页的页号,序号从1开始.
     * @return 返回int
     */
    public int getPrePage() {
        if (hasPrePage()) {
            return start / limit;
        } else {
            return 1;
        }
    }

    public int getCurrentPage() {
        if (hasPrePage()) {
            return start / limit +1;
        } else {
            return 1;
        }
    }

    public int getFirstPage() {
        return 1;
    }
    
    public int getLastPage() {
        return getTotalPages();
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    
    public String toString(){
    	StringBuffer pageHtml = new StringBuffer();
    	// 最多显示几个页码
    	int length = 8;
    	// 前后显示页面长度
    	int slider =1;
    	
    	int first = 1;
    	
    	
    	// 处理函数名
    	String funcName="page";
    	if (!this.hasPrePage()) {// 如果是首页
    		pageHtml.append("<li class=\"disabled\"><a href=\"javascript:\">&#171; 上一页</a></li>\n");
		} else {
			pageHtml.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+getPrePage()+","+limit+");\">&#171; 上一页</a></li>\n");
		}
    	
    	int pageNo = getCurrentPage();
    	
    	int begin = pageNo - (length / 2);
    	
    	int last = getLastPage();
    	
    	
		if (begin < first) {
			begin = first;
		}

		int end = begin + length - 1;

		if (end >= last) {
			end = last;
			begin = end - length + 1;
			if (begin < first) {
				begin = first;
			}
		}
    	
		if (begin > first) {
			int i = 0;
			for (i = first; i < first + slider && i < begin; i++) {
				pageHtml.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+limit+");\">"
						+ (i + 1 - first) + "</a></li>\n");
			}
			if (i < begin) {
				pageHtml.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
			}
		}

		for (int i = begin; i <= end; i++) {
			if (i == pageNo) {
				pageHtml.append("<li class=\"active\"><a href=\"javascript:\">" + (i + 1 - first)
						+ "</a></li>\n");
			} else {
				pageHtml.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+limit+");\">"
						+ (i + 1 - first) + "</a></li>\n");
			}
		}

		if (last - end > slider) {
			pageHtml.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
			end = last - slider;
		}

		for (int i = end + 1; i <= last; i++) {
			pageHtml.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+limit+");\">"
					+ (i + 1 - first) + "</a></li>\n");
		}

		if (pageNo == last) {
			pageHtml.append("<li class=\"disabled\"><a href=\"javascript:\">下一页 &#187;</a></li>\n");
		} else {
			pageHtml.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+getNextPage()+","+limit+");\">"
					+ "下一页 &#187;</a></li>\n");
		}

		pageHtml.append("<li class=\"disabled controls\"><a href=\"javascript:\">当前 ");
		pageHtml.append("<input type=\"text\" value=\""+pageNo+"\" onkeypress=\"var e=window.event||this;var c=e.keyCode||e.which;if(c==13)");
		pageHtml.append(funcName+"(this.value,"+limit+");\" onclick=\"this.select();\"/> / ");
		pageHtml.append("<input type=\"text\" value=\""+limit+"\" onkeypress=\"var e=window.event||this;var c=e.keyCode||e.which;if(c==13)");
		pageHtml.append(funcName+"("+pageNo+",this.value);\" onclick=\"this.select();\"/> 条，");
		pageHtml.append("共 " + getTotal() + " 条</a><li>\n");

		pageHtml.insert(0,"<ul>\n").append("</ul>\n");
		
		pageHtml.append("<div style=\"clear:both;\"></div>");
    	
    	return pageHtml.toString();
    }

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
}
