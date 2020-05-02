package com.me.pipelinedetectionapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author HaiRun
 * @time 2020/5/2.14:33
 */
@Entity
public class PipePSCheckDb {
    @Id(autoincrement = true)
    Long id;
    String projectName;
    String imageName;
    String roadName;
    String lineDot;
    String connectionPoint;
    String checkLength;
    String flow;
    String fullness;
    String pipeMaterials;
    String pipeSize;
    String defectLength;
    String defectCode;
    String defectGrade;
    String hybrid;
    String wellQuestion;
    String waterQuestion;
    String aboutQuestion;
    String picture;
    String local;
    String remark;
    Date inspectDate;
    String pipeType;
    @Generated(hash = 1272351678)
    public PipePSCheckDb(Long id, String projectName, String imageName,
            String roadName, String lineDot, String connectionPoint,
            String checkLength, String flow, String fullness, String pipeMaterials,
            String pipeSize, String defectLength, String defectCode,
            String defectGrade, String hybrid, String wellQuestion,
            String waterQuestion, String aboutQuestion, String picture,
            String local, String remark, Date inspectDate, String pipeType) {
        this.id = id;
        this.projectName = projectName;
        this.imageName = imageName;
        this.roadName = roadName;
        this.lineDot = lineDot;
        this.connectionPoint = connectionPoint;
        this.checkLength = checkLength;
        this.flow = flow;
        this.fullness = fullness;
        this.pipeMaterials = pipeMaterials;
        this.pipeSize = pipeSize;
        this.defectLength = defectLength;
        this.defectCode = defectCode;
        this.defectGrade = defectGrade;
        this.hybrid = hybrid;
        this.wellQuestion = wellQuestion;
        this.waterQuestion = waterQuestion;
        this.aboutQuestion = aboutQuestion;
        this.picture = picture;
        this.local = local;
        this.remark = remark;
        this.inspectDate = inspectDate;
        this.pipeType = pipeType;
    }
    @Generated(hash = 1574632657)
    public PipePSCheckDb() {
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
    public String getCheckLength() {
        return this.checkLength;
    }
    public void setCheckLength(String checkLength) {
        this.checkLength = checkLength;
    }
    public String getFlow() {
        return this.flow;
    }
    public void setFlow(String flow) {
        this.flow = flow;
    }
    public String getFullness() {
        return this.fullness;
    }
    public void setFullness(String fullness) {
        this.fullness = fullness;
    }
    public String getPipeMaterials() {
        return this.pipeMaterials;
    }
    public void setPipeMaterials(String pipeMaterials) {
        this.pipeMaterials = pipeMaterials;
    }
    public String getPipeSize() {
        return this.pipeSize;
    }
    public void setPipeSize(String pipeSize) {
        this.pipeSize = pipeSize;
    }
    public String getDefectLength() {
        return this.defectLength;
    }
    public void setDefectLength(String defectLength) {
        this.defectLength = defectLength;
    }
    public String getDefectCode() {
        return this.defectCode;
    }
    public void setDefectCode(String defectCode) {
        this.defectCode = defectCode;
    }
    public String getDefectGrade() {
        return this.defectGrade;
    }
    public void setDefectGrade(String defectGrade) {
        this.defectGrade = defectGrade;
    }
    public String getHybrid() {
        return this.hybrid;
    }
    public void setHybrid(String hybrid) {
        this.hybrid = hybrid;
    }
    public String getWellQuestion() {
        return this.wellQuestion;
    }
    public void setWellQuestion(String wellQuestion) {
        this.wellQuestion = wellQuestion;
    }
    public String getWaterQuestion() {
        return this.waterQuestion;
    }
    public void setWaterQuestion(String waterQuestion) {
        this.waterQuestion = waterQuestion;
    }
    public String getAboutQuestion() {
        return this.aboutQuestion;
    }
    public void setAboutQuestion(String aboutQuestion) {
        this.aboutQuestion = aboutQuestion;
    }
    public String getPicture() {
        return this.picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getLocal() {
        return this.local;
    }
    public void setLocal(String local) {
        this.local = local;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Date getInspectDate() {
        return this.inspectDate;
    }
    public void setInspectDate(Date inspectDate) {
        this.inspectDate = inspectDate;
    }
    public String getPipeType() {
        return this.pipeType;
    }
    public void setPipeType(String pipeType) {
        this.pipeType = pipeType;
    }
}
