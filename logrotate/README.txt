将指定日志文件按时间间隔切分到指定目录新文件，并备份到备份目录文件中
可以通过配置文件配置属性参数
动作结束后，执行脚本
文件名生产规则

1. command parser - jopt 
2. file reader/writer - commons io RandomAccessFile FileChannel
2.1 script execute - java processbuilder/common exec
3. parse command line
4. configuration with command line
5. configuration with config file