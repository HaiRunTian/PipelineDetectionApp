package com.me.pipelinedetectionapp.view;

/**
 * @author HaiRun
 * @time 2020/5/2.12:04
 */
public interface DataInterface {

    /**
     * 获取影像编号
     *
     * @return
     */
    String getImaNum();

    /**
     * 设置影像编号
     */
    void setImaNum(String imaNum);

    /**
     * 获取区域
     *
     * @return
     */
    String getArea();

    /**
     * 设置区域
     */
    void setArea(String area);

    /**
     * 获取道路
     *
     * @return
     */
    String getRoad();

    /**
     * 设置道路
     */
    void setRoad(String road);

    /**
     * 获取起点编号
     *
     * @return
     */
    String getStartPoint();

    /**
     * 设置起点编号
     */
    void setStartPoint(String point);

    /**
     * 获取终点编号
     *
     * @return
     */
    String getEndtPoint();

    /**
     * 设置终点编号
     */
    void setEndPoint(String point);

    /**
     * 获取检测长度
     *
     * @return
     */
    String getCheckLength();

    /**
     * 设置终点编号
     */
    void setChenkLength(String length);

    /**
     * 获取流向
     *
     * @return
     */
    String getFlow();

    /**
     * 设置流向
     */
    void setFlow(String flow);

    /**
     * 获取充满度
     *
     * @return
     */
    String getFullness();

    /**
     * 设置充满度
     */
    void setFullness(String fullness);
    /**
     * 获取管类
     *
     * @return
     */
    String getPipeType();

    /**
     * 设置管类
     */
    void setPipeType(String type);

    /**
     * 获取管材
     *
     * @return
     */
    String getPipeMaterials();

    /**
     * 设置管材
     */
    void setPipeMaterials(String pipeMaterials);

    /**
     * 获取管渠规格
     *
     * @return
     */
    String getPipeSize();

    /**
     * 设置管渠规格
     */
    void setPipeSize(String pipeSize);

    /**
     * 获取缺陷距离
     *
     * @return
     */
    String getDefectLength();

    /**
     * 设置缺陷距离
     */
    void setDefectLength(String length);

    /**
     * 获取缺陷代码
     *
     * @return
     */
    String getDefectCode();

    /**
     * 设置缺陷代码
     */
    void setDefectCode(String code);

    /**
     * 获取缺陷等级
     *
     * @return
     */
    String getDefectGrade();

    /**
     * 设置缺陷等级
     */
    void setDefectGrade(String grade);

    /**
     * 获取雨污混接情况
     *
     * @return
     */
    String getHybrid();

    /**
     * 设置污混接情况
     */
    void setHybrid(String hybrid);

    /**
     * 获取检查井问题
     *
     * @return
     */
    String getWell();

    /**
     * 设置检查井问题
     */
    void setWell(String well);

    /**
     * 获取雨水口问题
     *
     * @return
     */
    String getWater();

    /**
     * 设置雨水口问题
     */
    void setWater(String water);

    /**
     * 获取其他问题
     *
     * @return
     */
    String getAbout();

    /**
     * 设置雨水口问题
     */
    void setAbout(String about);

    /**
     * 获取照片
     *
     * @return
     */
    String getImg();

    /**
     * 设置照片
     */
    void setImg(String img);

    /**
     * 获取位置
     *
     * @return
     */
    String getLocal();

    /**
     * 设置位置
     */
    void setLocal(String local);

    /**
     * 获取备注
     *
     * @return
     */
    String getRemark();

    /**
     * 设置备注
     */
    void setRemark(String remark);


}
