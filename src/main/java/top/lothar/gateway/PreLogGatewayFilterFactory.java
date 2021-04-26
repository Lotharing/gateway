package top.lothar.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 官方有一种是继承 AbstractGatewayFilterFactory 配置文件对应的是args那种
 * 此处使用AbstractNameValueGatewayFilterFactory 也就是当前的配置文件filter格式, 是第一种的简化
 * 名称一定以GatewayFilterFactory结尾
 * @author zhaolutong
 */
@Slf4j
@Component
public class PreLogGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                // 获取配置文件PreLog配置的name value
                log.info("请求进来参数信息{},{}", config.getName(), config.getValue());
                // 修改request
                ServerHttpRequest modifiedRequest = exchange.getRequest()
                        .mutate()
                        .build();
                // 修改exchange
                ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(modifiedRequest)
                        .build();
                // 传递给下一个过滤器处理
                return chain.filter(modifiedExchange);
            }
        };

        // GateWayFilter可以指定order顺序，也就是配置文件中的 GatewayFilter(filter,1);
    }
}
