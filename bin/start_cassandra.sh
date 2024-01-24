#!/usr/bin/env bash

set -euo pipefail
which docker > /dev/null || (echoerr "Please ensure that docker is in your PATH" && exit 1)
SCRIPTPATH="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

mkdir -p $HOME/docker/volumes/cassandra
mkdir -p $HOME/docker/volumes/cassandra/default_schema

rm -rf $HOME/docker/volumes/cassandra/data
rm -rf $HOME/docker/volumes/cassandra/commit_logs
rm -rf $HOME/docker/volumes/cassandra/saved_caches

cp "$SCRIPTPATH/schema.cql" $HOME/docker/volumes/cassandra/default_schema/
cp "$SCRIPTPATH/data.cql" $HOME/docker/volumes/cassandra/default_schema/

docker run --rm --name cass-docker -v $HOME/docker/volumes/cassandra:/var/lib/cassandra  -v $HOME/docker/volumes/cassandra/default_schema:/opt/data -p 9042:9042 -e DS_LICENSE=accept -d datastax/ddac:5.1.17

sleep 120

docker exec cass-docker cqlsh  -f /opt/data/schema.cql
docker exec cass-docker cqlsh  -f /opt/data/data.cql
