package top.lothar.gateway;

import lombok.Data;

import java.time.LocalTime;

/**
 * 配置路由谓词工厂 TimeBetween 的时间区间
 * @author zhaolutong
 */
@Data
public class TimeBetweenConfig {
    private LocalTime start;
    private LocalTime end;
}
