---------------------------------------------
-- Export file for user JCDS               --
-- Created by DELL on 2015/08/21, 10:52:19 --
---------------------------------------------

spool procedure.log

prompt
prompt Creating procedure PROC_ARC_ELECTRIC_DAY_DATA
prompt =============================================
prompt
create or replace procedure JCDS.proc_arc_electric_day_data(curdate in date) is
  peakflag  int;
  peakstart varchar2(2);
  peakend   varchar2(2);

  vallyflag  int;
  vallystart varchar2(2);
  vallyend   varchar2(2);

  dayflag  int;
  daystart varchar2(2);
  dayend   varchar2(2);

  nightflag  int;
  nightstart varchar2(2);
  nightend   varchar2(2);

  dtend date;
begin
  dtend := curdate + 1;

  -- �鵵ǰɾ������ݣ�
  delete from nh_electric_meter_day where day_of_month_key = curdate;

  -- ���㲨�岨�Ȱ������ϵ�����
  --select @peakStart=left(config_value, 2),@vallyFlag=flag  from NH_CONFIG_MANAGE where code = 'BFBG_BF'
  --select @vallyStart=left(config_value,2),@vallyEnd=SUBSTRING(config_value, 7, 2),@peakFlag=flag  from NH_CONFIG_MANAGE where code = 'BFBG_BG'
  select substr(config_value, 1, 2)
    into daystart
    from nh_config_manage
   where code = 'BTWS_BT';
  select substr(config_value, 7, 2)
    into dayend
    from nh_config_manage
   where code = 'BTWS_BT';
  select flag into dayflag from nh_config_manage where code = 'BTWS_BT';
  select substr(config_value, 1, 2)
    into nightstart
    from nh_config_manage
   where code = 'BTWS_WS';
  select substr(config_value, 7, 2)
    into nightend
    from nh_config_manage
   where code = 'BTWS_WS';
  select flag into nightflag from nh_config_manage where code = 'BTWS_WS';

  -- �鵵���
  insert into nh_electric_meter_day
    (id,
     device_id,
     day_of_month_key,
     peak_value,
     vally_value,
     common_value,
     day_daytime_value,
     day_night_value,
     total_day_value,
     total_yoy)
    select seq_nh_electric_meter_day.nextval,
           device_id,
           day_of_month_key,
           peak_value,
           vally_value,
           common_value,
           day_daytime_value,
           day_night_value,
           total_day_value,
           total_yoy
      from (select a.device_id,
                   a.collect_date day_of_month_key,
                   0 peak_value,
                   0 vally_value,
                   0 as common_value,
                   sum(case
                         when a.collect_hour >= to_number(daystart) and
                              a.collect_hour <= to_number(dayend) then
                          a.incremental
                         else
                          0
                       end) as day_daytime_value,
                   sum(case
                         when a.collect_hour >= to_number(nightstart) or
                              a.collect_hour <= to_number(nightend) then
                          a.incremental
                         else
                          0
                       end) as day_night_value,
                   sum(a.incremental) as total_day_value,
                   0 as total_yoy
              from nh_electric_meter a
             where a.collect_time between curdate and dtend
             group by a.collect_date, a.device_id
             order by a.collect_date);

  -- �޸�TOTAL_YOY���
  update nh_electric_meter_day b
     set b.total_yoy =
         (select a.total_day_value
            from nh_electric_meter_day a
           where a.device_id = b.device_id
             and a.day_of_month_key =
                 (b.day_of_month_key - interval '1' day)
             and to_char(b.day_of_month_key, 'dd') =
                 to_char(a.day_of_month_key, 'dd'))
   where exists (select 1
            from nh_electric_meter_day a
           where a.device_id = b.device_id
             and a.day_of_month_key =
                 (b.day_of_month_key - interval '1' day)
             and to_char(b.day_of_month_key, 'dd') =
                 to_char(a.day_of_month_key, 'dd'))
     and b.day_of_month_key = curdate;

end proc_arc_electric_day_data;
/

prompt
prompt Creating procedure PROC_ARC_ELECTRIC_HOUR_DATA
prompt ==============================================
prompt
create or replace procedure JCDS.proc_arc_electric_hour_data(curdate in date) is
  dtend         date;
  dtbgn         date;
  cur           date;
  procedurename varchar2(50) := 'proc_arc_electric_hour_data';
