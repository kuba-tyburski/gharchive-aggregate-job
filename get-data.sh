#!/usr/bin/env bash
YEAR=$1
MONTH=$2

for DAY in `echo {01..31}`; do
	wget http://data.gharchive.org/${YEAR}-${MONTH}-${DAY}-{0..23}.json.gz;
done