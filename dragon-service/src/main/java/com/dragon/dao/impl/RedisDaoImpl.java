package com.dragon.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.dragon.reflect.BeanUtils;
import org.dragon.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.dragon.dao.RedisDao;

public class RedisDaoImpl implements RedisDao{
	
	@Autowired
	protected StringRedisTemplate stringRedisTemplate = null ; 
	
	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;
	
	private RedisSerializer<String> getRedisSerializer() {
		return stringRedisTemplate.getStringSerializer() ;
	}
	
	
	public String get(final String keyId){
		return stringRedisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();  
				byte[] key = serializer.serialize( keyId );
				byte[] result = connection.get(key) ; 
				return serializer.deserialize( result ) ;
			}
		});
	}
	
	public boolean delete(String key) {
		stringRedisTemplate.delete(key) ; 
		return true ;
	}
	
	public boolean set(final String key,final String value) {
		return stringRedisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer() ;
				if(null == get(key)){
					return connection.setNX(serializer.serialize(key) , serializer.serialize(value) ) ;
				}else{
					connection.set(serializer.serialize(key) ,  serializer.serialize(value) );
					return true ; 
				}
			}
		}) ;
	}
	
	public boolean set(String head, String key, Object value) {
		Map<String, String> beanToMap = BeanUtils.beanToMapString( value );
		String allKey = String.format("%s_%s", head ,  
				ConvertUtils.trimString(beanToMap.get( key )) ); 
		BoundHashOperations<String, Object, Object> boundHashOps =
				stringRedisTemplate.boundHashOps( allKey ) ; 
		boundHashOps.putAll( beanToMap ) ; 
		return true ;
	}
	
	
	public Map<String,String> get(String head,String key) {
		final String allKey = String.format("%s_%s", head ,  key ) ;
		return stringRedisTemplate.execute(new RedisCallback<Map<String,String>>() { 
			 public Map<String,String> doInRedis(RedisConnection connection)
					throws DataAccessException {
				Map<String,String> keyValue = new HashMap<String, String>();
				byte[] key = getRedisSerializer().serialize( allKey ) ;
				if(connection.exists(key)){
					Map<byte[], byte[]> hGetAll = connection.hGetAll( key );
					for(byte[] keyBytes : hGetAll.keySet()){
						keyValue.put(getRedisSerializer().deserialize(keyBytes)
								, getRedisSerializer().deserialize(hGetAll.get(keyBytes)) );
					} 
				}
				return keyValue;
			}
		}) ; 
	}
	
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring-data-redis.xml") ; 
		RedisDaoImpl redisDao = applicationContext.getBean( RedisDaoImpl.class ) ; 
		System.out.println( redisDao.get("user", "1") ) ; 
		applicationContext.close(); 
	}
}
