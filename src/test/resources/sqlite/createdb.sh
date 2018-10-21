#!/bin/bash
cd ./src/test/resources/sqlite
rm test.db
printf '.read model\n.quit\n' | sqlite3 test.db


