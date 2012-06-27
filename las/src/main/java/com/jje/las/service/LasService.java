package com.jje.las.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jje.las.action.admin.LogDelForm;
import com.jje.las.action.log.LogQueryResult;
import com.jje.las.config.LasConfiguration;
import com.jje.las.domain.Log;
import com.jje.las.domain.MongoLogObject;
import com.jje.las.handler.MongoHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@Service
public class LasService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LasConfiguration lasConf;
    @Autowired
    MongoHandler handler;

    public void insert(Log log) {
        if (log == null) {
            return;
        }
        handler.getDayPriorityDBTable(log.getLogTime(), log.getPriority()).insert(MongoLogObject.get(log));
    }

    public LogQueryResult query(Date from, Date to, String priority, String module, int page, int pageSize) {
        valid(from, to, priority, module);
        List<Log> list = new ArrayList<Log>();
        DBCollection collection = handler.getDayPriorityDBTable(from, priority);
        QueryBuilder qb = QueryBuilder.start("logTime").greaterThanEquals(from).and("logTime").lessThanEquals(to);
        if(!module.equals("")){
            qb.and("module").is(module);
        }
        DBObject condition = qb.get();
        DBObject sort = QueryBuilder.start("logTime").is(-1).get();
        logger.info("query object: {} with sort {} " ,condition, sort);
        long count = collection.count(condition);
        DBCursor cursor = null;
        if (page > 1) {
            cursor = collection.find(condition).skip((page - 1) * pageSize).sort(sort).limit(pageSize);
        } else {
            cursor = collection.find(condition).sort(sort).limit(pageSize);
        }
        try{
        	while (cursor.hasNext()) {
        		try {
        			list.add(MongoLogObject.get(cursor.next()));
        		} catch (Exception e) {
        			logger.error("error in query.", e);
        		}
        	}
        }finally{
        	cursor.close();
        }
        return LogQueryResult.getResult(list, count, page, pageSize);

    }

    private void valid(Date from, Date to, String priority, String module) {
        logger.debug("validate data:"+priority+":"+module+":"+from+":"+to);
        if (priority == null || module == null || from == null || to == null) {
            throw new IllegalArgumentException("Priority & module & from/to must not be null in Query.");
        }
        Calendar cfrom = Calendar.getInstance();
        cfrom.setTime(from);
        Calendar cto = Calendar.getInstance();
        cto.setTime(to);
        if (cfrom.after(cto) || !isSameDay(cfrom, cto)) {
            throw new IllegalArgumentException("fromDate must before toDate and should be same day in query");
        }
    }

    private boolean isSameDay(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    public List<Log> associateQuery(Log l) {
        Calendar c = Calendar.getInstance();
        c.setTime(l.getLogTime());
        c.add(Calendar.SECOND, -lasConf.getInterval());
        Date from = c.getTime();
        c.add(Calendar.SECOND, lasConf.getInterval() * 2);
        Date to = c.getTime();

        DBObject condition = QueryBuilder.start("logTime").greaterThanEquals(from).and("logTime").lessThanEquals(to)
                .get();
        DBObject sort = QueryBuilder.start("logTime").is(-1).get();
        logger.info("query object in associate query: {}", condition);
        List<Log> results = new ArrayList<Log>();
        String[] prioritys = lasConf.getPrioritys();
        for(String p : prioritys){
            results.addAll(query(l, p, condition, sort));
        }
        return results;
    }

    private List<Log> query(Log l, String priority, DBObject condition, DBObject sort) {
    	List<Log> list = new ArrayList<Log>();
        DBCursor cursor = handler.getDayPriorityDBTable(l.getLogTime(), priority).find(condition).sort(sort);
        try{
        	while (cursor.hasNext()) {
        		try {
        			list.add(MongoLogObject.get(cursor.next()));
        		} catch (Exception e) {
        			logger.error("error in query.", e);
        		}
        	}
        }finally{
        	cursor.close();
        }
        return list;
    }

    public Log detail(String id, Date logTime, String priority) {
        try {
            return MongoLogObject.get( handler.getDayPriorityDBTable(logTime, priority).findOne(new BasicDBObject("_id", new ObjectId(id))));
        } catch (Exception e) {
            logger.error("error in get ", e);
        }
        return null;
    }

    /**
     * @deprecated because we split db every day.
     * @param form
     */
    public void delete(LogDelForm form) {
        // no use
    }
}