begin
  execute immediate 'TRUNCATE TABLE NH_ELECTRIC_METER_TEMP';

  dtbgn := curdate;
  dtend := curdate + 1;
  cur   := curdate - 3 / 24;
  begin
    insert into execute_debug_log
      (id, name, message, msg_time)
    values
      (seq_execute_debug_log.nextval,
       procedurename,
       '��ʼ������ʱ��....',
       sysdate);
    commit;
  end;

  insert into nh_electric_meter_temp
    (id,
     bit_no,
     device_id,
     read_data1,
     read_data2,
     incremental,
     collect_date,
     collect_hour,
     collect_time)
    select t.id,
           t.bit_no,
           0,
           t.total,
           0.00 as total2,
           0.00 as usepower,
           to_date(to_char(t.collect_time, 'yyyy-mm-dd'), 'yyyy-mm-dd') as collect_date,
           to_number(to_char(t.collect_time - 2 / (24 * 60), 'hh24')) as collect_hour,
           t.collect_time
      from nh_electric_meter_realtime t
     where t.collect_time between cur and dtend
       and t.total is not null;
  begin
    insert into execute_debug_log
      (id, name, message, msg_time)
    values
      (seq_execute_debug_log.nextval,
       procedurename,
       '������ʱ�����.��ʼ������ʱ��read_data2...',
       sysdate);
    commit;
  end;

  update nh_electric_meter_temp
     set read_data2 = get_electric_last_readdata(bit_no, collect_time)
   where collect_time between dtbgn and dtend;
  begin
    insert into execute_debug_log
      (id, name, message, msg_time)
    values
      (seq_execute_debug_log.nextval,
       procedurename,
       '������ʱ��read_data2���',
       sysdate);
    commit;
  end;

  update nh_electric_meter_temp
  --SET INCREMENTAL = ReadData1 - ReadData2
  --SET INCREMENTAL = (case when (ReadData1 - ReadData2)>0 then (ReadData1 - ReadData2) else 0 end)
     set incremental = (case
                         when nvl(read_data1, 0) = 0 or
                              nvl(read_data2, 0) = 0 or
                              (read_data1 - read_data2) < 0 then
                          0
                         else
                          (read_data1 - read_data2)
                       end)
   where collect_time between dtbgn and dtend;

  update nh_electric_meter_temp a
     set device_id =
         (select t.id from device t where a.bit_no = t.extended1)
   where exists (select 1 from device t where a.bit_no = t.extended1)
     and collect_time between dtbgn and dtend;

  --�����ܱ�    
  update nh_electric_meter_temp a
     set is_total =
         (select 1
            from device t
           where a.bit_no = t.extended1
             and t.summarized = 1)
   where exists (select 1
            from device t
           where a.bit_no = t.extended1
             and t.summarized = 1)
     and collect_time between dtbgn and dtend;

  -----ɾ�������
  delete from nh_electric_meter where collect_time between dtbgn and dtend;
  -----ɾ�������(�ܱ�)
  delete from nh_electric_meter_total
   where collect_time between dtbgn and dtend;
  ---- �����������(�ų��ܱ�)

  insert into nh_electric_meter
    (id,
     bit_no,
     device_id,
     total,
     incremental,
     collect_date,
     collect_hour,
     collect_time)
    select seq_nh_electric_meter.nextval,
           bit_no,
           device_id,
           total,
           incremental,
           collect_date,
           collect_hour,
           collect_time
      from (select t.bit_no,
                   t.device_id,
                   max(t.read_data1) as total,
                   sum(nvl(t.incremental, 0)) as incremental,
                   t.collect_date,
                   t.collect_hour,
                   max(t.collect_time) as collect_time
              from nh_electric_meter_temp t
             where t.is_total = 0
               and collect_time between dtbgn and dtend
             group by t.bit_no, t.device_id, t.collect_date, t.collect_hour);

  ---- �����������(�ܱ�)

  insert into nh_electric_meter_total
    (id,
     bit_no,
     device_id,
     total,
     incremental,
     collect_date,
     collect_hour,
     collect_time)
    select seq_nh_electric_meter_total.nextval,
           bit_no,
           device_id,
           total,
           incremental,
           collect_date,
           collect_hour,
           collect_time
      from (select t.bit_no,
                   t.device_id,
                   max(t.read_data1) as total,
                   sum(nvl(t.incremental, 0)) as incremental,
                   t.collect_date,
                   t.collect_hour,
                   max(t.collect_time) as collect_time
              from nh_electric_meter_temp t
             where t.is_total = 1
               and collect_time between dtbgn and dtend
             group by t.bit_no, t.device_id, t.collect_date, t.collect_hour);
end proc_arc_electric_hour_data;
/

prompt
prompt Creating procedure PROC_ARC_ELECTRIC_MONTH_DATA
prompt ===============================================
prompt
create or replace procedure JCDS.proc_arc_electric_month_data(curdate in date) is
  curmonthstr varchar2(7);
begin
  curmonthstr := to_char(curdate, 'yyyy-mm');
  -- ɾ��鵵�·ݵ����
  delete from nh_electric_meter_month where month_key = curmonthstr;

  -- �鵵��Ӧ�·ݵ��������  
  insert into nh_electric_meter_month
    (id,
     device_id,
     month_key,
     peak_value,
     vally_value,
     common_value,
     month_daytime_value,
     month_night_value,
     month_weeken_value,
     total_month_value,
     total_yoy)
    select seq_nh_electric_meter_month.nextval,
           device_id,
           month_key,
           peak_value,
           vally_value,
           common_value,
           month_daytime_value,
           month_night_value,
           month_weeken_value,
           total_month_value,
           total_yoy
      from (select device_id,
                   to_char(day_of_month_key, 'yyyy-mm') month_key,
                   sum(peak_value) peak_value,
                   sum(vally_value) vally_value,
                   sum(common_value) common_value,
                   sum(day_daytime_value) month_daytime_value,
                   sum(day_night_value) month_night_value,
                   sum(case
                         when to_char(day_of_month_key, 'D') in (1, 7) then
                          total_day_value
                         else
                          0
                       end) month_weeken_value,
                   sum(total_day_value) total_month_value,
                   0 total_yoy
              from nh_electric_meter_day
             where to_char(day_of_month_key, 'yyyy-mm') = curmonthstr
             group by to_char(day_of_month_key, 'yyyy-mm'), device_id);

  -- �޸�TOTAL_YOY���
  update nh_electric_meter_month b
     set b.total_yoy =
         (select a.total_month_value
            from nh_electric_meter_month a
           where a.device_id = b.device_id
             and a.month_key =
                 to_char(add_months(to_date(b.month_key, 'yyyy-mm'), -12),
                         'yyyy-mm'))
   where exists
   (select 1
            from nh_electric_meter_month a
           where a.device_id = b.device_id
             and a.month_key =
                 to_char(add_months(to_date(b.month_key, 'yyyy-mm'), -12),
                         'yyyy-mm'))
     and b.month_key = curmonthstr;
end proc_arc_electric_month_data;
/

prompt
prompt Creating procedure PROC_ARC_ELECTRIC_MAIN
prompt =========================================
prompt
create or replace procedure JCDS.proc_arc_electric_main is
  dtend   date;
  curdate date;
begin
  curdate := to_char(sysdate, 'yyyy-mm-dd');
  dtend   := to_char(sysdate, 'yyyy-mm-dd');
  while curdate <= dtend loop
    begin
      proc_arc_electric_hour_data(curdate);
      proc_arc_electric_day_data(curdate);
      proc_arc_electric_month_data(curdate);
      curdate := curdate + interval '1' day;
    end;
  end loop;

  --���µ��������
  update device
     set device_value = get_electric_max_readdata(id)
   where category_id = 93;
end proc_arc_electric_main;
/

