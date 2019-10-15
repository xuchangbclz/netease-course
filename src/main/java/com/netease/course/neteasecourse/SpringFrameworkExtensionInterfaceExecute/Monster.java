package com.netease.course.neteasecourse.SpringFrameworkExtensionInterfaceExecute;

import com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.对接保朝写的Netty服务端.MonsterStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 怪物AI
 * @Author daituo
 * @Date
 **/
@Data
@Accessors(chain = true)
@Component
public class Monster implements Serializable {

    private String name = "哥斯拉";

    private Integer xAxis;

    private Integer yAxis;

    /**
     * 警戒半径，单位cm
     */
    private Integer warningRadius;

    /**
     * 攻击半径，单位cm
     */
    private Integer attackRadius;

    /**
     * 怪物状态
     */
    private Integer status;


    public static Monster init() {
        return new Monster().setName("哥斯拉").setXAxis(500).setYAxis(900).setAttackRadius(300).setWarningRadius(400)
                .setStatus(MonsterStatusEnum.outOfFighting.getCode());
    }

}
