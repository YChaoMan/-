package com.entity;

/**
 * ��ҳ�࣬���ڷ�װ��ҳ������
 */
public class Page {

    private int currentPage; // ��ǰҳ��
    private int startRow; // ��ǰ��¼
    private int totalPage; // ��ҳ��
    private int totalRow; // �ܼ�¼
    private int pageSize; // ��ʾ������

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
