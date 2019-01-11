#!/bin/sh
docker build -f Dockerfile -t demo/chat:jre8 .
docker run -i demo/chat:jre8
