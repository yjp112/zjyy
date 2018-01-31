---------------------------------------------
-- Export file for user JCDS               --
-- Created by DELL on 2015/08/21, 13:55:05 --
---------------------------------------------

spool view.log

prompt
prompt Creating view VIEW_NH_ELECTRIC_METER
prompt ====================================
prompt
CREATE OR REPLACE VIEW JCDS.VIEW_NH_ELECTRIC_METER AS
(
	SELECT
		BIT_NO,
		nvl(TOTAL - INCREMENTAL, 0) LAST_TOTAL,
		TOTAL,
		INCREMENTAL,
		COLLECT_TIME
	FROM
		NH_ELECTRIC_METER
	UNION ALL
		SELECT
			BIT_NO,
			nvl(TOTAL - INCREMENTAL, 0) LAST_TOTAL,
			TOTAL,
			INCREMENTAL,
			COLLECT_TIME
		FROM
			NH_ELECTRIC_METER_TOTAL
);

prompt
prompt Creating view VIEW_NH_ENERGY_METER
prompt ==================================
prompt
CREATE OR REPLACE VIEW JCDS.VIEW_NH_ENERGY_METER AS
(
  SELECT
    BIT_NO,
    nvl(TOTAL - INCREMENTAL, 0) LAST_TOTAL,
    TOTAL,
    INCREMENTAL,
    COLLECT_TIME
  FROM
    NH_ENERGY_METER
  UNION ALL
    SELECT
      BIT_NO,
      nvl(TOTAL - INCREMENTAL, 0) LAST_TOTAL,
      TOTAL,
      INCREMENTAL,
      COLLECT_TIME
    FROM
      NH_ENERGY_METER_TOTAL
);

prompt
prompt Creating view VIEW_NH_GAS_METER
prompt ===============================
prompt
CREATE OR REPLACE VIEW JCDS.VIEW_NH_GAS_METER AS
(
  SELECT
    BIT_NO,
    nvl(TOTAL - INCREMENTAL, 0) LAST_TOTAL,
    TOTAL,
    INCREMENTAL,
    COLLECT_TIME
  FROM
    NH_GAS_METER
  UNION ALL
    SELECT
      BIT_NO,
      nvl(TOTAL - INCREMENTAL, 0) LAST_TOTAL,
      TOTAL,
      INCREMENTAL,
      COLLECT_TIME
    FROM
      NH_GAS_METER_TOTAL
);

prompt
prompt Creating view VIEW_NH_WATER_METER
prompt =================================
prompt
CREATE OR REPLACE VIEW JCDS.VIEW_NH_WATER_METER AS
(
  SELECT
    BIT_NO,
    nvl(TOTAL - INCREMENTAL, 0) LAST_TOTAL,
    TOTAL,
    INCREMENTAL,
    COLLECT_TIME
  FROM
    NH_WATER_METER
  UNION ALL
    SELECT
      BIT_NO,
      nvl(TOTAL - INCREMENTAL, 0) LAST_TOTAL,
      TOTAL,
      INCREMENTAL,
      COLLECT_TIME
    FROM
      NH_WATER_METER_TOTAL
);


spool off
