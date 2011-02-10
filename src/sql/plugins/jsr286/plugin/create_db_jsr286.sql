-- Table structure for table 'portlet_jsr286'

DROP TABLE IF EXISTS portlet_jsr286;
CREATE TABLE portlet_jsr286 (
  id_portlet INT DEFAULT '0' NOT NULL,
  jsr286Name varchar(100) NOT NULL,
  contextName varchar(255) NOT NULL,
  PRIMARY KEY (id_portlet)
);

CREATE INDEX index_portlet_jsr286 ON portlet_jsr286 (id_portlet);