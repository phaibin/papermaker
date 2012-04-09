package com.jje.las.service;

import com.jje.las.action.admin.LogDelForm;
import com.jje.las.action.log.Log;
import com.jje.las.action.log.LogQueryForm;
import com.jje.las.action.log.LogQueryResult;
import com.jje.las.config.LasConfiguration;
import com.jje.las.handler.MongoHandler;
import com.jje.las.util.Convert;
import com.mongodb.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LasLogService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LasConfiguration conf;
    @Autowired
    MongoHandler handler;

    private DBCollection getLogCollection() {
        DB db = handler.getConnection().getDB(conf.getSchema());
        DBCollection conn = db.getCollection(conf.getErrorTable());
        return conn;
    }

    public void insert(Log l) {
        if(l == null ){
            return;
        }
        BasicDBObject b = Convert.parseDBObject(l);
        getLogCollection().insert(b);
    }

    public LogQueryResult query(LogQueryForm form) {

        List<Log> list = new ArrayList<Log>();
        DBCollection collection = getLogCollection();
        BasicDBObject queryObject = new BasicDBObject();
        buildBetweenCondition(form, queryObject);
        if (StringUtils.hasLength(form.getPriority())) {
            queryObject.put("priority", form.getPriority());
        }
        if (StringUtils.hasLength(form.getLogFrom())) {
            queryObject.put("logFrom", form.getLogFrom());
        }

        BasicDBObject sort = new BasicDBObject();
        sort.put("logTime", -1);
        logger.info("query object:" + queryObject);
        logger.info("sort object:" + sort);
        long count = collection.count(queryObject);
        DBCursor cursor = null;
        if (form.getPage() > 1) {
            cursor = collection.find(queryObject).skip((form.getPage() - 1) * form.getPageSize()).sort(sort)
                    .limit(form.getPageSize());
        } else {
            cursor = collection.find(queryObject).sort(sort).limit(form.getPageSize());
        }

        while (cursor.hasNext()) {
            BasicDBObject b = (BasicDBObject) cursor.next();
            Log l = null;
            try {
                l = (Log) Convert.parseObject(b, Log.class);
                list.add(l);
            } catch (Exception e) {
                logger.error("error in query.", e);
            }
        }
        cursor.close();
        return LogQueryResult.getResult(list, count, form.getPage(), form.getPageSize());

    }

    private void buildBetweenCondition(LogQueryForm form, BasicDBObject queryObject) {
        swapBeginEnd(form);
        BasicDBObject time = new BasicDBObject();
        if (null != form.getBegin()) {
            time.put("$gte", form.getBegin());
        }

        if (null != form.getEnd()) {
            time.put("$lte", form.getEnd());
        }

        if (!time.isEmpty()) {
            queryObject.put("logTime", time);
        }
    }

    private void swapBeginEnd(LogQueryForm form) {
        if (form.getBegin() != null && form.getEnd() != null) {
            if (form.getBegin().after(form.getEnd())) {
                Date tmp = form.getBegin();
                form.setBegin(form.getEnd());
                form.setEnd(tmp);
            }
        }
    }

    public List<Log> associateQuery(Log l) {
        LogQueryForm form = new LogQueryForm();
        Calendar c = Calendar.getInstance();
        c.setTime(l.getLogTime());
        c.add(Calendar.SECOND, -5);
        form.setBegin(c.getTime());
        c.add(Calendar.SECOND, 10);
        form.setEnd(c.getTime());
        return query(form).getList();
    }

    public Log get(String id) {
        try {
            DBCollection collection = getLogCollection();
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("_id", new ObjectId(id));
            BasicDBObject o = (BasicDBObject) collection.findOne(searchQuery);
            return (Log) Convert.parseObject(o, Log.class);
        } catch (Exception e) {
            logger.error("error in get ", e);
        }
        return null;
    }

    public void delete(LogDelForm form) {
        if (null != form.getDt()) {
            BasicDBObject time = new BasicDBObject();
            BasicDBObject deleteCondition = new BasicDBObject();
            time.put("$lte", form.getDt());
            deleteCondition.put("logTime", time);
            DBCollection collection = getLogCollection();
            collection.remove(deleteCondition);
        }
    }
}
