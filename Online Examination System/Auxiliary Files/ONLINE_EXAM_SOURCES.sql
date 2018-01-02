SQL>CONNECT system/###;
SQL>CREATE USER exam IDENTIFIED BY ###;
SQL>GRANT CONNECT , RESOURCE TO exam;

-- CREATE QUESTIONS TABLE
CREATE TABLE QUESTIONS(
    QNO NUMBER(3) PRIMARY KEY CHECK(QNO <= 100),
    QUESTION VARCHAR2(100) NOT NULL,
    OPTION1 VARCHAR2(10) NOT NULL,
    OPTION2 VARCHAR2(10) NOT NULL,
    OPTION3 VARCHAR2(10) NOT NULL,
    OPTION4 VARCHAR2(10) NOT NULL
);
-- END

-- CREATE CANDIDATE TABLE
CREATE TABLE CANDIDATE(
    ROLL NUMBER(2) PRIMARY KEY,
    LOGPASS VARCHAR2(5) NOT NULL
);
-- END

-- CREATE RESPONSE TABLE
CREATE TABLE RESPONSE(
    ROLL NUMBER(2) PRIMARY KEY,
    RESP VARCHAR2(100) NOT NULL
);
-- END

-- AUXILIARY QUERIES
SELECT LOGPASS FROM CANDIDATE WHERE ROLL = 
-- END

INSERT INTO QUESTIONS VALUES(
    4,
    'QUESTION 4',
    'Q4 O1',
    'Q4 O2',
    'Q4 O3',
    'Q4 O4'
);

-- AUXILIARY QUERIES
SELECT QUESTION,OPTION1,OPTION2,OPTION3,OPTION4 FROM QUESTIONS
-- END