prompt
prompt Creating procedure PROC_ARC_ELECTRIC_MAIN_BYHAND
prompt ================================================
prompt
create or replace procedure JCDS.proc_arc_electric_main_byhand(dtstart date,
                                                          dtend   date) is
  curdate date;
  enddate date;
begin

  curdate := dtstart;
  enddate := dtend;
  if enddate is null then
    enddate := sysdate();
  end if;
  while curdate <= enddate loop
    --begin
    proc_arc_electric_hour_data(curdate);
    proc_arc_electric_day_data(curdate);
    proc_arc_electric_month_data(curdate);
    curdate := curdate + 1;
    commit;
    /*exception
      when others then
        rollback;
        raise;
    end;*/
  end loop;

end;
/

prompt
prompt Creating procedure PROC_ARC_ENERGY_DAY_DATA
prompt ===========================================
prompt
create or replace procedure JCDS.proc_arc_energy_day_data(curdate in date) is
  dayflag  int;
  daystart varchar2(2);
  dayend   varchar2(2);

  nightflag  int;
  nightstart varchar2(2);
  nightend   varchar2(2);
  dtend      date;
begin
  dtend := curdate + 1;
  -- �鵵ǰɾ������ݣ�
  delete from nh_energy_meter_day where day_of_month_key = curdate;

  -- ���㲨�岨�Ȱ������ϵ�����
  select substr(config_value, 1, 2)
    into daystart
    from nh_config_manage
   where code = 'BTWS_BT';
  select substr(config_value, 7, 2)
    into dayend
    from nh_config_manage
   where code = 'BTWS_BT';
  select flag into dayflag from nh_config_manage where code = 'BTWS_BT';
  select substr(config_value, 1, 2)
    into nightstart
    from nh_config_manage
   where code = 'BTWS_WS';
  select substr(config_value, 7, 2)
    into nightend
    from nh_config_manage
   where code = 'BTWS_WS';
  select flag into nightflag from nh_config_manage where code = 'BTWS_WS';

  -- �鵵���
  insert into nh_energy_meter_day
    (id,
     device_id,
     day_of_month_key,
     --    PEAK_VALUE,
     --  VALLY_VALUE,
     --  COMMON_VALUE,
     day_daytime_value,
     day_night_value,
     total_day_value,
     total_yoy)
    select seq_nh_energy_meter_day.nextval�� device_id,
           day_of_month_key,
           --    PEAK_VALUE,
           --  VALLY_VALUE,
           --  COMMON_VALUE,
           day_daytime_value,
           day_night_value,
           total_day_value,
           total_yoy
      from (select a.device_id,
                   a.collect_date day_of_month_key
                   -- , 0 PEAK_VALUE
                   -- , 0 VALLY_VALUE
                   -- , 0 AS COMMON_VALUE
                  ,
                   sum(case
                         when a.collect_hour >= to_number(daystart) and
                              a.collect_hour <= to_number(dayend) then
                          a.incremental
                         else
                          0
                       end) as day_daytime_value,
                   sum(case
                         when a.collect_hour >= to_number(nightstart) or
                              a.collect_hour <= to_number(nightend) then
                          a.incremental
                         else
                          0
                       end) as day_night_value,
                   sum(a.incremental) as total_day_value,
                   0 as total_yoy
              from nh_energy_meter a
             where a.collect_time between curdate and dtend
             group by a.collect_date, a.device_id
             order by a.collect_date);

  -- �޸�TOTAL_YOY���
  update nh_energy_meter_day b
     set b.total_yoy =
         (select a.total_day_value
            from nh_energy_meter_day a
           where a.device_id = b.device_id
             and a.day_of_month_key = add_months(b.day_of_month_key, -1))
   where exists
   (select 1
            from nh_energy_meter_day a
           where a.device_id = b.device_id
             and a.day_of_month_key = add_months(b.day_of_month_key, -1))
     and b.day_of_month_key = curdate;
  -- routine body goes here, e.g.
  -- SELECT 'Navicat for SQL Server'
end proc_arc_energy_day_data;
/

prompt
prompt Creating procedure PROC_ARC_ENERGY_HOUR_DATA
prompt ============================================
prompt
create or replace procedure JCDS.proc_arc_energy_hour_data(curdate in date) is
  dtend date;
  dtbgn date;
  cur   date;
