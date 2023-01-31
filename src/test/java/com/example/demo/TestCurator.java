package com.example.demo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Create on 2022/8/20
 *  测试zookeeper客户端API Curator
 * @author 周志文
 */
public class TestCurator {

    private CuratorFramework client;

    @Before
    public void testCurator() throws Exception {
        // 创建一个客户端
        /**
         * @param connectString zookeeper服务器地址
         *                      如果是多台服务器，可以使用逗号分隔
         *                      如果是单台服务器，可以使用host:port的形式
         * @param sessionTimeout 会话超时时间，单位毫秒
         * @param connectionTimeout 连接超时时间，单位毫秒
         * @param maxRetries 最大重试次数
         * @param retryPolicy 重试策略
         */
        RetryPolicy retryPolicy =new ExponentialBackoffRetry(1000, 3);
        //第一种方式：
        client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",60*1000,15*1000,retryPolicy);
        //第二种方式：
        CuratorFramework client2 = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(60*1000)
                .connectionTimeoutMs(15*1000)
                .retryPolicy(retryPolicy)
                .namespace("名称空间")
                .build();
        //开始连接
        client.start();
    }

    @After
    public void colseCurator(){
        if(client != null){
            client.close();
        }
    }

    /**
     * 创建节点：
     *     创建一个节点，如果节点不存在，则创建；如果节点存在，则更新节点内容
     *     创建节点的方式有两种：
     *     1.使用create方法
     *     2.使用createOrSet方法
     * 节点类型：临时节点、持久节点、临时顺序节点、持久顺序节点
     */
    @Test
    public void testCreate() throws Exception {
        //基本创建，默认是有数据，当前客户端的IP作为数据存储
        String path  = client.create().forPath("/test/test1");
        System.out.println(path);

        //带数据的创建
        client.create().forPath("/test/test2", "test2".getBytes());

        //设置节点类型：临时节点、持久节点、临时顺序节点、持久顺序节点
        /**
         *     PERSISTENT(0, false, false, false, false),
         *     PERSISTENT_SEQUENTIAL(2, false, true, false, false),
         *     EPHEMERAL(1, true, false, false, false),
         *     EPHEMERAL_SEQUENTIAL(3, true, true, false, false),
         *     CONTAINER(4, false, false, true, false),
         *     PERSISTENT_WITH_TTL(5, false, false, false, true),
         *     PERSISTENT_SEQUENTIAL_WITH_TTL(6, false, true, false, true);
         */
        client.create().withMode(CreateMode.PERSISTENT).forPath("/test/test3", "test3".getBytes());

        //创建多级节点,creatingParentsIfNeeded()如果父节点不存在，则创建父节点
        client.create().creatingParentsIfNeeded().forPath("/test/test4/test4_1", "test4_1".getBytes());
    }

    /**
     * 查询节点：
     * 1、查询数据：get
     * 2、查询子节点：ls
     * 3、查询节点状态信息：ls -s
     */
    @Test
    public void testGet() throws Exception {
        //查询数据
       byte[] data =  client.getData().forPath("/name");
        System.out.println(new String(data));

        //查询子节点
        List<String> children = client.getChildren().forPath("/name");
        System.out.println(children);

        //查询节点状态信息
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath("/name");
        System.out.println(stat.toString());
    }

    /**
     * 修改节点：
     * 1、修改数据：setData
     * 2、修改子节点：setData
     * 3、根据版本号修改数据
     */
    @Test
    public void testUpdate() throws Exception {
        //修改数据
        client.setData().forPath("/name", "test".getBytes());
        //修改子节点
        client.setData().forPath("/name/zhou", "test1".getBytes());

        //根据版本号修改数据
        int version = 0;
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath("/name");
        version = stat.getVersion();
        client.setData().withVersion(version).forPath("/name","zhouzhiwen".getBytes());
    }

    /**
     * 删除节点：
     * 1、删除单个：delete   delete()
     * 2、删除带有子节点：deleteall  delete().deletingChildrenIfNeeded()
     * 3、必须成功删除   delete().guaranteed()
     * 4、回调  delete().inBackground()
     */
    @Test
    public void testDelete() throws Exception {
        //删除数据
        client.delete().forPath("/name/ouyang");
        //删除子节点
        client.delete().deletingChildrenIfNeeded().forPath("/name");
        //必须成功删除
        client.delete().guaranteed().forPath("/name");

        //回调
        client.delete().inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println("删除成功");
            }
        }).forPath("/name");
    }

    /**
     * 给指定节点注册监听器
     */
    @Test
    public void testNodeCache() throws Exception {
        //创建节点监听器
        final NodeCache nodeCache = new NodeCache(client, "/name");
        //注册监听
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("节点变化了");

                //获取修改节点
                byte[] data = nodeCache.getCurrentData().getData();
                System.out.println(new String(data));
            }
        });

        //启动监听,true表示缓存数据，false表示不缓存数据
        nodeCache.start(true);
    }

    /**
     * 监听某个节点下的子节点
     */
    @Test
    public void testPathChildrenCache() throws Exception {
        //创建节点监听器
        final PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/name",true);
        //注册监听
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println("子节点变化了");
                System.out.println(pathChildrenCacheEvent);
                //监听子数据的变更，并且拿到更新后的数据
                PathChildrenCacheEvent.Type type = pathChildrenCacheEvent.getType();
                if(type.equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)){
                    System.out.println("数据变化了");
                    byte[] data = pathChildrenCacheEvent.getData().getData();
                    System.out.println(data);
                }
            }
        });

        //启动监听,true表示缓存数据，false表示不缓存数据
        pathChildrenCache.start(true);
    }

    /**
     * 监听某个节点和子节点
     */
    @Test
    public void testTreeCache() throws Exception {
        //创建节点监听器
        final TreeCache treeCache = new TreeCache(client, "/name");
        //注册监听
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                System.out.println("节点变化了");
                System.out.println(treeCacheEvent);
            }
        });

        //启动监听,true表示缓存数据，false表示不缓存数据
        treeCache.start();
    }


}
