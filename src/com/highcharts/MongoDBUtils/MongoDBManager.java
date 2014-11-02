package com.highcharts.MongoDBUtils;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.highcharts.Model.ElectionResult;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDBManager {
	
	MongoClient mongoclient;
	DB db;
	DBCollection collection;

	public MongoDBManager() {
		try {
			mongoclient = new MongoClient("localhost" , 27017);
			db                   = mongoclient.getDB("bulk");
			collection = db.getCollection("test");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Map<String, Object>> getCountyResult(String countyID){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		BasicDBObject nfield = new BasicDBObject();
		nfield.put("_id", 0); // _id bilgisi gelmesin
		BasicDBObject query = new BasicDBObject().append("county", countyID); // ilçe bilgisi verilerek 
		DBCursor cursor = collection.find(query,nfield);
		while(cursor.hasNext()){
			DBObject dbo = cursor.next();
			BasicDBObject basicdbobj = (BasicDBObject) dbo;
			
			ElectionResult ero = new ElectionResult();
			ero.setParty(basicdbobj.getString("party"));
			ero.setVote(basicdbobj.getInt("vote"));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("electionresult", ero);
			list.add(map);
		}
		return list;
	}	
	public Map<String, Object> getCountyResult(){
		Map<String, Object> map = new HashMap<String, Object>();
		BasicDBObject allQuery = new BasicDBObject();
		BasicDBObject fields = new BasicDBObject();
		fields.put("_id", 0);
		DBCursor cursor = collection.find(allQuery,fields);
		while(cursor.hasNext()){
			DBObject dbo = cursor.next();
			BasicDBObject basicdbobj = (BasicDBObject) dbo;
			int ap = basicdbobj.getInt("AP");
			int bp = basicdbobj.getInt("BP");
			int cp = basicdbobj.getInt("CP");
			
			map.put("ap", ap);
			map.put("bp", bp);
			map.put("cp", cp);
		}
		return map;
	}	
}
