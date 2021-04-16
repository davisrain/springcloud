package com.dzy.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("news_baidu_route1", r -> r.path("/guonei").uri("https://news.baidu.com/guonei"));
        routes.route("news_baidu_route2", r -> r.path("/mil").uri("https://news.baidu.com/mil"));
        return routes.build();
    }
}
