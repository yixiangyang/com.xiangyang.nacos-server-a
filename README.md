# com.xiangyang.nacos-server-a
引入              <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <version>2.1.1.RELEASE</version>
        </dependency>
使用@EnableDiscoveryClient spring Cloud原生注解 向nacos发送服务注册

引入          <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
          <version>0.2.0.RELEASE</version>
        </dependency>
 进行服务配置，使用@RefreshScope 刷新依赖


