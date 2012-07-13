import socket
import sys
reload(sys)
sys.setdefaultencoding('utf-8')
        
origGetAddrInfo = socket.getaddrinfo

def getAddrInfoWrapper(host, port, family=0, socktype=0, proto=0, flags=0):
    return origGetAddrInfo(host, port, socket.AF_INET, socktype, proto, flags)

socket.getaddrinfo = getAddrInfoWrapper

print '---------------init core package-------------'