begin
  dtbgn := curdate;
  dtend := curdate + 1;
  cur   := curdate - 3 / 24;
  insert into nh_energy_meter_temp
    (id,
     bit_no,
     device_id,
     read_data1 --��ǰ�������
    ,
     read_data2 --��һ�γ������
    ,
     incremental,
     collect_date,
     collect_hour,
     collect_time)
    select t.id,
           t.bit_no,
           0,
           t.total,
           0.00 as total2,
           0.00 as usepower,
           to_date(to_char(t.collect_time, 'yyyy-mm-dd'), 'yyyy-mm-dd') as collect_date,
           to_number(to_char(t.collect_time - 2 / (24 * 60), 'hh24')) as collect_hour,
           t.collect_time
      from nh_energy_meter_realtime t
     where t.collect_time between cur and dtend
       and t.total is not null;

  update nh_energy_meter_temp
     set read_data2 = get_energy_last_readdata(bit_no, collect_time)
   where collect_time between dtbgn and dtend;

  update nh_energy_meter_temp
  --SET INCREMENTAL = ReadData1 - ReadData2
  --SET INCREMENTAL = (case when isnull(ReadData1,0)=0 OR isnull(ReadData2,0) =0 then 0 ELSE (ReadData1 - ReadData2)  end)
     set incremental = (case
                         when nvl(read_data1, 0) = 0 or
                              nvl(read_data2, 0) = 0 or
                              (read_data1 - read_data2) < 0 then
                          0
                         else
                          (read_data1 - read_data2)
                       end)
   where collect_time between dtbgn and dtend;

  update nh_energy_meter_temp a
     set device_id =
         (select t.id from device t where a.bit_no = t.extended1)
   where exists (select 1 from device t where a.bit_no = t.extended1)
     and collect_time between dtbgn and dtend;

  --�����ܱ�    
  update nh_energy_meter_temp a
     set is_total =
         (select 1
            from device t
           where a.bit_no = t.extended1
             and t.summarized = 1)
   where exists (select 1
            from device t
           where a.bit_no = t.extended1
             and t.summarized = 1)
     and collect_time between dtbgn and dtend;

  -----ɾ�������
  delete from nh_energy_meter where collect_time between dtbgn and dtend;
  -----ɾ�������(�ܱ�)
  delete from nh_energy_meter_total
   where collect_time between dtbgn and dtend;
  ---- �����������(�ų��ܱ�)

  insert into nh_energy_meter
    (id,
     bit_no,
     device_id,
     total,
     incremental,
     collect_date,
     collect_hour,
     collect_time)
    select seq_nh_energy_meter.nextval,
           bit_no,
           device_id,
           total,
           incremental,
           collect_date,
           collect_hour,
           collect_time
      from (select t.bit_no,
                   t.device_id,
                   max(t.read_data1) as total,
                   sum(nvl(t.incremental, 0)) as incremental,
                   t.collect_date,
                   t.collect_hour,
                   max(t.collect_time) as collect_time
              from nh_energy_meter_temp t
             where t.is_total = 0
               and collect_time between dtbgn and dtend
             group by t.bit_no, t.device_id, t.collect_date, t.collect_hour);

  ---- �����������(�ܱ�)

  insert into nh_energy_meter_total
    (id,
     bit_no,
     device_id,
     total,
     incremental,
     collect_date,
     collect_hour,
     collect_time)
    select seq_nh_energy_meter_total.nextval,
           bit_no,
           device_id,
           total,
           incremental,
           collect_date,
           collect_hour,
           collect_time
      from (select t.bit_no,
                   t.device_id,
                   max(t.read_data1) as total,
                   sum(nvl(t.incremental, 0)) as incremental,
                   t.collect_date,
                   t.collect_hour,
                   max(t.collect_time) as collect_time
              from nh_energy_meter_temp t
             where t.is_total = 1
               and collect_time between dtbgn and dtend
             group by t.bit_no, t.device_id, t.collect_date, t.collect_hour);
end proc_arc_energy_hour_data;
/

prompt
prompt Creating procedure PROC_ARC_ENERGY_MONTH_DATA
prompt =============================================
prompt
create or replace procedure JCDS.proc_arc_energy_month_data(curdate in date) is
  curmonthstr varchar2(7);
begin
  curmonthstr := to_char(curdate, 'yyyy-mm');
  -- ɾ��鵵�·ݵ����
  delete from nh_energy_meter_month where month_key = curmonthstr;

  -- �鵵��Ӧ�·ݵ��������  
  insert into nh_energy_meter_month
    (id,
     device_id,
     month_key,
     --        PEAK_VALUE,
     --        VALLY_VALUE,
     --       COMMON_VALUE,
     month_daytime_value,
     month_night_value,
     month_weeken_value,
     total_month_value,
     total_yoy)
    select seq_nh_energy_meter_month.nextval,
           device_id,
           month_key,
           --        PEAK_VALUE,
           --        VALLY_VALUE,
           --       COMMON_VALUE,
           month_daytime_value,
           month_night_value,
           month_weeken_value,
           total_month_value,
           total_yoy
      from (select device_id,
                   to_char(day_of_month_key, 'yyyy-mm') month_key,
                   --    SUM (PEAK_VALUE) PEAK_VALUE,
                   --    SUM (VALLY_VALUE) VALLY_VALUE,
                   --    SUM (COMMON_VALUE) COMMON_VALUE,
                   sum(day_daytime_value) month_daytime_value,
                   sum(day_night_value) month_night_value,
                   sum(case
                         when to_char(day_of_month_key, 'D') in (1, 7) then
                          total_day_value
                         else
                          0
                       end) month_weeken_value,
                   sum(total_day_value) total_month_value,
                   0 total_yoy
              from nh_energy_meter_day
             where to_char(day_of_month_key, 'yyyy-mm') = curmonthstr
             group by to_char(day_of_month_key, 'yyyy-mm'), device_id);

  -- �޸�TOTAL_YOY���
  update nh_energy_meter_month b
     set b.total_yoy =
         (select a.total_month_value
            from nh_energy_meter_month a
           where a.device_id = b.device_id
             and a.month_key =
                 to_char(add_months(to_date(b.month_key, 'yyyy-mm'), -12),
                         'yyyy-mm'))
   where exists
   (select 1
            from nh_energy_meter_month a
           where a.device_id = b.device_id
             and a.month_key =
                 to_char(add_months(to_date(b.month_key, 'yyyy-mm'), -12),
                         'yyyy-mm'))
     and b.month_key = curmonthstr;
end proc_arc_energy_month_data;
/

prompt
prompt Creating procedure PROC_ARC_ENERGY_MAIN
prompt =======================================
prompt
create or replace procedure JCDS.PROC_ARC_ENERGY_MAIN
is
dtEnd DATE;
CurDate DATE;
begin
  CurDate := to_char(sysdate,'yyyy-mm-dd');
  dtEnd := to_char(sysdate,'yyyy-mm-dd');
  WHILE CurDate <= dtEnd loop
BEGIN
    PROC_ARC_ENERGY_HOUR_DATA(CurDate);
    PROC_ARC_ENERGY_DAY_DATA(CurDate);
    PROC_ARC_ENERGY_MONTH_DATA(CurDate);
    CurDate := CurDate+ interval '1' day;
END;
end loop;
	--���µ��������
	UPDATE DEVICE SET DEVICE_VALUE = GET_ENERGY_MAX_READDATA (ID)
		WHERE CATEGORY_ID = 94;
end PROC_ARC_ENERGY_MAIN;
/

prompt
prompt Creating procedure PROC_ARC_ENERGY_MAIN_BYHAND
prompt ==============================================
prompt
create or replace procedure JCDS.proc_arc_energy_main_byhand(dtstart date,
                                                        dtend   date) is
  curdate date;
  enddate date;
