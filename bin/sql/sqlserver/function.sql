/****** Object:  UserDefinedFunction [dbo].[Get_ELECTRIC_Last_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Get_ELECTRIC_Last_ReadData]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
DROP FUNCTION [dbo].[Get_ELECTRIC_Last_ReadData]
GO

/****** Object:  UserDefinedFunction [dbo].[Get_ELECTRIC_Max_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Get_ELECTRIC_Max_ReadData]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
DROP FUNCTION [dbo].[Get_ELECTRIC_Max_ReadData]
GO

/****** Object:  UserDefinedFunction [dbo].[Get_ENERGY_Last_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Get_ENERGY_Last_ReadData]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
DROP FUNCTION [dbo].[Get_ENERGY_Last_ReadData]
GO

/****** Object:  UserDefinedFunction [dbo].[Get_ENERGY_Max_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Get_ENERGY_Max_ReadData]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
DROP FUNCTION [dbo].[Get_ENERGY_Max_ReadData]
GO

/****** Object:  UserDefinedFunction [dbo].[Get_GAS_Last_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Get_GAS_Last_ReadData]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
DROP FUNCTION [dbo].[Get_GAS_Last_ReadData]
GO

/****** Object:  UserDefinedFunction [dbo].[Get_GAS_Max_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Get_GAS_Max_ReadData]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
DROP FUNCTION [dbo].[Get_GAS_Max_ReadData]
GO

/****** Object:  UserDefinedFunction [dbo].[Get_WATER_Last_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Get_WATER_Last_ReadData]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
DROP FUNCTION [dbo].[Get_WATER_Last_ReadData]
GO

/****** Object:  UserDefinedFunction [dbo].[Get_WATER_Max_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Get_WATER_Max_ReadData]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
DROP FUNCTION [dbo].[Get_WATER_Max_ReadData]
GO

/****** Object:  UserDefinedFunction [dbo].[Get_ELECTRIC_Last_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create FUNCTION [dbo].[Get_ELECTRIC_Last_ReadData] (@Bit_No VARCHAR(30)
                                            , @COLLECT_TIME DATETIME)
RETURNS FLOAT
BEGIN
    DECLARE @ReadData2 FLOAT
    
    SELECT TOP 1 @ReadData2 = isnull (ReadData1, 0)
    FROM NH_ELECTRIC_METER_Temp    
    WHERE BIT_NO = @Bit_No AND COLLECT_TIME < @COLLECT_TIME
    AND isnull(ReadData1,0)>0
    ORDER BY COLLECT_TIME DESC
    
    RETURN @ReadData2
END

GO

/****** Object:  UserDefinedFunction [dbo].[Get_ELECTRIC_Max_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[Get_ELECTRIC_Max_ReadData] (@device_id BIGINT)
RETURNS FLOAT
BEGIN
    DECLARE @ReadData2 FLOAT
    
    SELECT TOP 1 @ReadData2=total
    FROM NH_ELECTRIC_METER   
    WHERE device_id = @device_id 
    ORDER BY COLLECT_TIME DESC 
    
    RETURN @ReadData2
END


GO

/****** Object:  UserDefinedFunction [dbo].[Get_ENERGY_Last_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[Get_ENERGY_Last_ReadData] (@Bit_No VARCHAR(30)
                                            , @COLLECT_TIME DATETIME)
RETURNS FLOAT
BEGIN
    DECLARE @ReadData2 FLOAT
    
    SELECT TOP 1 @ReadData2 = isnull (ReadData1, 0)
    FROM NH_ENERGY_METER_Temp    
    WHERE BIT_NO = @Bit_No AND COLLECT_TIME < @COLLECT_TIME
    AND isnull(ReadData1,0)>0
    ORDER BY COLLECT_TIME DESC
    
    RETURN @ReadData2
END








GO

/****** Object:  UserDefinedFunction [dbo].[Get_ENERGY_Max_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[Get_ENERGY_Max_ReadData] (@device_id BIGINT)
RETURNS FLOAT
BEGIN
    DECLARE @ReadData2 FLOAT
    
    SELECT TOP 1 @ReadData2=total
    FROM NH_ENERGY_METER   
    WHERE device_id = @device_id 
    ORDER BY COLLECT_TIME DESC 
    
    RETURN @ReadData2
END
GO

/****** Object:  UserDefinedFunction [dbo].[Get_GAS_Last_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[Get_GAS_Last_ReadData] (@Bit_No VARCHAR(30)
                                            , @COLLECT_TIME DATETIME)
RETURNS FLOAT
BEGIN
    DECLARE @ReadData2 FLOAT
    
    SELECT TOP 1 @ReadData2 = isnull (ReadData1, 0)
    FROM NH_GAS_METER_Temp    
    WHERE BIT_NO = @Bit_No AND COLLECT_TIME < @COLLECT_TIME
    AND isnull(ReadData1,0)>0
    ORDER BY COLLECT_TIME DESC
    
    RETURN @ReadData2
END







GO

/****** Object:  UserDefinedFunction [dbo].[Get_GAS_Max_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[Get_GAS_Max_ReadData] (@device_id BIGINT)
RETURNS FLOAT
BEGIN
    DECLARE @ReadData2 FLOAT
    
    SELECT TOP 1 @ReadData2=total
    FROM NH_GAS_METER   
    WHERE device_id = @device_id 
    ORDER BY COLLECT_TIME DESC 
    
    RETURN @ReadData2
END
GO

/****** Object:  UserDefinedFunction [dbo].[Get_WATER_Last_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[Get_WATER_Last_ReadData] (@Bit_No VARCHAR(30)
                                            , @COLLECT_TIME DATETIME)
RETURNS FLOAT
BEGIN
    DECLARE @ReadData2 FLOAT
    
    SELECT TOP 1 @ReadData2 = isnull (ReadData1, 0)
    FROM NH_WATER_METER_Temp    
    WHERE BIT_NO = @Bit_No AND COLLECT_TIME < @COLLECT_TIME
    AND isnull(ReadData1,0)>0
    ORDER BY COLLECT_TIME DESC
    
    RETURN @ReadData2
END






GO

/****** Object:  UserDefinedFunction [dbo].[Get_WATER_Max_ReadData]    Script Date: 08/21/2015 10:44:04 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create FUNCTION [dbo].[Get_WATER_Max_ReadData] (@device_id BIGINT)
RETURNS FLOAT
BEGIN
    DECLARE @ReadData2 FLOAT
    
    SELECT TOP 1 @ReadData2=total
    FROM NH_WATER_METER   
    WHERE device_id = @device_id 
    ORDER BY COLLECT_TIME DESC 
    
    RETURN @ReadData2
END
GO

