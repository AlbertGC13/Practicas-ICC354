#!/usr/bin/env bash

host1=ec2-44-211-80-176.compute-1.amazonaws.com
host2=ec2-54-208-31-195.compute-1.amazonaws.com
host3=ec2-54-209-136-223.compute-1.amazonaws.com

host_balanceo=ec2-54-209-136-223.compute-1.amazonaws.com

ruta_key=~/Downloads/key.pem
chmod 400 $ruta_key

scp -i $ruta_key files/* .env docker-compose.yml Dockerfile ubuntu@$host1:~/
scp -i $ruta_key files/* .env docker-compose.yml Dockerfile ubuntu@$host2:~/
scp -i $ruta_key files/* .env docker-compose.yml Dockerfile ubuntu@$host3:~/
scp -i $ruta_key files/* .env docker-compose.yml Dockerfile ubuntu@$host_balanceo:~/

