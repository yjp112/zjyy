---------------------------------------------
-- Export file for user JCDS               --
-- Created by DELL on 2015/08/21, 14:02:43 --
---------------------------------------------

spool function.log

prompt
prompt Creating type OBJ_DEPT
prompt ======================
prompt
CREATE OR REPLACE TYPE JCDS.obj_dept AS OBJECT (
   id   NUMBER,
   pid number,
   code varchar2(200),
   name   varchar2(200)
)
/

prompt
prompt Creating type T_DEPT
prompt ====================
prompt
CREATE OR REPLACE TYPE JCDS.t_dept AS TABLE OF obj_dept
/

prompt
prompt Creating function FUNC_DEPT_CHILDREN
prompt ====================================
prompt
create or replace function JCDS.func_dept_children(deptid in long) return t_dept
  pipelined is
begin
  for myrow in (select level, d.*
                  from ho_department d
                 start with d.id = deptid
                connect by prior d.id = d.pid)

   loop

    pipe row(obj_dept(myrow.id, myrow.pid, myrow.code, myrow.name));

  end loop;

  return;
end;
/

prompt
prompt Creating function FUNC_DEPT_CONCAT
prompt ==================================
prompt
create or replace function JCDS.func_dept_concat(deptid in long) return varchar2 is
  result varchar2(4000);
begin
  select replace(wmsys.wm_concat(t.name), ',', '/')
    into result
    from (select *
            from (select level, d.*
                    from ho_department d
                   start with d.id = deptid
                  connect by prior d.pid = d.id
                   order by level desc) a
           where a.code != 'ROOT') t;
  return(result);
end func_dept_concat;
/

prompt
prompt Creating function FUNC_DEPT_PARENTS
prompt ===================================
prompt
create or replace function JCDS.func_dept_parents(deptid in long) return t_dept
  pipelined is
begin
  for myrow in (select level, d.*
                  from ho_department d
                 start with d.id = deptid
                connect by prior d.pid = d.id)

   loop

    pipe row(obj_dept(myrow.id, myrow.pid, myrow.code, myrow.name));

  end loop;

  return;
end;
/

prompt
prompt Creating function GET_ELECTRIC_LAST_READDATA
prompt ============================================
prompt
create or replace function JCDS.get_electric_last_readdata(bit  in varchar2,
                                                      coll in date)
  return number is
  result number(10, 2);
begin
  /*SELECT NVL (READ_DATA1, 0) INTO Result
  FROM NH_ELECTRIC_METER_TEMP
  WHERE BIT_NO = bit AND COLLECT_TIME < coll
  AND NVL(READ_DATA1,0)>0 AND ROWNUM=1
  ORDER BY COLLECT_TIME DESC; */

  select read_data1
    into result
    from (select total read_data1,
                 row_number() over(order by b.collect_time desc) rn
            from nh_electric_meter_realtime b
           where b.bit_no = bit
             and b.collect_time < coll
             and nvl(b.total, 0) > 0) t
   where rn = 1;
  return(result);
end get_electric_last_readdata;
/

prompt
prompt Creating function GET_ELECTRIC_MAX_READDATA
prompt ===========================================
prompt
create or replace function JCDS.get_electric_max_readdata(did in number)
  return number is
  result number(10, 2);
begin

  select total
    into result
    from (select row_number() over(order by collect_time desc) rn, total
            from nh_electric_meter
           where device_id = did)
   where rn = 1;
  return(result);
end get_electric_max_readdata;
/

prompt
prompt Creating function GET_ENERGY_LAST_READDATA
prompt ==========================================
prompt
create or replace function JCDS.get_energy_last_readdata(bit  in varchar2,
                                                    coll in date)
  return number is
  result number(10, 2);
begin
  /*select nvl(read_data1, 0)
   into result
   from nh_energy_meter_temp
  where bit_no = bit
    and collect_time < coll
    and nvl(read_data1, 0) > 0
    and rownum = 1
  order by collect_time desc;*/

  select read_data1
    into result
    from (select total read_data1,
                 row_number() over(order by b.collect_time desc) rn
            from nh_energy_meter_realtime b
           where b.bit_no = bit
             and b.collect_time < coll
             and nvl(b.total, 0) > 0) t
   where rn = 1;
  return(result);
end get_energy_last_readdata;
/

prompt
prompt Creating function GET_ENERGY_MAX_READDATA
prompt =========================================
prompt
create or replace function JCDS.get_energy_max_readdata(did in number)
  return number is
  result number(10, 2);
begin
  select total
    into result
    from (select row_number() over(order by collect_time desc) rn, total
            from nh_energy_meter
           where device_id = did)
   where rn = 1;
  return(result);
end get_energy_max_readdata;
/

prompt
prompt Creating function GET_GAS_LAST_READDATA
prompt =======================================
prompt
create or replace function JCDS.get_gas_last_readdata(bit  in varchar2,
                                                 coll in date)
  return number is
  result number(10, 2);
begin
  /*SELECT NVL (READ_DATA1, 0) INTO RESULT
  FROM NH_GAS_METER_TEMP
  WHERE BIT_NO = bit AND COLLECT_TIME < coll
  AND NVL(READ_DATA1,0)>0 AND ROWNUM=1
  ORDER BY COLLECT_TIME DESC;  */

  select read_data1
    into result
    from (select total read_data1,
                 row_number() over(order by b.collect_time desc) rn
            from nh_gas_meter_realtime b
           where b.bit_no = bit
             and b.collect_time < coll
             and nvl(b.total, 0) > 0) t
   where rn = 1;
  return(result);
end get_gas_last_readdata;
/

prompt
prompt Creating function GET_GAS_MAX_READDATE
prompt ======================================
prompt
create or replace function JCDS.get_gas_max_readdate(did in number)
  return number is
  result number(10, 2);
begin

  select total
    into result
    from (select row_number() over(order by collect_time desc) rn, total
            from nh_gas_meter
           where device_id = did)
   where rn = 1;
  return(result);
end get_gas_max_readdate;
/

prompt
prompt Creating function GET_WATER_LAST_READDATA
prompt =========================================
prompt
create or replace function JCDS.get_water_last_readdata(bit  in varchar2,
                                                   coll in date)
  return number is
  result number(10, 2);
begin

  select read_data1
    into result
    from (select total read_data1,
                 row_number() over(order by b.collect_time desc) rn
            from nh_water_meter_realtime b
           where b.bit_no = bit
             and b.collect_time < coll
             and nvl(b.total, 0) > 0) t
   where rn = 1;
  return(result);
end get_water_last_readdata;
/

prompt
prompt Creating function GET_WATER_MAX_READDATA
prompt ========================================
prompt
create or replace function JCDS.get_water_max_readdata(did in number)
  return number is
  result number(10, 2);
begin
  select total
    into result
    from (select row_number() over(order by collect_time desc) rn, total
            from nh_water_meter
           where device_id = did)
   where rn = 1;
  return(result);
end get_water_max_readdata;
/


spool off
