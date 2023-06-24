#!/bin/bash
for i in `seq 1 1000`;
do
    curl -s http://localhost:80/categories/whatAmI >> test.txt
    echo
done


