package sport;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.datanucleus.DatastoreManager;



public class CacheManager {

	private DataManager dsm;

	public CacheManager() {
		super();
		dsm = new DataManager();

	}


	public void setValue(String k, String v){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		syncCache.put(k, v); // populate cache
	}

	public String getValue(String k){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		String value = (String) syncCache.get(k); // read from cache
		return value;
	}
	
	public void deleteValue(String k){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		syncCache.delete(k);
	}


	public String getValueCache(String entity,String key){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		String value = (String) syncCache.get(entity+"-"+key); // read from cache
		System.out.println("Cache value : " + value);
		if (value == null) {
			System.out.println("Message not in cache");
			String val_dataS = dsm.getOneEgualValue(entity, key);
			syncCache.put(entity+"-"+key, val_dataS); // populate cache
			return val_dataS;
		}
		else{
			return value;
		}

	}










}


