package com.jje.las.analysis;

import com.jje.las.action.admin.MonitFile;
import com.jje.las.action.log.Log;
import com.jje.las.service.ErrorLogService;
import com.jje.las.service.LasLogService;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Log4JHandler implements IHandleLogFile {

    private LasLogService handle;

    @Autowired
    public void setHandle(LasLogService handle) {
        this.handle = handle;
    }

    public void handleLogFile(MonitFile mfile) throws Exception {
        String fileAddress = mfile.getPath();
        File file = new File(fileAddress);
        if (!file.exists()) {
            return;
        }
        FileInputStream is = new FileInputStream(file);
        try {
            parser(is, mfile.getFileName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        is.close();
        file.delete();
    }

    protected void parser(InputStream is, String from) throws IOException {
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line;
        Log last = null;
        while ((line = br.readLine()) != null) {
            Log l = parseLine(line, last);
            if (last != l) {
                if (last != null) {
                    last.setLogFrom(from);
                    handle.insert(last);
                }
                last = l;
            }
        }
        if(last != null){
            last.setLogFrom(from);
            handle.insert(last);
        }
        br.close();
        isr.close();
    }

    //2012-02-09 14:31:43,288 INFO [com.jje.autorental.order.esb.OrderDispatchResource] - 执行同步子订单调度信息, resource url :/autorental/order/syncOrderDispatch
    private Log parseLine(String line, Log last) throws IOException {
        String regex = "(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2},\\d{3}) ([^ ]*) \\[(.*)\\] - (.*)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);
        Log l = new Log();
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (m.matches() && m.groupCount() == 5) {
            try {
                l.setLogTime(simpleFormat.parse(m.group(1) + " " + m.group(2)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            l.setPriority(m.group(3));
            l.setClassName(m.group(4));
            l.setMessage(m.group(5));
            return l;
        } else {
            if(last != null){
                last.appendDetail(line+"\n");
            }
            return last;
        }

    }
}
