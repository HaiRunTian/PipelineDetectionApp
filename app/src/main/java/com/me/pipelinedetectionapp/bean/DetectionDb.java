package com.me.pipelinedetectionapp.bean;


import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 *
 *
 */
@Entity
public class DetectionDb {
    @Id(autoincrement = true)
    Long id;
    @Nullable
    String projectName;
    String imageName;
    String roadName;
    String lineDot;
    String connectionPoint;
    String startingPointOrigin;
    String startingPointEnd;
    String folwDirection;
    String pipe;
    String pipeDiameter;
    String type;
    Date inspectDate;
    String remark;
    @Generated(hash = 1618105611)
    public DetectionDb(Long id, String projectName, String imageName,
            String roadName, String lineDot, String connectionPoint,
            String startingPointOrigin, String startingPointEnd,
            String folwDirection, String pipe, String pipeDiameter, String type,
            Date inspectDate, String remark) {
        this.id = id;
        this.projectName = projectName;
        this.imageName = imageName;
        this.roadName = roadName;
        this.lineDot = lineDot;
        this.connectionPoint = connectionPoint;
        this.startingPointOrigin = startingPointOrigin;
        this.startingPointEnd = startingPointEnd;
        this.folwDirection = folwDirection;
        this.pipe = pipe;
        this.pipeDiameter = pipeDiameter;
        this.type = type;
        this.inspectDate = inspectDate;
        this.remark = remark;
    }
    @Generated(hash = 1486199693)
    public DetectionDb() {
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
    public String getImageName() {
        return this.imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    public String getRoadName() {
        return this.roadName;
    }
    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }
    public String getLineDot() {
        return this.lineDot;
    }
    public void setLineDot(String lineDot) {
        this.lineDot = lineDot;
    }
    public String getConnectionPoint() {
        return this.connectionPoint;
    }
    public void setConnectionPoint(String connectionPoint) {
        this.connectionPoint = connectionPoint;
    }
    public String getStartingPointOrigin() {
        return this.startingPointOrigin;
    }
    public void setStartingPointOrigin(String startingPointOrigin) {
        this.startingPointOrigin = startingPointOrigin;
    }
    public String getStartingPointEnd() {
        return this.startingPointEnd;
    }
    public void setStartingPointEnd(String startingPointEnd) {
        this.startingPointEnd = startingPointEnd;
    }
    public String getFolwDirection() {
        return this.folwDirection;
    }
    public void setFolwDirection(String folwDirection) {
        this.folwDirection = folwDirection;
    }
    public String getPipe() {
        return this.pipe;
    }
    public void setPipe(String pipe) {
        this.pipe = pipe;
    }
    public String getPipeDiameter() {
        return this.pipeDiameter;
    }
    public void setPipeDiameter(String pipeDiameter) {
        this.pipeDiameter = pipeDiameter;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Date getInspectDate() {
        return this.inspectDate;
    }
    public void setInspectDate(Date inspectDate) {
        this.inspectDate = inspectDate;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
 
   
}

