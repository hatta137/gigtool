CREATE DATABASE gigtool;

CREATE USER gigtool WITH ENCRYPTED PASSWORD 'gigtool';
GRANT ALL PRIVILEGES ON DATABASE gigtool TO gigtool;

-- Database for Unittests
CREATE DATABASE gigtool_test;
CREATE USER test_user WITH ENCRYPTED PASSWORD 'testpw';
GRANT ALL PRIVILEGES ON DATABASE gigtool_test TO test_user;
