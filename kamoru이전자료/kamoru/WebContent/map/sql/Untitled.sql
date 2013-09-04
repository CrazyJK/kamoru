SELECT m.memberid, m.type, m.name, u.dept_id, u.dept_name, m.customid, m.latitude, 
       m.longitude, m.status, m.site, m.task, m.address, m.starttime, m.arrivaltime, m.dscpt
  FROM map_member m, tb_userinfo u
 WHERE m.type = 'M'
   AND m.customid = u.user_id(+)
   AND u.dept_id = '000010314'
;

SELECT *
FROM   map_member
WHERE  type = 'C'
;