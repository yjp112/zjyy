/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ELECTRIC_DAY_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_ELECTRIC_DAY_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_ELECTRIC_DAY_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ELECTRIC_HOUR_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_ELECTRIC_HOUR_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_ELECTRIC_HOUR_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ELECTRIC_MAIN]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_ELECTRIC_MAIN]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_ELECTRIC_MAIN]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ELECTRIC_MONTH_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_ELECTRIC_MONTH_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_ELECTRIC_MONTH_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ENERGY_DAY_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_ENERGY_DAY_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_ENERGY_DAY_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ENERGY_HOUR_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_ENERGY_HOUR_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_ENERGY_HOUR_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ENERGY_MAIN]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_ENERGY_MAIN]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_ENERGY_MAIN]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ENERGY_MONTH_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_ENERGY_MONTH_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_ENERGY_MONTH_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_GAS_DAY_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_GAS_DAY_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_GAS_DAY_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_GAS_HOUR_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_GAS_HOUR_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_GAS_HOUR_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_GAS_MAIN]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_GAS_MAIN]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_GAS_MAIN]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_GAS_MONTH_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_GAS_MONTH_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_GAS_MONTH_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_WATER_DAY_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_WATER_DAY_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_WATER_DAY_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_WATER_HOUR_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_WATER_HOUR_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_WATER_HOUR_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_WATER_MAIN]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_WATER_MAIN]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_WATER_MAIN]
GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_WATER_MONTH_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_ARCHIVE_WATER_MONTH_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_ARCHIVE_WATER_MONTH_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_FIX_ELECTRIC_ARCHIVE_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_FIX_ELECTRIC_ARCHIVE_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_FIX_ELECTRIC_ARCHIVE_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_FIX_ENERGY_ARCHIVE_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_FIX_ENERGY_ARCHIVE_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_FIX_ENERGY_ARCHIVE_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_FIX_GAS_ARCHIVE_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_FIX_GAS_ARCHIVE_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_FIX_GAS_ARCHIVE_DATA]
GO

