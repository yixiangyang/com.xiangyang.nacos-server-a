# com.xiangyang.nacos-server-a
引入     

```java
<dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <version>2.1.1.RELEASE</version>
        </dependency>
```

 使用@EnableDiscoveryClient spring Cloud原生注解 向nacos发送服务注册

**版本来源 2.1.1RELEASE**

在client这一侧是心跳的发起源，进入NacosNamingService，可以发现，只有注册服务实例的时候才会构造心跳包

```
   @Override
    public void registerInstance(String serviceName, String groupName, Instance instance) throws NacosException {

        if (instance.isEphemeral()) {
            BeatInfo beatInfo = new BeatInfo();
            beatInfo.setServiceName(NamingUtils.getGroupedName(serviceName, groupName));
            beatInfo.setIp(instance.getIp());
            beatInfo.setPort(instance.getPort());
            beatInfo.setCluster(instance.getClusterName());
            beatInfo.setWeight(instance.getWeight());
            beatInfo.setMetadata(instance.getMetadata());
            beatInfo.setScheduled(false);
            long instanceInterval = instance.getInstanceHeartBeatInterval();
            beatInfo.setPeriod(instanceInterval == 0 ? DEFAULT_HEART_BEAT_INTERVAL : instanceInterval);

            beatReactor.addBeatInfo(NamingUtils.getGroupedName(serviceName, groupName), beatInfo);
        }

        serverProxy.registerService(NamingUtils.getGroupedName(serviceName, groupName), groupName, instance);
    }
```

 没有特殊情况，目前ephemeral都是true。BeatReactor维护了一个Map对象，记录了需要发送心跳的BeatInfo，构造了一个心跳包后，BeatReactor.addBeatInfo方法将BeatInfo放入Map中。然后，内部有一个定时器，每隔5秒发送一次心跳。 

```
 private volatile long clientBeatInterval = 5 * 1000;
 
class BeatProcessor implements Runnable {

        @Override
        public void run() {
            try {
                for (Map.Entry<String, BeatInfo> entry : dom2Beat.entrySet()) {
                    BeatInfo beatInfo = entry.getValue();
                    if (beatInfo.isScheduled()) {
                        continue;
                    }
                    beatInfo.setScheduled(true);
                    executorService.schedule(new BeatTask(beatInfo), 0, TimeUnit.MILLISECONDS);
                }
            } catch (Exception e) {
                NAMING_LOGGER.error("[CLIENT-BEAT] Exception while scheduling beat.", e);
            } finally {
                executorService.schedule(this, clientBeatInterval, TimeUnit.MILLISECONDS);
            }
        }
    }
```

 通过设置scheduled的值来控制是否已经下发了心跳任务，具体的心跳任务逻辑放在了BeatTask。 

引入   

```java
<dependency>
	<groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    <version>0.2.0.RELEASE</version>
 </dependency>
```

 进行服务配置，使用@RefreshScope 刷新依赖


