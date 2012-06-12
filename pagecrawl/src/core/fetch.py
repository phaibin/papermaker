#!/usr/bin/env python2.7
# -*- coding: utf-8 -*-
import threading
import urllib2

class Fetch:
    pass

class ThreadUrl(threading.Thread):
    def __init__(self, queue, out_queue):
        threading.Thread.__init__(self)
        self.queue = queue
        self.out_queue = out_queue

    def run(self):
        while True:
            host = self.queue.get()
            opener = urllib2.build_opener();
            chunk = opener.open(host).read()
            self.out_queue.put(chunk)
            self.queue.task_done()

class DatamineThread(threading.Thread):
    def __init__(self, out_queue):
        threading.Thread.__init__(self)
        self.out_queue = out_queue

    def run(self):
        while True:
            chunk = self.out_queue.get()
            self.out_queue.task_done()