begin

  curdate := dtstart;
  enddate := dtend;
  if enddate is null then
    enddate := sysdate();
  end if;
  while curdate <= enddate loop
    --begin
  
    proc_arc_energy_hour_data(curdate);
  
    proc_arc_energy_day_data(curdate);
  
    proc_arc_energy_month_data(curdate);
  
    curdate := curdate + 1;
    commit;
    /*exception
      when others then
        rollback;
        raise;
    end;*/
  end loop;

end;
/

prompt
prompt Creating procedure PROC_ARC_GAS_DAY_DATA
prompt ========================================
prompt
create or replace procedure JCDS.proc_arc_gas_day_data(curdate in date) is
  dayflag  int;
  daystart varchar2(2);
  dayend   varchar2(2);

  nightflag  int;
  nightstart varchar2(2);
  nightend   varchar2(2);
  --sql VARCHAR(5000);
  dtend date;
begin
  dtend := curdate + 1;

  -- �鵵ǰɾ������ݣ�
  delete from nh_gas_meter_day where day_of_month_key = curdate;

  -- ���㲨�岨�Ȱ������ϵ�����
  --  select @peakStart=left(config_value,2),@peakEnd=SUBSTRING(config_value, 7, 2),@vallyFlag=flag  from NH_CONFIG_MANAGE where code = 'BFBG_BF'
  --select @vallyStart=left(config_value,2),@vallyEnd=SUBSTRING(config_value, 7, 2),@peakFlag=flag  from NH_CONFIG_MANAGE where code = 'BFBG_BG'
  select substr(config_value, 1, 2)
    into daystart
    from nh_config_manage
   where code = 'BTWS_BT';
  select substr(config_value, 7, 2)
    into dayend
    from nh_config_manage
   where code = 'BTWS_BT';
  select flag into dayflag from nh_config_manage where code = 'BTWS_BT';
  select substr(config_value, 1, 2)
    into nightstart
    from nh_config_manage
   where code = 'BTWS_WS';
  select substr(config_value, 7, 2)
    into nightend
    from nh_config_manage
   where code = 'BTWS_WS';
  select flag into nightflag from nh_config_manage where code = 'BTWS_WS';

  -- �鵵���
  insert into nh_gas_meter_day
    (id,
     device_id,
     day_of_month_key,
     --    PEAK_VALUE,
     --    VALLY_VALUE,
     --  COMMON_VALUE,
     day_daytime_value,
     day_night_value,
     total_day_value,
     total_yoy)
    select seq_nh_gas_meter_day.nextval,
           device_id,
           day_of_month_key,
           --    PEAK_VALUE,
           --    VALLY_VALUE,
           --  COMMON_VALUE,
           day_daytime_value,
           day_night_value,
           total_day_value,
           total_yoy
      from (select a.device_id,
                   a.collect_date day_of_month_key
                   -- , 0 PEAK_VALUE
                   -- , 0 VALLY_VALUE
                   -- , 0 AS COMMON_VALUE
                  ,
                   sum(case
                         when a.collect_hour >= to_number(daystart) and
                              a.collect_hour <= to_number(dayend) then
                          a.incremental
                         else
                          0
                       end) as day_daytime_value,
                   sum(case
                         when a.collect_hour >= to_number(nightstart) or
                              a.collect_hour <= to_number(nightend) then
                          a.incremental
                         else
                          0
                       end) as day_night_value,
                   sum(a.incremental) as total_day_value,
                   0 as total_yoy
              from nh_gas_meter a
             where a.collect_time between curdate and dtend
             group by a.collect_date, a.device_id);

  -- �޸�TOTAL_YOY���
  update nh_gas_meter_day b
     set b.total_yoy =
         (select a.total_day_value
            from nh_gas_meter_day a
           where a.device_id = b.device_id
             and a.day_of_month_key = add_months(b.day_of_month_key, -1))
   where exists
   (select 1
            from nh_gas_meter_day a
           where a.device_id = b.device_id
             and a.day_of_month_key = add_months(b.day_of_month_key, -1))
     and b.day_of_month_key = curdate;
end proc_arc_gas_day_data;
/

prompt
prompt Creating procedure PROC_ARC_GAS_HOUR_DATA
prompt =========================================
prompt
create or replace procedure JCDS.proc_arc_gas_hour_data(curdate in date) is
  dtend date;
  dtbgn date;
  cur   date;
