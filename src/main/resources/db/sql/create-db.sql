CREATE OR REPLACE TABLE template_config (
  id         BIGINT NOT NULL PRIMARY KEY,
  template_name VARCHAR(250),
  class_names  VARCHAR(1000),
  OWNER_EMAILS VARCHAR(1000),
  EMAILS_BUILDER VARCHAR(1000),
);

CREATE OR REPLACE TABLE template_updater_config (
  id         BIGINT NOT NULL PRIMARY KEY,
  template_name VARCHAR(250),
  class_names  VARCHAR(1000)
);

CREATE OR REPLACE TABLE file_error_log (
    id          BIGINT NOT NULL PRIMARY KEY,
    batch_id    BIGINT NOT NULL,
    SEVERITY    VARCHAR(10) NOT NULL,
    error_msg   VARCHAR(4000),
    CREATED_TIME TIMESTAMP NOT NULL
);

CREATE OR REPLACE TABLE SESSION_HEADER (    
    ID BIGINT NOT NULL PRIMARY KEY, 
    TEMPLATE VARCHAR(30) NOT NULL, 
    FILE_NAME VARCHAR2(250) NOT NULL,
    PROCESS_DATE DATE NOT NULL, 
    TENEMENT VARCHAR(250), 
    TENEMENT_HOLDER VARCHAR(250), 
    REPORTING_DATE DATE NOT NULL, 
    PROJECT_NAME VARCHAR(250), 
    STATUS VARCHAR(20) NOT NULL, 
    COMMENTS VARCHAR(1000), 
    EMAIL_SENT CHAR(1) DEFAULT 'N',
    APPROVED INT DEFAULT 0 NOT NULL CHECK ( APPROVED IN (0,1)),
    REJECTED INT DEFAULT 0 NOT NULL CHECK( REJECTED IN (0,1)),
    CREATED TIMESTAMP DEFAULT SYSDATE NOT NULL,
);

CREATE OR REPLACE TABLE DH_DRILLING_DETAILS (
    ID          BIGINT NOT NULL PRIMARY KEY,
    FILE_NAME VARCHAR(50) NOT NULL,
    DRILL_TYPE  VARCHAR(20) NOT NULL,
    DRILL_COMPANY VARCHAR(250) NOT NULL,
    DRILL_DESCRIPTION VARCHAR(250)
);

CREATE TABLE lOC_SITE (
    LOADER_ID BIGINT NOT NULL, 
    SITE_ID VARCHAR(250) NOT NULL, 
    GSV_SITE_ID BIGINT DEFAULT null, 
    ROW_NUMBER VARCHAR(10) NOT NULL, 
    PARISH VARCHAR(250) DEFAULT ' ' NOT NULL, 
    PROSPECT VARCHAR(250) DEFAULT 'UNK' NOT NULL, 
    AMG_ZONE DECIMAL DEFAULT 0 NOT NULL, 
    EASTING DECIMAL DEFAULT 0 NOT NULL, 
    NORTHING DECIMAL DEFAULT 0 NOT NULL, 
    LATITUDE DECIMAL DEFAULT 0, 
    LONGITUDE DECIMAL DEFAULT 0, 
    LOCN_ACC DECIMAL DEFAULT 0, 
    LOCN_DATUM_CD VARCHAR2(5) DEFAULT '0' NOT NULL, 
    ELEVATION_GL DECIMAL DEFAULT null, 
    ELEV_ACC DECIMAL DEFAULT null, 
    ELEV_DATUM_CD VARCHAR2(5) DEFAULT 'UNK' NOT NULL, 
    COORD_SYSTEM VARCHAR2(20), 
    VERTICAL_DATUM VARCHAR2(25), 
    NUM_DATA_RECORDS int NOT NULL,
    FILE_NAME VARCHAR(50) NOT NULL,
    ISSUE_COLUMN_INDEX int DEFAULT -1,
    UWI VARCHAR2(250) DEFAULT NULL,
    LOCN_NAME VARCHAR2(250) DEFAULT NULL,
    LOCN_DESC VARCHAR2(250) DEFAULT NULL,
    STATE VARCHAR2(25) DEFAULT NULL,
    BORE_DIAMETER DECIMAL DEFAULT 0,
    TD DECIMAL DEFAULT 0,
    TVD DECIMAL DEFAULT 0,
    DEPTH_DATUM VARCHAR2(5) DEFAULT NULL,
);

