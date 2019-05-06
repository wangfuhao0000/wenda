package cug.wfh.wenda.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

@Service
public class JedisAdapter implements InitializingBean {
    private JedisPool pool;

    private static Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    public static void print(int index, Object obj) {
        System.out.println(String.format("%d, %s", index, obj));
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://localhost:6379/9");
        jedis.flushDB();

        //get set
        jedis.set("hello", "world");
        jedis.get("hello");
        jedis.rename("hello", "newworld");  //给key重命名
        jedis.setex("hello2", 15, "world");  // 设置过期时间

        //数值操作
        jedis.set("pv", "100");
        jedis.incr("pv");
        jedis.incrBy("pv", 5);
        print(2, jedis.get("pv"));
        jedis.decrBy("pv", 2);
        print(3, jedis.get("pv"));

        print(4, jedis.keys("*"));  //正则表达式

        String listName = "list";
        jedis.del(listName);
        for (int i = 0; i < 10; i++) {
            jedis.lpush(listName, "a" + String.valueOf(i));
        }
        print(5, jedis.lrange(listName, 0, 12));
        print(6, jedis.llen(listName));
        print(7, jedis.lpop(listName));
        print(8, jedis.llen(listName));
        jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "a4", "xx");
        jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "a4", "yy");
        print(9, jedis.lrange(listName, 0, 13));

        //hash
        String userKey = "userxx";
        jedis.hset(userKey, "name", "jia");
        jedis.hset(userKey, "age", "12");
        jedis.hset(userKey, "phone", "1231433234");
        print(12, jedis.hget(userKey, "name"));
        print(13, jedis.hgetAll(userKey));
        jedis.hdel(userKey, "phone");
        print(13, jedis.hgetAll(userKey));
        print(14, jedis.hexists(userKey, "age"));
        print(15, jedis.hexists(userKey, "phone"));
        print(16, jedis.hkeys(userKey));
        print(17, jedis.hvals(userKey));
        jedis.hsetnx(userKey, "school", "zjx");
        jedis.hsetnx(userKey, "age", "44");
        print(18, jedis.hgetAll(userKey));

        //set,没有重复元素
        String likeKey1 = "commentLike1";
        String likeKey2 = "commentLike2";
        for (int i = 0; i < 10; i++) {
            jedis.sadd(likeKey1, String.valueOf(i));
            jedis.sadd(likeKey2, String.valueOf(i*i));
        }
        print(20, jedis.smembers(likeKey1));
        print(21, jedis.smembers(likeKey2));
        print(22, jedis.sunion(likeKey1, likeKey2));
        print(23, jedis.sdiff(likeKey1, likeKey2));     //第一个没有，第二个有的
        print(24, jedis.sinter(likeKey1, likeKey2));
        print(25, jedis.sismember(likeKey1, "12"));
        print(26, jedis.sismember(likeKey2, "16"));
        jedis.srem(likeKey1, "5");

        //优先级队列
        String rankKey = "rankKey";
        jedis.zadd(rankKey, 15, "jia");
        jedis.zadd(rankKey, 60, "ben");
        jedis.zadd(rankKey, 90, "Lee");
        jedis.zadd(rankKey, 75, "Lucy");
        jedis.zadd(rankKey, 80, "Mei");
        print(30, jedis.zcard(rankKey));
        print(31, jedis.zcount(rankKey, 61, 100));
        print(32, jedis.zscore(rankKey, "Lucy"));
        jedis.zincrby(rankKey, 2, "Lucy");
        print(32, jedis.zscore(rankKey, "Lucy"));
        jedis.zincrby(rankKey, 2, "Luc");       //不存在的用户默认会加上
        print(32, jedis.zscore(rankKey, "Luc"));
        print(33, jedis.zrange(rankKey, 0, 100));
        print(36, jedis.zrange(rankKey, 1, 3)); //默认从小到大排序,根据排名
        print(37, jedis.zrevrange(rankKey, 1, 3));  //反过来顺序
        //根据分数范围来进行排序输出
        for (Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, 60, 100)) {
            print(38, tuple.getElement() + ":" + String.valueOf(tuple.getScore()));
        }
        //查看某人排名
        print(39, jedis.zrank(rankKey, "ben"));
        print(39, jedis.zrevrank(rankKey, "ben"));




    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/10");
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }
}
