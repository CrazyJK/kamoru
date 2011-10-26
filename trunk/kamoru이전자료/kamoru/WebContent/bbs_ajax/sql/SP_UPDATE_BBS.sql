create or replace PROCEDURE SP_UPDATE_BBS
( i_selectTagId   IN  VARCHAR2
, i_newTagName    IN  VARCHAR2
, i_title         IN  VARCHAR2
, i_content       IN  VARCHAR2
, i_modifier      IN  VARCHAR2
, i_bbsid         IN  NUMBER) 
IS
	l_idx 		      NUMBER;
	l_InputString	  VARCHAR2(100);
	l_String 		    VARCHAR2(100);

	l_tagid	        NUMBER;
  l_selectTagID   VARCHAR2(200);
  l_selectTagName VARCHAR2(200);
  
  l_oldTagNames   VARCHAR2(200);
BEGIN
/* �Է� ����
--i_selectTagId	  56,71,
--i_newTagName		TEST,kAmOrU
--i_title		      �׽�Ʈ Ÿ��Ʋ
--i_content		    <p>T
--i_creator       kamoru
*/
  l_selectTagID   := i_selectTagId;
  l_selectTagName := i_newTagName;

  /* �ű� �±��̸� ����(split)�� bbs_tag�� ���Ͽ�
     ���� �±��̸� status ����
     �ű� �±��̸� INSERT
  */
	l_InputString := i_newTagName;
  l_String      := '';
  l_idx         := 0;
	WHILE LENGTH(l_InputString) > 0 LOOP
  
    /* l_tempNewTags �� ���� split �۾�
       . l_String  split���  
       . l_idx     index value
    */
    --DBMS_OUTPUT.PUT_LINE('l_InputString [' || l_InputString || ']');   
		l_idx := INSTR(l_InputString, ',');
    IF l_idx = 0 THEN
      l_String := l_InputString; 
      l_InputString := '';
    ELSE
      l_String := SUBSTR(l_InputString, 1, l_idx-1);
		  l_InputString := SUBSTR(l_InputString, l_idx+1, LENGTH(l_InputString));
    END IF;
    --DBMS_OUTPUT.PUT_LINE('l_String ' || l_String);
      
		BEGIN
      -- �ű� �±��̸��� ���� �±׿� �ִ��� ã�ƺ���
			SELECT tagid 
			  INTO l_tagid 
			  FROM bbs_tag 
			 WHERE lower(tagname) like lower(l_String);
       
      -- NO_DATA_FOUND exception�� �߻����� �ʾ����Ƿ�, �ִ� ��.
    
    -- ������ ���� �±��̸�
		EXCEPTION WHEN NO_DATA_FOUND THEN
			BEGIN
        -- bbs_tag�� �űԷ� ���
				INSERT INTO bbs_tag(tagid, tagname) 
				VALUES (seq_tagid.nextval, l_String);
        SELECT seq_tagid.currval INTO l_tagid FROM dual;
			END;
		END;
    -- bbs_tag status count ������Ʈ�� ���� ��ġ��.
    l_selectTagID := l_selectTagID || to_char(l_tagid) || ',';
	END LOOP;

  /* ���� �±� count ���� */
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

  /* �ű� �±� count */
	l_InputString := l_selectTagID;
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
		UPDATE bbs_tag SET status = status + 1 WHERE tagid = TO_NUMBER(l_String);
	END LOOP;

  UPDATE bbs 
  SET tags = l_selectTagID, title = i_title, content = i_content, modifier = i_modifier 
  WHERE bbsid = i_bbsid;

EXCEPTION
	WHEN OTHERS THEN
		raise_application_error(-91224, sqlerrm);

END SP_UPDATE_BBS;