CREATE TABLE DH_BOREHOLE (    
    LOADER_ID BIGINT NOT NULL, 
    FILE_NAME VARCHAR(50) NOT NULL,
    HOLE_ID VARCHAR(120) NOT NULL, 
    ROW_NUMBER VARCHAR(10) NOT NULL, 
    BH_AUTHORITY_CD VARCHAR2(1) DEFAULT 'U' NOT NULL, 
    BH_REGULATION_CD VARCHAR2(5) DEFAULT 'UNK' NOT NULL, 
    DILLING_DETAILS_ID BIGINT NOT NULL,
    DRILLING_START_DT DATE NOT NULL, 
    DRILLING_COMPLETION_DT DATE NOT NULL, 
    DEPTH DECIMAL, 
    ELEVATION_KB DECIMAL, 
    AZIMUTH_MAG DECIMAL,
    BH_CONFIDENTIAL_FLG VARCHAR2(1) DEFAULT 'N' NOT NULL,
    DEPTH_UOM VARCHAR2(10) DEFAULT 'MTR' NOT NULL, 
    AZIMUTH VARCHAR2(20), 
    LOCAL_NAME VARCHAR2(225), 
    AZIMUTH_UOM VARCHAR2(20), 
    DIP VARCHAR2(20), 
    DIP_UOM VARCHAR2(20)
);

CREATE TABLE TEMPLATE_CONTEXT_PROPERTIES (    
    ID BIGINT NOT NULL, 
    TEMPLATE_NAME VARCHAR2(250) DEFAULT NULL NOT NULL, 
    PROPERTY_NAME VARCHAR2(250) DEFAULT NULL NOT NULL, 
    PROPERTY_VALUE VARCHAR2(1000) DEFAULT NULL NOT NULL
);

CREATE TABLE DH_DOWNHOLE (    
    ID BIGINT NOT NULL, 
    LOADER_ID BIGINT DEFAULT NULL NOT NULL,
    HOLE_ID VARCHAR2(250) DEFAULT NULL NOT NULL,
    FILE_NAME VARCHAR2(250) NOT NULL,
    SURVEYED_DEPTH decimal DEFAULT NULL NOT NULL,
    AZIMUTH_MAG decimal DEFAULT NULL,
    DIP decimal DEFAULT NULL NOT NULL,
    AZIMUTH_TRUE decimal DEFAULT NULL
);

CREATE TABLE DH_LITHOLOGY (    
    ID BIGINT NOT NULL, 
    LOADER_ID BIGINT DEFAULT NULL NOT NULL,
    HOLE_ID VARCHAR2(250) DEFAULT NULL NOT NULL,
    FILE_NAME VARCHAR(50) NOT NULL,
    DEPTH_FROM decimal DEFAULT NULL NOT NULL
);

CREATE TABLE DH_GEOCHEMISTRY (    
    ID BIGINT NOT NULL, 
    LOADER_ID BIGINT DEFAULT NULL NOT NULL,
    HOLE_ID VARCHAR2(250) DEFAULT NULL NOT NULL,
    SAMPLE_ID VARCHAR2(250) DEFAULT NULL NOT NULL,
    FILE_NAME VARCHAR(50) NOT NULL,
    SAMPLE_FROM decimal DEFAULT NULL NOT NULL,
    SAMPLE_TO decimal DEFAULT NULL NOT NULL,
    DRILL_CODE VARCHAR2(10) DEFAULT NULL NOT NULL
);

CREATE TABLE DH_SURFACE_GEOCHEMISTRY (    
    ID BIGINT NOT NULL, 
    LOADER_ID BIGINT DEFAULT NULL NOT NULL,
    SAMPLE_ID VARCHAR2(250) DEFAULT NULL NOT NULL,
    FILE_NAME VARCHAR(50) NOT NULL,
    EASTING DECIMAL DEFAULT 0 NOT NULL, 
    NORTHING DECIMAL DEFAULT 0 NOT NULL, 
    AMG_ZONE DECIMAL DEFAULT 0 NOT NULL,
    SAMPLE_TYPE VARCHAR2(50) DEFAULT NULL NOT NULL,
    ISSUE_COLUMN_INDEX int
);

CREATE TABLE DH_OPTIONAL_FIELDS (    
    ID BIGINT NOT NULL, 
    LOADER_ID BIGINT DEFAULT NULL NOT NULL,
    FILE_NAME VARCHAR(50) NOT NULL,
    TEMPLATE_NAME VARCHAR2(100) DEFAULT NULL NOT NULL, 
    TEMPLATE_HEADER VARCHAR2(100) DEFAULT NULL NOT NULL, 
    ROW_NUMBER VARCHAR2(10) DEFAULT 0 NOT NULL, 
    FIELD_VALUE VARCHAR2(4000) DEFAULT NULL
);

CREATE TABLE TEMPLATE_DISPLAY_PROPERTIES (    
    ID BIGINT NOT NULL, 
    TEMPLATE VARCHAR2(30) NOT NULL, 
    DISPLAY_PROPERTIES  VARCHAR2(4000)
)    