begin
  execute immediate 'TRUNCATE TABLE NH_GAS_METER_TEMP';

  dtbgn := curdate;
  dtend := curdate + 1;
  cur   := curdate - 3 / 24;

  insert into nh_gas_meter_temp
    (id,
     bit_no,
     device_id,
     read_data1,
     read_data2,
     incremental,
     collect_date,
     collect_hour,
     collect_time)
    select t.id,
           t.bit_no,
           0,
           t.total,
           0.00 as total2,
           0.00 as usepower,
           to_date(to_char(t.collect_time, 'yyyy-mm-dd'), 'yyyy-mm-dd') as collect_date,
           to_number(to_char(t.collect_time - 2 / (24 * 60), 'hh24')) as collect_hour,
           t.collect_time
      from nh_gas_meter_realtime t
     where t.collect_time between cur and dtend
       and t.total is not null;

  update nh_gas_meter_temp
     set read_data2 = get_gas_last_readdata(bit_no, collect_time)
   where collect_time between dtbgn and dtend;

  update nh_gas_meter_temp
  --SET INCREMENTAL = ReadData1 - ReadData2
  --SET INCREMENTAL = (case when (ReadData1 - ReadData2)>0 then (ReadData1 - ReadData2) else 0 end)
  --SET INCREMENTAL = (case when isnull(ReadData1,0)=0 OR isnull(ReadData2,0) =0 then 0 ELSE (ReadData1 - ReadData2)  end)
     set incremental = (case
                         when nvl(read_data1, 0) = 0 or
                              nvl(read_data2, 0) = 0 or
                              (read_data1 - read_data2) < 0 then
                          0
                         else
                          (read_data1 - read_data2)
                       end)
   where collect_time between dtbgn and dtend;

  update nh_gas_meter_temp a
     set device_id = ��select t.id from device t
   where a.bit_no = t.extended1)
   where exists (select 1 from device t where a.bit_no = t.extended1)
     and collect_time between dtbgn and dtend;

  --�����ܱ�    
  update nh_gas_meter_temp a
     set is_total =
         (select 1
            from device t
           where a.bit_no = t.extended1
             and t.summarized = 1)
   where exists (select 1
            from device t
           where a.bit_no = t.extended1
             and t.summarized = 1)
     and collect_time between dtbgn and dtend;

  -----ɾ�������
  delete from nh_gas_meter where collect_time between dtbgn and dtend;

  -----ɾ�������(�ܱ�)
  delete from nh_gas_meter_total
   where collect_time between dtbgn and dtend;
  ---- �����������(�ų��ܱ�)

  insert into nh_gas_meter
    (id,
     bit_no,
     device_id,
     total,
     incremental,
     collect_date,
     collect_hour,
     collect_time)
    select seq_nh_gas_meter.nextval,
           bit_no,
           device_id,
           total,
           incremental,
           collect_date,
           collect_hour,
           collect_time
      from (select t.bit_no,
                   t.device_id,
                   max(t.read_data1) as total,
                   sum(nvl(t.incremental, 0)) as incremental,
                   t.collect_date,
                   t.collect_hour,
                   max(t.collect_time) as collect_time
              from nh_gas_meter_temp t
             where t.is_total = 0
               and collect_time between dtbgn and dtend
             group by t.bit_no, t.device_id, t.collect_date, t.collect_hour);

  ---- �����������(�ܱ�)

  insert into nh_gas_meter_total
    (id,
     bit_no,
     device_id,
     total,
     incremental,
     collect_date,
     collect_hour,
     collect_time)
    select seq_nh_gas_meter_total.nextval,
           bit_no,
           device_id,
           total,
           incremental,
           collect_date,
           collect_hour,
           collect_time
      from (select t.bit_no,
                   t.device_id,
                   max(t.read_data1) as total,
                   sum(nvl(t.incremental, 0)) as incremental,
                   t.collect_date,
                   t.collect_hour,
                   max(t.collect_time) as collect_time
              from nh_gas_meter_temp t
             where t.is_total = 1
               and collect_time between dtbgn and dtend
             group by t.bit_no, t.device_id, t.collect_date, t.collect_hour);
end proc_arc_gas_hour_data;
/

prompt
prompt Creating procedure PROC_ARC_GAS_MONTH_DATA
prompt ==========================================
prompt
create or replace procedure JCDS.proc_arc_gas_month_data(curdate in date) is
  curmonthstr varchar2(7);
begin
  curmonthstr := to_char(curdate, 'yyyy-mm');
  -- ɾ��鵵�·ݵ����
  delete from nh_gas_meter_month where month_key = curmonthstr;

  -- �鵵��Ӧ�·ݵ��������  
  insert into nh_gas_meter_month
    (id,
     device_id,
     month_key,
     --      PEAK_VALUE,
     --      VALLY_VALUE,
     --      COMMON_VALUE,
     month_daytime_value,
     month_night_value,
     month_weeken_value,
     total_month_value,
     total_yoy)
    select seq_nh_gas_meter_month.nextval,
           device_id,
           month_key,
           --      PEAK_VALUE,
           --      VALLY_VALUE,
           --      COMMON_VALUE,
           month_daytime_value,
           month_night_value,
           month_weeken_value,
           total_month_value,
           total_yoy
      from (select device_id,
                   to_char(day_of_month_key, 'yyyy-mm') month_key,
                   --    SUM (PEAK_VALUE) PEAK_VALUE,
                   --    SUM (VALLY_VALUE) VALLY_VALUE,
                   --    SUM (COMMON_VALUE) COMMON_VALUE,
                   sum(day_daytime_value) month_daytime_value,
                   sum(day_night_value) month_night_value,
                   sum(case
                         when to_char(day_of_month_key, 'D') in (1, 7) then
                          total_day_value
                         else
                          0
                       end) month_weeken_value,
                   sum(total_day_value) total_month_value,
                   0 total_yoy
              from nh_gas_meter_day
             where to_char(day_of_month_key, 'yyyy-mm') = curmonthstr
             group by to_char(day_of_month_key, 'yyyy-mm'), device_id);

  -- �޸�TOTAL_YOY���
  update nh_gas_meter_month b
     set b.total_yoy =
         (select a.total_month_value
            from nh_gas_meter_month a
           where a.device_id = b.device_id
             and a.month_key =
                 to_char(add_months(to_date(b.month_key, 'yyyy-mm'), -12),
                         'yyyy-mm'))
   where exists
   (select 1
            from nh_gas_meter_month a
           where a.device_id = b.device_id
             and a.month_key =
                 to_char(add_months(to_date(b.month_key, 'yyyy-mm'), -12),
                         'yyyy-mm'))
     and b.month_key = curmonthstr;
end proc_arc_gas_month_data;
/

prompt
prompt Creating procedure PROC_ARC_GAS_MAIN
prompt ====================================
prompt
create or replace procedure JCDS.proc_arc_gas_main is
  dtend   date;
  curdate date;
begin
  curdate := to_char(sysdate, 'yyyy-mm-dd');
  dtend   := to_char(sysdate, 'yyyy-mm-dd');
  while curdate <= dtend loop
    begin
      proc_arc_gas_hour_data(curdate);
      proc_arc_gas_day_data(curdate);
      proc_arc_gas_month_data(curdate);
      curdate := curdate + 1;
    
    end;
  end loop;

  --���µ��������
  update device
     set device_value = get_gas_max_readdate(id)
   where category_id = 108;
end proc_arc_gas_main;
/

prompt
prompt Creating procedure PROC_ARC_GAS_MAIN_BYHAND
prompt ===========================================
prompt
create or replace procedure JCDS.proc_arc_gas_main_byhand(dtstart date,
                                                     dtend   date) is
  curdate date;
  enddate date;
begin

  curdate := dtstart;
  enddate := dtend;
  if enddate is null then
    enddate := sysdate();
  end if;
  while curdate <= enddate loop
    --begin
    proc_arc_gas_hour_data(curdate);
    proc_arc_gas_day_data(curdate);
    proc_arc_gas_month_data(curdate);
    curdate := curdate + 1;
    commit;
    /*exception
      when others then
        rollback;
        raise;
    end;*/
  end loop;

