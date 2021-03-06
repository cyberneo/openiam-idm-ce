INSERT INTO ROLE (ROLE_ID,SERVICE_ID,ROLE_NAME) VALUES ('SUPER_SEC_ADMIN','IDM','Super Security Admin');
INSERT INTO ROLE (ROLE_ID,SERVICE_ID,ROLE_NAME) VALUES ('SEC_ADMIN','IDM','Security Admin');

insert into USER_GRP (USER_GRP_ID, grp_id, user_id) 	values('1000','SUPER_SEC_ADMIN_GRP','3000');
insert into USER_GRP (USER_GRP_ID,grp_id, user_id) 	values('1001','SUPER_SEC_ADMIN_GRP','3001');

INSERT INTO GRP_ROLE(ROLE_ID,GRP_ID, SERVICE_ID) VALUES ('SUPER_SEC_ADMIN','SUPER_SEC_ADMIN_GRP', 'IDM');
commit;


/* After roles are loaded and need to map ldap to role */

INSERT INTO RESOURCE_ROLE(RESOURCE_ID, SERVICE_ID, ROLE_ID, PRIVILEGE_ID)
SELECT '101',SERVICE_ID,ROLE_ID,'NA' FROM ROLE WHERE SERVICE_ID = 'USR_SEC_DOMAIN'; 