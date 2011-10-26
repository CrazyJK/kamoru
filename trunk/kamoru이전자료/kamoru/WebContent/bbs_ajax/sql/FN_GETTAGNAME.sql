create or replace function		 fn_getTagname
  ( i_tags IN varchar2)
  RETURN  varchar2
  IS
  tags  varchar2(20);
  l_tag   varchar2(10);
	l_value 			varchar2(2000);
	l_retvalue 		varchar2(100);
  l_idx         number;
BEGIN
  tags := i_tags;
  l_retvalue := '';
LOOP
  -- tags 를 ',' 로 잘라 내기
  l_idx := INSTR(tags, ',');
  DBMS_OUTPUT.PUT_LINE('l_idx ' || l_idx);
  l_tag := SUBSTR(tags, 1, l_idx-1);
  DBMS_OUTPUT.PUT_LINE('l_tag ' || l_tag);
  SELECT tagname INTO l_value
    FROM bbs_tag
   WHERE tagid = to_number(l_tag);

  l_retvalue := l_retvalue || ' ' || l_value;
  DBMS_OUTPUT.PUT_LINE('l_retvalue ' || l_retvalue);
  DBMS_OUTPUT.PUT_LINE('tags length' || LENGTH(tags));

  IF LENGTH(tags) <= l_idx+1 THEN
     EXIT;
  END IF;

  tags := SUBSTR(tags, l_idx+1, LENGTH(tags));
  DBMS_OUTPUT.PUT_LINE('tags [' || tags || ']');
  DBMS_OUTPUT.PUT_LINE('tags length' || LENGTH(tags));
  DBMS_OUTPUT.PUT_LINE('================================');
END LOOP;

  RETURN l_retvalue;

END;