end;
/

prompt
prompt Creating procedure PROC_ARC_WATER_DAY_DATA
prompt ==========================================
prompt
create or replace procedure JCDS.proc_arc_water_day_data(curdate in date) is
  dayflag  int;
  daystart varchar2(2);
  dayend   varchar2(2);

  nightflag  int;
  nightstart varchar2(2);
  nightend   varchar2(2);
  --sql VARCHAR2(5000);
  dtend date;
begin
  dtend := (curdate - 1);
  -- �鵵ǰɾ������ݣ�
  delete from nh_water_meter_day where day_of_month_key = curdate;

  -- ���㲨�岨�Ȱ������ϵ�����
  -- select @peakStart=left(config_value,2),@peakEnd=SUBSTRING(config_value, 7, 2),@vallyFlag=flag  from NH_CONFIG_MANAGE where code = 'BFBG_BF'
  --  select @vallyStart=left(config_value,2),@vallyEnd=SUBSTRING(config_value, 7, 2),@peakFlag=flag  from NH_CONFIG_MANAGE where code = 'BFBG_BG'
  select substr(config_value, 1, 2)
    into daystart
    from nh_config_manage
   where code = 'BTWS_BT';
  select substr(config_value, 7, 2)
    into dayend
    from nh_config_manage
   where code = 'BTWS_BT';
  select flag into dayflag from nh_config_manage where code = 'BTWS_BT';
  select substr(config_value, 1, 2)
    into nightstart
    from nh_config_manage
   where code = 'BTWS_WS';
  select substr(config_value, 7, 2)
    into nightend
    from nh_config_manage
   where code = 'BTWS_WS';
  select flag into nightflag from nh_config_manage where code = 'BTWS_WS';

  -- �鵵���
  insert into nh_water_meter_day
    (id,
     device_id,
     day_of_month_key,
     --  PEAK_VALUE,
     --  VALLY_VALUE,
     --  COMMON_VALUE,
     day_daytime_value,
     day_night_value,
     total_day_value,
     total_yoy)
    select seq_nh_water_meter_day.nextval,
           device_id,
           day_of_month_key,
           --  PEAK_VALUE,
           --  VALLY_VALUE,
           --  COMMON_VALUE,
           day_daytime_value,
           day_night_value,
           total_day_value,
           total_yoy
      from (select a.device_id,
                   a.collect_date day_of_month_key
                   -- , 0 PEAK_VALUE
                   -- , 0 VALLY_VALUE
                   -- , 0 AS COMMON_VALUE
                  ,
                   sum(case
                         when a.collect_hour >= to_number(daystart) and
                              a.collect_hour <= to_number(dayend) then
                          a.incremental
                         else
                          0
                       end) as day_daytime_value,
                   sum(case
                         when a.collect_hour >= to_number(nightstart) or
                              a.collect_hour <= to_number(nightend) then
                          a.incremental
                         else
                          0
                       end) as day_night_value,
                   sum(a.incremental) as total_day_value,
                   0 as total_yoy
              from nh_water_meter a
             where a.collect_time between curdate and dtend
             group by a.collect_date, a.device_id
             order by a.collect_date);

  -- �޸�TOTAL_YOY���
  update nh_water_meter_day b
     set b.total_yoy =
         (select a.total_day_value
            from nh_water_meter_day a
           where a.device_id = b.device_id
             and a.day_of_month_key = add_months(b.day_of_month_key, -1))
   where exists
   (select 1
            from nh_water_meter_day a
           where a.device_id = b.device_id
             and a.day_of_month_key = add_months(b.day_of_month_key, -1))
     and b.day_of_month_key = curdate;

end proc_arc_water_day_data;
/

prompt
prompt Creating procedure PROC_ARC_WATER_HOUR_DATA
prompt ===========================================
prompt
create or replace procedure JCDS.proc_arc_water_hour_data(curdate in date) is
  dtend date;
  dtbgn date;
  cur   date;
begin

  execute immediate 'TRUNCATE TABLE NH_WATER_METER_TEMP';
  dtbgn := curdate;
  dtend := curdate + 1;
  cur   := curdate - 3 / 24;
  insert into nh_water_meter_temp
    (id,
     bit_no,
     device_id,
     read_data1,
     read_data2,
     incremental,
     collect_date,
     collect_hour,
     collect_time)
    select t.id,
           t.bit_no,
           0,
           t.total,
           0.00 as total2,
           0.00 as usepower,
           to_date(to_char(t.collect_time, 'yyyy-mm-dd'), 'yyyy-mm-dd') as collect_date,
           to_number(to_char(t.collect_time - 2 / (24 * 60), 'hh24')) as collect_hour,
           t.collect_time
      from nh_water_meter_realtime t
     where t.collect_time between cur and dtend
       and t.total is not null;

  update nh_water_meter_temp
     set read_data2 = get_water_last_readdata(bit_no, collect_time)
   where collect_time between dtbgn and dtend;

  update nh_water_meter_temp
  --SET INCREMENTAL = ReadData1 - ReadData2
  --SET INCREMENTAL = (case when (ReadData1 - ReadData2)>0 then (ReadData1 - ReadData2) else 0 end)
  --SET INCREMENTAL = (case when isnull(ReadData1,0)=0 OR isnull(ReadData2,0) =0 then 0 ELSE (ReadData1 - ReadData2)  end)
     set incremental = (case
                         when nvl(read_data1, 0) = 0 or
                              nvl(read_data2, 0) = 0 or
                              (read_data1 - read_data2) < 0 then
                          0
                         else
                          (read_data1 - read_data2)
                       end)
   where collect_time between dtbgn and dtend;

  update nh_water_meter_temp a
     set device_id =
         (select t.id
            from device t
           where a.bit_no = t.extended1
             and a.collect_time between dtbgn and dtend)
   where exists (select 1
            from device t
           where a.bit_no = t.extended1
             and a.collect_time between dtbgn and dtend);

  --�����ܱ�    
  update nh_water_meter_temp a
     set is_total =
         (select 1
            from device t
           where a.bit_no = t.extended1
             and t.summarized = 1
             and a.collect_time between dtbgn and dtend)
   where exists (select 1
            from device t
           where a.bit_no = t.extended1
             and t.summarized = 1
             and a.collect_time between dtbgn and dtend);

  -----ɾ�������
  delete from nh_water_meter where collect_time between dtbgn and dtend;
  -----ɾ�������(�ܱ�)
  delete from nh_water_meter_total
   where collect_time between dtbgn and dtend;
  ---- �����������(�ų��ܱ�)

  insert into nh_water_meter
    (id,
     bit_no,
     device_id,
     total,
     incremental,
     collect_date,
     collect_hour,
     collect_time)
    select seq_nh_water_meter.nextval,
           bit_no,
           device_id,
           total,
           incremental,
           collect_date,
           collect_hour,
           collect_time
      from (select t.bit_no,
                   t.device_id,
                   max(t.read_data1) as total,
                   sum(nvl(t.incremental, 0)) as incremental,
                   t.collect_date,
                   t.collect_hour,
                   max(t.collect_time) as collect_time
              from nh_water_meter_temp t
             where t.is_total = 0
               and collect_time between dtbgn and dtend
             group by t.bit_no, t.device_id, t.collect_date, t.collect_hour);
  ---- �����������(�ܱ�)

  insert into nh_water_meter_total
    (id,
     bit_no,
     device_id,
     total,
     incremental,
     collect_date,
     collect_hour,
     collect_time)
    select seq_nh_water_meter_total.nextval,
           bit_no,
           device_id,
           total,
           incremental,
           collect_date,
           collect_hour,
           collect_time
      from (select t.bit_no,
                   t.device_id,
                   max(t.read_data1) as total,
                   sum(nvl(t.incremental, 0)) as incremental,
                   t.collect_date,
                   t.collect_hour,
                   max(t.collect_time) as collect_time
              from nh_water_meter_temp t
             where t.is_total = 1
               and collect_time between dtbgn and dtend
             group by t.bit_no, t.device_id, t.collect_date, t.collect_hour);
