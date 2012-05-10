package com.jje.las.analysis;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.las.analysis.command.AnalysisChain;
import com.jje.las.analysis.command.LasContext;
import com.jje.las.domain.Log;
import com.jje.las.domain.MonitFile;
import com.jje.las.service.LasService;

@Component
public class FileParserHandler {

    private LasService handle;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AnalysisChain analysisChain;

    @Autowired
    public void setHandle(LasService handle) {
        this.handle = handle;
    }

    public void handleLogFile(MonitFile mfile, IAction action) {
        try {
            parser(mfile.getRealFile(), mfile.getFileName(), mfile.getEncoding());
        } catch (Exception e) {
            logger.error("Parse exception:", e);
        }
        action.perform(mfile);
    }

    protected void parser(File file, String from, String encoding) throws Exception {
        Log last = null;
        LasContext context = new LasContext();
        LineIterator it = FileUtils.lineIterator(file, encoding);
        try {
            while (it.hasNext()) {
                context.setCurrentLine(it.nextLine()).setLastLog(last).setFileFrom(from);
                if (!analysisChain.execute(context)) {
                    logger.debug("Can't parser line : " + context.getCurrentLine());
                    continue;
                }
                Log newLog = context.getNewLog();
                if (newLog != null) {
                    handle.insert(last);
                    last = newLog;
                }
            }
            handle.insert(last);
        } finally {
            it.close();
        }
    }

}
