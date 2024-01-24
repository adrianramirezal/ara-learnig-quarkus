#!/usr/bin/env bash

set -euo pipefail
which docker > /dev/null || (echoerr "Please ensure that docker is in your PATH" && exit 1)
docker run --rm --name jaeger-docker -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 -d jaegertracing/all-in-one:latest