end proc_arc_water_hour_data;
/

prompt
prompt Creating procedure PROC_ARC_WATER_MONTH_DATA
prompt ============================================
prompt
create or replace procedure JCDS.proc_arc_water_month_data(curdate in date) is
  curmonthstr varchar2(7);
begin
  curmonthstr := to_char(curdate, 'yyyy-mm');
  -- ɾ��鵵�·�֮������
  delete from nh_water_meter_month where month_key = curmonthstr;

  -- �鵵��Ӧ�·�֮����������

  insert into nh_water_meter_month
    (id,
     device_id,
     month_key,
     --       PEAK_VALUE,
     --       VALLY_VALUE,
     --       COMMON_VALUE,
     month_daytime_value,
     month_night_value,
     month_weeken_value,
     total_month_value,
     total_yoy)
    select seq_nh_water_meter_month.nextval,
           device_id,
           month_key,
           --       PEAK_VALUE,
           --       VALLY_VALUE,
           --       COMMON_VALUE,
           month_daytime_value,
           month_night_value,
           month_weeken_value,
           total_month_value,
           total_yoy
      from (select device_id,
                   to_char(day_of_month_key, 'yyyy-mm') month_key,
                   --    SUM (PEAK_VALUE) PEAK_VALUE,
                   --    SUM (VALLY_VALUE) VALLY_VALUE,
                   --    SUM (COMMON_VALUE) COMMON_VALUE,
                   sum(day_daytime_value) month_daytime_value,
                   sum(day_night_value) month_night_value,
                   sum(case
                         when to_char(day_of_month_key, 'D') in (1, 7) then
                          total_day_value
                         else
                          0
                       end) month_weeken_value,
                   sum(total_day_value) total_month_value,
                   0 total_yoy
              from nh_water_meter_day
             where to_char(day_of_month_key, 'yyyy-mm') = curmonthstr
             group by to_char(day_of_month_key, 'yyyy-mm'), device_id);

  -- �޸�TOTAL_YOY���
  update nh_water_meter_month b
     set b.total_yoy =
         (select a.total_month_value
            from nh_water_meter_month a
           where a.device_id = b.device_id
             and a.month_key =
                 to_char(add_months(to_date(b.month_key, 'yyyy-mm'), -12),
                         'yyyy-mm'))
   where exists
   (select 1
            from nh_water_meter_month a
           where a.device_id = b.device_id
             and a.month_key =
                 to_char(add_months(to_date(b.month_key, 'yyyy-mm'), -12),
                         'yyyy-mm'))
     and b.month_key = curmonthstr;
end proc_arc_water_month_data;
/

prompt
prompt Creating procedure PROC_ARC_WATER_MAIN
prompt ======================================
prompt
create or replace procedure JCDS.proc_arc_water_main is
  dtend   date;
  curdate date;
begin
  curdate := to_char(sysdate, 'yyyy-mm-dd');
  dtend   := to_char(sysdate, 'yyyy-mm-dd');
  while curdate <= dtend loop
    begin
      proc_arc_water_hour_data(curdate);
      proc_arc_water_day_data(curdate);
      proc_arc_water_month_data(curdate);
      curdate := (curdate + 1);
    end;
  end loop;
  --���µ��������
  update device
     set device_value = get_water_max_readdata(id)
   where category_id = 92;
end proc_arc_water_main;
/

prompt
prompt Creating procedure PROC_ARC_WATER_MAIN_BYHAND
prompt =============================================
prompt
create or replace procedure JCDS.proc_arc_water_main_byhand(dtstart date,
                                                       dtend   date) is
  curdate date;
  enddate date;
begin

  curdate := dtstart;
  enddate := dtend;
  if enddate is null then
    enddate := sysdate();
  end if;
  while curdate <= enddate loop
    begin
      proc_arc_water_hour_data(curdate);
      proc_arc_water_day_data(curdate);
      proc_arc_water_month_data(curdate);
      curdate := (curdate + 1);
      commit;
    exception
      when others then
        rollback;
        raise;
    end;
  end loop;

end;
/


spool off
