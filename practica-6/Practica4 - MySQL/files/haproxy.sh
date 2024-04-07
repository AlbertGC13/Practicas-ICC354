#!/usr/bin/env bash
echo "Configuracion sesiones centralizadas de HAProxy"

if [ ! -e "/etc/haproxy/haproxy.cfg.original" ]; then
    echo "Creando copia del archivo"
    sudo cp /etc/haproxy/haproxy.cfg /etc/haproxy/haproxy.cfg.original
fi

sudo apt update && sudo apt install -y haproxy certbot nmap

sudo cp ~/haproxy.cfg.sesiones-centralizadas /etc/haproxy/haproxy.cfg

sudo service haproxy stop && sudo service haproxy start
