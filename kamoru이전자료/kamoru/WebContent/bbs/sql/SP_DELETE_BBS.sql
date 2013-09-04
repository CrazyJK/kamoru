create or replace PROCEDURE SP_DELETE_BBS
( i_bbsid         IN NUMBER
)IS
	l_idx 		      NUMBER;
	l_InputString	  VARCHAR2(100);
	l_String 		    VARCHAR2(100);

  l_oldTagNames   VARCHAR2(200);
BEGIN

  /* 기존 태그 count 제거 */
  SELECT tags INTO l_oldTagNames FROM bbs WHERE bbsid = i_bbsid;

	l_InputString := l_oldTagNames;
	l_String      := '';
	l_idx         := 0;
	WHILE LENGTH(l_InputString) > 0 LOOP
		l_idx := INSTR(l_InputString, ',');
		IF l_idx = 0 THEN
			l_String := l_InputString; 
			l_InputString := '';
		ELSE
			l_String := SUBSTR(l_InputString, 1, l_idx-1);
			l_InputString := SUBSTR(l_InputString, l_idx+1, LENGTH(l_InputString));
		END IF;
		UPDATE bbs_tag SET status = status - 1 WHERE tagid = TO_NUMBER(l_String);
	END LOOP;

  /* bbs 삭제 표시 */
  UPDATE bbs SET state = 'D' WHERE bbsid = i_bbsid;
  
END SP_DELETE_BBS;