/****** Object:  StoredProcedure [dbo].[PROC_FIX_WATER_ARCHIVE_DATA]    Script Date: 08/21/2015 10:42:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[PROC_FIX_WATER_ARCHIVE_DATA]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[PROC_FIX_WATER_ARCHIVE_DATA]
GO


GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ELECTRIC_DAY_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE  PROCEDURE [dbo].[PROC_ARCHIVE_ELECTRIC_DAY_DATA]
  @CurDate DATETIME 
AS
BEGIN
  DECLARE @peakFlag INT
  DECLARE @peakStart varchar(2)
  DECLARE @peakEnd varchar(2)

  DECLARE @vallyFlag INT
  DECLARE @vallyStart varchar(2)
  DECLARE @vallyEnd varchar(2)
  
  DECLARE @dayFlag INT
  DECLARE @dayStart varchar(2)
  DECLARE @dayEnd   varchar(2)

  DECLARE @nightFlag INT
  DECLARE @nightStart varchar(2)
  DECLARE @nightEnd varchar(2)

	DECLARE @sql VARCHAR(5000)
	DECLARE @dtEnd DATETIME 
	
	SET @dtEnd = dateadd(day,1,@CurDate)

 
  -- 归档前删除当天数据；
  delete from NH_ELECTRIC_METER_DAY WHERE DAY_OF_MONTH_KEY = @CurDate 

  -- 计算波峰波谷白天晚上等条件
  --select @peakStart=left(config_value, 2),@vallyFlag=flag  from NH_CONFIG_MANAGE where code = 'BFBG_BF'
	--select @vallyStart=left(config_value,2),@vallyEnd=SUBSTRING(config_value, 7, 2),@peakFlag=flag  from NH_CONFIG_MANAGE where code = 'BFBG_BG'
	select @dayStart=left(config_value,2),@dayEnd=SUBSTRING(config_value, 7, 2),@dayFlag=flag  from NH_CONFIG_MANAGE where code = 'BTWS_BT'
	select @nightStart=left(config_value,2),@nightEnd=SUBSTRING(config_value, 7, 2),@nightFlag=flag  from NH_CONFIG_MANAGE where code = 'BTWS_WS'
  
 
  -- 归档数据
  INSERT INTO dbo.NH_ELECTRIC_METER_DAY(
		DEVICE_ID,
		DAY_OF_MONTH_KEY,
		PEAK_VALUE,
		VALLY_VALUE,
		COMMON_VALUE,
		DAY_DAYTIME_VALUE,
		DAY_NIGHT_VALUE,
		TOTAL_DAY_VALUE,
		TOTAL_YOY
		) 
SELECT a.DEVICE_ID
    , a.collect_Date DAY_OF_MONTH_KEY
    , 0 PEAK_VALUE
    , 0 VALLY_VALUE
    , 0 AS COMMON_VALUE
    , sum (CASE WHEN a.COLLECT_HOUR >= convert (INT, @dayStart) AND a.COLLECT_HOUR <= convert (INT, @dayEnd) THEN a.incremental ELSE 0 END) AS DAY_DAYTIME_VALUE
    , sum (CASE WHEN a.COLLECT_HOUR >= convert (INT, @nightStart) OR a.COLLECT_HOUR <= convert (INT, @nightEnd) THEN a.incremental ELSE 0 END) AS DAY_NIGHT_VALUE
    , sum (a.INCREMENTAL) AS TOTAL_DAY_VALUE
    , 0 AS TOTAL_YOY
FROM NH_ELECTRIC_METER a
WHERE a.COLLECT_TIME BETWEEN  @CurDate  AND @dtEnd
GROUP BY a.COLLECT_DATE, a.DEVICE_ID
ORDER BY a.collect_date

   -- 修复TOTAL_YOY数据
	UPDATE b
	SET b.TOTAL_YOY = a.TOTAL_DAY_VALUE
	FROM NH_ELECTRIC_METER_DAY a
	INNER JOIN NH_ELECTRIC_METER_DAY b ON a.DEVICE_ID = b.DEVICE_ID
	AND a.DAY_OF_MONTH_KEY = dateadd(	MONTH ,- 1,	b.DAY_OF_MONTH_KEY)
  AND DATEPART(DAY, b.DAY_OF_MONTH_KEY)= DATEPART(DAY, A.DAY_OF_MONTH_KEY)
	AND b.DAY_OF_MONTH_KEY = @CurDate

END



GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ELECTRIC_HOUR_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_ELECTRIC_HOUR_DATA] 
	@CurDate DATETIME
	
	AS
	
	DECLARE @dtEnd DATETIME   ,@dtBgn DATETIME

	
	TRUNCATE TABLE NH_ELECTRIC_METER_Temp 

	set @dtBgn = @CurDate
  SET @dtEnd = dateadd(day,1,@CurDate)
	set @CurDate=DATEADD(hour,-3, @CurDate)

	
  
INSERT INTO NH_ELECTRIC_METER_Temp (BIT_NO
	    , DEVICE_ID
	    , ReadData1
	    , ReadData2
	    , INCREMENTAL
	    , COLLECT_DATE
	    , COLLECT_HOUR
	    , COLLECT_TIME)
	    SELECT t.BIT_NO
	        , 0
	        , t.TOTAL
	        , 0.00 AS Total2
	        , 0.00 AS usePower
	        , convert (VARCHAR (10), t.COLLECT_TIME, 120) AS COLLECT_DATE
	        , datepart (hour, DATEADD(MINUTE, -2, t.COLLECT_TIME)) AS COLLECT_HOUR
	        , t.COLLECT_TIME
	    FROM NH_ELECTRIC_METER_REALTIME t
	    WHERE t.COLLECT_TIME BETWEEN  @CurDate AND @dtEnd 
	    and t.total is not null
	

	UPDATE NH_ELECTRIC_METER_Temp  SET ReadData2 = dbo.Get_ELECTRIC_Last_ReadData (BIT_NO, COLLECT_TIME)
where COLLECT_TIME BETWEEN @dtBgn and @dtEnd
		

	UPDATE NH_ELECTRIC_METER_Temp
	    --SET INCREMENTAL = ReadData1 - ReadData2
		--SET INCREMENTAL = (case when (ReadData1 - ReadData2)>0 then (ReadData1 - ReadData2) else 0 end)
		SET INCREMENTAL = (case when isnull(ReadData1,0)=0 OR isnull(ReadData2,0) =0 OR (ReadData1 - ReadData2)<0 then 0 ELSE (ReadData1 - ReadData2)  end)
	where COLLECT_TIME BETWEEN @dtBgn and @dtEnd
	
	UPDATE NH_ELECTRIC_METER_Temp
	    SET DEVICE_ID = t.ID
	     FROM DEVICE t
	WHERE NH_ELECTRIC_METER_Temp.BIT_NO = t.EXTENDED1
and COLLECT_TIME BETWEEN @dtBgn and @dtEnd
	
	--设置总表	  
	UPDATE NH_ELECTRIC_METER_Temp
	    SET IS_TOTAL = 1 FROM DEVICE t
	WHERE NH_ELECTRIC_METER_Temp.BIT_NO = t.EXTENDED1	
  AND T.SUMMARIZED=1
and COLLECT_TIME BETWEEN @dtBgn and @dtEnd
	
	-----删除当日数据
	DELETE FROM NH_ELECTRIC_METER WHERE COLLECT_TIME BETWEEN  @dtBgn and @dtEnd
	-----删除当日数据(总表)
	DELETE FROM NH_ELECTRIC_METER_TOTAL WHERE COLLECT_TIME BETWEEN @dtBgn and @dtEnd
	---- 新增当日数据(排除总表)
	
	INSERT INTO dbo.NH_ELECTRIC_METER
		(
		BIT_NO,
		DEVICE_ID,
		TOTAL ,
		INCREMENTAL,
		COLLECT_DATE,
		COLLECT_HOUR,
		COLLECT_TIME
		)
		SELECT t.BIT_NO
	    , t.DEVICE_ID
	    , max (t.ReadData1) AS total
	    , sum (isnull(t.INCREMENTAL,0)) AS INCREMENTAL
	    , t.COLLECT_DATE
	    , t.COLLECT_HOUR
	    , max (t.COLLECT_TIME) AS COLLECT_TIME
	FROM NH_ELECTRIC_METER_Temp t
  WHERE T.IS_TOTAL=0
	and COLLECT_TIME BETWEEN @dtBgn and @dtEnd
	GROUP  BY t.BIT_NO, t.DEVICE_ID, t.COLLECT_DATE, t.COLLECT_HOUR

---- 新增当日数据(总表)
	
	INSERT INTO dbo.NH_ELECTRIC_METER_TOTAL
		(
		BIT_NO,
		DEVICE_ID,
		TOTAL ,
		INCREMENTAL,
		COLLECT_DATE,
		COLLECT_HOUR,
		COLLECT_TIME
		)
		SELECT t.BIT_NO
	    , t.DEVICE_ID
	    , max (t.ReadData1) AS total
	    , sum (isnull(t.INCREMENTAL,0)) AS INCREMENTAL
	    , t.COLLECT_DATE
	    , t.COLLECT_HOUR
	    , max (t.COLLECT_TIME) AS COLLECT_TIME
	FROM NH_ELECTRIC_METER_Temp t
  WHERE T.IS_TOTAL=1
	and COLLECT_TIME BETWEEN @dtBgn and @dtEnd
	GROUP  BY t.BIT_NO, t.DEVICE_ID, t.COLLECT_DATE, t.COLLECT_HOUR



GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ELECTRIC_MAIN]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_ELECTRIC_MAIN] AS

DECLARE @dtEnd DATETIME, @CurDate DATETIME

SET @CurDate = convert (VARCHAR (10), getdate (), 120)
SET @dtEnd = convert (VARCHAR (10), getdate (), 120)
WHILE (@CurDate <= @dtEnd)
BEGIN
    EXEC PROC_ARCHIVE_ELECTRIC_HOUR_DATA @CurDate
    EXEC PROC_ARCHIVE_ELECTRIC_DAY_DATA @CurDate
    EXEC PROC_ARCHIVE_ELECTRIC_MONTH_DATA @CurDate
    SET @CurDate = dateadd (day, 1, @CurDate)
END


--更新电表最后计数
UPDATE DEVICE SET DEVICE_VALUE = dbo.Get_ELECTRIC_Max_ReadData (ID)
	WHERE CATEGORY_ID = 93



GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ELECTRIC_MONTH_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_ELECTRIC_MONTH_DATA]
  @CurDate DATETIME  
AS
BEGIN

  DECLARE @CurMonthStr VARCHAR(7)

  set @CurMonthStr =   CONVERT (VARCHAR (7),@CurDate,121)

  -- 删除归档月份的数据
  delete from NH_ELECTRIC_METER_MONTH where MONTH_KEY = @CurMonthStr
  
  -- 归档相应月份的所有数据  
	INSERT INTO dbo.NH_ELECTRIC_METER_MONTH(        
        DEVICE_ID,
        MONTH_KEY,
        PEAK_VALUE,
        VALLY_VALUE,
        COMMON_VALUE,
        MONTH_DAYTIME_VALUE,
        MONTH_NIGHT_VALUE,
        MONTH_WEEKEN_VALUE,
        TOTAL_MONTH_VALUE,
        TOTAL_YOY
		)
		SELECT
			DEVICE_ID,
      CONVERT (VARCHAR (7),DAY_OF_MONTH_KEY,121) MONTH_KEY,
			SUM (PEAK_VALUE) PEAK_VALUE,
			SUM (VALLY_VALUE) VALLY_VALUE,
			SUM (COMMON_VALUE) COMMON_VALUE,
			SUM (DAY_DAYTIME_VALUE) MONTH_DAYTIME_VALUE,
			SUM (DAY_NIGHT_VALUE) MONTH_NIGHT_VALUE,
		  sum(case when DATEPART(weekday,DAY_OF_MONTH_KEY) in(1,7) then TOTAL_DAY_VALUE else 0 END) MONTH_WEEKEN_VALUE,
			SUM (TOTAL_DAY_VALUE) TOTAL_MONTH_VALUE,
		  0 TOTAL_YOY 
		FROM
			NH_ELECTRIC_METER_DAY
		WHERE
			CONVERT (VARCHAR (7),DAY_OF_MONTH_KEY,121) = @CurMonthStr
		GROUP BY
			CONVERT (VARCHAR (7),DAY_OF_MONTH_KEY,121),
			DEVICE_ID

     -- 修复TOTAL_YOY数据
     update b
			set b.total_yoy = a.TOTAL_MONTH_VALUE
			from NH_ELECTRIC_METER_MONTH a INNER JOIN NH_ELECTRIC_METER_MONTH b ON 
     a.DEVICE_ID = b.device_id and 
         a.MONTH_KEY = CONVERT (VARCHAR (7),dateadd(YEAR ,-1, b.MONTH_KEY + '-01'),121)
		where b.MONTH_KEY = @CurMonthStr

END


GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ENERGY_DAY_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_ENERGY_DAY_DATA]
  @CurDate DATETIME 
AS
BEGIN
--  DECLARE @peakFlag INT
 -- DECLARE @peakStart varchar(2)
 -- DECLARE @peakEnd varchar(2)
--
 -- DECLARE @vallyFlag INT
 -- DECLARE @vallyStart varchar(2)
 -- DECLARE @vallyEnd varchar(2)
  
  DECLARE @dayFlag INT
  DECLARE @dayStart varchar(2)
  DECLARE @dayEnd   varchar(2)

  DECLARE @nightFlag INT
  DECLARE @nightStart varchar(2)
  DECLARE @nightEnd varchar(2)

	DECLARE @sql VARCHAR(5000)
	DECLARE @dtEnd DATETIME 
	
	SET @dtEnd = dateadd(day,1,@CurDate)
	
  

  -- 归档前删除当天数据；
  delete from NH_ENERGY_METER_DAY where DAY_OF_MONTH_KEY = @CurDate 

  -- 计算波峰波谷白天晚上等条件
  select @dayStart=left(config_value,2),@dayEnd=SUBSTRING(config_value, 7, 2),@dayFlag=flag  from NH_CONFIG_MANAGE where code = 'BTWS_BT'
	select @nightStart=left(config_value,2),@nightEnd=SUBSTRING(config_value, 7, 2),@nightFlag=flag  from NH_CONFIG_MANAGE where code = 'BTWS_WS'
  

  -- 归档数据
  INSERT INTO dbo.NH_ENERGY_METER_DAY(
		DEVICE_ID,
		DAY_OF_MONTH_KEY,
--		PEAK_VALUE,
	--	VALLY_VALUE,
	--	COMMON_VALUE,
		DAY_DAYTIME_VALUE,
		DAY_NIGHT_VALUE,
		TOTAL_DAY_VALUE,
		TOTAL_YOY
		) 
SELECT a.DEVICE_ID
    , a.collect_Date DAY_OF_MONTH_KEY
   -- , 0 PEAK_VALUE
   -- , 0 VALLY_VALUE
   -- , 0 AS COMMON_VALUE
    , sum (CASE WHEN a.COLLECT_HOUR >= convert (INT, @dayStart) AND a.COLLECT_HOUR <= convert (INT, @dayEnd) THEN a.incremental ELSE 0 END) AS DAY_DAYTIME_VALUE
    , sum (CASE WHEN a.COLLECT_HOUR >= convert (INT, @nightStart) OR a.COLLECT_HOUR <= convert (INT, @nightEnd) THEN a.incremental ELSE 0 END) AS DAY_NIGHT_VALUE
    , sum (a.INCREMENTAL) AS TOTAL_DAY_VALUE
    , 0 AS TOTAL_YOY
FROM NH_ENERGY_METER a
WHERE a.COLLECT_TIME BETWEEN   @CurDate  AND @dtEnd
GROUP BY a.COLLECT_DATE, a.DEVICE_ID
ORDER BY a.collect_date

   -- 修复TOTAL_YOY数据
		update b
		set b.TOTAL_YOY = a.TOTAL_DAY_VALUE
		from NH_ENERGY_METER_DAY a INNER JOIN NH_ENERGY_METER_DAY b on a.DEVICE_ID=b.DEVICE_ID
		and a.DAY_OF_MONTH_KEY = dateadd(MONTH ,-1,b.DAY_OF_MONTH_KEY)
    where b.DAY_OF_MONTH_KEY = @CurDate
  -- routine body goes here, e.g.
  -- SELECT 'Navicat for SQL Server'
END

GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ENERGY_HOUR_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_ENERGY_HOUR_DATA]
  @CurDate DATETIME
AS
BEGIN
	TRUNCATE TABLE NH_ENERGY_METER_Temp

	DECLARE @dtEnd DATETIME   ,@dtBgn DATETIME	

	set @dtBgn = @CurDate
  SET @dtEnd = dateadd(day,1,@CurDate)
	set @CurDate=DATEADD(hour,-3, @CurDate)

	    INSERT INTO NH_ENERGY_METER_Temp (BIT_NO
	    , DEVICE_ID
	    , ReadData1		--当前抄表数据
	    , ReadData2		--上一次抄表数据
	    , INCREMENTAL
	    , COLLECT_DATE
	    , COLLECT_HOUR
	    , COLLECT_TIME)
	    SELECT t.BIT_NO
	        , 0
	        , t.TOTAL
	        , 0.00 AS Total2
	        , 0.00 AS usePower
	        , convert (VARCHAR (10), t.COLLECT_TIME, 120) AS COLLECT_DATE
	        , datepart (hour, DATEADD(MINUTE, -2, t.COLLECT_TIME)) AS COLLECT_HOUR
	        , t.COLLECT_TIME
	    FROM NH_ENERGY_METER_REALTIME t
	    WHERE t.COLLECT_TIME BETWEEN  @CurDate AND @dtEnd 
	    and t.total is not null
	
	UPDATE NH_ENERGY_METER_Temp  SET ReadData2 = dbo.Get_ENERGY_Last_ReadData (BIT_NO, COLLECT_TIME)
	where COLLECT_TIME BETWEEN @dtBgn and @dtEnd

	UPDATE NH_ENERGY_METER_Temp
	    --SET INCREMENTAL = ReadData1 - ReadData2
			--SET INCREMENTAL = (case when isnull(ReadData1,0)=0 OR isnull(ReadData2,0) =0 then 0 ELSE (ReadData1 - ReadData2)  end)
		SET INCREMENTAL = (case when isnull(ReadData1,0)=0 OR isnull(ReadData2,0) =0 OR (ReadData1 - ReadData2)<0 then 0 ELSE (ReadData1 - ReadData2)  end)	
	where COLLECT_TIME BETWEEN @dtBgn and @dtEnd

	UPDATE NH_ENERGY_METER_Temp
	    SET DEVICE_ID = t.ID FROM DEVICE t
	WHERE NH_ENERGY_METER_Temp.BIT_NO = t.EXTENDED1
	AND COLLECT_TIME BETWEEN @dtBgn and @dtEnd

	--设置总表	  
	UPDATE NH_ENERGY_METER_Temp
	    SET IS_TOTAL = 1 FROM DEVICE t
	WHERE NH_ENERGY_METER_Temp.BIT_NO = t.EXTENDED1	
  AND T.SUMMARIZED=1
	and  COLLECT_TIME BETWEEN @dtBgn and @dtEnd

	-----删除当日数据
	DELETE FROM NH_ENERGY_METER WHERE COLLECT_TIME BETWEEN  @dtBgn and @dtEnd
	-----删除当日数据(总表)
	DELETE FROM NH_ENERGY_METER_TOTAL WHERE COLLECT_TIME BETWEEN  @dtBgn and @dtEnd
	---- 新增当日数据(排除总表)
	
	INSERT INTO dbo.NH_ENERGY_METER
		(
		BIT_NO,
		DEVICE_ID,
		TOTAL ,
		INCREMENTAL,
		COLLECT_DATE,
		COLLECT_HOUR,
		COLLECT_TIME
		)
		SELECT t.BIT_NO
	    , t.DEVICE_ID
	    , max (t.ReadData1) AS total
	    , sum (isnull(t.INCREMENTAL,0)) AS INCREMENTAL
	    , t.COLLECT_DATE
	    , t.COLLECT_HOUR
	    , max (t.COLLECT_TIME) AS COLLECT_TIME
	FROM NH_ENERGY_METER_Temp t
  WHERE T.IS_TOTAL=0
and COLLECT_TIME BETWEEN  @dtBgn and @dtEnd
	GROUP  BY t.BIT_NO, t.DEVICE_ID, t.COLLECT_DATE, t.COLLECT_HOUR


---- 新增当日数据(总表)
	
	INSERT INTO dbo.NH_ENERGY_METER_TOTAL
		(
		BIT_NO,
		DEVICE_ID,
		TOTAL ,
		INCREMENTAL,
		COLLECT_DATE,
		COLLECT_HOUR,
		COLLECT_TIME
		)
		SELECT t.BIT_NO
	    , t.DEVICE_ID
	    , max (t.ReadData1) AS total
	    , sum (isnull(t.INCREMENTAL,0)) AS INCREMENTAL
	    , t.COLLECT_DATE
	    , t.COLLECT_HOUR
	    , max (t.COLLECT_TIME) AS COLLECT_TIME
	FROM NH_ENERGY_METER_Temp t
  WHERE T.IS_TOTAL=1
and COLLECT_TIME BETWEEN  @dtBgn and @dtEnd
	GROUP  BY t.BIT_NO, t.DEVICE_ID, t.COLLECT_DATE, t.COLLECT_HOUR
END

GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ENERGY_MAIN]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_ENERGY_MAIN] AS

DECLARE @dtEnd DATETIME, @CurDate DATETIME

SET @CurDate = convert (VARCHAR (10), getdate (), 120)
SET @dtEnd = convert (VARCHAR (10), getdate (), 120)

WHILE (@CurDate <= @dtEnd)
BEGIN
    EXEC PROC_ARCHIVE_ENERGY_HOUR_DATA @CurDate
    EXEC PROC_ARCHIVE_ENERGY_DAY_DATA @CurDate
    EXEC PROC_ARCHIVE_ENERGY_MONTH_DATA @CurDate
    SET @CurDate = dateadd (day, 1, @CurDate)
END
	--更新电表最后计数
	UPDATE DEVICE SET DEVICE_VALUE = dbo.Get_ENERGY_Max_ReadData (ID)
		WHERE CATEGORY_ID = 94









GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_ENERGY_MONTH_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_ENERGY_MONTH_DATA]
  @CurDate DATETIME  
AS
BEGIN 

  DECLARE @CurMonthStr VARCHAR(7)

  set @CurMonthStr =   CONVERT (VARCHAR (7),@CurDate,121)
 
  -- 删除归档月份的数据
  delete from NH_ENERGY_METER_MONTH where MONTH_KEY = @CurMonthStr
  
  -- 归档相应月份的所有数据  
	INSERT INTO dbo.NH_ENERGY_METER_MONTH(        
        DEVICE_ID,
        MONTH_KEY,
--        PEAK_VALUE,
--        VALLY_VALUE,
 --       COMMON_VALUE,
        MONTH_DAYTIME_VALUE,
        MONTH_NIGHT_VALUE,
        MONTH_WEEKEN_VALUE,
        TOTAL_MONTH_VALUE,
        TOTAL_YOY
		)
		SELECT
			DEVICE_ID,
      CONVERT (VARCHAR (7),DAY_OF_MONTH_KEY,121) MONTH_KEY,
	--		SUM (PEAK_VALUE) PEAK_VALUE,
	--		SUM (VALLY_VALUE) VALLY_VALUE,
	--		SUM (COMMON_VALUE) COMMON_VALUE,
			SUM (DAY_DAYTIME_VALUE) MONTH_DAYTIME_VALUE,
			SUM (DAY_NIGHT_VALUE) MONTH_NIGHT_VALUE,
		  sum(case when DATEPART(weekday,DAY_OF_MONTH_KEY) in(1,7) then TOTAL_DAY_VALUE else 0 END) MONTH_WEEKEN_VALUE,
			SUM (TOTAL_DAY_VALUE) TOTAL_MONTH_VALUE,
		  0 TOTAL_YOY 
		FROM
			NH_ENERGY_METER_DAY
		WHERE
			CONVERT (VARCHAR (7),DAY_OF_MONTH_KEY,121) = @CurMonthStr
		GROUP BY
			CONVERT (VARCHAR (7),DAY_OF_MONTH_KEY,121),
			DEVICE_ID

     -- 修复TOTAL_YOY数据
     update b
			set b.total_yoy = a.TOTAL_MONTH_VALUE
			from NH_ENERGY_METER_MONTH a INNER JOIN NH_ENERGY_METER_MONTH b 
      on a.DEVICE_ID = b.device_id and 
         a.MONTH_KEY = CONVERT (VARCHAR (7),dateadd(YEAR ,-1, b.MONTH_KEY + '-01'),121)
		where b.MONTH_KEY = @CurMonthStr
END

GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_GAS_DAY_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_GAS_DAY_DATA]
  @CurDate DATETIME
AS
BEGIN

  
  DECLARE @dayFlag INT
  DECLARE @dayStart varchar(2)
  DECLARE @dayEnd   varchar(2)

  DECLARE @nightFlag INT
  DECLARE @nightStart varchar(2)
  DECLARE @nightEnd varchar(2)

	DECLARE @sql VARCHAR(5000)
	DECLARE @dtEnd DATETIME
	
	SET @dtEnd = dateadd(day,1,@CurDate)
  

  -- 归档前删除当天数据；
  delete from NH_GAS_METER_DAY where DAY_OF_MONTH_KEY = @CurDate 

  -- 计算波峰波谷白天晚上等条件
--  select @peakStart=left(config_value,2),@peakEnd=SUBSTRING(config_value, 7, 2),@vallyFlag=flag  from NH_CONFIG_MANAGE where code = 'BFBG_BF'
	--select @vallyStart=left(config_value,2),@vallyEnd=SUBSTRING(config_value, 7, 2),@peakFlag=flag  from NH_CONFIG_MANAGE where code = 'BFBG_BG'
	select @dayStart=left(config_value,2),@dayEnd=SUBSTRING(config_value, 7, 2),@dayFlag=flag  from NH_CONFIG_MANAGE where code = 'BTWS_BT'
	select @nightStart=left(config_value,2),@nightEnd=SUBSTRING(config_value, 7, 2),@nightFlag=flag  from NH_CONFIG_MANAGE where code = 'BTWS_WS'
  
 
  -- 归档数据
  INSERT INTO dbo.NH_GAS_METER_DAY(
		DEVICE_ID,
		DAY_OF_MONTH_KEY,
--		PEAK_VALUE,
--		VALLY_VALUE,
	--	COMMON_VALUE,
		DAY_DAYTIME_VALUE,
		DAY_NIGHT_VALUE,
		TOTAL_DAY_VALUE,
		TOTAL_YOY
		)
SELECT a.DEVICE_ID
    , a.collect_Date DAY_OF_MONTH_KEY
   -- , 0 PEAK_VALUE
   -- , 0 VALLY_VALUE
   -- , 0 AS COMMON_VALUE
    , sum (CASE WHEN a.COLLECT_HOUR >= convert (INT, @dayStart) AND a.COLLECT_HOUR <= convert (INT, @dayEnd) THEN a.incremental ELSE 0 END) AS DAY_DAYTIME_VALUE
    , sum (CASE WHEN a.COLLECT_HOUR >= convert (INT, @nightStart) OR a.COLLECT_HOUR <= convert (INT, @nightEnd) THEN a.incremental ELSE 0 END) AS DAY_NIGHT_VALUE
    , sum (a.INCREMENTAL) AS TOTAL_DAY_VALUE
    , 0 AS TOTAL_YOY
FROM NH_GAS_METER a
WHERE a.COLLECT_TIME BETWEEN  @CurDate AND @dtEnd
GROUP BY a.COLLECT_DATE, a.DEVICE_ID


   -- 修复TOTAL_YOY数据
		update b
		set b.TOTAL_YOY = a.TOTAL_DAY_VALUE
		from NH_GAS_METER_DAY a INNER JOIN NH_GAS_METER_DAY b on a.DEVICE_ID=b.DEVICE_ID
		and a.DAY_OF_MONTH_KEY = dateadd(MONTH ,-1,b.DAY_OF_MONTH_KEY)
    where b.DAY_OF_MONTH_KEY = @CurDate
END

GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_GAS_HOUR_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_GAS_HOUR_DATA]
  @CurDate DATETIME 
AS
BEGIN
	TRUNCATE TABLE NH_GAS_METER_Temp

	DECLARE @dtEnd DATETIME   ,@dtBgn DATETIME

	set @dtBgn = @CurDate
  SET @dtEnd = dateadd(day,1,@CurDate)
	set @CurDate=DATEADD(hour,-3, @CurDate)

	    INSERT INTO NH_GAS_METER_Temp (BIT_NO
	    , DEVICE_ID
	    , ReadData1
	    , ReadData2
	    , INCREMENTAL
	    , COLLECT_DATE
	    , COLLECT_HOUR
	    , COLLECT_TIME)
	    SELECT t.BIT_NO
	        , 0
	        , t.TOTAL
	        , 0.00 AS Total2
	        , 0.00 AS usePower
	        , convert (VARCHAR (10), t.COLLECT_TIME, 120) AS COLLECT_DATE
	        , datepart (hour, DATEADD(MINUTE, -2, t.COLLECT_TIME)) AS COLLECT_HOUR
	        , t.COLLECT_TIME
	    FROM NH_GAS_METER_REALTIME t
	    WHERE t.COLLECT_TIME BETWEEN @CurDate and @dtEnd 
			and t.total is not null
	
	UPDATE NH_GAS_METER_Temp  SET ReadData2 = dbo.Get_GAS_Last_ReadData (BIT_NO, COLLECT_TIME)
	where COLLECT_TIME BETWEEN @dtBgn and @dtEnd

	UPDATE NH_GAS_METER_Temp
	    --SET INCREMENTAL = ReadData1 - ReadData2
		--SET INCREMENTAL = (case when (ReadData1 - ReadData2)>0 then (ReadData1 - ReadData2) else 0 end)
		--SET INCREMENTAL = (case when isnull(ReadData1,0)=0 OR isnull(ReadData2,0) =0 then 0 ELSE (ReadData1 - ReadData2)  end)
		SET INCREMENTAL = (case when isnull(ReadData1,0)=0 OR isnull(ReadData2,0) =0 OR (ReadData1 - ReadData2)<0 then 0 ELSE (ReadData1 - ReadData2)  end)
	where COLLECT_TIME BETWEEN @dtBgn and @dtEnd

	UPDATE NH_GAS_METER_Temp
	    SET DEVICE_ID = t.ID FROM DEVICE t
	WHERE NH_GAS_METER_Temp.BIT_NO = t.EXTENDED1
and COLLECT_TIME BETWEEN @dtBgn and @dtEnd

	--设置总表	  
	UPDATE NH_GAS_METER_Temp
	    SET IS_TOTAL = 1 FROM DEVICE t
	WHERE NH_GAS_METER_Temp.BIT_NO = t.EXTENDED1	
  AND T.SUMMARIZED=1	
and COLLECT_TIME BETWEEN @dtBgn and @dtEnd

	-----删除当日数据
	DELETE FROM NH_GAS_METER WHERE COLLECT_TIME BETWEEN @dtBgn and @dtEnd
	
	-----删除当日数据(总表)
	DELETE FROM NH_GAS_METER_TOTAL WHERE COLLECT_TIME BETWEEN @dtBgn and @dtEnd
	---- 新增当日数据(排除总表)
	
	INSERT INTO dbo.NH_GAS_METER
		(
		BIT_NO,
		DEVICE_ID,
		TOTAL ,
		INCREMENTAL,
		COLLECT_DATE,
		COLLECT_HOUR,
		COLLECT_TIME
		)
		SELECT t.BIT_NO
	    , t.DEVICE_ID
	    , max (t.ReadData1) AS total
	    , sum (isnull(t.INCREMENTAL,0)) AS INCREMENTAL
	    , t.COLLECT_DATE
	    , t.COLLECT_HOUR
	    , max (t.COLLECT_TIME) AS COLLECT_TIME
	FROM NH_GAS_METER_Temp t
  WHERE T.IS_TOTAL=0
and COLLECT_TIME BETWEEN @dtBgn and @dtEnd
	GROUP  BY t.BIT_NO, t.DEVICE_ID, t.COLLECT_DATE, t.COLLECT_HOUR

---- 新增当日数据(总表)
	
	INSERT INTO dbo.NH_GAS_METER_TOTAL
		(
		BIT_NO,
		DEVICE_ID,
		TOTAL ,
		INCREMENTAL,
		COLLECT_DATE,
		COLLECT_HOUR,
		COLLECT_TIME
		)
		SELECT t.BIT_NO
	    , t.DEVICE_ID
	    , max (t.ReadData1) AS total
	    , sum (isnull(t.INCREMENTAL,0)) AS INCREMENTAL
	    , t.COLLECT_DATE
	    , t.COLLECT_HOUR
	    , max (t.COLLECT_TIME) AS COLLECT_TIME
	FROM NH_GAS_METER_Temp t
  WHERE T.IS_TOTAL=1
and COLLECT_TIME BETWEEN @dtBgn and @dtEnd
	GROUP  BY t.BIT_NO, t.DEVICE_ID, t.COLLECT_DATE, t.COLLECT_HOUR
END


GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_GAS_MAIN]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_GAS_MAIN] AS

DECLARE @dtEnd DATETIME, @CurDate DATETIME

SET @CurDate = convert (VARCHAR (10), getdate (), 120)
SET @dtEnd = convert (VARCHAR (10), getdate (), 120)

WHILE (@CurDate <= @dtEnd)
BEGIN
    EXEC PROC_ARCHIVE_GAS_HOUR_DATA @CurDate
    EXEC PROC_ARCHIVE_GAS_DAY_DATA @CurDate
    EXEC PROC_ARCHIVE_GAS_MONTH_DATA @CurDate
    SET @CurDate = dateadd (day, 1, @CurDate)
END

--更新电表最后计数
UPDATE DEVICE SET DEVICE_VALUE = dbo.Get_GAS_Max_ReadData (ID)
	WHERE CATEGORY_ID = 108











GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_GAS_MONTH_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_GAS_MONTH_DATA]
  @CurDate DATETIME  
AS
BEGIN
 

  DECLARE @CurMonthStr VARCHAR(7)

  set @CurMonthStr =   CONVERT (VARCHAR (7),@CurDate,121)
  
  -- 删除归档月份的数据
  delete from NH_GAS_METER_MONTH where MONTH_KEY = @CurMonthStr
  
  -- 归档相应月份的所有数据  
	INSERT INTO dbo.NH_GAS_METER_MONTH(        
        DEVICE_ID,
        MONTH_KEY,
  --      PEAK_VALUE,
  --      VALLY_VALUE,
  --      COMMON_VALUE,
        MONTH_DAYTIME_VALUE,
        MONTH_NIGHT_VALUE,
        MONTH_WEEKEN_VALUE,
        TOTAL_MONTH_VALUE,
        TOTAL_YOY
		)
		SELECT
			DEVICE_ID,
      CONVERT (VARCHAR (7),DAY_OF_MONTH_KEY,121) MONTH_KEY,
	--		SUM (PEAK_VALUE) PEAK_VALUE,
	--		SUM (VALLY_VALUE) VALLY_VALUE,
	--		SUM (COMMON_VALUE) COMMON_VALUE,
			SUM (DAY_DAYTIME_VALUE) MONTH_DAYTIME_VALUE,
			SUM (DAY_NIGHT_VALUE) MONTH_NIGHT_VALUE,
		  sum(case when DATEPART(weekday,DAY_OF_MONTH_KEY) in(1,7) then TOTAL_DAY_VALUE else 0 END) MONTH_WEEKEN_VALUE,
			SUM (TOTAL_DAY_VALUE) TOTAL_MONTH_VALUE,
		  0 TOTAL_YOY 
		FROM NH_GAS_METER_DAY
		WHERE CONVERT(VARCHAR (7),DAY_OF_MONTH_KEY,121	) = @CurMonthStr
		GROUP BY
			CONVERT(VARCHAR (7),DAY_OF_MONTH_KEY,121),
			DEVICE_ID

     -- 修复TOTAL_YOY数据
     update b
			set b.total_yoy = a.TOTAL_MONTH_VALUE
			from NH_GAS_METER_MONTH a INNER JOIN NH_GAS_METER_MONTH b 
      on a.DEVICE_ID = b.device_id and 
         a.MONTH_KEY = CONVERT (VARCHAR (7),dateadd(YEAR ,-1, b.MONTH_KEY + '-01'),121)
		where b.MONTH_KEY = @CurMonthStr

END

GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_WATER_DAY_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_WATER_DAY_DATA]
  @CurDate DATETIME 
AS
BEGIN
--  DECLARE @peakFlag INT
---  DECLARE @peakStart varchar(2)
--  DECLARE @peakEnd varchar(2)

--  DECLARE @vallyFlag INT
--  DECLARE @vallyStart varchar(2)
 -- DECLARE @vallyEnd varchar(2)
  
  DECLARE @dayFlag INT
  DECLARE @dayStart varchar(2)
  DECLARE @dayEnd   varchar(2)

  DECLARE @nightFlag INT
  DECLARE @nightStart varchar(2)
  DECLARE @nightEnd varchar(2)

	DECLARE @sql VARCHAR(5000)
	DECLARE @dtEnd DATETIME 
	
	SET @dtEnd = dateadd(day,1,@CurDate)
 

  -- 归档前删除当天数据；
  delete from NH_WATER_METER_DAY where DAY_OF_MONTH_KEY = @CurDate 

  -- 计算波峰波谷白天晚上等条件
 -- select @peakStart=left(config_value,2),@peakEnd=SUBSTRING(config_value, 7, 2),@vallyFlag=flag  from NH_CONFIG_MANAGE where code = 'BFBG_BF'
--	select @vallyStart=left(config_value,2),@vallyEnd=SUBSTRING(config_value, 7, 2),@peakFlag=flag  from NH_CONFIG_MANAGE where code = 'BFBG_BG'
	select @dayStart=left(config_value,2),@dayEnd=SUBSTRING(config_value, 7, 2),@dayFlag=flag  from NH_CONFIG_MANAGE where code = 'BTWS_BT'
	select @nightStart=left(config_value,2),@nightEnd=SUBSTRING(config_value, 7, 2),@nightFlag=flag  from NH_CONFIG_MANAGE where code = 'BTWS_WS'
  
 

  -- 归档数据
  INSERT INTO dbo.NH_WATER_METER_DAY(
		DEVICE_ID,
		DAY_OF_MONTH_KEY,
	--	PEAK_VALUE,
	--	VALLY_VALUE,
	--	COMMON_VALUE,
		DAY_DAYTIME_VALUE,
		DAY_NIGHT_VALUE,
		TOTAL_DAY_VALUE,
		TOTAL_YOY
		) 
SELECT a.DEVICE_ID
    , a.collect_Date DAY_OF_MONTH_KEY
   -- , 0 PEAK_VALUE
   -- , 0 VALLY_VALUE
   -- , 0 AS COMMON_VALUE
    , sum (CASE WHEN a.COLLECT_HOUR >= convert (INT, @dayStart) AND a.COLLECT_HOUR <= convert (INT, @dayEnd) THEN a.incremental ELSE 0 END) AS DAY_DAYTIME_VALUE
    , sum (CASE WHEN a.COLLECT_HOUR >= convert (INT, @nightStart) OR a.COLLECT_HOUR <= convert (INT, @nightEnd) THEN a.incremental ELSE 0 END) AS DAY_NIGHT_VALUE
    , sum (a.INCREMENTAL) AS TOTAL_DAY_VALUE
    , 0 AS TOTAL_YOY
FROM NH_WATER_METER a
WHERE a.COLLECT_TIME BETWEEN  @CurDate  AND @dtEnd 
GROUP BY a.COLLECT_DATE, a.DEVICE_ID
ORDER BY a.collect_date

   -- 修复TOTAL_YOY数据
		update b
		set b.TOTAL_YOY = a.TOTAL_DAY_VALUE
		from NH_WATER_METER_DAY a INNER JOIN NH_WATER_METER_DAY b on a.DEVICE_ID=b.DEVICE_ID
		and a.DAY_OF_MONTH_KEY = dateadd(MONTH ,-1,b.DAY_OF_MONTH_KEY)
    where b.DAY_OF_MONTH_KEY = @CurDate

END

GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_WATER_HOUR_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_WATER_HOUR_DATA]
	@CurDate DATETIME
AS
BEGIN

	
	TRUNCATE TABLE NH_WATER_METER_Temp

DECLARE @dtEnd DATETIME   ,@dtBgn DATETIME

	set @dtBgn = @CurDate
  SET @dtEnd = dateadd(day,1,@CurDate)
	set @CurDate=DATEADD(hour,-3, @CurDate)


	    INSERT INTO NH_WATER_METER_Temp (BIT_NO
	    , DEVICE_ID
	    , ReadData1
	    , ReadData2
	    , INCREMENTAL
	    , COLLECT_DATE
	    , COLLECT_HOUR
	    , COLLECT_TIME)
	    SELECT t.BIT_NO
	        , 0
	        , t.TOTAL
	        , 0.00 AS Total2
	        , 0.00 AS usePower
	        , convert (VARCHAR (10), t.COLLECT_TIME, 120) AS COLLECT_DATE
	        , datepart (hour, DATEADD(MINUTE, -2, t.COLLECT_TIME)) AS COLLECT_HOUR
	        , t.COLLECT_TIME
	    FROM NH_WATER_METER_REALTIME t
	    WHERE t.COLLECT_TIME BETWEEN @CurDate and @dtEnd 
      and t.total is not null;
	
	UPDATE NH_WATER_METER_Temp  SET ReadData2 = dbo.Get_WATER_Last_ReadData (BIT_NO, COLLECT_TIME)
	where COLLECT_TIME BETWEEN @dtBgn and @dtEnd

	UPDATE NH_WATER_METER_Temp
	    --SET INCREMENTAL = ReadData1 - ReadData2
		--SET INCREMENTAL = (case when (ReadData1 - ReadData2)>0 then (ReadData1 - ReadData2) else 0 end)
		--SET INCREMENTAL = (case when isnull(ReadData1,0)=0 OR isnull(ReadData2,0) =0 then 0 ELSE (ReadData1 - ReadData2)  end)
		SET INCREMENTAL = (case when isnull(ReadData1,0)=0 OR isnull(ReadData2,0) =0 OR (ReadData1 - ReadData2)<0 then 0 ELSE (ReadData1 - ReadData2)  end)
where COLLECT_TIME BETWEEN @dtBgn and @dtEnd
	
	UPDATE NH_WATER_METER_Temp
	    SET DEVICE_ID = t.ID FROM DEVICE t
	WHERE NH_WATER_METER_Temp.BIT_NO = t.EXTENDED1
and  COLLECT_TIME BETWEEN @dtBgn and @dtEnd
	
	--设置总表	  
	UPDATE NH_WATER_METER_Temp
	    SET IS_TOTAL = 1 FROM DEVICE t
	WHERE NH_WATER_METER_Temp.BIT_NO = t.EXTENDED1	
  AND T.SUMMARIZED=1
and  COLLECT_TIME BETWEEN @dtBgn and @dtEnd

	-----删除当日数据
	DELETE FROM NH_WATER_METER WHERE COLLECT_TIME BETWEEN  @dtBgn and @dtEnd
	-----删除当日数据(总表)
	DELETE FROM NH_WATER_METER_TOTAL WHERE COLLECT_TIME BETWEEN  @dtBgn and @dtEnd
	---- 新增当日数据(排除总表)
	
	INSERT INTO dbo.NH_WATER_METER
		(
		BIT_NO,
		DEVICE_ID,
		TOTAL ,
		INCREMENTAL,
		COLLECT_DATE,
		COLLECT_HOUR,
		COLLECT_TIME
		)
		SELECT t.BIT_NO
	    , t.DEVICE_ID
	    , max (t.ReadData1) AS total
	    , sum (isnull(t.INCREMENTAL,0)) AS INCREMENTAL
	    , t.COLLECT_DATE
	    , t.COLLECT_HOUR
	    , max (t.COLLECT_TIME) AS COLLECT_TIME
	FROM NH_WATER_METER_Temp t
  WHERE T.IS_TOTAL=0
and COLLECT_TIME BETWEEN  @dtBgn and @dtEnd
	GROUP  BY t.BIT_NO, t.DEVICE_ID, t.COLLECT_DATE, t.COLLECT_HOUR
---- 新增当日数据(总表)
	
	INSERT INTO dbo.NH_WATER_METER_TOTAL
		(
		BIT_NO,
		DEVICE_ID,
		TOTAL ,
		INCREMENTAL,
		COLLECT_DATE,
		COLLECT_HOUR,
		COLLECT_TIME
		)
		SELECT t.BIT_NO
	    , t.DEVICE_ID
	    , max (t.ReadData1) AS total
	    , sum (isnull(t.INCREMENTAL,0)) AS INCREMENTAL
	    , t.COLLECT_DATE
	    , t.COLLECT_HOUR
	    , max (t.COLLECT_TIME) AS COLLECT_TIME
	FROM NH_WATER_METER_Temp t
  WHERE T.IS_TOTAL=1
and COLLECT_TIME BETWEEN  @dtBgn and @dtEnd
	GROUP  BY t.BIT_NO, t.DEVICE_ID, t.COLLECT_DATE, t.COLLECT_HOUR
END


GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_WATER_MAIN]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_WATER_MAIN] AS

DECLARE @dtEnd DATETIME, @CurDate DATETIME

SET @CurDate = convert (VARCHAR (10), getdate (), 120)
SET @dtEnd = convert (VARCHAR (10), getdate (), 120)

WHILE (@CurDate <= @dtEnd)
BEGIN
    EXEC PROC_ARCHIVE_WATER_HOUR_DATA @CurDate
    EXEC PROC_ARCHIVE_WATER_DAY_DATA @CurDate
    EXEC PROC_ARCHIVE_WATER_MONTH_DATA @CurDate
    SET @CurDate = dateadd (day, 1, @CurDate)
END

--更新电表最后计数
UPDATE DEVICE SET DEVICE_VALUE = dbo.Get_WATER_Max_ReadData (ID)
	WHERE CATEGORY_ID = 92






GO

/****** Object:  StoredProcedure [dbo].[PROC_ARCHIVE_WATER_MONTH_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_ARCHIVE_WATER_MONTH_DATA]
 @CurDate DATETIME  
AS
BEGIN
  --处理@CurDate 为获取当前年份和当前月份
  --DECLARE @CurYear INT
  --DECLARE @CurMonth INT

  DECLARE @CurMonthStr VARCHAR(7)

  --set @CurMonth = MONTH(@CurDate)
  --set @CurYear = YEAR(@CurDate)

  set @CurMonthStr =   CONVERT (
				VARCHAR (7),
				@CurDate,
				121
			)
  --print @CurMonthStr

  --select * from NH_ELECTRIC_METER_MONTH where LEFT(MONTH_KEY,4) >= @CurYear and RIGHT(MONTH_KEY,2) >= @CurMonth
  
  --select LEFT(MONTH_KEY,4),RIGHT(MONTH_KEY,2) from NH_ELECTRIC_METER_MONTH
  -- 删除归档月份之后的数据
  delete from NH_WATER_METER_MONTH where MONTH_KEY = @CurMonthStr
  
  -- 归档相应月份之后的所有数据
  
	INSERT INTO dbo.NH_WATER_METER_MONTH(        
        DEVICE_ID,
        MONTH_KEY,
 --       PEAK_VALUE,
 --       VALLY_VALUE,
 --       COMMON_VALUE,
        MONTH_DAYTIME_VALUE,
        MONTH_NIGHT_VALUE,
        MONTH_WEEKEN_VALUE,
        TOTAL_MONTH_VALUE,
        TOTAL_YOY
		)
		SELECT
			DEVICE_ID,
      CONVERT (VARCHAR (7),DAY_OF_MONTH_KEY,121) MONTH_KEY,
	--		SUM (PEAK_VALUE) PEAK_VALUE,
	--		SUM (VALLY_VALUE) VALLY_VALUE,
	--		SUM (COMMON_VALUE) COMMON_VALUE,
			SUM (DAY_DAYTIME_VALUE) MONTH_DAYTIME_VALUE,
			SUM (DAY_NIGHT_VALUE) MONTH_NIGHT_VALUE,
		  sum(case when DATEPART(weekday,DAY_OF_MONTH_KEY) in(1,7) then TOTAL_DAY_VALUE else 0 END) MONTH_WEEKEN_VALUE,
			SUM (TOTAL_DAY_VALUE) TOTAL_MONTH_VALUE,
		  0 TOTAL_YOY 
		FROM
			NH_WATER_METER_DAY
		WHERE
			CONVERT (
				VARCHAR (7),
				DAY_OF_MONTH_KEY,
				121
			) = @CurMonthStr
		GROUP BY
			CONVERT (
				VARCHAR (7),
				DAY_OF_MONTH_KEY,
				121
			),
			DEVICE_ID

     -- 修复TOTAL_YOY数据
     update b
			set b.total_yoy = a.TOTAL_MONTH_VALUE
			from NH_WATER_METER_MONTH a INNER JOIN NH_WATER_METER_MONTH b 
      on a.DEVICE_ID = b.device_id and 
         a.MONTH_KEY = CONVERT (VARCHAR (7),dateadd(YEAR ,-1, b.MONTH_KEY + '-01'),121)
		where b.MONTH_KEY = @CurMonthStr

END

GO

/****** Object:  StoredProcedure [dbo].[PROC_FIX_ELECTRIC_ARCHIVE_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_FIX_ELECTRIC_ARCHIVE_DATA] 
	@CurDate DATETIME
	
	AS
	
	TRUNCATE TABLE NH_ELECTRIC_METER_Temp 

   
set @CurDate=DATEADD(hour,-3, @CurDate)
	
--排除掉总表	    
INSERT INTO NH_ELECTRIC_METER_Temp (BIT_NO
	    , DEVICE_ID
	    , ReadData1
	    , ReadData2
	    , INCREMENTAL
	    , COLLECT_DATE
	    , COLLECT_HOUR
	    , COLLECT_TIME)
	    SELECT t.BIT_NO
	        , 0
	        , t.TOTAL
	        , 0.00 AS Total2
	        , 0.00 AS usePower
	        , convert (VARCHAR (10), t.COLLECT_TIME, 120) AS COLLECT_DATE
	        , datepart (hour, DATEADD(MINUTE, -2, t.COLLECT_TIME)) AS COLLECT_HOUR
	        , t.COLLECT_TIME
	    FROM NH_ELECTRIC_METER_REALTIME t
	    WHERE t.COLLECT_TIME >= @CurDate and t.total is not null
      AND NOT EXISTS(select 1 from DEVICE t1 where t1.spring_el is not NULL and t1.hpid=t.bit_no)
	
	UPDATE NH_ELECTRIC_METER_Temp  SET ReadData2 = dbo.Get_ELECTRIC_Last_ReadData (BIT_NO, COLLECT_TIME)
	
	UPDATE NH_ELECTRIC_METER_Temp
	    --SET INCREMENTAL = ReadData1 - ReadData2
				SET INCREMENTAL = (case when (ReadData1 - ReadData2)>0 then (ReadData1 - ReadData2) else 0 end)
	
	
	UPDATE NH_ELECTRIC_METER_Temp
	    SET DEVICE_ID = t.ID FROM DEVICE t
	WHERE NH_ELECTRIC_METER_Temp.BIT_NO = t.EXTENDED1
	
	-----删除当日数据
	DELETE FROM NH_ELECTRIC_METER WHERE COLLECT_TIME >= @CurDate	
	---- 新增当日数据
	
	INSERT INTO dbo.NH_ELECTRIC_METER
		(
		BIT_NO,
		DEVICE_ID,
		TOTAL ,
		INCREMENTAL,
		COLLECT_DATE,
		COLLECT_HOUR,
		COLLECT_TIME
		)
		SELECT t.BIT_NO
	    , t.DEVICE_ID
	    , max (t.ReadData1) AS total
	    , sum (isnull(t.INCREMENTAL,0)) AS INCREMENTAL
	    , t.COLLECT_DATE
	    , t.COLLECT_HOUR
	    , max (t.COLLECT_TIME) AS COLLECT_TIME
	FROM NH_ELECTRIC_METER_Temp t
	GROUP  BY t.BIT_NO, t.DEVICE_ID, t.COLLECT_DATE, t.COLLECT_HOUR
GO

/****** Object:  StoredProcedure [dbo].[PROC_FIX_ENERGY_ARCHIVE_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_FIX_ENERGY_ARCHIVE_DATA] 
	@CurDate DATETIME
	
	AS
	
	TRUNCATE TABLE NH_ENERGY_METER_Temp

	set @CurDate=DATEADD(hour,-3, @CurDate)

	    INSERT INTO NH_ENERGY_METER_Temp (BIT_NO
	    , DEVICE_ID
	    , ReadData1		--当前抄表数据
	    , ReadData2		--上一次抄表数据
	    , INCREMENTAL
	    , COLLECT_DATE
	    , COLLECT_HOUR
	    , COLLECT_TIME)
	    SELECT t.BIT_NO
	        , 0
	        , t.TOTAL
	        , 0.00 AS Total2
	        , 0.00 AS usePower
	        , convert (VARCHAR (10), t.COLLECT_TIME, 120) AS COLLECT_DATE
	        , datepart (hour, DATEADD(MINUTE, -2, t.COLLECT_TIME)) AS COLLECT_HOUR
	        , t.COLLECT_TIME
	    FROM NH_ENERGY_METER_REALTIME t
	    WHERE t.COLLECT_TIME >= @CurDate
	
	UPDATE NH_ENERGY_METER_Temp  SET ReadData2 = dbo.Get_ENERGY_Last_ReadData (BIT_NO, COLLECT_TIME)
	
	UPDATE NH_ENERGY_METER_Temp
	    --SET INCREMENTAL = ReadData1 - ReadData2
				SET INCREMENTAL = (case when isnull(ReadData1,0)=0 OR isnull(ReadData2,0) =0 then 0 ELSE (ReadData1 - ReadData2)  end)
	
	
	UPDATE NH_ENERGY_METER_Temp
	    SET DEVICE_ID = t.ID FROM DEVICE t
	WHERE NH_ENERGY_METER_Temp.BIT_NO = t.EXTENDED1
	
	-----删除当日数据
	DELETE FROM NH_ENERGY_METER WHERE COLLECT_TIME >= @CurDate	
	---- 新增当日数据
	
	INSERT INTO dbo.NH_ENERGY_METER
		(
		BIT_NO,
		DEVICE_ID,
		TOTAL ,
		INCREMENTAL,
		COLLECT_DATE,
		COLLECT_HOUR,
		COLLECT_TIME
		)
		SELECT t.BIT_NO
	    , t.DEVICE_ID
	    , max (t.ReadData1) AS total
	    , sum (isnull(t.INCREMENTAL,0)) AS INCREMENTAL
	    , t.COLLECT_DATE
	    , t.COLLECT_HOUR
	    , max (t.COLLECT_TIME) AS COLLECT_TIME
	FROM NH_ENERGY_METER_Temp t
	GROUP  BY t.BIT_NO, t.DEVICE_ID, t.COLLECT_DATE, t.COLLECT_HOUR






GO

/****** Object:  StoredProcedure [dbo].[PROC_FIX_GAS_ARCHIVE_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_FIX_GAS_ARCHIVE_DATA] 
	@CurDate DATETIME
	
	AS
	
	TRUNCATE TABLE NH_GAS_METER_Temp

	set @CurDate=DATEADD(hour,-3, @CurDate)

	    INSERT INTO NH_GAS_METER_Temp (BIT_NO
	    , DEVICE_ID
	    , ReadData1
	    , ReadData2
	    , INCREMENTAL
	    , COLLECT_DATE
	    , COLLECT_HOUR
	    , COLLECT_TIME)
	    SELECT t.BIT_NO
	        , 0
	        , t.TOTAL
	        , 0.00 AS Total2
	        , 0.00 AS usePower
	        , convert (VARCHAR (10), t.COLLECT_TIME, 120) AS COLLECT_DATE
	        , datepart (hour, DATEADD(MINUTE, -2, t.COLLECT_TIME)) AS COLLECT_HOUR
	        , t.COLLECT_TIME
	    FROM NH_GAS_METER_REALTIME t
	    WHERE t.COLLECT_TIME >= @CurDate
	
	UPDATE NH_GAS_METER_Temp  SET ReadData2 = dbo.Get_GAS_Last_ReadData (BIT_NO, COLLECT_TIME)
	
	UPDATE NH_GAS_METER_Temp
	    --SET INCREMENTAL = ReadData1 - ReadData2
				SET INCREMENTAL = (case when (ReadData1 - ReadData2)>0 then (ReadData1 - ReadData2) else 0 end)
	
	
	UPDATE NH_GAS_METER_Temp
	    SET DEVICE_ID = t.ID FROM DEVICE t
	WHERE NH_GAS_METER_Temp.BIT_NO = t.EXTENDED1
	
	-----删除当日数据
	DELETE FROM NH_GAS_METER WHERE COLLECT_TIME >= @CurDate	
	---- 新增当日数据
	
	INSERT INTO dbo.NH_GAS_METER
		(
		BIT_NO,
		DEVICE_ID,
		TOTAL ,
		INCREMENTAL,
		COLLECT_DATE,
		COLLECT_HOUR,
		COLLECT_TIME
		)
		SELECT t.BIT_NO
	    , t.DEVICE_ID
	    , max (t.ReadData1) AS total
	    , sum (isnull(t.INCREMENTAL,0)) AS INCREMENTAL
	    , t.COLLECT_DATE
	    , t.COLLECT_HOUR
	    , max (t.COLLECT_TIME) AS COLLECT_TIME
	FROM NH_GAS_METER_Temp t
	GROUP  BY t.BIT_NO, t.DEVICE_ID, t.COLLECT_DATE, t.COLLECT_HOUR




GO

/****** Object:  StoredProcedure [dbo].[PROC_FIX_WATER_ARCHIVE_DATA]    Script Date: 08/21/2015 10:42:13 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[PROC_FIX_WATER_ARCHIVE_DATA] 
	@CurDate DATETIME
	
	AS
	
	TRUNCATE TABLE NH_WATER_METER_Temp

	set @CurDate=DATEADD(hour,-3, @CurDate)

	    INSERT INTO NH_WATER_METER_Temp (BIT_NO
	    , DEVICE_ID
	    , ReadData1
	    , ReadData2
	    , INCREMENTAL
	    , COLLECT_DATE
	    , COLLECT_HOUR
	    , COLLECT_TIME)
	    SELECT t.BIT_NO
	        , 0
	        , t.TOTAL
	        , 0.00 AS Total2
	        , 0.00 AS usePower
	        , convert (VARCHAR (10), t.COLLECT_TIME, 120) AS COLLECT_DATE
	        , datepart (hour, DATEADD(MINUTE, -2, t.COLLECT_TIME)) AS COLLECT_HOUR
	        , t.COLLECT_TIME
	    FROM NH_WATER_METER_REALTIME t
	    WHERE t.COLLECT_TIME >= @CurDate
	
	UPDATE NH_WATER_METER_Temp  SET ReadData2 = dbo.Get_WATER_Last_ReadData (BIT_NO, COLLECT_TIME)
	
	UPDATE NH_WATER_METER_Temp
	    --SET INCREMENTAL = ReadData1 - ReadData2
			SET INCREMENTAL = (case when (ReadData1 - ReadData2)>0 then (ReadData1 - ReadData2) else 0 end)
	
	
	UPDATE NH_WATER_METER_Temp
	    SET DEVICE_ID = t.ID FROM DEVICE t
	WHERE NH_WATER_METER_Temp.BIT_NO = t.EXTENDED1
	
	-----删除当日数据
	DELETE FROM NH_WATER_METER WHERE COLLECT_TIME >= @CurDate	
	---- 新增当日数据
	
	INSERT INTO dbo.NH_WATER_METER
		(
		BIT_NO,
		DEVICE_ID,
		TOTAL ,
		INCREMENTAL,
		COLLECT_DATE,
		COLLECT_HOUR,
		COLLECT_TIME
		)
		SELECT t.BIT_NO
	    , t.DEVICE_ID
	    , max (t.ReadData1) AS total
	    , sum (isnull(t.INCREMENTAL,0)) AS INCREMENTAL
	    , t.COLLECT_DATE
	    , t.COLLECT_HOUR
	    , max (t.COLLECT_TIME) AS COLLECT_TIME
	FROM NH_WATER_METER_Temp t
	GROUP  BY t.BIT_NO, t.DEVICE_ID, t.COLLECT_DATE, t.COLLECT_HOUR



GO

