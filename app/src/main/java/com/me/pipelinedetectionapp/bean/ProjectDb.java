package com.me.pipelinedetectionapp.bean;

import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author linshen on 2019-09-23.
 * 邮箱: 18475453284@163.com
 */
@Entity
public class ProjectDb {
    @Id(autoincrement = true)
    Long id;
    @Nullable
    String projectName;
    //项目编号
    String  projectNumber;
    //检测员
    String inspectorName;
    //记录员
    String registrarName;
    //区域名称
    String areaName;
    //检测方法
    String testMethod;
    //检测日期
    String inspectDate;
    //模式
    String projectMode;
    @Generated(hash = 774777906)
    public ProjectDb(Long id, String projectName, String projectNumber,
            String inspectorName, String registrarName, String areaName,
            String testMethod, String inspectDate, String projectMode) {
        this.id = id;
        this.projectName = projectName;
        this.projectNumber = projectNumber;
        this.inspectorName = inspectorName;
        this.registrarName = registrarName;
        this.areaName = areaName;
        this.testMethod = testMethod;
        this.inspectDate = inspectDate;
        this.projectMode = projectMode;
    }
    @Generated(hash = 824296593)
    public ProjectDb() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getProjectName() {
        return this.projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getProjectNumber() {
        return this.projectNumber;
    }
    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }
    public String getInspectorName() {
        return this.inspectorName;
    }
    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }
    public String getRegistrarName() {
        return this.registrarName;
    }
    public void setRegistrarName(String registrarName) {
        this.registrarName = registrarName;
    }
    public String getAreaName() {
        return this.areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    public String getTestMethod() {
        return this.testMethod;
    }
    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }
    public String getInspectDate() {
        return this.inspectDate;
    }
    public void setInspectDate(String inspectDate) {
        this.inspectDate = inspectDate;
    }

    @Override
    public String toString() {
        return "ProjectDb{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", projectNumber='" + projectNumber + '\'' +
                ", inspectorName='" + inspectorName + '\'' +
                ", registrarName='" + registrarName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", testMethod='" + testMethod + '\'' +
                ", inspectDate='" + inspectDate + '\'' +
                '}';
    }
    public String getProjectMode() {
        return this.projectMode;
    }
    public void setProjectMode(String projectMode) {
        this.projectMode = projectMode;
    }
}
